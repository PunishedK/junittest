import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StoreService {
    private final Map<String, User> users = new LinkedHashMap<>();
    private final Map<String, Product> products = new LinkedHashMap<>();
    private final List<Order> orders = new ArrayList<>();

    public StoreService() {
        seedData();
    }

    private void seedData() {

        users.put("admin",
                new User("admin", "123456", "Quản trị viên", Role.ADMIN));

        users.put("khach",
                new User("khach", "123456", "Nguyễn Văn A","khach@gmail.com",
                        "0912345678", Role.CUSTOMER));

        users.put("ppktx",
                new User("ppktx", "123456", "Nguyễn Văn Điệp","diep@gmail.com",
                        "0967293629", Role.CUSTOMER));

        users.put("khanhduy",
                new User("khanhduy", "123456", "Trần Khánh Duy",
                        "khanhduy@gmail.com", "0912345678", Role.CUSTOMER));

        users.put("quangkhanh",
                new User("quangkhanh", "123456", "Nguyễn Quang Khánh",
                        "quangkhanh@gmail.com", "0923456789", Role.CUSTOMER));

        users.put("ngoctuan",
                new User("ngoctuan", "123456", "Mai Ngọc Tuấn",
                        "ngoctuan@gmail.com", "0934567890", Role.CUSTOMER));

        users.put("tuananh",
                new User("tuananh", "123456", "Nguyễn Trọng Tuấn Anh",
                        "tuananh@gmail.com", "0945678901", Role.CUSTOMER));

        products.put("SP01",
                new Product("SP01", "Laptop Dell Inspiron", 15000000, 10));

        products.put("SP02",
                new Product("SP02", "Chuột Logitech", 300000, 50));

        products.put("SP03",
                new Product("SP03", "Bàn phím cơ AKKO", 1250000, 20));

        products.put("SP04",
                new Product("SP04", "Màn hình Samsung 24 inch", 2800000, 12));

        products.put("SP05",
                new Product("SP05", "Tai nghe Bluetooth", 650000, 30));

        products.put("SP06",
                new Product("SP06", "Ổ cứng SSD Kingston", 1200000, 15));

        products.put("SP07",
                new Product("SP07", "Loa Bluetooth JBL", 2200000, 0));

        products.put("SP08",
                new Product("SP08", "Webcam Logitech C920", 1900000, 10));

        products.put("SP09",
                new Product("SP09", "RAM Kingston 16GB", 1500000, 25));

        products.put("SP10",
                new Product("SP10", "USB SanDisk 64GB", 250000, 40));

        products.put("SP11",
                new Product("SP11", "Bộ phát WiFi TP-Link", 900000, 18));

        products.put("SP12",
                new Product("SP12", "Máy in Canon LBP2900", 4200000, 7));

        products.put("SP13",
                new Product("SP13", "Laptop Asus VivoBook", 16500000, 8));

        products.put("SP14",
                new Product("SP14", "Ổ cứng HDD Seagate 1TB", 1100000, 14));

        products.put("SP15",
                new Product("SP15", "Micro thu âm HyperX", 2450000, 0));
    }

    public User login(String username, String password) {
        String key = normalize(username).toLowerCase();
        User user = users.get(key);
        if (user == null || !user.checkPassword(password)) {
            throw new IllegalArgumentException("Sai tên đăng nhập hoặc mật khẩu");
        }
        return user;
    }


    public User registerCustomer(String username, String password,
                                 String confirmPassword, String fullName,
                                 String email, String phone) {
        String key = normalize(username).toLowerCase();
        String normalizedName = normalize(fullName);
        String normalizedEmail = normalize(email).toLowerCase();
        String normalizedPhone = normalize(phone);

        if (!key.matches("[a-z0-9_]{4,20}")) {
            throw new IllegalArgumentException(
                    "Tên đăng nhập phải có 4-20 ký tự, chỉ gồm chữ, số hoặc dấu gạch dưới"
            );
        }
        if (users.containsKey(key)) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }
        if (normalizedName.length() < 2) {
            throw new IllegalArgumentException("Họ tên phải có ít nhất 2 ký tự");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
        }
        if (!normalizedEmail.matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (!normalizedPhone.matches("^0\\d{9}$")) {
            throw new IllegalArgumentException(
                    "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng 0"
            );
        }

        boolean emailExists = users.values().stream()
                .anyMatch(user -> normalizedEmail.equalsIgnoreCase(user.getEmail()));
        if (emailExists) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }

        User customer = new User(
                key, password, normalizedName,
                normalizedEmail, normalizedPhone, Role.CUSTOMER
        );
        users.put(key, customer);
        return customer;
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    public void addProduct(Product product) {
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Mã sản phẩm đã tồn tại");
        }
        products.put(product.getId(), product);
    }

    public void updateProduct(String id, String name, double price, int quantity) {
        Product product = findProduct(id);
        if (product == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
    }

    public void deleteProduct(String id) {
        Product product = findProduct(id);
        if (product == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        products.remove(product.getId());
    }

    public Product findProduct(String id) {
        if (id == null) {
            return null;
        }
        return products.get(id.trim().toUpperCase());
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.matches(keyword)) {
                result.add(product);
            }
        }
        return result;
    }

    public Order checkout(User customer, List<CartItem> cartItems, String receiverName, String phone, String address, String paymentMethod) {
        if (customer == null) {
            throw new IllegalArgumentException("Khach hang khong hop le");
        }
        if (receiverName == null || receiverName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ho ten nguoi nhan khong duoc de trong");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("So dien thoai khong duoc de trong");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Dia chi khong duoc de trong");
        }
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng đang trống");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            product.reduceQuantity(item.getQuantity());
            orderItems.add(new OrderItem(product.getId(), product.getName(), product.getPrice(), item.getQuantity()));
        }

        String orderId = String.format("DH%03d", orders.size() + 1);
        Order order = new Order(orderId, customer, receiverName, phone, address, paymentMethod, orderItems);
        orders.add(order);
        return order;
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        Order order = findOrder(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Không tìm thấy đơn hàng");
        }
        order.setStatus(status);
    }

    public Order findOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public List<Order> getOrdersFor(User user) {
        if (user.getRole() == Role.ADMIN) {
            return new ArrayList<>(orders);
        }

        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer().getUsername().equals(user.getUsername())) {
                result.add(order);
            }
        }
        return result;
    }

    public int getTotalProductTypes() {
        return products.size();
    }

    public int getTotalStock() {
        int total = 0;
        for (Product product : products.values()) {
            total += product.getQuantity();
        }
        return total;
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Order order : orders) {
            if (order.getStatus() != OrderStatus.DA_HUY) {
                total += order.getTotal();
            }
        }
        return total;
    }

    public int getTotalOrders() {
        return orders.size();
    }


    public List<Product> filterAvailableProducts() {
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getQuantity() > 0) {
                result.add(product);
            }
        }
        return result;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
