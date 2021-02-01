package com.vaio.java.io.collection;

import breeze.linalg.View.Copy;
import com.google.inject.internal.util.$ObjectArrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 题目: Java 集合用法进行集锦
 * <p>
 * 思路: Java集合脑图 + 常用方法
 * <p>
 * 算法:
 * <p>
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-03-15
 */
public class CollectionDs {

  /**********************************************Set*********************************************/
  private Set hashSet = new HashSet();
  private Set linkedHashSet = new LinkedHashSet();
  private Set treeSet = new TreeSet();


  /**********************************************List*********************************************/
  private List arrayList = new ArrayList();
  private List linkedList = new LinkedList();
  private List vector = new Vector();
  private List stack = new Stack();

  /**********************************************Queue*********************************************/
  private Queue queue = new LinkedList();
  private Queue arrayDeque = new ArrayDeque();
  private Queue priorityQueue = new PriorityQueue();
  private Queue arrayBlockingQueue = new ArrayBlockingQueue(1);
  private Queue linkedBlockingQueue = new LinkedBlockingQueue();
  private Queue priorityBlockingQueue =  new PriorityBlockingQueue();
  private Queue synchronousQueue = new SynchronousQueue();

  /**********************************************Map*********************************************/
  private Map hashMap = new HashMap();
  private Map treeMap = new TreeMap();
  private Map linkedHashMap = new LinkedHashMap();
  private Map hashtable = new Hashtable();


  /**********************************************高级数据结构**************************************/
  private List copyOnWriteArrayList = new CopyOnWriteArrayList();
  private Map concurrentHashMap = new ConcurrentHashMap();

  public static void main(String[] args){
  }
}