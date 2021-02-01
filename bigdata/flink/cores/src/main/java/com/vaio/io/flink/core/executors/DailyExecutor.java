package com.vaio.io.flink.core.executors;

/**
 * Flink Daily Executor.
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2019-05-20.
 */
public interface DailyExecutor {


  void executeOn() throws Exception;

  default void preExecute() {
  }
  default void postExecute() {
  }

  default void execute() {
    preExecute();
    try {
      executeOn();
    } catch (Throwable throwable) {
      throw new RuntimeException("Daily job execute error: " + throwable.getMessage(), throwable);
    }
  }
}