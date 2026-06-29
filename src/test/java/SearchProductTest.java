import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchProductTest {

    @Test
    public void tcTimTen() {
        StoreService service = new StoreService();

        List<Product> result = service.searchProducts("Dell");

        assertEquals(1, result.size());
        assertEquals("SP01", result.get(0).getId());
    }

    @Test
    public void timID() {
        StoreService service = new StoreService();

        List<Product> result = service.searchProducts("SP03");

        assertEquals(1, result.size());
        assertEquals("SP03", result.get(0).getId());
    }

    @Test
    public void tcVietthuong() {
        StoreService service = new StoreService();

        List<Product> result = service.searchProducts("logitech");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(product -> product.matches("Logitech")));
    }

    @Test
    public void tcNhieuSp() {
        StoreService service = new StoreService();

        List<Product> result = service.searchProducts("Laptop");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(product -> product.getId().equals("SP01")));
        assertTrue(result.stream().anyMatch(product -> product.getId().equals("SP13")));
    }

    @Test
    public void tcKotonTai() {
        StoreService service = new StoreService();

        List<Product> result = service.searchProducts("abcxyz");

        assertTrue(result.isEmpty());
    }

    @Test
    public void tcTKKhoangTrang() {
        StoreService service = new StoreService();

        List<Product> result = service.searchProducts(" ");

        assertEquals(15, result.size());
    }

    @Test
    public void tcTKCoKhangTrang2Dau() {
        StoreService service = new StoreService();

        List<Product> result = service.searchProducts("  Dell  ");

        assertEquals(1, result.size());
    }
}
