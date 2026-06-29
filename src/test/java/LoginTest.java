import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử hàm đăng nhập")
public class LoginTest {

    private StoreService service;

    @BeforeEach
    public void setUp() {
        service = new StoreService();
        service.registerCustomer("usera", "123456", "123456",
                "Nguyễn Văn A", "vana@gmail.com", "0901234567");
    }

    @AfterEach
    public void tearDown() {
        service = null;
    }

    @Test
    @DisplayName("TC_DN_01 - Đăng nhập Admin thành công")
    public void tcDNTCAdmin() {
        User user = service.login("admin", "123456");

        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("TC_DN_02 - Đăng nhập khách hàng thành công")
    public void tcDNTCKhach() {
        User user = service.login("usera", "123456");

        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals("usera", user.getUsername());
    }

    @Test
    @DisplayName("TC_DN_03 - Đăng nhập với username viết hoa")
    public void tcUsernamevietHoa() {
        User user = service.login("USERA", "123456");

        assertEquals("usera", user.getUsername());
    }

    @Test
    @DisplayName("TC_DN_04 - Đăng nhập với username có khoảng trắng")
    public void tcUsernamecoKhoangTrang() {
        User user = service.login("  usera  ", "123456");

        assertEquals("usera", user.getUsername());
    }

    @Test
    @DisplayName("TC_DN_05 - Đăng nhập sai mật khẩu")
    public void tcSaiMk() {
        assertThrows(IllegalArgumentException.class,
                () -> service.login("usera", "sai"));
    }

    @Test
    @DisplayName("TC_DN_06 - Đăng nhập bằng username chưa tồn tại")
    public void tcUsernameChuaco() {
        assertThrows(IllegalArgumentException.class,
                () -> service.login("testuser", "123456"));
    }

    @Test
    @DisplayName("TC_DN_07 - Đăng nhập với username rỗng")
    public void tcUsernameRong() {
        assertThrows(IllegalArgumentException.class,
                () -> service.login("", "123456"));
    }

    @Test
    @DisplayName("TC_DN_08 - Đăng nhập với mật khẩu rỗng")
    public void tcMKRong() {
        assertThrows(IllegalArgumentException.class,
                () -> service.login("usera", ""));
    }
}
