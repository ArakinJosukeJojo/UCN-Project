package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.MonsterController;
import databaselayer.DataAccessException;
import model.Monster;

public class FindMonsterFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private MonsterController monsterController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FindMonsterFrame frame = new FindMonsterFrame();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FindMonsterFrame() {
		// Initialize the product controller
		try {
			monsterController = new MonsterController();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Search:");
		panel.add(lblNewLabel);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(15);

		JButton btnFind = new JButton("Find");
		panel.add(btnFind);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Set up table to display product details
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Name", "Lifespan", "Classification", "Size", "Habitat", "Danger" }));
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Back");
		panel_1.add(btnNewButton);

		// Add action listener to the Find button
		btnFind.addActionListener(e -> {
			try {
				searchMonster();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		btnNewButton.addActionListener(e -> {
			try {
				goBack(e); // Call the goBack method when Back button is clicked
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}

	/**
	 * Search for a product by barcode and display it in the table.
	 */
	private void searchMonster() throws SQLException {
		String name = textField.getText();
		if (name.isEmpty()) {
			// If the input field is empty, do nothing or show a warning
			return;
		}
		try {
			List<Monster> monster = monsterController.findMonsterByName(name);
			if (monster != null) {
				displayMonster(monster);
			} else {
				clearTable();
				System.out.println("Monster not found");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display the product in the table.
	 */
	private void displayMonster(List<Monster> monsters) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Clear existing rows

		// Loop through the list and add each character as a row in the table
		for (Monster monster : monsters) {
			model.addRow(new Object[] { monster.getName(), monster.getLifespan(), monster.getClassification(),
					monster.getSize(), monster.getHabitat(), monster.getDanger() });
		}
		;
	}

	/**
	 * Clear the table.
	 */
	private void clearTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
	}

	private void goBack(ActionEvent e) throws SQLException {
		this.setVisible(false); // Hide the current MainFrame
		DataOverviewFrame dataOverviewFrame = new DataOverviewFrame();
		dataOverviewFrame.setVisible(true); // Show the CreateCharacterFrame
	}
}
