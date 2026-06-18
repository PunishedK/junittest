import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAuth {
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
}
