package oriedita.editor.swing;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import javax.inject.Inject;
import javax.inject.Named;

import oriedita.editor.Colors;
import oriedita.editor.canvas.MouseMode;
import oriedita.editor.canvas.CreasePattern_Worker;
import oriedita.editor.databinding.*;
import oriedita.editor.exception.FileReadingException;
import oriedita.editor.save.Save;
import oriedita.editor.save.SaveV1;
import oriedita.editor.service.ButtonService;
import oriedita.editor.service.FileSaveService;
import oriedita.editor.service.ResetService;
import oriedita.editor.task.TaskExecutor;
import oriedita.editor.datatransfer.SaveTransferable;

import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@Singleton
public class AppMenuBar extends JMenuBar {
    private static final Logger logger = Logger.getLogger(AppMenuBar.class.getName());
    private final JFrame frame;
    private final FileSaveService fileSaveService;
    private final FileModel fileModel;
    private JCheckBoxMenuItem showPointRangeCheckBox;//点を探す範囲
    private JCheckBoxMenuItem pointOffsetCheckBox;//点を離すかどうか
    private JCheckBoxMenuItem gridInputAssistCheckBox;//高密度用入力をするかどうか
    private JCheckBoxMenuItem displayCommentsCheckBox;//文章
    private JCheckBoxMenuItem displayCpLinesCheckBox;//折線
    private JCheckBoxMenuItem displayAuxLinesCheckBox;//補助活線cyan
    private JCheckBoxMenuItem displayLiveAuxLinesCheckBox;//補助画線
    private JCheckBoxMenuItem displayStandardFaceMarksCheckBox;//Marking lines such as crosses and reference planes
    private JCheckBoxMenuItem cpOnTopCheckBox;//展開図を折り上がり予想図の上に描く
    private JCheckBoxMenuItem darkModeCheckBox;
    private JCheckBoxMenuItem preciseZoomCheckBox;
    private JCheckBoxMenuItem displaySelfIntersectionCheckBox;
    private JMenuItem newButton;
    private JMenuItem openButton;
    private JMenuItem saveButton;
    private JMenuItem saveAsButton;
    private JMenuItem exportButton;
    private JMenuItem importButton;
    private JMenuItem importAddButton;
    private JMenuItem exitButton;
    private JMenuItem toggleHelpMenuItem;
    private JMenu openRecentMenu;
    private JMenuItem clearRecentFileMenuItem;

    private JMenuItem copyButton;
    private JMenuItem cutButton;
    private JMenuItem pasteButton;
    private JMenuItem pasteOffsetButton;

    @Inject
    public AppMenuBar(@Named("mainFrame") JFrame frame,
                      ApplicationModel applicationModel,
                      FileSaveService fileSaveService,
                      ButtonService buttonService,
                      CanvasModel canvasModel,
                      FileModel fileModel,
                      CreasePattern_Worker mainCreasePatternWorker,
                      FoldedFigureModel foldedFigureModel,
                      ResetService resetService,
                      FoldedFiguresList foldedFiguresList) {
        this.frame = frame;
        this.fileSaveService = fileSaveService;
        this.fileModel = fileModel;

        applicationModel.addPropertyChangeListener(e -> setData(applicationModel));

        //--------------------------------------------------------------------------------------------------
        frame.addWindowListener(new WindowAdapter() {//ウィンドウの状態が変化したときの処理
            //終了ボタンを有効化
            public void windowClosing(WindowEvent evt) {
                closing();//Work to be done when pressing X at the right end of the upper side of the window
            }//終了ボタンを有効化 ここまで。
        });//Processing when the window state changes Up to here.

        createElements();
        buttonService.registerButton(newButton, "newAction");
        buttonService.registerButton(openButton, "openAction");
        buttonService.registerButton(openRecentMenu, "openRecentAction");
        buttonService.registerButton(saveButton, "saveAction");
        buttonService.registerButton(saveAsButton, "saveAsAction");
        buttonService.registerButton(importButton, "importAction");
        buttonService.registerButton(importAddButton, "importAddAction");
        buttonService.registerButton(exportButton, "exportAction");
        buttonService.registerButton(exitButton, "exitAction");
        buttonService.registerButton(showPointRangeCheckBox, "showPointRangeAction");
        buttonService.registerButton(pointOffsetCheckBox, "pointOffsetAction");
        buttonService.registerButton(gridInputAssistCheckBox, "gridInputAssistAction");
        buttonService.registerButton(displayCommentsCheckBox, "displayCommentsAction");
        buttonService.registerButton(displayCpLinesCheckBox, "displayCpLinesAction");
        buttonService.registerButton(displayAuxLinesCheckBox, "displayAuxLinesAction");
        buttonService.registerButton(displayLiveAuxLinesCheckBox, "displayLiveAuxLinesAction");
        buttonService.registerButton(displayStandardFaceMarksCheckBox, "displayStandardFaceMarksAction");
        buttonService.registerButton(cpOnTopCheckBox, "cpOnTopAction");
        buttonService.registerButton(toggleHelpMenuItem, "toggleHelpAction");
        buttonService.registerButton(darkModeCheckBox, "toggleDarkModeAction");
        buttonService.registerButton(preciseZoomCheckBox, "preciseZoomAction");
        buttonService.registerButton(displaySelfIntersectionCheckBox, "displaySelfIntersectionAction");

        buttonService.registerButton(copyButton, "copyClipboardAction");
        buttonService.registerButton(cutButton, "cutClipboardAction");
        buttonService.registerButton(pasteButton, "pasteClipboardAction");
        buttonService.registerButton(pasteOffsetButton, "pasteOffsetClipboardAction");

        newButton.addActionListener(e -> {
            if (!fileModel.isSaved()) {
                int choice = JOptionPane.showConfirmDialog(frame, "<html>Current file not saved.<br/>Do you want to save it?", "File not saved", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    fileSaveService.saveFile();
                } else if (choice == JOptionPane.CLOSED_OPTION || choice == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            fileModel.reset();
            //展開図の初期化　開始
            //settei_syokika_cp();//展開図パラメータの初期化
            resetService.developmentView_initialization();
            //展開図の初期化　終了
            //
            //折畳予測図のの初期化　開始
            foldedFiguresList.removeAllElements();
            //折畳予測図のの初期化　終了

            canvasModel.setMouseMode(MouseMode.FOLDABLE_LINE_DRAW_71);

            mainCreasePatternWorker.record();
            mainCreasePatternWorker.auxRecord();
        });
        openButton.addActionListener(e -> fileSaveService.openFile());
        clearRecentFileMenuItem.addActionListener(e -> applicationModel.setRecentFileList(new ArrayList<>()));

        saveButton.addActionListener(e -> fileSaveService.saveFile());
        saveAsButton.addActionListener(e -> fileSaveService.saveAsFile());
        exportButton.addActionListener(e -> {
            if (canvasModel.getMouseMode() != MouseMode.OPERATION_FRAME_CREATE_61) {
                mainCreasePatternWorker.setDrawingStage(0);
            }//枠設定時(==61)には、その枠を消さないためにes1.set_i_egaki_dankaiを０にしないでおく　20180524

            fileSaveService.exportFile();
        });
        importButton.addActionListener(e -> fileSaveService.importFile());
        importAddButton.addActionListener(e -> {
            logger.info("readFile2Memo() 開始");
            File file = fileSaveService.selectImportFile();
            Save save = null;
            try {
                save = fileSaveService.readImportFile(file);
            } catch (FileReadingException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred when reading this file", "Read Error", JOptionPane.ERROR_MESSAGE);
            }
            logger.info("readFile2Memo() 終了");

            if (save != null) {
                mainCreasePatternWorker.setSave_for_reading_tuika(save);
                mainCreasePatternWorker.record();
            }
        });
        exitButton.addActionListener(e -> closing());
        showPointRangeCheckBox.addActionListener(e -> getData(applicationModel));
        pointOffsetCheckBox.addActionListener(e -> getData(applicationModel));
        gridInputAssistCheckBox.addActionListener(e -> {
            if (gridInputAssistCheckBox.isSelected()) {
                logger.info(" kou_mitudo_nyuuryoku on");
            } else {
                logger.info(" kou_mitudo_nyuuryoku off");
            }
            getData(applicationModel);
        });
        displayCommentsCheckBox.addActionListener(e -> getData(applicationModel));
        displayCpLinesCheckBox.addActionListener(e -> getData(applicationModel));
        displayAuxLinesCheckBox.addActionListener(e -> getData(applicationModel));
        displayLiveAuxLinesCheckBox.addActionListener(e -> getData(applicationModel));
        displayStandardFaceMarksCheckBox.addActionListener(e -> getData(applicationModel));
        cpOnTopCheckBox.addActionListener(e -> getData(applicationModel));
        toggleHelpMenuItem.addActionListener(e -> applicationModel.toggleHelpVisible());
        darkModeCheckBox.addActionListener(e -> {
            applicationModel.toggleDarkMode();

            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame, "Restore custom colors in grid and folded figure for this color scheme?", "Restore colors", JOptionPane.YES_NO_OPTION)) {
                if (FlatLaf.isLafDark()) {
                    applicationModel.setGridColor(Colors.GRID_LINE_DARK);
                    applicationModel.setGridScaleColor(Colors.GRID_SCALE_DARK);

                    foldedFigureModel.setFrontColor(Colors.FIGURE_FRONT_DARK);
                    foldedFigureModel.setBackColor(Colors.FIGURE_BACK_DARK);
                } else {
                    applicationModel.setGridColor(Colors.GRID_LINE);
                    applicationModel.setGridScaleColor(Colors.GRID_SCALE);

                    foldedFigureModel.setFrontColor(Colors.FIGURE_FRONT);
                    foldedFigureModel.setBackColor(Colors.FIGURE_BACK);
                }
            }
        });
        preciseZoomCheckBox.addActionListener(e -> applicationModel.togglePreciseZoom());
        displaySelfIntersectionCheckBox.addActionListener(e -> applicationModel.toggleDisplaySelfIntersection());

        copyButton.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            Save save = new SaveV1();
            mainCreasePatternWorker.foldLineSet.getSaveForSelectFolding(save);

            clipboard.setContents(new SaveTransferable(save), (clipboard1, contents) -> {});
        });
        cutButton.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            Save save = new SaveV1();
            mainCreasePatternWorker.foldLineSet.getSaveForSelectFolding(save);


            clipboard.setContents(new SaveTransferable(save), (clipboard1, contents) -> {});

            mainCreasePatternWorker.foldLineSet.delSelectedLineSegmentFast();

            mainCreasePatternWorker.record();
        });
        pasteButton.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable clipboardContents = clipboard.getContents(null);

            try {
                if (clipboardContents.isDataFlavorSupported(SaveTransferable.saveFlavor)) {
                    Save save = (Save) clipboardContents.getTransferData(SaveTransferable.saveFlavor);

                    mainCreasePatternWorker.setSaveForPaste(save);
                }
            } catch (IOException | UnsupportedFlavorException ignored) {
                // We don't know how to paste this
            }
        });
        pasteOffsetButton.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable clipboardContents = clipboard.getContents(null);

            try {
                if (clipboardContents.isDataFlavorSupported(SaveTransferable.saveFlavor)) {
                    Save save = (Save) clipboardContents.getTransferData(SaveTransferable.saveFlavor);

                    mainCreasePatternWorker.setSave_for_reading_tuika(save);
                }
            } catch (IOException | UnsupportedFlavorException ignored) {
                // We don't know how to paste this
            }
        });
    }

    private void createElements() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        add(fileMenu);

        newButton = new JMenuItem("New");
        fileMenu.add(newButton);

        openButton = new JMenuItem("Open...");
        fileMenu.add(openButton);

        openRecentMenu = new JMenu("Open Recent");
        fileMenu.add(openRecentMenu);

        clearRecentFileMenuItem = new JMenuItem("Clear");
        openRecentMenu.add(clearRecentFileMenuItem);

        saveButton = new JMenuItem("Save");
        fileMenu.add(saveButton);

        saveAsButton = new JMenuItem("Save as...");
        fileMenu.add(saveAsButton);

        fileMenu.addSeparator();

        exportButton = new JMenuItem("Export");
        fileMenu.add(exportButton);

        importButton = new JMenuItem("Import");
        fileMenu.add(importButton);

        importAddButton = new JMenuItem("Import (Add)");
        fileMenu.add(importAddButton);

        fileMenu.addSeparator();

        exitButton = new JMenuItem("Exit");
        fileMenu.add(exitButton);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        add(editMenu);

        copyButton = new JMenuItem("Copy");
        editMenu.add(copyButton);

        cutButton = new JMenuItem("Cut");
        editMenu.add(cutButton);

        JMenu pasteMenu = new JMenu("Paste");
        editMenu.add(pasteMenu);

        pasteButton = new JMenuItem("Paste");
        pasteMenu.add(pasteButton);

        pasteOffsetButton = new JMenuItem("Paste (offset)");
        pasteMenu.add(pasteOffsetButton);

        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');

        add(viewMenu);

        darkModeCheckBox = new JCheckBoxMenuItem("Dark Mode");
        viewMenu.add(darkModeCheckBox);
        preciseZoomCheckBox = new JCheckBoxMenuItem("Smooth zoom");
        viewMenu.add(preciseZoomCheckBox);
        showPointRangeCheckBox = new JCheckBoxMenuItem("Show point range");
        viewMenu.add(showPointRangeCheckBox);
        pointOffsetCheckBox = new JCheckBoxMenuItem("Offset cursor");
        viewMenu.add(pointOffsetCheckBox);
        gridInputAssistCheckBox = new JCheckBoxMenuItem("Grid input assist");
        viewMenu.add(gridInputAssistCheckBox);
        displayCommentsCheckBox = new JCheckBoxMenuItem("Display comments");
        viewMenu.add(displayCommentsCheckBox);
        displayCpLinesCheckBox = new JCheckBoxMenuItem("Display cp lines");
        viewMenu.add(displayCpLinesCheckBox);
        displayAuxLinesCheckBox = new JCheckBoxMenuItem("Display aux lines");
        viewMenu.add(displayAuxLinesCheckBox);
        displayLiveAuxLinesCheckBox = new JCheckBoxMenuItem("Display live aux lines");
        viewMenu.add(displayLiveAuxLinesCheckBox);
        displayStandardFaceMarksCheckBox = new JCheckBoxMenuItem("Display standard face marks");
        viewMenu.add(displayStandardFaceMarksCheckBox);
        cpOnTopCheckBox = new JCheckBoxMenuItem("Crease pattern on top");
        viewMenu.add(cpOnTopCheckBox);
        displaySelfIntersectionCheckBox = new JCheckBoxMenuItem("Display self intersection");
        viewMenu.add(displaySelfIntersectionCheckBox);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        add(helpMenu);

        toggleHelpMenuItem = new JMenuItem("Toggle help");
        helpMenu.add(toggleHelpMenuItem);
    }

    public void getData(ApplicationModel applicationModel) {
        applicationModel.setDisplayPointSpotlight(showPointRangeCheckBox.isSelected());
        applicationModel.setDisplayPointOffset(pointOffsetCheckBox.isSelected());
        applicationModel.setDisplayGridInputAssist(gridInputAssistCheckBox.isSelected());
        applicationModel.setDisplayComments(displayCommentsCheckBox.isSelected());
        applicationModel.setDisplayCpLines(displayCpLinesCheckBox.isSelected());
        applicationModel.setDisplayAuxLines(displayAuxLinesCheckBox.isSelected());
        applicationModel.setDisplayLiveAuxLines(displayLiveAuxLinesCheckBox.isSelected());
        applicationModel.setDisplayMarkings(displayStandardFaceMarksCheckBox.isSelected());
        applicationModel.setDisplayCreasePatternOnTop(cpOnTopCheckBox.isSelected());
        applicationModel.setDarkMode(darkModeCheckBox.isSelected());
        applicationModel.setPreciseZoom(preciseZoomCheckBox.isSelected());
        applicationModel.setDisplaySelfIntersection(displaySelfIntersectionCheckBox.isSelected());
    }

    public void setData(ApplicationModel applicationModel) {
        showPointRangeCheckBox.setSelected(applicationModel.getDisplayPointSpotlight());
        pointOffsetCheckBox.setSelected(applicationModel.getDisplayPointOffset());
        gridInputAssistCheckBox.setSelected(applicationModel.getDisplayGridInputAssist());
        displayCommentsCheckBox.setSelected(applicationModel.getDisplayComments());
        displayCpLinesCheckBox.setSelected(applicationModel.getDisplayCpLines());
        displayAuxLinesCheckBox.setSelected(applicationModel.getDisplayAuxLines());
        displayLiveAuxLinesCheckBox.setSelected(applicationModel.getDisplayLiveAuxLines());
        displayStandardFaceMarksCheckBox.setSelected(applicationModel.getDisplayMarkings());
        cpOnTopCheckBox.setSelected(applicationModel.getDisplayCreasePatternOnTop());
        darkModeCheckBox.setSelected(applicationModel.getLaf().equals(FlatDarkLaf.class.getName()));
        preciseZoomCheckBox.setSelected(applicationModel.isPreciseZoom());
        displaySelfIntersectionCheckBox.setSelected(applicationModel.getDisplaySelfIntersection());

        openRecentMenu.removeAll();
        if (applicationModel.getRecentFileList().isEmpty()) {
            JMenuItem noItemsMenuItem = new JMenuItem("No items");
            noItemsMenuItem.setEnabled(false);
            openRecentMenu.add(noItemsMenuItem);
        }
        for (File recentFile : applicationModel.getRecentFileList()) {
            JMenuItem recentFileMenuItem = new JMenuItem(recentFile.getName());
            recentFileMenuItem.addActionListener(e -> {
                try {
                    fileSaveService.openFile(recentFile);
                    // Move this file to the top of the recent file list.
                    applicationModel.addRecentFile(recentFile);
                } catch (FileReadingException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "An error occurred when reading this file", "Read Error", JOptionPane.ERROR_MESSAGE);
                    applicationModel.removeRecentFile(recentFile);
                }
            });
            openRecentMenu.add(recentFileMenuItem);
        }
        openRecentMenu.addSeparator();
        openRecentMenu.add(clearRecentFileMenuItem);
    }

    public void closing() {
        if (!fileModel.isSaved()) {
            int option = JOptionPane.showConfirmDialog(frame, "Save crease pattern before exiting?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            switch (option) {
                case JOptionPane.YES_OPTION:
                    fileSaveService.saveFile();

                    TaskExecutor.stopTask();
                    System.exit(0);
                case JOptionPane.NO_OPTION:
                    TaskExecutor.stopTask();
                    System.exit(0);
                case JOptionPane.CANCEL_OPTION:
                    break;
            }
        } else {
            TaskExecutor.stopTask();
            System.exit(0);
        }
    }
}