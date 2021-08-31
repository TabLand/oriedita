package origami_editor.editor;

import origami_editor.graphic2d.grid.Grid;
import origami_editor.record.memo.Memo;

import javax.swing.*;
import java.awt.*;

public class AppMenuBar extends JMenuBar {
    public JCheckBoxMenuItem showPointRangeCheckBox;//点を探す範囲
    public JCheckBoxMenuItem pointOffsetCheckBox;//点を離すかどうか
    public JCheckBoxMenuItem gridInputAssistCheckBox;//高密度用入力をするかどうか
    public JCheckBoxMenuItem displayCommentsCheckBox;//文章
    public JCheckBoxMenuItem displayCpLinesCheckBox;//折線
    public JCheckBoxMenuItem displayAuxLinesCheckBox;//補助活線cyan
    public JCheckBoxMenuItem displayLiveAuxLinesCheckBox;//補助画線
    public JCheckBoxMenuItem displayStandardFaceMarksCheckBox;//Marking lines such as crosses and reference planes
    public JCheckBoxMenuItem cpOnTopCheckBox;//展開図を折り上がり予想図の上に描く
    JMenuItem openButton;
    JMenuItem saveButton;
    private JMenuItem toggleHelpMenuItem;

    public AppMenuBar(App app) {
        createElements();

        openButton.addActionListener(e -> {
            app.setHelp("qqq/yomi.png");

            app.Button_shared_operation();

            app.mouseDraggedValid = false;
            app.mouseReleasedValid = false;
            Memo memo_temp;

            System.out.println("readFile2Memo() 開始");
            memo_temp = app.readFile2Memo();
            System.out.println("readFile2Memo() 終了");

            if (memo_temp.getLineCount() > 0) {
                //展開図の初期化　開始
                app.developmentView_initialization();
                //展開図パラメータの初期化
                app.es1.reset();                                                //描き職人の初期化

                app.es1.setBaseState(Grid.State.HIDDEN);

                app.currentLineColor = LineColor.RED_1;
                app.es1.setColor(app.currentLineColor);                                        //最初の折線の色を指定する。0は黒、1は赤、2は青。
                app.buttonColorReset();
                app.colRedButton.setForeground(Color.black);
                app.colRedButton.setBackground(Color.red);    //折線のボタンの色設定
                //展開図の初期化　終了


                //折畳予測図のの初期化　開始
                app.OZ = app.temp_OZ;//20171223この行は不要かもしれないが、一瞬でもOZが示すOriagari_Zuがなくなることがないように念のために入れておく
                app.foldedFigures.clear();
                app.addNewFoldedFigure();
                app.set_i_OAZ(0);
                app.configure_initialize_prediction();

                app.Button_F_color.setBackground(app.OZ.foldedFigure_F_color);    //ボタンの色設定
                app.Button_B_color.setBackground(app.OZ.foldedFigure_B_color);    //ボタンの色設定
                app.Button_L_color.setBackground(app.OZ.foldedFigure_L_color);    //ボタンの色設定
                //折畳予測図のの初期化　終了

                app.es1.setCamera(app.camera_of_orisen_input_diagram);//20170702この１行を入れると、解凍したjarファイルで実行し、最初にデータ読み込んだ直後はホイールでの展開図拡大縮小ができなくなる。jarのままで実行させた場合はもんだいないようだ。原因不明。
                app.es1.setMemo_for_reading(memo_temp);
                app.es1.record();

// -----------------20180503追加
                app.scaleFactor = app.camera_of_orisen_input_diagram.getCameraZoomX();
                app.scaleFactorTextField.setText(String.valueOf(app.scaleFactor)); //縮尺係数
                app.scaleFactorTextField.setCaretPosition(0);

                app.rotationCorrection = app.camera_of_orisen_input_diagram.getCameraAngle();
                app.rotationTextField.setText(String.valueOf(app.rotationCorrection));//回転表示角度の補正係数
                app.rotationTextField.setCaretPosition(0);
// -----------------20180503追加ここまで
            }
        });
        saveButton.addActionListener(e -> {
            app.setHelp("qqq/kaki.png");
            app.Button_shared_operation();
            app.mouseDraggedValid = false;
            app.mouseReleasedValid = false;
            app.writeMemo2File();
            app.es1.record();
        });
        showPointRangeCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_ten_sagasi.png");
            app.repaintCanvas();
        });
        pointOffsetCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_ten_hanasi.png");

            app.repaintCanvas();
        });
        gridInputAssistCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_kou_mitudo_nyuuryoku.png");

            if (gridInputAssistCheckBox.isSelected()) {
                System.out.println(" kou_mitudo_nyuuryoku on");
                app.es1.setGridInputAssist(true);
            } else {
                System.out.println(" kou_mitudo_nyuuryoku off");
                app.es1.setGridInputAssist(false);
            }
            app.repaintCanvas();
        });
        displayCommentsCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_bun.png");
            app.repaintCanvas();
        });
        displayCpLinesCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_cp.png");
            app.repaintCanvas();
        });
        displayAuxLinesCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_a0.png");
            app.repaintCanvas();
        });
        displayLiveAuxLinesCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_a1.png");
            app.repaintCanvas();
        });
        displayStandardFaceMarksCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_mejirusi.png");

            app.repaintCanvas();
        });
        cpOnTopCheckBox.addActionListener(e -> {
            app.setHelp("qqq/ckbox_cp_ue.png");

            app.repaintCanvas();
        });
        toggleHelpMenuItem.addActionListener(e -> {
            app.displayExplanation = !app.displayExplanation;

            app.mouseDraggedValid = false;
            app.mouseReleasedValid = false;

            app.repaintCanvas();
        });
    }

    public JCheckBoxMenuItem getShowPointRangeCheckBox() {
        return showPointRangeCheckBox;
    }

    public JCheckBoxMenuItem getPointOffsetCheckBox() {
        return pointOffsetCheckBox;
    }

    public JCheckBoxMenuItem getGridInputAssistCheckBox() {
        return gridInputAssistCheckBox;
    }

    public JCheckBoxMenuItem getDisplayCommentsCheckBox() {
        return displayCommentsCheckBox;
    }

    public JCheckBoxMenuItem getDisplayCpLinesCheckBox() {
        return displayCpLinesCheckBox;
    }

    public JCheckBoxMenuItem getDisplayAuxLinesCheckBox() {
        return displayAuxLinesCheckBox;
    }

    public JCheckBoxMenuItem getDisplayLiveAuxLinesCheckBox() {
        return displayLiveAuxLinesCheckBox;
    }

    public JCheckBoxMenuItem getDisplayStandardFaceMarksCheckBox() {
        return displayStandardFaceMarksCheckBox;
    }

    public JCheckBoxMenuItem getCpOnTopCheckBox() {
        return cpOnTopCheckBox;
    }

    private void createElements() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        add(fileMenu);

        openButton = new JMenuItem("Open");
        openButton.setMnemonic('O');

        fileMenu.add(openButton);

        saveButton = new JMenuItem("Save");
        saveButton.setMnemonic('S');

        fileMenu.add(saveButton);

        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');

        add(viewMenu);

        // -------------------------------------------------------------------
//点探し
        showPointRangeCheckBox = new JCheckBoxMenuItem("Show point range");
        viewMenu.add(showPointRangeCheckBox);

// -------------------------------------------------------------------
//点離し
        pointOffsetCheckBox = new JCheckBoxMenuItem("Offset cursor");
        viewMenu.add(pointOffsetCheckBox);
// -------------------------------------------------------------------
//高密度入力
        gridInputAssistCheckBox = new JCheckBoxMenuItem("Grid input assist");
        viewMenu.add(gridInputAssistCheckBox);
// -------------------------------------------------------------------

//文表示
        displayCommentsCheckBox = new JCheckBoxMenuItem("Display comments");
        viewMenu.add(displayCommentsCheckBox);
// -------------------------------------------------------------------
//折線表示
        displayCpLinesCheckBox = new JCheckBoxMenuItem("Display cp lines");
        viewMenu.add(displayCpLinesCheckBox);
// -------------------------------------------------------------------
//補助活線表示
        displayAuxLinesCheckBox = new JCheckBoxMenuItem("Display aux lines");
        viewMenu.add(displayAuxLinesCheckBox);
// -------------------------------------------------------------------
//補助画線表示
        displayLiveAuxLinesCheckBox = new JCheckBoxMenuItem("Display live aux lines");
        viewMenu.add(displayLiveAuxLinesCheckBox);
// -------------------------------------------------------------------
//十字や基準面などの目印画線
        displayStandardFaceMarksCheckBox = new JCheckBoxMenuItem("Display standard face marks");
        viewMenu.add(displayStandardFaceMarksCheckBox);

// -------------------------------------------------------------------
//折りあがり図を補助線の手前側にするかどうか
        cpOnTopCheckBox = new JCheckBoxMenuItem("Crease pattern on top");
        viewMenu.add(cpOnTopCheckBox);


        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        add(helpMenu);

// *******北*********************************************************************** 解説
        toggleHelpMenuItem = new JMenuItem("Toggle help");

        toggleHelpMenuItem.setMargin(new Insets(0, 0, 0, 0));
        helpMenu.add(toggleHelpMenuItem);

        toggleHelpMenuItem.setMargin(new Insets(0, 0, 0, 0));
    }
}