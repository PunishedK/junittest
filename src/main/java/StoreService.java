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
        users.put("admin", new User("admin", "123456", "Quản trị viên", Role.ADMIN));
        users.put("khach", new User("khach", "123", "Khách hàng mẫu", Role.CUSTOMER));

        products.put("SP01", new Product("SP01", "Laptop Dell Inspiron", 15000000, 10));
        products.put("SP02", new Product("SP02", "Chuột Logitech", 300000, 50));
        products.put("SP03", new Product("SP03", "Bàn phím cơ AKKO", 1250000, 20));
        products.put("SP04", new Product("SP04", "Màn hình Samsung 24 inch", 2800000, 12));
        products.put("SP05", new Product("SP05", "Tai nghe Bluetooth", 650000, 30));
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user == null || !user.checkPassword(password)) {
            throw new IllegalArgumentException("Sai tên đăng nhập hoặc mật khẩu");
        }
        return user;
    }

    public void registerCustomer(String username, String password, String fullName) {
        String key = username.trim();
        if (users.containsKey(key)) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }
        users.put(key, new User(key, password, fullName, Role.CUSTOMER));
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
