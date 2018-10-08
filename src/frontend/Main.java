package frontend;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame marco = new JFrame("te amo");
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setBounds(300, 100, 640, 400);
		marco.setVisible(true);

		marco.setContentPane(new Lista(marco));
		marco.validate();

		// SQL sql = new SQL("127.0.0.1");

		// sql.GetData();
	}

}
