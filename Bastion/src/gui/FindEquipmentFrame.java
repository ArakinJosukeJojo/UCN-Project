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

import control.EquipmentController;
import databaselayer.DataAccessException;
import model.Equipment;

public class FindEquipmentFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private EquipmentController equipmentController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FindEquipmentFrame frame = new FindEquipmentFrame();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FindEquipmentFrame() {
		// Initialize the product controller
		try {
			equipmentController = new EquipmentController();
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
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Category", "Stock", "Price" }));
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Back");
		panel_1.add(btnNewButton);

		// Add action listener to the Find button
		btnFind.addActionListener(e -> searchEquipment());

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
	private void searchEquipment() {
		String name = textField.getText();
		if (name.isEmpty()) {
			// If the input field is empty, do nothing or show a warning
			return;
		}
		try {
			// Fetch the product using the product controller
			List<Equipment> equipment = equipmentController.findEquipmentByName(name);
			if (equipment != null) {
				displayEquipment(equipment);
			} else {
				clearTable();
				System.out.println("Character not found");
			}
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display the product in the table.
	 */
	private void displayEquipment(List<Equipment> equipment) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // Clear existing rows

		// Loop through the list and add each character as a row in the table
		for (Equipment equipments : equipment) {
			model.addRow(new Object[] { equipments.getName(), equipments.getCategory(), equipments.getStock(),
					equipments.getPrice() });
		}
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
