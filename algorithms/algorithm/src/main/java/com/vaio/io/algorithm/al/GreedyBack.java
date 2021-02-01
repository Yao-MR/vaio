package com.vaio.io.algorithm.al;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 背景:
 *      贪心&&回朔
 *
 * 思路:
 *
 * 算法:
 *
 *      贪心
 *
 *
 *      回溯
 *          39. 组合总和
 *
 *
 *
 *
 * 参考:
 *
 * @author yao.wang
 * @date 2021-01-18
 */
public class GreedyBack {

    //39. 组合总和
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<List<Integer>> ans = new ArrayList<>();
        if (len == 0){
            return ans;
        }
        Deque<Integer> path = new ArrayDeque<>();
        dfs(candidates,0,len,target,path,ans);
        return ans;
    }

    public void dfs(int[] candidate, int begin, int len, int target, Deque<Integer> path, List<List<Integer>> ans){
        if (target < 0){
            return;
        }
        if (target == 0){
            ans.add( new ArrayList<>(path));
        }
        for (int i = begin; i < len; i++){
            path.addLast(candidate[i]);
            dfs(candidate,i,len,target - candidate[i],path,ans);
            path.removeLast();
        }
    }
}