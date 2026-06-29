import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private final StoreService service = new StoreService();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel root = new JPanel(cardLayout);

    private User currentUser;
    private final List<CartItem> cart = new ArrayList<>();

    private JTextField loginUsername;
    private JPasswordField loginPassword;
    private JTextField registerFullName;
    private JTextField registerUsername;
    private JTextField registerEmail;
    private JTextField registerPhone;
    private JPasswordField registerPassword;
    private JPasswordField registerConfirmPassword;

    private DefaultTableModel adminProductModel;
    private JTable adminProductTable;
    private JTextField adminSearch;
    private JTextField txtProductId;
    private JTextField txtProductName;
    private JTextField txtProductPrice;
    private JTextField txtProductQuantity;
    private JLabel adminStats;

    private DefaultTableModel adminOrderModel;
    private JTable adminOrderTable;
    private JComboBox<String> cboOrderStatus;

    private DefaultTableModel customerProductModel;
    private JTable customerProductTable;
    private JTextField customerSearch;
    private JTextField txtBuyQuantity;
    private JLabel cartBadge;

    private DefaultTableModel cartModel;
    private JTable cartTable;
    private JLabel cartTotalLabel;
    private JTextField txtReceiverName;
    private JTextField txtPhone;
    private JTextArea txtAddress;
    private JComboBox<String> cboPayment;

    private DefaultTableModel customerOrderModel;
    private JTable customerOrderTable;

    public MainFrame() {
        setTitle("Hệ Thống Quản Lý Bán Hàng");
        setSize(1180, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Ui.BG);

        root.setBackground(Ui.BG);
        root.add(loginPanel(), "login");
        root.add(registerPanel(), "register");
        add(root);

        showLogin();
    }

    private JPanel page() {
        JPanel page = new JPanel(new BorderLayout(12, 12));
        page.setBackground(Ui.BG);
        page.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        return page;
    }

    private JPanel nav(String title) {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Ui.PANEL);
        nav.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JLabel lblTitle = Ui.title("🛒 " + title);
        JLabel user = Ui.small(currentUser == null ? "" : "Xin chào, " + currentUser.getFullName() + "  •  " + (currentUser.getRole() == Role.ADMIN ? "Admin" : "Khách hàng"));

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        left.add(lblTitle);
        left.add(user);

        JButton logout = Ui.button("Đăng xuất", Ui.DANGER);
        logout.addActionListener(e -> logout());

        nav.add(left, BorderLayout.WEST);
        nav.add(logout, BorderLayout.EAST);
        return nav;
    }

    private JPanel loginPanel() {
        JPanel page = page();

        JPanel card = Ui.panel();
        card.setLayout(new GridBagLayout());

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 10));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(430, 360));

        JLabel title = Ui.title("Đăng Nhập Hệ Thống");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        loginUsername = Ui.input();
        loginPassword = Ui.password();

        JButton btnLogin = Ui.button("Đăng nhập", Ui.PRIMARY);
        JButton btnRegister = Ui.button("Đăng ký tài khoản khách", Ui.SUCCESS);

        form.add(title);
        form.add(Ui.small("Tài khoản admin: admin / 123456"));
        form.add(label("Tên đăng nhập"));
        form.add(loginUsername);
        form.add(label("Mật khẩu"));
        form.add(loginPassword);
        form.add(btnLogin);
        form.add(btnRegister);

        card.add(form);
        page.add(card, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> showRegister());

        return page;
    }

    private JPanel registerPanel() {
        JPanel page = page();

        JPanel card = Ui.panel();
        card.setLayout(new GridBagLayout());

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 10));
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(460, 630));

        JLabel title = Ui.title("Đăng Ký Khách Hàng");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        registerFullName = Ui.input();
        registerUsername = Ui.input();
        registerEmail = Ui.input();
        registerPhone = Ui.input();
        registerPassword = Ui.password();
        registerConfirmPassword = Ui.password();

        JButton btnRegister = Ui.button("Tạo tài khoản", Ui.SUCCESS);
        JButton btnBack = Ui.button("Quay lại đăng nhập", Ui.WARNING);

        form.add(title);
        form.add(label("Họ tên"));
        form.add(registerFullName);
        form.add(label("Tên đăng nhập"));
        form.add(registerUsername);
        form.add(label("Email"));
        form.add(registerEmail);
        form.add(label("Số điện thoại"));
        form.add(registerPhone);
        form.add(label("Mật khẩu"));
        form.add(registerPassword);
        form.add(label("Xác nhận mật khẩu"));
        form.add(registerConfirmPassword);
        form.add(btnRegister);
        form.add(btnBack);

        card.add(form);
        page.add(card, BorderLayout.CENTER);

        btnRegister.addActionListener(e -> register());
        btnBack.addActionListener(e -> showLogin());

        return page;
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Ui.MUTED);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return label;
    }

    private JTextField searchInput(String placeholder) {
        JTextField field = Ui.input();
        field.setEditable(true);
        field.setEnabled(true);
        field.setFocusable(true);
        field.setColumns(24);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                field.requestFocusInWindow();
            }
        });
        return field;
    }

    private void login() {
        try {
            currentUser = service.login(loginUsername.getText().trim(), new String(loginPassword.getPassword()));
            cart.clear();

            if (currentUser.getRole() == Role.ADMIN) {
                root.add(adminPanel(), "admin");
                showAdmin();
            } else {
                root.add(customerPanel(), "customer");
                showCustomer();
            }
            Ui.message(this, "Đăng nhập thành công!");
        } catch (IllegalArgumentException ex) {
            Ui.message(this, ex.getMessage());
        }
    }

    private void register() {
        try {
            String username = registerUsername.getText().trim();
            service.registerCustomer(
                    username,
                    new String(registerPassword.getPassword()),
                    new String(registerConfirmPassword.getPassword()),
                    registerFullName.getText().trim(),
                    registerEmail.getText().trim(),
                    registerPhone.getText().trim()
            );
            Ui.message(this, "Đăng ký thành công! Vui lòng đăng nhập.");
            showLogin();
            loginUsername.setText(username);
            loginPassword.requestFocusInWindow();
        } catch (IllegalArgumentException ex) {
            Ui.message(this, ex.getMessage());
        }
    }

    private JPanel adminPanel() {
        JPanel page = page();
        page.add(nav("Trang Quản Trị"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(Ui.BG);
        tabs.setForeground(Ui.TEXT);
        tabs.addTab("Sản phẩm", adminProductsPanel());
        tabs.addTab("Đơn hàng", adminOrdersPanel());

        page.add(tabs, BorderLayout.CENTER);
        return page;
    }

    private JPanel adminProductsPanel() {
        JPanel panel = Ui.panel();
        panel.setLayout(new BorderLayout(12, 12));

        JPanel top = new JPanel(new BorderLayout(12, 12));
        top.setOpaque(false);

        adminStats = Ui.title("");
        adminStats.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.setOpaque(false);
        adminSearch = searchInput("Tìm kiếm sản phẩm...");
        JButton btnSearch = Ui.button("Tìm kiếm", Ui.PRIMARY);
        searchPanel.add(adminSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        top.add(adminStats, BorderLayout.WEST);
        top.add(searchPanel, BorderLayout.EAST);

        adminProductModel = new DefaultTableModel(new Object[]{"Mã SP", "Tên sản phẩm", "Giá bán", "Tồn kho", "Tình trạng"}, 0);
        adminProductTable = Ui.table();
        adminProductTable.setModel(adminProductModel);

        JPanel form = new JPanel(new GridLayout(2, 5, 8, 8));
        form.setOpaque(false);
        txtProductId = Ui.input();
        txtProductName = Ui.input();
        txtProductPrice = Ui.input();
        txtProductQuantity = Ui.input();

        JButton btnAdd = Ui.button("Thêm", Ui.SUCCESS);
        JButton btnUpdate = Ui.button("Sửa", Ui.WARNING);
        JButton btnDelete = Ui.button("Xóa", Ui.DANGER);
        JButton btnClear = Ui.button("Làm mới", Ui.PRIMARY);

        form.add(label("Mã SP"));
        form.add(label("Tên sản phẩm"));
        form.add(label("Giá"));
        form.add(label("Số lượng"));
        form.add(new JLabel(""));
        form.add(txtProductId);
        form.add(txtProductName);
        form.add(txtProductPrice);
        form.add(txtProductQuantity);

        JPanel btns = new JPanel(new GridLayout(1, 4, 6, 6));
        btns.setOpaque(false);
        btns.add(btnAdd);
        btns.add(btnUpdate);
        btns.add(btnDelete);
        btns.add(btnClear);
        form.add(btns);

        panel.add(top, BorderLayout.NORTH);
        panel.add(Ui.scroll(adminProductTable), BorderLayout.CENTER);
        panel.add(form, BorderLayout.SOUTH);

        btnSearch.addActionListener(e -> loadAdminProducts());
        adminSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { loadAdminProducts(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { loadAdminProducts(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { loadAdminProducts(); }
        });
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearProductForm());

        adminProductTable.getSelectionModel().addListSelectionListener(e -> fillProductForm());

        return panel;
    }

    private JPanel adminOrdersPanel() {
        JPanel panel = Ui.panel();
        panel.setLayout(new BorderLayout(12, 12));

        adminOrderModel = new DefaultTableModel(new Object[]{"Mã đơn", "Tài khoản", "Người nhận", "SĐT", "Tổng tiền", "Ngày đặt", "Trạng thái"}, 0);
        adminOrderTable = Ui.table();
        adminOrderTable.setModel(adminOrderModel);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        cboOrderStatus = Ui.combo(new String[]{"Chờ xử lý", "Đang giao", "Hoàn thành", "Đã hủy"});
        JButton btnUpdate = Ui.button("Cập nhật trạng thái", Ui.PRIMARY);
        bottom.add(label("Trạng thái:"));
        bottom.add(cboOrderStatus);
        bottom.add(btnUpdate);

        panel.add(Ui.scroll(adminOrderTable), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        btnUpdate.addActionListener(e -> updateOrderStatus());
        return panel;
    }

    private JPanel customerPanel() {
        JPanel page = page();
        page.add(nav("Cửa Hàng"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(Ui.BG);
        tabs.setForeground(Ui.TEXT);
        tabs.addTab("Mua hàng", customerProductsPanel());
        tabs.addTab("Giỏ hàng & Thanh toán", cartPanel());
        tabs.addTab("Đơn hàng của tôi", customerOrdersPanel());

        tabs.addChangeListener(e -> {
            loadCart();
            loadCustomerOrders();
        });

        page.add(tabs, BorderLayout.CENTER);
        return page;
    }

    private JPanel customerProductsPanel() {
        JPanel panel = Ui.panel();
        panel.setLayout(new BorderLayout(12, 12));

        JPanel top = new JPanel(new BorderLayout(12, 12));
        top.setOpaque(false);

        customerSearch = searchInput("Tìm kiếm sản phẩm...");
        JButton btnSearch = Ui.button("Tìm kiếm", Ui.PRIMARY);
        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.setOpaque(false);
        searchPanel.add(customerSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        cartBadge = Ui.title("Giỏ hàng: 0 sản phẩm");
        cartBadge.setFont(new Font("Segoe UI", Font.BOLD, 16));

        top.add(searchPanel, BorderLayout.CENTER);
        top.add(cartBadge, BorderLayout.EAST);

        customerProductModel = new DefaultTableModel(new Object[]{"Mã SP", "Tên sản phẩm", "Giá bán", "Còn lại", "Tình trạng"}, 0);
        customerProductTable = Ui.table();
        customerProductTable.setModel(customerProductModel);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        txtBuyQuantity = Ui.input();
        txtBuyQuantity.setPreferredSize(new Dimension(80, 36));
        JButton btnAddCart = Ui.button("Thêm vào giỏ", Ui.SUCCESS);
        bottom.add(label("Số lượng mua:"));
        bottom.add(txtBuyQuantity);
        bottom.add(btnAddCart);

        panel.add(top, BorderLayout.NORTH);
        panel.add(Ui.scroll(customerProductTable), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        btnSearch.addActionListener(e -> loadCustomerProducts());
        customerSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { loadCustomerProducts(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { loadCustomerProducts(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { loadCustomerProducts(); }
        });
        btnAddCart.addActionListener(e -> addToCart());

        return panel;
    }

    private JPanel cartPanel() {
        JPanel panel = Ui.panel();
        panel.setLayout(new BorderLayout(12, 12));

        cartModel = new DefaultTableModel(new Object[]{"Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"}, 0);
        cartTable = Ui.table();
        cartTable.setModel(cartModel);

        JPanel checkout = new JPanel(new GridLayout(0, 2, 10, 10));
        checkout.setOpaque(false);

        txtReceiverName = Ui.input();
        txtPhone = Ui.input();
        txtAddress = new JTextArea(3, 20);
        txtAddress.setBackground(Ui.PANEL_2);
        txtAddress.setForeground(Ui.TEXT);
        txtAddress.setCaretColor(Ui.TEXT);
        txtAddress.setBorder(BorderFactory.createLineBorder(new Color(60, 70, 95)));

        cboPayment = Ui.combo(new String[]{"Thanh toán khi nhận hàng (COD)", "Chuyển khoản ngân hàng"});

        cartTotalLabel = Ui.title("Tổng: 0 đ");
        cartTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton btnRemove = Ui.button("Xóa khỏi giỏ", Ui.DANGER);
        JButton btnCheckout = Ui.button("Xác nhận đặt hàng", Ui.SUCCESS);

        checkout.add(label("Họ tên người nhận"));
        checkout.add(txtReceiverName);
        checkout.add(label("Số điện thoại"));
        checkout.add(txtPhone);
        checkout.add(label("Địa chỉ nhận hàng"));
        checkout.add(new JScrollPane(txtAddress));
        checkout.add(label("Phương thức thanh toán"));
        checkout.add(cboPayment);

        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.setOpaque(false);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setOpaque(false);
        actions.add(cartTotalLabel);
        actions.add(btnRemove);
        actions.add(btnCheckout);

        bottom.add(checkout, BorderLayout.CENTER);
        bottom.add(actions, BorderLayout.SOUTH);

        panel.add(Ui.scroll(cartTable), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        btnRemove.addActionListener(e -> removeFromCart());
        btnCheckout.addActionListener(e -> checkout());

        return panel;
    }

    private JPanel customerOrdersPanel() {
        JPanel panel = Ui.panel();
        panel.setLayout(new BorderLayout(12, 12));

        customerOrderModel = new DefaultTableModel(new Object[]{"Mã đơn", "Người nhận", "SĐT", "Tổng tiền", "Ngày đặt", "Thanh toán", "Trạng thái"}, 0);
        customerOrderTable = Ui.table();
        customerOrderTable.setModel(customerOrderModel);

        panel.add(Ui.scroll(customerOrderTable), BorderLayout.CENTER);
        return panel;
    }

    private void loadAdminProducts() {
        if (adminProductModel == null) {
            return;
        }
        adminProductModel.setRowCount(0);
        for (Product p : service.searchProducts(adminSearch == null ? "" : adminSearch.getText())) {
            adminProductModel.addRow(new Object[]{
                    p.getId(), p.getName(), Ui.money(p.getPrice()), p.getQuantity(), p.getQuantity() > 0 ? "Còn hàng" : "Hết hàng"
            });
        }
        adminStats.setText("Tổng SP: " + service.getTotalProductTypes() + "   |   Tồn kho: " + service.getTotalStock()
                + "   |   Đơn hàng: " + service.getTotalOrders() + "   |   Doanh thu: " + Ui.money(service.getTotalRevenue()));
        loadAdminOrders();
    }

    private void loadAdminOrders() {
        if (adminOrderModel == null) {
            return;
        }
        adminOrderModel.setRowCount(0);
        for (Order order : service.getOrders()) {
            adminOrderModel.addRow(new Object[]{
                    order.getOrderId(),
                    order.getCustomer().getUsername(),
                    order.getReceiverName(),
                    order.getPhone(),
                    Ui.money(order.getTotal()),
                    order.getDate(),
                    order.getStatus().getText()
            });
        }
    }

    private void loadCustomerProducts() {
        if (customerProductModel == null) {
            return;
        }
        customerProductModel.setRowCount(0);
        for (Product p : service.searchProducts(customerSearch == null ? "" : customerSearch.getText())) {
            customerProductModel.addRow(new Object[]{
                    p.getId(), p.getName(), Ui.money(p.getPrice()), p.getQuantity(), p.getQuantity() > 0 ? "Còn hàng" : "Hết hàng"
            });
        }
        updateCartBadge();
    }

    private void loadCart() {
        if (cartModel == null) {
            return;
        }
        cartModel.setRowCount(0);
        double total = 0;
        for (CartItem item : cart) {
            cartModel.addRow(new Object[]{
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    Ui.money(item.getProduct().getPrice()),
                    item.getQuantity(),
                    Ui.money(item.getSubTotal())
            });
            total += item.getSubTotal();
        }

        double discount = total >= 1000000 ? total * 0.1 : 0;
        cartTotalLabel.setText("Tổng: " + Ui.money(total - discount) + "  (Giảm: " + Ui.money(discount) + ")");
        updateCartBadge();
    }

    private void loadCustomerOrders() {
        if (customerOrderModel == null || currentUser == null) {
            return;
        }
        customerOrderModel.setRowCount(0);
        for (Order order : service.getOrdersFor(currentUser)) {
            customerOrderModel.addRow(new Object[]{
                    order.getOrderId(),
                    order.getReceiverName(),
                    order.getPhone(),
                    Ui.money(order.getTotal()),
                    order.getDate(),
                    order.getPaymentMethod(),
                    order.getStatus().getText()
            });
        }
    }

    private void fillProductForm() {
        int row = adminProductTable.getSelectedRow();
        if (row >= 0) {
            txtProductId.setText(adminProductModel.getValueAt(row, 0).toString());
            txtProductName.setText(adminProductModel.getValueAt(row, 1).toString());
            Product p = service.findProduct(txtProductId.getText());
            if (p != null) {
                txtProductPrice.setText(String.valueOf((long) p.getPrice()));
                txtProductQuantity.setText(String.valueOf(p.getQuantity()));
            }
            txtProductId.setEditable(false);
        }
    }

    private void addProduct() {
        try {
            service.addProduct(new Product(
                    txtProductId.getText(),
                    txtProductName.getText(),
                    Double.parseDouble(txtProductPrice.getText()),
                    Integer.parseInt(txtProductQuantity.getText())
            ));
            clearProductForm();
            loadAdminProducts();
            Ui.message(this, "Thêm sản phẩm thành công!");
        } catch (NumberFormatException ex) {
            Ui.message(this, "Giá và số lượng phải là số");
        } catch (IllegalArgumentException ex) {
            Ui.message(this, ex.getMessage());
        }
    }

    private void updateProduct() {
        try {
            service.updateProduct(
                    txtProductId.getText(),
                    txtProductName.getText(),
                    Double.parseDouble(txtProductPrice.getText()),
                    Integer.parseInt(txtProductQuantity.getText())
            );
            clearProductForm();
            loadAdminProducts();
            Ui.message(this, "Sửa sản phẩm thành công!");
        } catch (NumberFormatException ex) {
            Ui.message(this, "Giá và số lượng phải là số");
        } catch (IllegalArgumentException ex) {
            Ui.message(this, ex.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteProduct(txtProductId.getText());
                clearProductForm();
                loadAdminProducts();
                Ui.message(this, "Xóa sản phẩm thành công!");
            }
        } catch (IllegalArgumentException ex) {
            Ui.message(this, ex.getMessage());
        }
    }

    private void clearProductForm() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtProductPrice.setText("");
        txtProductQuantity.setText("");
        txtProductId.setEditable(true);
        adminProductTable.clearSelection();
    }

    private void updateOrderStatus() {
        int row = adminOrderTable.getSelectedRow();
        if (row < 0) {
            Ui.message(this, "Vui lòng chọn đơn hàng");
            return;
        }
        String orderId = adminOrderModel.getValueAt(row, 0).toString();
        service.updateOrderStatus(orderId, OrderStatus.fromText(cboOrderStatus.getSelectedItem().toString()));
        loadAdminOrders();
        loadAdminProducts();
        Ui.message(this, "Đã cập nhật trạng thái đơn hàng!");
    }

    private void addToCart() {
        try {
            int row = customerProductTable.getSelectedRow();
            if (row < 0) {
                Ui.message(this, "Vui lòng chọn sản phẩm");
                return;
            }

            String productId = customerProductModel.getValueAt(row, 0).toString();
            Product product = service.findProduct(productId);
            int quantity = Integer.parseInt(txtBuyQuantity.getText());

            if (quantity <= 0) {
                throw new IllegalArgumentException("Số lượng mua phải lớn hơn 0");
            }
            if (quantity > product.getQuantity()) {
                throw new IllegalArgumentException("Không đủ hàng trong kho");
            }

            CartItem existing = findCartItem(productId);
            if (existing == null) {
                cart.add(new CartItem(product, quantity));
            } else {
                if (existing.getQuantity() + quantity > product.getQuantity()) {
                    throw new IllegalArgumentException("Số lượng trong giỏ vượt quá tồn kho");
                }
                existing.addQuantity(quantity);
            }

            txtBuyQuantity.setText("");
            updateCartBadge();
            Ui.message(this, "Đã thêm sản phẩm vào giỏ hàng!");
        } catch (NumberFormatException ex) {
            Ui.message(this, "Số lượng mua phải là số");
        } catch (IllegalArgumentException ex) {
            Ui.message(this, ex.getMessage());
        }
    }

    private CartItem findCartItem(String productId) {
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    private void removeFromCart() {
        int row = cartTable.getSelectedRow();
        if (row < 0) {
            Ui.message(this, "Vui lòng chọn sản phẩm trong giỏ");
            return;
        }
        cart.remove(row);
        loadCart();
    }

    private void checkout() {
        try {
            Order order = service.checkout(
                    currentUser,
                    cart,
                    txtReceiverName.getText(),
                    txtPhone.getText(),
                    txtAddress.getText(),
                    cboPayment.getSelectedItem().toString()
            );
            cart.clear();
            txtReceiverName.setText("");
            fillCustomerInformation();
            txtPhone.setText("");
            txtAddress.setText("");

            loadCart();
            loadCustomerProducts();
            loadCustomerOrders();

            Ui.message(this, "Thanh toán thành công! Mã đơn: " + order.getOrderId()
                    + "\nTổng tiền: " + Ui.money(order.getTotal()));
        } catch (IllegalArgumentException ex) {
            Ui.message(this, ex.getMessage());
        }
    }

    private void updateCartBadge() {
        if (cartBadge == null) {
            return;
        }
        int count = 0;
        for (CartItem item : cart) {
            count += item.getQuantity();
        }
        cartBadge.setText("Giỏ hàng: " + count + " sản phẩm");
    }

    private void showLogin() {
        loginUsername.setText("");
        loginPassword.setText("");
        cardLayout.show(root, "login");
    }

    private void showRegister() {
        registerFullName.setText("");
        registerUsername.setText("");
        registerEmail.setText("");
        registerPhone.setText("");
        registerPassword.setText("");
        registerConfirmPassword.setText("");
        cardLayout.show(root, "register");
    }

    private void showAdmin() {
        cardLayout.show(root, "admin");
        loadAdminProducts();
        if (adminSearch != null) {
            SwingUtilities.invokeLater(() -> adminSearch.requestFocusInWindow());
        }
    }

    private void showCustomer() {
        cardLayout.show(root, "customer");
        fillCustomerInformation();
        loadCustomerProducts();
        if (customerSearch != null) {
            SwingUtilities.invokeLater(() -> customerSearch.requestFocusInWindow());
        }
        loadCart();
        loadCustomerOrders();
    }

    private void logout() {
        currentUser = null;
        cart.clear();
        showLogin();
    }

    private void fillCustomerInformation() {
        if (currentUser == null
                || txtReceiverName == null
                || txtPhone == null) {
            return;
        }
        txtReceiverName.setText(
                currentUser.getFullName()
        );
        txtPhone.setText(
                currentUser.getPhone()
        );
    }
}
