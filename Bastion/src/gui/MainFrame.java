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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import databaselayer.DataAccessException;
import databaselayer.DataFill;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel);

		JButton btnNewButton = new JButton("Character Creation");
		btnNewButton.addActionListener(e -> {
			try {
				characterCreation(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Monster Creation");
		btnNewButton_1.addActionListener(e -> {
			try {
				monsterCreation(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Data Overview");
		btnNewButton_2.addActionListener(e -> {
			try {
				dataOverview(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		JButton btnNewButton_4 = new JButton("Equipment Creation");
		btnNewButton_4.addActionListener(e -> {
			try {
				equipmentCreation(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel.add(btnNewButton_4);
		panel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Fill Data");
		btnNewButton_3.addActionListener(e -> {
			try {
				fillData(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (DataAccessException e1) {
				e1.printStackTrace();
			}
		});
		panel.add(btnNewButton_3);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel_4 = new JLabel("Bastion Data Management");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNewLabel_4);
	}

	private void complain(String title, String text, Exception e) {
		JOptionPane.showMessageDialog(this, text + " (" + e.getMessage() + ") ", title, JOptionPane.OK_OPTION);
	}

	private void success(String text) {
		JOptionPane.showMessageDialog(this, text, text, JOptionPane.OK_CANCEL_OPTION);
	}

	private void characterCreation(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		CreateCharacterFrame createCharacterFrame = new CreateCharacterFrame();
		createCharacterFrame.setVisible(true); // Show the CreateCharacterFrame
	}

	private void monsterCreation(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		CreateMonsterFrame createMonsterFrame = new CreateMonsterFrame();
		createMonsterFrame.setVisible(true); // Show the CreateMonsterFrame
	}

	private void equipmentCreation(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		CreateEquipmentFrame createEquipmentFrame = new CreateEquipmentFrame();
		createEquipmentFrame.setVisible(true); // Show the CreateMonsterFrame
	}

	private void fillData(ActionEvent e) throws SQLException, DataAccessException {
		DataFill df = new DataFill();
		df.cleanAndRecreateDatabase();
		success("Success! Data are reset and added.");

	}

	private void dataOverview(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		DataOverviewFrame dataOverviewFrame = new DataOverviewFrame();
		dataOverviewFrame.setVisible(true); // Show the CreateCharacterFrame
	}

}
