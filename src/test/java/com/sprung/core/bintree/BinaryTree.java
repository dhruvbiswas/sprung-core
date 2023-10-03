package com.sprung.core.bintree;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BinaryTree<N extends Comparable> {

    private TreeNode<N> root = null;

    public BinaryTree(N d) {
        TreeNode<N> t = new TreeNode(d);
        this.root = t;
    }

    //public List<N> serialize() {
    //
    //}

    private void initialize() {
        List<N> bftList = new ArrayList<>();
        Queue<TreeNode<N>> queue = new LinkedBlockingQueue<>();
        this.root.setSerializationIndex(0);
        queue.add(this.root);
        this.bft(bftList, queue);
    }

    private void bft(List<N> bftList, Queue<TreeNode<N>> queue) {
        while(!queue.isEmpty()) {
            TreeNode<N> popped = queue.poll();
            if(popped.getLeft() != null) {
                popped.getLeft().setSerializationIndex(popped.getSerializationIndex() * 2 + 1);
                queue.add(popped.getLeft());
            }
            if(popped.getRight() != null) {
                popped.getRight().setSerializationIndex(popped.getSerializationIndex() * 2 + 2);
                queue.add(popped.getRight());
            }
        }
    }

}