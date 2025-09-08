package guilayer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controllayer.PackOrderController;
import model.Product;
import model.SalesOrderLine;

public class PackOrderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultListModel<SalesOrderLine> orderLineListModel;
	private JList<SalesOrderLine> orderLineList;
	private JButton updateButton;
	private JButton discardButton;
	private JButton saveButton;
	private JButton togglePackedButton;
	private JTextField headerText;
	private PackOrderController poc;
	private Set<SalesOrderLine> packedOrderLines; // Tracks packed order lines

	public PackOrderPanel(PackOrderController poc) {
		setLayout(new BorderLayout());
		this.poc = poc;
		packedOrderLines = new HashSet<>();

		// Header Text
		headerText = new JTextField("Pak Ordre");
		headerText.setEditable(false);
		headerText.setHorizontalAlignment(JTextField.CENTER);
		add(headerText, BorderLayout.NORTH);

		// Order Line List
		orderLineListModel = new DefaultListModel<>();
		orderLineList = new JList<>(orderLineListModel);
		orderLineList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
			// Custom renderer to display [Packed] for packed order lines
			String text = value.toString();
			if (packedOrderLines.contains(value)) {
				text += " [Packed]";
			}
			JLabel label = new JLabel(text);
			label.setOpaque(true); // Make the background visible
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
			} else {
				label.setBackground(list.getBackground());
				label.setForeground(list.getForeground());
			}
			return label;
		});
		JScrollPane listScrollPane = new JScrollPane(orderLineList);
		add(listScrollPane, BorderLayout.CENTER);

		// Buttons Panel
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0)); // 1 row, 4 columns, spacing
		updateButton = new JButton("Opdater");
		updateButton.addActionListener(e -> {
			SalesOrderLine so = orderLineList.getSelectedValue();
			if (so != null) {
				updateOrderLineClicked(so);
			}
		});

		discardButton = new JButton("Kassér produkt");
		discardButton.addActionListener(e -> {
			SalesOrderLine so = orderLineList.getSelectedValue();
			if (so != null) {
				discardProductClicked(so);
			}
		});

		togglePackedButton = new JButton("Marker/Umarker");
		togglePackedButton.addActionListener(e -> {
			SalesOrderLine so = orderLineList.getSelectedValue();
			if (so != null) {
				togglePackedStatus(so);
			}
		});

		saveButton = new JButton("Gem");
		saveButton.addActionListener(e -> saveOrder());

		buttonPanel.add(updateButton);
		buttonPanel.add(discardButton);
		buttonPanel.add(togglePackedButton);
		buttonPanel.add(saveButton);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	private void updateOrderLineClicked(SalesOrderLine so) {
		new UpdateOrderLineDialog(this, poc, so);
	}

	private void discardProductClicked(SalesOrderLine so) {
		Product productToDiscard = so.getProduct();
		new DiscardProductDialog(this, poc, productToDiscard);
	}

	private void togglePackedStatus(SalesOrderLine so) {
		if (packedOrderLines.contains(so)) {
			// Unmark the order line
			packedOrderLines.remove(so);
			System.out.println("Unmarked as packed: " + so);
		} else {
			// Mark the order line as packed
			packedOrderLines.add(so);
			System.out.println("Marked as packed: " + so);
		}
		// Refresh the list to update display
		orderLineList.repaint();
	}

	private void saveOrder() {
		try {
			poc.updateOrder();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refresh() {
		packedOrderLines.clear();
		orderLineListModel.clear();
		if (poc.getSalesOrderController().getCurrentOrder() != null) {
			System.out.println("Current Order: " + poc.getSalesOrderController().getCurrentOrder());
			System.out.println("Order Lines: " + poc.getSalesOrderController().getCurrentOrder().getOrderLines());
			orderLineListModel.addAll(poc.getSalesOrderController().getCurrentOrder().getOrderLines());
		} else {
			System.out.println("No current order to refresh.");
		}
	}
}
