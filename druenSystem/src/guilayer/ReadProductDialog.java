package guilayer;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import model.Product;

public class ReadProductDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNotYetImplemented;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	public ReadProductDialog(Product p) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		txtNotYetImplemented = new JTextField();
		txtNotYetImplemented.setText("Not yet implemented");
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtNotYetImplemented, 5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtNotYetImplemented, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtNotYetImplemented, 431, SpringLayout.WEST, contentPane);
		contentPane.add(txtNotYetImplemented);
		txtNotYetImplemented.setColumns(10);

		textField = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, 31, SpringLayout.SOUTH, txtNotYetImplemented);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, contentPane);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_2 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_2, 26, SpringLayout.SOUTH, textField);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_2, 0, SpringLayout.EAST, textField);
		textField_2.setColumns(10);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_3, 20, SpringLayout.SOUTH, textField_2);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_3, 0, SpringLayout.EAST, textField);
		textField_3.setColumns(10);
		contentPane.add(textField_3);

		textField_4 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_4, 19, SpringLayout.SOUTH, textField_3);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField_4, 0, SpringLayout.EAST, textField);
		textField_4.setColumns(10);
		contentPane.add(textField_4);

		textField_5 = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField_5, 17, SpringLayout.SOUTH, textField_4);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField_5, 0, SpringLayout.WEST, textField);
		textField_5.setColumns(10);
		contentPane.add(textField_5);
		setVisible(true);
	}

}
