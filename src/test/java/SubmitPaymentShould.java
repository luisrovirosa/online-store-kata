import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubmitPaymentShould {

    @Test
    public void works_when_everything_is_correct() throws EmptyShoppingCartException {
        StockValidator stockValidator = mock(StockValidator.class);
        SubmitPayment submitPayment = new SubmitPayment(stockValidator);
        ShoppingBasket shoppingBasket = mock(ShoppingBasket.class);
        when(shoppingBasket.items()).thenReturn(Arrays.asList(new Item()));

        submitPayment.execute(shoppingBasket);
    }

    @Test(expected = EmptyShoppingCartException.class)
    public void avoid_make_a_payment_of_an_empty_shopping_basket() throws EmptyShoppingCartException {
        StockValidator stockValidator = mock(StockValidator.class);
        SubmitPayment submitPayment = new SubmitPayment(stockValidator);
        ShoppingBasket shoppingBasket = mock(ShoppingBasket.class);
        when(shoppingBasket.items()).thenReturn(Arrays.asList(new Item[]{}));

        submitPayment.execute(shoppingBasket);
    }

    @Test
    public void check_that_all_the_items_are_in_stock() throws EmptyShoppingCartException {
        StockValidator stockValidator = mock(StockValidator.class);
        SubmitPayment submitPayment = new SubmitPayment(stockValidator);
        ShoppingBasket shoppingBasket = mock(ShoppingBasket.class);
        Item anItem = new Item();
        when(shoppingBasket.items()).thenReturn(Arrays.asList(new Item[]{anItem}));

        submitPayment.execute(shoppingBasket);

        verify(stockValidator).hasStock(anItem);
    }

    // abort_payment_when_there_is_an_item_out_of_stock
    // make_the_payment_against_the_payment_gateway
    // send_an_email_when_the_payment_is_successful

    // returns_ok_when_everything_went_well
    // returns_fail_with_the_message_when_there_is_a_problem

}
