package com.helton_hcs.monads;

import java.util.function.Supplier;

/**
 * Created by helton on 5/14/15.
 */
public class Lazy<T> {
    private T value;
    private boolean valueCreated;
    private Supplier<T> function;

    public Lazy(Supplier<T> function) {
        this.function = function;
        this.valueCreated = false;
        this.value = null;
    }

    public boolean isValueCreated() {
        return valueCreated;
    }

    public T getValue() {
        if (!valueCreated) {
            this.value = function.get();
            this.valueCreated = true;
        }
        return value;
    }
}
