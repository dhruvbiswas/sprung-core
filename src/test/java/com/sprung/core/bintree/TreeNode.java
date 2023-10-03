package com.sprung.core.bintree;

public class TreeNode<N extends Comparable> {

    private TreeNode<N> left = null;
    private TreeNode<N> right = null;

    private N data;

    private int serializationIndex;

    public TreeNode(N data) {
        this.data = data;
    }

    private TreeNode<N> addLeftChild(TreeNode<N> node, N d) {
        TreeNode<N> n = new TreeNode<>(d);
        n.setLeft(node);
        return n;
    }

    private TreeNode<N> addRightChild(TreeNode<N> node, N d) {
        TreeNode<N> n = new TreeNode<>(d);
        n.setRight(node);
        return n;
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

    public int getSerializationIndex() {
        return serializationIndex;
    }

    public void setSerializationIndex(int serializationIndex) {
        this.serializationIndex = serializationIndex;
    }

}
