import com.helton_hcs.monads.Maybe;
import com.helton_hcs.monads.OnDemand;

import java.util.Date;
import java.util.function.Function;

/**
 * Created by helton on 5/13/15.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("[Testing Maybe<T>]");
        testMaybeInstances();
        System.out.println("[Testing OnDemand<T>]");
        testOnDemandInstances();
        System.out.println("[Test function composition]");
        testComposition();
    }

    private static void testMaybeInstances() {
        Maybe<Integer> maybe = Maybe.unit(123);
        System.out.println(maybe);

        Maybe<Double> d1 = Maybe.safeLog(-1);
        Maybe<Double> d2 = Maybe.safeLog(987654);
        System.out.println(d1.toString());
        System.out.println(d2.toString());

        //Demonstrating the identity property
        Maybe<Integer> integerMaybe1 = Maybe.unit(123);
        Maybe<Integer> integerMaybe2 = new Maybe<>();
        Maybe<Integer> integerMaybe1Copy = Maybe.bind(integerMaybe1, Maybe::unit);
        Maybe<Integer> integerMaybe2Copy = Maybe.bind(integerMaybe2, Maybe::unit);
        System.out.println("integerMaybe1 = " + integerMaybe1);
        System.out.println("integerMaybe1Copy = " + integerMaybe1Copy);
        System.out.println("integerMaybe2 = " + integerMaybe2);
        System.out.println("integerMaybe2Copy = " + integerMaybe2Copy);


        Integer original = 123;
        Maybe<Double> result1 = Maybe.safeLog(original);
        Maybe<Integer> n = Maybe.unit(original);
        Maybe<Double> result2 = Maybe.bind(n, Maybe::safeLog);
        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }

    private static void testOnDemandInstances() {
        OnDemand<Integer> onDemand = OnDemand.unit(123);
        System.out.println(onDemand.call());

        //Demonstrating the identity property
        OnDemand<String> theOnDemand = new OnDemand<>(() -> new Date().toString());
        OnDemand<String> theOnDemandCopy = OnDemand.bind(theOnDemand, OnDemand::unit);
        System.out.println("theOnDemand.call() = " + theOnDemand.call());
        System.out.println("theOnDemandCopy.call() = " + theOnDemandCopy.call());
    }

    public static void testComposition() {
        Function<Long, Long> cube    = x -> x * x * x;
        Function<Long, Double> halve = y -> y / 2.0;
        Function<Long, Double> halveTheCube1 = z -> halve(cube(z));
        Function<Long, Double> halveTheCube2 = compose(Main::cube, Main::halve);
        System.out.println(halveTheCube(123L));
        System.out.println(halveTheCube1.apply(123L));
        System.out.println(halveTheCube2.apply(123L));

        Function<Integer, Maybe<Double>> log = x -> x > 0 ? new Maybe<>(Math.log(x)) : new Maybe<>();
        Function<Double, Maybe<String>> doubleToString = d -> new Maybe<>(d.toString());
        Function<Integer, Maybe<String>> logToString = composeSpecial(log, doubleToString);
        System.out.println(logToString.apply(123));
    }

    public static <X, Y, Z> Function<X, Z> compose(Function<X, Y> f, Function<Y, Z> g) {
        return x -> g.apply(f.apply(x));
    }

    public static <X, Y, Z> Function<X, Maybe<Z>> composeSpecial(Function<X, Maybe<Y>> f, Function<Y, Maybe<Z>> g) {
        return x -> Maybe.bind(f.apply(x), g);
    }

    public static Long cube(Long x) {
        return x * x * x;
    }

    public static Double halve(Long x) {
        return x / 2.0;
    }

    public static double halveTheCube(Long x) {
        return halve(cube(x));
    }
}
