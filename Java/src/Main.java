import com.helton_hcs.monads.Nullable;
import com.helton_hcs.monads.OnDemand;
import com.helton_hcs.monads.factories.MonadFactory;

/**
 * Created by helton on 5/13/15.
 */
public class Main {

    public static void main(String[] args) {
        testNullableInstances();
        testOnDemandInstances();
    }

    private static void testNullableInstances() {
        Nullable<Integer> nullable = MonadFactory.createSimpleNullable(123);
        System.out.println(nullable);
    }

    private static void testOnDemandInstances() {
        OnDemand<Integer> onDemand = MonadFactory.createSimpleOnDemand(123);
        System.out.println(onDemand.call());
    }
}
