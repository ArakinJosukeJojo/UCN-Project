package guilayer;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controllayer.PackOrderController;

public class RemainingOrdersPanel extends JPanel implements DatabaseObserver {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JLabel monitorHeaderText;

	public RemainingOrdersPanel(PackOrderController poc) {
		// Set layout for the panel itself
		setLayout(new BorderLayout());

		// Initialize and add components
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		textField.setText("nothing to show");
		add(textField, BorderLayout.CENTER);

		// Register as observer
		poc.getMonitor().addObserver(this);

		monitorHeaderText = new JLabel("Ordrer der mangler at blive pakket:");
		monitorHeaderText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		monitorHeaderText.setHorizontalAlignment(JLabel.CENTER); // Center-align the text
		add(monitorHeaderText, BorderLayout.NORTH);
	}

	@Override
	public void update(int state) {
		System.out.println("update about to be called in observer frame");
		SwingUtilities.invokeLater(() -> textField.setText(Integer.toString(state)));
		System.out.println("update called in observer frame");
	}
}
