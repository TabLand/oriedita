package jp.gr.java_conf.mt777.origami.orihime.oriagari_zu;

import java.awt.*;

import jp.gr.java_conf.mt777.origami.orihime.*;
import jp.gr.java_conf.mt777.origami.orihime.basicbranch_worker.*;
import jp.gr.java_conf.mt777.origami.orihime.tenkaizu_syokunin.*;
import jp.gr.java_conf.mt777.origami.orihime.jyougehyou_syokunin.*;

import jp.gr.java_conf.mt777.origami.dougu.camera.*;
import jp.gr.java_conf.mt777.origami.dougu.keijiban.*;
import jp.gr.java_conf.mt777.origami.dougu.linestore.*;
import jp.gr.java_conf.mt777.kiroku.memo.*;

import jp.gr.java_conf.mt777.zukei2d.ten.Point;


// -------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------

public class Oriagari_Zu {
    App orihime_app;

    double r = 3.0;                   //基本枝構造の直線の両端の円の半径、枝と各種ポイントの近さの判定基準


    public double d_foldedFigure_syukusyaku_keisuu = 1.0;//折り上がり図の縮尺係数
    public double d_foldedFigure_kaiten_hosei = 0.0;//折り上がり図の回転表示角度の補正角度

    public WireFrame_Worker bb_worker = new WireFrame_Worker(r);    //Basic branch craftsman. Before passing the point set of cp_worker2 to cp_worker3,
    // The point set of cp_worker2 may have overlapping bars, so
    // Pass it to bb_worker once and organize it as a line segment set.

    public CreasePattern_Worker cp_worker1 = new CreasePattern_Worker(r);    //Net craftsman. Fold the input line segment set first to make a fold-up diagram of the wire-shaped point set.
    public CreasePattern_Worker cp_worker2 = new CreasePattern_Worker(r);    //Net craftsman. It holds the folded-up view of the wire-shaped point set created by cp_worker1 and functions as a line segment set.
    public CreasePattern_Worker cp_worker3 = new CreasePattern_Worker(r);    //Net craftsman. Organize the wire-shaped point set created by cp_worker1. It has functions such as recognizing a new surface.

    public ClassTable_Worker ct_worker;

    public Camera camera_of_foldedFigure = new Camera();
    public Camera camera_of_oriagari_front = new Camera();//折り上がり
    public Camera camera_of_oriagari_rear = new Camera();
    public Camera camera_of_transparent_front = new Camera();
    public Camera camera_of_transparant_rear = new Camera();

    public Color foldedFigure_F_color = new Color(255, 255, 50);//Folded surface color
    public Color foldedFigure_B_color = new Color(233, 233, 233);//The color of the back side of the folded figure
    public Color foldedFigure_L_color = Color.black;//Folded line color

    public int hyouji_flg_backup = 4;//表示様式hyouji_flgの一時的バックアップ用
    //int hyouji_flg_backup=4;//表示様式hyouji_flgの一時的バックアップ用
    public int display_flg = 0;//折り上がり図の表示様式の指定。1なら展開図整理、2なら針金図。3なら透過図。4なら実際に折り紙を折った場合と同じ。
    public int i_estimated_order = 0;//Instructions on how far to perform folding estimation
    public int i_estimated_step = 0;//Display of how far the folding estimation has been completed

    //Variable to store the value for display
    public int ip1 = -1;// At the time of initial setting of the upper and lower front craftsmen, the front and back sides are the same after folding
    // A variable that stores 0 if there is an error of being adjacent, and 1000 if there is no error.
    // The initial value here can be any number other than (0 or 1000).
    public int ip2 = -1;// When the top and bottom craftsmen look for a foldable stacking method,
    // A variable that stores 0 if there is no possible overlap, and 1000 if there is a possible overlap.
    // The initial value here can be any number other than (0 or 1000).
    //int ip3a=1;
    public int ip3 = 1;// Used by cp_worker1 to specify the reference plane for folding.

    public int ip4 = 0;// This specifies whether to flip over at the beginning of cp_worker1. Do not set to 0. If it is 1, turn it over.

    public int ip5 = -1;    // After the top and bottom craftsmen once show the overlap of foldable paper,
    // The result of the first ct_worker.susumu (Smensuu) when looking for yet another paper overlap. If it was
    // 0, there was no room for new susumu. If non-zero, the smallest number of changed Smen ids

    public int ip6 = -1;    // After the top and bottom craftsmen once show the overlap of foldable paper,
    // The result of ct_worker.kanou_kasanari_sagasi () when looking for another paper overlap. If
    // 0, there is no possible overlapping state.
    // If it is 1000, another way of overlapping was found.

    public int different_search_flg = 0;     //これは「別の重なりを探す」ことが有効の場合は１、無効の場合は０をとる。
    public int discovered_fold_cases = 0;    //折り重なり方で、何通り発見したかを格納する。

    public int transparent_transparency = 16;//Transparency when drawing a transparent diagram in color

    public int i_oriagari_sousa_mode = 1;//1 = When deformed, it becomes a wire diagram, and after deformation, the upper and lower tables are recalculated, the old mode,2 = A mode in which the folded figure remains even when deformed, and the upper and lower tables are basically not recalculated after deformation.

    public BulletinBoard bulletinBoard;

    public boolean w_image_jikkoutyuu = false;//折畳みまとめ実行の。単一回のイメージ書き出しが実行中ならtureになる。
    public boolean matome_write_image_jikkoutyuu = false;//matome_write_imageが実行中ならtureになる。これは、複数の折りあがり形の予測の書き出しがかすれないように使う。20170613

    String fname_and_number;//まとめ書き出しに使う。


    //各種変数の定義
    String c = "";                //文字列処理用のクラスのインスタンス化
    public String text_kekka;                //結果表示用文字列のクラスのインスタンス化

    public int i_toukazu_color = 0;//透過図をカラーにするなら１、しないなら０


    // **************************************************
//コンストラクタ
    public Oriagari_Zu(App app0) {
        orihime_app = app0;

        ct_worker = new ClassTable_Worker(app0);
        bulletinBoard = new BulletinBoard(app0);

        //カメラの設定 ------------------------------------------------------------------
        oriagari_camera_syokika();
        //カメラの設定はここまで----------------------------------------------------

        text_kekka = "";
    }

    //----------------------------------------------------------
    public void estimated_initialize() {
        text_kekka = "";
        bb_worker.reset();
        cp_worker1.reset();
        cp_worker2.reset();
        cp_worker3.reset();
        ct_worker.reset();

        //oriagari_camera_syokika();		//20170615 実行しないようにした（折りあがり図の表示状況を変えないようにするため）
        //cp_worker2.setCamera(camera_of_oriagarizu);	//20170615 実行しないようにした（折りあがり図の表示状況を変えないようにするため）
        //ct_worker.setCamera(camera_of_oriagarizu);	//20170615 実行しないようにした（折りあがり図の表示状況を変えないようにするため）

        display_flg = 0;//折り上がり図の表示様式の指定。1なら展開図整理、2なら針金図。3なら透過図。5なら実際に折り紙を折った場合と同じ。
        i_estimated_order = 0;//折り畳み推定をどの段階まで行うかの指示
        i_estimated_step = 0;//折り畳み推定がどの段階までできたかの表示
        different_search_flg = 0;

        matome_write_image_jikkoutyuu = false; //複数の折りあがり形の予測の書き出しがが実行中ならtureになる。20170615
    }


    //----------------------------------------------------------
    public void oriagari_camera_syokika() {
        //camera_of_oriagarizu	;
        camera_of_foldedFigure.setCameraPositionX(0.0);
        camera_of_foldedFigure.setCameraPositionY(0.0);
        camera_of_foldedFigure.setCameraAngle(0.0);
        camera_of_foldedFigure.set_camera_kagami(1.0);
        camera_of_foldedFigure.setCameraZoomX(1.0);
        camera_of_foldedFigure.setCameraZoomY(1.0);
        camera_of_foldedFigure.setDisplayPositionX(350.0);
        camera_of_foldedFigure.setDisplayPositionY(350.0);


        //camera_of_oriagari_omote	;
        camera_of_oriagari_front.setCameraPositionX(0.0);
        camera_of_oriagari_front.setCameraPositionY(0.0);
        camera_of_oriagari_front.setCameraAngle(0.0);
        camera_of_oriagari_front.set_camera_kagami(1.0);
        camera_of_oriagari_front.setCameraZoomX(1.0);
        camera_of_oriagari_front.setCameraZoomY(1.0);
        camera_of_oriagari_front.setDisplayPositionX(350.0);
        camera_of_oriagari_front.setDisplayPositionY(350.0);

        //camera_of_oriagari_ura	;
        camera_of_oriagari_rear.setCameraPositionX(0.0);
        camera_of_oriagari_rear.setCameraPositionY(0.0);
        camera_of_oriagari_rear.setCameraAngle(0.0);
        camera_of_oriagari_rear.set_camera_kagami(-1.0);
        camera_of_oriagari_rear.setCameraZoomX(1.0);
        camera_of_oriagari_rear.setCameraZoomY(1.0);
        camera_of_oriagari_rear.setDisplayPositionX(350.0);
        camera_of_oriagari_rear.setDisplayPositionY(350.0);


        //camera_of_touka_omote	;
        camera_of_transparent_front.setCameraPositionX(0.0);
        camera_of_transparent_front.setCameraPositionY(0.0);
        camera_of_transparent_front.setCameraAngle(0.0);
        camera_of_transparent_front.set_camera_kagami(1.0);
        camera_of_transparent_front.setCameraZoomX(1.0);
        camera_of_transparent_front.setCameraZoomY(1.0);
        camera_of_transparent_front.setDisplayPositionX(350.0);
        camera_of_transparent_front.setDisplayPositionY(350.0);

        //camera_of_touka_ura	;
        camera_of_transparant_rear.setCameraPositionX(0.0);
        camera_of_transparant_rear.setCameraPositionY(0.0);
        camera_of_transparant_rear.setCameraAngle(0.0);
        camera_of_transparant_rear.set_camera_kagami(-1.0);
        camera_of_transparant_rear.setCameraZoomX(1.0);
        camera_of_transparant_rear.setCameraZoomY(1.0);
        camera_of_transparant_rear.setDisplayPositionX(350.0);
        camera_of_transparant_rear.setDisplayPositionY(350.0);


    }

    // ------------------------------------------------------------------------------------------
    public void foldUp_draw(Graphics bufferGraphics, int i_mejirusi_hyouji) {

        //hyouji_flg==2,ip4==0  front
        //hyouji_flg==2,ip4==1	rear
        //hyouji_flg==2,ip4==2	front & rear
        //hyouji_flg==2,ip4==3	front & rear

        //hyouji_flg==3,ip4==0  front
        //hyouji_flg==3,ip4==1	rear
        //hyouji_flg==3,ip4==2	front & rear
        //hyouji_flg==3,ip4==3	front & rear

        //hyouji_flg==5,ip4==0  front
        //hyouji_flg==5,ip4==1	rear
        //hyouji_flg==5,ip4==2	front & rear
        //hyouji_flg==5,ip4==3	front & rear & front2 & rear2


        //折り上がり図の表示はct_workerが行うので表示自体はcp_worker2にカメラをセットする必要はないが、その後、画面クリックをcp_worker2が判定したりするのでcp_worker2のカメラ更新は表示と同期して行う必要がある。
        cp_worker2.setCamera(camera_of_foldedFigure);
        cp_worker2.setCam_front(camera_of_oriagari_front);
        cp_worker2.setCam_rear(camera_of_oriagari_rear);
        cp_worker2.setCam_transparent_front(camera_of_transparent_front);
        cp_worker2.setCam_transparent_rear(camera_of_transparant_rear);


        //針金図の表示
        //System.out.println("paint　+++++++++++++++++++++　針金図の表示");
        if (display_flg == 2) {
            cp_worker2.oekaki_with_camera(bufferGraphics, ip4);//折り上がり図の操作はこのcp_worker2の針金図を動かす。
        }

        //折りあがり図（表）の表示
        if (((ip4 == 0) || (ip4 == 2)) || (ip4 == 3)) {
            ct_worker.setCamera(camera_of_oriagari_front);

            //透過図の表示
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");
            if (display_flg == 3) {        // hyouji_flg;折り上がり図の表示様式の指定。１なら実際に折り紙を折った場合と同じ。２なら透過図。3なら針金図。
                ct_worker.oekaki_toukazu_with_camera(bufferGraphics, cp_worker1, cp_worker2.get(), cp_worker3.get(), i_toukazu_color, transparent_transparency);
            }

            //折り上がり図の表示************* //System.out.println("paint　+++++++++++++++++++++　折り上がり図の表示");
            if (display_flg == 5) {
                ct_worker.oekaki_foldedFigure_with_camera(bufferGraphics, cp_worker1, cp_worker2.get(), cp_worker3.get());// hyouji_flg;折り上がり図の表示様式の指定。5なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。
            }

            //折り上がり図の動かし中心の十字表示
            //System.out.println("paint　+++++++++++++++++++++　折り上がり図の動かし中心の十字表示)");
            if (i_mejirusi_hyouji == 1) {
                ct_worker.oekaki_jyuuji_with_camera(bufferGraphics);
            }
        }

        //折りあがり図（裏）の表示
        if (((ip4 == 1) || (ip4 == 2)) || (ip4 == 3)) {
            camera_of_oriagari_rear.display();
            ct_worker.setCamera(camera_of_oriagari_rear);

            //透過図の表示
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");
            if (display_flg == 3) {        // hyouji_flg;折り上がり図の表示様式の指定。１なら実際に折り紙を折った場合と同じ。２なら透過図。3なら針金図。
                ct_worker.oekaki_toukazu_with_camera(bufferGraphics, cp_worker1, cp_worker2.get(), cp_worker3.get(), i_toukazu_color, transparent_transparency);
            }

            //折り上がり図の表示************* //System.out.println("paint　+++++++++++++++++++++　折り上がり図の表示");
            if (display_flg == 5) {
                ct_worker.oekaki_foldedFigure_with_camera(bufferGraphics, cp_worker1, cp_worker2.get(), cp_worker3.get());// hyouji_flg;折り上がり図の表示様式の指定。5なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。
            }

            //折り上がり図の動かし中心の十字表示
            //System.out.println("paint　+++++++++++++++++++++　折り上がり図の動かし中心の十字表示)");
            if (i_mejirusi_hyouji == 1) {
                ct_worker.oekaki_jyuuji_with_camera(bufferGraphics);
            }
        }

        //透過図（折りあがり図表示時に追加する分）
        if ((ip4 == 3) && (display_flg == 5)) {
            // ---------------------------------------------------------------------------------
            ct_worker.setCamera(camera_of_transparent_front);
            //透過図の表示
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");
            ct_worker.oekaki_toukazu_with_camera(bufferGraphics, cp_worker1, cp_worker2.get(), cp_worker3.get(), i_toukazu_color, transparent_transparency);

            //折り上がり図の動かし中心の十字表示
            //System.out.println("paint　+++++++++++++++++++++　折り上がり図の動かし中心の十字表示)");
            if (i_mejirusi_hyouji == 1) {
                ct_worker.oekaki_jyuuji_with_camera(bufferGraphics);
            }

            // ---------------------------------------------------------------------------------
            ct_worker.setCamera(camera_of_transparant_rear);

            //透過図の表示
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");
            ct_worker.oekaki_toukazu_with_camera(bufferGraphics, cp_worker1, cp_worker2.get(), cp_worker3.get(), i_toukazu_color, transparent_transparency);

            //折り上がり図の動かし中心の十字表示
            //System.out.println("paint　+++++++++++++++++++++　折り上がり図の動かし中心の十字表示)");
            if (i_mejirusi_hyouji == 1) {
                ct_worker.oekaki_jyuuji_with_camera(bufferGraphics);
            }
            // ---------------------------------------------------------------------------------
        }


        //折り上がり図動かし時の針金図と展開図上の対応点の表示


        for (int i = 1; i <= cp_worker1.getTensuu(); i++) {
            if (cp_worker1.getPointState(i) == 1) {
                cp_worker1.oekaki_Ten_id_with_camera(bufferGraphics, i);
                //	cp_worker2.oekaki_Ten_id_with_camera(bufferGraphics,i,ip4);
            }
        }


        for (int i = 1; i <= cp_worker2.getTensuu(); i++) {
            if (cp_worker2.getPointState(i) == 1) {
                cp_worker1.oekaki_Ten_id_with_camera_green(bufferGraphics, i);
                cp_worker2.oekaki_Ten_id_with_camera(bufferGraphics, i, ip4);
            }
        }


    }


    // -------------------------------------------------------
    public void set_syukusyaku(double d0) {
        d_foldedFigure_syukusyaku_keisuu = d0;
    }//折り上がり図の縮尺係数

    public double get_syukusyaku() {
        return d_foldedFigure_syukusyaku_keisuu;
    }//折り上がり図の縮尺係数

    public void set_kaiten(double d0) {
        d_foldedFigure_kaiten_hosei = d0;
    }//折り上がり図の回転表示角度の補正角度

    public double get_kaiten() {
        return d_foldedFigure_kaiten_hosei;
    }//折り上がり図の回転表示角度の補正角度

//mmmmmmm

    //---------------------------------------------------------


    public Memo getMemo_for_svg_export() {

        Memo memo_temp = new Memo();

        //針金図のsvg
        if (display_flg == 2) {
            memo_temp.addMemo(ct_worker.getMemo_wirediagram_for_svg_export(cp_worker1, cp_worker2.get(), cp_worker3.get(), 0));//４番目の整数は０なら面の枠のみ、１なら面を塗る
        }

        //折りあがり図（表）のsvg
        if (((ip4 == 0) || (ip4 == 2)) || (ip4 == 3)) {
            ct_worker.setCamera(camera_of_oriagari_front);

            //透過図のsvg
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");
            if (display_flg == 3) {        // hyouji_flg;折り上がり図の表示様式の指定。１なら実際に折り紙を折った場合と同じ。２なら透過図。3なら針金図。
                memo_temp.addMemo(ct_worker.getMemo_wirediagram_for_svg_export(cp_worker1, cp_worker2.get(), cp_worker3.get(), 1));
            }

            //折り上がり図のsvg************* //System.out.println("paint　+++++++++++++++++++++　折り上がり図の表示");
            if (display_flg == 5) {
                //ct_worker.oekaki_oriagarizu_with_camera(bufferGraphics,cp_worker1,cp_worker2.get(),cp_worker3.get());// hyouji_flg;折り上がり図の表示様式の指定。5なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。
                memo_temp.addMemo(ct_worker.getMemo_for_svg_with_camera(cp_worker1, cp_worker2.get(), cp_worker3.get()));// hyouji_flg;折り上がり図の表示様式の指定。5なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。

            }
        }

        //折りあがり図（裏）のsvg
        if (((ip4 == 1) || (ip4 == 2)) || (ip4 == 3)) {

            ct_worker.setCamera(camera_of_oriagari_rear);

            //透過図のsvg
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");
            if (display_flg == 3) {        // hyouji_flg;折り上がり図の表示様式の指定。１なら実際に折り紙を折った場合と同じ。２なら透過図。3なら針金図。
                memo_temp.addMemo(ct_worker.getMemo_wirediagram_for_svg_export(cp_worker1, cp_worker2.get(), cp_worker3.get(), 1));
            }

            //折り上がり図のsvg************* //System.out.println("paint　+++++++++++++++++++++　折り上がり図の表示");
            if (display_flg == 5) {
                //ct_worker.oekaki_oriagarizu_with_camera(bufferGraphics,cp_worker1,cp_worker2.get(),cp_worker3.get());// hyouji_flg;折り上がり図の表示様式の指定。5なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。
                memo_temp.addMemo(ct_worker.getMemo_for_svg_with_camera(cp_worker1, cp_worker2.get(), cp_worker3.get()));// hyouji_flg;折り上がり図の表示様式の指定。5なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。

            }


        }

        //透過図（折りあがり図表示時に追加する分）
        if ((ip4 == 3) && (display_flg == 5)) {
            // ---------------------------------------------------------------------------------
            ct_worker.setCamera(camera_of_transparent_front);
            //透過図のsvg
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");

            //ct_worker.oekaki_toukazu_with_camera(bufferGraphics,cp_worker1,cp_worker2.get(),cp_worker3.get());
            //ct_worker.getMemo_toukazu_with_camera(bufferGraphics,cp_worker1,cp_worker2.get(),cp_worker3.get());


            // ---------------------------------------------------------------------------------
            ct_worker.setCamera(camera_of_transparant_rear);

            //透過図のsvg
            //System.out.println("paint　+++++++++++++++++++++　透過図の表示");

            //ct_worker.oekaki_toukazu_with_camera(bufferGraphics,cp_worker1,cp_worker2.get(),cp_worker3.get());
            //ct_worker.getMemo_toukazu_with_camera(bufferGraphics,cp_worker1,cp_worker2.get(),cp_worker3.get());

            // ---------------------------------------------------------------------------------
        }
        return memo_temp;

    }


//-----------------------------------

    void oritatami_suitei_camera_configure(Camera camera_of_orisen_nyuuryokuzu, WireFrame Ss0) {
        d_foldedFigure_syukusyaku_keisuu = camera_of_orisen_nyuuryokuzu.getCameraZoomX();
        orihime_app.text29.setText(String.valueOf(d_foldedFigure_syukusyaku_keisuu));
        orihime_app.text29.setCaretPosition(0);

        d_foldedFigure_kaiten_hosei = camera_of_orisen_nyuuryokuzu.getCameraAngle();
        orihime_app.text30.setText(String.valueOf(d_foldedFigure_kaiten_hosei));
        orihime_app.text30.setCaretPosition(0);


        System.out.println("cp_worker1.ten_of_kijyunmen_ob     " + cp_worker1.point_of_referencePlane_ob.getX());

        Point p0 = new Point();
        Point p = new Point();


        p.set(cp_worker1.point_of_referencePlane_ob);
        p0.set(camera_of_orisen_nyuuryokuzu.object2TV(p));


        double d_camera_position_x = p.getX();
        double d_camera_position_y = p.getY();
        double d_display_position_x = p0.getX();
        double d_display_position_y = p0.getY();

        camera_of_foldedFigure.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_foldedFigure.setCameraPositionX(d_camera_position_x);
        camera_of_foldedFigure.setCameraPositionY(d_camera_position_y);
        camera_of_foldedFigure.setDisplayPositionX(d_display_position_x + 20.0);
        camera_of_foldedFigure.setDisplayPositionY(d_display_position_y + 20.0);

        camera_of_oriagari_front.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_oriagari_front.setCameraPositionX(d_camera_position_x);
        camera_of_oriagari_front.setCameraPositionY(d_camera_position_y);
        camera_of_oriagari_front.setDisplayPositionX(d_display_position_x + 20.0);
        camera_of_oriagari_front.setDisplayPositionY(d_display_position_y + 20.0);

        camera_of_oriagari_rear.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_oriagari_rear.setCameraPositionX(d_camera_position_x);
        camera_of_oriagari_rear.setCameraPositionY(d_camera_position_y);
        camera_of_oriagari_rear.setDisplayPositionX(d_display_position_x + 40.0);
        camera_of_oriagari_rear.setDisplayPositionY(d_display_position_y + 20.0);

        camera_of_transparent_front.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_transparent_front.setCameraPositionX(d_camera_position_x);
        camera_of_transparent_front.setCameraPositionY(d_camera_position_y);
        camera_of_transparent_front.setDisplayPositionX(d_display_position_x + 20.0);
        camera_of_transparent_front.setDisplayPositionY(d_display_position_y + 0.0);

        camera_of_transparant_rear.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_transparant_rear.setCameraPositionX(d_camera_position_x);
        camera_of_transparant_rear.setCameraPositionY(d_camera_position_y);
        camera_of_transparant_rear.setDisplayPositionX(d_display_position_x + 40.0);
        camera_of_transparant_rear.setDisplayPositionY(d_display_position_y + 0.0);

        double d_camera_kagami = camera_of_oriagari_rear.get_camera_kagami();
        camera_of_oriagari_rear.set_camera_kagami(d_camera_kagami * -1.0);
        camera_of_transparant_rear.set_camera_kagami(d_camera_kagami * -1.0);


    }

    //-----------------------------------
    public void folding_estimated(Camera camera_of_orisen_nyuuryokuzu, WireFrame wireFrame) {//折畳み予測の最初に、cp_worker1.lineStore2pointStore(lineStore)として使う。　Ss0は、es1.get_for_oritatami()かes1.get_for_select_oritatami()で得る。
        int i_camera_estimated = 0;

        //-------------------------------折り上がり図表示用カメラの設定

        if ((i_estimated_step == 0) && (i_estimated_order <= 5)) {
            i_camera_estimated = 1;


        }

        if (i_estimated_order == 51) {
            i_estimated_order = 5;
        }
        //-------------------------------
        // suitei = estimated
        // dankai = step
        // meirei = order
        if ((i_estimated_step == 0) && (i_estimated_order == 1)) {
            estimated_initialize(); // estimated_initialize
            folding_estimated_01(wireFrame);
            i_estimated_step = 1;
            display_flg = 1;
        } else if ((i_estimated_step == 0) && (i_estimated_order == 2)) {
            estimated_initialize();
            folding_estimated_01(wireFrame);
            i_estimated_step = 1;
            display_flg = 1;
            folding_estimated_02();
            i_estimated_step = 2;
            display_flg = 2;
        } else if ((i_estimated_step == 0) && (i_estimated_order == 3)) {
            estimated_initialize();
            folding_estimated_01(wireFrame);
            i_estimated_step = 1;
            display_flg = 1;
            folding_estimated_02();
            i_estimated_step = 2;
            display_flg = 2;
            folding_estimated_03();
            i_estimated_step = 3;
            display_flg = 3;
        } else if ((i_estimated_step == 0) && (i_estimated_order == 5)) {
            estimated_initialize();
            folding_estimated_01(wireFrame);
            i_estimated_step = 1;
            display_flg = 1;
            folding_estimated_02();
            i_estimated_step = 2;
            display_flg = 2;
            folding_estimated_03();
            i_estimated_step = 3;
            display_flg = 3;
            folding_estimated_04();
            i_estimated_step = 4;
            display_flg = 4;
            folding_estimated_05();
            i_estimated_step = 5;
            display_flg = 5;
            if ((discovered_fold_cases == 0) && (different_search_flg == 0)) {
                i_estimated_step = 3;
                display_flg = 3;
            }
        } else if ((i_estimated_step == 1) && (i_estimated_order == 1)) {
        } else if ((i_estimated_step == 1) && (i_estimated_order == 2)) {
            folding_estimated_02();
            i_estimated_step = 2;
            display_flg = 2;
        } else if ((i_estimated_step == 1) && (i_estimated_order == 3)) {
            folding_estimated_02();
            i_estimated_step = 2;
            display_flg = 2;
            folding_estimated_03();
            i_estimated_step = 3;
            display_flg = 3;
        } else if ((i_estimated_step == 1) && (i_estimated_order == 5)) {
            folding_estimated_02();
            i_estimated_step = 2;
            display_flg = 2;
            folding_estimated_03();
            i_estimated_step = 3;
            display_flg = 3;
            folding_estimated_04();
            i_estimated_step = 4;
            display_flg = 4;
            folding_estimated_05();
            i_estimated_step = 5;
            display_flg = 5;
            if ((discovered_fold_cases == 0) && (different_search_flg == 0)) {
                i_estimated_step = 3;
                display_flg = 3;
            }
        } else if ((i_estimated_step == 2) && (i_estimated_order == 1)) {
        } else if ((i_estimated_step == 2) && (i_estimated_order == 2)) {
        } else if ((i_estimated_step == 2) && (i_estimated_order == 3)) {
            folding_estimated_03();
            i_estimated_step = 3;
            display_flg = 3;
        } else if ((i_estimated_step == 2) && (i_estimated_order == 5)) {
            folding_estimated_03();
            i_estimated_step = 3;
            display_flg = 3;
            folding_estimated_04();
            i_estimated_step = 4;
            display_flg = 4;
            folding_estimated_05();
            i_estimated_step = 5;
            display_flg = 5;
            if ((discovered_fold_cases == 0) && (different_search_flg == 0)) {
                i_estimated_step = 3;
                display_flg = 3;
            }
        } else if ((i_estimated_step == 3) && (i_estimated_order == 1)) {
        } else if ((i_estimated_step == 3) && (i_estimated_order == 2)) {
            display_flg = 2;
        } else if ((i_estimated_step == 3) && (i_estimated_order == 3)) {
            display_flg = 3;
        } else if ((i_estimated_step == 3) && (i_estimated_order == 5)) {
            folding_estimated_04();
            i_estimated_step = 4;
            display_flg = 4;
            folding_estimated_05();
            i_estimated_step = 5;
            display_flg = 5;
            if ((discovered_fold_cases == 0) && (different_search_flg == 0)) {
                i_estimated_step = 3;
                display_flg = 3;
            }
        } else if ((i_estimated_step == 5) && (i_estimated_order == 1)) {
        } else if ((i_estimated_step == 5) && (i_estimated_order == 2)) {
            display_flg = 2;
        } else if ((i_estimated_step == 5) && (i_estimated_order == 3)) {
            display_flg = 3;
        } else if ((i_estimated_step == 5) && (i_estimated_order == 5)) {
            display_flg = 5;
        } else if ((i_estimated_step == 5) && (i_estimated_order == 6)) {
            folding_estimated_05();
            i_estimated_step = 5;
            display_flg = 5;
        }

        if (i_camera_estimated == 1) {
            oritatami_suitei_camera_configure(camera_of_orisen_nyuuryokuzu, wireFrame);
        }
    }

    //--------------------------------------------------------------------------
    public void oritatami_suitei_2col(Camera camera_of_orisen_nyuuryokuzu, WireFrame Ss0) {//２色塗りわけ展開図

        //-------------------------------折り上がり図表示用カメラの設定

        //	if( (i_suitei_dankai==0)&&(i_suitei_meirei<=5) ){

        d_foldedFigure_syukusyaku_keisuu = camera_of_orisen_nyuuryokuzu.getCameraZoomX();
        orihime_app.text29.setText(String.valueOf(d_foldedFigure_syukusyaku_keisuu));
        orihime_app.text29.setCaretPosition(0);

        d_foldedFigure_kaiten_hosei = camera_of_orisen_nyuuryokuzu.getCameraAngle();
        orihime_app.text30.setText(String.valueOf(d_foldedFigure_kaiten_hosei));
        orihime_app.text30.setCaretPosition(0);

        double d_display_position_x = camera_of_orisen_nyuuryokuzu.getDisplayPositionX();
        double d_display_position_y = camera_of_orisen_nyuuryokuzu.getDisplayPositionY();

        camera_of_foldedFigure.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_foldedFigure.setDisplayPositionX(d_display_position_x + 20.0);
        camera_of_foldedFigure.setDisplayPositionY(d_display_position_y + 20.0);

        camera_of_oriagari_front.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_oriagari_front.setDisplayPositionX(d_display_position_x + 20.0);
        camera_of_oriagari_front.setDisplayPositionY(d_display_position_y + 20.0);

        camera_of_oriagari_rear.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_oriagari_rear.setDisplayPositionX(d_display_position_x + 40.0);
        camera_of_oriagari_rear.setDisplayPositionY(d_display_position_y + 20.0);

        camera_of_transparent_front.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_transparent_front.setDisplayPositionX(d_display_position_x + 20.0);
        camera_of_transparent_front.setDisplayPositionY(d_display_position_y + 0.0);

        camera_of_transparant_rear.setCamera(camera_of_orisen_nyuuryokuzu);
        camera_of_transparant_rear.setDisplayPositionX(d_display_position_x + 40.0);
        camera_of_transparant_rear.setDisplayPositionY(d_display_position_y + 0.0);

        double d_camera_kagami = camera_of_oriagari_rear.get_camera_kagami();
        camera_of_oriagari_rear.set_camera_kagami(d_camera_kagami * -1.0);
        camera_of_transparant_rear.set_camera_kagami(d_camera_kagami * -1.0);
        //	}

        //-------------------------------
        estimated_initialize();
        folding_estimated_01(Ss0);
        i_estimated_step = 1;
        display_flg = 1;
        oritatami_suitei_02col();
        i_estimated_step = 2;
        display_flg = 2;
        folding_estimated_03();
        i_estimated_step = 3;
        display_flg = 3;
        folding_estimated_04();
        i_estimated_step = 4;
        display_flg = 4;
        folding_estimated_05();
        i_estimated_step = 5;
        display_flg = 5;
        i_estimated_step = 10;
        //if((OZ.hakkenn_sita_kazu==0)&&(OZ.betu_sagasi_flg==0)){ OZ.i_suitei_dankai=3; OZ.hyouji_flg=3;}

        //return 1000;
    }
//-----------------------------------


    //-------------------------------bbbbbbb----
    public int folding_estimated_01(WireFrame wireFrame) {
        System.out.println("＜＜＜＜＜oritatami_suitei_01;開始");
        bulletinBoard.write("<<<<oritatami_suitei_01;  start");
        //マウスの入力でes1の中に作った線分集合をcp_worker1に渡し、点集合(展開図に相当)にする
        // Pass the line segment set created in es1 to cp_worker1 by mouse input and make it a point set (corresponding to the development view).
        cp_worker1.lineStore2pointStore(wireFrame);
        ip3 = cp_worker1.set_referencePlane_id(ip3);
        ip3 = cp_worker1.set_referencePlane_id(orihime_app.point_of_referencePlane_old);//20180222折り線選択状態で折り畳み推定をする際、以前に指定されていた基準面を引き継ぐために追加

        return 1000;
    }


    //-----------------------------------
    public int folding_estimated_02() {
        System.out.println("＜＜＜＜＜oritatami_suitei_02;開始");
        bulletinBoard.write("<<<<oritatami_suitei_02;  start");
        //cp_worker1が折りたたみを行い、できた針金図をcp_worker2に渡す。
        //cp_worker1 folds and passes the resulting wire diagram to cp_worker2.
        //cp_worker2が折りあがった形を少しだけ変形したいような場合に働く。
        //It works when you want to slightly deform the folded shape of cp_worker2.
        cp_worker2.set(cp_worker1.folding());
        orihime_app.bulletinBoard.write("<<<<oritatami_suitei_02; end");

        //cp_worker2.Iti_sitei(0.0 , 0.0);点集合の平均位置を全点の重心にする。
        //  if(ip4==1){ cp_worker2.uragaesi();}
        // cp_worker2.set( cp_worker2.oritatami())  ;//折り畳んだ針金図を、折り開きたい場合の操作
        //ここまでで針金図はできていて、cp_worker2が持っている。これは、マウスで操作、変形できる。
        return 1000;
    }

    //-----------------------------------
    public int oritatami_suitei_02col() {//20171225　２色塗りわけをするための特別推定（折り畳み位置を推定しない）
        System.out.println("＜＜＜＜＜oritatami_suitei_02;開始");
        bulletinBoard.write("<<<<oritatami_suitei_02;  start");
        cp_worker2.set(cp_worker1.surface_position_request());
        orihime_app.bulletinBoard.write("<<<<oritatami_suitei_02; end");
        return 1000;
    }

    //-----------------------------------
    public int folding_estimated_03() {
        System.out.println("＜＜＜＜＜oritatami_suitei_03;開始");
        bulletinBoard.write("<<<<oritatami_suitei_03;  start");
        //cp_worker2は折る前の展開図の面を保持した点集合を持っている。
        //折りたたんだ場合の面の上下関係を推定するにはcp_worker2の持つ針金図に応じて面を
        //細分した（細分した面をSmenと言うことにする）点集合を使う。
        //このSmen面に分割した点集合はcp_worker3が持つようにする。
        //cp_worker2の持つ点集合をcp_worker3に渡す前に、cp_worker2の持つ点集合は棒が重なっていたりするかもしれないので、
        //いったんbb_workerに渡して線分集合として整理する。
        // cp_worker2 has a set of points that holds the faces of the unfolded view before folding.
        // To estimate the vertical relationship of the surface when folded, set the surface according to the wire diagram of cp_worker2.
        // Use a set of subdivided points (let's call the subdivided surface Smen).
        // Let cp_worker3 have the set of points divided into this Smen plane.
        // Before passing the point set of cp_worker2 to cp_worker3, the point set of cp_worker2 may have overlapping bars, so
        // Pass it to bb_worker and organize it as a set of line segments.
        System.out.println("＜＜＜＜＜oritatami_suitei_03()_____基本枝職人bb_workerはcp_worker2から線分集合（針金図からできたもの）を受け取り、整理する。");
        bb_worker.set(cp_worker2.getLineStore());
        System.out.println("＜＜＜＜＜oritatami_suitei_03()_____基本枝職人bb_workerがbb_worker.bunkatu_seiri_for_Smen_hassei;実施。");
        bb_worker.split_arrangement_for_Smen_hassei();//重なった線分や交差する線分折り畳み推定などで得られる針金図の整理
        //展開図職人cp_worker3はbb_workerから点集合（cp_worker2の持つ針金図を整理したもの）を受け取り、Smenに分割する。
        System.out.println("＜＜＜＜＜oritatami_suitei_03()_____展開図職人cp_worker3はbb_workerから整理された線分集合を受け取り、Smenに分割する。");
        System.out.println("　　　oritatami_suitei_03()のcp_worker3.Senbunsyuugou2Tensyuugou(bb_worker.get());実施");
        cp_worker3.lineStore2pointStore(bb_worker.get());

        System.out.println("＜＜＜＜＜oritatami_suitei_03()_____上下表職人ct_workerは、展開図職人cp_worker3から点集合を受け取り、Smenを設定する。");
        ct_worker.Smen_configure(cp_worker1, cp_worker2.get(), cp_worker3.get());
        //If you want to make a transparent map up to this point, you can. The transmission diagram is a Smen diagram with density added.
        return 1000;
    }

    //-----------------------------------
    public int folding_estimated_04() {
        System.out.println("＜＜＜＜＜oritatami_suitei_04;開始");
        bulletinBoard.write("<<<<oritatami_suitei_04;  start");
        //Make an upper and lower table of faces (faces in the unfolded view before folding).
        // This includes the point set of cp_worker2 (which has information on the positional relationship of the faces after folding).
        // Use the point set of cp_worker3 (which has the information of Smen whose surface is subdivided in the wire diagram).
        // Also, use the information on the positional relationship of the surface when folded, which cp_worker1 has.
        System.out.println("＜＜＜＜＜oritatami_suitei_04()_____上下表職人ct_workerが面(折りたたむ前の展開図の面のこと)の上下表を作る。");

        ip1 = 0;
        different_search_flg = 0;
        ip1 = ct_worker.ClassTable_configure(cp_worker1, cp_worker2.get(), cp_worker3.get());   //ip1=折った後の表裏が同じ面が隣接するという誤りがあれば0を、無ければ1000を格納する変数。
        if (ip1 == 1000) {
            ip1 = 1000;
            different_search_flg = 1;
        }
        discovered_fold_cases = 0;
        System.out.println("＜＜＜＜＜oritatami_suitei_04()____終了");
        return 1000;
    }


    //-----------------------------------
    public int folding_estimated_05() {
        System.out.println("＜＜＜＜＜oritatami_suitei_05()_____上下表職人ct_workerがct_worker.kanou_kasanari_sagasi()実施。");
        orihime_app.bulletinBoard.write("<<<<oritatami_suitei_05()  ___ct_worker.kanou_kasanari_sagasi()  start");

        if ((i_estimated_step == 4) || (i_estimated_step == 5)) {
            if (different_search_flg == 1) {

                ip2 = ct_worker.possible_overlapping_search();//ip2=上下表職人が折り畳み可能な重なり方を探した際に、可能な重なり方がなければ0を、可能な重なり方があれば1000を格納する変数。

                if (ip2 == 1000) {
                    discovered_fold_cases = discovered_fold_cases + 1;
                }

                ip5 = ct_worker.next(ct_worker.getSmen_yuukou_suu());//次の重なり探しの準備//ip5=0なら新たにsusumu余地がなかった。0以外なら変化したSmenのidの最も小さい番号
            }
        }
        orihime_app.bulletinBoard.clear();

        text_kekka = "Number of found solutions = " + discovered_fold_cases + "  ";

        different_search_flg = 0;
        if ((ip2 == 1000) && (ip5 > 0)) {
            different_search_flg = 1;
        }

        if (different_search_flg == 0) {
            text_kekka = text_kekka + " There is no other solution. ";
        }

        return 1000;
    }

//int oritatami_suitei_06(){return 1000;}


    public void toukazu_color_sage() {
        transparent_transparency = transparent_transparency / 2;
        if (transparent_transparency < 1) {
            transparent_transparency = 1;
        }
    }


    public void toukazu_color_age() {
        transparent_transparency = transparent_transparency * 2;
        if (transparent_transparency > 64) {
            transparent_transparency = 64;
        }
    }    //20180819バグ修正　透過度の最大値がこれまで128で、プログラムで線の描画時に２倍するとく、256となり、透過度の上限255オーバーで、オリヒメ自体が
    //フリーズした。これは、128を127の変えることでもフリーズはなくなるが、透過度は２の倍数にしておかないと、2分の一にしたとき値がずれるかもしれないので、透過度の最大値は64としておくことにする。


    private Point p_m_left_on = new Point();//Coordinates when the left mouse button is pressed
    private int i_nanini_near = 0;//Point p is close to the point in the development view = 1, close to the point in the folded view = 2, not close to either = 0
    private int i_most_near_PointId;
    private int i_point_selection = 0;//Both cp_worker1 and cp_worker2 are not selected (situation i_point_selection = 0), cp_worker1 is selected and cp_worker2 is not selected (situation i_point_selection = 1), and the vertex is cp_worker2 selected (situation i_point_selection = 2).
    private Point move_previous_selection_point = new Point();//動かす前の選択した点の座標


    //-----------------------------------------------------------------------------------------------------uuuuuuu--
    public void oriagari_sousa_mouse_on(Point p) {//Work when the left mouse button is pressed in the fold-up diagram operation
        if (i_oriagari_sousa_mode == 1) {
            oriagari_sousa_mouse_on_1(p);
        }
        if (i_oriagari_sousa_mode == 2) {
            oriagari_sousa_mouse_on_2(p);
        }
    }

    public void oriagari_sousa_mouse_drag(Point p) {//折り上がり図操作でマウスの左ボタンを押したままドラッグしたときの作業
        if (i_oriagari_sousa_mode == 1) {
            oriagari_sousa_mouse_drag_1(p);
        }
        if (i_oriagari_sousa_mode == 2) {
            oriagari_sousa_mouse_drag_2(p);
        }
    }


    public void oriagari_sousa_mouse_off(Point p) {//折り上がり図操作でマウスの左ボタンを離したときの作業
        if (i_oriagari_sousa_mode == 1) {
            oriagari_sousa_mouse_off_1(p);
        }
        if (i_oriagari_sousa_mode == 2) {
            oriagari_sousa_mouse_off_2(p);
        }
    }


    //  =================================================================================================================================
    //-----------------------------------------------------------------------------------------------------uuuuuuu--
    public void oriagari_sousa_mouse_on_1(Point p) {//折り上がり図操作でマウスの左ボタンを押したときの作業   折りずらし機能

        p_m_left_on.set(new Point(p.getX(), p.getY()));

        cp_worker2.setCamera(camera_of_foldedFigure);
        cp_worker2.setCam_front(camera_of_oriagari_front);
        cp_worker2.setCam_rear(camera_of_oriagari_rear);

        //i_mottomo_tikai_Tenidにpに最も近い点の番号を格納。近い点がまったくない場合はi_mottomo_tikai_Tenid=0
        i_nanini_near = 0;//展開図の点に近い=1、折り上がり図の点に近い=2、どちらにも近くない=0
        i_most_near_PointId = cp_worker1.mottomo_tikai_Tenid_with_camera(p);
        if (i_most_near_PointId != 0) {
            i_nanini_near = 1;
        }
        if (cp_worker2.mottomo_tikai_Tenid_with_camera(p, ip4) != 0) {
            if (cp_worker1.mottomo_tikai_Ten_kyori_with_camera(p) > cp_worker2.mottomo_tikai_Ten_kyori_with_camera(p, ip4)) {
                i_most_near_PointId = cp_worker2.mottomo_tikai_Tenid_with_camera(p, ip4);
                i_nanini_near = 2;
            }
        }//i_mottomo_tikai_Tenidにpに最も近い点の番号を格納 ここまで

        move_previous_selection_point.set(cp_worker2.getTen(i_most_near_PointId));


        System.out.println("i_nanini_tikai = " + i_nanini_near);

        if (i_nanini_near == 1) {

            //i_ten_sentakuを決める
            i_point_selection = 0;
            if (cp_worker1.getPointState(i_most_near_PointId) == 1) {
                i_point_selection = 1;
            }
            if (cp_worker2.getPointState(i_most_near_PointId) == 1) {
                i_point_selection = 2;
            }
            //i_ten_sentakuを決める  ここまで


            if (i_point_selection == 0) {
                set_all_ten_sentaku_0();
                //折り上がり図でi_mottomo_tikai_Tenidと同じ位置の点の番号を求め、cp_worker1でその番号の点を選択済みにする
                Point ps = new Point();
                ps.set(cp_worker2.getTen(i_most_near_PointId));
                for (int i = 1; i <= cp_worker2.getTensuu(); i++) {
                    if (ps.distance(cp_worker2.getTen(i)) < 0.0000001) {
                        cp_worker1.setPointState1(i);
                    }
                }
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 1) {
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 2) {
                cp_worker2.changePointState(i_most_near_PointId);
            }


        }

        if (i_nanini_near == 2) {

            //i_ten_sentakuを決める
            i_point_selection = 0;
            if (cp_worker1.getPointState(i_most_near_PointId) == 1) {
                i_point_selection = 1;
                if (cp_worker2.getSelectedPointsNum() > 0) {
                    i_point_selection = 2;
                }    //折図上で指定した点で、そこに重なるいずれかの点がcp_worker2で選択されている。要するに折図上の緑表示されている点を選んだ状態
            }
            //i_ten_sentakuを決める  ここまで
            System.out.println("i_ten_sentaku = " + i_point_selection);

            if (i_point_selection == 0) {
                set_all_ten_sentaku_0();

                //折り上がり図でi_mottomo_tikai_Tenidと同じ位置の点の番号を求め、cp_worker1でその番号の点を選択済みにする
                Point ps = new Point();
                ps.set(cp_worker2.getTen(i_most_near_PointId));
                for (int i = 1; i <= cp_worker2.getTensuu(); i++) {
                    if (ps.distance(cp_worker2.getTen(i)) < 0.0000001) {
                        cp_worker1.setPointState1(i);
                    }
                }
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 1) {
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 2) {
                //cp_worker2.change_ten_sentaku(i_mottomo_tikai_Tenid);
            }


            if (i_oriagari_sousa_mode == 1) {

                hyouji_flg_backup = display_flg;   //20180216  //hyouji_flgは、折り上がり図の表示様式の指定。4なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。
                display_flg = 2;            //20180216
            }


        }


        System.out.println("cp_worker1.get_ten_sentakusuu() = " + cp_worker1.getSelectedPointsNum());
        System.out.println("cp_worker2.get_ten_sentakusuu() = " + cp_worker2.getSelectedPointsNum());


    }

    //-------------
    public void oriagari_sousa_mouse_drag_1(Point p) {//折り上がり図操作でマウスの左ボタンを押したままドラッグしたときの作業

        cp_worker2.setCamera(camera_of_foldedFigure);
        cp_worker2.setCam_front(camera_of_oriagari_front);
        cp_worker2.setCam_rear(camera_of_oriagari_rear);

        if (i_nanini_near == 1) {
        }

        if (i_nanini_near == 2) {
            cp_worker2.mDragged_sentakuten_ugokasi_with_camera(move_previous_selection_point, p_m_left_on, p, ip4);


            if (i_oriagari_sousa_mode == 2) {
                folding_estimated_03();//20180216
            }
        }

    }

    //-------------
    public void oriagari_sousa_mouse_off_1(Point p) {//折り上がり図操作でマウスの左ボタンを離したときの作業
        cp_worker2.setCamera(camera_of_foldedFigure);
        cp_worker2.setCam_front(camera_of_oriagari_front);
        cp_worker2.setCam_rear(camera_of_oriagari_rear);

        if (i_nanini_near == 1) {
        }

        if (i_nanini_near == 2) {

            display_flg = hyouji_flg_backup;//20180216

            cp_worker2.mReleased_sentakuten_ugokasi_with_camera(move_previous_selection_point, p_m_left_on, p, ip4);
            if (p_m_left_on.distance(p) > 0.0000001) {
                kiroku();
                i_estimated_step = 2;

                if (display_flg == 2) {
                }

                if (display_flg == 5) {
                    i_estimated_order = 5;
                    orihime_app.oritatami_suitei();
                }//オリジナル 20180124 これ以外だと、表示いったんもどるようでうざい
            }


            cp_worker1.setAllPointState0();
            //折り上がり図でi_mottomo_tikai_Tenidと同じ位置の点の番号を求め、cp_worker1でその番号の点を選択済みにする
            Point ps = new Point();
            ps.set(cp_worker2.getTen(i_most_near_PointId));
            for (int i = 1; i <= cp_worker2.getTensuu(); i++) {
                if (ps.distance(cp_worker2.getTen(i)) < 0.0000001) {
                    cp_worker1.setPointState1(i);
                }
            }
        }
    }

    //-------------------------------------------------------------------------------------------------------
//  =================================================================================================================================
//  ==========折り上がり図のまま変形操作===========================================================================================================
    //-----------------------------------------------------------------------------------------------------uuuuuuu--
    public void oriagari_sousa_mouse_on_2(Point p) {//折り上がり図操作でマウスの左ボタンを押したときの作業

        p_m_left_on.set(new Point(p.getX(), p.getY()));

        cp_worker2.setCamera(camera_of_foldedFigure);
        cp_worker2.setCam_front(camera_of_oriagari_front);
        cp_worker2.setCam_rear(camera_of_oriagari_rear);

        //i_mottomo_tikai_Tenidにpに最も近い点の番号を格納。近い点がまったくない場合はi_mottomo_tikai_Tenid=0
        i_nanini_near = 0;//展開図の点に近い=1、折り上がり図の点に近い=2、どちらにも近くない=0
        i_most_near_PointId = cp_worker1.mottomo_tikai_Tenid_with_camera(p);
        if (i_most_near_PointId != 0) {
            i_nanini_near = 1;
        }
        if (cp_worker2.mottomo_tikai_Tenid_with_camera(p, ip4) != 0) {
            if (cp_worker1.mottomo_tikai_Ten_kyori_with_camera(p) > cp_worker2.mottomo_tikai_Ten_kyori_with_camera(p, ip4)) {
                i_most_near_PointId = cp_worker2.mottomo_tikai_Tenid_with_camera(p, ip4);
                i_nanini_near = 2;
            }
        }//i_mottomo_tikai_Tenidにpに最も近い点の番号を格納 ここまで

        move_previous_selection_point.set(cp_worker2.getTen(i_most_near_PointId));


        System.out.println("i_nanini_tikai = " + i_nanini_near);

        if (i_nanini_near == 1) {

            //i_ten_sentakuを決める
            i_point_selection = 0;
            if (cp_worker1.getPointState(i_most_near_PointId) == 1) {
                i_point_selection = 1;
            }
            if (cp_worker2.getPointState(i_most_near_PointId) == 1) {
                i_point_selection = 2;
            }
            //i_ten_sentakuを決める  ここまで


            if (i_point_selection == 0) {
                set_all_ten_sentaku_0();
                //折り上がり図でi_mottomo_tikai_Tenidと同じ位置の点の番号を求め、cp_worker1でその番号の点を選択済みにする
                Point ps = new Point();
                ps.set(cp_worker2.getTen(i_most_near_PointId));
                for (int i = 1; i <= cp_worker2.getTensuu(); i++) {
                    if (ps.distance(cp_worker2.getTen(i)) < 0.0000001) {
                        cp_worker1.setPointState1(i);
                    }
                }
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 1) {
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 2) {
                cp_worker2.changePointState(i_most_near_PointId);
            }


        }

        if (i_nanini_near == 2) {

            //i_ten_sentakuを決める
            i_point_selection = 0;
            if (cp_worker1.getPointState(i_most_near_PointId) == 1) {
                i_point_selection = 1;
                if (cp_worker2.getSelectedPointsNum() > 0) {
                    i_point_selection = 2;
                }    //折図上で指定した点で、そこに重なるいずれかの点がcp_worker2で選択されている。要するに折図上の緑表示されている点を選んだ状態
            }
            //i_ten_sentakuを決める  ここまで
            System.out.println("i_ten_sentaku = " + i_point_selection);

            if (i_point_selection == 0) {
                set_all_ten_sentaku_0();

                //折り上がり図でi_mottomo_tikai_Tenidと同じ位置の点の番号を求め、cp_worker1でその番号の点を選択済みにする
                Point ps = new Point();
                ps.set(cp_worker2.getTen(i_most_near_PointId));
                for (int i = 1; i <= cp_worker2.getTensuu(); i++) {
                    if (ps.distance(cp_worker2.getTen(i)) < 0.0000001) {
                        cp_worker1.setPointState1(i);
                    }
                }
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 1) {
                cp_worker2.changePointState(i_most_near_PointId);
            } else if (i_point_selection == 2) {
                //cp_worker2.change_ten_sentaku(i_mottomo_tikai_Tenid);
            }


            if (i_oriagari_sousa_mode == 1) {

                hyouji_flg_backup = display_flg;   //20180216  //hyouji_flgは、折り上がり図の表示様式の指定。4なら実際に折り紙を折った場合と同じ。3なら透過図。2なら針金図。
                display_flg = 2;            //20180216
            }


        }


        System.out.println("cp_worker1.get_ten_sentakusuu() = " + cp_worker1.getSelectedPointsNum());
        System.out.println("cp_worker2.get_ten_sentakusuu() = " + cp_worker2.getSelectedPointsNum());


    }

    //-------------
    public void oriagari_sousa_mouse_drag_2(Point p) {//折り上がり図操作でマウスの左ボタンを押したままドラッグしたときの作業

        cp_worker2.setCamera(camera_of_foldedFigure);
        cp_worker2.setCam_front(camera_of_oriagari_front);
        cp_worker2.setCam_rear(camera_of_oriagari_rear);

        if (i_nanini_near == 1) {
        }

        if (i_nanini_near == 2) {
            cp_worker2.mDragged_sentakuten_ugokasi_with_camera(move_previous_selection_point, p_m_left_on, p, ip4);


            if (i_oriagari_sousa_mode == 2) {
                folding_estimated_03();//20180216
            }
        }

    }

    //-------------
    public void oriagari_sousa_mouse_off_2(Point p) {//折り上がり図操作でマウスの左ボタンを離したときの作業
        cp_worker2.setCamera(camera_of_foldedFigure);
        cp_worker2.setCam_front(camera_of_oriagari_front);
        cp_worker2.setCam_rear(camera_of_oriagari_rear);

        if (i_nanini_near == 1) {
        }

        if (i_nanini_near == 2) {
            cp_worker2.mReleased_sentakuten_ugokasi_with_camera(move_previous_selection_point, p_m_left_on, p, ip4);
            if (p_m_left_on.distance(p) > 0.0000001) {
                kiroku();
                //if(cp_worker2.get_ten_sentakusuu()!=0){
                i_estimated_step = 2;


                if (i_oriagari_sousa_mode == 1) {
                    display_flg = hyouji_flg_backup;//20180216
                }
                if (display_flg == 2) {
                }


//if(i_oriagari_sousa_mode==1){
                //if(hyouji_flg==5){i_suitei_meirei=5;orihime_ap.oritatami_suitei();}//オリジナル 20180124 これ以外だと、表示いったんもどるようでうざい
//}
//if(i_oriagari_sousa_mode==2){
                folding_estimated_03();//20180216
//}


            }
            //cp_worker2. set_all_ten_sentaku_0();

            //}
            cp_worker1.setAllPointState0();
            //折り上がり図でi_mottomo_tikai_Tenidと同じ位置の点の番号を求め、cp_worker1でその番号の点を選択済みにする
            Point ps = new Point();
            ps.set(cp_worker2.getTen(i_most_near_PointId));
            for (int i = 1; i <= cp_worker2.getTensuu(); i++) {
                if (ps.distance(cp_worker2.getTen(i)) < 0.0000001) {
                    cp_worker1.setPointState1(i);
                }
            }


        }
    }


    //-------------------------------------------------------------------------------------------------------
//  =================================================================================================================================


    public void kiroku() {
        cp_worker2.kiroku();
    }

    public void redo() {
        cp_worker2.redo();
        folding_estimated_03();
    }

    public void undo() {
        cp_worker2.undo();
        folding_estimated_03();
    }

    //--------------------
    public void set_all_ten_sentaku_0() {
        cp_worker1.setAllPointState0();
        cp_worker2.setAllPointState0();
    }
    //--------------------
}
