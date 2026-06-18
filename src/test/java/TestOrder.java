import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrder {
    @Test
    public void testCustomerViewOrderHistory() {
        StoreService service = new StoreService();
        User customer = service.login("khach", "123");
        Product product = service.findProduct("SP02");

        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product, 1));

        service.checkout(customer, cart,
                "Nguyễn Văn Điệp", "0912345678", "Hà Nội", "COD");

        assertEquals(1, service.getOrdersFor(customer).size());
    }

    @Test
    public void testAdminViewAllOrders() {
        StoreService service = new StoreService();
        User customer = service.login("khach", "123");
        Product product = service.findProduct("SP02");

        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product, 1));

        service.checkout(customer, cart,
                "Nguyễn Văn Điệp", "0912345678", "Hà Nội", "COD");

        User admin = service.login("admin", "123456");

        assertEquals(1, service.getOrdersFor(admin).size());
    }

    @Test
    public void testUpdateOrderStatus() {
        StoreService service = new StoreService();
        User customer = service.login("khach", "123");
        Product product = service.findProduct("SP02");

        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(product, 1));

        Order order = service.checkout(customer, cart, "Nguyễn Văn Điệp", "0912345678", "Hà Nội", "COD");
        service.updateOrderStatus(order.getOrderId(), OrderStatus.DANG_GIAO);

        assertEquals(OrderStatus.DANG_GIAO, service.findOrder(order.getOrderId()).getStatus());
    }
}
