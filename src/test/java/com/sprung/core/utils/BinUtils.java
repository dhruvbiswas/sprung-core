package com.sprung.core.utils;

public class BinUtils {

    public static String intToBinary(Long num) {
        StringBuilder s = new StringBuilder();
        Long rolling = num;
        while(true) {
            if(rolling == 1) {
                break;
            }
            Long rem = rolling % 2;
            s.append(Long.toString(rem));
            rolling = rolling / 2;
        }
        s.append(Long.toString(rolling));
        String reversed = s.toString();
        System.out.println("Reversed: " + reversed);
        StringBuilder reversedBuilder = new StringBuilder();
        //Find how many 0s are needed to be padded
        int nearestMultipleOf4 = (reversed.length() / 4 + 1) * 4;
        int numberOfDigitsToPad = nearestMultipleOf4 - reversed.length();
        StringBuilder paddingStringBuilder = new StringBuilder();
        for(int i = 0; i < numberOfDigitsToPad; i++) {
            paddingStringBuilder.append("0");
        }
        for(int i = reversed.length() - 1; i >= 0; i--) {
            reversedBuilder.append(reversed.charAt(i));
        }
        return paddingStringBuilder.toString() + reversedBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(BinUtils.intToBinary(2246720732033l));
    }

}
