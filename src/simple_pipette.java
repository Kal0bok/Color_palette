import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ColorPickerApp extends JFrame {
    protected JLabel colorPreview;
    protected JTextArea colorInfo;
    protected JButton pickButton;

    public ColorPickerApp() {
        setTitle("Java Color Picker");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        colorPreview = new JLabel(" ", SwingConstants.CENTER);
        colorPreview.setOpaque(true);
        colorPreview.setPreferredSize(new Dimension(100, 100));
        colorPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        colorInfo = new JTextArea("Press button to start...");
        colorInfo.setEditable(false);

        pickButton = new JButton("Pick pipette");

        add(pickButton, BorderLayout.NORTH);
        add(colorPreview, BorderLayout.CENTER);
        add(new JScrollPane(colorInfo), BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorPickerApp().setVisible(true));
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
                    Color pickedColor = new Color(screenShot.getRGB(e.getX(), e.getY()));
                    updateUI(pickedColor);
                    captureWindow.dispose();
                }
            });

            captureWindow.setVisible(true);
            captureWindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка захвата экрана");
        }
    }
    
    private void updateUI(Color c) {
        colorPreview.setBackground(c);
        
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();

        String hex = String.format("#%02X%02X%02X", r, g, b);
        String rgb = String.format("RGB: %d, %d, %d", r, g, b);
        String rgba = String.format("RGBA: %d, %d, %d, %d", r, g, b, a);
        
        float[] hsb = Color.RGBtoHSB(r, g, b, null);
        String hslText = String.format("HSL (HSB): %.0f°, %.0f%%, %.0f%%", 
                         hsb[0] * 360, hsb[1] * 100, hsb[2] * 100);

        colorInfo.setText(hex + "\n" + rgb + "\n" + rgba + "\n" + hslText);
    }
}