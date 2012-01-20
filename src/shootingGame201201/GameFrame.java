package shootingGame201201;

import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame { //JFrame
	/* よくわからｎ */
	//private static final long serialVersionUID = 1L;
	
	/* 変数 */
	private BufferedImage pictureImage;
	
	/* main関数 */
	public static void main(String[] args) {
		new GameFrame();
		System.out.println("動作てすと: GameFrame/main");
	}
	
	/* 実装 */
	GameFrame() {
		/* 画面設定 */
//		setTitle("CPSF: Shooting Game -ver0.6-"); //ウィンドウタイトル
		super("CPSF: Shooting Game -ver0.6-");
		setBounds(100, 100, 480, 740); //setSize(480,740);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //クローズ処理
		
		/* アイコン設定 */ /*
		ImageIcon icon = new ImageIcon("./icon.png");
	    setIconImage(icon.getImage());
		*/
		
		/* メニューバー */ //キーボードアクセラレータ参考URI: http://www.javadrive.jp/tutorial/jmenu/
		JMenuBar menubar = new JMenuBar();
		JMenu menu1 = new JMenu("メニュー");	JMenu menu2 = new JMenu("ゲーム設定");
		JMenu menu3 = new JMenu("解像度とか");	JMenu menu4 = new JMenu("ヘルプ");
			
			//(メニューバーの第1階層)
			menubar.add(menu1);	menubar.add(menu2); menubar.add(menu3);
//			menubar.add(Box.createRigidArea(new Dimension(20,1))); //メニュー間に余白を作れる
			menubar.add(Box.createHorizontalGlue()); menubar.add(menu4);
			//(メニューバーの第2階層)
				//[メニュー]
				JMenuItem menuitem1_1 = new JMenuItem("CPSF/Shooting Gameについて");
				JMenuItem menuitem1_2 = new JMenuItem("環境設定");
				JMenuItem menuitem1_3 = new JMenuItem("スコア送信とか？");
				JMenuItem menuitem1_4 = new JMenuItem("ゲームを終了");
				menu1.add(menuitem1_1);	menuitem1_1.setEnabled(false);	menu1.addSeparator(); //セパレータ
				menu1.add(menuitem1_2);	menuitem1_2.setEnabled(false);	menu1.addSeparator();
				menu1.add(menuitem1_3);	menuitem1_3.setEnabled(false);	menu1.addSeparator();
				menu1.add(menuitem1_4);
				//[ゲーム設定]
				JMenuItem menuitem2_1 = new JMenuItem("(まだ)");
				menu2.add(menuitem2_1); menuitem2_1.setEnabled(false);
				//[解像度みたいな]
				JMenuItem menuitem3_1 = new JMenuItem("(まだ)");
				menu3.add(menuitem3_1); menuitem3_1.setEnabled(false);
				//ヘルプ
				JMenuItem menuitem4_1 = new JMenuItem("(まだ)");
				menu4.add(menuitem4_1); menuitem4_1.setEnabled(false);
		setJMenuBar(menubar);
		
		/* レイアウト構成 */
		//Container pane1 = getContentPane(); //ContentPane取得
		//pane1.setLayout(new BorderLayout()); //コンポーネントの追加
		
		//キャンバスを生成
		MyCanvas canv = new MyCanvas();
		add(canv);

		/* 画像表示テスト */ /*
		ImageIcon icon1 = new ImageIcon("./my_ship.jpg");
		JLabel label1 = new JLabel(icon1);
		pane1.add(label1);
		*/
		
	    setVisible(true); //表示を有効化
	    
		//ゲームデータの初期化
		canv.init();
		//スレッドを作成
		canv.initThread();
	}
}
