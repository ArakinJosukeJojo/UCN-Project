package guilayer;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllayer.SalesOrderController;
import model.Product;

public class AddProductDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private JTextField txtNavn;
	private JTextField txtAntal;
	private JTextField quantityField;
	private JRadioButton typeButton;
	private SalesOrderController soc;
	private Product p;
	private JRadioButton btnKasse;
	private ButtonGroup buttonGroup;
	private FindAndAddProductToOrderPanel frame;

	public AddProductDialog(FindAndAddProductToOrderPanel frame, Product p, SalesOrderController soc) {
		this.frame = frame;
		this.soc = soc;
		this.p = p;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		{
			txtNavn = new JTextField();
			txtNavn.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtNavn.setText("Navn:");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, txtNavn, 28, SpringLayout.NORTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.WEST, txtNavn, 30, SpringLayout.WEST, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, txtNavn, -162, SpringLayout.SOUTH, contentPanel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, txtNavn, 101, SpringLayout.WEST, contentPanel);
			txtNavn.setEditable(false);
			contentPanel.add(txtNavn);
			txtNavn.setColumns(10);
		}
		{
			nameField = new JTextField();
			nameField.setFont(new Font("Tahoma", Font.PLAIN, 18));
			sl_contentPanel.putConstraint(SpringLayout.NORTH, nameField, 0, SpringLayout.NORTH, txtNavn);
			sl_contentPanel.putConstraint(SpringLayout.WEST, nameField, 16, SpringLayout.EAST, txtNavn);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, nameField, 0, SpringLayout.SOUTH, txtNavn);
			sl_contentPanel.putConstraint(SpringLayout.EAST, nameField, -5, SpringLayout.EAST, contentPanel);
			nameField.setEditable(false);
			contentPanel.add(nameField);
			nameField.setColumns(10);
			nameField.setText(p.getName());
		}
		{
			txtAntal = new JTextField();
			txtAntal.setFont(new Font("Tahoma", Font.PLAIN, 18));
			txtAntal.setText("Antal:");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, txtAntal, 49, SpringLayout.SOUTH, txtNavn);
			sl_contentPanel.putConstraint(SpringLayout.WEST, txtAntal, 0, SpringLayout.WEST, txtNavn);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, txtAntal, 79, SpringLayout.SOUTH, txtNavn);
			sl_contentPanel.putConstraint(SpringLayout.EAST, txtAntal, 71, SpringLayout.WEST, txtNavn);
			txtAntal.setEditable(false);
			txtAntal.setColumns(10);
			contentPanel.add(txtAntal);
		}
		{
			quantityField = new JTextField();
			quantityField.setFont(new Font("Tahoma", Font.PLAIN, 18));
			sl_contentPanel.putConstraint(SpringLayout.NORTH, quantityField, 0, SpringLayout.NORTH, txtAntal);
			sl_contentPanel.putConstraint(SpringLayout.WEST, quantityField, 0, SpringLayout.WEST, nameField);
			sl_contentPanel.putConstraint(SpringLayout.SOUTH, quantityField, 30, SpringLayout.NORTH, txtAntal);
			sl_contentPanel.putConstraint(SpringLayout.EAST, quantityField, -153, SpringLayout.EAST, nameField);
			quantityField.setColumns(10);
			contentPanel.add(quantityField);
		}

		typeButton = new JRadioButton("Styk");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, typeButton, -1, SpringLayout.NORTH, txtAntal);
		sl_contentPanel.putConstraint(SpringLayout.WEST, typeButton, 15, SpringLayout.EAST, quantityField);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, typeButton, 78, SpringLayout.SOUTH, nameField);
		sl_contentPanel.putConstraint(SpringLayout.EAST, typeButton, 73, SpringLayout.EAST, quantityField);
		contentPanel.add(typeButton);

		btnKasse = new JRadioButton("Kasse");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, btnKasse, -1, SpringLayout.NORTH, txtAntal);
		sl_contentPanel.putConstraint(SpringLayout.WEST, btnKasse, 6, SpringLayout.EAST, typeButton);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, btnKasse, 29, SpringLayout.NORTH, txtAntal);
		sl_contentPanel.putConstraint(SpringLayout.EAST, btnKasse, -20, SpringLayout.EAST, contentPanel);
		contentPanel.add(btnKasse);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(btnKasse);
		buttonGroup.add(typeButton);

		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
				cancelButton.setVerticalAlignment(SwingConstants.BOTTOM);
				cancelButton.setHorizontalAlignment(SwingConstants.LEFT);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, BorderLayout.WEST);
			}
			{
				JButton okButton = new JButton("TILFØJ");
				okButton.addActionListener(e -> {
					addButtonClicked();
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, BorderLayout.EAST);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setVisible(true);
	}

	private void addButtonClicked() {
		// if the customer purchases in weight, hide the buttons.
		if (soc.getCurrentOrder().getCustomer().getPreferedUnit().equals("Weight")) {
			btnKasse.setEnabled(false);
			btnKasse.setVisible(false);
			typeButton.setEnabled(false);
			typeButton.setVisible(false);
		}
		// if the product is sold in bundles, name the button "bundt" instead of "styk".
		if (p.getUnitConverter().getUnitConversionType().equals("bundle")) {
			typeButton.setText("Bundt");
		}
		// get the user input of quantity, and if the box button is selected, calculate
		// the quantity.
		try {
			double quantity = Double.parseDouble(quantityField.getText());
			if (btnKasse.isSelected()) {
				int itemsPerBox = (int) Math
						.round(p.getUnitConverter().getBoxWeight() / p.getUnitConverter().getConversionRate());
				quantity = quantity * itemsPerBox;
			}
			// add the product to the order calling the controller
			try {
				soc.addProductToOrder(p, quantity);
			} catch (Exception e) {

			}
			// close down the window after adding
			frame.updateOrderList();
			dispose();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Indtast en gyldig værdi", "Ugyldigt Input", JOptionPane.ERROR_MESSAGE);
		}
	}
}
