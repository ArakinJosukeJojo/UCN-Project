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

public class MainPackOrderFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PackOrderController poc;
	private SalesOrderController soc;
	private JPanel mainPanel;
	private JPanel monitorPanel;
	private JPanel topPanel;
	private CardLayout cardLayout;
	private JButton btnNewButton;
	private ViewPackingListsPanel vplistspanel;
	private ViewPackingListPanel vplistpanel;
	private PackOrderPanel pop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBCleaner dbc = new DBCleaner();
					dbc.cleanAndRecreateDatabase();
					MainPackOrderFrame frame = new MainPackOrderFrame();
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
	public MainPackOrderFrame() {
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		monitorPanel = new RemainingOrdersPanel(poc);
		contentPane.add(monitorPanel, BorderLayout.WEST);
		monitorPanel.setVisible(true);

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		vplistspanel = new ViewPackingListsPanel(poc);
		vplistpanel = new ViewPackingListPanel(poc);
		pop = new PackOrderPanel(poc);
		mainPanel.add(vplistspanel, "ViewPackingListsPanel");
		mainPanel.add(vplistpanel, "ViewPackingListPanel");
		mainPanel.add(pop, "PackOrderPanel");
		cardLayout.show(mainPanel, "ViewPackingListsPanel");

		topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 0, 0, 0));

		btnNewButton = new JButton("Some menu item");
		topPanel.add(btnNewButton);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		JButton backButton = new JButton("Tilbage");
		bottomPanel.add(backButton);

		JButton nextButton = new JButton("Fortsæt");
		nextButton.addActionListener(e -> {
			nextButtonClicked();
		});
		bottomPanel.add(nextButton);

		contentPane.add(bottomPanel, BorderLayout.SOUTH);

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

	private void nextButtonClicked() {
		vplistpanel.refresh();
		if (poc.getSalesOrderController().getCurrentOrder() != null) {
			pop.refresh();
			System.out.print("Sales Order should show orderlines");
		}
		cardLayout.next(mainPanel);
	}

}
