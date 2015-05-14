package com.helton_hcs.monads;

import com.helton_hcs.monads.factories.MonadFactory;

import java.util.function.Function;

/**
 * Created by helton on 5/13/15.
 */
public class Nullable<T> implements Monad {
    private T value;

    public Nullable(T value) {
        this.value = value;
    }

    public Nullable() {
        this.value = null;
    }

    public T getValue() {
        return value;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public String toString() {
        return hasValue() ? value.toString() : "<null>";
    }

    public static <A, R> Nullable<R> applyFunction(Nullable<A> nullable, Function<A, R> function) {
        if (nullable.hasValue()) {
            A unwrappedValue = nullable.getValue();
            R result = function.apply(unwrappedValue);
            return MonadFactory.createSimpleNullable(result);
        }
        return new Nullable<>();
    }

    public static <A, R> Nullable<R> applySpecialFunction(Nullable<A> nullable, Function<A, Nullable<R>> function) {
        if (nullable.hasValue()) {
            A unwrappedValue = nullable.getValue();
            Nullable<R> result = function.apply(unwrappedValue);
            return result;
        }
        return new Nullable<>();
    }

    public static Nullable<Integer> addOne(Nullable<Integer> nullable) {
        return applyFunction(nullable, i -> i + 1);
    }

    public static Nullable<Double> safeLog(Integer x) {
        if (x > 0) {
            return new Nullable<>(Math.log(x));
        }
        return new Nullable<>();
    }
}
