import com.vm.OGVendingMachine;
import com.vm.domain.Coin;
import com.vm.domain.Item;
import com.vm.exception.NotEnoughChangeException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by egucer on 27-Mar-19.
 */
public class VendingMachineTest {

    private static OGVendingMachine vendingMachine;

    @BeforeClass
    public static void setup() {
        vendingMachine = new OGVendingMachine();
    }

    @Before
    public void initialize() {
        vendingMachine.initialize();
    }

    @Test
    public void testInitialize(){
        assertThat(vendingMachine.getCoinInventory().keySet(), hasSize(3));
        assertThat(vendingMachine.getItemInventory().keySet(), hasSize(4));
        for (Coin c : Coin.values()) {
            assertThat(vendingMachine.getCoinInventory().get(c), is(equalTo(Integer.valueOf(2))));
        }
        for (Item i : Item.values()) {
            assertThat(vendingMachine.getItemInventory().get(i), is(equalTo(Integer.valueOf(10))));
        }
    }

    @Test(expected = NotEnoughChangeException.class)
    public void testGetChangeNotAppropriateCoinsShouldThrowException() {
        vendingMachine.getChange(145);
    }

    @Test(expected = NotEnoughChangeException.class)
    public void testGetChangeOutOfCoinsShouldThrowException() {
        vendingMachine.getChange(375);
    }

    @Test
    public void testGetChangeShouldNotThrowException() {
        vendingMachine.getChange(325);
    }

    @Test
    public void testInsertCoin() {
        vendingMachine.insertCoin(Coin.LIRA1);
        assertThat(vendingMachine.getCurrentBalance(), is(equalTo(Integer.valueOf(100))));
    }
}
