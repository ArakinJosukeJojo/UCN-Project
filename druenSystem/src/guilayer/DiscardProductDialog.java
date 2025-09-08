package guilayer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controllayer.PackOrderController;
import model.Product;

public class DiscardProductDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField quantityInput;
	private JTextField quantityTextField;
	private Product p;
	private PackOrderPanel packPanel;

	/**
	 * Create the dialog.
	 */
	public DiscardProductDialog(PackOrderPanel packPanel, PackOrderController poc, Product p) {
		this.packPanel = packPanel;
		this.p = p;
		setBounds(100, 100, 250, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			quantityTextField = new JTextField();
			quantityTextField.setEditable(false);
			contentPanel.add(quantityTextField);
			quantityTextField.setColumns(10);
		}
		{
			quantityInput = new JTextField();
			contentPanel.add(quantityInput);
			quantityInput.setColumns(10);
		}
		{
			JButton discardButton = new JButton("Kassér");
			discardButton.addActionListener(e -> {
				discardButtonClicked(poc);
			});
			contentPanel.add(discardButton);
		}
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

	private void discardButtonClicked(PackOrderController poc) {
		double quantityToDiscard = Double.parseDouble(quantityInput.getText());
		double convertedQuantity = poc.getSalesOrderController().getCurrentOrder().convertToWeight(p,
				quantityToDiscard);
		poc.discardProducts(p, quantityToDiscard);
		packPanel.refresh();
		dispose();
	}

}
