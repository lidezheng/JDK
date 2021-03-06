/*
 * Copyright (c) 2007, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.graalvm.compiler.jtt.lang;

import org.junit.Test;

import org.graalvm.compiler.jtt.JTTTest;

/*
 */
public class System_identityHashCode01 extends JTTTest {

    private static final Object object0 = new Object();
    private static final Object object1 = new Object();
    private static final Object object2 = new Object();

    private static final int hash0 = System.identityHashCode(object0);
    private static final int hash1 = System.identityHashCode(object1);
    private static final int hash2 = System.identityHashCode(object2);

    public static boolean test(int i) {
        if (i == 0) {
            return hash0 == System.identityHashCode(object0);
        }
        if (i == 1) {
            return hash1 == System.identityHashCode(object1);
        }
        if (i == 2) {
            return hash2 == System.identityHashCode(object2);
        }
        if (i == 3) {
            return 0 == System.identityHashCode(null);
        }
        return false;
    }

    @Test
    public void run0() throws Throwable {
        runTest("test", 0);
    }

    @Test
    public void run1() throws Throwable {
        runTest("test", 1);
    }

    @Test
    public void run2() throws Throwable {
        runTest("test", 2);
    }

    @Test
    public void run3() throws Throwable {
        runTest("test", 3);
    }

    @Test
    public void run4() throws Throwable {
        runTest("test", 4);
    }
}
