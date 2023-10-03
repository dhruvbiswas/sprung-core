package com.sprung.core.utils;

import java.util.*;

class ChildNodeRowList {

    private Integer row;
    private List<String> childNodeList;

    public ChildNodeRowList(Integer row, List<String> childNodeList) {
        this.row = row;
        this.childNodeList = childNodeList;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public List<String> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<String> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(row + ":");
        this.childNodeList.stream().forEach(element -> {
            builder.append(element);
        });
        builder.append("\n");
        return builder.toString();
    }
}

public class GraphAsAdjacencyMatrix {

    private Map<String, Integer> nodeToIndexMap;
    private Map<Integer, String> indexToNodeMap;
    private Integer[][] adjacencyMatrix;

    private GraphAsAdjacencyMatrix(Map<String, Integer> nodeToIndexMap, Integer[][] adjacencyMatrix) {
        this.nodeToIndexMap = nodeToIndexMap;
        this.adjacencyMatrix = adjacencyMatrix;

        this.nodeToIndexMap.forEach((k, v) -> {
            this.indexToNodeMap.put(v, k);
        });
    }

    private ChildNodeRowList getChildNodes(String node) {
        List<String> childNodeList = new ArrayList<>();
        Integer row = -1;
        if(this.nodeToIndexMap.containsKey(node)) {
            row = this.nodeToIndexMap.get(node);
            for(int j = 0; j < this.adjacencyMatrix[row].length; j++) {
                if(this.adjacencyMatrix[row][j] == 1) {
                    // Find the node name for that column index
                    // using index to node map
                    childNodeList.add(this.indexToNodeMap.get(j));
                }
            }
        }
        return new ChildNodeRowList(row, childNodeList);
    }

    /*
     * list all paths from a given node
     */
    public List<List<String>> listAllPaths(String node) {
        List<List<String>> pathList = new ArrayList<>();
        Stack<String> dftStack = new Stack<>();
        this.listAllPathsDFT(node, dftStack, pathList);
        return pathList;
    }

    private void listAllPathsDFT(String node, Stack<String> dftStack, List<List<String>> pathList) {
        // First insert the node into the dft stack
        dftStack.push(node);
        // Fetch child nodes for this node
        ChildNodeRowList childNodeRowList = this.getChildNodes(node);
        if (childNodeRowList.getChildNodeList().size() == 0) {
            // We have hit a leaf node
            List<String> leafNodePathList = new ArrayList<>();
            // pop elements from the stack, build a list
            while (!dftStack.empty()) {
                leafNodePathList.add(dftStack.pop());
            }
            // insert into the pathList
            pathList.add(leafNodePathList);
            // push elements back into the stack
            for (int i = leafNodePathList.size() - 1; i <= 0; i--) {
                dftStack.push(leafNodePathList.get(i));
            }
            // pop the top of stack as thats the leaf node
            dftStack.pop();
        } else {
            // iterate over each child node and recurse
            for (int i = 0; i < childNodeRowList.getChildNodeList().size(); i++) {
                listAllPathsDFT(childNodeRowList.getChildNodeList().get(i), dftStack, pathList);
            }
            dftStack.pop();
        }
    }

    public static void main(String[] args) {
        // Directed graph described as an adjacency matrix
        GraphAsAdjacencyMatrix graphAsAdjacencyMatrix = new GraphAsAdjacencyMatrix(new HashMap<String, Integer>() {{
            put("v1", 0);
            put("v2", 1);
            put("v3", 2);
            put("v4", 3);
            put("v5", 4);
        }}, new Integer[][] {
                {0, 1, 0, 1, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0}
        });
        List<List<String>> paths = graphAsAdjacencyMatrix.listAllPaths("v2");
    }

}
