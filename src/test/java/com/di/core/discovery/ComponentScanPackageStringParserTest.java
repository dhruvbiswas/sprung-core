package com.di.core.discovery;

import org.junit.Assert;
import org.junit.Test;

public class ComponentScanPackageStringParserTest {

    @Test
    public void testComponentScanPackageStringParser() {
        String str = "com.sample.app.lib, com.sample.app.lib2";
        String[] tokens = str.split(",");

        Assert.assertEquals(tokens[0].trim(), "com.sample.app.lib");
        Assert.assertEquals(tokens[1].trim(), "com.sample.app.lib2");
    }
}
