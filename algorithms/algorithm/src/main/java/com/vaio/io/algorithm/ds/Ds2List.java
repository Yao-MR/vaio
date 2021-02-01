package com.vaio.io.algorithm.ds;

import java.util.*;

/**
 *
 * 背景:
 *      链表与数组
 * 思路:
 *
 * 算法:
 *
 *      链表:
 *           链表找倒数第K个节点
 *           求链表交点
 *           链表是否有环
 *           链表反转
 *           链表反转m, n位反转
 *           链表求和
 *           归并两个有序的链表
 *           从有序链表中删除重复节点
 *           删除链表的倒数第 n 个节点
 *           交换链表中的相邻结点
 *           回文链表
 *           分隔链表
 *           链表元素按奇偶聚集
 *
 *      数组:
 *           最长连续不重复子序列
 *           出现一次的两个数字
 *           数字在排序数组中出现的次数
 *           在排序数组中查找元素的第一个和最后一个位置
 *           下一个排列
 *           买卖股票一次
 *           乱序数组求和为target的下标
 *           三数之和
 *           跳跃游戏
 *           最长连续序列
 *           左旋字符串
 *           根据空格反转字符串
 *           数组中元素与下一个比它大的元素之间的距离
 *           循环数组中比当前元素大的下一个元素
 *           一个数组，求子数组的最大和（子数组连续，数组内有正有负）
 *
 *      矩阵:
 *           搜索二维矩阵
 *
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-05-25
 */
public class Ds2List {

  /*********************************************链表********************************************/
  public class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      next = null;
    }
  }

  /**
   * 快慢指针
   * 链表找倒数第K个节点，注意各种异常情况
   */
  public ListNode findKthNode(ListNode head, int k) {
    ListNode fast = head, slow = head;
    int cnt = 0;
    while (fast != null) {
      if (cnt >= k) {
        slow = slow.next;
      }
      fast = fast.next;
      cnt++;

    }
    return slow;
  }

  //求两个链表的交点
  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    ListNode headATag = headA;
    ListNode headBTag = headB;
    while (headATag != headBTag) {
      if (headATag != null) {
        headATag = headATag.next;
      } else {
        headATag = headB;
      }

      if (headBTag != null) {
        headBTag = headBTag.next;
      } else {
        headBTag = headA;
      }
    }
    return headATag == null ? null : headATag;
  }

  //链表是否有环，并返回入环口
  public ListNode detectCycle(ListNode head) {
    //首先这种情况是不满足环存在条件的，所以直接pass
    if (head == null || head.next == null || head.next.next == null) {
      return null;
    }

    ListNode fast = head;
    ListNode slow = head;
    do {
      fast = fast.next.next;
      slow = slow.next;
    } while (fast != slow && fast.next != null && fast.next.next != null);//因为快指针要移动两步，所以要保证不为null

    if (fast != slow) {
      return null;
    }
    //上面进行非环的条件判定，以及相遇位置的定位

    while (head != slow) {//此处是两个指针相遇的地方按照公式(k+l)*2 = k+l+k+j
      head = head.next;
      slow = slow.next;
    }
    return head;
  }


  /**
   * 链表翻转
   * <p>
   * 类似滑动窗口的类似操作,先对将要滑动覆盖的部分进行tmp临时保存，然后开始进行划动
   *
   * @param head
   * @return
   */
  public ListNode reverseList(ListNode head) {
    //首先构造初始位置
    ListNode pre = null;
    ListNode cur = head;
    while (cur != null) {
      ListNode tmp = cur.next;
      cur.next = pre;
      //上面首先记录cur的下一个位置，然后进行反转
      //下面进行滑动移位操作
      pre = cur;
      cur = tmp;
    }
    return pre;
  }

  /**
   * 反转从m到n位置的链表，与每隔几位进行反转的道理是一样的
   * <p>
   * 1: 先走到m位置，记录m位n位
   * 2: 记录下当前接口的两个指针
   * 3: 头插发开始进行反转
   * 4: 对接口进行链接
   * 5: 判断特殊情况的返回值
   *
   * @param head
   * @param m
   * @param n
   * @return
   */
  public ListNode reverseBetween(ListNode head, int m, int n) {
    if (head == null) {
      return null;
    }
    ListNode cur = head;
    ListNode pre = null;

    ListNode conFirst;
    ListNode conSecond;
    while (m > 1) {//进入接口的位置处
      pre = cur;
      cur = cur.next;
      m--;
      n--;
    }
    //中间需要记录的是第一次断开的节点的信息
    conFirst = pre;
    conSecond = cur;

    //上面的逻辑就是进行断开，找到断开的节点的位置
    while (n > 0) {//此时进入接口位置的后方,结果就是现在的cur是只想m的后一个元素的
      ListNode nextToBeCur = cur.next;//这里的惯用套路就是保存下一个将要进行遍历的位置节点
      cur.next = pre;
      pre = cur;
      cur = nextToBeCur;
      m--;
      n--;
    }
    //首先m到n肯定是一个合法的值，但是m的前端节点和n后面的节点可不一定是一定有值的，所以
    if (conFirst != null) {//如果前置节点不为空
      conFirst.next = pre;
    } else {//如果前置节点为空，那么head节点应该进行重置
      head = pre;
    }
    conSecond.next = cur;//后面不用check，因为后面是就是null也是很自然的事情
    return head;
  }


  /**
   * 链表求和
   * 给定两个用链表表示的整数，每个节点包含一个数位。 这些数位是反向存放的，
   * 也就是个位排在链表首部。 编写函数对这两个整数求和，并用链表形式返回结果。
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode head = null;
    ListNode cur = null;
    int lastSumToAdd = 0;
    while (l1 != null || l2 != null || lastSumToAdd != 0) {
      int sum = (l1 == null ? 0 : l1.value) + (l2 == null ? 0 : l2.value) + lastSumToAdd;
      lastSumToAdd = sum / 10;
      int value = sum % 10;

      ListNode newNode = new ListNode(value);
      if (l1 != null) {
        l1 = l1.next;
      }
      if (l2 != null) {
        l2 = l2.next;
      }
      if (head == null) {
        head = newNode;
        cur = newNode;
      } else {
        cur.next = newNode;
        cur = newNode;
      }
    }
    return head;
  }

  //归并两个有序的链表
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) return l2;
    if (l2 == null) return l1;
    if (l1.value < l2.value) {
      l1.next = mergeTwoLists(l1.next, l2);
      return l1;
    } else {
      l2.next = mergeTwoLists(l1, l2.next);
      return l2;
    }
  }

  //从有序链表中删除重复节点
  public ListNode deleteDuplicates(ListNode head) {
    if (head == null || head.next == null) return head;
    head.next = deleteDuplicates(head.next);
    return head.value == head.next.value ? head.next : head;
  }

  //删除链表的倒数第 n 个节点
  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode fast = head;
    while (n-- > 0) {
      fast = fast.next;
    }
    if (fast == null) return head.next;
    ListNode slow = head;
    while (fast.next != null) {
      fast = fast.next;
      slow = slow.next;
    }
    slow.next = slow.next.next;
    return head;
  }

  //交换链表中的相邻结点
  public ListNode swapPairs(ListNode head) {
    ListNode node = new ListNode(-1);
    node.next = head;
    ListNode pre = node;
    while (pre.next != null && pre.next.next != null) {
      ListNode l1 = pre.next, l2 = pre.next.next;
      ListNode next = l2.next;
      l1.next = next;
      l2.next = l1;
      pre.next = l2;

      pre = l1;
    }
    return node.next;
  }

  //回文链表
  public boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;
    ListNode slow = head, fast = head.next;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    if (fast != null) slow = slow.next;  // 偶数节点，让 slow 指向下一个节点
    cut(head, slow);                     // 切成两个链表
    return isEqual(head, reverse(slow));
  }

  private void cut(ListNode head, ListNode cutNode) {
    while (head.next != cutNode) {
      head = head.next;
    }
    head.next = null;
  }

  private ListNode reverse(ListNode head) {
    ListNode newHead = null;
    while (head != null) {
      ListNode nextNode = head.next;
      head.next = newHead;
      newHead = head;
      head = nextNode;
    }
    return newHead;
  }

  private boolean isEqual(ListNode l1, ListNode l2) {
    while (l1 != null && l2 != null) {
      if (l1.value != l2.value) return false;
      l1 = l1.next;
      l2 = l2.next;
    }
    return true;
  }

  //分隔链表
  public ListNode[] splitListToParts(ListNode root, int k) {
    int N = 0;
    ListNode cur = root;
    while (cur != null) {
      N++;
      cur = cur.next;
    }
    int mod = N % k;
    int size = N / k;
    ListNode[] ret = new ListNode[k];
    cur = root;
    for (int i = 0; cur != null && i < k; i++) {
      ret[i] = cur;
      int curSize = size + (mod-- > 0 ? 1 : 0);
      for (int j = 0; j < curSize - 1; j++) {
        cur = cur.next;
      }
      ListNode next = cur.next;
      cur.next = null;
      cur = next;
    }
    return ret;
  }

  //链表元素按奇偶聚集
  public ListNode oddEvenList(ListNode head) {
    if (head == null) {
      return head;
    }
    ListNode odd = head, even = head.next, evenHead = even;
    while (even != null && even.next != null) {
      odd.next = odd.next.next;
      odd = odd.next;
      even.next = even.next.next;
      even = even.next;
    }
    odd.next = evenHead;
    return head;
  }

  /********************************************数组**********************************************/
  //最长不含重复字符的子字符串
  public int lengthOfLongestSubstring(String str) {
    int maxLength = 0;
    int startIndex = -1;
    Map<Character, Integer> meta = new HashMap<>();
    for (int i = 0; i < str.length(); i++) {
      char tmp = str.charAt(i);
      if (meta.containsKey(tmp)) {
        startIndex = Math.max(startIndex, meta.get(tmp));//这里计算的是目前没有出现过重复的字母的的最大位置 abba
      }
      maxLength = Math.max(maxLength, i - startIndex);
      meta.put(tmp, i);
    }
    return maxLength;
  }

  /**
   * 一个整型数组里除了两个数字之外，其他的数字都出现了两次，使用位运算
   * <p>
   * x ^ 0s = x//这里就是异或运算，相同则结果为0，不同则结果为1
   * <p>
   * x ^ 1s = ~x
   * <p>
   * x ^ x = 0
   * <p>
   * x & 0s = 0//这里是与的关系
   * <p>
   * x & 1s = x
   * <p>
   * x & x = x
   * <p>
   * x | 0s = x//这里是或的关系
   * <p>
   * x | 1s = 1s
   * <p>
   * x | x = x
   */
  public void findNumsAppearOnce(int[] array, int num1[], int num2[]) {
    int left = 0;
    int right = 0;
    int index = findFirstBit(array);
    for (int i = 0; i < array.length; i++) {
      if (isBitOneOnIndex(array[i], index)) {
        left ^= array[i];
      } else {
        right ^= array[i];
      }
    }
    num1[0] = left;
    num2[0] = right;
  }

  public int findFirstBit(int[] array) {//目的是找到这些数字的和为首位是1的地方
    int sum = 0;
    for (int i = 0; i < array.length; i++) {
      sum ^= array[i];
    }
    int cnt = 0;
    while (!((sum & 1) == 1)) {
      sum = sum >> 1;
      cnt++;
    }
    return cnt;
  }

  public boolean isBitOneOnIndex(int num, int index) {//检查该位置是否为1
    while (index > 0) {
      num = num >> 1;
      index--;
    }
    return (num & 1) == 1;
  }

  /**
   * 数字在排序数组中出现的次数
   * <p>
   * 1: 小0.5的数字出现的位置
   * 2: 大0.5的数字出现的位置
   */
  public int getNumberOfK(int[] array, int k) {
    return binarySearch(array, k + 0.5f) - binarySearch(array, k - 0.5f);
  }

  //二分查找对小数进行查找
  public int binarySearch(int[] sourceArray, double insertTarget) {
    int start = 0;//第一个元素
    int end = sourceArray.length - 1;//最后一个元素
    while (start <= end) {
      //中间值倾向于靠前
      int mid = start + ((end - start) / 2);
      //这里不可能出现相等的情况
      if (sourceArray[mid] > insertTarget) {
        end = mid - 1;
      } else {
        start = mid + 1;
      }
    }
    //这里找到的一定是比这个元素大的位置甚至是数组越界后的元素
    return start;
  }

  /**
   * 在排序数组中查找元素的第一个和最后一个位置
   */
  public int[] searchRange(int[] nums, int target) {
    int[] result = new int[2];
    int start = binarySearch(nums, target - 0.5f);
    int end = binarySearch(nums, target + 0.5f);
    if (start == end) {
      result[0] = -1;
      result[1] = -1;
      return result;
    } else {
      result[0] = start;
      result[1] = end;
      return result;
    }
  }

  /**
   * 乱序数组中和为target的两个数字的下标
   * 使用元信息存储一次遍历完成
   *
   * @param nums
   * @param target
   * @return
   */
  public int[] twoSum(int[] nums, int target) {
    int[] result = new int[2];
    Map<Integer, Integer> meta = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      int key = target - nums[i];
      if (!meta.containsKey(key)) {
        meta.put(key, i);
      }
    }
    for (int i = 0; i < nums.length; i++) {
      if (meta.containsKey(nums[i]) && meta.get(nums[i]) != i) {
        result[0] = i;
        result[1] = meta.get(nums[i]);
      }
    }
    return result;
  }

  /**
   * 三数之和
   * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
   * <p>
   * 注意：答案中不可以包含重复的三元组。
   *
   * @param nums
   * @return
   */
  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums == null || nums.length < 3) {//优化点
      return result;
    }
    Arrays.sort(nums);
    for (int i = 0; i <= nums.length - 3; i++) {//优化点
      if (nums[i] > 0) {
        break;
      }
      if (i > 0 && nums[i] == nums[i - 1]) continue;//优化点
      int start = i + 1;
      int end = nums.length - 1;
      while (start < end) {
        int sumInterval = nums[start] + nums[end] + nums[i];
        if (sumInterval > 0) {
          end--;
        } else if (sumInterval == 0) {
          result.add(Arrays.asList(nums[i], nums[start], nums[end]));
          while ((start < end) && nums[start] == nums[start + 1]) start++;//优化点
          while ((start < end) && nums[end] == nums[end - 1]) end--;//优化点
          start++;
          end--;
        } else {
          start++;
        }
      }
    }
    return result;
  }

  /**
   * 跳跃游戏
   * <p>
   * 给定一个非负整数数组，你最初位于数组的第一个位置。
   * <p>
   * 数组中的每个元素代表你在该位置可以跳跃的{最大长度}。
   * <p>
   * 判断你是否能够到达最后一个位置。
   * <p>
   * 示例 1:
   * 输入: [2,3,1,1,4]
   * 输出: true
   * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
   */
  public boolean canJump(int[] nums) {
    int currentJump = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i > currentJump) {
        return false;
      } else {
        currentJump = Math.max(currentJump, i + nums[i]);
      }
    }
    return true;
  }

  /**
   * 最长连续序列
   * <p>
   * 给定一个未排序的整数数组，找出最长连续序列的长度。
   * <p>
   * 要求算法的时间复杂度为 O(n)。
   * <p>
   * 示例:
   * 输入: [100, 4, 200, 1, 3, 2]
   * 输出: 4
   * 解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4
   *
   * @param nums
   * @return
   */
  public int longestConsecutive(int[] nums) {
    int maxLengthOfSub = 0;
    Set<Integer> meta = new HashSet<>();
    for (int i = 0; i < nums.length; i++) {
      meta.add(nums[i]);
    }
    //核心在于对每个数字判定小于他的连续数字有多少，全局维护最大值
    for (int i = 0; i < nums.length; i++) {
      int loopTmp = 0;
      int loopNum = nums[i];
      while (meta.contains(loopNum)) {
        loopTmp++;
        loopNum--;
      }
      maxLengthOfSub = Math.max(loopTmp, maxLengthOfSub);
    }
    return maxLengthOfSub;
  }

  /**
   * 下一个排列
   * <p>
   * 输入：nums = [1,2,3]
   * 输出：[1,3,2]
   * <p>
   * 一个数字数组，求比这个数组组成的数字的更大的数字,如果没有就排列成最小的排列
   * 1:从后向前找第一个比前一个数字大的数字
   * 2:从后向前找到第一个比指定位置大的数字进行替换
   * 2:对替换完后的数字进行反转即可
   */
  public void nextPermutation(int[] nums) {
    int i = nums.length - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) {//从后向前找到按照位找到前一位数字更大的数字
      i--;
    }
    if (i >= 0) {//判定是否找到
      int j = nums.length - 1;
      while (j >= 0 && nums[j] <= nums[i]) {
        j--;
      }
      int tmp = nums[i];
      nums[i] = nums[j];
      nums[j] = tmp;
    }
    //这里还涉及到了排序这一步
    reverseArray(nums, i + 1, nums.length - 1);

  }

  public void reverseArray(int[] array, int start, int end) {
    while (start < end) {
      int tmp = array[start];
      array[start] = array[end];
      array[end] = tmp;
      start++;
      end--;
    }
  }

  /**
   * 买卖股票的最佳时机
   * <p>
   * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
   * <p>
   * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
   * <p>
   * 注意：你不能在买入股票前卖出股票
   * <p>
   * 输入: [7,1,5,3,6,4]
   * 输出: 5
   * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
   * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
   * <p>
   * 全程记录最低股票和最大利润
   * 每次遍历如果是低价肯定是进行更新最低价格，
   * 如果是高价肯定是更新最大利润，
   * 全程是按照顺序，具有可见性的排序问题
   */

  public int maxProfit(int[] price) {
    int maxProfit = 0;
    int minPrice = Integer.MAX_VALUE;
    for (int i = 0; i < price.length; i++) {
      if (price[i] < minPrice) {
        minPrice = price[i];
      } else {
        maxProfit = Math.max(maxProfit, price[i] - minPrice);
      }
    }
    return maxProfit;
  }

  /**
   * 左旋字符串
   */
  public String reverseLeftWords(String str, int n) {
    char[] chars = str.toCharArray();
    int leng = n;
    reverse(chars, 0, leng - 1);
    reverse(chars, leng - 1, chars.length - 1);
    reverse(chars, 0, chars.length - 1);
    return chars.toString();
  }

  public void reverse(char[] chars, int i, int j) {
    while (i < j) {
      char tmp = chars[i];
      chars[i] = chars[j];
      chars[j] = tmp;
      i++;
      j--;
    }
  }


  /**
   * 根据空格反转字符串
   */
  public String reverseSentence(String str) {
    int length = str.length();
    char[] chars = str.toCharArray();
    int i = 0, j = 0;
    while (j <= length) {
      // 首先对长度进行判断，不能放在最后，这里是对空格进行隔开的字符串进行反转
      if (j == length || chars[j] == ' ') {
        reverseChars(chars, i, j - 1);
        i = j + 1;
      }
      j++;
    }
    reverseChars(chars, 0, length - 1);
    System.out.println(chars);
    return new String(chars);
  }

  //按照指定的位置进行反转
  public void reverseChars(char[] chars, int i, int j) {
    while (i < j) {
      char tmp = chars[i];
      chars[i] = chars[j];
      chars[j] = tmp;
      i++;
      j--;
    }
  }

  //数组中元素与下一个比它大的元素之间的距离
  public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] dist = new int[n];
    Stack<Integer> indexs = new Stack<>();
    for (int curIndex = 0; curIndex < n; curIndex++) {
      while (!indexs.isEmpty() && temperatures[curIndex] > temperatures[indexs.peek()]) {
        int preIndex = indexs.pop();
        dist[preIndex] = curIndex - preIndex;
      }
      indexs.add(curIndex);
    }
    return dist;
  }

  //循环数组中比当前元素大的下一个元素
  public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] next = new int[n];
    Arrays.fill(next, -1);
    Stack<Integer> pre = new Stack<>();
    for (int i = 0; i < n * 2; i++) {
      int num = nums[i % n];
      while (!pre.isEmpty() && nums[pre.peek()] < num) {
        next[pre.pop()] = num;
      }
      if (i < n){
        pre.push(i);
      }
    }
    return next;
  }

  //一个数组，求子数组的最大和（子数组连续，数组内有正有负）
  public int subArraySum(int[] array){
    int[] dp = new int[array.length];
    Arrays.fill(dp,1);
    dp[0] = array[0];
    int maxValue = Integer.MIN_VALUE;
    for (int i = 1; i < array.length; i++){
      dp[i] = Math.max(array[i],dp[i-1]+array[i]);
      maxValue = Math.max(dp[i],maxValue);
    }
    return maxValue;
  }

  /********************************************数组**********************************************/
  //搜索二维矩阵
  public boolean searchMatrix(int[][] matrix, int target) {
    int row = matrix.length;
    int col = 0;

    while (row >= 0 && col < matrix[0].length) {
      if (matrix[row][col] > target) {
        row--;
      } else if (matrix[row][col] < target) {
        col++;
      } else {
        return true;
      }
    }
    return false;
  }
}
