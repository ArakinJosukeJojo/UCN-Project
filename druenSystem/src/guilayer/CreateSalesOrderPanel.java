package guilayer;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import controllayer.SalesOrderController;

public class CreateSalesOrderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private SalesOrderController soc;
	private JTextField txtOpretNySalgsordre;
	private JRadioButton standardOrderButton;
	private JRadioButton standingOrderButton;
	private JButton btnNewButton;
	private JButton btnTilbage;
	private JButton createButton;

	public CreateSalesOrderPanel(SalesOrderController soc) throws SQLException {
		this.soc = soc;

		// Set layout and padding
		setBorder(new EmptyBorder(5, 5, 5, 5));
		SpringLayout sl_contentPane = new SpringLayout();
		setLayout(sl_contentPane);

		// Components
		txtOpretNySalgsordre = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtOpretNySalgsordre, 10, SpringLayout.NORTH, this);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtOpretNySalgsordre, 25, SpringLayout.WEST, this);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtOpretNySalgsordre, -25, SpringLayout.EAST, this);
		txtOpretNySalgsordre.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtOpretNySalgsordre.setText("Opret ny salgsordre");
		txtOpretNySalgsordre.setEditable(false);
		add(txtOpretNySalgsordre);

		standardOrderButton = new JRadioButton("Normal ordre");
		sl_contentPane.putConstraint(SpringLayout.NORTH, standardOrderButton, 50, SpringLayout.SOUTH,
				txtOpretNySalgsordre);
		sl_contentPane.putConstraint(SpringLayout.WEST, standardOrderButton, 25, SpringLayout.WEST, this);
		standardOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(standardOrderButton);

		standingOrderButton = new JRadioButton("Fastordre");
		standingOrderButton.setEnabled(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, standingOrderButton, 20, SpringLayout.SOUTH,
				standardOrderButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, standingOrderButton, 25, SpringLayout.WEST, this);
		standingOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(standingOrderButton);

		createButton = new JButton("Opret ordre");
		createButton.addActionListener(e -> {
			soc.createSalesOrder();
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, createButton, 168, SpringLayout.WEST, this);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, createButton, -51, SpringLayout.SOUTH, this);
		add(createButton);
	}
}
