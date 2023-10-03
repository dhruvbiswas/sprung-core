package com.sprung.core.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class BinaryTree<N extends Comparable> {

    private TreeNode<N> root = null;

    public BinaryTree(N data) {
        // Create a new Root node for this tree
        this.root = new TreeNode<N>(data);
    }

    public BinaryTree(TreeNode<N> root) {
        this.root = root;
    }

    public TreeNode<N> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<N> root) {
        this.root = root;
    }

    public void addNode(N data) {
        addNode(this.root, data);
    }

    private void addNode(TreeNode<N> node, N data) {
        if (data.compareTo(node.getData()) > 0) {
            if ( node.getRight() != null) {
                this.addNode(node.getRight(), data);
            } else {
                TreeNode<N> newNode = new TreeNode<>(data);
                // Add as right node to the current node
                node.setRight(newNode);
                // Calculate balance factors
            }
        }
    }

    public void bft() {
        Queue<TreeNode<N>> queue = new LinkedBlockingQueue<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            TreeNode<N> popped = queue.poll();
            System.out.println(popped.toString());
            if (popped.getLeft() != null) {
                queue.add(popped.getLeft());
            }
            if (popped.getRight() != null) {
                queue.add(popped.getRight());
            }
        }
    }

    public List<N> bft(TreeNode<N> root) {
        List<N> rootBftList = new ArrayList<>();
        Queue<TreeNode<N>> rootBftQueue = new LinkedBlockingQueue<>();
        rootBftQueue.add(root);

        while(!rootBftQueue.isEmpty()) {
            TreeNode<N> popped = rootBftQueue.poll();
            rootBftList.add(popped.getData());
            if(popped.getLeft() != null) {
                rootBftQueue.add(popped.getLeft());
            }
            if(popped.getRight() != null) {
                rootBftQueue.add(popped.getRight());
            }
        }
        return rootBftList;
    }

    /*
     * Start from the current node
     * recursively compute balance factors
     */
    public void computeBalanceFactors(TreeNode<N> node) {
        Stack<TreeNode<N>> dftStack = new Stack<>();
        this.computeBalanceFactors(node, dftStack);
    }

    private int computeBalanceFactors(TreeNode<N> node, Stack<TreeNode<N>> dftStack) {
        dftStack.push(node);
        if (node.getLeft() == null && node.getRight() == null) {
            // We have hit a leaf node
            node.setBfactor(0);
            dftStack.pop();
            return 0;
        } else {
            int leftSubTreeHeight = 0;
            int rightSubTreeHeight = 0;
            if (node.getLeft() != null) {
                leftSubTreeHeight = this.computeBalanceFactors(node.getLeft(), dftStack) + 1;
            }
            if (node.getRight() != null) {
                rightSubTreeHeight = this.computeBalanceFactors(node.getRight(), dftStack) + 1;
            }
            node.setBfactor(rightSubTreeHeight - leftSubTreeHeight);
            dftStack.pop();
            return node.getBfactor();
        }
    }

    /*
     * Check if a binary tree is balanced or not
     * A binary tree is balanced if all of its left
     * and right sub-trees have a height difference of at most 1
     */
    public boolean isBalanced() {
        Stack<TreeNode<N>> dftStack = new Stack <TreeNode<N>>();
        return isBalanced(this.root, dftStack);
    }

    private boolean isBalanced(TreeNode<N> node, Stack<TreeNode<N>> dftStack) {
        dftStack.push(node);
        if (node.getLeft() == null && node.getRight() == null) {
            // We have reached a root node
            dftStack.pop();
            return true;
        } else {
            boolean isLeftSubtreeBalanced = false;
            boolean isRightSubtreeBalanced = false;
            if (node.getLeft() != null) {
                isLeftSubtreeBalanced = isBalanced(node.getLeft(), dftStack);
            }
            if (node.getRight() != null) {
                isRightSubtreeBalanced = isBalanced(node.getRight(), dftStack);
            }

            if (isLeftSubtreeBalanced && isRightSubtreeBalanced) {
                // Set the current node left subtree height
                node.setLeftSubtreeHeight(node.getLeft().getLeftSubtreeHeight() + 1);
                // Set the current node right subtree height
                node.setRightSubtreeHeight(node.getRight().getRightSubtreeHeight() + 1);
                // Check the diff of the left and right sub-tree heights
                int heightDiff = Math.abs(node.getLeftSubtreeHeight() - node.getRightSubtreeHeight());
                dftStack.pop();
                if (heightDiff == 0 || heightDiff == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                dftStack.pop();
                // return false If either the left subtree or the right subtree is unbalanced
                return false;
            }
        }
    }

    public List<List<TreeNode<N>>> rootToLeafPath(N data) {
        Stack<TreeNode<N>> dftStack = new Stack<>();
        List<List<TreeNode<N>>> treeNodeList = new ArrayList<>();
        this.rootToLeafPath(this.root, dftStack, treeNodeList, data);
        return treeNodeList;
    }

    public void rootToLeafPath(TreeNode<N> node,
                               Stack<TreeNode<N>> dftStack,
                               List<List<TreeNode<N>>> treeNodeList,
                               N data) {
        dftStack.push(node);
        if (node.getLeft() == null && node.getRight() == null) {
            List<TreeNode<N>> t = new ArrayList<>();
            // This ensures that we traverse the stack
            // without having to pop and push elements
            dftStack.forEach(e -> {
                t.add(e);
            });
            // Check if the list contains the data
            for (int i = 0; i < t.size(); i++) {
                // Add the current list into the
                // potential root-to-leaf-node paths
                if (t.get(i).getData().equals(data)) {
                    treeNodeList.add(t);
                    break;
                }
            }
            dftStack.pop();
        } else {
            if (node.getLeft() != null) {
                // recurse into the left sub-tree
                this.rootToLeafPath(node.getLeft(), dftStack, treeNodeList, data);
            }
            if (node.getRight() != null) {
                // recurse into the right sub-tree
                this.rootToLeafPath(node.getRight(), dftStack, treeNodeList, data);
            }
            dftStack.pop();
        }
    }

    public TreeNode<N> lowestCommonAncestor(N node1Data, N node2Data) {
        List<List<TreeNode<N>>> node1DataRootToLeafNodeListOfLists = this.rootToLeafPath(node1Data);
        List<List<TreeNode<N>>> node2DataRootToLeafNodeListOfLists = this.rootToLeafPath(node2Data);

        boolean lcaFound = false;
        int index = 0;
        if (node1DataRootToLeafNodeListOfLists.get(0) != null && node2DataRootToLeafNodeListOfLists.get(0) != null) {
            while (!lcaFound) {
                if (node1DataRootToLeafNodeListOfLists.get(0).get(index) ==
                        node2DataRootToLeafNodeListOfLists.get(0).get(index)) {
                    index = index + 1;
                } else {
                    lcaFound = true;
                }
            }
            return node1DataRootToLeafNodeListOfLists.get(0).get(index - 1);
        } else {
            return null;
        }
    }

    /*
     *
     */
    public boolean isSameTree(TreeNode<N> root1, TreeNode<N> root2) {
        boolean isSame = false;
        if(root1 != null && root2 != null) {
            isSame = true;
            List<N> root1BftList = this.bft(root1);
            List<N> root2BftList = this.bft(root2);
            if(root1BftList != null && root2BftList != null && root1BftList.size() != root2BftList.size()) {
                int i = 0, j = 0;
                while(i < root1BftList.size() && j < root2BftList.size()) {
                    if(root1BftList.get(i).compareTo(root2BftList.get(j)) != 0) {
                        isSame = false;
                        break;
                    }
                }
                return isSame;
            }
        } else {
            isSame = false;
        }
        return isSame;
    }

    public List<N> serialize(TreeNode<N> root) {
        return this.bft(root);
    }

    public TreeNode<N> deserialize(List<N> list) {
        // Lets do a dft
        Stack<N> serializedTreeAsListDftStack = new Stack<>();
        Stack<TreeNode<N>> linkedTreeDftStack = new Stack<>();
        TreeNode<N> root = null;
        dft(list, 0, root, serializedTreeAsListDftStack, linkedTreeDftStack);
        return null;
    }

    public void dft(List<N> list, int currentIndex,
                    TreeNode<N> root,
                    Stack<N> serializedListStack,
                    Stack<TreeNode<N>> linkedTreeDftStack) {
        serializedListStack.push(list.get(currentIndex));
        if(currentIndex == 0) {
            root = new TreeNode<N>(list.get(currentIndex));
            linkedTreeDftStack.push(root);
        }
        // Compute the left and right child indexes
        int leftChildIndex = currentIndex * 2 + 1;
        int rightChildIndex = currentIndex * 2 + 2;
        if((leftChildIndex > list.size() - 1) && (rightChildIndex > list.size() - 1)) {
            // we have reached a leaf node
            serializedListStack.pop();
            linkedTreeDftStack.pop();
        } else {
            // If there is a left child
            if(leftChildIndex > list.size() - 1) {
                // Peek into the top of the stack of linkedTreeDftStack
                // add a left child to the node that is at the top of the stack
                // push the left child
                TreeNode<N> leftChild = new TreeNode<>(list.get(leftChildIndex));
                linkedTreeDftStack.peek().setLeft(leftChild);
                linkedTreeDftStack.push(leftChild);
                dft(list, leftChildIndex, root, serializedListStack, linkedTreeDftStack);
            }
            if(rightChildIndex > list.size() - 1) {
                // Peek into the top of the stack of linkedTreeDftStack
                // add a left child to the node that is at the top of the stack
                // push the right child into linkedTreeDftStack
                TreeNode<N> rightChild = new TreeNode<>(list.get(rightChildIndex));
                linkedTreeDftStack.peek().setRight(rightChild);
                linkedTreeDftStack.push(rightChild);
                dft(list, rightChildIndex, root, serializedListStack, linkedTreeDftStack);
            }
            serializedListStack.pop();
            linkedTreeDftStack.pop();
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree = new BinaryTree<>(5);
        TreeNode<Integer> n_6 = binaryTree.getRoot().addLeftChild(6);
        TreeNode<Integer> n_3 = n_6.addLeftChild(3);
        TreeNode<Integer> n_4 = n_6.addRightChild(4);
        TreeNode<Integer> n_10 = n_4.addLeftChild(10);
        TreeNode<Integer> n_11 = n_4.addRightChild(11);

        TreeNode<Integer> n_7 = binaryTree.getRoot().addRightChild(7);
        TreeNode<Integer> n_8 = n_7.addLeftChild(8);
        TreeNode<Integer> n_9 = n_7.addRightChild(9);
        TreeNode<Integer> n_12 = n_8.addLeftChild(12);
        TreeNode<Integer> n_13 = n_8.addRightChild(13);
        TreeNode<Integer> n_14 = n_13.addRightChild(14);
        TreeNode<Integer> n_15 = n_14.addRightChild(15);

        boolean isBalanced = binaryTree.isBalanced();
        System.out.println("IsBalanced: " + isBalanced);

        binaryTree.computeBalanceFactors(binaryTree.getRoot());
        binaryTree.bft();

        /* List<List<TreeNode<Integer>>> pathsTo_n_10 = binaryTree.rootToLeafPath(10);
        List<List<TreeNode<Integer>>> pathsTo_n_11 = binaryTree.rootToLeafPath(11);

        System.out.print("Root to Leaf path for n_10: ");
        pathsTo_n_10.get(0).stream().forEach(element -> {
            System.out.print(element.getData().toString() + "|");
        });
        System.out.println();

        System.out.print("Root to Leaf path for n_11: ");
        pathsTo_n_11.get(0).stream().forEach(element -> {
            System.out.print(element.getData().toString() + "|");
        });
        System.out.println(); */

        TreeNode<Integer> lcaTreeNode = binaryTree.lowestCommonAncestor(n_12.getData(), n_15.getData());
        System.out.println("Lowest Common Ancestor: " + lcaTreeNode.getData().toString());

    }

}
