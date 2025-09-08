package guilayer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import controllayer.SalesOrderController;
import model.Product;
import model.SalesOrderLine;

public class FindAndAddProductToOrderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtSgProdukt;
	private JTextField textField;
	private JList<Product> findProductList;
	private JList<SalesOrderLine> orderProductList;
	private SalesOrderController soc;
	private DefaultListModel<Product> listModelProduct = new DefaultListModel<>();
	private DefaultListModel<SalesOrderLine> listModelOrderline = new DefaultListModel<>();

	public FindAndAddProductToOrderPanel(SalesOrderController soc) {
		this.soc = soc;

		setLayout(new GridLayout(1, 0));

		// Left Panel for Product Search
		JPanel productSearchPanel = new JPanel(new BorderLayout());
		add(productSearchPanel);

		// Product Search Controls
		JPanel productSearchControls = new JPanel();
		productSearchPanel.add(productSearchControls, BorderLayout.SOUTH);

		JButton backButton = new JButton("Tilbage");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productSearchControls.add(backButton);

		JButton searchButton = new JButton("Søg Produkt");
		searchButton.addActionListener(e -> searchProductClicked());
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productSearchControls.add(searchButton);

		JButton addProductButton = new JButton("Tilføj til ordre");
		addProductButton.addActionListener(e -> {
			Product p = findProductList.getSelectedValue();
			if (p != null) {
				addProductClicked(p);
			}
		});
		addProductButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productSearchControls.add(addProductButton);

		JButton viewProductButton = new JButton("Se produktinfo");
		viewProductButton.addActionListener(e -> {
			Product p = findProductList.getSelectedValue();
			if (p != null) {
				readProductInfoClicked(p);
			}
		});
		viewProductButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productSearchControls.add(viewProductButton);

		// Product List Scroll Pane
		JScrollPane productScrollPane = new JScrollPane();
		productSearchPanel.add(productScrollPane, BorderLayout.CENTER);

		findProductList = new JList<>();
		productScrollPane.setViewportView(findProductList);

		// Search Field
		JSplitPane searchPane = new JSplitPane();
		productScrollPane.setColumnHeaderView(searchPane);

		txtSgProdukt = new JTextField("Søg produkt:");
		txtSgProdukt.setEditable(false);
		txtSgProdukt.setColumns(10);
		searchPane.setLeftComponent(txtSgProdukt);

		textField = new JTextField("...");
		textField.setColumns(10);
		searchPane.setRightComponent(textField);

		// Right Panel for Order Product List
		JPanel orderProductPanel = new JPanel(new BorderLayout());
		add(orderProductPanel);

		// Order Product Controls
		JPanel orderProductControls = new JPanel();
		orderProductPanel.add(orderProductControls, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Annullér");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		orderProductControls.add(cancelButton);

		JButton removeProductButton = new JButton("Slet Produkt");
		removeProductButton.addActionListener(e -> {
			SalesOrderLine line = orderProductList.getSelectedValue();
			if (line != null) {
				removeProductClicked(line.getProduct());
			}
		});
		removeProductButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		orderProductControls.add(removeProductButton);

		JButton saveOrderButton = new JButton("Gem ordre");
		saveOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		saveOrderButton.addActionListener(e -> saveOrderClicked());
		orderProductControls.add(saveOrderButton);

		// Order Product List Scroll Pane
		JScrollPane orderScrollPane = new JScrollPane();
		orderProductPanel.add(orderScrollPane, BorderLayout.CENTER);

		orderProductList = new JList<>();
		orderScrollPane.setViewportView(orderProductList);
	}

	private void saveOrderClicked() {
		soc.saveOrder();
	}

	private void removeProductClicked(Product p) {
		// Remove a product from a sales order
		// soc.removeProductFromOrder();
	}

	private void addProductClicked(Product p) {
		// Open a new JDialog for the specific product, passing along the stateful
		// controller
		new AddProductDialog(this, p, soc);
	}

	private void readProductInfoClicked(Product p) {
		// Open a new JFrame for the specific product
		new ReadProductDialog(p);
	}

	private void searchProductClicked() {
		String searchString = textField.getText();
		updateProductList(soc.findProduct(searchString));
	}

	private void updateProductList(List<Product> list) {
		listModelProduct.clear();
		listModelProduct.addAll(list);
		findProductList.setModel(listModelProduct);
	}

	public void updateOrderList() {
		listModelOrderline.clear();
		listModelOrderline.addAll(soc.getCurrentOrder().getOrderLines());
		orderProductList.setModel(listModelOrderline);
	}
}
