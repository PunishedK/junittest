import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddProductTest {

    @Test
    public void tcThemTC() {
        StoreService service = new StoreService();
        Product product = new Product("SP99", "Ổ cứng SSD", 900000, 8);

        service.addProduct(product);

        assertSame(product, service.findProduct("SP99"));
    }

    @Test
    public void tcMSPTrung() {
        StoreService service = new StoreService();
        Product product = new Product("SP01", "Sản phẩm trùng", 500000, 5);

        assertThrows(IllegalArgumentException.class,
                () -> service.addProduct(product));
    }

    @Test
    public void tcMSP_RONG() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("", "Ổ cứng SSD", 900000, 8)));
    }

    @Test
    public void tcTenSp_Rong() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP96", "", 900000, 8)));
    }

    @Test
    public void tcgia_bang0() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP95", "Ổ cứng SSD", 0, 8)));
    }

    @Test
    public void tcSL_am() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP94", "Ổ cứng SSD", 900000, -1)));
    }

    @Test
    public void tcGia_Am() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP93", "Ổ cứng SSD", -100000, 8)));
    }

    @Test
    public void tc_them_SP_hetHang() {
        StoreService service = new StoreService();
        Product product = new Product("SP92", "Sản phẩm hết hàng", 500000, 0);

        service.addProduct(product);

        assertEquals(0, service.findProduct("SP92").getQuantity());
    }
}
