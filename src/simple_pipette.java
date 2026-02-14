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

        colorInfo = new JTextArea("Нажмите кнопку, чтобы начать...");
        colorInfo.setEditable(false);

        pickButton = new JButton("Взять пипетку");

        add(pickButton, BorderLayout.NORTH);
        add(colorPreview, BorderLayout.CENTER);
        add(new JScrollPane(colorInfo), BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorPickerApp().setVisible(true));
    }
}