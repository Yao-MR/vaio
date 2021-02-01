package com.vaio.io.algorithm.ds;

/**
 * 背景:
 *     排序相关
 *
 * 思路:
 *
 * 算法:
 *      排序:
 *           冒泡排序
 *           快速排序
 *           归并排序
 *           堆排序
 *
 * 参考:
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-05-31
 */
public class Ds5Sort {

  //冒泡
  public void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = arr.length - 1; j > i; j--) {
        if (arr[j] > arr[j - 1]) {
          //交换位置开始下沉
          int tmp = arr[j];
          arr[j] = arr[j - 1];
          arr[j - 1] = tmp;
        }
      }
    }
  }

  //快排
  public void quickSort(int[] a, int l, int r){
    if (l >= r){
      return;
    }
    int templ = l;
    int tempr = r;
    int insertVal = a[templ];//与二叉树一样的判定相关逻辑，选定一个基准点
    while (templ < tempr){
      while (templ < tempr && a[tempr] > insertVal){
        tempr--;

      }
      if (templ < tempr){
        a[templ] = a[tempr];
      }
      while (templ < tempr && a[templ] < insertVal){
        templ ++;
      }
      if (templ < tempr){
        a[tempr] = a[templ];
      }
    }
    a[templ] = insertVal;
    quickSort(a,l,tempr - 1);
    quickSort(a,templ+1, r);
  }

  //归并排序
  public void mergeSort(int a[], int first, int last, int temp[]) {
    if (first >= last){
      return;
    } else {
      int middle = (first + last)/2;
      mergeSort(a, first, middle, temp);
      mergeSort(a, middle + 1, last, temp);
      mergeArray(a,first,middle,last,temp);
    }
  }

  public void mergeArray(int a[], int first, int middle, int end, int temp[]) {//这里的temp的作用只是作为一个集合传入
    //第一个待排序的数列
    int i = first;
    int m = middle;
    //第二个数列
    int j = middle + 1;
    int n = end;

    int k = 0;
    while (i <= m && j <= n) {
      if (a[i] <= a[j]) {
        //将小值传给temp
        temp[k] = a[i];
        k++;
        i++;
      } else {
        temp[k] = a[j];
        k++;
        j++;
      }
    }
    //终止是可能一个遍历到头了
    while (i <= m) {
      temp[k] = a[i];
      k++;
      i++;
    }
    while (j <= n) {
      temp[k] = a[j];
      k++;
      j++;
    }
    //将传进来的暂时的结果返回赋值给原数组
    for (int ii = 0; ii < k; ii++) {
      a[first + ii] = temp[ii];
    }
  }

  //小顶堆的排序
  public void MinSortHeap(int a[]) {
    //a是存元素的数组，n是数组的大小
    for (int i = (a.length - 1) / 2; i >= 0; i--) {
      //堆排序只保证父节点与子节点之间的关系，不保证左右子树之间的关系
      //所以这里是遍历所有父亲节点，按照顺序来进行遍历，保证父节点与子节点之间的关系, 注意其中的顺序，是倒着来
      MinHeapFixdown(a, i, a.length);
    }
  }

  public void MinHeapFixdown(int a[], int i, int n) {
    //传入父亲节点的左子节点
    int j = i * 2 + 1;
    while (j < n) {
      //从左右子树中选择一个比较小的节点进行选取，拿来与父节点进行判定
      if (j + 1 < n && a[j + 1] < a[j]) {
        j++;
      }
      //如果正，常情况是父亲节点的是小于左右子树的就进行返回，无需进行调整
      if (a[i] < a[j]) {//
        break;
      } else {//堆顺序进行替换，确保顶节点是最小的
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
      }
      i = j;//开始遍历
      j = i * 2 + 1;
    }
  }
}
