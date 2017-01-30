public class SubmitPayment {
    public void execute(ShoppingBasket shoppingBasket) throws EmptyShoppingCartException {
        if (shoppingBasket.items().size() == 0){
            throw new EmptyShoppingCartException();
        }
    }
}
