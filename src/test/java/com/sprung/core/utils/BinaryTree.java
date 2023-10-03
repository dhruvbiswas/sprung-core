package com.sprung.core.utils;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

class TreeNode<T> {
    private T t               = null;
    private int height        = 0;
    private TreeNode<T> left  = null;
    private TreeNode<T> right = null;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean hasRightChild() {
        if(this.right != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasLeftChild() {
        if(this.left != null) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.t.toString());
        return builder.toString();
    }
}

public class BinaryTree<T> {

    private TreeNode<T> root            = null;
    private boolean isHeightInitialized = false;

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public TreeNode<T> createNode(T t) {
        // Create a node
        TreeNode<T> n = new TreeNode<T>();
        n.setT(t);
        return n;
    }

    // Add a left child to a node
    public TreeNode<T> addLeftChild(TreeNode<T> node, T t) {
        // Create a node
        TreeNode<T> leftChildNode = this.createNode(t);
        // Add as left child
        node.setLeft(leftChildNode);
        return leftChildNode;
    }

    // Add a right child to a node
    public TreeNode<T> addRightChild(TreeNode<T> node, T t) {
        // Create a node
        TreeNode<T> rightChildNode = this.createNode(t);
        // Add as right child
        node.setRight(rightChildNode);
        return rightChildNode;
    }

    public void initializeNodeHeights() {
        Queue<TreeNode<T>> bfsQ= new LinkedBlockingQueue<TreeNode<T>>();
        this.root.setHeight(0);
        bfsQ.add(this.root);
        while(!bfsQ.isEmpty()) {
            // pop from the queue set child node heights
            TreeNode<T> popped = bfsQ.poll();
            if(popped.hasLeftChild()) {
                TreeNode<T> poppedLeftChild = popped.getLeft();
                poppedLeftChild.setHeight(popped.getHeight() + 1);
                bfsQ.add(poppedLeftChild);
            }
            if(popped.hasRightChild()) {
                TreeNode<T> poppedRightChild = popped.getRight();
                poppedRightChild.setHeight(popped.getHeight() + 1);
                bfsQ.add(poppedRightChild);
            }
        }
    }

    public void findSiblings(TreeNode<T> node) {
        Queue<TreeNode<T>> bfsQ= new LinkedBlockingQueue<TreeNode<T>>();
        Map<Integer, List<TreeNode<T>>> nodeMap = new HashMap<>();
        bfsQ.add(this.root);
        while(!bfsQ.isEmpty()) {
            // pop from the queue set child node heights
            TreeNode<T> popped = bfsQ.poll();
            System.out.print(popped.toString() + " ");
            if(popped.hasLeftChild()) {
                bfsQ.add(popped.getLeft());
            }
            if(popped.hasRightChild()) {
                bfsQ.add(popped.getRight());
            }
        }
    }

    public void bfs() {
        Queue<TreeNode<T>> bfsQ= new LinkedBlockingQueue<TreeNode<T>>();
        bfsQ.add(this.root);
        while(!bfsQ.isEmpty()) {
            // pop from the queue set child node heights
            TreeNode<T> popped = bfsQ.poll();
            System.out.print(popped.toString() + " ");
            if(popped.hasLeftChild()) {
                bfsQ.add(popped.getLeft());
            }
            if(popped.hasRightChild()) {
                bfsQ.add(popped.getRight());
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        binaryTree.setRoot(binaryTree.createNode(1));

        TreeNode<Integer> lChild = binaryTree.addLeftChild(binaryTree.getRoot(), 2);
        TreeNode<Integer> rChild = binaryTree.addRightChild(binaryTree.getRoot(), 3);

        TreeNode<Integer> llChild = binaryTree.addLeftChild(lChild, 4);
        TreeNode<Integer> rlChild = binaryTree.addRightChild(lChild, 5);

        TreeNode<Integer> lrChild = binaryTree.addLeftChild(rChild, 6);
        TreeNode<Integer> rrChild = binaryTree.addRightChild(rChild, 7);

        binaryTree.bfs();
    }
}
