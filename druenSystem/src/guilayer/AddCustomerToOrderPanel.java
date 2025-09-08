package guilayer;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import controllayer.SalesOrderController;

public class AddCustomerToOrderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtIndtastKundensCvr;
	private JTextField inputCVRField;
	private JTextField txtTilfjKundeTil;
	private JTextField txtKundeTilfjet;
	private JTextField customerNameField;
	private SalesOrderController soc;

	public AddCustomerToOrderPanel(SalesOrderController soc) {
		this.soc = soc;
		setLayout(new SpringLayout());

		// Header text
		txtTilfjKundeTil = new JTextField("Tilføj kunde til ordre");
		txtTilfjKundeTil.setFont(new Font("Tahoma", Font.PLAIN, 26));
		txtTilfjKundeTil.setEditable(false);
		txtTilfjKundeTil.setColumns(10);
		add(txtTilfjKundeTil);

		// Label for CVR input
		txtIndtastKundensCvr = new JTextField("Indtast kundens CVR:");
		txtIndtastKundensCvr.setEditable(false);
		txtIndtastKundensCvr.setColumns(10);
		add(txtIndtastKundensCvr);

		// CVR input field
		inputCVRField = new JTextField();
		inputCVRField.setColumns(10);
		add(inputCVRField);

		// Add customer button
		JButton addButton = new JButton("TILFØJ");
		addButton.addActionListener(e -> addClicked());
		add(addButton);

		// Label for customer added
		txtKundeTilfjet = new JTextField("Kunde tilføjet:");
		txtKundeTilfjet.setEditable(false);
		txtKundeTilfjet.setColumns(10);
		add(txtKundeTilfjet);

		// Customer name field
		customerNameField = new JTextField();
		customerNameField.setColumns(10);
		add(customerNameField);

		// Layout setup
		SpringLayout layout = (SpringLayout) getLayout();
		layout.putConstraint(SpringLayout.NORTH, txtTilfjKundeTil, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, txtTilfjKundeTil, 37, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, txtIndtastKundensCvr, 20, SpringLayout.SOUTH, txtTilfjKundeTil);
		layout.putConstraint(SpringLayout.WEST, txtIndtastKundensCvr, 37, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, inputCVRField, 0, SpringLayout.NORTH, txtIndtastKundensCvr);
		layout.putConstraint(SpringLayout.WEST, inputCVRField, 20, SpringLayout.EAST, txtIndtastKundensCvr);
		layout.putConstraint(SpringLayout.EAST, inputCVRField, -50, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.NORTH, addButton, 18, SpringLayout.SOUTH, inputCVRField);
		layout.putConstraint(SpringLayout.WEST, addButton, 171, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, txtKundeTilfjet, 20, SpringLayout.SOUTH, addButton);
		layout.putConstraint(SpringLayout.WEST, txtKundeTilfjet, 37, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, customerNameField, 0, SpringLayout.NORTH, txtKundeTilfjet);
		layout.putConstraint(SpringLayout.WEST, customerNameField, 43, SpringLayout.EAST, txtKundeTilfjet);
		layout.putConstraint(SpringLayout.EAST, customerNameField, -27, SpringLayout.EAST, this);

	}

	private void addClicked() {
		// Get input from the CVR field and pass to the controller
		String inputString = inputCVRField.getText();
		if (soc.addCustomerToOrder(inputString)) {
			customerNameField.setText(soc.getCurrentOrder().getCustomer().getName());
		} else {
			customerNameField.setText("Ingen kunde fundet. Prøv igen");
		}
	}
}
