import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public void works_when_everything_is_correct() throws EmptyShoppingCartException {
        ShoppingBasket shoppingBasket = shoppingBasketWith(Collections.singletonList(new Item()));

        submitPayment.execute(shoppingBasket);
    }

    @Test(expected = EmptyShoppingCartException.class)
    public void avoid_make_a_payment_of_an_empty_shopping_basket() throws EmptyShoppingCartException {
        submitPayment.execute(emptyShoppingBasket());
    }


    @Test
    public void check_that_all_the_items_are_in_stock() throws EmptyShoppingCartException {
        Item anItem = new Item();
        ShoppingBasket shoppingBasket = shoppingBasketWith(Collections.singletonList(anItem));

        submitPayment.execute(shoppingBasket);

        verify(stockValidator).hasStock(anItem);
    }

    // abort_payment_when_there_is_an_item_out_of_stock
    // returns_ok_when_everything_went_well

    // returns_fail_with_the_message_when_there_is_a_problem
    // send_an_email_when_the_payment_is_successful
    // make_the_payment_against_the_payment_gateway

    private ShoppingBasket emptyShoppingBasket() {
        return shoppingBasketWith(Collections.emptyList());
    }

    private ShoppingBasket shoppingBasketWith(List<Item> t) {
        ShoppingBasket shoppingBasket = mock(ShoppingBasket.class);
        when(shoppingBasket.items()).thenReturn(t);
        return shoppingBasket;
    }

}
