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
	//ウィンドウサイズと背景
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
	//敵クラス
	static final int ENEMY_MAX = 80; //敵の最大数
	DrawEnemyShip[] EnemyShip;
	boolean enemyVisible[] = new boolean[ENEMY_MAX];
	int enemy_count;
	int[] temp_enemyXYWH = new int[ENEMY_MAX];
	//自分の弾クラス
	static final int TAMA_MAX = 100; //自分が一気に撃てる弾の最大数
	DrawMyBullet[] drawMyBullet;
	boolean tamaVisible[] = new boolean[TAMA_MAX];
	int tama_count;
	int[] tama_temp = new int[TAMA_MAX];
	//爆発生成クラス
	static final int EXPLOSION_MAX = 40; //爆発の最大数
	CreateExplosion[] createExplosion;
	boolean ExplosionVisible[] = new boolean[EXPLOSION_MAX];
	int explosion_count;
	//自機関係
	static final int MOVE = 4; //自機の動く距離(速さ)の指定
	int[] myShipXY = {200, 500}; //自機の座標(200,400)
	int[] myShipSize = {162/3, 210/3}; //自機の大きさ(自動取得したいけど分からないから取り敢えず)
	Image img_myShip; //自機画像(素材-> http://maglog.jp/layer79ray97/Article1191293.html)
	
	
	/****************************************************
	 * コストラクタ
	 ****************************************************/
	MyCanvas() {
		int i;
		//キー入力クラス
		keyboard = new KeyboardListener();
		addKeyListener(keyboard);
		//敵クラス(取り敢えず1個→量産可能に)
		EnemyShip = new DrawEnemyShip[ENEMY_MAX];
		for (i=0; i<ENEMY_MAX; i++) EnemyShip[i] = new DrawEnemyShip();
		enemy_count = 0;
		//爆発生成クラス(爆発はEXPLOSION_MAX個まで一気に起こる)
		createExplosion = new CreateExplosion[EXPLOSION_MAX];
		for (i=0; i<EXPLOSION_MAX; i++) createExplosion[i] = new CreateExplosion();
		explosion_count = 0;
		//自分の弾クラス(弾はTAMA_MAX発まで一気に撃てる)
		drawMyBullet = new DrawMyBullet[TAMA_MAX];
		for (i=0; i<TAMA_MAX; i++) drawMyBullet[i] = new DrawMyBullet();
		tama_count = 0;
		//敵の弾クラス
		
	}

	/****************************************************
	 * 変数を初期化する関数(ゲームスタート時に実行)
	 ****************************************************/
	public void init() {
		int a;
		gameSceneSelecter = GAME_TITLE;
		score = 0; //ゲームスコア
		level = 0;
		
		//敵機の位置などのリセット
		for (a=0; a<ENEMY_MAX; a++) {
			enemyVisible[a] = false;
			EnemyShip[a].init();
		}
		
		//弾を表示と位置をリセット
		for (a=0; a<TAMA_MAX; a++) {
			tamaVisible[a] = false;
			drawMyBullet[a].init();
		}
		
		//爆発をリセット
		for (a=0; a<EXPLOSION_MAX; a++) ExplosionVisible[a] = false;
		
		/* 画像の読み込み */
		background_img = getToolkit().getImage("img/back01@x480y720.jpg"); //背景
		img_myShip = getToolkit().getImage("img/myship@x162y210.png"); //自機
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
			gBuf.fillRect(0,0,480,700);
			
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
				if (keyboard.EscapeKeyListener() == 1) init(); //(最高スコアを記録し、)ゲームを初期化
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
		/* プレイヤーの位置を移動 */
		
		//左右移動(端に行ったら逆からループ) →(端に行ったらストップ)に変更
		if (keyboard.LeftRightListener() == -1) {
			if (myShipXY[0] >= MOVE) myShipXY[0] -= MOVE;
		} else if (keyboard.LeftRightListener() == 1) {
			if (myShipXY[0] <= WIDTH-myShipSize[0]-MOVE) myShipXY[0] += MOVE;
		}
		//上下移動(端に行ったらストップ)
		if (keyboard.UpDownListener() == -1) {
			if (myShipXY[1] >= MOVE) myShipXY[1] -= MOVE;
		} else if (keyboard.UpDownListener() == 1) {
			if (myShipXY[1] <= HEIGHT-myShipSize[1]-MOVE) myShipXY[1] += MOVE;
		}
		
		//プレイヤーを描画
		gBuf2.drawImage(img_myShip, myShipXY[0], myShipXY[1], myShipSize[0], myShipSize[1], this);
		
		
		//敵をランダムに生成する
		if (counter%24 == 0) {
			EnemyShip[enemy_count].init();
			enemyVisible[enemy_count] = true;
			
			enemy_count++;
			if (enemy_count == ENEMY_MAX) enemy_count = 0;
		} //(やられた敵は自然に表示がfalseに)
		//画面外(下)に出た敵は非表示に
		for (i=0; i<ENEMY_MAX; i++) {
			if (enemyVisible[i] == true) {
				temp_enemyXYWH = EnemyShip[i].getEnemyShip_XYWH(); 
				if (temp_enemyXYWH[1] > HEIGHT) enemyVisible[i] = false;
			}
		}
			
		//敵を描画
		for (i=0; i<ENEMY_MAX; i++) if (enemyVisible[i] == true) EnemyShip[i].enemyDraw(gBuf2);

		//スペースキーで弾を発射
		if (keyboard.SpaceKeyListener() == 1) { //Spaceキー押下時
			if (counter%7 == 0) { //連射しすぎ防止のため、カウンターが8の倍数の時だけ発射。
				drawMyBullet[tama_count].init(myShipXY, myShipSize); //現在の位置から弾が動き出す
				tamaVisible[tama_count] = true; //表示を有効化
				tama_count++;
				
				//弾が生成した弾クラスの上限に達したら0に戻す
				if (tama_count == TAMA_MAX-1) tama_count = 0;
			}
		}
		//弾が画面外に出たら表示をやめる
		for (i=0; i<TAMA_MAX; i++) {
			if (tamaVisible[i]) {
				tama_temp = drawMyBullet[i].getTamaXYWH();
				if (tama_temp[1] < 0) tamaVisible[i] = false;
			}
		}
		//発射した自分の弾を描画
		for (i=0; i<TAMA_MAX; i++) {
			if (tamaVisible[i]) drawMyBullet[i].drawMyShipTama(gBuf2);
		}
		
		//自分の弾が敵に当たっていたら爆発を生成して敵を消す
		for (i=0; i<TAMA_MAX; i++)
			if (tamaVisible[i]) EnemyAndMyShipTama(gBuf2);
		//生成された爆発を描画
		for (i=0; i<EXPLOSION_MAX; i++)
			if (ExplosionVisible[i] == true) createExplosion[i].drawFire(gBuf2);
		//描画の終わった爆発の表示を停止
		for (i=0; i<EXPLOSION_MAX; i++)
			if (createExplosion[i].returnFireStep() > 140) ExplosionVisible[i] = false;
		
		//敵の弾に自分が当たったらゲームオーバー
		//hogeeeeeeeeee!!
		
		//文字などを描画
		gBuf2.setColor(Color.white);
		gBuf2.drawString("GAME START...", 10, 80);
		gBuf2.drawString("STAGE 1", 10, 96);
		gBuf2.setFont(subtitleFont);
		//gBuf2.drawString("(counter=" + counter + ")", 20, 200);
	}

	/*--------------------------------------------------
	 * ゲームオーバー
	 *-------------------------------------------------*/
	private void drawGameOver(Graphics gBuf2) {
		gBuf2.setColor(Color.white);
		gBuf2.setFont(titleFont);
		gBuf2.drawString("GAME OVER...", 20, 150);
		gBuf2.setFont(subtitleFont);
		//gBuf2.drawString("(counter=" + counter + ")", 20, 200);
	}
	
	/**************************
	 * 自分の弾と敵の座標の衝突を判定する関数(仮)
	 **************************/
	private void EnemyAndMyShipTama (Graphics gBuf2) {
		int i, j;
		int[] enemyShipA_XYWH = new int[4];
		int[] myShipTama; //弾の座標を取り敢えず入れておくための配列
		
		for (j=0; j<ENEMY_MAX; j++) {
			enemyShipA_XYWH = EnemyShip[j].getEnemyShip_XYWH(); //敵機の座標、大きさを取得
			if (enemyVisible[j] == true) { //弾が有効だったら
				for (i=0; i<TAMA_MAX; i++) {
					if (tamaVisible[i] == true) { //敵がもう死んでたら判断いらない
						myShipTama = drawMyBullet[i].getTamaXYWH(); //自分の撃った弾の座標。仮。
						//X座標の判定(敵の大きさでも判断)
						if (enemyShipA_XYWH[0]<=myShipTama[0] && myShipTama[0]<=enemyShipA_XYWH[0]+enemyShipA_XYWH[2]) {
							//Y座標判定(敵の大きさでも判断)
							if (enemyShipA_XYWH[1]<=myShipTama[1] && myShipTama[1]<=enemyShipA_XYWH[1]+enemyShipA_XYWH[3]) {
							//座標が被っていたら
								//スコア追加
								score += 5;
								if (score > score_max) score_max = score;
								
								//爆発を生成
								createExplosion[explosion_count].init(enemyShipA_XYWH);
								ExplosionVisible[explosion_count] = true;
								
								explosion_count++;
								if (explosion_count == EXPLOSION_MAX-1) explosion_count = 0;
								
								//敵の機体を消す
								enemyVisible[j] = false;
								//弾も消す
								tamaVisible[i] = false;
							}
						}
					}
				}
			} else { //お前はもう死んでいry
				//敵は死んでいるので得点・爆発はいじらない
			}
		}
	}	
}






