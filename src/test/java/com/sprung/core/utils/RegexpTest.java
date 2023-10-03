package com.sprung.core.utils;

import org.junit.Assert;
import org.junit.Test;

public class RegexpTest {

    @Test
    public void test() {
        Assert.assertTrue(Regexp.isMatch("com/sprung/core/app/lib/AppClass2.class", "class$"));
    }
}
