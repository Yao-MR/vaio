package com.vaio.io.java.thread.executors.vaioEs;

/**
 * 题目: 线程的实现方式总结
 * <p>
 * 思路:
 * <p>
 * 算法:
 * <p>
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-04
 */
public interface DenyPolicy {

  void reject(Runnable runnable, ThreadPool threadPool);

  public static class DiscardDenyPolicy implements DenyPolicy{
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
      //do  nothing
    }
  }

  public static class AbortDenyPolicy implements DenyPolicy{
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
      throw new RunnableDenyException("the runnable " + runnable + " will be aborted");
    }
  }

  public static class RunnerDenyPolicy implements DenyPolicy{
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
      if (!threadPool.isShutdown()){
        runnable.run();
      }
    }
  }
}
