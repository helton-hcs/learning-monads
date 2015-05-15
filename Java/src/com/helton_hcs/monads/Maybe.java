package com.helton_hcs.monads;

import java.util.function.Function;

/**
 * Created by helton on 5/13/15.
 */
public class Maybe<T> implements Monad {
    private T value;

    public Maybe(T value) {
        this.value = value;
    }

    public Maybe() {
        this.value = null;
    }

    public static <T> Maybe<T> unit(T value) {
        return new Maybe<>(value);
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

    private static <A, R> Maybe<R> internalBind(Maybe<A> maybe, Function<A, R> function) {
        if (maybe.hasValue()) {
            A unwrappedValue = maybe.getValue();
            R result = function.apply(unwrappedValue);
            return Maybe.unit(result);
        }
        return new Maybe<>();
    }

    public static <A, R> Maybe<R> bind(Maybe<A> maybe, Function<A, Maybe<R>> function) {
        if (maybe.hasValue()) {
            A unwrappedValue = maybe.getValue();
            Maybe<R> result = function.apply(unwrappedValue);
            return result;
        }
        return new Maybe<>();
    }

    public static Maybe<Integer> addOne(Maybe<Integer> maybe) {
        return internalBind(maybe, i -> i + 1);
    }

    public static Maybe<Double> safeLog(Integer x) {
        if (x > 0) {
            return new Maybe<>(Math.log(x));
        }
        return new Maybe<>();
    }
}
