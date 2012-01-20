package shootingGame201201;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/* ---タスクリスト---
 * ・敵も弾を撃つように
 * 
 * ・敵の動きのタイプのバリエーション
 * ・全体的に画像がどれも大きいからちょっとずつ小さくする
 * ・敵&敵弾の実装
 * ・動きに合わせて背景も少し移動
 * ・爆弾とか
 * ・一時停止
 * ・残り機数の概念
 * ・体力ゲージの概念
 */

public class MyCanvas extends Canvas implements Runnable {
	/****************************************************
	 * 変数とかクラス
	 ****************************************************/
	/*----ゲーム全体に関わる変数----*/
		//時間経過カウント
		int counter;
		//ゲームスコアと最高スコア
		public int score;
		public int score_max;
		public int level;
		//ゲームのモードと#define
		int gameSceneSelecter;
		static final int GAME_TITLE = 0;
		static final int GAME_MAINGAME = 1;
		static final int GAME_GAMEOVER = 2;
	/*----ウィンドウとか描画関係----*/
		//ウィンドウサイズとゲームスピードの設定
		static final int WIDTH = 480;
		static final int HEIGHT = 700;
		static final int GAMESPEED = 15; //描画ループの間隔(15ミリ秒=0.015秒)
		//描画関係
		Image imgBuf;
		Graphics gBuf;
		Font titleFont = new Font("SansSerif", Font.BOLD, 28); //SansSerif
		Font subtitleFont = new Font("SansSerif", Font.PLAIN, 16); //SansSerif
		Font scoreFont = new Font("popstarregular", Font.PLAIN, 12);
	/*----その他のクラスとか変数----*/
		//背景画像
		Image background_img;
		//キー監視クラス
		KeyboardListener keyboard;
		//自機クラス
		DrawMyShip myShip;
		boolean myShip_Visible;
		//敵機クラス
		static final int ENEMY_SHIP_MAX = 80; //敵の最大数
		DrawEnemyShip[] enemyShip;
		boolean enemyShip_Visible[] = new boolean[ENEMY_SHIP_MAX];
		int enemyShip_count;
		//自分の弾クラス
		static final int MY_BULLET_MAX = 100; //自分が一気に撃てる弾の最大数
		DrawMyBullet[] drawMyBullet;
		boolean myBullet_Visible[] = new boolean[MY_BULLET_MAX];
		int myBullet_count;
		//敵の弾クラス
			////
		//爆発生成クラス
		static final int EXPLOSION_MAX = 40; //爆発の最大数
		DrawExplosion[] drawExplosion;
		boolean ExplosionVisible[] = new boolean[EXPLOSION_MAX];
		int explosion_count;
		
	/*
	//自機関係
	static final int MOVE = 4; //自機の動く距離(速さ)の指定
	int[] myShipXY = {200, 500}; //自機の座標(200,400)
	int[] myShipSize = {162/3, 210/3}; //自機の大きさ(自動取得したいけど分からないから取り敢えず)
	Image img_myShip; //自機画像(素材-> http://maglog.jp/layer79ray97/Article1191293.html)
	*/
	
	/****************************************************
	 * コストラクタ
	 ****************************************************/
	MyCanvas() {
		int i;
		//キー入力クラス
		keyboard = new KeyboardListener();
		addKeyListener(keyboard);
		//自機クラス
		myShip = new DrawMyShip();
		//敵機クラス
		enemyShip = new DrawEnemyShip[ENEMY_SHIP_MAX];
		for (i=0; i<ENEMY_SHIP_MAX; i++) enemyShip[i] = new DrawEnemyShip();
		enemyShip_count = 0;
		//爆発生成クラス
		drawExplosion = new DrawExplosion[EXPLOSION_MAX];
		for (i=0; i<EXPLOSION_MAX; i++) drawExplosion[i] = new DrawExplosion();
		explosion_count = 0;
		//自分の弾クラス
		drawMyBullet = new DrawMyBullet[MY_BULLET_MAX];
		for (i=0; i<MY_BULLET_MAX; i++) drawMyBullet[i] = new DrawMyBullet();
		myBullet_count = 0;
		//敵の弾クラス
			////
	}

	/****************************************************
	 * 変数を初期化する関数(ゲームスタート時に実行)
	 ****************************************************/
	public void init() {
		int i; //(ループ用)
		
		gameSceneSelecter = GAME_TITLE;
		score = 0; //ゲームスコア
		level = 0; //レベル
		
		//自機の位置と表示をリセット
		myShip.init();
		myShip_Visible = true;
		
		//敵機の位置と表示をリセット
		for (i=0; i<ENEMY_SHIP_MAX; i++) {
			enemyShip[i].init();
			enemyShip_Visible[i] = false;
		}
		
		//自分の弾を位置と表示をリセット
		for (i=0; i<MY_BULLET_MAX; i++) {
			drawMyBullet[i].init();
			myBullet_Visible[i] = false;
		}
		
		//敵の弾を位置と表示をリセット
			////
		
		//爆発の表示をリセット
		for (i=0; i<EXPLOSION_MAX; i++) {
			ExplosionVisible[i] = false;
		}
		
		//画像の読み込み
		background_img = getToolkit().getImage("img/back01@x480y720.jpg"); //背景画像
	}
	
	//スレッドを初期化する関数
	public void initThread() {
		Thread thread = new Thread(this);
		thread.start();
		System.out.println("動作てすと: MyCanvas/initThread");
	}
	
	
	/****************************************************
	 * スレッド(メインループ)
	 ****************************************************/
	@Override
	public void run() {
		//描画のちらつき防止のため、ダブルバッファリング処理
		//(URL: http://hp.vector.co.jp/authors/VA012735/java/dbuf1.htm)
		imgBuf = createImage(480, 700); //これがオフスクリーンバッファ(?)
		gBuf = imgBuf.getGraphics();
		
		//ここで色々と描画
		for (counter = 0; ; counter++) {			
			//バッファを白で塗りつぶしてクリア
			gBuf.setColor(Color.white);
			gBuf.fillRect(0, 0, 480, 700);
			
			//ゲーム画面を描画
			gameScene();
			
			//再描画
			repaint();
			
			//ループの間隔(20ミリ秒=0.02秒)
			try {
				Thread.sleep(GAMESPEED);
			} catch(InterruptedException e) {}
		}
	}
	
	/****************************************************
	 * 描画制御系の関数
	 ****************************************************/
	//バッファを画面に描画する
	@Override
	public void paint(Graphics g) {
		g.drawImage(imgBuf, 0, 0, this); //座標(0,0)の位置にバッファを描く
	}
	
	//repaint時のupdateメソッドが呼び出されると画面が一旦背景色で塗りつぶしてしまい画面がちらつくので、
	//それを防止するためにこれをオーバーライドしてpaintメソッドに移行(?)
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	/****************************************************
	 * ゲーム状況の変更にあわせて描画をする関数
	 * (キー入力でゲーム状況の変更も制御する)
	 ****************************************************/
	void gameScene() {
		//背景レイヤーを描画
		drawBackGround(gBuf);
		//ゲームの内容を描画
		switch (gameSceneSelecter) {
			case GAME_TITLE:
				//Spaceキーで開始(GAME_MAINGAMEへ)
				if (keyboard.SpaceKeyListener() != 0) gameSceneSelecter = GAME_MAINGAME;
				drawTitle(gBuf); break;
			case GAME_MAINGAME:
				//Escapeキーで離脱(GAME_TITLEへ)
				if (keyboard.EscapeKeyListener() == 1) init(); //ゲームを初期化
				drawMainGame(gBuf);	break;
			case GAME_GAMEOVER:
				//Spaceキーでリセット(GAME_TITLEへ)
				if (keyboard.EscapeKeyListener() == 1) init(); //ゲームを初期化
				drawGameOver(gBuf);	break;
		}
		//スコアとレベルのレイヤーを描画
		drawScore(gBuf);
	}
	
	/****************************************************
	 * 背景を描画する関数
	 ****************************************************/
	private void drawBackGround(Graphics gBuf2) {
		gBuf2.drawImage(background_img, 0, 0, 480, 720, this);
	}
	
	/****************************************************
	 * スコアとレベルを描画する関数
	 ****************************************************/
	private void drawScore(Graphics gBuf2) {
		gBuf2.setFont(scoreFont);
		gBuf2.setColor(Color.white);
		gBuf2.drawString("CREDITS: "+score_max, 345, 24); //画面右上に最高スコアを描画
		gBuf2.drawString("SCORE: "+score, 358, 44); //画面右上にゲームスコアを描画
		gBuf2.drawString("LEVEL: "+level, 10, 24); //画面左上にレベルを描画
		
		gBuf2.drawString("counter: "+counter, 10, 680); //カウンター
	}

	/****************************************************
	 * ゲームの内容を描画する関数群(シーン別)
	 ****************************************************/
	/*--------------------------------------------------
	 * ゲームタイトル
	 *-------------------------------------------------*/
	private void drawTitle(Graphics gBuf2) {
		gBuf2.setColor(Color.white);
		
//		String s1 = String.valueOf(keyboard.SpaceKeyListener()); //デバッグ用
		
		gBuf2.setFont(titleFont);
		gBuf2.drawString("シューティング作ってみた", 65, 230);
		gBuf2.setFont(subtitleFont);
//		gBuf2.drawString("(counter=" + counter + ")", 175, 220); //カウンターの出力
//		gBuf2.drawString(s1, 160, 300); //デバッグ用
		
		//"Press Space Key"の出力
		gBuf2.setFont(subtitleFont);
		gBuf2.drawString("Press Space Key", 175, 500);
		//(ドットを点滅)
		if (counter%300 <= 100) gBuf2.drawString(".", 300, 500);
		else if (counter%300 <= 200) gBuf2.drawString("..", 300, 500);
		else gBuf2.drawString("...", 300, 500);
	}
	
	/*--------------------------------------------------
	 * メインゲーム
	 *-------------------------------------------------*/
	private void drawMainGame(Graphics gBuf2) {
		int i;
		int[] myShipXYWH = new int[4];
		int[] myBulletXYWH = new int[4];
		int[] enemyShipXYWH = new int[4];
		myShipXYWH = myShip.getMyShipXYWH();
		
		/**** 自機の描画 ****/
		//自機の位置を移動し、描画
		myShip.moveMyShip(WIDTH, HEIGHT, keyboard.LeftRightListener(), keyboard.UpDownListener());
		myShip.drawMyShip(gBuf2);
		
		/**** 敵機の描画 ****/
		//敵機をランダムに生成(やられた敵は自然に表示がfalseになる)
		if (counter%24 == 0) {
			enemyShip[enemyShip_count].init();
			enemyShip_Visible[enemyShip_count] = true;
			enemyShip_count++;
			//敵機クラスの上限数に達したら0に戻す
			if (enemyShip_count == ENEMY_SHIP_MAX) enemyShip_count = 0;
		}
		//画面外(下)に出た敵機は表示を止める
		for (i=0; i<ENEMY_SHIP_MAX; i++) {
			if (enemyShip_Visible[i] == true) {
				enemyShipXYWH = enemyShip[i].getEnemyShip_XYWH(); 
				if (enemyShipXYWH[1] > HEIGHT) enemyShip_Visible[i] = false;
			}
		}
		//敵機を描画
		for (i=0; i<ENEMY_SHIP_MAX; i++) if (enemyShip_Visible[i] == true) enemyShip[i].enemyDraw(gBuf2);
		
		/**** 自分の弾の描画 ****/
		//スペースキーで自分の弾を発射
		if (keyboard.SpaceKeyListener() == 1) { //Spaceキー押下時
			if (counter%7 == 0) { //連射しすぎ防止のため、カウンターが8の倍数の時だけ発射。
				drawMyBullet[myBullet_count].init(myShipXYWH); //現在の位置から弾が動き出す
				myBullet_Visible[myBullet_count] = true; //表示を有効化
				myBullet_count++;
				//自分の弾クラスの上限数に達したら0に戻す
				if (myBullet_count == MY_BULLET_MAX) myBullet_count = 0;
			}
		}
		//画面外(上)に出た自分の弾は表示をやめる
		for (i=0; i<MY_BULLET_MAX; i++) {
			if (myBullet_Visible[i]) {
				myBulletXYWH = drawMyBullet[i].getMyBulletXYWH();
				if (myBulletXYWH[1] < 0) myBullet_Visible[i] = false;
			}
		}
		//発射した自分の弾を描画
		for (i=0; i<MY_BULLET_MAX; i++) {
			if (myBullet_Visible[i]) drawMyBullet[i].drawMyBullet(gBuf2);
		}
		
		/**** 1. 自分の弾と敵機 の衝突判定 ****/
		//自分の弾が敵に当たっていたら爆発を生成して敵を消す
		for (i=0; i<MY_BULLET_MAX; i++)
			if (myBullet_Visible[i]) EnemyShip_and_MyBullet_Encounter(gBuf2);
		//生成された爆発を描画
		for (i=0; i<EXPLOSION_MAX; i++)
			if (ExplosionVisible[i] == true) drawExplosion[i].drawFire(gBuf2);
		//描画の終わった爆発の表示を停止
		for (i=0; i<EXPLOSION_MAX; i++) {
			if (drawExplosion[i].returnFireStep() > 140) ExplosionVisible[i] = false; }
		
		/**** 2. 敵の弾と自機 の衝突判定 ****/
		//敵の弾に自機が当たったらゲームオーバー
			////
		
		/**** 3. 敵機と自機 の衝突判定 ****/
		//敵機に自機が当たったらゲームオーバー
			////
		
		/**** その他の描画 ****/
		//文字などを描画
		gBuf2.setColor(Color.white);
		gBuf2.drawString("GAME START...", 10, 80);
		gBuf2.drawString("STAGE 1", 10, 96);
	}

	/*--------------------------------------------------
	 * ゲームオーバー
	 *-------------------------------------------------*/
	private void drawGameOver(Graphics gBuf2) {
		gBuf2.setColor(Color.white);
		gBuf2.setFont(titleFont);
		gBuf2.drawString("GAME OVER!!", 20, 80);
		gBuf2.drawString("Your Score is..."+score, 10, 110);
		gBuf2.drawString("Your Rank is....S", 10, 128);
	}
	
	/****************************************************
	 * 1. 自分の弾と敵機 の衝突判定をする関数
	 ****************************************************/
	private void EnemyShip_and_MyBullet_Encounter (Graphics gBuf2) {
		int i, j;
		int[] enemyShipXYWH = new int[4];
		int[] myBulletXYWH = new int[4]; //弾の座標を取り敢えず入れておくための配列
		
		for (j=0; j<ENEMY_SHIP_MAX; j++) {
			enemyShipXYWH = enemyShip[j].getEnemyShip_XYWH(); //敵機の座標、大きさを取得
			
			if (enemyShip_Visible[j] == true) { //弾が有効だったら
				for (i=0; i<MY_BULLET_MAX; i++) {
					if (myBullet_Visible[i] == true) { //敵がまだ生きていたら
						myBulletXYWH = drawMyBullet[i].getMyBulletXYWH(); //自分の撃った弾の座標
						//X座標の判定(敵の大きさでも判断)
						if ((enemyShipXYWH[0]<=(myBulletXYWH[0]+myBulletXYWH[2]))&&(myBulletXYWH[0]<=(enemyShipXYWH[0]+enemyShipXYWH[2]))) {
							//Y座標判定(敵の大きさでも判断)
							if ((enemyShipXYWH[1]<=(myBulletXYWH[1]+myBulletXYWH[3]))&&(myBulletXYWH[1]<=(enemyShipXYWH[1]+enemyShipXYWH[3]))) {
							//座標が被っていたら
								//スコア追加
								score += 5;
								if (score > score_max) score_max = score;
								
								//爆発を生成
								drawExplosion[explosion_count].init(enemyShipXYWH);
								ExplosionVisible[explosion_count] = true;
								
								explosion_count++;
								if (explosion_count == EXPLOSION_MAX) explosion_count = 0;
								
								//敵の機体を消す
								enemyShip_Visible[j] = false;
								//弾も消す
								myBullet_Visible[i] = false;
							}
						}
					}
				}
			}
		}
	}
	
	/****************************************************
	 * 2. 敵の弾と自機 の衝突判定をする関数
	 ****************************************************/
	private void EnemyBullet_and_MyShip_Encounter (Graphics gBuf2) {
		////てかまだ敵の弾実装してない
	}
	
	/****************************************************
	 * 3. 敵機と自機 の衝突判定をする関数
	 ****************************************************/
	private void EnemyShip_and_MyShip_Encounter (Graphics gBuf2) {
		int i;
		int[] myShipXYWH = new int[4];
		int[] enemyShipXYWH = new int[4];
		
		myShipXYWH = myShip.getMyShipXYWH();
		
		for (i=0; i<ENEMY_SHIP_MAX; i++) {
			//敵機の座標を取得
			enemyShipXYWH = enemyShip[i].getEnemyShip_XYWH();
			//自機の座標と敵機の座標が被っているかチェック
			if ((enemyShipXYWH[0]+enemyShipXYWH[2]<=myShipXYWH[0])&&(myShipXYWH[0]+myShipXYWH[2]<=enemyShipXYWH[0])) { //x座標判定
				if ((enemyShipXYWH[1]+enemyShipXYWH[3]<=myShipXYWH[1])&&(myShipXYWH[1]+myShipXYWH[3]<=enemyShipXYWH[1])) { //y座標判定
					//座標が被っていたら
					System.out.println("動作てすと: MyCanvas/EnemyShip_and_MyShip_Encounter (敵機&自機の衝突発生)");
					
					//爆発を生成
					drawExplosion[explosion_count].init(enemyShipXYWH);
					ExplosionVisible[explosion_count] = true;
					
					explosion_count++;
					if (explosion_count == EXPLOSION_MAX) explosion_count = 0;
					
					//敵の機体を消す
					enemyShip_Visible[i] = false;
					
					//自分の機体を消す
					myShip_Visible = false;
				}
			}
		}
	}
}






