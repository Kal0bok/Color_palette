import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ColorPickerApp extends JFrame {
    private JLabel colorPreview;
    private JTextArea colorInfo;
    private JButton pickButton;
    private List<NamedColor> colorDatabase;

    public ColorPickerApp() {
        initColorDatabase();
        setTitle("Screen Color Picker");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pickButton = new JButton("Pick Color");
        pickButton.setFont(new Font("Arial", Font.BOLD, 14));
        pickButton.addActionListener(e -> startPicking());

        colorPreview = new JLabel("COLOR", SwingConstants.CENTER);
        colorPreview.setOpaque(true);
        colorPreview.setPreferredSize(new Dimension(150, 150));
        colorPreview.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        colorInfo = new JTextArea("Click the button and select a color on your screen");
        colorInfo.setEditable(false);
        colorInfo.setFont(new Font("Monospaced", Font.PLAIN, 13));
        colorInfo.setMargin(new Insets(10, 10, 10, 10));

        add(pickButton, BorderLayout.NORTH);
        add(colorPreview, BorderLayout.CENTER);
        add(new JScrollPane(colorInfo), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void initColorDatabase() {
        colorDatabase = new ArrayList<>();
        colorDatabase.add(new NamedColor("White", 255, 255, 255));
        colorDatabase.add(new NamedColor("Black", 0, 0, 0));
        colorDatabase.add(new NamedColor("Red", 255, 0, 0));
        colorDatabase.add(new NamedColor("Lime", 0, 255, 0));
        colorDatabase.add(new NamedColor("Blue", 0, 0, 255));
        colorDatabase.add(new NamedColor("Yellow", 255, 255, 0));
        colorDatabase.add(new NamedColor("Cyan", 0, 255, 255));
        colorDatabase.add(new NamedColor("Magenta", 255, 0, 255));
        colorDatabase.add(new NamedColor("Silver", 192, 192, 192));
        colorDatabase.add(new NamedColor("Gray", 128, 128, 128));
        colorDatabase.add(new NamedColor("Maroon", 128, 0, 0));
        colorDatabase.add(new NamedColor("Olive", 128, 128, 0));
        colorDatabase.add(new NamedColor("Green", 0, 128, 0));
        colorDatabase.add(new NamedColor("Purple", 128, 0, 128));
        colorDatabase.add(new NamedColor("Teal", 0, 128, 128));
        colorDatabase.add(new NamedColor("Navy", 0, 0, 128));
        colorDatabase.add(new NamedColor("Estate Olive", 76, 69, 44));
        colorDatabase.add(new NamedColor("Golden Grass", 218, 165, 32));
        colorDatabase.add(new NamedColor("Slate Gray", 112, 128, 144));
        colorDatabase.add(new NamedColor("Crimson", 220, 20, 60));
        colorDatabase.add(new NamedColor("Royal Blue", 65, 105, 225));
        colorDatabase.add(new NamedColor("Chocolate", 210, 105, 30));
        colorDatabase.add(new NamedColor("Coral", 255, 127, 80));
        colorDatabase.add(new NamedColor("Ivory", 255, 255, 240));
        colorDatabase.add(new NamedColor("Lavender", 230, 230, 250));
        colorDatabase.add(new NamedColor("Turquoise", 64, 224, 208));
        colorDatabase.add(new NamedColor("Gold", 255, 215, 0));
        colorDatabase.add(new NamedColor("Indigo", 75, 0, 130));
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
                    Color pickedColor = new Color(rgb, true);
                    updateUI(pickedColor);
                    captureWindow.dispose();
                    setVisible(true);
                    toFront();
                }
            });

            captureWindow.setVisible(true);
            captureWindow.setAlwaysOnTop(true);
            captureWindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(this, "Screen capture error");
        }
    }

    private void updateUI(Color c) {
        colorPreview.setBackground(c);
        colorPreview.setForeground(new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue()));

        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();

        String hex = String.format("#%02X%02X%02X", r, g, b);
        String rgb = String.format("RGB:  %d, %d, %d", r, g, b);
        String rgba = String.format("RGBA: %d, %d, %d, %d", r, g, b, a);
        
        float[] hsb = Color.RGBtoHSB(r, g, b, null);
        String hslText = String.format("HSL:  %.0f°, %.0f%%, %.0f%%", 
                         hsb[0] * 360, hsb[1] * 100, hsb[2] * 100);

        String name = findClosestName(c);

        colorInfo.setText(String.format(
            "Name:      %s\n" +
            "HEX:       %s\n" +
            "%s\n" +
            "%s\n" +
            "%s", 
            name, hex, rgb, rgba, hslText
        ));
    }

    private String findClosestName(Color color) {
        String closestName = "Unknown";
        double minDistance = Double.MAX_VALUE;

        for (NamedColor nc : colorDatabase) {
            double distance = Math.sqrt(
                Math.pow(color.getRed() - nc.r, 2) +
                Math.pow(color.getGreen() - nc.g, 2) +
                Math.pow(color.getBlue() - nc.b, 2)
            );
            if (distance < minDistance) {
                minDistance = distance;
                closestName = nc.name;
            }
        }
        return closestName;
    }

    private static class NamedColor {
        String name;
        int r, g, b;
        NamedColor(String name, int r, int g, int b) {
            this.name = name; this.r = r; this.g = g; this.b = b;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorPickerApp().setVisible(true));
    }
}