package com.vaio.io.algorithm.al;

import java.util.Arrays;
import java.util.List;


/**
 * 背景:
 *      动态规划
 *
 * 思路:
 *      线性数据结构双循环更新
 *      矩阵数据结构进行遍历更新
 *      final可解问题转化为小问题是否可解，即状态转化方程
 *
 * 算法:
 *
 *                斐波那契数列
 *                      斐波那契数
 *                      爬楼梯
 *                      强盗抢劫
 *                      强盗在环形街区抢劫
 *                      信件错排
 *                      母牛生产
 *                矩阵路径
 *                      矩阵的最小路径和
 *                      矩阵的总路径数
 *                数组区间
 *                      数组区间和
 *                      数组中等差递增子区间的个数
 *                分割整数
 *                     分割整数的最大乘积
 *                     按平方数来分割整数
 *                     分割整数构成字母字符串
 *                最长递增子序列
 *                     最长递增子序列
 *                     一组整数对能够构成的最长链
 *                     最长摆动子序列
 *                     最长公共子序列
 *                     最长连续公共子串
 *                背包
 *                     划分数组为和相等的两部分
 *                     改变一组数的正负号使得它们的和为一给定数
 *                     字符构成最多的字符串
 *                     找零钱的最少硬币数
 *                     找零钱的硬币数组合
 *                     字符串按单词列表分割
 *                     组合总和
 *                股票交易
 *                     需要冷却期的股票交易
 *                     需要交易费用的股票交易
 *                     只能进行两次的股票交易
 *                     只能进行 k 次的股票交易
 *                字符串编辑
 *                     删除两个字符串的字符使它们相等
 *                     编辑距离
 *                     复制粘贴字符
 *
 * 参考:
 *
 * @author yao.wang
 * @date 2021-01-18
 */
public class Dynamic {

  //斐波那契数
  int fib(int n) {
    if (n < 2) return n;
    int pre = 0, curr = 1;
    for (int i = 0; i < n - 1; i++) {
      int sum = pre + curr;
      pre = curr;
      curr = sum;
    }
    return curr;
  }

  //爬楼梯
  public int climbStairs(int n) {
    if (n <= 2) {
      return n;
    }
    int pre2 = 1, pre1 = 2;
    for (int i = 2; i < n; i++) {
      int cur = pre1 + pre2;
      pre2 = pre1;
      pre1 = cur;
    }
    return pre1;
  }

  //强盗抢劫
  public int rob(int[] nums) {
    if (nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    if (nums.length == 2) return Math.max(nums[0], nums[1]);
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    for (int i = 2; i < nums.length; i++) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
    }
    return dp[nums.length - 1];
  }

  // 强盗在环形街区抢劫
  public int rob2(int[] nums) {
    int n = nums.length;
    if (n == 0) return 0;
    if (n == 1) return nums[0];
    return Math.max(robRange(nums, 0, n - 1), robRange(nums, 1, n));
  }

  public int robRange(int[] nums, int start, int end) {
    int length = end - start + 1;
    if (length == 0) return 0;
    if (length == 1) return nums[start];
    if (length == 2) return Math.max(nums[0], nums[1]);
    int dp[] = new int[end - start + 1];
    dp[0] = nums[start];
    dp[1] = Math.max(nums[start], nums[start + 1]);
    for (int i = 2; i < length; i++) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[start + i]);
    }
    return dp[end - start];
  }

  // 信件错排
  public int maxWrongStamp(int n) {
    if (n == 0) return 0;
    if (n == 1) return 0;
    int[] dp = new int[n];
    dp[0] = 0;
    dp[1] = 1;
    for (int i = 2; i < n; ++i) {
      dp[i] = (i - 1) * (dp[i - 2] + dp[i - 1]);
    }
    return dp[n - 1];
  }

  //母牛生产
  public int cowNums(int n) {
    int[] dp = new int[n + 1];
    if (n == 0) {
      return 0;
    }
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    if (n == 3) {
      return 3;
    }
    dp[0] = 0;
    dp[1] = 1;
    dp[2] = 2;
    dp[3] = 3;
    for (int i = 4; i <= n; i++) {
      dp[i] = dp[i - 1] + dp[i - 3];
    }
    return dp[n];
  }

  //矩阵的最小路径和
  public int minPathSum(int[][] gird) {
    if (gird.length == 0 || gird[0].length == 0) {
      return 0;
    }
    int m = gird.length;
    int n = gird[0].length;
    int[][] dp = new int[m][n];
    for (int i = 1; i < m; i++) {
      dp[i][0] = dp[i - 1][0] + gird[i][0];
    }
    for (int j = 1; j < n; j++) {
      dp[0][j] = dp[0][j - 1] + gird[0][j - 1];
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1] + gird[i][j]);
      }
    }
    return dp[m - 1][n - 1];
  }

  //矩阵的总路径数
  public int uniquePaths(int row, int col) {
    int[][] dp = new int[row][col];
    for (int i = 0; i < row; i++) {
      dp[row][0] = 1;
    }
    for (int j = 0; j < col; j++) {
      dp[0][col] = 1;
    }
    for (int i = 1; i < row; i++) {
      for (int j = 1; j < col; j++) {
        dp[i][j] = dp[row - 1][col] + dp[row][col - 1];
      }
    }
    return dp[row - 1][col - 1];
  }

  //数组区间和
  class NumArray {

    private int[] sums;

    public NumArray(int[] nums) {
      sums = new int[nums.length + 1];
      for (int i = 1; i <= nums.length; i++) {
        sums[i] = sums[i - 1] + nums[i - 1];
      }
    }

    public int sumRange(int i, int j) {
      return sums[j + 1] - sums[i];
    }
  }

  //数组中等差递增子区间的个数
  public int numberOfArithmeticSlices(int[] a) {
    if (a == null || a.length == 0) {
      return 0;
    }
    int n = a.length;
    int[] dp = new int[n];
    for (int i = 2; i < n; i++) {
      if (a[i] - a[i - 1] == a[i - 1] - a[i - 2]) {
        dp[i] = dp[i - 1] + 1;
      }
    }
    int total = 0;
    for (int cnt : dp) {
      total += cnt;
    }
    return total;
  }

  //分割整数的最大乘积
  public int integerBreak(int n) {
    int[] dp = new int[n + 1];
    for (int i = 2; i <= n; i++) {
      int curMax = 0;
      for (int j = 1; j < 1; j++) {
        curMax = Math.max(curMax, Math.max(j * (i - j), j * dp[i - j]));
      }
    }
    return dp[n];
  }

  //按平方数来分割整数
  public int numSquares(int n) {
    int[] dp = new int[n + 1]; // 默认初始化值都为0
    for (int i = 1; i <= n; i++) {
      dp[i] = i; // 最坏的情况就是每次+1
      for (int j = 1; i - j * j >= 0; j++) {
        dp[i] = Math.min(dp[i], dp[i - j * j] + 1); // 动态转移方程
      }
    }
    return dp[n];
  }

  //分割整数构成字母字符串
  public int numDecodings(String s) {
    int pre = 1;
    int cur = 1;
    //第一个字母
    if (s.charAt(0) == '0') return 0;
    for (int i = 1; i < s.length(); i++) {
      int b = cur;
      if (s.charAt(i) == '0') {
        cur = 0;
      }
      int tmp = (s.charAt(i - 1) - '0') * 10 + s.charAt(i) - '0';//如果用Integer.parseInt时间要慢很多
      if (tmp >= 10 && tmp <= 26) {
        cur += pre;
      }
      pre = b;
    }
    return cur;
  }

  //最长上升子序列
  public int lengthOfLIS(int[] nums) {  //以此字母结尾的递增子序列的长度，所以需要进程初始化为1;最后的取值是数组中的最大值
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);
    for (int i = 0; i < nums.length; i++) {
      for (int j = 0; j < i; j++) {
        if (nums[j] < nums[i]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }
    int maxValue = 0;
    for (int i = 0; i < dp.length; i++) {
      maxValue = Math.max(maxValue, dp[i]);
    }
    return maxValue;
  }

  //一组整数对能够构成的最长链
  public int findLongestChain(int[][] pairs) {
    Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
    int N = pairs.length;
    int[] dp = new int[N];
    Arrays.fill(dp, 1);

    for (int j = 1; j < N; ++j) {
      for (int i = 0; i < j; ++i) {
        if (pairs[i][1] < pairs[j][0])
          dp[j] = Math.max(dp[j], dp[i] + 1);
      }
    }

    int ans = 0;
    for (int x : dp) if (x > ans) ans = x;
    return ans;
  }

  //最长摆动子序列
  public int wiggleMaxLength(int[] nums) {
    int n = nums.length;
    if (n < 2) {
      return n;
    }
    int[] up = new int[n];
    int[] down = new int[n];
    up[0] = down[0] = 1;
    for (int i = 1; i < n; i++) {
      if (nums[i] > nums[i - 1]) {
        up[i] = Math.max(up[i - 1], down[i - 1] + 1);
        down[i] = down[i - 1];
      } else if (nums[i] < nums[i - 1]) {
        up[i] = up[i - 1];
        down[i] = Math.max(up[i - 1] + 1, down[i - 1]);
      } else {
        up[i] = up[i - 1];
        down[i] = down[i - 1];
      }
    }
    return Math.max(up[n - 1], down[n - 1]);
  }

  //最长公共子序列
  public int longestCommonSubsequence(String text1, String text2) {
    //1 矩阵信息表进行初始化
    if (text1.length() == 0 || text2.length() == 0) {
      return 0;
    }
    int string1Length = text1.length();
    int string2Length = text2.length();
    int[][] dp = new int[string1Length + 1][string2Length + 1];

    //2 矩阵信息开始进行关键更新
    for (int i = 1; i <= string1Length; i++) {
      for (int j = 1; j <= string2Length; j++) {
        //矩阵信息更新核心步骤
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }
    return dp[string1Length][string2Length];
  }

  //最长连续公共子串
  public int longestCommonSubString(String str1, String str2) {
    int maxCount = Integer.MIN_VALUE;
    if (str1.length() == 0 || str2.length() == 0) return 0;
    //1. 矩阵信息表初始化，初始化为双边都为0
    int[][] dp = new int[str1.length()][str2.length()];
    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(0) == str2.charAt(i)) {
        dp[0][i] = 1;
      } else {
        dp[0][i] = 0;
      }
    }

    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(0) == str2.charAt(i)) {
        dp[i][0] = 1;
      } else {
        dp[i][0] = 0;
      }
    }
    //2. 矩阵信息初始化完成，开始进行动态规划遍历更新
    for (int i = 1; i < str1.length(); i++) {
      for (int j = 1; j < str2.length(); j++) {
        //矩阵信息核心更新算法
        if (str1.charAt(i) == str2.charAt(j)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = 0;
        }//这里只能是对角线的机制
        maxCount = Math.max(maxCount, dp[i][j]);
      }
    }
    return maxCount;
  }

  //划分数组为和相等的两部分
  public boolean canPartition(int[] nums) {
    int n = nums.length;
    if (n < 2) {
      return false;
    }
    int sum = 0;
    int maxNum = 0;
    for (int num : nums) {
      sum += num;
      maxNum = Math.max(maxNum, num);
    }
    if (sum % 2 != 0) {
      return false;
    }
    int target = sum / 2;
    if (maxNum > target) {
      return false;
    }

    boolean[][] dp = new boolean[n][target + 1];
    for (int i = 0; i < n; i++) {
      dp[i][0] = true;
    }

    dp[0][nums[0]] = true;

    for (int i = 1; i < n; i++) {
      int num = nums[i];
      for (int j = 1; j <= target; j++) {//是否能得到和为j的数组
        if (j >= num) {
          dp[i][j] = dp[i - 1][j] || dp[i - 1][j - num];
        } else {
          dp[i][j] = dp[i - 1][j];
        }
      }

    }
    return dp[n - 1][target];
  }

  //改变一组数的正负号使得它们的和为一给定数
  public int findTargetSumWays(int[] nums, int S) {
    int[][] dp = new int[nums.length][2001];
    dp[0][nums[0] + 1000] = 1;
    dp[0][-nums[0] + 1000] += 1;
    for (int i = 1; i < nums.length; i++) {
      for (int sum = -1000; sum <= 1000; sum++) {
        if (dp[i - 1][sum + 1000] > 0) {
          dp[i][sum + nums[i] + 1000] += dp[i - 1][sum + 1000];
          dp[i][sum - nums[i] + 1000] += dp[i - 1][sum + 1000];
        }
      }
    }
    return S > 1000 ? 0 : dp[nums.length - 1][S + 1000];
  }

  //字符构成最多的字符
  public int findMaxForm(String[] strs, int m, int n) {
    if (strs == null || strs.length == 0) {
      return 0;
    }
    int[][] dp = new int[m + 1][n + 1];
    for (String s : strs) {    // 每个字符串只能用一次
      int ones = 0, zeros = 0;
      for (char c : s.toCharArray()) {
        if (c == '0') {
          zeros++;
        } else {
          ones++;
        }
      }
      for (int i = m; i >= zeros; i--) {
        for (int j = n; j >= ones; j--) {
          dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
        }
      }
    }
    return dp[m][n];
  }

  //找零钱的最少硬币数
  public int coinChange(int[] coins, int amount) {
    //核心单数组信息更新表初始化
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int i = 0; i < dp.length; i++) {
      for (int coin : coins) {
        if (coin > i) {
          continue;
        } else {
          dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
        }
      }
    }
    return dp[amount] == amount + 1 ? -1 : dp[amount];
  }

  //找零钱的硬币数组合
  public int change(int amount, int[] coins) {
    if (coins == null) {
      return 0;
    }
    int[] dp = new int[amount + 1];
    dp[0] = 1;
    for (int coin : coins) {
      for (int i = coin; i <= amount; i++) {
        dp[i] += dp[i - coin];
      }
    }
    return dp[amount];
  }

  //字符串按单词列表分割
  public boolean wordBreak(String s, List<String> wordDict) {
    int n = s.length();
    boolean[] dp = new boolean[n + 1];
    dp[0] = true;
    for (int i = 1; i <= n; i++) {
      for (String word : wordDict) {   // 对物品的迭代应该放在最里层
        int len = word.length();
        if (len <= i && word.equals(s.substring(i - len, i))) {
          dp[i] = dp[i] || dp[i - len];
        }
      }
    }
    return dp[n];
  }

  //组合总和
  public int combinationSum4(int[] nums, int target) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int[] maximum = new int[target + 1];
    maximum[0] = 1;
    Arrays.sort(nums);
    for (int i = 1; i <= target; i++) {
      for (int j = 0; j < nums.length && nums[j] <= i; j++) {
        maximum[i] += maximum[i - nums[j]];
      }
    }
    return maximum[target];
  }

  //无限制股票交易
  public int maxProfit(int[] prices) {
    int maxProfit = 0;
    for (int i = 0; i < prices.length - 1; i++) {
      if (prices[i] < prices[i + 1]) {
        maxProfit += prices[i + 1] - prices[i];
      }
    }
    return maxProfit;
  }

  //需要冷却期的股票交易
  public int maxProfitCool(int[] prices) {
    if (prices == null || prices.length == 0) {
      return 0;
    }
    int N = prices.length;
    int[] buy = new int[N];
    int[] s1 = new int[N];
    int[] sell = new int[N];
    int[] s2 = new int[N];
    s1[0] = buy[0] = -prices[0];
    sell[0] = s2[0] = 0;
    for (int i = 1; i < N; i++) {
      buy[i] = s2[i - 1] - prices[i];
      s1[i] = Math.max(buy[i - 1], s1[i - 1]);
      sell[i] = Math.max(buy[i - 1], s1[i - 1]) + prices[i];
      s2[i] = Math.max(s2[i - 1], sell[i - 1]);
    }
    return Math.max(sell[N - 1], s2[N - 1]);
  }

  //需要交易费用的股票交易
  public int maxProfitFee(int[] prices, int fee) {
    int N = prices.length;
    int[] buy = new int[N];
    int[] s1 = new int[N];
    int[] sell = new int[N];
    int[] s2 = new int[N];
    s1[0] = buy[0] = -prices[0];
    sell[0] = s2[0] = 0;
    for (int i = 1; i < N; i++) {
      buy[i] = Math.max(sell[i - 1], s2[i - 1]) - prices[i];
      s1[i] = Math.max(buy[i - 1], s1[i - 1]);
      sell[i] = Math.max(buy[i - 1], s1[i - 1]) - fee + prices[i];
      s2[i] = Math.max(s2[i - 1], sell[i - 1]);
    }
    return Math.max(sell[N - 1], s2[N - 1]);
  }

  //只能进行两次的股票交易
  public int maxProfit2(int[] prices) {
    int firstBuy = Integer.MIN_VALUE, firstSell = 0;
    int secondBuy = Integer.MIN_VALUE, secondSell = 0;
    for (int curPrice : prices) {
      if (firstBuy < -curPrice) {
        firstBuy = -curPrice;
      }
      if (firstSell < firstBuy + curPrice) {
        firstSell = firstBuy + curPrice;
      }
      if (secondBuy < firstSell - curPrice) {
        secondBuy = firstSell - curPrice;
      }
      if (secondSell < secondBuy + curPrice) {
        secondSell = secondBuy + curPrice;
      }
    }
    return secondSell;
  }

  //只能进行 k 次的股票交易
  public int maxProfitk(int k, int[] prices) {
    int n = prices.length;
    if (k >= n / 2) {   // 这种情况下该问题退化为普通的股票交易问题
      int maxProfit = 0;
      for (int i = 1; i < n; i++) {
        if (prices[i] > prices[i - 1]) {
          maxProfit += prices[i] - prices[i - 1];
        }
      }
      return maxProfit;
    }
    int[][] maxProfit = new int[k + 1][n];
    for (int i = 1; i <= k; i++) {
      int localMax = maxProfit[i - 1][0] - prices[0];
      for (int j = 1; j < n; j++) {
        maxProfit[i][j] = Math.max(maxProfit[i][j - 1], prices[j] + localMax);
        localMax = Math.max(localMax, maxProfit[i - 1][j] - prices[j]);
      }
    }
    return maxProfit[k][n - 1];
  }

  //删除两个字符串的字符使它们相等
  public int deleteCharEqual(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
        }
      }
    }
    return m + n - 2 * dp[m][n];
  }

  //编辑距离
  public int minDistance(String word1, String word2) {
    if (word1 == null || word2 == null) {
      return 0;
    }
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
      dp[i][0] = i;
    }
    for (int i = 1; i <= n; i++) {
      dp[0][i] = i;
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
        }
      }
    }
    return dp[m][n];
  }

  //复制粘贴字符
  public int minSteps(int n) {
    int[] dp = new int[n + 1];
    int h = (int) Math.sqrt(n);
    for (int i = 2; i <= n; i++) {
      dp[i] = i;
      for (int j = 2; j <= h; j++) {
        if (i % j == 0) {
          dp[i] = dp[j] + dp[i / j];
          break;
        }
      }
    }
    return dp[n];
  }

  //最大正方形
  public int maximalSquare(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {//临界条件判定
      return 0;
    }

    int maxSide = 0;
    int rows = matrix.length;
    int columns = matrix[0].length;
    int dp[][] = new int[rows][columns];

    for (int i = 0; i < rows; i++) {//二维动态规划典型
      for (int j = 0; j < columns; j++) {
        if (matrix[i][j] == '1') {
          if (i == 0 || j == 0) {
            dp[i][j] = 1;
          } else {
            dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
          }
          maxSide = Math.max(maxSide, dp[i][j]);
        }
      }
    }
    int maxSquare = maxSide * maxSide;
    return maxSquare;
  }
}
