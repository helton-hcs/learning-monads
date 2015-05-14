package com.helton_hcs.monads;

import com.helton_hcs.monads.factories.MonadFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by helton on 5/13/15.
 */
public class OnDemand<T> implements Monad {
    private Supplier<T> function;

    public OnDemand(Supplier<T> function) {
        this.function = function;
    }

    public T call() {
        return function.get();
    }

    public static <A, R> OnDemand<R> applyFunction(OnDemand<A> onDemand, Function<A, R> function) {
        return new OnDemand<R>(() -> {
            A unwrappedValue = onDemand.call();
            R result = function.apply(unwrappedValue);
            return result;
        });
    }

    public static <A, R> OnDemand<R> applySpecialFunction(OnDemand<A> onDemand, Function<A, OnDemand<R>> function) {
        return new OnDemand<R>(() -> {
            A unwrappedValue = onDemand.call();
            OnDemand<R> result = function.apply(unwrappedValue);
            return result.call();
        });
    }

    public static OnDemand<Long> addOne(OnDemand<Long> onDemand) {
        return applyFunction(onDemand, i -> i + 1);
    }
}
