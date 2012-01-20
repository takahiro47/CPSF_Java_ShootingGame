package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawMyShip extends Canvas {
	//���@ �̒�`
	static final int MOVESPEED = 4; //���������̐ݒ�
	
	Image MyShip_img; //�摜
	int[] MyShipXY = {200, 500}; //�������W
	int[] MyShipXY_Move = {0, 0}; //�ړ��l
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
	 * @return 
	 ****************************************************/
	void init () {
		System.out.println("����Ă���: DrawMyShip/init");
		//�ړ��l�����Z�b�g
		MyShipXY_Move[0] = 0;
		MyShipXY_Move[1] = 0;
	}
	
	/****************************************************
	 * ���@���ړ�����֐�
	 ****************************************************/
	void moveMyShip (int width, int height, int LeftRight, int UpDown) {
		//���E�ړ�(�[�ɍs������X�g�b�v)
		if (LeftRight == -1) {
			if (MyShipXY[0] + MyShipXY_Move[0] >= MOVESPEED - MyShipSize[0]/2)
				MyShipXY_Move[0] -= MOVESPEED;
		} else if (LeftRight == 1) {
			if (MyShipXY[0] + MyShipXY_Move[0] <= width - MyShipSize[0]/2 - MOVESPEED)
				MyShipXY_Move[0] += MOVESPEED;
		}
		//�㉺�ړ�(�[�ɍs������X�g�b�v)
		if (UpDown == -1) {
			if (MyShipXY[1] + MyShipXY_Move[1] >= MOVESPEED + MyShipSize[1]/2)
				MyShipXY_Move[1] -= MOVESPEED;
		} else if (UpDown == 1) {
			if (MyShipXY[1] + MyShipXY_Move[1] <= height - MyShipSize[1]/2 - MOVESPEED)
				MyShipXY_Move[1] += MOVESPEED;
		}
	}
	
	/****************************************************
	 * ���@��`�悷��֐�
	 ****************************************************/
	void drawMyShip (Graphics gBuf2) {
//		System.out.println("����Ă���: DrawMyShip/drawShip");
		gBuf2.drawImage(MyShip_img,
				MyShipXY[0]+MyShipXY_Move[0], MyShipXY[1]+MyShipXY_Move[1],
				MyShipSize[0], MyShipSize[1], this);
	}
	
	/****************************************************
	 * �ʃN���X���玩�@�̈ʒu���擾����ׂ̊֐�
	 ****************************************************/
	int[] getMyShipXYWH () {
		int[] tempXYWH = {MyShipXY[0]+MyShipXY_Move[0], MyShipXY[1]+MyShipXY_Move[1],
				MyShipSize[0], MyShipSize[1]};
		return tempXYWH;
	}
}
