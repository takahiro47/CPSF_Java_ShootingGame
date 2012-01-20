package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawExplosion extends Canvas {
	//爆発の画像とステップ(弾とかが当たった時に発生)
	Image img_fire_a; //爆発画像ステップ1
	Image img_fire_b; //爆発画像ステップ2
	Image img_fire_c; //爆発画像ステップ3
	Image img_fire_d; //爆発画像ステップ4
	Image img_fire_e; //爆発画像ステップ4
	Image img_fire_f; //爆発画像ステップ4
	Image img_fire_g; //爆発画像ステップ4
	int[] fireSize = {64, 64}; //{64, 64}
	int fireStep;
	
	//爆発を起こす時の敵機の位置と、敵機のサイズを保管
	int EnemyShipXYWH_AtThat[] = new int[4];
	
	/**************************
	 * コンストラクタ
	 **************************/
	DrawExplosion() {
		img_fire_a = getToolkit().getImage("img/fire01_a@x64y64.png"); //爆発系
		img_fire_b = getToolkit().getImage("img/fire01_b@x64y64.png"); //爆発系
		img_fire_c = getToolkit().getImage("img/fire01_c@x64y64.png"); //爆発系
		img_fire_d = getToolkit().getImage("img/fire01_d@x64y64.png"); //爆発系
		img_fire_e = getToolkit().getImage("img/fire01_e@x64y64.png"); //爆発系
		img_fire_f = getToolkit().getImage("img/fire01_f@x64y64.png"); //爆発系
		img_fire_g = getToolkit().getImage("img/fire01_g@x64y64.png"); //爆発系
		fireStep = 0;
	}
	
	/**************************
	 * リセット
	 **************************/
	public void init(int[] EnemyShipXYWH) {
		System.out.println("動作てすと: CreateEnemy/init");
		//自機の位置とサイズを取得して保管
		EnemyShipXYWH_AtThat[0] = EnemyShipXYWH[0];
		EnemyShipXYWH_AtThat[1] = EnemyShipXYWH[1];
		EnemyShipXYWH_AtThat[2] = EnemyShipXYWH[2];
		EnemyShipXYWH_AtThat[3] = EnemyShipXYWH[3];
		//爆発の進捗画像を最初に戻す
		fireStep = 0;
	}
	
	/**************************
	 * 爆発を描画する関数
	 **************************/
	public void drawFire(Graphics gBuf2) {
		System.out.println("動作てすと: CreateEnemy/drawFire");
		int temp_x = EnemyShipXYWH_AtThat[0] + EnemyShipXYWH_AtThat[2]/2 - fireSize[0]/2;
		int temp_y = EnemyShipXYWH_AtThat[1] + EnemyShipXYWH_AtThat[3]/2 - fireSize[1]/2;
		
		if (fireStep<20) gBuf2.drawImage(img_fire_a, temp_x, temp_y, fireSize[0], fireSize[1], this);
		else if (fireStep<40)  gBuf2.drawImage(img_fire_b, temp_x, temp_y, fireSize[0], fireSize[1], this);
		else if (fireStep<60)  gBuf2.drawImage(img_fire_c, temp_x, temp_y, fireSize[0], fireSize[1], this);
		else if (fireStep<80)  gBuf2.drawImage(img_fire_d, temp_x, temp_y, fireSize[0], fireSize[1], this);
		else if (fireStep<100) gBuf2.drawImage(img_fire_e, temp_x, temp_y, fireSize[0], fireSize[1], this);
		else if (fireStep<120) gBuf2.drawImage(img_fire_f, temp_x, temp_y, fireSize[0], fireSize[1], this);
		else if (fireStep<140) gBuf2.drawImage(img_fire_g, temp_x, temp_y, fireSize[0], fireSize[1], this);
		
		fireStep++;
	}
	
	/**************************
	 * 爆発描画の進捗状況を返す関数
	 **************************/
	public int returnFireStep() {
		return fireStep;
	}
	
}
