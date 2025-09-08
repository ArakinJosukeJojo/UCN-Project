package guilayer;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controllayer.PackOrderController;
import model.SalesOrder;

public class ViewPackingListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField header;
	private DefaultListModel<SalesOrder> listModelSalesOrders;
	private JList<SalesOrder> ordersList;
	private PackOrderController poc;

	public ViewPackingListPanel(PackOrderController poc) {
		// Initialize panel layout
		setLayout(null);
		this.poc = poc;
		// Header field
		header = new JTextField("Paklister");
		header.setFont(new Font("Tahoma", Font.PLAIN, 25));
		header.setEditable(false);
		header.setBounds(10, 10, 416, 30);
		add(header);

		// Scroll pane for the orders list
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 416, 159);
		add(scrollPane);

		// Orders list model and JList
		listModelSalesOrders = new DefaultListModel<>();
		ordersList = new JList<>(listModelSalesOrders);
		scrollPane.setViewportView(ordersList);

		// Add sales orders to the list model
		listModelSalesOrders.addAll(poc.getCurrentPackingList());

		// Back button
		JButton cancelButton = new JButton("Tilbage");
		cancelButton.setBounds(10, 242, 85, 21);
		cancelButton.addActionListener(e -> handleBack());
		add(cancelButton);

		// Pack Order button
		JButton packOrderButton = new JButton("Pak Ordre");
		packOrderButton.setBounds(341, 242, 85, 21);
		packOrderButton.addActionListener(e -> {
			SalesOrder s = ordersList.getSelectedValue();
			if (s != null) {
				packOrderClicked(s);
			}
		});
		add(packOrderButton);
	}

	private void handleBack() {
		System.out.println("Back button clicked. Implement navigation logic.");
		// Implement back navigation logic if needed
	}

	private void packOrderClicked(SalesOrder s) {
		try {
			poc.selectOrder(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refresh() {
		listModelSalesOrders.clear();
		listModelSalesOrders.addAll(poc.getCurrentPackingList());
	}
}
