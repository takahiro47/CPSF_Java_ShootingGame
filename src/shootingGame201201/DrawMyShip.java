package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawMyShip extends Canvas {
	//���@ �̒�`
	static final int MOVESPEED = 4; //���������̐ݒ�
	
	Image MyShip_img; //�摜
	int[] MyShipXY = {200, 500}; //�������W
	int[] MyShipSize = {162/3, 210/3}; //���@�̑傫��(�����擾���������Ǖ�����Ȃ������芸����)
	
	/****************************************************
	 * �R���X�g���N�^
	 ****************************************************/
	DrawMyShip() {
		System.out.println("����Ă���: DrawMyShip/�R���X�g���N�^");
		
		MyShip_img = getToolkit().getImage("img/myship@x162y210.png"); //���@
	}
	
	/****************************************************
	 * ���@�̈ʒu�̏�����
	 ****************************************************/
	init () {
		System.out.println("����Ă���: DrawMyShip/init");
	}
	
	/****************************************************
	 * ���@���ړ�����֐�
	 ****************************************************/
	void moveMyShip (int LeftRight, int UpDown) {
		//���E�ړ�(�[�ɍs������X�g�b�v)
		if (LeftRight == -1)
			if (MyShipXY[0] >= MOVESPEED) MyShipXY[0] -= MOVESPEED;
		else if (LeftRight == 1)
			if (MyShipXY[0] <= WIDTH-MyShipSize[0]-MOVESPEED) MyShipXY[0] += MOVESPEED;
		//�㉺�ړ�(�[�ɍs������X�g�b�v)
		if (UpDown == -1)
			if (MyShipXY[1] >= MOVESPEED) MyShipXY[1] -= MOVESPEED;
		else if (UpDown == 1)
			if (MyShipXY[1] <= HEIGHT-MyShipSize[1]-MOVESPEED) MyShipXY[1] += MOVESPEED;
	}
	
	/****************************************************
	 * ���@��`�悷��֐�
	 ****************************************************/
	void drawMyShip (Graphics gBuf2) {
//		System.out.println("����Ă���: DrawMyShip/drawShip");
		gBuf2.drawImage(MyShip_img,
				MyShipXY[0], MyShipXY[1], MyShipSize[0], MyShipSize[1], this);
	}
	
	/****************************************************
	 * �ʃN���X���玩�@�̈ʒu���擾����ׂ̊֐�
	 ****************************************************/
	int[] getMyShipXYWH () {
		int[] tempXYWH = {MyShipXY[0], MyShipXY[1], MyShipSize[0], MyShipSize[1]};
		return tempXYWH;
	}
}
