package origami_editor.editor;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import origami.crease_pattern.element.LineColor;
import origami_editor.editor.component.ColorIcon;
import origami_editor.editor.databinding.*;
import origami_editor.editor.canvas.FoldLineAdditionalInputMode;
import origami_editor.tools.StringOp;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class RightPanel {
    private final App app;
    private final OpenFrame frame;
    private JCheckBox cAMVCheckBox;
    private JButton ck4_colorIncreaseButton;
    private JCheckBox ckTCheckBox;
    private JCheckBox ckOCheckBox;
    private JButton fxOButton;
    private JButton fxTButton;
    private JButton angleSystemAButton;
    private JButton ck4_colorDecreaseButton;
    private JButton angleSystemADecreaseButton;
    private JButton angleSystemAIncreaseButton;
    private JButton angleSystemBDecreaseButton;
    private JButton angleSystemBButton;
    private JButton angleSystemBIncreaseButton;
    private JTextField angleATextField;
    private JTextField angleCTextField;
    private JTextField angleBTextField;
    private JButton restrictedAngleABCSetButton;
    private JButton c_colButton;
    private JButton l1Button;
    private JTextField measuredLength1TextField;
    private JTextField measuredLength2TextField;
    private JButton ad_fncButton;
    private JTextField auxUndoTotalTextField;
    private JButton degButton;
    private JButton deg3Button;
    private JButton angleRestrictedButton;
    private JButton deg2Button;
    private JButton deg4Button;
    private JButton polygonSizeSetButton;
    private JTextField polygonSizeTextField;
    private JButton regularPolygonButton;
    private JButton circleDrawFreeButton;
    private JButton circleDrawButton;
    private JButton circleDrawSeparateButton;
    private JButton circleDrawConcentricButton;
    private JButton circleDrawConcentricSelectButton;
    private JButton circleDrawTwoConcentricButton;
    private JButton circleDrawTangentLineButton;
    private JButton circleDrawThreePointButton;
    private JButton circleDrawInvertedButton;
    private JButton sen_tokutyuu_color_henkouButton;
    private JButton h_undoButton;
    private JButton h_redoButton;
    private JButton h_senhaba_sageButton;
    private JButton h_senhaba_ageButton;
    private JButton h_senbun_nyuryokuButton;
    private JButton h_senbun_sakujyoButton;
    private JButton restrictedAngleSetDEFButton;
    private JTextField angleDTextField;
    private JTextField angleETextField;
    private JTextField angleFTextField;
    private JButton h_undoTotalSetButton;
    private JButton colOrangeButton;
    private JButton colYellowButton;
    private JButton l2Button;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JTextField measuredAngle1TextField;
    private JTextField measuredAngle2TextField;
    private JTextField measuredAngle3TextField;
    private JPanel root;

    public RightPanel(App app, AngleSystemModel angleSystemModel) {
        this.app = app;

        $$$setupUI$$$();

        app.registerButton(ck4_colorIncreaseButton, "ck4_colorIncreaseAction");
        app.registerButton(fxOButton, "fxOAction");
        app.registerButton(fxTButton, "fxTAction");
        app.registerButton(angleSystemAButton, "angleSystemAAction");
        app.registerButton(ck4_colorDecreaseButton, "ck4_colorDecreaseAction");
        app.registerButton(angleSystemADecreaseButton, "angleSystemADecreaseAction");
        app.registerButton(angleSystemAIncreaseButton, "angleSystemAIncreaseAction");
        app.registerButton(angleSystemBDecreaseButton, "angleSystemBDecreaseAction");
        app.registerButton(angleSystemBButton, "angleSystemBAction");
        app.registerButton(angleSystemBIncreaseButton, "angleSystemBIncreaseAction");
        app.registerButton(restrictedAngleABCSetButton, "restrictedAngleABCSetAction");
        app.registerButton(c_colButton, "c_colAction");
        app.registerButton(l1Button, "l1Action");
        app.registerButton(ad_fncButton, "ad_fncAction");
        app.registerButton(degButton, "degAction");
        app.registerButton(deg3Button, "deg3Action");
        app.registerButton(angleRestrictedButton, "angleRestrictedAction");
        app.registerButton(deg2Button, "deg2Action");
        app.registerButton(deg4Button, "deg4Action");
        app.registerButton(polygonSizeSetButton, "polygonSizeSetAction");
        app.registerButton(regularPolygonButton, "regularPolygonAction");
        app.registerButton(circleDrawFreeButton, "circleDrawFreeAction");
        app.registerButton(circleDrawButton, "circleDrawAction");
        app.registerButton(circleDrawSeparateButton, "circleDrawSeparateAction");
        app.registerButton(circleDrawConcentricButton, "circleDrawConcentricAction");
        app.registerButton(circleDrawConcentricSelectButton, "circleDrawConcentricSelectAction");
        app.registerButton(circleDrawTwoConcentricButton, "circleDrawTwoConcentricAction");
        app.registerButton(circleDrawTangentLineButton, "circleDrawTangentLineAction");
        app.registerButton(circleDrawThreePointButton, "circleDrawThreePointAction");
        app.registerButton(circleDrawInvertedButton, "circleDrawInvertedAction");
        app.registerButton(sen_tokutyuu_color_henkouButton, "sen_tokutyuu_color_henkouAction");
        app.registerButton(h_undoButton, "h_undoAction");
        app.registerButton(h_redoButton, "h_redoAction");
        app.registerButton(h_senhaba_sageButton, "h_senhaba_sageAction");
        app.registerButton(h_senhaba_ageButton, "h_senhaba_ageAction");
        app.registerButton(h_senbun_nyuryokuButton, "h_senbun_nyuryokuAction");
        app.registerButton(h_senbun_sakujyoButton, "h_senbun_sakujyoAction");
        app.registerButton(restrictedAngleSetDEFButton, "restrictedAngleSetDEFAction");
        app.registerButton(h_undoTotalSetButton, "h_undoTotalSetAction");
        app.registerButton(colOrangeButton, "colOrangeAction");
        app.registerButton(colYellowButton, "colYellowAction");
        app.registerButton(l2Button, "l2Action");
        app.registerButton(a1Button, "a1Action");
        app.registerButton(a2Button, "a2Action");
        app.registerButton(a3Button, "a3Action");
        app.registerButton(ckOCheckBox, "ckOAction");
        app.registerButton(ckTCheckBox, "ckTAction");
        app.registerButton(cAMVCheckBox, "cAMVAction");

        ckOCheckBox.addActionListener(e -> {
            app.mainCreasePatternWorker.unselect_all();

            if (ckOCheckBox.isSelected()) {
                app.mainCreasePatternWorker.check1();//r_hitosiiとr_heikouhanteiは、hitosiiとheikou_hanteiのずれの許容程度
                app.mainCreasePatternWorker.set_i_check1(true);
            } else {
                app.mainCreasePatternWorker.set_i_check1(false);
            }
            app.repaintCanvas();
        });
        fxOButton.addActionListener(e -> {

            app.mainCreasePatternWorker.unselect_all();
            app.mainCreasePatternWorker.fix1();
            app.mainCreasePatternWorker.check1();
            app.repaintCanvas();
        });
        ckTCheckBox.addActionListener(e -> {
            app.mainCreasePatternWorker.unselect_all();

            if (ckTCheckBox.isSelected()) {
                app.mainCreasePatternWorker.check2();//r_hitosiiとr_heikouhanteiは、hitosiiとheikou_hanteiのずれの許容程度
                app.mainCreasePatternWorker.setCheck2(true);
            } else {
                app.mainCreasePatternWorker.setCheck2(false);
            }
            app.repaintCanvas();
        });
        fxTButton.addActionListener(e -> {
            app.mainCreasePatternWorker.unselect_all();
            app.mainCreasePatternWorker.fix2();
            app.mainCreasePatternWorker.check2();
            app.repaintCanvas();
        });
        cAMVCheckBox.addActionListener(e -> {
            app.mainCreasePatternWorker.unselect_all();

            app.applicationModel.setCheck4Enabled(cAMVCheckBox.isSelected());

            app.Button_shared_operation();
            app.repaintCanvas();
        });
        ck4_colorDecreaseButton.addActionListener(e -> {
            app.mainCreasePatternWorker.lightenCheck4Color();
            app.repaintCanvas();
        });
        ck4_colorIncreaseButton.addActionListener(e -> {
            app.mainCreasePatternWorker.darkenCheck4Color();
            app.repaintCanvas();
        });
        angleSystemADecreaseButton.addActionListener(e -> angleSystemModel.decreaseAngleSystemA());

        angleSystemAButton.addActionListener(e -> angleSystemModel.setCurrentAngleSystemDivider(angleSystemModel.getAngleSystemADivider()));
        angleSystemAIncreaseButton.addActionListener(e -> angleSystemModel.increaseAngleSystemA());

        angleSystemBDecreaseButton.addActionListener(e -> angleSystemModel.decreaseAngleSystemB());

        angleSystemBButton.addActionListener(e -> angleSystemModel.setCurrentAngleSystemDivider(angleSystemModel.getAngleSystemBDivider()));
        angleSystemBIncreaseButton.addActionListener(e -> angleSystemModel.increaseAngleSystemB());
        restrictedAngleABCSetButton.addActionListener(e -> {
            getData(angleSystemModel);

            angleSystemModel.setCurrentABC();
        });

        restrictedAngleSetDEFButton.addActionListener(e -> {
            getData(angleSystemModel);

            angleSystemModel.setCurrentDEF();
        });

        degButton.addActionListener(e -> {
            angleSystemModel.setAngleSystemInputType(AngleSystemModel.AngleSystemInputType.DEG_1);
            app.canvasModel.setMouseMode(MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_13);
        });
        deg3Button.addActionListener(e -> {
            angleSystemModel.setAngleSystemInputType(AngleSystemModel.AngleSystemInputType.DEG_3);
            app.canvasModel.setMouseMode(MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_2_17);

        });
        angleRestrictedButton.addActionListener(e -> {
            angleSystemModel.setAngleSystemInputType(AngleSystemModel.AngleSystemInputType.DEG_5);
            app.canvasModel.setMouseMode(MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_5_37);
        });
        deg2Button.addActionListener(e -> {
            angleSystemModel.setAngleSystemInputType(AngleSystemModel.AngleSystemInputType.DEG_2);
            app.canvasModel.setMouseMode(MouseMode.ANGLE_SYSTEM_16);

        });
        deg4Button.addActionListener(e -> {
            angleSystemModel.setAngleSystemInputType(AngleSystemModel.AngleSystemInputType.DEG_4);
            app.canvasModel.setMouseMode(MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_3_18);
        });
        polygonSizeSetButton.addActionListener(e -> {
            app.applicationModel.setNumPolygonCorners(StringOp.String2int(polygonSizeTextField.getText(), app.applicationModel.getNumPolygonCorners()));
            app.canvasModel.setMouseMode(MouseMode.POLYGON_SET_NO_CORNERS_29);

            app.repaintCanvas();
        });
        regularPolygonButton.addActionListener(e -> {
            app.applicationModel.setNumPolygonCorners(StringOp.String2int(polygonSizeTextField.getText(), app.applicationModel.getNumPolygonCorners()));
            app.canvasModel.setMouseMode(MouseMode.POLYGON_SET_NO_CORNERS_29);
            app.canvasModel.setMouseModeAfterColorSelection(MouseMode.POLYGON_SET_NO_CORNERS_29);

            app.repaintCanvas();
            app.mainCreasePatternWorker.unselect_all();
        });
        circleDrawFreeButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_FREE_47);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_42);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawSeparateButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_SEPARATE_44);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawConcentricButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_CONCENTRIC_48);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawConcentricSelectButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_CONCENTRIC_SELECT_49);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawTwoConcentricButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_CONCENTRIC_TWO_CIRCLE_SELECT_50);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawTangentLineButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_TANGENT_LINE_45);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawThreePointButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_THREE_POINT_43);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        circleDrawInvertedButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_DRAW_INVERTED_46);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        c_colButton.addActionListener(e -> {
            //以下にやりたいことを書く

            Color color = JColorChooser.showDialog(null, "color", new Color(100, 200, 200));
            if (color != null) {
                app.applicationModel.setCircleCustomizedColor(color);
            }

            app.canvasModel.setMouseMode(MouseMode.CIRCLE_CHANGE_COLOR_59);

            app.repaintCanvas();
        });
        sen_tokutyuu_color_henkouButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.CIRCLE_CHANGE_COLOR_59);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        h_undoButton.addActionListener(e -> {
            app.mainCreasePatternWorker.auxUndo();
            app.repaintCanvas();
        });
        h_undoTotalSetButton.addActionListener(e -> app.historyStateModel.setAuxHistoryTotal(StringOp.String2int(auxUndoTotalTextField.getText(), app.historyStateModel.getAuxHistoryTotal())));
        h_redoButton.addActionListener(e -> {
            app.mainCreasePatternWorker.auxRedo();
            app.repaintCanvas();
        });
        h_senhaba_sageButton.addActionListener(e -> app.applicationModel.decreaseAuxLineWidth());
        h_senhaba_ageButton.addActionListener(e -> app.applicationModel.increaseAuxLineWidth());
        colOrangeButton.addActionListener(e -> {
            app.canvasModel.setAuxLiveLineColor(LineColor.ORANGE_4);

            app.repaintCanvas();
        });
        colYellowButton.addActionListener(e -> {
            app.canvasModel.setAuxLiveLineColor(LineColor.YELLOW_7);

            app.repaintCanvas();
        });
        h_senbun_nyuryokuButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.DRAW_CREASE_FREE_1);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();

            app.canvasModel.setFoldLineAdditionalInputMode(FoldLineAdditionalInputMode.AUX_LINE_1);
        });
        h_senbun_sakujyoButton.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.LINE_SEGMENT_DELETE_3);
            app.canvasModel.setFoldLineAdditionalInputMode(FoldLineAdditionalInputMode.AUX_LINE_1);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        l1Button.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.DISPLAY_LENGTH_BETWEEN_POINTS_1_53);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        MeasuresModel measuresModel = app.measuresModel;
        measuredLength1TextField.addActionListener(e -> measuresModel.setMeasuredLength1(StringOp.String2double(measuredLength1TextField.getText(), measuresModel.getMeasuredLength1())));
        l2Button.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.DISPLAY_LENGTH_BETWEEN_POINTS_2_54);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        measuredLength2TextField.addActionListener(e -> measuresModel.setMeasuredLength2(StringOp.String2double(measuredLength2TextField.getText(), measuresModel.getMeasuredLength2())));
        a1Button.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.DISPLAY_ANGLE_BETWEEN_THREE_POINTS_1_55);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        measuredAngle1TextField.addActionListener(e -> measuresModel.setMeasuredAngle1(StringOp.String2double(measuredAngle1TextField.getText(), measuresModel.getMeasuredAngle1())));
        a2Button.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.DISPLAY_ANGLE_BETWEEN_THREE_POINTS_2_56);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        measuredAngle2TextField.addActionListener(e -> measuresModel.setMeasuredAngle2(StringOp.String2double(measuredAngle2TextField.getText(), measuresModel.getMeasuredAngle2())));
        a3Button.addActionListener(e -> {
            app.canvasModel.setMouseMode(MouseMode.DISPLAY_ANGLE_BETWEEN_THREE_POINTS_3_57);

            app.mainCreasePatternWorker.unselect_all();
            app.repaintCanvas();
        });
        measuredAngle3TextField.addActionListener(e -> measuresModel.setMeasuredAngle3(StringOp.String2double(measuredAngle3TextField.getText(), measuresModel.getMeasuredAngle3())));

        frame = new OpenFrame("additionalFrame", app);

        ad_fncButton.addActionListener(e -> {
            frame.setLocationRelativeTo(ad_fncButton);
            frame.setVisible(true);
        });
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridLayoutManager(24, 1, new Insets(2, 2, 2, 2), 2, 2));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cAMVCheckBox = new JCheckBox();
        cAMVCheckBox.setText("cAMV");
        panel1.add(cAMVCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ck4_colorIncreaseButton = new JButton();
        ck4_colorIncreaseButton.setIcon(new ImageIcon(getClass().getResource("/ppp/ck4_color_age.png")));
        panel1.add(ck4_colorIncreaseButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fxTButton = new JButton();
        fxTButton.setText("fxT");
        panel1.add(fxTButton, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ckOCheckBox = new JCheckBox();
        ckOCheckBox.setText("ckO");
        panel1.add(ckOCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ckTCheckBox = new JCheckBox();
        ckTCheckBox.setText("ckT");
        panel1.add(ckTCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ck4_colorDecreaseButton = new JButton();
        ck4_colorDecreaseButton.setIcon(new ImageIcon(getClass().getResource("/ppp/ck4_color_sage.png")));
        panel1.add(ck4_colorDecreaseButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fxOButton = new JButton();
        fxOButton.setText("fxO");
        panel1.add(fxOButton, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        angleSystemAButton = new JButton();
        angleSystemAButton.setText("180/12=15.0");
        panel2.add(angleSystemAButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        angleSystemADecreaseButton = new JButton();
        angleSystemADecreaseButton.setIcon(new ImageIcon(getClass().getResource("/ppp/tiisaku.png")));
        panel2.add(angleSystemADecreaseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        angleSystemAIncreaseButton = new JButton();
        angleSystemAIncreaseButton.setIcon(new ImageIcon(getClass().getResource("/ppp/ookiku.png")));
        panel2.add(angleSystemAIncreaseButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        angleSystemBDecreaseButton = new JButton();
        angleSystemBDecreaseButton.setIcon(new ImageIcon(getClass().getResource("/ppp/tiisaku.png")));
        panel2.add(angleSystemBDecreaseButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        angleSystemBButton = new JButton();
        angleSystemBButton.setText("180/8=22.5");
        panel2.add(angleSystemBButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        angleSystemBIncreaseButton = new JButton();
        angleSystemBIncreaseButton.setIcon(new ImageIcon(getClass().getResource("/ppp/ookiku.png")));
        panel2.add(angleSystemBIncreaseButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        angleATextField = new JTextField();
        angleATextField.setText("40.0");
        panel3.add(angleATextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        angleCTextField = new JTextField();
        angleCTextField.setText("80.0");
        panel3.add(angleCTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        angleBTextField = new JTextField();
        angleBTextField.setText("60.0");
        panel3.add(angleBTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        restrictedAngleABCSetButton = new JButton();
        restrictedAngleABCSetButton.setText("S");
        panel3.add(restrictedAngleABCSetButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, -1), null, 0, false));
        angleDTextField = new JTextField();
        angleDTextField.setText("30.0");
        panel3.add(angleDTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        angleETextField = new JTextField();
        angleETextField.setText("50.0");
        panel3.add(angleETextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        angleFTextField = new JTextField();
        angleFTextField.setText("100.0");
        panel3.add(angleFTextField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        restrictedAngleSetDEFButton = new JButton();
        restrictedAngleSetDEFButton.setText("S");
        panel3.add(restrictedAngleSetDEFButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        degButton = new JButton();
        degButton.setIcon(new ImageIcon(getClass().getResource("/ppp/deg.png")));
        panel4.add(degButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deg3Button = new JButton();
        deg3Button.setIcon(new ImageIcon(getClass().getResource("/ppp/deg3.png")));
        panel4.add(deg3Button, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        angleRestrictedButton = new JButton();
        angleRestrictedButton.setIcon(new ImageIcon(getClass().getResource("/ppp/senbun_nyuryoku37.png")));
        panel4.add(angleRestrictedButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel5, new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        c_colButton = new JButton();
        c_colButton.setText("C_col");
        panel5.add(c_colButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sen_tokutyuu_color_henkouButton = new JButton();
        sen_tokutyuu_color_henkouButton.setIcon(new ImageIcon(getClass().getResource("/ppp/sen_tokutyuu_color_henkou.png")));
        panel5.add(sen_tokutyuu_color_henkouButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel6, new GridConstraints(20, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        l1Button = new JButton();
        l1Button.setHorizontalAlignment(11);
        l1Button.setText("L1=");
        panel6.add(l1Button, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        measuredLength1TextField = new JTextField();
        measuredLength1TextField.setOpaque(true);
        measuredLength1TextField.setText("0.0");
        panel6.add(measuredLength1TextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        l2Button = new JButton();
        l2Button.setHorizontalAlignment(11);
        l2Button.setText("L2=");
        panel6.add(l2Button, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        measuredLength2TextField = new JTextField();
        measuredLength2TextField.setOpaque(true);
        measuredLength2TextField.setText("0.0");
        panel6.add(measuredLength2TextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        a3Button = new JButton();
        a3Button.setHorizontalAlignment(11);
        a3Button.setText("A3=");
        panel6.add(a3Button, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        measuredAngle3TextField = new JTextField();
        measuredAngle3TextField.setOpaque(true);
        measuredAngle3TextField.setText("0.0");
        panel6.add(measuredAngle3TextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        measuredAngle2TextField = new JTextField();
        measuredAngle2TextField.setOpaque(true);
        measuredAngle2TextField.setText("0.0");
        panel6.add(measuredAngle2TextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        measuredAngle1TextField = new JTextField();
        measuredAngle1TextField.setOpaque(true);
        measuredAngle1TextField.setText("0.0");
        panel6.add(measuredAngle1TextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
        a2Button = new JButton();
        a2Button.setHorizontalAlignment(11);
        a2Button.setText("A2=");
        panel6.add(a2Button, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        a1Button = new JButton();
        a1Button.setHorizontalAlignment(11);
        a1Button.setText("A1=");
        panel6.add(a1Button, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel7, new GridConstraints(22, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ad_fncButton = new JButton();
        ad_fncButton.setText("ad_fnc");
        panel7.add(ad_fncButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel8, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        circleDrawFreeButton = new JButton();
        circleDrawFreeButton.setIcon(new ImageIcon(getClass().getResource("/ppp/en_nyuryoku_free.png")));
        panel8.add(circleDrawFreeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        circleDrawConcentricButton = new JButton();
        circleDrawConcentricButton.setIcon(new ImageIcon(getClass().getResource("/ppp/dousin_en_tuika_s.png")));
        panel8.add(circleDrawConcentricButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        circleDrawSeparateButton = new JButton();
        circleDrawSeparateButton.setIcon(new ImageIcon(getClass().getResource("/ppp/en_bunri_nyuryoku.png")));
        panel8.add(circleDrawSeparateButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel9, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        deg2Button = new JButton();
        deg2Button.setIcon(new ImageIcon(getClass().getResource("/ppp/deg2.png")));
        panel9.add(deg2Button, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deg4Button = new JButton();
        deg4Button.setIcon(new ImageIcon(getClass().getResource("/ppp/deg4.png")));
        panel9.add(deg4Button, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel10, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        polygonSizeTextField = new JTextField();
        polygonSizeTextField.setText("5");
        panel10.add(polygonSizeTextField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        polygonSizeSetButton = new JButton();
        polygonSizeSetButton.setText("Set");
        panel10.add(polygonSizeSetButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        regularPolygonButton = new JButton();
        regularPolygonButton.setIcon(new ImageIcon(getClass().getResource("/ppp/sei_takakukei.png")));
        panel10.add(regularPolygonButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel11, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        circleDrawButton = new JButton();
        circleDrawButton.setIcon(new ImageIcon(getClass().getResource("/ppp/en_nyuryoku.png")));
        panel11.add(circleDrawButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        circleDrawConcentricSelectButton = new JButton();
        circleDrawConcentricSelectButton.setIcon(new ImageIcon(getClass().getResource("/ppp/dousin_en_tuika_d.png")));
        panel11.add(circleDrawConcentricSelectButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel12, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        circleDrawTwoConcentricButton = new JButton();
        circleDrawTwoConcentricButton.setIcon(new ImageIcon(getClass().getResource("/ppp/en_en_dousin_en.png")));
        panel12.add(circleDrawTwoConcentricButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        circleDrawTangentLineButton = new JButton();
        circleDrawTangentLineButton.setIcon(new ImageIcon(getClass().getResource("/ppp/en_en_sessen.png")));
        panel12.add(circleDrawTangentLineButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel13, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        circleDrawThreePointButton = new JButton();
        circleDrawThreePointButton.setIcon(new ImageIcon(getClass().getResource("/ppp/en_3ten_nyuryoku.png")));
        panel13.add(circleDrawThreePointButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        circleDrawInvertedButton = new JButton();
        circleDrawInvertedButton.setIcon(new ImageIcon(getClass().getResource("/ppp/hanten.png")));
        panel13.add(circleDrawInvertedButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel14, new GridConstraints(16, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        h_undoButton = new JButton();
        h_undoButton.setIcon(new ImageIcon(getClass().getResource("/ppp/h_undo.png")));
        panel14.add(h_undoButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        auxUndoTotalTextField = new JTextField();
        auxUndoTotalTextField.setColumns(2);
        auxUndoTotalTextField.setText("50");
        panel14.add(auxUndoTotalTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        h_undoTotalSetButton = new JButton();
        h_undoTotalSetButton.setText("S");
        panel14.add(h_undoTotalSetButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        h_redoButton = new JButton();
        h_redoButton.setIcon(new ImageIcon(getClass().getResource("/ppp/h_redo.png")));
        panel14.add(h_redoButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel15, new GridConstraints(17, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        h_senhaba_sageButton = new JButton();
        h_senhaba_sageButton.setIcon(new ImageIcon(getClass().getResource("/ppp/h_senhaba_sage.png")));
        panel15.add(h_senhaba_sageButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        h_senhaba_ageButton = new JButton();
        h_senhaba_ageButton.setIcon(new ImageIcon(getClass().getResource("/ppp/h_senhaba_age.png")));
        panel15.add(h_senhaba_ageButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        colOrangeButton = new JButton();
        colOrangeButton.setBackground(new Color(-6908266));
        colOrangeButton.setText("a1");
        panel15.add(colOrangeButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        colYellowButton = new JButton();
        colYellowButton.setBackground(new Color(-6908266));
        colYellowButton.setText("a2");
        panel15.add(colYellowButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(30, -1), new Dimension(30, -1), null, 0, false));
        final JPanel panel16 = new JPanel();
        panel16.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 1, 1));
        root.add(panel16, new GridConstraints(18, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        h_senbun_nyuryokuButton = new JButton();
        h_senbun_nyuryokuButton.setIcon(new ImageIcon(getClass().getResource("/ppp/h_senbun_nyuryoku.png")));
        panel16.add(h_senbun_nyuryokuButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        h_senbun_sakujyoButton = new JButton();
        h_senbun_sakujyoButton.setIcon(new ImageIcon(getClass().getResource("/ppp/h_senbun_sakujyo.png")));
        panel16.add(h_senbun_sakujyoButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        root.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer2 = new Spacer();
        root.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer3 = new Spacer();
        root.add(spacer3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer4 = new Spacer();
        root.add(spacer4, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer5 = new Spacer();
        root.add(spacer5, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer6 = new Spacer();
        root.add(spacer6, new GridConstraints(19, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer7 = new Spacer();
        root.add(spacer7, new GridConstraints(21, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 5), new Dimension(-1, 5), null, 0, false));
        final Spacer spacer8 = new Spacer();
        root.add(spacer8, new GridConstraints(23, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

    public void setData(AngleSystemModel data) {
        angleATextField.setText(String.valueOf(data.getAngleA()));
        angleCTextField.setText(String.valueOf(data.getAngleC()));
        angleBTextField.setText(String.valueOf(data.getAngleB()));
        angleDTextField.setText(String.valueOf(data.getAngleD()));
        angleETextField.setText(String.valueOf(data.getAngleE()));
        angleFTextField.setText(String.valueOf(data.getAngleF()));

        angleSystemAButton.setText(data.getAngleSystemADescription());
        angleSystemBButton.setText(data.getAngleSystemBDescription());
    }

    public void getData(AngleSystemModel data) {
        data.setAngleA(app.measuresModel.string2double(angleATextField.getText(), data.getAngleA()));
        data.setAngleB(app.measuresModel.string2double(angleBTextField.getText(), data.getAngleB()));
        data.setAngleC(app.measuresModel.string2double(angleCTextField.getText(), data.getAngleC()));
        data.setAngleD(app.measuresModel.string2double(angleDTextField.getText(), data.getAngleD()));
        data.setAngleE(app.measuresModel.string2double(angleETextField.getText(), data.getAngleE()));
        data.setAngleF(app.measuresModel.string2double(angleFTextField.getText(), data.getAngleF()));
    }

    public void setData(MeasuresModel data) {
        measuredLength1TextField.setText(String.valueOf(data.getMeasuredLength1()));
        measuredLength2TextField.setText(String.valueOf(data.getMeasuredLength2()));
        measuredAngle1TextField.setText(String.valueOf(data.getMeasuredAngle1()));
        measuredAngle2TextField.setText(String.valueOf(data.getMeasuredAngle2()));
        measuredAngle3TextField.setText(String.valueOf(data.getMeasuredAngle3()));
    }

    public void getData(ApplicationModel data) {
        data.setNumPolygonCorners(StringOp.String2int(polygonSizeTextField.getText(), data.getNumPolygonCorners()));
    }

    public void setData(ApplicationModel data) {
        c_colButton.setIcon(new ColorIcon(data.getCircleCustomizedColor()));
        cAMVCheckBox.setSelected(data.getCheck4Enabled());
    }

    public void setData(PropertyChangeEvent e, CanvasModel data) {
        frame.setData(e, data);

        if (e.getPropertyName() == null || e.getPropertyName().equals("mouseMode") || e.getPropertyName().equals("foldLineAdditionalInputMode")) {
            MouseMode m = data.getMouseMode();
            FoldLineAdditionalInputMode f = data.getFoldLineAdditionalInputMode();

            regularPolygonButton.setSelected(m == MouseMode.POLYGON_SET_NO_CORNERS_29);
            circleDrawFreeButton.setSelected(m == MouseMode.CIRCLE_DRAW_FREE_47);
            circleDrawButton.setSelected(m == MouseMode.CIRCLE_DRAW_42);
            circleDrawSeparateButton.setSelected(m == MouseMode.CIRCLE_DRAW_SEPARATE_44);
            circleDrawConcentricButton.setSelected(m == MouseMode.CIRCLE_DRAW_CONCENTRIC_48);
            circleDrawConcentricSelectButton.setSelected(m == MouseMode.CIRCLE_DRAW_CONCENTRIC_SELECT_49);
            circleDrawTwoConcentricButton.setSelected(m == MouseMode.CIRCLE_DRAW_CONCENTRIC_TWO_CIRCLE_SELECT_50);
            circleDrawTangentLineButton.setSelected(m == MouseMode.CIRCLE_DRAW_TANGENT_LINE_45);
            circleDrawThreePointButton.setSelected(m == MouseMode.CIRCLE_DRAW_THREE_POINT_43);
            circleDrawInvertedButton.setSelected(m == MouseMode.CIRCLE_DRAW_INVERTED_46);
            sen_tokutyuu_color_henkouButton.setSelected(m == MouseMode.CIRCLE_CHANGE_COLOR_59);
            h_senbun_nyuryokuButton.setSelected(m == MouseMode.DRAW_CREASE_FREE_1 && f == FoldLineAdditionalInputMode.AUX_LINE_1);
            h_senbun_sakujyoButton.setSelected(m == MouseMode.LINE_SEGMENT_DELETE_3 && f == FoldLineAdditionalInputMode.AUX_LINE_1);
            l1Button.setSelected(m == MouseMode.DISPLAY_LENGTH_BETWEEN_POINTS_1_53);
            l2Button.setSelected(m == MouseMode.DISPLAY_LENGTH_BETWEEN_POINTS_2_54);
            a1Button.setSelected(m == MouseMode.DISPLAY_ANGLE_BETWEEN_THREE_POINTS_1_55);
            a2Button.setSelected(m == MouseMode.DISPLAY_ANGLE_BETWEEN_THREE_POINTS_2_56);
            a3Button.setSelected(m == MouseMode.DISPLAY_ANGLE_BETWEEN_THREE_POINTS_3_57);
            degButton.setSelected(m == MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_13);
            deg2Button.setSelected(m == MouseMode.ANGLE_SYSTEM_16);
            deg3Button.setSelected(m == MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_2_17);
            deg4Button.setSelected(m == MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_3_18);
            angleRestrictedButton.setSelected(m == MouseMode.DRAW_CREASE_ANGLE_RESTRICTED_5_37);
        }

        switch (data.getAuxLiveLineColor()) {
            case ORANGE_4:
                colOrangeButton.setBackground(Color.ORANGE);
                colYellowButton.setBackground(new Color(150, 150, 150));
                break;
            case YELLOW_7:
                colYellowButton.setBackground(Color.YELLOW);
                colOrangeButton.setBackground(new Color(150, 150, 150));
        }
    }

    public void setData(HistoryStateModel historyStateModel) {
        auxUndoTotalTextField.setText(String.valueOf(historyStateModel.getAuxHistoryTotal()));
    }
}
