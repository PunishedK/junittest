import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    @Test
    public void tcDKThanhcong() {
        StoreService service = new StoreService();

        User user = service.registerCustomer(
                "user01", "abc123", "abc123",
                "Nguyễn Văn A", "user01@gmail.com", "0901234567"
        );

        assertNotNull(user);
        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals("user01@gmail.com", user.getEmail());
    }

    @Test
    public void tcUsernameNgan() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("abc", "abc123", "abc123",
                        "Nguyễn Văn A", "user02@gmail.com", "0901234567"));
    }

    @Test
    public void tcTrungUserName() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("usera", "abc123", "abc123",
                        "Nguyễn Văn B", "vanb@gmail.com", "0901234568"));
    }

    @Test
    public void tcMKNgan() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user04", "12345", "12345",
                        "Nguyễn Văn A", "user04@gmail.com", "0901234567"));
    }

    @Test
    public void tcMKXacNhanSai() {
        StoreService service = new StoreService();

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user05", "abc123", "xyz789",
                        "Nguyễn Văn A", "user05@gmail.com", "0901234567"));

        assertEquals("Mật khẩu xác nhận không khớp", error.getMessage());
    }

    @Test
    public void tcSaiEmail() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user06", "abc123", "abc123",
                        "Nguyễn Văn A", "email-sai.com", "0901234567"));
    }

    @Test
    public void tcTenRong() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user07", "abc123", "abc123",
                        "", "user07@gmail.com", "0901234567"));
    }

    @Test
    public void tcSDTSai() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user08", "abc123", "abc123",
                        "Nguyễn Văn A", "user08@gmail.com", "12345"));
    }

    @Test
    public void tcEmailDaTonTai() {
        StoreService service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("userb", "abc123", "abc123",
                        "Nguyễn Văn B", "VANA@gmail.com", "0901234568"));
    }

    @Test
    public void TcUsernameKTkophuhop() {
        StoreService service = new StoreService();

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user@01", "abc123", "abc123",
                        "Nguyễn Văn A", "user10@gmail.com", "0901234567"));
    }
}
