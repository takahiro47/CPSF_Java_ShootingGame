package shootingGame201201;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/* ---�^�X�N���X�g---
 * �E�G���e�����悤��
 * 
 * �E�G�̓����̃^�C�v�̃o���G�[�V����
 * �E�S�̓I�ɉ摜���ǂ���傫�����炿����Ƃ�����������
 * �E�G&�G�e�̎���
 * �E�����ɍ��킹�Ĕw�i�������ړ�
 * �E���e�Ƃ�
 * �E�ꎞ��~
 * �E�c��@���̊T�O
 * �E�̗̓Q�[�W�̊T�O
 */

public class MyCanvas extends Canvas implements Runnable {
	/****************************************************
	 * �ϐ��Ƃ��N���X
	 ****************************************************/
	/*----�Q�[���S�̂Ɋւ��ϐ�----*/
	//���Ԍo�߃J�E���g
	int counter;
	//�Q�[���X�R�A�ƍō��X�R�A
	public int score;
	public int score_max;
	public int level;
	//�Q�[���̃��[�h��#define
	int gameSceneSelecter;
	static final int GAME_TITLE = 0;
	static final int GAME_MAINGAME = 1;
	static final int GAME_GAMEOVER = 2;
	/*----�E�B���h�E�Ƃ��`��֌W----*/
	//�E�B���h�E�T�C�Y�Ɣw�i
	static final int WIDTH = 480;
	static final int HEIGHT = 700;
	static final int GAMESPEED = 15; //�`�惋�[�v�̊Ԋu(15�~���b=0.015�b)
	//�`��֌W
	Image imgBuf;
	Graphics gBuf;
	Font titleFont = new Font("SansSerif", Font.BOLD, 28); //SansSerif
	Font subtitleFont = new Font("SansSerif", Font.PLAIN, 16); //SansSerif
	Font scoreFont = new Font("popstarregular", Font.PLAIN, 12);
	/*----���̑��̃N���X�Ƃ��ϐ�----*/
	//�w�i�摜
	Image background_img;
	//�L�[�Ď��N���X
	KeyboardListener keyboard;
	//�G�N���X
	static final int ENEMY_MAX = 80; //�G�̍ő吔
	DrawEnemyShip[] EnemyShip;
	boolean enemyVisible[] = new boolean[ENEMY_MAX];
	int enemy_count;
	int[] temp_enemyXYWH = new int[ENEMY_MAX];
	//�����̒e�N���X
	static final int TAMA_MAX = 100; //��������C�Ɍ��Ă�e�̍ő吔
	DrawMyBullet[] drawMyBullet;
	boolean tamaVisible[] = new boolean[TAMA_MAX];
	int tama_count;
	int[] tama_temp = new int[TAMA_MAX];
	//���������N���X
	static final int EXPLOSION_MAX = 40; //�����̍ő吔
	CreateExplosion[] createExplosion;
	boolean ExplosionVisible[] = new boolean[EXPLOSION_MAX];
	int explosion_count;
	//���@�֌W
	static final int MOVE = 4; //���@�̓�������(����)�̎w��
	int[] myShipXY = {200, 500}; //���@�̍��W(200,400)
	int[] myShipSize = {162/3, 210/3}; //���@�̑傫��(�����擾���������Ǖ�����Ȃ������芸����)
	Image img_myShip; //���@�摜(�f��-> http://maglog.jp/layer79ray97/Article1191293.html)
	
	
	/****************************************************
	 * �R�X�g���N�^
	 ****************************************************/
	MyCanvas() {
		int i;
		//�L�[���̓N���X
		keyboard = new KeyboardListener();
		addKeyListener(keyboard);
		//�G�N���X(��芸����1���ʎY�\��)
		EnemyShip = new DrawEnemyShip[ENEMY_MAX];
		for (i=0; i<ENEMY_MAX; i++) EnemyShip[i] = new DrawEnemyShip();
		enemy_count = 0;
		//���������N���X(������EXPLOSION_MAX�܂ň�C�ɋN����)
		createExplosion = new CreateExplosion[EXPLOSION_MAX];
		for (i=0; i<EXPLOSION_MAX; i++) createExplosion[i] = new CreateExplosion();
		explosion_count = 0;
		//�����̒e�N���X(�e��TAMA_MAX���܂ň�C�Ɍ��Ă�)
		drawMyBullet = new DrawMyBullet[TAMA_MAX];
		for (i=0; i<TAMA_MAX; i++) drawMyBullet[i] = new DrawMyBullet();
		tama_count = 0;
		//�G�̒e�N���X
		
	}

	/****************************************************
	 * �ϐ�������������֐�(�Q�[���X�^�[�g���Ɏ��s)
	 ****************************************************/
	public void init() {
		int a;
		gameSceneSelecter = GAME_TITLE;
		score = 0; //�Q�[���X�R�A
		level = 0;
		
		//�G�@�̈ʒu�Ȃǂ̃��Z�b�g
		for (a=0; a<ENEMY_MAX; a++) {
			enemyVisible[a] = false;
			EnemyShip[a].init();
		}
		
		//�e��\���ƈʒu�����Z�b�g
		for (a=0; a<TAMA_MAX; a++) {
			tamaVisible[a] = false;
			drawMyBullet[a].init();
		}
		
		//���������Z�b�g
		for (a=0; a<EXPLOSION_MAX; a++) ExplosionVisible[a] = false;
		
		/* �摜�̓ǂݍ��� */
		background_img = getToolkit().getImage("img/back01@x480y720.jpg"); //�w�i
		img_myShip = getToolkit().getImage("img/myship@x162y210.png"); //���@
	}
	
	//�X���b�h������������֐�
	public void initThread() {
		Thread thread = new Thread(this);
		thread.start();
		System.out.println("����Ă���: MyCanvas/initThread");
	}
	
	
	/****************************************************
	 * �X���b�h(���C�����[�v)
	 ****************************************************/
	@Override
	public void run() {
		//�`��̂�����h�~�̂��߁A�_�u���o�b�t�@�����O����
		//(URL: http://hp.vector.co.jp/authors/VA012735/java/dbuf1.htm)
		imgBuf = createImage(480, 700); //���ꂪ�I�t�X�N���[���o�b�t�@(?)
		gBuf = imgBuf.getGraphics();
		
		//�����ŐF�X�ƕ`��
		for (counter = 0; ; counter++) {			
			//�o�b�t�@�𔒂œh��Ԃ��ăN���A
			gBuf.setColor(Color.white);
			gBuf.fillRect(0,0,480,700);
			
			//�Q�[����ʂ�`��
			gameScene();
			
			//�ĕ`��
			repaint();
			
			//���[�v�̊Ԋu(20�~���b=0.02�b)
			try {
				Thread.sleep(GAMESPEED);
			} catch(InterruptedException e) {}
		}
	}
	
	/****************************************************
	 * �`�搧��n�̊֐�
	 ****************************************************/
	//�o�b�t�@����ʂɕ`�悷��
	@Override
	public void paint(Graphics g) {
		g.drawImage(imgBuf, 0, 0, this); //���W(0,0)�̈ʒu�Ƀo�b�t�@��`��
	}
	
	//repaint����update���\�b�h���Ăяo�����Ɖ�ʂ���U�w�i�F�œh��Ԃ��Ă��܂���ʂ�������̂ŁA
	//�����h�~���邽�߂ɂ�����I�[�o�[���C�h����paint���\�b�h�Ɉڍs(?)
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	/****************************************************
	 * �Q�[���󋵂̕ύX�ɂ��킹�ĕ`�������֐�
	 * (�L�[���͂ŃQ�[���󋵂̕ύX�����䂷��)
	 ****************************************************/
	void gameScene() {
		//�w�i���C���[��`��
		drawBackGround(gBuf);
		//�Q�[���̓��e��`��
		switch (gameSceneSelecter) {
			case GAME_TITLE:
				//Space�L�[�ŊJ�n(GAME_MAINGAME��)
				if (keyboard.SpaceKeyListener() != 0) gameSceneSelecter = GAME_MAINGAME;
				drawTitle(gBuf); break;
			case GAME_MAINGAME:
				//Escape�L�[�ŗ��E(GAME_TITLE��)
				if (keyboard.EscapeKeyListener() == 1) init(); //�Q�[����������
				drawMainGame(gBuf);	break;
			case GAME_GAMEOVER:
				//Space�L�[�Ń��Z�b�g(GAME_TITLE��)
				if (keyboard.EscapeKeyListener() == 1) init(); //(�ō��X�R�A���L�^���A)�Q�[����������
				drawGameOver(gBuf);	break;
		}
		//�X�R�A�ƃ��x���̃��C���[��`��
		drawScore(gBuf);
	}
	
	/****************************************************
	 * �w�i��`�悷��֐�
	 ****************************************************/
	private void drawBackGround(Graphics gBuf2) {
		gBuf2.drawImage(background_img, 0, 0, 480, 720, this);
	}
	
	/****************************************************
	 * �X�R�A�ƃ��x����`�悷��֐�
	 ****************************************************/
	private void drawScore(Graphics gBuf2) {
		gBuf2.setFont(scoreFont);
		gBuf2.setColor(Color.white);
		gBuf2.drawString("CREDITS: "+score_max, 345, 24); //��ʉE��ɍō��X�R�A��`��
		gBuf2.drawString("SCORE: "+score, 358, 44); //��ʉE��ɃQ�[���X�R�A��`��
		gBuf2.drawString("LEVEL: "+level, 10, 24); //��ʍ���Ƀ��x����`��
		
		gBuf2.drawString("counter: "+counter, 10, 680); //�J�E���^�[
	}

	/****************************************************
	 * �Q�[���̓��e��`�悷��֐��Q(�V�[����)
	 ****************************************************/
	/*--------------------------------------------------
	 * �Q�[���^�C�g��
	 *-------------------------------------------------*/
	private void drawTitle(Graphics gBuf2) {
		gBuf2.setColor(Color.white);
		
//		String s1 = String.valueOf(keyboard.SpaceKeyListener()); //�f�o�b�O�p
		
		gBuf2.setFont(titleFont);
		gBuf2.drawString("�V���[�e�B���O����Ă݂�", 65, 230);
		gBuf2.setFont(subtitleFont);
//		gBuf2.drawString("(counter=" + counter + ")", 175, 220); //�J�E���^�[�̏o��
//		gBuf2.drawString(s1, 160, 300); //�f�o�b�O�p
		
		//"Press Space Key"�̏o��
		gBuf2.setFont(subtitleFont);
		gBuf2.drawString("Press Space Key", 175, 500);
		//(�h�b�g��_��)
		if (counter%300 <= 100) gBuf2.drawString(".", 300, 500);
		else if (counter%300 <= 200) gBuf2.drawString("..", 300, 500);
		else gBuf2.drawString("...", 300, 500);
	}
	
	/*--------------------------------------------------
	 * ���C���Q�[��
	 *-------------------------------------------------*/
	private void drawMainGame(Graphics gBuf2) {
		int i;
		/* �v���C���[�̈ʒu���ړ� */
		
		//���E�ړ�(�[�ɍs������t���烋�[�v) ��(�[�ɍs������X�g�b�v)�ɕύX
		if (keyboard.LeftRightListener() == -1) {
			if (myShipXY[0] >= MOVE) myShipXY[0] -= MOVE;
		} else if (keyboard.LeftRightListener() == 1) {
			if (myShipXY[0] <= WIDTH-myShipSize[0]-MOVE) myShipXY[0] += MOVE;
		}
		//�㉺�ړ�(�[�ɍs������X�g�b�v)
		if (keyboard.UpDownListener() == -1) {
			if (myShipXY[1] >= MOVE) myShipXY[1] -= MOVE;
		} else if (keyboard.UpDownListener() == 1) {
			if (myShipXY[1] <= HEIGHT-myShipSize[1]-MOVE) myShipXY[1] += MOVE;
		}
		
		//�v���C���[��`��
		gBuf2.drawImage(img_myShip, myShipXY[0], myShipXY[1], myShipSize[0], myShipSize[1], this);
		
		
		//�G�������_���ɐ�������
		if (counter%24 == 0) {
			EnemyShip[enemy_count].init();
			enemyVisible[enemy_count] = true;
			
			enemy_count++;
			if (enemy_count == ENEMY_MAX) enemy_count = 0;
		} //(���ꂽ�G�͎��R�ɕ\����false��)
		//��ʊO(��)�ɏo���G�͔�\����
		for (i=0; i<ENEMY_MAX; i++) {
			if (enemyVisible[i] == true) {
				temp_enemyXYWH = EnemyShip[i].getEnemyShip_XYWH(); 
				if (temp_enemyXYWH[1] > HEIGHT) enemyVisible[i] = false;
			}
		}
			
		//�G��`��
		for (i=0; i<ENEMY_MAX; i++) if (enemyVisible[i] == true) EnemyShip[i].enemyDraw(gBuf2);

		//�X�y�[�X�L�[�Œe�𔭎�
		if (keyboard.SpaceKeyListener() == 1) { //Space�L�[������
			if (counter%7 == 0) { //�A�˂������h�~�̂��߁A�J�E���^�[��8�̔{���̎��������ˁB
				drawMyBullet[tama_count].init(myShipXY, myShipSize); //���݂̈ʒu����e�������o��
				tamaVisible[tama_count] = true; //�\����L����
				tama_count++;
				
				//�e�����������e�N���X�̏���ɒB������0�ɖ߂�
				if (tama_count == TAMA_MAX-1) tama_count = 0;
			}
		}
		//�e����ʊO�ɏo����\������߂�
		for (i=0; i<TAMA_MAX; i++) {
			if (tamaVisible[i]) {
				tama_temp = drawMyBullet[i].getTamaXYWH();
				if (tama_temp[1] < 0) tamaVisible[i] = false;
			}
		}
		//���˂��������̒e��`��
		for (i=0; i<TAMA_MAX; i++) {
			if (tamaVisible[i]) drawMyBullet[i].drawMyShipTama(gBuf2);
		}
		
		//�����̒e���G�ɓ������Ă����甚���𐶐����ēG������
		for (i=0; i<TAMA_MAX; i++)
			if (tamaVisible[i]) EnemyAndMyShipTama(gBuf2);
		//�������ꂽ������`��
		for (i=0; i<EXPLOSION_MAX; i++)
			if (ExplosionVisible[i] == true) createExplosion[i].drawFire(gBuf2);
		//�`��̏I����������̕\�����~
		for (i=0; i<EXPLOSION_MAX; i++)
			if (createExplosion[i].returnFireStep() > 140) ExplosionVisible[i] = false;
		
		//�G�̒e�Ɏ���������������Q�[���I�[�o�[
		//hogeeeeeeeeee!!
		
		//�����Ȃǂ�`��
		gBuf2.setColor(Color.white);
		gBuf2.drawString("GAME START...", 10, 80);
		gBuf2.drawString("STAGE 1", 10, 96);
		gBuf2.setFont(subtitleFont);
		//gBuf2.drawString("(counter=" + counter + ")", 20, 200);
	}

	/*--------------------------------------------------
	 * �Q�[���I�[�o�[
	 *-------------------------------------------------*/
	private void drawGameOver(Graphics gBuf2) {
		gBuf2.setColor(Color.white);
		gBuf2.setFont(titleFont);
		gBuf2.drawString("GAME OVER...", 20, 150);
		gBuf2.setFont(subtitleFont);
		//gBuf2.drawString("(counter=" + counter + ")", 20, 200);
	}
	
	/**************************
	 * �����̒e�ƓG�̍��W�̏Փ˂𔻒肷��֐�(��)
	 **************************/
	private void EnemyAndMyShipTama (Graphics gBuf2) {
		int i, j;
		int[] enemyShipA_XYWH = new int[4];
		int[] myShipTama; //�e�̍��W����芸��������Ă������߂̔z��
		
		for (j=0; j<ENEMY_MAX; j++) {
			enemyShipA_XYWH = EnemyShip[j].getEnemyShip_XYWH(); //�G�@�̍��W�A�傫�����擾
			if (enemyVisible[j] == true) { //�e���L����������
				for (i=0; i<TAMA_MAX; i++) {
					if (tamaVisible[i] == true) { //�G����������ł��画�f����Ȃ�
						myShipTama = drawMyBullet[i].getTamaXYWH(); //�����̌������e�̍��W�B���B
						//X���W�̔���(�G�̑傫���ł����f)
						if (enemyShipA_XYWH[0]<=myShipTama[0] && myShipTama[0]<=enemyShipA_XYWH[0]+enemyShipA_XYWH[2]) {
							//Y���W����(�G�̑傫���ł����f)
							if (enemyShipA_XYWH[1]<=myShipTama[1] && myShipTama[1]<=enemyShipA_XYWH[1]+enemyShipA_XYWH[3]) {
							//���W������Ă�����
								//�X�R�A�ǉ�
								score += 5;
								if (score > score_max) score_max = score;
								
								//�����𐶐�
								createExplosion[explosion_count].init(enemyShipA_XYWH);
								ExplosionVisible[explosion_count] = true;
								
								explosion_count++;
								if (explosion_count == EXPLOSION_MAX-1) explosion_count = 0;
								
								//�G�̋@�̂�����
								enemyVisible[j] = false;
								//�e������
								tamaVisible[i] = false;
							}
						}
					}
				}
			} else { //���O�͂�������ł�ry
				//�G�͎���ł���̂œ��_�E�����͂�����Ȃ�
			}
		}
	}	
}






