import java.util.List;
import java.util.stream.Stream;

public class SubmitPayment {
    private final StockValidator stockValidator;
    private final PaymentGateway paymentGateway;
    private final Mailer mailer;

    public SubmitPayment(StockValidator stockValidator, PaymentGateway paymentGateway, Mailer mailer) {
        this.stockValidator = stockValidator;
        this.paymentGateway = paymentGateway;
        this.mailer = mailer;
    }

    public void execute(ShoppingBasket shoppingBasket) throws EmptyShoppingCartException, OutOfStockException {
        assertHasItems(shoppingBasket);
        assertHasStock(shoppingBasket);
        pay(shoppingBasket);
        sendConfirmationEmail(shoppingBasket);
    }

    private void assertHasItems(ShoppingBasket shoppingBasket) throws EmptyShoppingCartException {
        if (shoppingBasket.items().size() == 0) {
            throw new EmptyShoppingCartException();
        }
    }

    private void assertHasStock(ShoppingBasket shoppingBasket) throws OutOfStockException {
        Stream<Item> outOfStockItems = shoppingBasket.items().stream().filter(
                (Item item) -> !stockValidator.hasStock(item)
        );
        if (outOfStockItems.count() > 0) {
            throw new OutOfStockException();
        }
    }

    private void pay(ShoppingBasket shoppingBasket) {
        paymentGateway.pay(shoppingBasket);
    }

    private void sendConfirmationEmail(ShoppingBasket shoppingBasket) {
        mailer.sendConfirmationEmail(shoppingBasket);
    }
}
