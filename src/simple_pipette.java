import javax.swing.*;
import java.awt.*;

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
}