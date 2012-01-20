package shootingGame201201;

import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame { //JFrame
	/* �悭�킩�炎 */
	//private static final long serialVersionUID = 1L;
	
	/* �ϐ� */
	private BufferedImage pictureImage;
	
	/* main�֐� */
	public static void main(String[] args) {
		new GameFrame();
		System.out.println("����Ă���: GameFrame/main");
	}
	
	/* ���� */
	GameFrame() {
		/* ��ʐݒ� */
//		setTitle("CPSF: Shooting Game -ver0.6-"); //�E�B���h�E�^�C�g��
		super("CPSF: Shooting Game -ver0.6-");
		setBounds(100, 100, 480, 740); //setSize(480,740);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�N���[�Y����
		
		/* �A�C�R���ݒ� */ /*
		ImageIcon icon = new ImageIcon("./icon.png");
	    setIconImage(icon.getImage());
		*/
		
		/* ���j���[�o�[ */ //�L�[�{�[�h�A�N�Z�����[�^�Q�lURI: http://www.javadrive.jp/tutorial/jmenu/
		JMenuBar menubar = new JMenuBar();
		JMenu menu1 = new JMenu("���j���[");	JMenu menu2 = new JMenu("�Q�[���ݒ�");
		JMenu menu3 = new JMenu("�𑜓x�Ƃ�");	JMenu menu4 = new JMenu("�w���v");
			
			//(���j���[�o�[�̑�1�K�w)
			menubar.add(menu1);	menubar.add(menu2); menubar.add(menu3);
//			menubar.add(Box.createRigidArea(new Dimension(20,1))); //���j���[�Ԃɗ]��������
			menubar.add(Box.createHorizontalGlue()); menubar.add(menu4);
			//(���j���[�o�[�̑�2�K�w)
				//[���j���[]
				JMenuItem menuitem1_1 = new JMenuItem("CPSF/Shooting Game�ɂ���");
				JMenuItem menuitem1_2 = new JMenuItem("���ݒ�");
				JMenuItem menuitem1_3 = new JMenuItem("�X�R�A���M�Ƃ��H");
				JMenuItem menuitem1_4 = new JMenuItem("�Q�[�����I��");
				menu1.add(menuitem1_1);	menuitem1_1.setEnabled(false);	menu1.addSeparator(); //�Z�p���[�^
				menu1.add(menuitem1_2);	menuitem1_2.setEnabled(false);	menu1.addSeparator();
				menu1.add(menuitem1_3);	menuitem1_3.setEnabled(false);	menu1.addSeparator();
				menu1.add(menuitem1_4);
				//[�Q�[���ݒ�]
				JMenuItem menuitem2_1 = new JMenuItem("(�܂�)");
				menu2.add(menuitem2_1); menuitem2_1.setEnabled(false);
				//[�𑜓x�݂�����]
				JMenuItem menuitem3_1 = new JMenuItem("(�܂�)");
				menu3.add(menuitem3_1); menuitem3_1.setEnabled(false);
				//�w���v
				JMenuItem menuitem4_1 = new JMenuItem("(�܂�)");
				menu4.add(menuitem4_1); menuitem4_1.setEnabled(false);
		setJMenuBar(menubar);
		
		/* ���C�A�E�g�\�� */
		//Container pane1 = getContentPane(); //ContentPane�擾
		//pane1.setLayout(new BorderLayout()); //�R���|�[�l���g�̒ǉ�
		
		//�L�����o�X�𐶐�
		MyCanvas canv = new MyCanvas();
		add(canv);

		/* �摜�\���e�X�g */ /*
		ImageIcon icon1 = new ImageIcon("./my_ship.jpg");
		JLabel label1 = new JLabel(icon1);
		pane1.add(label1);
		*/
		
	    setVisible(true); //�\����L����
	    
		//�Q�[���f�[�^�̏�����
		canv.init();
		//�X���b�h���쐬
		canv.initThread();
	}
}
