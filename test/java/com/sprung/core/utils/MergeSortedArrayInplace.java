package com.sprung.core.utils;

import org.junit.Test;

public class MergeSortedArrayInplace {

    @Test
    public void test() {
        int[] arr = new int[]{1,3,5,7,2,4,6,8};
        //int[] arr = new int[]{101,102,103,104,2,4,6,8};
        //int[] arr = new int[]{1,2,103,104,5,6,7,8};
        //int[] arr = new int[]{1,2,3,4,5,6,7,8};
        MergeSortedArrayInplace.merge_in_place(arr, 0, 4);
        //int[] merged = MergeSortedArrayInplace.merge(new int[]{1,3,5}, new int[]{2,4,6,8});
        MergeSortedArrayInplace.display(arr);
    }

    public static void display(int[] arr) {
        System.out.print("Merged: ");
        for(int l = 0; l < arr.length; l++) {
            System.out.print(arr[l] + ",");
        }
        System.out.println();
    }

    /*
     * sub-array start1 to (start2 - 1) is sorted in ascending order
     * sub-array start2 to (arr.length - 1) is sorted in ascending order
     */
    public static void merge_in_place(int[] arr, int start1, int start2) {
        int i = start1;
        int j = start2;
        while(i < arr.length - 1 && j < arr.length - 1) {
            if(i == j) {
                j++;
            }
            if(arr[i] == arr[j]) {
                i++; j++;
            } else if(arr[i] < arr[j]) {
                i++;
            } else {
                // swap elements at index i and j
                swap(arr, i, j);
                // from j to end2 check if the sort order got disrupted
                // If the sort order is disrupted then re-allign elements
                int counter = j;
                while (counter < arr.length - 1 && arr[counter] > arr[counter + 1]) {
                    swap(arr, counter, counter + 1);
                    counter = counter + 1;
                }
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        if(i >= 0 && i < arr.length && j >= 0 && j < arr.length) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    /*
     * all elements in arr1 are sorted in ascending order
     * all elements in arr2 are sorted in ascending order
     */
    public static int[] merge(int[] arr1, int arr2[]) {
        int[] merged = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while(i < arr1.length && j < arr2.length) {
            if(arr1[i] == arr2[j]) {
                merged[k++] = arr1[i++];
                merged[k++] = arr2[j++];
            } else if(arr1[i] < arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        while(i < arr1.length) {
            merged[k++] = arr1[i++];
        }
        while(j < arr2.length) {
            merged[k++] = arr2[j++];
        }

        return merged;
    }

    public static int[] mergesort(int[] arr) {
        if(arr.length == 1) {
            return arr;
        } else {
            int mid = arr.length / 2;
            int[] left_arr = new int[mid];
            int[] right_arr = new int[arr.length - mid];
            for(int i = 0; i < mid; i++) {
                left_arr[i] = arr[i];
            }
            for(int j = mid, i = 0; j < arr.length; i++, j++) {
                right_arr[i] = arr[j];
            }
            return merge(mergesort(left_arr), mergesort(right_arr));
        }
    }

}
