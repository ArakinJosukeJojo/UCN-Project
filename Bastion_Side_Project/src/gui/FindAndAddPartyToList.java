package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import control.CharacterController;
import control.PartyController;
import databaselayer.DataAccessException;
import model.Characters;
import model.PartyLines;

public class FindAndAddPartyToList extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField textField_1; // Search field
	private JList<Characters> findCharacterList;
	private DefaultListModel<Characters> characterListModel = new DefaultListModel<>(); // List model for characters
	private DefaultListModel<PartyLines> partyListModel = new DefaultListModel<>();
	private CharacterController coc;
	private PartyController poc;
	private JList<PartyLines> partyList;
	private Characters c;
	private JTextPane textPane;

	/**
	 * Create the panel.
	 * 
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public FindAndAddPartyToList(PartyController poc, CharacterController coc)
			throws DataAccessException, SQLException {
		this.coc = coc; // Injected controller for database access
		this.poc = new PartyController();
		setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("New button");
		panel_2.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Add Characters");
		btnNewButton_1.addActionListener(e -> {
			Characters c = findCharacterList.getSelectedValue();
			if (c != null) {
				addCharacterClicked(c);
			}
		});
		panel_2.add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane();
		scrollPane.setColumnHeaderView(splitPane);

		JButton Search = new JButton("Search");
		Search.addActionListener(e -> searchCharacter());
		splitPane.setLeftComponent(Search);

		textField_1 = new JTextField();
		splitPane.setRightComponent(textField_1);
		textField_1.setColumns(10);

		characterListModel = new DefaultListModel<>();
		findCharacterList = new JList<>(characterListModel);
		scrollPane.setViewportView(findCharacterList);

		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.SOUTH);

		JButton btnNewButton_2 = new JButton("Remove characters");
		btnNewButton_2.addActionListener(e -> {
			PartyLines selectedPartyLine = partyList.getSelectedValue();
			if (selectedPartyLine != null) {
				removeCharacter(selectedPartyLine);
			}
		});
		panel_3.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Save party");
		panel_3.add(btnNewButton_3);

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);

		partyListModel = new DefaultListModel<>();
		partyList = new JList<>();
		scrollPane_1.setViewportView(partyList);

		JPanel panel_4 = new JPanel();
		scrollPane_1.setColumnHeaderView(panel_4);

		JLabel lblNewLabel = new JLabel("Party");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblNewLabel);

		textPane = new JTextPane();
		textPane.setText("0/4");
		panel_4.add(textPane);
	}

	private void searchCharacter() {
		String name = textField_1.getText();
		if (name.isEmpty()) {
			// If the input field is empty, do nothing or show a warning
			return;
		}
		try {
			List<Characters> characters = coc.findCharacterByName(name);
			if (characters != null && !characters.isEmpty()) {
				displayCharacter(characters);
			} else {
				clearCharacterList();
				System.out.println("Character not found");
			}
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void displayCharacter(List<Characters> characters) {
		characterListModel.clear(); // Clear existing items in the list
		for (Characters character : characters) {
			characterListModel.addElement(character); // Add each character to the list model
		}
	}

	private void clearCharacterList() {
		characterListModel.clear(); // Clear all items from the list model
	}

	private void addCharacterClicked(Characters c) {
		List<PartyLines> partyLines = poc.getCurrentParty().getPartyLine();
		boolean isAlreadyInParty = partyLines.stream().anyMatch(partyLine -> partyLine.getCharacter().equals(c));
		if (isAlreadyInParty) {
			System.out.println("Character is already on the list!");
			return;
		} else if (partyLines.size() == 4) {
			System.out.println("The party is full");
			return;
		}

		poc.addCharacterToList(c);
		updatePartyList(partyLines);

		updateCharacterCounter(partyLines);
	}

	private void updateCharacterCounter(List<PartyLines> partyline) {
//		int currentCount = poc.getCurrentParty().getPartyLine().size(); // Get the number of characters in the party
//		String text = "Characters in Party: " + currentCount;

		// Update the JTextPane with the new counter
		int counter = 0;
		for (PartyLines partylines : partyline) {
			if (counter >= 4) {
				System.out.println("Full sized party.");
			} else {
				counter++;
			}
		}
		String text = Integer.toString(counter);
		textPane.setText(text + "/4");

	}

	private void updatePartyList(List<PartyLines> partyline) {
		partyListModel.clear(); // Clear the existing party list model
//		partyListModel.addAll(poc.getCurrentParty().getCharacterList()); // Get updated party list from the
//		partyList.setModel(partyListModel);
		for (PartyLines partylines : partyline) {
			partyListModel.addElement(partylines);
		}
		partyList.setModel(partyListModel);
	}

	private void removeCharacter(PartyLines partyLine) {
		// Remove the PartyLines object from the partyListModel
		partyListModel.removeElement(partyLine);

		// Remove the corresponding character from the party in the controller
		poc.removeCharacterFromParty(partyLine.getCharacter());

		// Update the party list display
		updatePartyList(poc.getCurrentParty().getPartyLine());

		// Update the character counter
		updateCharacterCounter(poc.getCurrentParty().getPartyLine());
	}

}
