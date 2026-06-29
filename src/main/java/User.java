public class User {
    private final String username;
    private final String password;
    private final String fullName;
    private final String email;
    private final String phone;
    private final Role role;

    public User(String username, String password, String fullName, Role role) {
        this(username, password, fullName, "", "", role);
    }

    public User(String username, String password, String fullName,
                String email, String phone, Role role) {
        if (isBlank(username)) {
            throw new IllegalArgumentException("Tài khoản không được để trống");
        }
        if (isBlank(password)) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }
        if (isBlank(fullName)) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        if (role == null) {
            throw new IllegalArgumentException("Vai trò không được để trống");
        }
        this.username = username.trim();
        this.password = password;
        this.fullName = fullName.trim();
        this.email = email == null ? "" : email.trim().toLowerCase();
        this.phone = phone == null ? "" : phone.trim();
        this.role = role;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }
}
