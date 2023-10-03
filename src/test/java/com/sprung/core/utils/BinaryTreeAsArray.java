package com.sprung.core.utils;

public class BinaryTreeAsArray<T> {

    private T[] binaryTreeArray = null;

    public BinaryTreeAsArray() {}

    private void incrementArray(int size) {
        if (size > 0) {
            if (this.binaryTreeArray == null) {
                binaryTreeArray = (T[])(new Object[size]);
            } else {
                T[] newArray = (T[])(new Object[this.binaryTreeArray.length + size]);
                for (int i = 0; i < newArray.length; i++) {
                    newArray[i] = null;
                }
                for (int i = 0; i < this.binaryTreeArray.length; i++) {
                    newArray[i] = this.binaryTreeArray[i];
                }
            }

        }
    }

    public static int[] binarySearchTreeMinimalHeight(int[] arr) {
        int[] binarySearchTree = null;
        if (arr != null && arr.length > 0) {
            binarySearchTree = new int[arr.length];
            BinaryTreeAsArray.binarySearchTreeMinimalHeight(arr, binarySearchTree, 0, arr.length - 1, 0);
        }
        return binarySearchTree;
    }

    public static void binarySearchTreeMinimalHeight(int[] arr, int[] binarySearchTree,
                                                     int start, int end, int rootIndex) {
        if (start == end) {
            // We have hit a leaf node
            binarySearchTree[rootIndex] = arr[start];
        } else {
            // Get the mid element and insert in root Index
            int mid = start + (end - start) / 2;
            binarySearchTree[rootIndex] = arr[mid];
            // Visit the left subtree
            binarySearchTreeMinimalHeight(arr, binarySearchTree,
                    start, mid - 1, rootIndex * 2 + 1);
            // Visit the right subtree
            binarySearchTreeMinimalHeight(arr, binarySearchTree,
                    mid + 1, end, rootIndex * 2 + 2);
        }
    }

    public static void main(String[] args) {
        // 4, 2, 6, 1, 3, 5, 7
        int[] binarySearchTree = BinaryTreeAsArray.binarySearchTreeMinimalHeight(new int[]{1, 2, 3, 4, 5, 6, 7});
        for (int i = 0; i < binarySearchTree.length; i++) {
            System.out.print(binarySearchTree[i] + " ");
        }
    }

}
