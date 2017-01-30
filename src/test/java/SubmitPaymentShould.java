import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubmitPaymentShould {

    private StockValidator stockValidator;
    private SubmitPayment submitPayment;

    @Before
    public void setUp(){
        stockValidator = mock(StockValidator.class);
        submitPayment = new SubmitPayment(stockValidator);
    }

    @Test
    public void works_when_everything_is_correct() throws Exception {
        ShoppingBasket shoppingBasket = validShoppingBasket();

        submitPayment.execute(shoppingBasket);
    }

    @Test(expected = EmptyShoppingCartException.class)
    public void avoid_make_a_payment_of_an_empty_shopping_basket() throws Exception {
        submitPayment.execute(emptyShoppingBasket());
    }

    @Test(expected = OutOfStockException.class)
    public void abort_payment_when_there_is_an_item_out_of_stock() throws Exception {
        ShoppingBasket shoppingBasket = validShoppingBasket();
        when(stockValidator.hasStock(any())).thenReturn(false);

        submitPayment.execute(shoppingBasket);
    }

    // make_the_payment_against_the_payment_gateway
    // send_an_email_when_the_payment_is_successful

    // returns_ok_when_everything_went_well
    // returns_fail_with_the_message_when_there_is_a_problem

    private ShoppingBasket validShoppingBasket() {
        Item item = new Item();
        when(stockValidator.hasStock(item)).thenReturn(true);
        return shoppingBasketWith(item);
    }

    private ShoppingBasket emptyShoppingBasket() {
        return shoppingBasketWith();
    }

    private ShoppingBasket shoppingBasketWith(Item... items) {
        ShoppingBasket shoppingBasket = mock(ShoppingBasket.class);
        when(shoppingBasket.items()).thenReturn(Arrays.asList(items));
        return shoppingBasket;
    }

}
