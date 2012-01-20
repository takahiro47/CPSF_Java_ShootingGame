package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawMyBullet extends Canvas {
	//自機の弾 の定義
	final static int MY_BULLET_SPEED = 5; //弾の速さ
	Image MyBullet_img; //画像
	int[] MyBullet_imgSize = {2*2, 10*2}; //大きさ
	int[] MyShipXYWH_AtThat = new int[4]; //自分が弾を撃った時点の座標を保管
	int[] MyBulletXY_Move = {0, 0}; //動き幅
	
	/****************************************************
	 * コンストラクタ
	 ****************************************************/
	DrawMyBullet() {
		System.out.println("動作てすと: DrawMyBullet/コンストラクタ");
		//画像
		MyBullet_img = getToolkit().getImage("img/MyBullet01_a@x2y10.png"); //自分の弾
	}
	
	/****************************************************
	 * 弾の位置と動きを0に初期化(リセットする時のinit) ?
	 ****************************************************/
	public void init() {
		System.out.println("動作てすと: DrawMyBullet/init");
		MyShipXYWH_AtThat[0] = 0;
		MyShipXYWH_AtThat[1] = 0;
		MyShipXYWH_AtThat[2] = MyBullet_imgSize[0];
		MyShipXYWH_AtThat[3] = MyBullet_imgSize[1];
		MyBulletXY_Move[0] = 0;
		MyBulletXY_Move[1] = 0;
	}
	
	/****************************************************
	 * 弾の位置と動きを初期化(撃つときのinit) ?
	 ****************************************************/
	public void init(int[] myShipXYWH) {
		System.out.println("動作てすと: DrawMyBullet/init");
		//弾を撃った瞬間の自機の位置を弾の初期位置に設定
		MyShipXYWH_AtThat[0] = myShipXYWH[0] + myShipXYWH[2]/2; //機体の真ん中から発射
		MyShipXYWH_AtThat[1] = myShipXYWH[1];
		MyShipXYWH_AtThat[2] = myShipXYWH[2];
		MyShipXYWH_AtThat[3] = myShipXYWH[3];
		//動きをリセット
		MyBulletXY_Move[0] = 0;
		MyBulletXY_Move[1] = 0;
	}
	
	/****************************************************
	 * 自分の弾を生成する関数
	 ****************************************************/
	void drawMyBullet (Graphics gBuf2) {
//		System.out.println("動作てすと: DrawMyBullet/drawMyBullet");
		
		//Y座標の動き
		MyBulletXY_Move[1] -= MY_BULLET_SPEED;
		//描画
		gBuf2.drawImage(MyBullet_img, 
				MyShipXYWH_AtThat[0]+MyBulletXY_Move[0], MyShipXYWH_AtThat[1]+MyBulletXY_Move[1],
				MyBullet_imgSize[0], MyBullet_imgSize[1], this);
	}
	
	/****************************************************
	 * 別クラスから自機の弾の位置と大きさを取得する為の関数
	 ****************************************************/
	public int[] getMyBulletXYWH() {
		int[] tempXYWH = {
				MyShipXYWH_AtThat[0]+MyBulletXY_Move[0], MyShipXYWH_AtThat[1]+MyBulletXY_Move[1],
				MyBullet_imgSize[0], MyBullet_imgSize[1]};
		return tempXYWH;
	}
}

