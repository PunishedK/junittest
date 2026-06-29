import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử hàm tìm kiếm sản phẩm")
public class SearchProductTest {

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
    @DisplayName("TC_TK_01 - Tìm sản phẩm theo tên")
    public void tcTimTen() {
        List<Product> result = service.searchProducts("Dell");

        assertEquals(1, result.size());
        assertEquals("SP01", result.get(0).getId());
    }

    @Test
    @DisplayName("TC_TK_02 - Tìm sản phẩm theo mã")
    public void timID() {
        List<Product> result = service.searchProducts("SP03");

        assertEquals(1, result.size());
        assertEquals("SP03", result.get(0).getId());
    }

    @Test
    @DisplayName("TC_TK_03 - Tìm kiếm không phân biệt chữ hoa chữ thường")
    public void tcVietthuong() {
        List<Product> result = service.searchProducts("logitech");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(product -> product.matches("Logitech")));
    }

    @Test
    @DisplayName("TC_TK_04 - Tìm kiếm trả về nhiều sản phẩm")
    public void tcNhieuSp() {
        List<Product> result = service.searchProducts("Laptop");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(product -> product.getId().equals("SP01")));
        assertTrue(result.stream().anyMatch(product -> product.getId().equals("SP13")));
    }

    @Test
    @DisplayName("TC_TK_05 - Tìm kiếm từ khóa không tồn tại")
    public void tcKotonTai() {
        List<Product> result = service.searchProducts("abcxyz");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("TC_TK_06 - Tìm kiếm với từ khóa chỉ có khoảng trắng")
    public void tcTKKhoangTrang() {
        List<Product> result = service.searchProducts(" ");

        assertEquals(15, result.size());
    }

    @Test
    @DisplayName("TC_TK_07 - Tìm kiếm với từ khóa có khoảng trắng hai đầu")
    public void tcTKCoKhangTrang2Dau() {
        List<Product> result = service.searchProducts("  Dell  ");

        assertEquals(1, result.size());
    }
}
