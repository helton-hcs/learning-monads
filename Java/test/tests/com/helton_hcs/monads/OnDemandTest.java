package tests.com.helton_hcs.monads;

import com.helton_hcs.monads.OnDemand;
import com.helton_hcs.monads.factories.MonadFactory;
import org.junit.Test;

import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by helton on 5/13/15.
 */
public class OnDemandTest {

    @Test
    public void shouldBeAbleToCallTheCallMethod() {
        OnDemand<String> onDemand = new OnDemand<>(() -> "Hello, World!");
        assertThat(onDemand.call(), is("Hello, World!"));
    }

    private static Long getTime() {
        return new Date().getTime();
    }

    @Test
    public void oneCanBeAddedToAnNullableWithValue() throws InterruptedException {
        OnDemand<Long> longNullable = new OnDemand(OnDemandTest::getTime);
        sleep(500); //just to make sure that the onDemand is not holding a static value
        OnDemand<Long> longNullablePlusOne = OnDemand.addOne(longNullable);
        Long expected = longNullablePlusOne.call();
        Long result = getTime() + 1;
        assertThat(expected, is(result));
    }

    @Test
    public void shouldBeAbleToConvertAnTypeValueToAnother() {
        OnDemand<Double> doubleValue = MonadFactory.createSimpleOnDemand(3.14);
        OnDemand<Integer> integerValue = OnDemand.applyFunction(doubleValue, d -> d.intValue());
        assertThat(integerValue.call(), is(3));
    }
}