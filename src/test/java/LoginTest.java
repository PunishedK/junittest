import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void tcDNTCAdmin() {
        StoreService service = new StoreService();

        User user = service.login("admin", "123456");

        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void tcDNTCKhach() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        User user = service.login("usera", "123456");

        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals("usera", user.getUsername());
    }

    @Test
    public void tcUsernamevietHoa() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        User user = service.login("USERA", "123456");

        assertEquals("usera", user.getUsername());
    }

    @Test
    public void tcUsernamecoKhoangTrang() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        User user = service.login("  usera  ", "123456");

        assertEquals("usera", user.getUsername());
    }

    @Test
    public void tcSaiMk() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        assertThrows(IllegalArgumentException.class,
                () -> service.login("usera", "sai"));
    }

    @Test
    public void tcUsernameChuaco() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class,
                () -> service.login("testuser", "123456"));
    }

    @Test
    public void tcUsernameRong() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        assertThrows(IllegalArgumentException.class,
                () -> service.login("", "123456"));
    }

    @Test
    public void tcMKRong() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        assertThrows(IllegalArgumentException.class,
                () -> service.login("usera", ""));
    }
}
