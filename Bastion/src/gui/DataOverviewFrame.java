package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DataOverviewFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataOverviewFrame frame = new DataOverviewFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataOverviewFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel);

		JButton btnNewButton = new JButton("Character Data");
		btnNewButton.addActionListener(e -> {
			try {
				characterOverview(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Monster Data");
		btnNewButton_1.addActionListener(e -> {
			try {
				monsterOverview(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Equipment Data");
		btnNewButton_2.addActionListener(e -> {
			try {
				equipmentOverview(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Back");
		btnNewButton_3.addActionListener(e -> {
			try {
				goBack(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel.add(btnNewButton_3);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel_4 = new JLabel("Data Overview");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNewLabel_4);
	}

	private void goBack(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true); // Show the CreateCharacterFrame
	}

	private void characterOverview(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		FindCharacterFrame characterFrame = new FindCharacterFrame();
		characterFrame.setVisible(true); // Show the CreateCharacterFrame
	}

	private void monsterOverview(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		FindMonsterFrame monsterFrame = new FindMonsterFrame();
		monsterFrame.setVisible(true); // Show the CreateCharacterFrame
	}

	private void equipmentOverview(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		FindEquipmentFrame equipmentFrame = new FindEquipmentFrame();
		equipmentFrame.setVisible(true); // Show the CreateCharacterFrame
	}

}
