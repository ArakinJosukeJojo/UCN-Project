package guilayer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import controllayer.PackOrderController;
import databaselayer.DataAccessException;
import model.Product;
import model.SalesOrderLine;

public class UpdateOrderLineDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField quantityField;
	private PackOrderController poc;
	private PackOrderPanel packPanel;

	/**
	 * Create the dialog.
	 */
	public UpdateOrderLineDialog(PackOrderPanel packPanel, PackOrderController poc, SalesOrderLine so) {
		this.poc = poc;
		this.packPanel = packPanel;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		JLabel productLabel = new JLabel("New label");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, productLabel, 15, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, productLabel, 15, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, productLabel, 42, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, productLabel, 176, SpringLayout.WEST, contentPanel);
		contentPanel.add(productLabel);

		quantityField = new JTextField();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, quantityField, 12, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, quantityField, 16, SpringLayout.EAST, productLabel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, quantityField, -3, SpringLayout.SOUTH, productLabel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, quantityField, 112, SpringLayout.EAST, productLabel);
		contentPanel.add(quantityField);
		quantityField.setColumns(10);

		JButton updateOrderLineButton = new JButton("Opdatér");
		updateOrderLineButton.addActionListener(e -> {
			updateOrderLineClicked(so);
		});
		sl_contentPanel.putConstraint(SpringLayout.NORTH, updateOrderLineButton, 35, SpringLayout.SOUTH, productLabel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, updateOrderLineButton, 10, SpringLayout.WEST, contentPanel);
		contentPanel.add(updateOrderLineButton);

		JButton removeOrderLineButton = new JButton("Fjern linje");
		removeOrderLineButton.addActionListener(e -> {
			removeOrderLineClicked(so);
		});
		sl_contentPanel.putConstraint(SpringLayout.NORTH, removeOrderLineButton, 0, SpringLayout.NORTH,
				updateOrderLineButton);
		sl_contentPanel.putConstraint(SpringLayout.WEST, removeOrderLineButton, 107, SpringLayout.EAST,
				updateOrderLineButton);
		contentPanel.add(removeOrderLineButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setVisible(true);
	}

	private void removeOrderLineClicked(SalesOrderLine so) {
		poc.getSalesOrderController().removeOrderLine(so);
		packPanel.refresh();
		dispose();
	}

	private void updateOrderLineClicked(SalesOrderLine so) {
		Product p = so.getProduct();
		double newQuantity = Double.parseDouble(quantityField.getText());
		try {
			poc.updateOrderLine(so, newQuantity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		packPanel.refresh();
		dispose();
	}
}
