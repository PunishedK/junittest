import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử hàm đăng ký")
public class RegisterTest {

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
    @DisplayName("TC_DK_01 - Đăng ký tài khoản thành công")
    public void tcDKThanhcong() {
        User user = service.registerCustomer(
                "user01", "abc123", "abc123",
                "Nguyễn Văn A", "user01@gmail.com", "0901234567"
        );

        assertNotNull(user);
        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals("user01@gmail.com", user.getEmail());
    }

    @Test
    @DisplayName("TC_DK_02 - Đăng ký thất bại do username quá ngắn")
    public void tcUsernameNgan() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("abc", "abc123", "abc123",
                        "Nguyễn Văn A", "user02@gmail.com", "0901234567"));
    }

    @Test
    @DisplayName("TC_DK_03 - Đăng ký thất bại do trùng username")
    public void tcTrungUserName() {
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("usera", "abc123", "abc123",
                        "Nguyễn Văn B", "vanb@gmail.com", "0901234568"));
    }

    @Test
    @DisplayName("TC_DK_04 - Đăng ký thất bại do mật khẩu quá ngắn")
    public void tcMKNgan() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user04", "12345", "12345",
                        "Nguyễn Văn A", "user04@gmail.com", "0901234567"));
    }

    @Test
    @DisplayName("TC_DK_05 - Đăng ký thất bại do xác nhận mật khẩu sai")
    public void tcMKXacNhanSai() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user05", "abc123", "xyz789",
                        "Nguyễn Văn A", "user05@gmail.com", "0901234567"));

        assertEquals("Mật khẩu xác nhận không khớp", error.getMessage());
    }

    @Test
    @DisplayName("TC_DK_06 - Đăng ký thất bại do email sai định dạng")
    public void tcSaiEmail() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user06", "abc123", "abc123",
                        "Nguyễn Văn A", "email-sai.com", "0901234567"));
    }

    @Test
    @DisplayName("TC_DK_07 - Đăng ký thất bại do họ tên rỗng")
    public void tcTenRong() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user07", "abc123", "abc123",
                        "", "user07@gmail.com", "0901234567"));
    }

    @Test
    @DisplayName("TC_DK_08 - Đăng ký thất bại do số điện thoại sai")
    public void tcSDTSai() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user08", "abc123", "abc123",
                        "Nguyễn Văn A", "user08@gmail.com", "12345"));
    }

    @Test
    @DisplayName("TC_DK_09 - Đăng ký thất bại do email đã tồn tại")
    public void tcEmailDaTonTai() {
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");

        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("userb", "abc123", "abc123",
                        "Nguyễn Văn B", "VANA@gmail.com", "0901234568"));
    }

    @Test
    @DisplayName("TC_DK_10 - Đăng ký thất bại do username chứa ký tự đặc biệt")
    public void TcUsernameKTkophuhop() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerCustomer("user@01", "abc123", "abc123",
                        "Nguyễn Văn A", "user10@gmail.com", "0901234567"));
    }
}
