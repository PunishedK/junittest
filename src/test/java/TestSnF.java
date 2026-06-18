import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSnF {
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
