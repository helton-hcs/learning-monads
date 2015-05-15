package tests.com.helton_hcs.monads;

import com.helton_hcs.monads.Maybe;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by helton on 5/13/15.
 */
public class MaybeTest {

    @Test
    public void shouldBeAbleToCreatePassingAValue() {
        Maybe<Integer> intMaybe = new Maybe(123);
        assertThat(intMaybe, notNullValue());
    }

    @Test
    public void shouldBeAbleToCreateWithoutPassingAValue() {
        Maybe<Integer> intMaybe = new Maybe();
        assertThat(intMaybe, notNullValue());
    }

    @Test
    public void hasValueShouldReturnTrueWhenCreatedWithValue() {
        Maybe<Integer> intMaybe = new Maybe(123);
        assertThat(intMaybe.hasValue(), is(true));
    }

    @Test
    public void hasValueShouldReturnFalseWhenCreatedWithoutAValue() {
        Maybe<Integer> intMaybe = new Maybe();
        assertThat(intMaybe.hasValue(), is(false));
    }

    @Test
    public void hasValueShouldReturnFalseWhenCreatedWithNull() {
        Maybe<Integer> intMaybe = new Maybe(null);
        assertThat(intMaybe.hasValue(), is(false));
    }

    @Test
    public void getValueShouldReturnTheWrappedValue() {
        Maybe<Integer> intMaybe = new Maybe(123);
        assertThat(intMaybe.getValue(), is(123));
    }

    @Test
    public void getValueShouldReturnNullWhenItHasNoValue() {
        Maybe<Integer> intMaybe = new Maybe();
        assertThat(intMaybe.getValue(), nullValue());
    }

    @Test
    public void nullableShouldHaveAnCustomToStringMethod() {
        Maybe<Integer> intMaybe = new Maybe(123);
        assertThat(intMaybe.toString(), is("123"));
        Maybe<Integer> nullMaybe = new Maybe();
        assertThat(nullMaybe.toString(), is("<null>"));
    }

    @Test
    public void oneCanBeAddedToAnNullableWithValue() {
        Maybe<Integer> intMaybe = new Maybe(99);
        Maybe<Integer> intMaybePlusOne = Maybe.addOne(intMaybe);
        assertThat(intMaybePlusOne.getValue(), is(100));
    }

    @Test
    public void oneCanBeAddedToAnNullableWithoutValue() {
        Maybe<Integer> intMaybe = new Maybe();
        Maybe<Integer> intMaybePlusOne = Maybe.addOne(intMaybe);
        assertThat(intMaybePlusOne.getValue(), nullValue());
    }
}