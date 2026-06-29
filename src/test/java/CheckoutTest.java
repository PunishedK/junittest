import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử hàm thanh toán")
public class CheckoutTest {

    private StoreService service;
    private User customer;
    private Product productSP02;
    private List<CartItem> cart;

    @BeforeEach
    public void setUp() {
        service = new StoreService();

        service.registerCustomer("userb", "123456", "123456",
                "Nguyễn Văn B", "vanb@gmail.com", "0901234568");

        customer = service.login("userb", "123456");
        productSP02 = service.findProduct("SP02");
        cart = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        cart = null;
        productSP02 = null;
        customer = null;
        service = null;
    }

    @Test
    @DisplayName("TC_TT_01 - Thanh toán một sản phẩm thành công")
    public void tcTT1SP() {
        int stockBefore = productSP02.getQuantity();
        cart.add(new CartItem(productSP02, 1));

        Order order = service.checkout(customer, cart,
                "Nguyễn Văn B", "0901234568", "Hà Nội", "COD");

        assertEquals("DH001", order.getOrderId());
        assertEquals(stockBefore - 1, productSP02.getQuantity());
    }

    @Test
    @DisplayName("TC_TT_02 - Thanh toán nhiều sản phẩm thành công")
    public void tcTTNhieuSP() {
        cart.add(new CartItem(service.findProduct("SP02"), 1));
        cart.add(new CartItem(service.findProduct("SP03"), 1));

        Order order = service.checkout(customer, cart,
                "Nguyễn Văn B", "0901234568", "Hà Nội", "COD");

        assertEquals(2, order.getItems().size());
    }

    @Test
    @DisplayName("TC_TT_03 - Thanh toán thất bại do giỏ hàng rỗng")
    public void tcGioHangRong() {
        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer, new ArrayList<>(),
                        "Nguyễn Văn B", "0901234568", "Hà Nội", "COD"));
    }

    @Test
    @DisplayName("TC_TT_04 - Thanh toán thất bại do mua vượt tồn kho")
    public void tcQuaTonKho() {
        int stockBefore = productSP02.getQuantity();
        cart.add(new CartItem(productSP02, 999));

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer, cart,
                        "Nguyễn Văn B", "0901234568", "Hà Nội", "COD"));

        assertEquals(stockBefore, productSP02.getQuantity());
    }

    @Test
    @DisplayName("TC_TT_05 - Tên người nhận rỗng không làm giảm kho")
    public void tcTennguoinhanRong() {
        int stockBefore = productSP02.getQuantity();
        cart.add(new CartItem(productSP02, 1));

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer, cart,
                        "", "0901234568", "Hà Nội", "COD"));

        assertEquals(stockBefore, productSP02.getQuantity());
    }

    @Test
    @DisplayName("TC_TT_06 - Số điện thoại rỗng không làm giảm kho")
    public void tcSDTRong() {
        int stockBefore = productSP02.getQuantity();
        cart.add(new CartItem(productSP02, 1));

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer, cart,
                        "Nguyễn Văn B", "", "Hà Nội", "COD"));

        assertEquals(stockBefore, productSP02.getQuantity());
    }

    @Test
    @DisplayName("TC_TT_07 - Địa chỉ rỗng không làm giảm kho")
    public void tcDiaChiRong() {
        int stockBefore = productSP02.getQuantity();
        cart.add(new CartItem(productSP02, 1));

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer, cart,
                        "Nguyễn Văn B", "0901234568", "", "COD"));

        assertEquals(stockBefore, productSP02.getQuantity());
    }

    @Test
    @DisplayName("TC_TT_08 - Phương thức thanh toán rỗng không làm giảm kho")
    public void tcPPRong() {
        int stockBefore = productSP02.getQuantity();
        cart.add(new CartItem(productSP02, 1));

        assertThrows(IllegalArgumentException.class, () ->
                service.checkout(customer, cart,
                        "Nguyễn Văn B", "0901234568", "Hà Nội", ""));

        assertEquals(stockBefore, productSP02.getQuantity());
    }
}
