package guilayer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllayer.PackOrderController;
import controllayer.SalesOrderController;
import databaselayer.DBCleaner;
import databaselayer.DataAccessException;

public class MainCreateSalesOrderFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PackOrderController poc;
	private SalesOrderController soc;
	private JPanel mainPanel;
	private JPanel monitorPanel;
	private JPanel topPanel;
	private CardLayout cardLayout;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBCleaner dbc = new DBCleaner();
					dbc.cleanAndRecreateDatabase();
					MainCreateSalesOrderFrame frame = new MainCreateSalesOrderFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainCreateSalesOrderFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// we create a PackOrderController immediately to get the monitor running
		try {
			poc = new PackOrderController();
		} catch (DataAccessException e1) {
		
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		monitorPanel = new RemainingOrdersPanel(poc);
		contentPane.add(monitorPanel, BorderLayout.WEST);
		monitorPanel.setVisible(true);

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		try {
			soc = new SalesOrderController();
			mainPanel.add(new CreateSalesOrderPanel(soc), "CreateSalesOrderPanel");
			mainPanel.add(new AddCustomerToOrderPanel(soc), "AddCustomerToOrderPanel");
			mainPanel.add(new FindAndAddProductToOrderPanel(soc), "FindAndAddProductToOrderPanel");
		} catch (SQLException e) {

			e.printStackTrace();
		}
		cardLayout.show(mainPanel, "CreateSalesOrderPanel");

		topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 0, 0, 0));

		btnNewButton = new JButton("Some menu items");
		topPanel.add(btnNewButton);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JButton backButton = new JButton("Tilbage");
		panel.add(backButton);

		JButton continueButton = new JButton("Fortsæt");
		continueButton.addActionListener(e -> {
			saveAndContinueClicked();
		});
		panel.add(continueButton);

		// make sure we stop our monitor when closing the app
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (poc != null) {
					poc.stopMonitor();
				}
			}
		});
	}

	private void saveAndContinueClicked() {
		cardLayout.next(mainPanel);

	}

}
