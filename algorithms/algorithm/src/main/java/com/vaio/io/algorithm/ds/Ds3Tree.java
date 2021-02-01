/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vaio.io.algorithm.ds;

import java.util.*;

/**
 * 背景: 二叉树算法合集
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * 思路:
*
 * ---------------------------------------------------------------------------------------------------------------------
 * 算法:
 *      遍历:
 *           先序递归&&栈遍历
 *           中序递归&&栈遍历
 *           后序递归&&栈遍历
 *           顺序层次遍历
 *           之字层次遍历
 *           右视图
 *           完全二叉树的最右节点
 *      操作:
 *           镜像反转二叉树
 *           前序中序重建二叉树--------------------多多练习
 *      路径:
 *           根节点到叶子的所有路径
 *           根节点出发路径和等于目标值的路径
 *           二叉树的最大路径和
 *           二叉树最近的公共祖先
 *      插入:
 *           堆插入值删除最小值
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-04-05
 */
public class Ds3Tree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /*************************************************遍历**************************************************************/
    //递归--先序遍历二叉树
    private List<Integer> result = new ArrayList<>();

    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        } else {
            result.add(root.val);
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
        return result;
    }

    //递归-中序递归遍历二叉树
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        } else {
            inorderTraversal(root.left);
            result.add(root.val);
            inorderTraversal(root.right);
        }
        return result;
    }

    //递归-后序遍历二叉树
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        } else {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            result.add(root.val);
        }
        return result;
    }

    //先序栈遍历二叉树 栈实现，先序遍历，按照我们想先遍历那个就把对应节点后放
    public List<Integer> preOrderTraversalWthStack(TreeNode root) {
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> output = new ArrayList<>();
        if (root == null) {
            return output;
        }

        stack.addLast(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pollLast();
            if (node.right != null) {
                stack.addLast(node.right);
            }
            if (node.left != null) {
                stack.addLast(node.left);
            }
            output.add(node.val);
        }
        return output;
    }

    //后序栈遍历二叉树,注意结果存储使用的相关集合,这种后续遍历与前序遍历是完全相反的，包括存储等所有的都是完全相反的逻辑
    public List<Integer> postOrderTraversalWithStack(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) {
            return output;
        }

        stack.addFirst(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pollFirst();
            if (node.left != null) {
                stack.addFirst(node.left);
            }
            if (node.right != null) {
                stack.addFirst(node.right);
            }
            output.addFirst(node.val);//取巧的地方实现后续遍历
        }
        return output;
    }

    //中序栈遍历二叉树 按照二叉树的的中序遍历顺序，必须先到最左节点，然后进行遍历，把右节点进行推入栈中
    //TODO: 2021/1/5 逻辑比较特殊，先进行搁置
    public List<Integer> inorderTraversalWithStack(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> output = new ArrayList<>();
        if (root == null) {
            return output;
        }
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pollLast();
            output.add(cur.val);
            cur = cur.right;
        }
        return output;
    }

    //二叉树的层次遍历，每一层先入栈，默认从左到右的顺序 提前记好每一层的节点个数就可以
    public List<List<Integer>> levelOrderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<List<Integer>> output = new ArrayList<>();

        if (root == null) {
            return output;
        }

        stack.addLast(root);
        while (!stack.isEmpty()) {
            int layerSize = stack.size();
            List<Integer> layerResult = new ArrayList<>();
            for (int i = 0; i < layerSize; i++) {
                TreeNode node = stack.pollFirst();
                layerResult.add(node.val);
                if (node.left != null) {
                    stack.addLast(node.left);
                }
                if (node.right != null) {
                    stack.addLast(node.right);
                }
            }
            output.add(layerResult);
        }
        return output;
    }

    // 二叉树的之字形遍历, 关键是设置一个标示符来标示每层的遍历时的次序，一次遍历完就进行标示符翻转即可
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<List<Integer>> output = new ArrayList<>();
        boolean layerFlag = true;
        if (root == null) {
            return output;
        }

        stack.addLast(root);
        while (!stack.isEmpty()) {
            int layerSize = stack.size();
            LinkedList<Integer> layerResult = new LinkedList<>();
            for (int i = 0; i < layerSize; i++) {
                TreeNode node = stack.pollFirst();
                if (layerFlag) {
                    layerResult.addLast(node.val);
                } else {
                    layerResult.addFirst(node.val);
                }

                if (node.left != null) {
                    stack.addLast(node.left);
                }
                if (node.right != null) {
                    stack.addLast(node.right);
                }
            }
            output.add(layerResult);
            layerFlag = !layerFlag;
        }
        return output;
    }

    //二叉树的右视图, 采用层次遍历的思路在层次遍历最后一个位置的元素进行收集即可
    public List<Integer> rightSideView(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> output = new ArrayList<>();

        if (root == null) {
            return output;
        }

        stack.addLast(root);
        while (!stack.isEmpty()) {
            int layerSize = stack.size();
            List<Integer> layerResult = new ArrayList<>();
            for (int i = 0; i < layerSize; i++) {
                TreeNode node = stack.pollFirst();
                if (i == (layerSize - 1)) {
                    output.add(node.val);
                }
                layerResult.add(node.val);
                if (node.left != null) {
                    stack.addLast(node.left);
                }
                if (node.right != null) {
                    stack.addLast(node.right);
                }
            }
        }
        return output;
    }

    //完全二叉树的最右节点
    public TreeNode getLastNode(TreeNode root){
        //递归出口判定条件
        if (root!=null && root.left == null){
            return root;
        }

        TreeNode left = root.left;
        int leftHight = 0;
        while (left != null){
            left = left.left;
            leftHight ++;
        }

        TreeNode right = root.right;
        int rightHeight = 0;
        while (right != null){
            right = root.left;
            rightHeight++;
        }

        if (leftHight > rightHeight)
            return getLastNode(root.left);
        else
            return getLastNode(root.right);
    }

    /*****************************************************操作**********************************************************/
    //翻转二叉树，递归逻辑实现
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     * 例如，给出
     * <p>
     * 先序遍历 preorder = [3,9,20,15,7]
     * 中序遍历 inorder = [9,3,15,20,7]
     * 返回如下的二叉树：
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     */
    public TreeNode buildTreeFromPreAndIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> metaOfInOrder = new HashMap<>();
        //加入中序遍历的元信息
        for (int i = 0; i < inorder.length; i++) {
            metaOfInOrder.put(inorder[i], i);
        }
        return constructBinTree(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1,
                metaOfInOrder
        );

    }

    //利用前序遍历,中序遍历, 以及中序遍历, 以及中序遍历的相关元信息进行构建完整的二叉树, 主逻辑是与前序进行关联
    public TreeNode constructBinTree(int[] preOrder, int preStart, int preEnd,
                                     int[] inOrder, int inStart, int inEnd,
                                     Map<Integer, Integer> metaMapOfInorder) {
        if (preStart > preEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[preStart]);//构造二叉树的结构，主要是借助中序的位置来对前序进行重新构建
        if (preStart == preEnd) {
            return root;
        }

        //前序遍历的的左右树进行相关配置
        int preStartInInorder = metaMapOfInorder.get(root.val);
        int leftSize = preStartInInorder - inStart;
        int rightSize = inEnd - preStartInInorder;
        root.left = constructBinTree(
                preOrder, preStart + 1, preStart + leftSize,
                inOrder, inStart, inStart + rightSize - 1,
                metaMapOfInorder
        );

        root.right = constructBinTree(
                preOrder, preStart + leftSize + 1, preEnd,
                inOrder, inEnd - rightSize + 1, inEnd,
                metaMapOfInorder
        );
        return root;
    }

    /***********************************************************路径****************************************************/
    // 二叉树从根节点到叶子节点的所有路径
    // 深度遍历的思路，全局结果结合随时添加结果，深度dfs时需要向下传入上面的结果，条件判定是否
    public List<String> binaryTreeAllPathsFromRootToLeaf(TreeNode root) {
        List<String> output = new ArrayList<>();
        if (root == null) {
            return output;
        }
        dfsTravelTree(output, root, new StringBuilder(""));
        return output;
    }

    public void dfsTravelTree(List<String> output, TreeNode node, StringBuilder s) {
        s.append(node.val);

        if (node.left == null && node.right == null) {
            //到叶子节点了,开始进行收割
            output.add(s.toString());
        }
        if (node.left != null) {//这里的s必须进行新建来确保没有进行以前的引用
            dfsTravelTree(output, node.left, new StringBuilder(s).append("->"));
        }
        if (node.right != null) {
            dfsTravelTree(output, node.right, new StringBuilder(s).append("->"));
        }
    }

    /**
     * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
     * <p>
     * 关键是在叶子节点进行判定是否满足题意思, 这个判定比较特殊，要明确判定的的节点位置就是尾节点的位置
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     * 思路，与遍历路径的算法基本一致,只是需要在叶子节点进行判定路径的和是否满足条件，如果满足就进行记下来
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> output = new ArrayList<>();
        LinkedList<Integer> singlePath = new LinkedList<>();
        if (root == null) {
            return output;
        }
        dfsTravelTreeSum(output, root, singlePath, sum);
        return output;
    }

    public void dfsTravelTreeSum(List<List<Integer>> output, TreeNode node, LinkedList<Integer> singlePath, int sum) {
        singlePath.add(node.val);
        sum = sum - node.val;
        if (sum == 0 && node.left == null && node.right == null) {
            output.add(new ArrayList<>(singlePath));
        }
        if (node.left != null) {
            dfsTravelTreeSum(output, node.left, singlePath, sum);
        }

        if (node.right != null) {
            dfsTravelTreeSum(output, node.right, singlePath, sum);
        }
        singlePath.removeLast();
    }

    /**
     * 给定一个非空二叉树，返回其最大路径和。
     * <p>
     * 本题中，路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。
     * 该路径至少包含一个节点，且不一定经过根节点。
     * <p>
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：[1,2,3]
     * <p>
     * 1
     * / \
     * 2   3
     * <p>
     * 输出：6
     * 示例 2：
     * <p>
     * 输入：[-10,9,20,null,null,15,7]
     * <p>
     *    -10
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * <p>
     * 输出：42
     * 实质就是计算该节点向下过程中的最大路径，指的是该节点向左或者向右的节点中哪条的路径最短
     * 在中间过程进行强制判断更新最大路径
     */
    public int maxPathSum(TreeNode root) {
        maxPathSumAndRecord(root);
        return maxSumInAllProcess;
    }

    int maxSumInAllProcess = Integer.MIN_VALUE;//全局跟踪最大路径不断进行相关更新

    public int maxPathSumAndRecord(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftSum = maxPathSumAndRecord(root.left);
        int rightSum = maxPathSumAndRecord(root.right);

        //这里是一个全局的更新机制，用于对全局的最大值进行更新
        maxSumInAllProcess = Math.max(maxSumInAllProcess, root.val + leftSum + rightSum);
        //计算完毕该点的向下最大路径
        return Math.max(0, root.val + Math.max(leftSum, rightSum));
    }

    /**
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * <p>
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * <p>
     * 例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4]
     * <p>
     * <p>
     * <p>
     *  
     * <p>
     * 示例 1:
     * <p>
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * 输出: 3
     * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
     * 示例 2:
     * <p>
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
     * 输出: 5
     * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
     * <p>
     * 全局结果变量维护，dfs进行遍历，遍历中对全局变量进行更新，最后返回结果，dfs过程传入前不进行参数的合法检查，在里面进行合法检查
     * 标示变量标示节点出现在左右或者节点上,来向上进行递归
     */

    TreeNode globalResultNode = null;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return globalResultNode;
    }

    public boolean dfs(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        //判断所要找到节点到底是在哪个位置
        boolean isInl = dfs(node.left, p, q);
        boolean isInr = dfs(node.right, p, q);
        boolean isInNode = node.val == p.val || node.val == q.val;
        if (isInNode) {
            if (isInl || isInr) {
                globalResultNode = node;
            }
            return true;
        } else {
            if (isInl && isInr) {
                globalResultNode = node;
                return true;
            } else {
                return isInl && isInr;
            }
        }
    }

    /*****************************************插入**********************************************/
    public static class VaioHeap {

        private int[] container;
        private int size;
        private int capacity;

        public VaioHeap(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.container = new int[capacity];
        }

        public int getSize() {
            return this.size;
        }

        public int getCapacity() {
            return this.capacity;
        }

        public void getHeapHead() {
            int headValue = container[0];
            System.out.println("移除头节点" + headValue);
            //把节点插入第一个位置然后进行树结构的改变
            siftDown(container, size);
            size--;
        }

        public void insertHeapTail(int val) {
            if (size == capacity) {
                tryGrow();
            }
            container[size] = val;
            size++;
            System.out.println("插入尾节点" + val);
            //把节点插入最后一个位置然后进行树结构的调整
            siftUp(container, size);
        }

        /**
         * 尾部节点头插进行下沉, 完整结构进行下沉调整
         *
         * @param container
         * @param size
         */
        public void siftDown(int[] container, int size) {
            int insertVal = container[size - 1];
            int insertPos = 0;
            container[insertPos] = insertVal;
            //最多定位到最下面的一层就进行停止
            while (insertPos <= ((size - 1) / 2)) {
                int parent = insertPos;
                int parentVal = container[parent];
                int leftChildren = insertPos * 2 + 1;
                int rightChildren = insertPos * 2 + 2;
                int maxChildren =
                        container[leftChildren] > container[rightChildren] ? leftChildren : rightChildren;
                if (parentVal >= container[maxChildren]) {
                    break;
                } else {
                    container[insertPos] = container[maxChildren];
                    insertPos = maxChildren;
                }
                container[insertPos] = insertVal;
            }
        }

        /**
         * 尾插上扬,完整结构进行上扬
         *
         * @param container
         * @param size
         */
        public void siftUp(int[] container, int size) {
            int insertPos = size - 1;
            while (insertPos > 0) {
                int parent = (insertPos - 1) / 2;
                if (container[parent] > container[insertPos]) {
                    return;
                } else {
                    int tmp = container[parent];
                    container[parent] = container[insertPos];
                    container[insertPos] = tmp;
                    insertPos = parent;
                }
            }
        }

        public void tryGrow() {
            int[] newContainer = new int[capacity * 2];
            System.arraycopy(container, 0, newContainer, 0, size);
            this.container = newContainer;
            this.capacity = capacity * 2;
        }
    }
}