package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import control.CharacterController;
import control.PartyController;
import databaselayer.DataAccessException;

public class PartySelectionFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private CardLayout cardLayout;
	private PartyController poc;
	private CharacterController coc;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartySelectionFrame frame = new PartySelectionFrame();
					frame.setVisible(true);
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public PartySelectionFrame() throws DataAccessException, SQLException {
		setTitle("Party Selection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);

		// Use the content pane directly
		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		contentPane.add(mainPanel, BorderLayout.CENTER);

		coc = new CharacterController();
		poc = new PartyController(); // Initialize the controller
		mainPanel.add(new FindAndAddPartyToList(poc, coc), "FindAndAddPartyToList");

		// Display the desired panel
		cardLayout.show(mainPanel, "FindAndAddPartyToList");

		setVisible(true); // Set the frame visible at the end
	}
}
