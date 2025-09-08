package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import control.EquipmentController;
import databaselayer.DataAccessException;
import model.Equipment;

public class CreateEquipmentFrame extends JFrame {

	private EquipmentController ec;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField stockField;
	private JTextField priceField;
	private JComboBox<String> categoryComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateEquipmentFrame frame = new CreateEquipmentFrame();
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
	public CreateEquipmentFrame() {

		try {
			ec = new EquipmentController(); // Ensure this aligns with your ProductController implementation
		} catch (Exception e) {
			complain("Initialization Error", "Could not initialize ProductController", e);
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 426, 0 };
		gbl_panel.rowHeights = new int[] { 224, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 102, 121, 96, 0 };
		gbl_panel_1.rowHeights = new int[] { 20, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);

		nameField = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 2;
		gbc_nameField.gridy = 0;
		panel_1.add(nameField, gbc_nameField);
		nameField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Category");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 1;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);

		categoryComboBox = new JComboBox<>(new String[] { "FOODS", "ITEMS", "OTHERS" });
		GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.gridx = 2;
		gbc_categoryComboBox.gridy = 1;
		panel_1.add(categoryComboBox, gbc_categoryComboBox);

		JLabel lblNewLabel_3 = new JLabel("Stock");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 2;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);

		stockField = new JTextField();
		GridBagConstraints gbc_rankField = new GridBagConstraints();
		gbc_rankField.insets = new Insets(0, 0, 5, 0);
		gbc_rankField.fill = GridBagConstraints.HORIZONTAL;
		gbc_rankField.gridx = 2;
		gbc_rankField.gridy = 2;
		panel_1.add(stockField, gbc_rankField);
		stockField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Price");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 3;
		panel_1.add(lblNewLabel_4, gbc_lblNewLabel_4);

		priceField = new JTextField();
		GridBagConstraints gbc_locationField = new GridBagConstraints();
		gbc_locationField.insets = new Insets(0, 0, 5, 0);
		gbc_locationField.fill = GridBagConstraints.HORIZONTAL;
		gbc_locationField.gridx = 2;
		gbc_locationField.gridy = 3;
		panel_1.add(priceField, gbc_locationField);
		priceField.setColumns(10);

		JButton btnNewButton = new JButton("Finish");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 4;

		btnNewButton.addActionListener(e -> {
			try {
				addEquipment(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel_1.add(btnNewButton, gbc_btnNewButton);

		//
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel("Equipment Creations");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_2.add(lblNewLabel_1);

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);

		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(e -> {
			try {
				backToMainFrame(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		panel_3.add(btnNewButton_1);
	}

	private void complain(String title, String text, Exception e) {
		JOptionPane.showMessageDialog(this, text + " (" + e.getMessage() + ") ", title, JOptionPane.OK_OPTION);
	}

	private void success(String text) {
		JOptionPane.showMessageDialog(this, text, text, JOptionPane.OK_CANCEL_OPTION);
	}

	private void addEquipment(ActionEvent e) throws SQLException {
		String name = nameField.getText();
		String stock = stockField.getText();
		String price = priceField.getText();
		String category = (String) categoryComboBox.getSelectedItem();

		if (name.isEmpty() || stock.isEmpty() || price.isEmpty()) {
			complain("Input error", "All fields must be filled out.", null);
			return;
		}

		try {

			int quantity = Integer.parseInt(stock);
			double value = Double.parseDouble(price);

			Equipment equipment = new Equipment(name, category, quantity, value);

			try {
				equipment = ec.addEquipment(equipment);
				success("Success! Equipment is added.");
			} catch (DataAccessException dae) {
				// e1.printStackTrace();
				complain("Data access error", "Could not insert new item", dae);
			}
			// Den her catch skal søger for at der opstår en error
			// hvis man skriver bogstav i stedet for int.
		} catch (NumberFormatException nfe) {
			complain("Input error", "input must be a number", nfe);
		}
	}

	private void backToMainFrame(ActionEvent e) throws SQLException {
		dispose(); // Close the current frame
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true); // Show the MainFrame
	}

}
