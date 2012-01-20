package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class DrawEnemyShip extends Canvas {
	/****************************************************
	 * �ϐ��Q
	 ****************************************************/
	//�G�@�̓����̃^�C�v(���)
	int enemyType;
	
	//�e �̒�`
	Image EnemyBullet_img; //�G�@�̒e(�_)
	int[] EnemyBullet = {2, 10}; //�傫��
	//�G�@A �̒�`
	Image EnemyShip_TypeA_img; //�G�@�摜
	int[] EnemyShip_TypeA_XY = {200, -100}; //�������W
	int[] EnemyShip_TypeA_Size = {58, 64}; //�傫��
	//�G�@B �̒�`
	Image EnemyShip_TypeB_img; //�G�@�摜
	int[] EnemyShip_TypeB_XY = {200, -100}; //�������W
	int[] EnemyShip_TypeB_Size = {64, 64}; //�傫��
	
	//�ϐ�
	int[] Enemy_XY_MOVE = {0, 0}; //����(�h��)��
	float x = 0; //x���W�̓������w�肷�鎞�Ɏg��
	//�����̐���
	Random rand = new Random();
	
	/****************************************************
	 * �R���X�g���N�^
	 ****************************************************/
	DrawEnemyShip() {
		System.out.println("����Ă���: DrawEnemyShip/�R���X�g���N�^");
		/* �摜�̓ǂݍ��� */
		EnemyShip_TypeA_img = getToolkit().getImage("img/enemy01_c@x58y64.png"); //�G�@A
		EnemyShip_TypeB_img = getToolkit().getImage("img/enemy02_b@x64y64.png"); //�G�@B
	}
	
	/****************************************************
	 * ���W�Ȃǂ����Z�b�g����֐�
	 * (�ʃN���X����A�N�Z�X��)
	 ****************************************************/
	public void init() {
		//�G�@�̓������̃��Z�b�g�����W�̈ʒu�ɖ߂�
		Enemy_XY_MOVE[0] = 0;
		Enemy_XY_MOVE[1] = 0;
		//�G�̎�ނ������_���Ɍ���
		enemyType = rand.nextInt(2);
		//�G��x���W�������_���ɐݒ�
		if (enemyType == 0) EnemyShip_TypeA_XY[0] += rand.nextInt(40) - 20;
		else if (enemyType == 1) EnemyShip_TypeB_XY[0] += rand.nextInt(300) - 150;;
	}
	
	/****************************************************
	 * �G�����ۂɕ`�悷��֐�
	 ****************************************************/
	public void enemyDraw(Graphics gBuf2) {
		if (enemyType == 0) { //����瓮���G�̓���
			//x�����̓���
			x += 0.01;
			Enemy_XY_MOVE[0] = (int)(120*Math.sin(Math.PI*x));
//			System.out.println((int)(100*Math.sin(Math.PI*x))); //�f�o�b�O�p
			//y�����̓���
			Enemy_XY_MOVE[1] += 1;
			gBuf2.drawImage(EnemyShip_TypeA_img,
					EnemyShip_TypeA_XY[0]+Enemy_XY_MOVE[0], EnemyShip_TypeA_XY[1]+Enemy_XY_MOVE[1],
					EnemyShip_TypeA_Size[0], EnemyShip_TypeA_Size[1], this);
		} else if (enemyType == 1) { //���i���Ă���G�̓���
			//x�����̓���
			Enemy_XY_MOVE[1] += 2;
			gBuf2.drawImage(EnemyShip_TypeB_img,
					EnemyShip_TypeB_XY[0]+Enemy_XY_MOVE[0], EnemyShip_TypeB_XY[1]+Enemy_XY_MOVE[1],
					EnemyShip_TypeB_Size[0], EnemyShip_TypeB_Size[1], this);
		} else {
			//(������)
		}
	}
	
	/****************************************************
	 * �ʃN���X����G�@�̍��W�ƓG�@�̑傫�����Q�Ƃł���悤�ɂ���֐�
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