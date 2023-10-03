package com.sprung.core.utils;

import java.util.*;

class NodePair {

    private Integer node1;
    private Integer node2;
    private Integer shortestDistance;

    public NodePair(Integer n1, Integer n2) {
        this.node1 = n1;
        this.node2 = n2;
    }

    public Integer getNode1() {
        return node1;
    }

    public Integer getNode2() {
        return node2;
    }

    public Integer getShortestDistance() {
        return shortestDistance;
    }

    public void setShortestDistance(Integer shortestDistance) {
        this.shortestDistance = shortestDistance;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.node1 + ":" + this.node2 + ":" + this.shortestDistance);
        return builder.toString();
    }
}

public class TreeExpressionCalculator {

    public List<NodePair> createNodePairList(Integer[] nodeArray) {
        List<NodePair> nodePairList = new ArrayList<>();
        this.createNodePairList(nodeArray, 0, nodePairList);
        return nodePairList;
    }

    public void createNodePairList(Integer[] nodeArray, int index, List<NodePair> nodePairList) {
        if (index >= nodeArray.length - 1) {
            // when we reach the last index create a node pair
            // with a single node and athe other being null
            NodePair nodePair = new NodePair(nodeArray[index], null);
            nodePairList.add(nodePair);
        } else {
            // index = index + 1;
            // recurse into the next index
            createNodePairList(nodeArray, index + 1, nodePairList);
            // index = index - 1;
            // Add the node at the current index in the node pair list
            NodePair nodePair = new NodePair(nodeArray[index], null);
            nodePairList.add(nodePair);
            for (int i = 0; i < nodePairList.size(); i++) {
                // Check the node pairs that have a single node in the Node Pair
                if (nodePairList.get(i).getNode2() == null &&
                        !nodePairList.get(i).getNode1().equals(nodeArray[index])) {
                    // Create a new Node Pair and
                    // pair the current index with this node
                    // to measure complexity count the number of node pair
                    // operations
                    NodePair pairedNodePair = new NodePair(nodeArray[index], nodePairList.get(i).getNode1());
                    nodePairList.add(pairedNodePair);
                }
            }
        }
    }

    /*
     * Undirected graph described as a 2-D array of edges
     * This function converts an undirected graph described as a 2-D array of edges
     * into an adjacency list
     */
    public Map<Integer, List<Integer>> createAdjacencyList(int[][] edgesAsArray) {
        Map<Integer, List<Integer>> adjacencyListAsMap = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < edgesAsArray.length; i++) {
            // each inner array is an edge definition
            if(adjacencyListAsMap.containsKey(edgesAsArray[i][0])) {
                adjacencyListAsMap.get(edgesAsArray[i][0]).add(edgesAsArray[i][1]);
            } else {
                // Allocate an inner array list as we are viewing this node for the
                // first time
                List<Integer> l = new ArrayList<Integer>();
                l.add(edgesAsArray[i][1]);
                adjacencyListAsMap.put(edgesAsArray[i][0], l);
            }
            if(adjacencyListAsMap.containsKey(edgesAsArray[i][1])) {
                adjacencyListAsMap.get(edgesAsArray[i][1]).add(edgesAsArray[i][0]);
            } else {
                // Allocate an inner arrat list as we are viewing this node for the
                // first time
                List<Integer> l = new ArrayList<>();
                l.add(edgesAsArray[i][0]);
                adjacencyListAsMap.put(edgesAsArray[i][1], l);
            }
        }
        return adjacencyListAsMap;
    }

    /*
     * Shortest distance between 2 nodes in an un-directed graph
     * This function finds the shortest distance between 2 nodes in an undirected graph
     * where edges are described using an adjacency list
     */
    public Integer shortestDistanceBetweenNodes(Integer node1,
                                             Integer node2,
                                             Map<Integer, List<Integer>> adjacencyListMap) {
        Stack<Integer> dftStack = new Stack<Integer>();
        Map<String, Integer> shortestDistanceMap = new HashMap<>();
        shortestDistanceMap.put("s", 0);
        this.shortestDistanceBetweenNodes(node1,
                node2, adjacencyListMap, dftStack, shortestDistanceMap);
        return shortestDistanceMap.get("s");
    }

    public void shortestDistanceBetweenNodes(Integer visitingNode,
                                             Integer node2,
                                             Map<Integer, List<Integer>> adjacencyListMap,
                                             Stack<Integer> dftStack,
                                             Map<String, Integer> shortestDistanceMap) {
        if (visitingNode.equals(node2)) {
            // push the visitingNode onto the stack
            dftStack.push(visitingNode);
            // We have reached the node of interest
            // Check the size of the dftStack
            int  stackSize = dftStack.size();
            Integer currentShortestDistance = shortestDistanceMap.get("s");
            if (currentShortestDistance == 0) {
                currentShortestDistance = stackSize;
            } else {
                if (stackSize < currentShortestDistance) {
                    currentShortestDistance = stackSize;
                }
            }
            shortestDistanceMap.put("s", currentShortestDistance);
            // System.out.println("Current Shortest Distance Calculated: " + shortestDistanceMap.get("s"));
            // pop the top of the stack
            dftStack.pop();
        } else {
            dftStack.push(visitingNode);
            // recurse over all the child nodes of this visiting node
            List<Integer> childNodeList = adjacencyListMap.get(visitingNode);
            if (childNodeList != null && childNodeList.size() > 0) {
                for (int i = 0; i < childNodeList.size(); i++) {
                    // If the child node of the visiting node is not in the stack
                    if (dftStack.search(childNodeList.get(i)) == -1) {
                        // Start a DFT for that child node
                        this.shortestDistanceBetweenNodes(childNodeList.get(i),
                                node2, adjacencyListMap,
                                dftStack, shortestDistanceMap);
                    }
                }
                dftStack.pop();
            }
        }
    }

    public static void main(String[] args) {

        TreeExpressionCalculator t = new TreeExpressionCalculator();

        Map<Integer, List<Integer>> adjacencyListMap = t.createAdjacencyList(new int[][]{
                {1,2}, {1,3}, {1,4}, {3,5}, {3,6}, {3,7}, {6,5}
        });

        List<NodePair> nodePairs = t.createNodePairList(new Integer[]{1, 2, 3, 4});
        nodePairs.stream().filter(element -> {
            return element.getNode2() != null;
        }).forEach(nodepair -> {
            nodepair.setShortestDistance(t.shortestDistanceBetweenNodes(
                    nodepair.getNode1(),
                    nodepair.getNode2(),
                    adjacencyListMap) - 1);
            //System.out.println(nodepair.toString());
        });
        nodePairs.stream().forEach(nodePair -> {
            System.out.println(nodePair.toString());
        });

        int sum = 0;
        for(int i = 0; i < nodePairs.size(); i++) {
            if(nodePairs.get(i).getNode1() != null && nodePairs.get(i).getNode2() != null) {
                sum += nodePairs.get(i).getNode1() *
                        nodePairs.get(i).getNode2() *
                        nodePairs.get(i).getShortestDistance();
            }
        }
        System.out.println("Sum: " + sum);
    }
}