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
        List<Item> items = shoppingBasket.items();
        if (items.size() == 0){
            throw new EmptyShoppingCartException();
        }
        if (items.stream().filter((Item item) -> !stockValidator.hasStock(item)).count() > 0){
            throw new OutOfStockException();
        }
        paymentGateway.pay(shoppingBasket);
    }
}
