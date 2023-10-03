package com.sprung.core.utils;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

class Node<T> {
    private T data;
    private Node<T> left;
    private Node<T> right;
    private int height;
    private int hdist;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHdist() {
        return hdist;
    }

    public void setHdist(int hdist) {
        this.hdist = hdist;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.data.toString());
        return builder.toString();
    }
}

public class BinTree<T> {

    private Node<T> root = null;

    public BinTree(Node<T> root) {
        this.root = root;
    }

    public int getHeight() {
        Stack<Node<T>> dftStack = new Stack<>();
        return this.getHeight(this.root, dftStack, 0);
    }

    public int getSubTreeHeight(Node<T> node) {
        int height = 0;
        // Compute the height of this subtree
        if (node != null) {
            Stack<Node<T>> dftStack = new Stack<>();
            height = this.getHeight(node, dftStack, 0);
        }
        return height;
    }

    /*
     * Run a BFT and initialize each tree node attribute
     */
    public void initializeTreeNodeAttributes() {
        Queue<Node<T>> bftQueue = new LinkedBlockingQueue<>();
        this.root.setHeight(0);
        this.root.setHdist(0);
        bftQueue.add(this.root);
        while (bftQueue.size() != 0) {
            Node<T> poppedNode = bftQueue.poll();
            if (poppedNode.getLeft() != null) {
                // Set the height of the left node to
                // 1 unit greater than the popped node
                poppedNode.getLeft().setHeight(poppedNode.getHeight() + 1);
                // Set the horizontal distance of the left node to
                // 1 unit less than the horizontal distance of the popped node
                poppedNode.getLeft().setHdist(poppedNode.getHdist() - 1);
                bftQueue.add(poppedNode.getLeft());
            }
            if (poppedNode.getRight() != null) {
                // Set the height of the left node to
                // 1 unit greater than the popped node
                poppedNode.getRight().setHeight(poppedNode.getHeight() + 1);
                // Set the horizontal distance of the right node to
                // 1 unit more than the horizontal distance of the popped node
                poppedNode.getRight().setHdist(poppedNode.getHdist() + 1);
                bftQueue.add(poppedNode.getRight());
            }
        }
    }

    /*
     * returns the top view of a tree
     */
    public List<Node<T>> getTopView() {
        List<Node<T>> topViewList = new ArrayList<>();
        // As we traverse each level we pick the farthest nodes
        // that are not masked by any of the previous levels
        Map<Integer, Boolean> distMap = new HashMap<>();
        Queue<Node<T>> bftQueue = new LinkedBlockingQueue<>();
        bftQueue.add(this.root);
        topViewList.add(this.root);
        distMap.put(this.root.getHdist(), true);
        while (bftQueue.size() != 0) {
            // pop a node and check if its hdist was already
            // masked by a node at a previous level
            Node<T> poppedNode = bftQueue.poll();
            if (!distMap.containsKey(poppedNode.getHdist())) {
                topViewList.add(poppedNode);
                distMap.put(poppedNode.getHdist(), true);
            }
            if(poppedNode.getLeft() != null) {
                bftQueue.add(poppedNode.getLeft());
            }
            if(poppedNode.getRight() != null) {
                bftQueue.add(poppedNode.getRight());
            }
        }
        return topViewList;
    }

    public int getHeight(Node<T> node, Stack<Node<T>> dftStack, int height) {
        dftStack.push(node);
        if (node.getLeft() == null && node.getRight() == null) {
            int heightTillLeafNode = dftStack.size() - 1;
            if (heightTillLeafNode > height) {
                height = heightTillLeafNode;
            }
            dftStack.pop();
            return height;
        }
        if (node.getLeft() != null) {
            height = this.getHeight(node.getLeft(), dftStack, height);
        }
        if (node.getRight() != null) {
            height = this.getHeight(node.getRight(), dftStack, height);
        }
        dftStack.pop();
        return height;
    }

    public void rotateLeft(Node<T> rotationNode) {
        // Left rotate around rotationNode
        // Check if there is a right child
        if (rotationNode.getRight() != null) {
            // Get a pointer to the right subtree from the rotation node
            Node<T> rotationNodeRightSubTree = rotationNode.getRight();
            // Get a pointer to the left subtree of the right subtree of the rotation node
            Node<T> rotationNodeRightSubtreeLeftSubTree = rotationNode.getRight().getLeft();
            // Set the left subtree of the Right Subtree to the rotation Node
            // effectively making the Right Subtree the new root
            rotationNodeRightSubTree.setLeft(rotationNode);
            if(rotationNodeRightSubtreeLeftSubTree != null) {
                // If the left subtree of the right subtree of the rotation node is not null
                // then set the Right Subtree of the rotationNode to this
                rotationNode.setRight(rotationNodeRightSubtreeLeftSubTree);
            }
        }
    }

    public void rotateRight(Node<T> rotationNode) {
        // Right rotate around rotationNode
        // Check if there is a left child
        if (rotationNode.getLeft() != null) {
            // Get a pointer to the left subtree from the rotation node
            Node<T> rotationNodeLeftSubTree = rotationNode.getLeft();
            // Get a pointer to the rotation node's left subtree's right subtree
            Node<T> rotationNodeLeftSubTreeRightSubTree = rotationNode.getLeft().getRight();
            // Set the
            rotationNodeLeftSubTree.setRight(rotationNode);
            if (rotationNodeLeftSubTreeRightSubTree != null) {
                rotationNode.setLeft(rotationNodeLeftSubTreeRightSubTree);
            }
        }

    }

    public static void main(String[] args) {
        Node<Integer> root = new Node<>();
        root.setData(0);

        Node<Integer> r1 = new Node<>();
        r1.setData(1);
        Node<Integer> r2 = new Node<>();
        r2.setData(2);
        Node<Integer> r3 = new Node<>();
        r3.setData(3);
        Node<Integer> r4 = new Node<>();
        r4.setData(4);
        Node<Integer> r5 = new Node<>();
        r5.setData(5);
        Node<Integer> r6 = new Node<>();
        r6.setData(6);

        root.setLeft(r1); root.setRight(r2);
        r1.setLeft(r3); r1.setRight(r4);
        r2.setLeft(r5); r2.setRight(r6);

        BinTree<Integer> b = new BinTree<>(root);
        // after a call to this each tree node should have
        // its level height and horizontal distance initialized
        b.initializeTreeNodeAttributes();

        System.out.println("Height: " + b.getHeight());

        // Fetch nodes that would appear as top view
        List<Node<Integer>> topViewList = b.getTopView();
        topViewList.stream().forEach(node -> {
            System.out.print(node.toString());
            System.out.print(",");
        });
    }

}

