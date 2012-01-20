package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawMyBullet extends Canvas {
	//���@�̒e �̒�`
	final static int MY_BULLET_SPEED = 5; //�e�̑���
	Image MyBullet_img; //�摜
	int[] MyBullet_imgSize = {2*2, 10*2}; //�傫��
	int[] MyShipXYWH_AtThat = new int[4]; //�������e�����������_�̍��W��ۊ�
	int[] MyBulletXY_Move = {0, 0}; //������
	
	/****************************************************
	 * �R���X�g���N�^
	 ****************************************************/
	DrawMyBullet() {
		System.out.println("����Ă���: DrawMyBullet/�R���X�g���N�^");
		//�摜
		MyBullet_img = getToolkit().getImage("img/MyBullet01_a@x2y10.png"); //�����̒e
	}
	
	/****************************************************
	 * �e�̈ʒu�Ɠ�����0�ɏ�����(���Z�b�g���鎞��init) ?
	 ****************************************************/
	public void init() {
		System.out.println("����Ă���: DrawMyBullet/init");
		MyShipXYWH_AtThat[0] = 0;
		MyShipXYWH_AtThat[1] = 0;
		MyShipXYWH_AtThat[2] = MyBullet_imgSize[0];
		MyShipXYWH_AtThat[3] = MyBullet_imgSize[1];
		MyBulletXY_Move[0] = 0;
		MyBulletXY_Move[1] = 0;
	}
	
	/****************************************************
	 * �e�̈ʒu�Ɠ�����������(���Ƃ���init) ?
	 ****************************************************/
	public void init(int[] myShipXYWH) {
		System.out.println("����Ă���: DrawMyBullet/init");
		//�e���������u�Ԃ̎��@�̈ʒu��e�̏����ʒu�ɐݒ�
		MyShipXYWH_AtThat[0] = myShipXYWH[0] + myShipXYWH[2]/2; //�@�̂̐^�񒆂��甭��
		MyShipXYWH_AtThat[1] = myShipXYWH[1];
		MyShipXYWH_AtThat[2] = myShipXYWH[2];
		MyShipXYWH_AtThat[3] = myShipXYWH[3];
		//���������Z�b�g
		MyBulletXY_Move[0] = 0;
		MyBulletXY_Move[1] = 0;
	}
	
	/****************************************************
	 * �����̒e�𐶐�����֐�
	 ****************************************************/
	void drawMyBullet (Graphics gBuf2) {
//		System.out.println("����Ă���: DrawMyBullet/drawMyBullet");
		
		//Y���W�̓���
		MyBulletXY_Move[1] -= MY_BULLET_SPEED;
		//�`��
		gBuf2.drawImage(MyBullet_img, 
				MyShipXYWH_AtThat[0]+MyBulletXY_Move[0], MyShipXYWH_AtThat[1]+MyBulletXY_Move[1],
				MyBullet_imgSize[0], MyBullet_imgSize[1], this);
	}
	
	/****************************************************
	 * �ʃN���X���玩�@�̒e�̈ʒu�Ƒ傫�����擾����ׂ̊֐�
	 ****************************************************/
	public int[] getMyBulletXYWH() {
		int[] tempXYWH = {
				MyShipXYWH_AtThat[0]+MyBulletXY_Move[0], MyShipXYWH_AtThat[1]+MyBulletXY_Move[1],
				MyBullet_imgSize[0], MyBullet_imgSize[1]};
		return tempXYWH;
	}
}

