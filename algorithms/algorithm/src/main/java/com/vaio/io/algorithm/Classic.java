package com.vaio.io.algorithm;

import java.util.*;

/**
 * 题目:
 *      常见经典算法题
 *
 * 思路:
 *      常见:
 *          LRU
 *          接雨水
 *          最大子序和
 *
 * 算法:
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-05-31
 */
public class Classic {
  public class LRUCache {//将map中的数据给双向链起来

    public class Node {
      int key;
      int value;
      Node pre;
      Node next;

      public Node(int key, int value) {
        this.key = key;
        this.value = value;
      }

      public Node() {
      }
    }

    private Node head = new Node();
    private Node tail = new Node();
    private int size;
    private int capacity;
    private Map<Integer, Node> wareHouse = new HashMap<>();

    public LRUCache(int capacity) {
      this.capacity = capacity;
      this.size = 0;
      this.head.next = tail;
      this.tail.pre = head;
    }

    //删除Node
    private void remove(Node node) {
      node.pre.next = node.next;
      node.next.pre = node.pre;
    }

    private Node removeTail() {
      Node res = tail.pre;
      remove(res);
      return res;
    }

    //添加到头节点的位置
    private void addToHead(Node node) {
      node.pre = head;
      node.next = head.next;
      head.next.pre = node;
      head.next = node;
    }

    public Node get(int key) {
      if (!wareHouse.containsKey(key)) {
        return null;
      } else {
        Node node = wareHouse.get(key);
        //删除
        remove(node);
        addToHead(node);
        return node;
      }
    }

    public void put(int key, int value) {
      Node newNode = new Node(key, value);
      if (!wareHouse.containsKey(key)) {
        if (size < capacity) {
          wareHouse.put(key, newNode);
          size++;
        } else {
          removeTail();
        }
        addToHead(newNode);
      } else {
        Node tmp = wareHouse.get(key);
        wareHouse.remove(tmp);
        remove(tmp);
        addToHead(newNode);
      }
    }
  }

  /**
   * 接雨水
   */
  public int trapMaxRainWater(int[] height) {
    int sum = 0;
    int maxLeft = height[0];
    int maxRight = 0;
    int[] maxRightArray = new int[height.length];

    //维护的是此格子以及向右的最大值，包括此格子,因为决定是否能继续装水的因素就是右边的围墙的木桶理论的高度
    for (int i = height.length - 1; i >= 0; i--) {
      if (i == height.length - 1) {
        maxRightArray[i] = height[i - 1];
      } else {
        maxRightArray[i] = Math.max(height[i], maxRightArray[i + 1]);
      }
    }
    for (int i = 0; i < height.length; i++) {
      //此时我们需要确认左右两边的木桶的高度,同时左右两侧无法进行水的承装
      if (i == height.length - 1) {
        continue;
      }
      if (i == 0) {
        continue;
      }
      int low = Math.max(maxLeft, maxRightArray[i + 1]);
      if (low >= height[i]) {
        sum += low - height[i];
      }
      maxLeft = Math.max(maxLeft, height[i]);
    }
    return sum;
  }

  /**
   * 只是判定两数之和的问题，核心判定另外一个数是否在集合中就ok了
   * @param nums
   * @param target
   * @return
   */
  public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(target - nums[i])) {
        return new int[]{map.get(target - nums[i]), i};
      }
      map.put(nums[i], i);
    }
    throw new IllegalArgumentException("No two sum soulution");
  }

  /**
   * 最大连续子序和
   *
   * 给定一个整数数组 nums，找到一个具有最大和的连续子数组子数组最少包含一个元素，返回其最大和。
   * 逻辑上要求连续，所以必须限定在一个连续上，所以根据和是否为0分割成多段，记录每段的最大和
   *
   *
   * 输入: [-2,1,-3,4,-1,2,1,-5,4],
   * 输出: 6
   * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
   *
   *
   * 动态规划的是首先对数组进行遍历，当前最大连续子序列和为 sum，结果为 ans
   * 如果 sum > 0，则说明 sum 对结果有增益效果，则 sum 保留并加上当前遍历数字
   * 如果 sum <= 0，则说明 sum 对结果无增益效果，需要舍弃，则 sum 直接更新为当前遍历数字
   * 每次比较 sum 和 ans的大小，将最大值置为ans，遍历结束返回结果
   * 时间复杂度：O(n)O(n)
   */
  public int maxSubArray(int[] nums) {
    int sum = 0;
    int ans = nums[0];
    for (int i = 0; i < nums.length; i++) {
      if (sum > 0) {
        sum += nums[i];
      } else {
        sum = 0;
      }
      ans = Math.max(ans, sum);
    }
    return ans;
  }

  /**
   * 最小栈
   */
  class MinStack {
    public Stack<Integer> wareHouse;
    public Stack<Integer> min;
    MinStack() {
      wareHouse = new Stack();
      min = new Stack();
    }
    void push(int x) {
      wareHouse.push(x);
      if (min.isEmpty()) {
        min.push(x);
      } else {
        min.push(Math.min(min.peek(), x));
      }
    }
    void pop() {
      wareHouse.pop();
      min.pop();
    }
    int top() {
      return wareHouse.peek();
    }
    int getMin() {
      return min.peek();
    }
  }

  /**
   * 队列实现栈
   */
  class CQueue {
    Stack<Integer> stack1;
    Stack<Integer> stack2;
    public CQueue() {
      this.stack1 = new Stack();
      this.stack2 = new Stack();
    }
    public void appendTail(int value) {
      stack1.push(value);
    }
    public int deleteHead() {
      if (stack2.isEmpty()){
        while (!stack1.isEmpty()){
          stack2.push(stack1.pop());
        }
      }
      if (stack2.isEmpty()){
        return -1;
      } else {
        return stack2.pop();
      }
    }
  }

  /**
   * 合并区间
   *
   * 给出一个区间的集合，请合并所有重叠的区间。
   *
   * 示例 1:
   *
   * 输入: [[1,3],[2,6],[8,10],[15,18]]
   * 输出: [[1,6],[8,10],[15,18]]
   * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
   *
   * @param intervals
   * @return
   */
  public int[][] merge(int[][] intervals) {
    if (intervals.length == 0) {
      return intervals;
    }
    Arrays.sort(intervals, Comparator.comparing(o -> o[0]));
    int[] internTmp = intervals[0];
    List<int[]> result = new ArrayList<>();
    int length = intervals.length;
    for (int i = 1; i <= length - 1; i++) {
      if (internTmp[1] >= intervals[i][0]) {
        internTmp[1] = Math.max(intervals[i][1], internTmp[1]);
      } else {
        result.add(internTmp);
        internTmp = intervals[i];
      }
    }
    result.add(internTmp);
    int[][] finalResult = new int[result.size()][2];
    for (int i = 0; i < result.size(); i++) {
      finalResult[i] = result.get(i);
    }
    return finalResult;
  }

  /**
   * 原地合并排序数组
   *
   * 两个从末尾开始进行合并，因为末尾的的位置是空出来的
   *
   * @param A
   * @param m
   * @param B
   * @param n
   */
  public void merge(int[] A, int m, int[] B, int n) {
    int place = A.length - 1;
    m--;
    n--;
    while (place >= 0) {
      int max;
      if (m >= 0 && n >= 0) {
        if (A[m] >= B[n]) {
          max = A[n];
          m--;
        } else {
          max = B[n];
          n--;
        }
      } else if (m >= 0) {
        max = A[m];
        m--;
      } else {
        max = B[n];
        n--;
      }
      A[place] = max;
      place--;
    }
  }

  /**
   * 加油站
   *
   * 在一条环路上有 N 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
   *
   * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
   *
   * 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。
   *
   * 说明: 
   *
   * 如果题目有解，该答案即为唯一答案。
   * 输入数组均为非空数组，且长度相同。
   * 输入数组中的元素均为非负数。
   *
   * @param gas
   * @param cost
   * @return
   */
  public int canCompleteCircuit(int[] gas, int[] cost) {
    int len = gas.length;
    int spare = 0;
    int minspare = Integer.MAX_VALUE;

    int length = gas.length;
    int totalTank = 0;
    int currTank = 0;
    int startStation = 0;
    for (int i = 0; i < length; i++){
      totalTank += gas[i] - cost[i];
      currTank += gas[i] - cost[i];
      if (currTank < 0){
        currTank = 0;
        startStation = i+1;
      }
    }
    return totalTank >= 0 ? startStation : -1;
  }
}