import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.DecimalFormat;

public class Ui {
    public static final Color BG = new Color(9, 13, 22);
    public static final Color PANEL = new Color(22, 28, 45);
    public static final Color PANEL_2 = new Color(30, 41, 59);
    public static final Color PRIMARY = new Color(99, 102, 241);
    public static final Color SUCCESS = new Color(16, 185, 129);
    public static final Color WARNING = new Color(245, 158, 11);
    public static final Color DANGER = new Color(244, 63, 94);
    public static final Color TEXT = new Color(248, 250, 252);
    public static final Color MUTED = new Color(148, 163, 184);

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###");

    public static String money(double value) {
        return MONEY_FORMAT.format(value) + " đ";
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        return label;
    }

    public static JLabel small(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }

    public static JPanel panel() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL);
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));
        return panel;
    }

    public static JButton button(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return button;
    }

    public static JTextField input() {
        JTextField field = new JTextField();
        field.setBackground(PANEL_2);
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 70, 95)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    public static JPasswordField password() {
        JPasswordField field = new JPasswordField();
        field.setBackground(PANEL_2);
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 70, 95)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    public static JComboBox<String> combo(String[] values) {
        JComboBox<String> combo = new JComboBox<>(values);
        combo.setBackground(PANEL_2);
        combo.setForeground(TEXT);
        return combo;
    }

    public static JTable table() {
        JTable table = new JTable();
        table.setRowHeight(36);
        table.setBackground(PANEL_2);
        table.setForeground(TEXT);
        table.setGridColor(new Color(45, 55, 75));
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(15, 23, 42));
        header.setForeground(MUTED);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(PANEL_2);
        renderer.setForeground(TEXT);
        table.setDefaultRenderer(Object.class, renderer);

        return table;
    }

    public static JScrollPane scroll(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.getViewport().setBackground(PANEL);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(45, 55, 75)));
        return scrollPane;
    }

    public static void message(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text);
    }
}
