import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {

    private User customer(StoreService service) {
        service.registerCustomer("userb", "123456", "123456",
                "Nguyễn Văn B", "vanb@gmail.com", "0901234568");
        return service.login("userb", "123456");
    }

    private List<CartItem> cartWith(StoreService service, String productId, int quantity) {
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(service.findProduct(productId), quantity));
        return cart;
    }

    @Test
    public void tcTT1SP() {
        StoreService service = new StoreService();
        Product product = service.findProduct("SP02");
        int stockBefore = product.getQuantity();

        Order order = service.checkout(customer(service), cartWith(service, "SP02", 1),
                "Nguyễn Văn B", "0901234568", "Hà Nội", "COD");

        assertEquals("DH001", order.getOrderId());
        assertEquals(stockBefore - 1, product.getQuantity());
    }

    @Test
    public void tcTTNhieuSP() {
        StoreService service = new StoreService();
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem(service.findProduct("SP02"), 1));
        cart.add(new CartItem(service.findProduct("SP03"), 1));

        Order order = service.checkout(customer(service), cart,
                "Nguyễn Văn B", "0901234568", "Hà Nội", "COD");

        assertEquals(2, order.getItems().size());
    }

    @Test
    public void tcGioHangRong() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer(service), new ArrayList<>(),
                        "Nguyễn Văn B", "0901234568", "Hà Nội", "COD"));
    }

    @Test
    public void tcQuaTonKho() {
        StoreService service = new StoreService();
        Product product = service.findProduct("SP02");
        int stockBefore = product.getQuantity();

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer(service), cartWith(service, "SP02", 999),
                        "Nguyễn Văn B", "0901234568", "Hà Nội", "COD"));
        assertEquals(stockBefore, product.getQuantity());
    }

    @Test
    public void tcTennguoinhanRong() {
        StoreService service = new StoreService();
        Product product = service.findProduct("SP02");
        int stockBefore = product.getQuantity();

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer(service), cartWith(service, "SP02", 1),
                        "", "0901234568", "Hà Nội", "COD"));
        assertEquals(stockBefore, product.getQuantity());
    }

    @Test
    public void tcSDTRong() {
        StoreService service = new StoreService();
        Product product = service.findProduct("SP02");
        int stockBefore = product.getQuantity();

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer(service), cartWith(service, "SP02", 1),
                        "Nguyễn Văn B", "", "Hà Nội", "COD"));
        assertEquals(stockBefore, product.getQuantity());
    }

    @Test
    public void tcDiaChiRong() {
        StoreService service = new StoreService();
        Product product = service.findProduct("SP02");
        int stockBefore = product.getQuantity();

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer(service), cartWith(service, "SP02", 1),
                        "Nguyễn Văn B", "0901234568", "", "COD"));
        assertEquals(stockBefore, product.getQuantity());
    }

    @Test
    public void tcPPRong() {
        StoreService service = new StoreService();
        Product product = service.findProduct("SP02");
        int stockBefore = product.getQuantity();

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer(service), cartWith(service, "SP02", 1),
                        "Nguyễn Văn B", "0901234568", "Hà Nội", ""));
        assertEquals(stockBefore, product.getQuantity());
    }
}
