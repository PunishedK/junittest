import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCart {
    @Test
    public void testCheckoutSuccessAndReduceStock() {
        StoreService service = new StoreService();
        User customer = service.login("khach", "123");
        Product product = service.findProduct("SP02");

        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product, 2));

        Order order = service.checkout(customer, cart, "Nguyễn Văn Điệp", "0912345678", "Hà Nội", "COD");

        assertEquals("DH001", order.getOrderId());
        assertEquals(2, order.getItems().get(0).getQuantity());
        assertEquals(48, product.getQuantity());
    }

    @Test
    public void testCheckoutEmptyCart() {
        StoreService service = new StoreService();
        User customer = service.login("khach", "123");

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer, new ArrayList<>(), "A", "0123", "Hà Nội", "COD")
        );
    }

    @Test
    public void testBuyMoreThanStock() {
        StoreService service = new StoreService();
        Product product = service.findProduct("SP01");

        assertThrows(IllegalArgumentException.class, () -> product.reduceQuantity(999));
    }

    @Test
    public void testDiscountWhenTotalOverOneMillion() {
        StoreService service = new StoreService();
        User customer = service.login("khach", "123");
        Product product = service.findProduct("SP03");

        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product, 1));

        Order order = service.checkout(customer, cart, "Nguyễn Văn Điệp", "0912345678", "Hà Nội", "COD");

        assertEquals(1250000, order.getTotalBeforeDiscount());
        assertEquals(125000, order.getDiscount());
        assertEquals(1125000, order.getTotal());
    }
}
