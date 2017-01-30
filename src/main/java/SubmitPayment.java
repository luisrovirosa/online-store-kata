import java.util.List;
import java.util.stream.Stream;

public class SubmitPayment {
    private final StockValidator stockValidator;
    private final PaymentGateway paymentGateway;

    public SubmitPayment(StockValidator stockValidator, PaymentGateway paymentGateway) {
        this.stockValidator = stockValidator;
        this.paymentGateway = paymentGateway;
    }

    public void execute(ShoppingBasket shoppingBasket) throws EmptyShoppingCartException, OutOfStockException {
        assertHasItems(shoppingBasket);
        assertHasStock(shoppingBasket);
        pay(shoppingBasket);
    }

    private void assertHasItems(ShoppingBasket shoppingBasket) throws EmptyShoppingCartException {
        if (shoppingBasket.items().size() == 0){
            throw new EmptyShoppingCartException();
        }
    }

    private void assertHasStock(ShoppingBasket shoppingBasket) throws OutOfStockException {
        if (shoppingBasket.items().stream().filter((Item item) -> !stockValidator.hasStock(item)).count() > 0){
            throw new OutOfStockException();
        }
    }

    private void pay(ShoppingBasket shoppingBasket) {
        paymentGateway.pay(shoppingBasket);
    }
}
