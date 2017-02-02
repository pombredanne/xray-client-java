package com.jfrog.xray.client.test;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by romang on 2/2/17.
 */
public class SystemTests extends XrayTestsBase {

    @Test
    public void testPing() {
        assertTrue(xray.system().ping());
    }
}
