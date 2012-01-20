package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawTama extends Canvas {
	//画像系
	Image img_tama; //弾の画像
	//座標系
	int[] img_tamaSize = {2*2, 10*2}; //弾(仮)
	int[] myShipXYWH_AtThat = new int[4]; //自分が弾を撃った時の座標を保管
	int[] tamaMOVE = {0, 0};
	
	/* コンストラクタ */
	DrawTama() {
		System.out.println("動作てすと: DrawTama/コンストラクタ");
		//画像
		img_tama = getToolkit().getImage("img/tama01_a@x2y10.png"); //自分の弾
	}
	
	/**************************
	 * 弾の位置と動きを0に初期化(リセットする時のinit)
	 **************************/
	public void init() {
		System.out.println("動作てすと: DrawTama/init");
		myShipXYWH_AtThat[0] = 0;
		myShipXYWH_AtThat[1] = 0;
		myShipXYWH_AtThat[2] = 0;
		myShipXYWH_AtThat[3] = 0;
		tamaMOVE[0] = 0;
		tamaMOVE[1] = 0;
	}
	
	/**************************
	 * 弾の位置と動きを初期化(撃つときのinit)
	 **************************/
	public void init(int[] myShipXY, int[] myShipSize) {
		System.out.println("動作てすと: DrawTama/init");
		myShipXYWH_AtThat[0] = myShipXY[0];
		myShipXYWH_AtThat[1] = myShipXY[1];
		myShipXYWH_AtThat[2] = myShipSize[0];
		myShipXYWH_AtThat[3] = myShipSize[1];
		tamaMOVE[0] = 0;
		tamaMOVE[1] = 0;
	}
	
	/**************************
	 * 自分の弾を生成する関数
	 **************************/
	void drawMyShipTama (Graphics gBuf2) {
//		System.out.println("動作てすと: DrawTama/drawShipTama");
		gBuf2.drawImage(img_tama, 
				myShipXYWH_AtThat[0]+myShipXYWH_AtThat[2]/2/*これで真ん中から発射*/+tamaMOVE[0], myShipXYWH_AtThat[1]+tamaMOVE[1],
				img_tamaSize[0], img_tamaSize[1], this);
		tamaMOVE[1] -= 4;
	}
	
	/**************************
	 * 別クラスから自機の弾の位置と大きさを取得できるようにする関数
	 **************************/
	public int[] getTamaXYWH() {
		int[] temp = {myShipXYWH_AtThat[0]+tamaMOVE[0], myShipXYWH_AtThat[1]+tamaMOVE[1], img_tamaSize[0], img_tamaSize[1]};
		return temp;
	}
}

