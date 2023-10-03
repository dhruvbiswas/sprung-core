package com.sprung.core.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MoneroUtils {

    public static final int RING_SIZE = 12; // network-enforced ring size

    private static int LOG_LEVEL = 0;
    private static long AU_PER_XMR = 1000000000000l;
    private static final int NUM_MNEMONIC_WORDS = 25;
    private static final int VIEW_KEY_LENGTH = 64;
    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final List<Character> CHARS = new ArrayList<Character>();
    static {
        for (char c : ALPHABET.toCharArray()) {
            CHARS.add(c);
        }
    }
    private final static BigDecimal ALPHABET_SIZE = new BigDecimal(ALPHABET.length());
    private final static Map<Integer, Integer> ENCODED_BLOCK_SIZE = new HashMap<Integer, Integer>();
    static {
        ENCODED_BLOCK_SIZE.put(0, 0);
        ENCODED_BLOCK_SIZE.put(2, 1);
        ENCODED_BLOCK_SIZE.put(3, 2);
        ENCODED_BLOCK_SIZE.put(5, 3);
        ENCODED_BLOCK_SIZE.put(6, 4);
        ENCODED_BLOCK_SIZE.put(7, 5);
        ENCODED_BLOCK_SIZE.put(9, 6);
        ENCODED_BLOCK_SIZE.put(10, 7);
        ENCODED_BLOCK_SIZE.put(11, 8);
    };
    private final static int FULL_BLOCK_SIZE = 8;
    private final static int FULL_ENCODED_BLOCK_SIZE = 11;
    private final static BigDecimal UINT64_MAX = new BigDecimal(Math.pow(2, 64));
    private final static Pattern STANDARD_ADDRESS_PATTERN = Pattern.compile("^[" + ALPHABET + "]{95}$");
    private final static Pattern INTEGRATED_ADDRESS_PATTERN = Pattern.compile("^[" + ALPHABET + "]{106}$");

    private static final int[] EMPTY_INT_ARRAY = new int[0];

    public static String decodeAddress(String address) throws Exception {
        boolean isIntegrated = false;
        if (!STANDARD_ADDRESS_PATTERN.matcher(address).matches()) {
            if (INTEGRATED_ADDRESS_PATTERN.matcher(address).matches()) {
                isIntegrated = true;
            } else {
                throw new Exception("Address has invalid regex pattern");
            }
        }
        // decode address to hex string
        return decodeAddressToHex(address);
    }

    private static String decodeAddressToHex(String address) {
        int[] bin = new int[address.length()];
        for (int i = 0; i < address.length(); i++) {
            bin[i] = address.codePointAt(i);
        }
        System.out.println("Printing ords");
        for(int i = 0; i < bin.length; i++) {
            System.out.println(bin[i]);
        }
        System.out.println("");
        int fullBlockCount = (int) Math.floor(bin.length / FULL_ENCODED_BLOCK_SIZE);
        int lastBlockSize = bin.length % FULL_ENCODED_BLOCK_SIZE;
        int lastBlockDecodedSize = ENCODED_BLOCK_SIZE.get(lastBlockSize);
        if (lastBlockDecodedSize < 0) {
            throw new IllegalArgumentException("Invalid encoded length");
        }

        int dataSize = fullBlockCount * FULL_BLOCK_SIZE + lastBlockDecodedSize;
        int[] data = new int[dataSize];
        System.out.println("Only first block...");
        data = decodeBlock(MoneroUtils.subarray(bin, 0 * FULL_ENCODED_BLOCK_SIZE, 0 * FULL_ENCODED_BLOCK_SIZE + FULL_ENCODED_BLOCK_SIZE), data, 0 * FULL_BLOCK_SIZE);
        System.out.println("Done....");
        for (int i = 0; i < fullBlockCount; i++) {
            data = decodeBlock(MoneroUtils.subarray(bin, i * FULL_ENCODED_BLOCK_SIZE, i * FULL_ENCODED_BLOCK_SIZE + FULL_ENCODED_BLOCK_SIZE), data, i * FULL_BLOCK_SIZE);
        }
        if (lastBlockSize > 0) {
            int[] subarray = MoneroUtils.subarray(bin, fullBlockCount * FULL_ENCODED_BLOCK_SIZE, fullBlockCount * FULL_ENCODED_BLOCK_SIZE + FULL_BLOCK_SIZE);
            data = decodeBlock(subarray, data, fullBlockCount * FULL_BLOCK_SIZE);
        }

        return binToHex(data);
    }

    private static int[] decodeBlock(int[] data, int[] buf, int index) {

        if (data.length < 1 || data.length > FULL_ENCODED_BLOCK_SIZE) {
            throw new RuntimeException("Invalid block length: " + data.length);
        }

        int resSize = ENCODED_BLOCK_SIZE.get(data.length);
        if (resSize <= 0) {
            throw new RuntimeException("Invalid block size");
        }
        BigDecimal resNum = BigDecimal.ZERO;
        BigDecimal order = BigDecimal.ONE;
        for (int i = data.length - 1; i >= 0; i--) {
            int digit = ALPHABET.indexOf(data[i]);
            System.out.println("Digit: " + digit);
            if (digit < 0) {
                throw new RuntimeException("Invalid symbol");
            }
            BigDecimal product = order.multiply(new BigDecimal(digit)).add(resNum);
            // if product > UINT64_MAX
            if (product.compareTo(UINT64_MAX) > 0) {
                throw new RuntimeException("Overflow");
            }
            resNum = product;
            order = order.multiply(ALPHABET_SIZE);
        }
        if (resSize < FULL_BLOCK_SIZE && (new BigDecimal(2).pow(8 * resSize).compareTo(resNum) <= 0)) {
            throw new RuntimeException("Overflow 2");
        }

        System.out.println("ResNum: " + resNum);
        System.out.println("ResSize: " + resSize);

        int[] tmpBuf = uint64To8be(resNum, resSize);
        System.out.println("Printing tmpbuf....");
        for(int k = 0; k < tmpBuf.length; k++) {
            System.out.print(tmpBuf[k] + " ");
        }
        System.out.println("");
        for (int j = 0; j < tmpBuf.length; j++) {
            buf[j + index] = tmpBuf[j];
        }

        return buf;
    }

    private static int[] uint64To8be(BigDecimal num, int size) {
        int[] res = new int[size];
        if (size < 1 || size > 8) {
            throw new RuntimeException("Invalid input length");
        }
        BigDecimal twopow8 = new BigDecimal(2).pow(8);
        for (int i = size - 1; i >= 0; i--) {
            res[i] = num.remainder(twopow8).intValue();
            num = num.divide(twopow8);
        }
        return res;
    }

    private static String binToHex(int[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i : data) {
            builder.append(String.format("%02x", i));
        }
        return builder.toString();
    }

    public static int[] subarray(final int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) return null;
        if (startIndexInclusive < 0) startIndexInclusive = 0;
        if (endIndexExclusive > array.length) endIndexExclusive = array.length;

        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) return EMPTY_INT_ARRAY;

        final int[] subarray = new int[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Decoded: " + MoneroUtils.decodeAddress("44AFFq5kSiGBoZ4NMDwYtN18obc8AemS33DBLWs3H7otXft3XjrpDtQGv7SqSsaBYBb98uNbr2VBBEt7f2wfn3RVGQBEP3A"));
    }

}
