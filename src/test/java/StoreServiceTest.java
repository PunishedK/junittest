import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreServiceTest {

    @Test
    public void testLoginAdminSuccess() {
        StoreService service = new StoreService();

        User user = service.login("admin", "123456");

        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testLoginWrongPassword() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () -> service.login("admin", "sai"));
    }

    @Test
    public void testRegisterCustomerSuccess() {
        StoreService service = new StoreService();

        service.registerCustomer("diep", "123", "Nguyễn Văn Điệp");
        User user = service.login("diep", "123");

        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals("Nguyễn Văn Điệp", user.getFullName());
    }

    @Test
    public void testRegisterDuplicateUsername() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () -> service.registerCustomer("admin", "123", "Tài khoản trùng"));
    }

    @Test
    public void testAdminAddProduct() {
        StoreService service = new StoreService();

        service.addProduct(new Product("SP99", "Ổ cứng SSD", 900000, 8));

        assertNotNull(service.findProduct("SP99"));
    }

    @Test
    public void testAdminUpdateProduct() {
        StoreService service = new StoreService();

        service.updateProduct("SP01", "Laptop Dell Gaming", 18000000, 7);

        Product product = service.findProduct("SP01");
        assertEquals("Laptop Dell Gaming", product.getName());
        assertEquals(18000000, product.getPrice());
        assertEquals(7, product.getQuantity());
    }

    @Test
    public void testAdminDeleteProduct() {
        StoreService service = new StoreService();

        service.deleteProduct("SP02");

        assertNull(service.findProduct("SP02"));
    }

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

    @Test
    public void testSearchProductSuccess() {
        StoreService service = new StoreService();

        List<Product> products = service.searchProducts("Dell");

        assertFalse(products.isEmpty());
        assertEquals("SP01", products.get(0).getId());
    }

    @Test
    public void testSearchProductNoResult() {
        StoreService service = new StoreService();

        List<Product> products = service.searchProducts("abcxyz");

        assertTrue(products.isEmpty());
    }


    @Test
    public void testFilterAvailableProducts() {
        StoreService service = new StoreService();

        List<Product> products = service.filterAvailableProducts();

        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getQuantity() > 0));
    }

}
