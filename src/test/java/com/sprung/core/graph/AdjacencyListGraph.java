package com.sprung.core.graph;

import java.lang.reflect.Array;
import java.util.*;

class MinHeap<T extends Comparable> {

    private Edge<T>[] heap = null;
    private Integer lastInsertedIndex = 0;

    public void insert(Edge<T> edge) {
        this.expandArray(1);
        // Always insert into the min heap at last inserted index
        this.heap[this.lastInsertedIndex] = edge;
        this.heapify(this.heap.length - 1);
    }

    public void heapify(int index) {
        // Compute parent index from this index
        int parentIndex = (index - 1)/ 2;
        if (parentIndex >= 0) {
            if (this.heap[parentIndex].getWeight() > this.heap[index].getWeight()) {
                // compare and replace
                Edge<T> tmp = this.heap[index];
                this.heap[index] = this.heap[parentIndex];
                this.heap[parentIndex] = tmp;
                index = parentIndex;
                this.heapify(index);
            }
        }
    }

    public Edge<T> remove() {
        Edge<T> element = this.heap[0];
        Edge<T> tmp = this.heap[0];
        this.heap[0] = this.heap[this.lastInsertedIndex];
        this.heap[lastInsertedIndex] = tmp;
        this.reAdjustHeap(0);
        this.heap[lastInsertedIndex] = null;
        // Decrease last inserted index
        this.lastInsertedIndex = this.lastInsertedIndex - 1;
        return element;
    }

    public void reAdjustHeap(int index) {
        if (index < this.lastInsertedIndex - 1) {
            // Check if the index is greater than the left or right child
            Integer leftChildIndex = index * 2 + 1;
            if (this.heap[index].getWeight() > this.heap[leftChildIndex].getWeight()) {
                // replace with left child
                Edge<T> tmp = this.heap[index];
                this.heap[index] = this.heap[leftChildIndex];
                this.heap[leftChildIndex] = tmp;
                // Recurse into left child
                this.reAdjustHeap(leftChildIndex);
            } else {
                Integer rightChildIndex = index * 2 + 2;
                if (this.heap[index].getWeight() > this.heap[rightChildIndex].getWeight()) {
                    // replace with right child
                    Edge<T> tmp = this.heap[index];
                    this.heap[index] = this.heap[rightChildIndex];
                    this.heap[rightChildIndex] = tmp;
                    // Recurse into right child
                    this.reAdjustHeap(rightChildIndex);
                }
            }
        }
    }

    public void expandArray(int capacity) {
        if (this.heap == null) {
            this.lastInsertedIndex = 0;
            this.heap = (Edge<T>[]) Array.newInstance(Edge.class, capacity);
            for (int i = 0; i < capacity; i++) {
                this.heap[i] = null;
            }
        } else {
            Edge<T>[] newArray = (Edge<T>[]) Array.newInstance(Edge.class,
                    this.heap.length + capacity);
            for (int i = 0; i < this.heap.length; i++) {
                newArray[i] = this.heap[i];
            }
            for (int j = this.heap.length; j < newArray.length; j++) {
                newArray[j] = null;
            }
            this.heap = newArray;
            this.lastInsertedIndex += capacity;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (this.heap != null) {
            for (int i = 0; i < this.heap.length; i++) {
                builder.append(this.heap[i].getSrc().getData().toString() + ":" +
                        this.heap[i].getDest().getData().toString() + ":" +
                        this.heap[i].getWeight());
                builder.append("\n");
            }
            builder.append("LastInsertedIndex: " + this.lastInsertedIndex);
        }
        return builder.toString();
    }
}

class Node<T> implements Comparable<T> {

    private T data;

    public Node(T t) {
        this.data = t;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int compareTo(Object o) {
        T paramObj = (T) o;
        return this.compareTo(paramObj);
    }

}

class Edge<T> implements Comparable {

    private Node<T> src = null;
    private Node<T> dest = null;
    private Integer weight = 0;

    public Edge(Node<T> src, Node<T> dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public Node<T> getSrc() {
        return src;
    }

    public void setSrc(Node<T> src) {
        this.src = src;
    }

    public Node<T> getDest() {
        return dest;
    }

    public void setDest(Node<T> dest) {
        this.dest = dest;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public int compareTo(Object o) {
        Edge<T> e = (Edge<T>) o;
        if(this.weight > e.weight) {
            return 1;
        } else if(this.weight < e.weight) {
            return -1;
        } else {
            return 0;
        }
    }
}

public class AdjacencyListGraph<T> {

    private Map<Node<T>, List<Edge<T>>> adjacencyListMap = new HashMap<>();

    public Map<Node<T>, List<Edge<T>>> getAdjacencyListMap() {
        return adjacencyListMap;
    }

    public void setAdjacencyListMap(Map<Node<T>, List<Edge<T>>> adjacencyListMap) {
        this.adjacencyListMap = adjacencyListMap;
    }

    public static void main(String[] args) {
        Map<Node<Integer>, List<Edge<Integer>>> adjacencyListMap = new HashMap<>();
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);
        Node<Integer> node5 = new Node<>(5);
        Node<Integer> node6 = new Node<>(6);

        Edge edge_1_2 = new Edge(node1, node2, 2);
        Edge edge_1_3 = new Edge(node1, node3, 3);
        adjacencyListMap.put(node1, Arrays.asList(edge_1_2, edge_1_3));

        Edge edge_2_4 = new Edge(node2, node4, 4);
        Edge edge_2_5 = new Edge(node2, node5, 5);
        adjacencyListMap.put(node2, Arrays.asList(edge_2_4, edge_2_5));

        Edge edge_3_5 = new Edge(node3, node5, 1);
        Edge edge_3_4 = new Edge(node3, node4, 3);
        adjacencyListMap.put(node3, Arrays.asList(edge_3_5, edge_3_4));

        Edge edge_5_4 = new Edge(node5, node4, 1);
        Edge edge_5_6 = new Edge(node5, node6, 2);
        adjacencyListMap.put(node5, Arrays.asList(edge_5_4, edge_5_6));

        MinHeap<Integer> minHeap = new MinHeap<>();
        minHeap.insert(edge_2_5);
        minHeap.insert(edge_2_4);
        minHeap.insert(edge_1_3);
        minHeap.insert(edge_1_2);
        minHeap.insert(edge_5_4);
        System.out.println(minHeap);
    }

}
