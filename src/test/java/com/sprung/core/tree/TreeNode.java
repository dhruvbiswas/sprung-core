package com.sprung.core.tree;

public class TreeNode<N> {

    // Node subtree
    private TreeNode<N> left;
    private TreeNode<N> right;
    private TreeNode<N> parent;

    // Node data
    private N data;

    // Node meta-data
    private int leftSubtreeHeight = 0;
    private int rightSubtreeHeight = 0;
    private int bfactor = 0;

    public TreeNode(N data) {
        this.data = data;
    }

    public TreeNode<N> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<N> left) {
        this.left = left;
    }

    public TreeNode<N> getRight() {
        return right;
    }

    public void setRight(TreeNode<N> right) {
        this.right = right;
    }

    public N getData() {
        return data;
    }

    public void setData(N data) {
        this.data = data;
    }

    public int getLeftSubtreeHeight() {
        return leftSubtreeHeight;
    }

    public void setLeftSubtreeHeight(int leftSubtreeHeight) {
        this.leftSubtreeHeight = leftSubtreeHeight;
    }

    public int getRightSubtreeHeight() {
        return rightSubtreeHeight;
    }

    public void setRightSubtreeHeight(int rightSubtreeHeight) {
        this.rightSubtreeHeight = rightSubtreeHeight;
    }

    public TreeNode<N> getParent() {
        return parent;
    }

    public void setParent(TreeNode<N> parent) {
        this.parent = parent;
    }

    public int getBfactor() {
        return bfactor;
    }

    public void setBfactor(int bfactor) {
        this.bfactor = bfactor;
    }

    public TreeNode<N> addLeftChild(N data) {
        // Create a new node and and as left child
        TreeNode<N> newNode = new TreeNode<>(data);
        this.setLeft(newNode);
        newNode.setParent(this);
        return newNode;
    }

    public TreeNode<N> addRightChild(N data) {
        // Create a new node and add as left child
        TreeNode<N> newNode = new TreeNode<>(data);
        this.setRight(newNode);
        newNode.setParent(this);
        return newNode;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.data.toString() + "|" +
                this.leftSubtreeHeight + ":" +
                this.rightSubtreeHeight + ":" +
                this.bfactor);
        return builder.toString();
    }

}
