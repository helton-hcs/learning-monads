package tests.com.helton_hcs.monads.factories;

import com.helton_hcs.monads.Nullable;
import com.helton_hcs.monads.OnDemand;
import com.helton_hcs.monads.factories.MonadFactory;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by helton on 5/13/15.
 */
public class MonadFactoryTest {

    @Test
    public void shouldBeAbleToCreateASimpleNullable() {
        Nullable<Integer> intNullable = MonadFactory.createSimpleNullable(123);
        assertThat(intNullable, notNullValue());
        assertThat(intNullable.hasValue(), is(true));
        assertThat(intNullable.getValue(), is(123));
    }

    @Test
    public void shouldBeAbleToCreateASimpleOnDemand() {
        OnDemand<String> onDemand = MonadFactory.createSimpleOnDemand("Hello, World!");
        assertThat(onDemand, notNullValue());
        assertThat(onDemand.call(), is("Hello, World!"));
    }
}