package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawTama extends Canvas {
	//�摜�n
	Image img_tama; //�e�̉摜
	//���W�n
	int[] img_tamaSize = {2*2, 10*2}; //�e(��)
	int[] myShipXYWH_AtThat = new int[4]; //�������e�����������̍��W��ۊ�
	int[] tamaMOVE = {0, 0};
	
	/* �R���X�g���N�^ */
	DrawTama() {
		System.out.println("����Ă���: DrawTama/�R���X�g���N�^");
		//�摜
		img_tama = getToolkit().getImage("img/tama01_a@x2y10.png"); //�����̒e
	}
	
	/**************************
	 * �e�̈ʒu�Ɠ�����0�ɏ�����(���Z�b�g���鎞��init)
	 **************************/
	public void init() {
		System.out.println("����Ă���: DrawTama/init");
		myShipXYWH_AtThat[0] = 0;
		myShipXYWH_AtThat[1] = 0;
		myShipXYWH_AtThat[2] = 0;
		myShipXYWH_AtThat[3] = 0;
		tamaMOVE[0] = 0;
		tamaMOVE[1] = 0;
	}
	
	/**************************
	 * �e�̈ʒu�Ɠ�����������(���Ƃ���init)
	 **************************/
	public void init(int[] myShipXY, int[] myShipSize) {
		System.out.println("����Ă���: DrawTama/init");
		myShipXYWH_AtThat[0] = myShipXY[0];
		myShipXYWH_AtThat[1] = myShipXY[1];
		myShipXYWH_AtThat[2] = myShipSize[0];
		myShipXYWH_AtThat[3] = myShipSize[1];
		tamaMOVE[0] = 0;
		tamaMOVE[1] = 0;
	}
	
	/**************************
	 * �����̒e�𐶐�����֐�
	 **************************/
	void drawMyShipTama (Graphics gBuf2) {
//		System.out.println("����Ă���: DrawTama/drawShipTama");
		gBuf2.drawImage(img_tama, 
				myShipXYWH_AtThat[0]+myShipXYWH_AtThat[2]/2/*����Ő^�񒆂��甭��*/+tamaMOVE[0], myShipXYWH_AtThat[1]+tamaMOVE[1],
				img_tamaSize[0], img_tamaSize[1], this);
		tamaMOVE[1] -= 4;
	}
	
	/**************************
	 * �ʃN���X���玩�@�̒e�̈ʒu�Ƒ傫�����擾�ł���悤�ɂ���֐�
	 **************************/
	public int[] getTamaXYWH() {
		int[] temp = {myShipXYWH_AtThat[0]+tamaMOVE[0], myShipXYWH_AtThat[1]+tamaMOVE[1], img_tamaSize[0], img_tamaSize[1]};
		return temp;
	}
}

