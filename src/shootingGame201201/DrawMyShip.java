package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawMyShip extends Canvas {
	//自機 の定義
	static final int MOVESPEED = 4; //動く速さの設定
	
	Image MyShip_img; //画像
	int[] MyShipXY = {200, 500}; //初期座標
	int[] MyShipXY_Move = {0, 0}; //移動値
	int[] MyShipSize = {162/3, 210/3}; //自機の大きさ(自動取得したいけど分からないから取り敢えず)
	
	/****************************************************
	 * コンストラクタ
	 ****************************************************/
	DrawMyShip() {
		System.out.println("動作てすと: DrawMyShip/コンストラクタ");
		
		MyShip_img = getToolkit().getImage("img/myship@x162y210.png"); //自機
	}
	
	/****************************************************
	 * 自機の位置の初期化
	 * @return 
	 ****************************************************/
	void init () {
		System.out.println("動作てすと: DrawMyShip/init");
		//移動値をリセット
		MyShipXY_Move[0] = 0;
		MyShipXY_Move[1] = 0;
	}
	
	/****************************************************
	 * 自機を移動する関数
	 ****************************************************/
	void moveMyShip (int width, int height, int LeftRight, int UpDown) {
		//左右移動(端に行ったらストップ)
		if (LeftRight == -1) {
			if (MyShipXY[0] + MyShipXY_Move[0] >= MOVESPEED - MyShipSize[0]/2)
				MyShipXY_Move[0] -= MOVESPEED;
		} else if (LeftRight == 1) {
			if (MyShipXY[0] + MyShipXY_Move[0] <= width - MyShipSize[0]/2 - MOVESPEED)
				MyShipXY_Move[0] += MOVESPEED;
		}
		//上下移動(端に行ったらストップ)
		if (UpDown == -1) {
			if (MyShipXY[1] + MyShipXY_Move[1] >= MOVESPEED + MyShipSize[1]/2)
				MyShipXY_Move[1] -= MOVESPEED;
		} else if (UpDown == 1) {
			if (MyShipXY[1] + MyShipXY_Move[1] <= height - MyShipSize[1]/2 - MOVESPEED)
				MyShipXY_Move[1] += MOVESPEED;
		}
	}
	
	/****************************************************
	 * 自機を描画する関数
	 ****************************************************/
	void drawMyShip (Graphics gBuf2) {
//		System.out.println("動作てすと: DrawMyShip/drawShip");
		gBuf2.drawImage(MyShip_img,
				MyShipXY[0]+MyShipXY_Move[0], MyShipXY[1]+MyShipXY_Move[1],
				MyShipSize[0], MyShipSize[1], this);
	}
	
	/****************************************************
	 * 別クラスから自機の位置を取得する為の関数
	 ****************************************************/
	int[] getMyShipXYWH () {
		int[] tempXYWH = {MyShipXY[0]+MyShipXY_Move[0], MyShipXY[1]+MyShipXY_Move[1],
				MyShipSize[0], MyShipSize[1]};
		return tempXYWH;
	}
}
