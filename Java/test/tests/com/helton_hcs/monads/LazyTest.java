package tests.com.helton_hcs.monads;

import com.helton_hcs.monads.Lazy;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by helton on 5/14/15.
 */
public class LazyTest {

    @Test
    public void shouldBeAbleToCreateAnInstanceAnGetAValue() {
        Lazy<String> lazy = new Lazy<>(() -> "Hello, World");
        assertThat(lazy.getValue(), is("Hello, World"));
        assertThat(lazy.getValue(), is("Hello, World"));
    }

    @Test
    public void shouldNotBeEvaluatedWhileIsNotCalled() {
        Lazy<String> lazy = new Lazy<>(() -> "Hello, World");
        assertThat(lazy.isValueCreated(), is(false));
        assertThat(lazy.isValueCreated(), is(false));
    }

    @Test
    public void shouldBeEvaluatedWhenCalled() {
        Lazy<String> lazy = new Lazy<>(() -> "Hello, World");
        lazy.getValue();
        assertThat(lazy.isValueCreated(), is(true));
    }
}