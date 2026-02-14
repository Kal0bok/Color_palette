import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ColorPickerApp extends JFrame {
    private CirclePanel colorCircle;
    private JTextArea colorInfo;
    private JButton pickButton;
    private List<NamedColor> colorDatabase;

    private final Color BACKGROUND_COLOR = new Color(30, 30, 35);
    private final Color ACCENT_COLOR = new Color(70, 130, 255);
    private final Color TEXT_COLOR = new Color(220, 220, 220);

    public ColorPickerApp() {
        initColorDatabase();
        setTitle("Modern Picker Pro");
        setSize(400, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        pickButton = createStyledButton("PICK SCREEN COLOR");
        pickButton.addActionListener(e -> startPicking());
        topPanel.add(pickButton);

        colorCircle = new CirclePanel();
        colorCircle.setBackground(BACKGROUND_COLOR);

        colorInfo = new JTextArea("Click button to start");
        colorInfo.setEditable(false);
        colorInfo.setFont(new Font("Monospaced", Font.BOLD, 13));
        colorInfo.setForeground(TEXT_COLOR);
        colorInfo.setBackground(new Color(45, 45, 50));
        colorInfo.setMargin(new Insets(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(colorInfo);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 65), 1));
        scrollPane.setPreferredSize(new Dimension(340, 180));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(colorCircle, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void updateUI(Color c) {
        colorCircle.setColor(c);
        
        String hex = String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
        String hexa = String.format("#%02X%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        String rgb = String.format("RGB:  %d, %d, %d", c.getRed(), c.getGreen(), c.getBlue());
        String rgba = String.format("RGBA: %d, %d, %d, %.2f", c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha() / 255.0f);
        
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        String hslString = String.format("HSL:  %.0f°, %.0f%%, %.0f%%", hsb[0] * 360, hsb[1] * 100, hsb[2] * 100);

        String name = findClosestName(c);

        colorInfo.setText(String.format(
            "NAME: %s\n" +
            "------------\n"+
            "HEX:  %s\n" +
            "HEXA: %s\n" +
            "%s\n" +
            "%s\n" +
            "%s", 
            name.toUpperCase(), hex, hexa, rgb, rgba, hslString
        ));
    }

    class CirclePanel extends JPanel {
        private Color currentColor = Color.GRAY;
        public void setColor(Color c) { this.currentColor = c; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int size = Math.min(getWidth(), getHeight()) - 40;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;
            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillOval(x + 3, y + 3, size, size);
            g2.setColor(currentColor);
            g2.fillOval(x, y, size, size);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(x, y, size, size);
        }
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(ACCENT_COLOR.darker());
                else if (getModel().isRollover()) g2.setColor(ACCENT_COLOR.brighter());
                else g2.setColor(ACCENT_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(220, 45));
        return btn;
    }

    private void initColorDatabase() {
        colorDatabase = new ArrayList<>();
        colorDatabase.add(new NamedColor("White", 255, 255, 255));
        colorDatabase.add(new NamedColor("Black", 0, 0, 0));
        colorDatabase.add(new NamedColor("Royal Blue", 65, 105, 225));
        colorDatabase.add(new NamedColor("Crimson", 220, 20, 60));
        colorDatabase.add(new NamedColor("Emerald Green", 80, 200, 120));
        colorDatabase.add(new NamedColor("Gold", 255, 215, 0));
    }

    private void startPicking() {
        try {
            Robot robot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(screenSize));
            JWindow captureWindow = new JWindow();
            captureWindow.setSize(screenSize);
            captureWindow.add(new JLabel(new ImageIcon(screenShot)));
            captureWindow.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int rgb = screenShot.getRGB(e.getX(), e.getY());
                    updateUI(new Color(rgb, true));
                    captureWindow.dispose();
                }
            });
            captureWindow.setVisible(true);
            captureWindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    private String findClosestName(Color color) {
        String closestName = "Unknown";
        double minDistance = Double.MAX_VALUE;
        for (NamedColor nc : colorDatabase) {
            double distance = Math.sqrt(Math.pow(color.getRed()-nc.r, 2) + 
                              Math.pow(color.getGreen()-nc.g, 2) + Math.pow(color.getBlue()-nc.b, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestName = nc.name;
            }
        }
        return closestName;
    }

    private static class NamedColor {
        String name; int r, g, b;
        NamedColor(String n, int r, int g, int b) { this.name = n; this.r = r; this.g = g; this.b = b; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorPickerApp().setVisible(true));
    }
}