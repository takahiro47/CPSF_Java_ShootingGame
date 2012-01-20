package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class DrawEnemyShip extends Canvas {
	/****************************************************
	 * 変数群
	 ****************************************************/
	//敵機の動きのタイプ(種類)
	int enemyType;
	
	//弾 の定義
	Image EnemyBullet_img; //敵機の弾(棒)
	int[] EnemyBullet = {2, 10}; //大きさ
	//敵機A の定義
	Image EnemyShip_TypeA_img; //敵機画像
	int[] EnemyShip_TypeA_XY = {200, -100}; //初期座標
	int[] EnemyShip_TypeA_Size = {58, 64}; //大きさ
	//敵機B の定義
	Image EnemyShip_TypeB_img; //敵機画像
	int[] EnemyShip_TypeB_XY = {200, -100}; //初期座標
	int[] EnemyShip_TypeB_Size = {64, 64}; //大きさ
	
	//変数
	int[] Enemy_XY_MOVE = {0, 0}; //動き(揺れ)幅
	float x = 0; //x座標の動きを指定する時に使う
	//乱数の生成
	Random rand = new Random();
	
	/****************************************************
	 * コンストラクタ
	 ****************************************************/
	DrawEnemyShip() {
		System.out.println("動作てすと: DrawEnemyShip/コンストラクタ");
		/* 画像の読み込み */
		EnemyShip_TypeA_img = getToolkit().getImage("img/enemy01_c@x58y64.png"); //敵機A
		EnemyShip_TypeB_img = getToolkit().getImage("img/enemy02_b@x64y64.png"); //敵機B
	}
	
	/****************************************************
	 * 座標などをリセットする関数
	 * (別クラスからアクセス可)
	 ****************************************************/
	public void init() {
		//敵機の動き幅のリセット→座標の位置に戻る
		Enemy_XY_MOVE[0] = 0;
		Enemy_XY_MOVE[1] = 0;
		//敵の種類をランダムに決定
		enemyType = rand.nextInt(2);
		//敵のx座標をランダムに設定
		if (enemyType == 0) EnemyShip_TypeA_XY[0] += rand.nextInt(40) - 20;
		else if (enemyType == 1) EnemyShip_TypeB_XY[0] += rand.nextInt(300) - 150;;
	}
	
	/****************************************************
	 * 敵を実際に描画する関数
	 ****************************************************/
	public void enemyDraw(Graphics gBuf2) {
		if (enemyType == 0) { //ゆらゆら動く敵の動き
			//x方向の動き
			x += 0.01;
			Enemy_XY_MOVE[0] = (int)(120*Math.sin(Math.PI*x));
//			System.out.println((int)(100*Math.sin(Math.PI*x))); //デバッグ用
			//y方向の動き
			Enemy_XY_MOVE[1] += 1;
			gBuf2.drawImage(EnemyShip_TypeA_img,
					EnemyShip_TypeA_XY[0]+Enemy_XY_MOVE[0], EnemyShip_TypeA_XY[1]+Enemy_XY_MOVE[1],
					EnemyShip_TypeA_Size[0], EnemyShip_TypeA_Size[1], this);
		} else if (enemyType == 1) { //直進してくる敵の動き
			//x方向の動き
			Enemy_XY_MOVE[1] += 2;
			gBuf2.drawImage(EnemyShip_TypeB_img,
					EnemyShip_TypeB_XY[0]+Enemy_XY_MOVE[0], EnemyShip_TypeB_XY[1]+Enemy_XY_MOVE[1],
					EnemyShip_TypeB_Size[0], EnemyShip_TypeB_Size[1], this);
		} else {
			//(未実装)
		}
	}
	
	/****************************************************
	 * 別クラスから敵機の座標と敵機の大きさを参照できるようにする関数
	 ****************************************************/
	public int[] getEnemyShip_XYWH() {
		int[] returnXYWH = new int[4];
		if (enemyType == 0) {
			returnXYWH[0] = EnemyShip_TypeA_XY[0]+Enemy_XY_MOVE[0];
			returnXYWH[1] = EnemyShip_TypeA_XY[1]+Enemy_XY_MOVE[1];
			returnXYWH[2] = EnemyShip_TypeA_Size[0];
			returnXYWH[3] = EnemyShip_TypeA_Size[1];
		} else if (enemyType == 1) {
			returnXYWH[0] = EnemyShip_TypeB_XY[0]+Enemy_XY_MOVE[0];
			returnXYWH[1] = EnemyShip_TypeB_XY[1]+Enemy_XY_MOVE[1];
			returnXYWH[2] = EnemyShip_TypeB_Size[0];
			returnXYWH[3] = EnemyShip_TypeB_Size[1];
		}
		return returnXYWH;
	}
	
	
}