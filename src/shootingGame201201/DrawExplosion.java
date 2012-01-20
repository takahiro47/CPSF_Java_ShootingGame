package shootingGame201201;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawExplosion extends Canvas {
	//�����̉摜�ƃX�e�b�v(�e�Ƃ��������������ɔ���)
	Image img_fire_a; //�����摜�X�e�b�v1
	Image img_fire_b; //�����摜�X�e�b�v2
	Image img_fire_c; //�����摜�X�e�b�v3
	Image img_fire_d; //�����摜�X�e�b�v4
	Image img_fire_e; //�����摜�X�e�b�v4
	Image img_fire_f; //�����摜�X�e�b�v4
	Image img_fire_g; //�����摜�X�e�b�v4
	int[] fireSize = {64, 64}; //{64, 64}
	int fireStep;
	
	//�������N�������̓G�@�̈ʒu�ƁA�G�@�̃T�C�Y��ۊ�
	int EnemyShipXYWH_AtThat[] = new int[4];
	
	/**************************
	 * �R���X�g���N�^
	 **************************/
	DrawExplosion() {
		img_fire_a = getToolkit().getImage("img/fire01_a@x64y64.png"); //�����n
		img_fire_b = getToolkit().getImage("img/fire01_b@x64y64.png"); //�����n
		img_fire_c = getToolkit().getImage("img/fire01_c@x64y64.png"); //�����n
		img_fire_d = getToolkit().getImage("img/fire01_d@x64y64.png"); //�����n
		img_fire_e = getToolkit().getImage("img/fire01_e@x64y64.png"); //�����n
		img_fire_f = getToolkit().getImage("img/fire01_f@x64y64.png"); //�����n
		img_fire_g = getToolkit().getImage("img/fire01_g@x64y64.png"); //�����n
		fireStep = 0;
	}
	
	/**************************
	 * ���Z�b�g
	 **************************/
	public void init(int[] EnemyShipXYWH) {
		System.out.println("����Ă���: CreateEnemy/init");
		//���@�̈ʒu�ƃT�C�Y���擾���ĕۊ�
		EnemyShipXYWH_AtThat[0] = EnemyShipXYWH[0];
		EnemyShipXYWH_AtThat[1] = EnemyShipXYWH[1];
		EnemyShipXYWH_AtThat[2] = EnemyShipXYWH[2];
		EnemyShipXYWH_AtThat[3] = EnemyShipXYWH[3];
		//�����̐i���摜���ŏ��ɖ߂�
		fireStep = 0;
	}
	
	/**************************
	 * ������`�悷��֐�
	 **************************/
	public void drawFire(Graphics gBuf2) {
		System.out.println("����Ă���: CreateEnemy/drawFire");
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
	 * �����`��̐i���󋵂�Ԃ��֐�
	 **************************/
	public int returnFireStep() {
		return fireStep;
	}
	
}
