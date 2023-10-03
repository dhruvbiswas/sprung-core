package com.sprung.core.utils;

class HackerRankTreeNode {
    int data;
    HackerRankTreeNode left;
    HackerRankTreeNode right;
}

public class HackerRank {

    // Recursively check if the left subtree is a BST
    // Recursively check if the right subtree is a BST
    public static boolean checkBST(HackerRankTreeNode root) {
        if(root.left == null && root.right == null) {
            return true;
        } else if (root.left != null && root.right == null) {
            // non-empty left subtree
            return (HackerRank.checkBST(root.left) && root.data > root.left.data);
        } else if (root.left == null && root.right != null) {
            // non-empty right sub-tree
            return (HackerRank.checkBST(root.right) && root.data < root.right.data);
        } else {
            // non-empty left and right sub-tree
            return (HackerRank.checkBST(root.left) &&
                    HackerRank.checkBST(root.right) &&
                    root.data > root.left.data &&
                    root.data < root.right.data);
        }
    }

}
