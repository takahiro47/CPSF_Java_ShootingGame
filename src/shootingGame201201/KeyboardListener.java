package shootingGame201201;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//http://www.maroon.dti.ne.jp/koten-kairo/works/Java3D/key4.html

public class KeyboardListener /* implements KeyListener*/ extends KeyAdapter {	
	int SpaceKeyCheck;
	boolean UpKeyCheck;
	boolean RightKeyCheck;
	boolean DownKeyCheck;
	boolean LeftKeyCheck;
	boolean EscapeKeyCheck;
	
	
	/**************************
	 * �R���X�g���N�^
	 **************************/
	public KeyboardListener() {
		SpaceKeyCheck = 0; //���������肠��
		UpKeyCheck = false; //�ȉ��Atrue�ŉ�������
		RightKeyCheck = false;
		DownKeyCheck = false;
		LeftKeyCheck = false;
		EscapeKeyCheck = false;
		System.out.println("����Ă���: KeyboardListener/�R���X�g���N�^");
	}
	
	/**************************
	 * �L�[�{�[�h�n
	 * 	��:37 ��:38 ��:39 ��:40 Space:32 Enter:10 
	 **************************/
	//�L�[��������
	@Override
	public void keyPressed(KeyEvent arg0) {
		int KeyCode = arg0.getKeyCode();
		if (KeyCode == KeyEvent.VK_UP) UpKeyCheck = true;
		if (KeyCode == KeyEvent.VK_RIGHT) RightKeyCheck = true;
		if (KeyCode == KeyEvent.VK_DOWN) DownKeyCheck = true;
		if (KeyCode == KeyEvent.VK_LEFT) LeftKeyCheck = true;
		if (KeyCode == KeyEvent.VK_SPACE) {
			if (SpaceKeyCheck == 0) SpaceKeyCheck = 1; //��������
			else SpaceKeyCheck = 2; //����������
		}
		if (KeyCode == KeyEvent.VK_ESCAPE) EscapeKeyCheck = true;
	}
	
	//�L�[�������ꂽ��
	@Override
	public void keyReleased(KeyEvent arg0) {
		int KeyCode = arg0.getKeyCode();
		if (KeyCode == KeyEvent.VK_UP) UpKeyCheck = false;
		if (KeyCode == KeyEvent.VK_RIGHT) RightKeyCheck = false;
		if (KeyCode == KeyEvent.VK_DOWN) DownKeyCheck = false;
		if (KeyCode == KeyEvent.VK_LEFT) LeftKeyCheck = false;
		if (KeyCode == KeyEvent.VK_SPACE) SpaceKeyCheck = 0;
		if (KeyCode == KeyEvent.VK_ESCAPE) EscapeKeyCheck = false;
	}
	
	//�L�[�����͂��ꂽ���̃C�x���g�H
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
	}
	
	/**************************
	 * �ȉ��A���ꂼ��̃L�[�̉�������
	 **************************/
	/*-------------------------
	 * Space�L�[�̉�������
	 * 	0:������Ă��Ȃ� 1:���� 2:�������� 
	 *------------------------*/
	public int SpaceKeyListener () {
		int space = 0;
		space = SpaceKeyCheck;
		return space;
	}
	/*-------------------------
	 * �㉺�̓�������
	 * 	-1:����� 0:�������� 1:������
	 *------------------------*/
	public int UpDownListener () {
		int y = 0;
		if (UpKeyCheck) y = -1; else if (DownKeyCheck) y = 1;
		return y;
	}
	/*-------------------------
	 * ���E�̓�������
	 * 	-1:������ 0:�������� 1:�E����
	 *------------------------*/
	public int LeftRightListener () {
		int x = 0;
		if (LeftKeyCheck) x = -1; else if (RightKeyCheck) x = 1;
		return x;
	}
	/*-------------------------
	 * Escape�L�[�̉�������
	 * 	0:������Ă��Ȃ� 1:����
	 *------------------------*/
	public int EscapeKeyListener () {
		int escape = 0;
		if (EscapeKeyCheck) escape = 1;
		return escape;
	}
}
