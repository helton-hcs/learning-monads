package com.helton_hcs.monads.factories;

import com.helton_hcs.monads.Nullable;
import com.helton_hcs.monads.OnDemand;

import java.util.function.Supplier;

/**
 * Created by helton on 5/13/15.
 */
public class MonadFactory {

    public static <T> Nullable<T> createSimpleNullable(T value) {
        return new Nullable<>(value);
    }

    public static <T> OnDemand<T> createSimpleOnDemand(T staticValue) {
        return new OnDemand<>(() -> staticValue);
    }
}
