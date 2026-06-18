import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProd {
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
}
