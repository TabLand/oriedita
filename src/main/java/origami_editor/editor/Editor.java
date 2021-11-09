package origami_editor.editor;

import javax.swing.*;
import java.awt.*;

/**
 * BorderLayout for each different part.
 */
public class Editor {
    private final App app;
    private JPanel root;
    private Canvas canvas;
    private RightPanel rightPanel;
    private BottomPanel bottomPanel;
    private TopPanel topPanel;
    private LeftPanel leftPanel;

    public Editor(App app, Canvas canvas) {
        this.app = app;
        this.canvas = canvas;
        $$$setupUI$$$();
    }

    private void createUIComponents() {
        rightPanel = new RightPanel(app, app.angleSystemModel);
        bottomPanel = new BottomPanel(app);
        topPanel = new TopPanel(app);
        leftPanel = new LeftPanel(app);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        root = new JPanel();
        root.setLayout(new BorderLayout(0, 0));
        root.add(bottomPanel.$$$getRootComponent$$$(), BorderLayout.SOUTH);
        root.add(rightPanel.$$$getRootComponent$$$(), BorderLayout.EAST);
        root.add(canvas, BorderLayout.CENTER);
        root.add(topPanel.$$$getRootComponent$$$(), BorderLayout.NORTH);
        root.add(leftPanel.$$$getRootComponent$$$(), BorderLayout.WEST);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }

    public BottomPanel getBottomPanel() {
        return bottomPanel;
    }

    public TopPanel getTopPanel() {
        return topPanel;
    }

    public LeftPanel getLeftPanel() {
        return leftPanel;
    }
}
