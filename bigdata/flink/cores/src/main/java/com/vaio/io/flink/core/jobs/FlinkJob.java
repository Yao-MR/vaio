package com.vaio.io.flink.core.jobs;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.Serializable;

/**
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2019-05-20.
 */
public abstract class FlinkJob implements Serializable {
  private static final long serialVersionUID = -3966338341509131440L;

  private ExecutionEnvironment env;
  private StreamExecutionEnvironment sEnv;

  protected FlinkJob() {
    preInitConf();
    initConf();
    postInitConf();
    preInitContext();
    initContext();
    postInitContext();
  }

  protected ExecutionEnvironment env() {
    return this.env;
  }
  protected StreamExecutionEnvironment sEnv() { return this.sEnv; }

  protected void preInitConf() {}
  protected void initConf() {}
  protected void postInitConf() {}

  protected void preInitContext() {}
  protected void initContext() {
    env = ExecutionEnvironment.getExecutionEnvironment();
    sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
  }
  protected void postInitContext() {}
}
