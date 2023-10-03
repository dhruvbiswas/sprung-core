package com.sprung.core.utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ArrayUtils {

    // Inplace
    public int partition_array(int[] arr) {
        int start_pointer = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < 0) {
                // swap i position in the array with start_pointer position
                int temp = arr[i];
                arr[i] = arr[start_pointer];
                arr[start_pointer] = temp;
                start_pointer++;
            }
        }
        return start_pointer;
    }

    public static List<Integer> reverseList(List<Integer> a) {
        List<Integer> l = new ArrayList<>();
        if (a != null && a.size() > 0) {
            int end = a.size() - 1;
            while(end >= 0) {
                l.add(a.get(end));
                end--;
            }
        }
        return l;
    }

    // non-recursive solution
    // take 2 pointers start and end pointer
    // swap elements across start and end pointer
    // O(n)
    public static void reverseArray(int[] arr) {
        if (arr != null && arr.length > 0) {
            int start = 0;
            int end = arr.length - 1;
            while (start < end) {
                int temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
                start++; end--;
            }
        }
    }

    public static void recursiveReverseArray(int[] arr, int start, int end) {
        // In every recursive call, push the start element down the
        // array to the end and push every other element up the array
        if (start >= end) {
            return;
        } else {
            ArrayUtils.recursiveReverseArray(arr, start + 1, end);
            int i = start;
            while (i <= end - 1) {
                // swap the element at i with the element at i + 1
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
                i++;
            }
        }
    }

    public static int[] maxHourGlassSum(int[][] arr) {
        // Heaparr would hold the max hour glass sum
        int[] heapArr = new int[]{0};
        int max = 0;
        if (arr != null && arr.length == 6 && arr[0].length == 6) {
            for (int i = 0; i < 4; i++) {
                // Take a set of 3 rows and then print hour glass
                for (int j = 0; j < 4; j++) {
                    // take a 3x3 grid starting at i and j and compute grid sum
                    int gridSum = 0;
                    for (int k = i; k < i + 3; k++) {
                        for (int l = j; l < j + 3; l++) {
                            if ((k == i + 1 && l == j) || (k == i + 1 && l == j + 2)) {
                                continue;
                            } else {
                                gridSum += arr[k][l];
                            }
                        }
                    }
                    System.out.println("gridsum: " + gridSum);
                    // Whenever we find a gridsum we add to the max heap
                    // In the end the 0th element is the max hourglass sum
                    heapArr = ArrayUtils.addToHeap(heapArr, 1);
                }
            }
        }
        return heapArr;
    }

    public static int[] addToHeap(int[] arr, int element) {
        // Increment arr by 1
        int[] new_arr = ArrayUtils.incrementArray(arr, 1);
        // Insert the element as the last element in the array
        new_arr[new_arr.length - 1] = element;
        ArrayUtils.heapify(new_arr, new_arr.length - 1);
        return new_arr;
    }

    public static void heapify(int[] arr, int index) {
        if (index == 0) {
            return;
        } else {
            // find root element and check if we need to swap the element
            int parent = index / 2;
            if (arr[parent] < arr[index]) {
                // swap parent with index
                int temp = arr[parent];
                arr[parent] = arr[index];
                arr[index] = temp;
            }
            // Recusrsively call heapify till we reach root
            // to balance max element
            ArrayUtils.heapify(arr, parent);
        }
    }

    public static int[] incrementArray(int[] arr, int length) {
        int[] new_arr = null;
        if (arr != null && arr.length > 0 && length > 0) {
            // Allocate a new array of size arr.length + length
            new_arr = new int[arr.length + length];
            // Initialize the newly allocated array to have elements
            // initialized to 0
            for (int i = 0; i < new_arr.length; i++) {
                new_arr[i] = 0;
            }
            // copy elements from array arr to new_arr
            for (int i = 0; i < arr.length; i++) {
                new_arr[i] = arr[i];
            }
        }
        return new_arr;
    }

    public static List<Integer> bftList(Integer[] treeAsArray) {
        Queue<Integer> bftQueue = new LinkedBlockingQueue<>();
        int index = 0;
        bftQueue.add(treeAsArray[index]);
        while (!bftQueue.isEmpty()) {
        }
        return null;
    }

    public static List<List<Integer>> createPairs(List<Integer> arr, int index) {
        if(index == arr.size() - 1) {
            List<List<Integer>> pairs = new ArrayList<>();
            List<Integer> innerList = new ArrayList<>();
            innerList.add(arr.get(index));
            pairs.add(innerList);
            return pairs;
        } else {
            // Recurse into the array with the next higher index
            List<List<Integer>> pairs = createPairs(arr,index + 1);
            // Create pairs using only single element inner lists
            // and add into the pairs list of lists
            List<List<Integer>> newPairs = new ArrayList<>();

            for (int i = 0; i < pairs.size(); i++) {
                List<Integer> innerList = pairs.get(i);
                if (innerList.size() == 1) {
                    //Pair it with the current element
                    List<Integer> newPairList = new ArrayList<>();
                    newPairList.add(arr.get(index));
                    newPairList.add(innerList.get(0));
                    newPairs.add(newPairList);
                }
            }
            // Add everything that is in pairs into newpairs
            for(int i = 0; i < pairs.size(); i++) {
                newPairs.add(pairs.get(i));
            }
            // Add the current element into pairs list
            List<Integer> currentElementList = new ArrayList<>();
            currentElementList.add(arr.get(index));
            newPairs.add(currentElementList);
            return newPairs;
        }
    }

    public static void main(String[] args) {

        int[][] arr = new int[][]{
                {-9, -9, -9, 1, 1, 1},
                {0, -9, 0, 4, 3, 2},
                {-9, -9, -9, 1, 2, 3},
                {0, 0, 8, 6, 6, 0},
                {0, 0, 0, -2, 0, 0},
                {0, 0, 1, 2, 4, 0}
        };
        ArrayUtils.maxHourGlassSum(arr);

        List<Integer> numbers = new ArrayList<>();
        numbers.add(4);
        numbers.add(2);
        numbers.add(6);
        numbers.add(1);
        numbers.add(3);
        List<List<Integer>> pairs = ArrayUtils.createPairs(numbers, 0);
        for(int i = 0; i < pairs.size(); i++) {
            List<Integer> pair = pairs.get(i);
            for(int j = 0; j < pair.size(); j++) {
                System.out.print(pair.get(j) + ",");
            }
            System.out.println();
        }

    }

}
