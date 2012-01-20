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
	 * コンストラクタ
	 **************************/
	public KeyboardListener() {
		SpaceKeyCheck = 0; //長押し判定あり
		UpKeyCheck = false; //以下、trueで押下判定
		RightKeyCheck = false;
		DownKeyCheck = false;
		LeftKeyCheck = false;
		EscapeKeyCheck = false;
		System.out.println("動作てすと: KeyboardListener/コンストラクタ");
	}
	
	/**************************
	 * キーボード系
	 * 	←:37 ↑:38 →:39 ↓:40 Space:32 Enter:10 
	 **************************/
	//キーが押下時
	@Override
	public void keyPressed(KeyEvent arg0) {
		int KeyCode = arg0.getKeyCode();
		if (KeyCode == KeyEvent.VK_UP) UpKeyCheck = true;
		if (KeyCode == KeyEvent.VK_RIGHT) RightKeyCheck = true;
		if (KeyCode == KeyEvent.VK_DOWN) DownKeyCheck = true;
		if (KeyCode == KeyEvent.VK_LEFT) LeftKeyCheck = true;
		if (KeyCode == KeyEvent.VK_SPACE) {
			if (SpaceKeyCheck == 0) SpaceKeyCheck = 1; //押下判定
			else SpaceKeyCheck = 2; //長押し判定
		}
		if (KeyCode == KeyEvent.VK_ESCAPE) EscapeKeyCheck = true;
	}
	
	//キーが離された時
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
	
	//キーが入力された時のイベント？
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
	}
	
	/**************************
	 * 以下、それぞれのキーの押下判定
	 **************************/
	/*-------------------------
	 * Spaceキーの押下判定
	 * 	0:押されていない 1:押下 2:長押し中 
	 *------------------------*/
	public int SpaceKeyListener () {
		int space = 0;
		space = SpaceKeyCheck;
		return space;
	}
	/*-------------------------
	 * 上下の動き判定
	 * 	-1:上方向 0:動き無し 1:下方向
	 *------------------------*/
	public int UpDownListener () {
		int y = 0;
		if (UpKeyCheck) y = -1; else if (DownKeyCheck) y = 1;
		return y;
	}
	/*-------------------------
	 * 左右の動き判定
	 * 	-1:左方向 0:動き無し 1:右方向
	 *------------------------*/
	public int LeftRightListener () {
		int x = 0;
		if (LeftKeyCheck) x = -1; else if (RightKeyCheck) x = 1;
		return x;
	}
	/*-------------------------
	 * Escapeキーの押下判定
	 * 	0:押されていない 1:押下
	 *------------------------*/
	public int EscapeKeyListener () {
		int escape = 0;
		if (EscapeKeyCheck) escape = 1;
		return escape;
	}
}
