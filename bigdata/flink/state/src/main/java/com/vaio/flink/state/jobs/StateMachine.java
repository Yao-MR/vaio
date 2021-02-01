package com.vaio.flink.state.jobs;

import com.vaio.io.flink.core.executors.DailyExecutor;
import com.vaio.io.flink.core.jobs.FlinkJob;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import com.vaio.flink.state.dfa.State;
import com.vaio.flink.state.event.Alert;
import com.vaio.flink.state.event.Event;
import com.vaio.flink.state.generator.EventsGeneratorSource;
import org.apache.flink.util.Collector;

/**
 * @author yao.wang, (yao.wang@leyantech.com)
 * @date 2019-05-23.
 */
public class StateMachine extends FlinkJob implements DailyExecutor {

  @Override
  public void executeOn() throws Exception {


    final SourceFunction<Event> source;
      double errorRate = 0.0;
      int sleep = 1;

      System.out.printf("Using standalone source with error rate %f and sleep delay %s millis\n",
          errorRate, sleep);
      System.out.println();

      source = new EventsGeneratorSource(errorRate, sleep);


    // ---- main program ----

    // create the environment to create streams and configure execution
    final StreamExecutionEnvironment env = sEnv();
    env.enableCheckpointing(2000L);

      final String checkpointDir = "cjeclp";
      boolean asyncCheckpoints = false;
      env.setStateBackend(new FsStateBackend(checkpointDir, asyncCheckpoints));

    DataStream<Event> events = env.addSource(source);

    DataStream<Alert> alerts = events
        // partition on the address to make sure equal addresses
        // end up in the same state machine flatMap function
        .keyBy(Event::sourceAddress)

        // the function that evaluates the state machine over the sequence of events
        .flatMap(new StateMachineMapper());

    // output the alerts to std-out
      alerts.print();



    // trigger program execution
    env.execute("State machine job");
  }

  // ------------------------------------------------------------------------

  /**
   * The function that maintains the per-IP-address state machines and verifies that the events are
   * consistent with the current state of the state machine. If the event is not consistent with the
   * current state, the function produces an alert.
   */
  @SuppressWarnings("serial")
  static class StateMachineMapper extends RichFlatMapFunction<Event, Alert> {

    /**
     * The state for the current key.
     */
    private ValueState<State> currentState;

    @Override
    public void open(Configuration conf) {
      // get access to the state object
      currentState = getRuntimeContext().getState(
          new ValueStateDescriptor<>("state", State.class));
    }

    @Override
    public void flatMap(Event evt, Collector<Alert> out) throws Exception {
      // get the current state for the key (source address)
      // if no state exists, yet, the state must be the state machine's initial state
      State state = currentState.value();
      if (state == null) {
        state = State.Initial;
      }

      // ask the state machine what state we should go to based on the given event
      State nextState = state.transition(evt.type());

      if (nextState == State.InvalidTransition) {
        // the current event resulted in an invalid transition
        // raise an alert!
        out.collect(new Alert(evt.sourceAddress(), state, evt.type()));
      } else if (nextState.isTerminal()) {
        // we reached a terminal state, clean up the current state
        currentState.clear();
      } else {
        // remember the new state
        currentState.update(nextState);
      }
    }
  }

  public  static void main(String args[]){

    new StateMachine().execute();
  }
}
