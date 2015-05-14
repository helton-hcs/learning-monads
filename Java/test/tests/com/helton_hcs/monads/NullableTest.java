package tests.com.helton_hcs.monads;

import com.helton_hcs.monads.Nullable;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by helton on 5/13/15.
 */
public class NullableTest {

    @Test
    public void shouldBeAbleToCreatePassingAValue() {
        Nullable<Integer> intNullable = new Nullable(123);
        assertThat(intNullable, notNullValue());
    }

    @Test
    public void shouldBeAbleToCreateWithoutPassingAValue() {
        Nullable<Integer> intNullable = new Nullable();
        assertThat(intNullable, notNullValue());
    }

    @Test
    public void hasValueShouldReturnTrueWhenCreatedWithValue() {
        Nullable<Integer> intNullable = new Nullable(123);
        assertThat(intNullable.hasValue(), is(true));
    }

    @Test
    public void hasValueShouldReturnFalseWhenCreatedWithoutAValue() {
        Nullable<Integer> intNullable = new Nullable();
        assertThat(intNullable.hasValue(), is(false));
    }

    @Test
    public void hasValueShouldReturnFalseWhenCreatedWithNull() {
        Nullable<Integer> intNullable = new Nullable(null);
        assertThat(intNullable.hasValue(), is(false));
    }

    @Test
    public void getValueShouldReturnTheWrappedValue() {
        Nullable<Integer> intNullable = new Nullable(123);
        assertThat(intNullable.getValue(), is(123));
    }

    @Test
    public void getValueShouldReturnNullWhenItHasNoValue() {
        Nullable<Integer> intNullable = new Nullable();
        assertThat(intNullable.getValue(), nullValue());
    }

    @Test
    public void nullableShouldHaveAnCustomToStringMethod() {
        Nullable<Integer> intNullable = new Nullable(123);
        assertThat(intNullable.toString(), is("123"));
        Nullable<Integer> nullNullable = new Nullable();
        assertThat(nullNullable.toString(), is("<null>"));
    }

    @Test
    public void oneCanBeAddedToAnNullableWithValue() {
        Nullable<Integer> intNullable = new Nullable(99);
        Nullable<Integer> intNullablePlusOne = Nullable.addOne(intNullable);
        assertThat(intNullablePlusOne.getValue(), is(100));
    }

    @Test
    public void oneCanBeAddedToAnNullableWithoutValue() {
        Nullable<Integer> intNullable = new Nullable();
        Nullable<Integer> intNullablePlusOne = Nullable.addOne(intNullable);
        assertThat(intNullablePlusOne.getValue(), nullValue());
    }
}