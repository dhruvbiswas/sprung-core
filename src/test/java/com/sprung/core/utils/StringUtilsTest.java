package com.sprung.core.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringUtilsTest {

    @Before
    public void setup() {}

    @Test
    public void test() {
        String[] tokens = "com/sprung/core/app/lib/AppClass2.class".split("\\.");
        System.out.println("Numtokens: " + tokens.length);
        for(int i = 0; i < tokens.length; i++) {
            System.out.println("Token: " + tokens[i]);
        }
    }

    @After
    public void destroy() {}

}
