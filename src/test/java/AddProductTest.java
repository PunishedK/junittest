import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử hàm thêm sản phẩm")
public class AddProductTest {

    private StoreService service;

    @BeforeEach
    public void setUp() {
        service = new StoreService();
    }

    @AfterEach
    public void tearDown() {
        service = null;
    }

    @Test
    @DisplayName("TC_SP_01 - Thêm sản phẩm thành công")
    public void tcThemTC() {
        Product product = new Product("SP99", "Ổ cứng SSD", 900000, 8);

        service.addProduct(product);

        assertSame(product, service.findProduct("SP99"));
    }

    @Test
    @DisplayName("TC_SP_02 - Thêm thất bại do mã sản phẩm trùng")
    public void tcMSPTrung() {
        Product product = new Product("SP01", "Sản phẩm trùng", 500000, 5);

        assertThrows(IllegalArgumentException.class,
                () -> service.addProduct(product));
    }

    @Test
    @DisplayName("TC_SP_03 - Thêm thất bại do mã sản phẩm rỗng")
    public void tcMSP_RONG() {
        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("", "Ổ cứng SSD", 900000, 8)));
    }

    @Test
    @DisplayName("TC_SP_04 - Thêm thất bại do tên sản phẩm rỗng")
    public void tcTenSp_Rong() {
        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP96", "", 900000, 8)));
    }

    @Test
    @DisplayName("TC_SP_05 - Thêm thất bại do giá bằng 0")
    public void tcgia_bang0() {
        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP95", "Ổ cứng SSD", 0, 8)));
    }

    @Test
    @DisplayName("TC_SP_06 - Thêm thất bại do số lượng âm")
    public void tcSL_am() {
        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP94", "Ổ cứng SSD", 900000, -1)));
    }

    @Test
    @DisplayName("TC_SP_07 - Thêm thất bại do giá âm")
    public void tcGia_Am() {
        assertThrows(IllegalArgumentException.class, () ->
                service.addProduct(new Product("SP93", "Ổ cứng SSD", -100000, 8)));
    }

    @Test
    @DisplayName("TC_SP_08 - Thêm sản phẩm hết hàng thành công")
    public void tc_them_SP_hetHang() {
        Product product = new Product("SP92", "Sản phẩm hết hàng", 500000, 0);

        service.addProduct(product);

        assertEquals(0, service.findProduct("SP92").getQuantity());
    }
}
