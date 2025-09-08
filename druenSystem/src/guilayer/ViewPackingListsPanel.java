package guilayer;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllayer.PackOrderController;
import model.PackingList;

public class ViewPackingListsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtPaklister;
	private DefaultListModel<PackingList> listModelPackingList;
	private JList<PackingList> packingList;
	private PackOrderController packOrderController;

	public ViewPackingListsPanel(PackOrderController packOrderController) {
		this.packOrderController = packOrderController;
		// Initialize layout and components
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 416, 164);
		add(scrollPane);

		listModelPackingList = new DefaultListModel<>();
		packingList = new JList<>(listModelPackingList);
		scrollPane.setViewportView(packingList);

		txtPaklister = new JTextField();
		txtPaklister.setHorizontalAlignment(SwingConstants.CENTER);
		txtPaklister.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtPaklister.setText("Paklister");
		txtPaklister.setEditable(false);
		txtPaklister.setBounds(10, 10, 416, 27);
		add(txtPaklister);

		JButton cancelButton = new JButton("Tilbage");
		cancelButton.setBounds(10, 232, 85, 21);
		cancelButton.addActionListener(e -> handleBack());
		add(cancelButton);

		JButton viewButton = new JButton("Se Ordrer");
		viewButton.setBounds(341, 232, 85, 21);
		viewButton.addActionListener(e -> {
			PackingList pl = packingList.getSelectedValue();
			if (pl != null) {
				viewPackingListClicked(pl);
			}
		});
		add(viewButton);

		// Populate the packing list
		populatePackingLists();
	}

	private void populatePackingLists() {
		try {
			listModelPackingList.clear();
			listModelPackingList.addAll(packOrderController.viewPackingLists());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void viewPackingListClicked(PackingList packingList) {
		try {
			// Take the packing list from the list and call the controller to populate it
			PackingList pl = packOrderController.selectPackingList(packingList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleBack() {
		System.out.println("Back button clicked. Implement navigation logic.");
		// Implement back navigation logic if needed
	}
}
