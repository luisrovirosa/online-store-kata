import java.util.List;

public class SubmitPayment {
    private final StockValidator stockValidator;

    public SubmitPayment(StockValidator stockValidator) {
        this.stockValidator = stockValidator;
    }

    public void execute(ShoppingBasket shoppingBasket) throws EmptyShoppingCartException {
        List<Item> items = shoppingBasket.items();
        if (items.size() == 0){
            throw new EmptyShoppingCartException();
        }
        items.forEach(item -> {
            stockValidator.hasStock(item);
        });
    }
}
