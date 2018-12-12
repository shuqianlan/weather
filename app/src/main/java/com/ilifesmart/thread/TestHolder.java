package com.ilifesmart.thread;

import java.util.Collection;
import java.util.Collections;

public class TestHolder {

    private Holder holder;

    public void initialize() {
        holder = new Holder(1);
    }

    public static class Holder {

        private int n;
        public Holder(int n) {
            this.n = n;
        }

        public void assertSanity() {
            if (n != n) {
                throw new AssertionError("This statement is false.");
            }
        }
    }
}
