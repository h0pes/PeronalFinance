import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePickerImpl;

import net.proteanit.sql.DbUtils;

/**
 * This is a class to open a second form used to load and show the transactions
 * records from the database within a JTable. It also allows to insert, update
 * and delete records in the database table tbltransazioni
 * 
 * @version: v.1.0 - 15 dic 2015 17:44:18
 * @author: Marco Canavese
 */

public class ExpensesMenu extends JFrame
{
	private JPanel mcontentPane;
	private JTable mtable;
	private JComboBox comboBoxSpeseEntrate;
	private List<CategoryReason> categoriesReasons;
	private JComboBox comboBoxCategorieCausali;
	private JDatePickerImpl datePicker;
	private JComboBox comboBoxSearch;

    /**
     * Launch the application.
     *
     * @param args
     */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
                        @Override
			public void run()
			{
				try
				{
					ExpensesMenu frame = new ExpensesMenu();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTextField mtextTransactionDesc;
	private JTextField mtextCategoryID;
	private JTextField mtextReasonID;
	// private JTextField mtextField;
	private JTextField mtextAmount;
	private JTextField mtextTransactionID;
	private JTextArea textAreaNotes;
	private JTextField mtextUpdateDate;
	private JTextField mtextSearch;

	/**
	 * It refreshes the table data as if the user had clicked on the Load data
	 * button
	 */
	public void refreshData()
	{
		try
		{
			// build the query to return all records from tbltransazioni
			// ordered by date
			String query = ""
					+ "SELECT tblTransazioni.ID_Transazione, tblCategorie.DescrizioneCategoria, tblCategorie.Entrata_Spesa, tblCausali.DescrizioneCausale, tblCausali.ID_Categoria, tblTransazioni.ID_Causale, tblTransazioni.DescrizioneTransazione, tblTransazioni.Data, tblTransazioni.Ammontare, tblTransazioni.Note "
					+ "FROM (tblCausali INNER JOIN tblCategorie ON tblCausali.ID_Categoria = tblCategorie.ID_Categoria) INNER JOIN tblTransazioni ON tblCausali.ID_Causale = tblTransazioni.ID_Causale "
					+ "ORDER BY tblTransazioni.Data DESC";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			mtable.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e, "Error loading data", 0);
		}
	}

	/**
	 * This method will populate the entry combo box with expenses or gain
	 * values
	 * 
	 */
	public void fillComboBox()
	{
		try
		{
			String query = "SELECT DISTINCT Entrata_Spesa FROM tblCategorie";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next())
			{
				comboBoxSpeseEntrate.addItem(rs.getString(1));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This is the constructor for the class and it allows to create the frame
	 * and add objects inside it. Various action performed methods are enclosed
	 * within it
	 * 
	 * @throws Exception
	 */
	public ExpensesMenu() throws Exception
	{
		connection = DBConnection.getConnection();
		setTitle("Entrate e Spese - Main menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1078, 644);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mcontentPane = new JPanel();
		mcontentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mcontentPane);
		mcontentPane.setLayout(null);

		JButton btnLoadData = new JButton("Load data");
		btnLoadData.setBackground(Color.ORANGE);
		btnLoadData.addActionListener(new ActionListener()
		{
			/**
			 * Load the table displayed on the form as the user click the load
			 * data button with all the records from the table tbltransazioni.
			 * 
			 * @param arg0
			 *            the ActionEvent type, in this case a mouse click
			 * 
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
                        @Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					// build the query to return all records from tbltransazioni
					// ordered by date
					String query = ""
							+ "SELECT tblTransazioni.ID_Transazione, tblCategorie.DescrizioneCategoria, tblCategorie.Entrata_Spesa, tblCausali.DescrizioneCausale, tblCausali.ID_Categoria, tblTransazioni.ID_Causale, tblTransazioni.DescrizioneTransazione, tblTransazioni.Data, tblTransazioni.Ammontare, tblTransazioni.Note "
							+ "FROM (tblCausali INNER JOIN tblCategorie ON tblCausali.ID_Categoria = tblCategorie.ID_Categoria) INNER JOIN tblTransazioni ON tblCausali.ID_Causale = tblTransazioni.ID_Causale "
							+ "ORDER BY tblTransazioni.Data DESC";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					mtable.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, e, "Error loading data",
							0);
				}
			}
		});
		btnLoadData.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLoadData.setBounds(343, 157, 101, 23);
		mcontentPane.add(btnLoadData);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 215, 1042, 379);
		mcontentPane.add(scrollPane);

		mtable = new JTable();
		mtable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				Color updateDateColor = Color.YELLOW;
				mtextUpdateDate.enable(true);
				mtextUpdateDate.setBackground(updateDateColor);
				mtextCategoryID.setText("");
				try
				{
					int row = mtable.getSelectedRow();
					int transactionID = Integer.parseInt(
							(mtable.getModel().getValueAt(row, 0)).toString());

					String query = "SELECT tblTransazioni.ID_Transazione, tblTransazioni.DescrizioneTransazione, tblTransazioni.Data, tblTransazioni.Ammontare, "
							+ "tblTransazioni.Note, tblTransazioni.ID_Causale "
							+ "FROM tblTransazioni WHERE tblTransazioni.ID_Transazione = '"
							+ transactionID + "' ";
					PreparedStatement pst = connection.prepareStatement(query);

					ResultSet rs = pst.executeQuery();

					while (rs.next())
					{
						mtextTransactionID
								.setText(rs.getString("ID_Transazione"));
						mtextTransactionDesc.setText(
								rs.getString("DescrizioneTransazione"));
						mtextAmount.setText(rs.getString("Ammontare"));
						mtextReasonID.setText(rs.getString("ID_Causale"));
						textAreaNotes.setText(rs.getString("Note"));
						mtextUpdateDate.setText(rs.getString("Data"));

						/*
						 * Commented out because not able to set date to
						 * datepicker String year =
						 * rs.getString("Data").substring(0, 4); String month =
						 * rs.getString("Data").substring(5, 7); String day =
						 * rs.getString("Data").substring(8);
						 * datePicker.getModel().setDate(Integer.parseInt(year),
						 * Integer.parseInt(month), Integer.parseInt(day));
						 */
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});
		scrollPane.setViewportView(mtable);

		comboBoxSpeseEntrate = new JComboBox();
		comboBoxSpeseEntrate.setBounds(88, 24, 89, 23);
		mcontentPane.add(comboBoxSpeseEntrate);

		// define ArrayList of Categories and Reasons to store those objects
		categoriesReasons = createCategoryReasonList();

		// define comboBoxCategorieCausali which will be populated with the
		// ArrayList
		comboBoxCategorieCausali = createComboBox(categoriesReasons);

		comboBoxCategorieCausali.setBounds(471, 24, 350, 22);
		mcontentPane.add(comboBoxCategorieCausali);

		JLabel lblExpenseGain = new JLabel("Expense/Gain");
		lblExpenseGain.setBounds(10, 27, 89, 14);
		mcontentPane.add(lblExpenseGain);

		JLabel lblCategoryReason = new JLabel("Category/Reason");
		lblCategoryReason.setBounds(348, 28, 113, 14);
		mcontentPane.add(lblCategoryReason);

		JLabel lblTransactionDesc = new JLabel("Transaction desc.");
		lblTransactionDesc.setBounds(348, 58, 113, 14);
		mcontentPane.add(lblTransactionDesc);

		mtextTransactionDesc = new JTextField();
		mtextTransactionDesc.setBounds(471, 55, 350, 20);
		mcontentPane.add(mtextTransactionDesc);
		mtextTransactionDesc.setColumns(10);

		JLabel lblNotes = new JLabel("Notes");
		lblNotes.setBounds(348, 105, 58, 14);
		mcontentPane.add(lblNotes);

		textAreaNotes = new JTextArea();
		textAreaNotes.setBounds(471, 100, 350, 104);
		mcontentPane.add(textAreaNotes);

		JLabel lblCategoryID = new JLabel("CatID");
		lblCategoryID.setBounds(855, 28, 46, 14);
		mcontentPane.add(lblCategoryID);

		JLabel lblReasonID = new JLabel("ReasonID");
		lblReasonID.setBounds(960, 30, 59, 14);
		mcontentPane.add(lblReasonID);

		mtextCategoryID = new JTextField();
		mtextCategoryID.setBounds(901, 25, 33, 20);
		mcontentPane.add(mtextCategoryID);
		mtextCategoryID.setColumns(10);

		mtextReasonID = new JTextField();
		mtextReasonID.setBounds(1019, 27, 33, 20);
		mcontentPane.add(mtextReasonID);
		mtextReasonID.setColumns(10);

		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(10, 58, 46, 14);
		mcontentPane.add(lblDate);

		JDatePicker myDatePicker = new JDatePicker();
		datePicker = myDatePicker.getDatePicker();
		datePicker.setBounds(88, 55, 178, 28);
		mcontentPane.add(datePicker);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener()
		{
                        @Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					String query = "UPDATE tbltransazioni SET ID_Transazione = '"
							+ mtextTransactionID.getText() + "', "
							+ "DescrizioneTransazione = '"
							+ mtextTransactionDesc.getText() + "', "
							+ "Data = '" + mtextUpdateDate.getText() + "', "
							+ "Ammontare = '" + mtextAmount.getText() + "', "
							+ "Note = '" + textAreaNotes.getText() + "', "
							+ "ID_Causale = '" + mtextReasonID.getText() + "'"
							+ "WHERE ID_Transazione = '"
							+ mtextTransactionID.getText() + "'";

					PreparedStatement pst = connection.prepareStatement(query);

					pst.execute();
					JOptionPane.showMessageDialog(null, "Record updated");

					pst.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				refreshData();
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEdit.setToolTipText("Edit the selected record...");
		btnEdit.setBackground(Color.YELLOW);
		btnEdit.setBounds(109, 157, 89, 23);
		mcontentPane.add(btnEdit);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener()
		{
                        @Override
			public void actionPerformed(ActionEvent e)
			{
				Calendar selectedDate = (Calendar) datePicker.getModel()
						.getValue();
				SimpleDateFormat formatDate = new SimpleDateFormat(
						"yyyy-MM-dd");
				try
				{
					String query = "INSERT INTO tblTransazioni (DescrizioneTransazione,Data,Ammontare,Note,ID_Causale) "
							+ "VALUES (?, ?, ?, ?, ?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, mtextTransactionDesc.getText());
					pst.setString(2, formatDate.format(selectedDate.getTime()));
					pst.setString(3, mtextAmount.getText());
					pst.setString(4, textAreaNotes.getText());
					pst.setString(5, mtextReasonID.getText());

					pst.execute();

					JOptionPane.showMessageDialog(null,
							"Record successfully inserted");
					pst.close();

				} catch (Exception exc)
				{
					exc.printStackTrace();
				}
				refreshData();
			}
		});
		btnAdd.setToolTipText("Add a new record...");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdd.setBackground(Color.GREEN);
		btnAdd.setBounds(10, 157, 89, 23);
		mcontentPane.add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int deleteDecision = JOptionPane.showConfirmDialog(null,
						"Do you really want to delete this record?", "Delete",
						JOptionPane.YES_NO_OPTION);
				if (deleteDecision == 0)
				{
					try
					{
						String query = "DELETE FROM tbltransazioni WHERE ID_Transazione = '"
								+ mtextTransactionID.getText() + "'";
						PreparedStatement pst = connection
								.prepareStatement(query);
						pst.execute();

						JOptionPane.showMessageDialog(null, "Record deleted");
						pst.close();
						mtextAmount.setText("");
						mtextTransactionDesc.setText("");
						mtextReasonID.setText("");
						textAreaNotes.setText("");
						mtextTransactionID.setText("");
						mtextUpdateDate.setText("");
					} catch (Exception e)
					{
						e.printStackTrace();
					}

				} else
				{
					JOptionPane.showMessageDialog(null, "Deletion cancelled");
				}
				refreshData();
			}
		});
		btnDelete.setToolTipText("Delete the selected record...");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBackground(Color.RED);
		btnDelete.setBounds(208, 157, 89, 23);
		mcontentPane.add(btnDelete);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(10, 105, 59, 14);
		mcontentPane.add(lblAmount);

		mtextAmount = new JTextField();
		mtextAmount.setBounds(88, 102, 86, 20);
		mcontentPane.add(mtextAmount);
		mtextAmount.setColumns(10);

		JLabel lblTransactionID = new JLabel("TransacID");
		lblTransactionID.setBounds(842, 58, 59, 14);
		mcontentPane.add(lblTransactionID);

		mtextTransactionID = new JTextField();
		mtextTransactionID.setBounds(901, 55, 33, 20);
		mcontentPane.add(mtextTransactionID);
		mtextTransactionID.setColumns(10);

		JLabel lblUpdateDate = new JLabel("Update Date");
		lblUpdateDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUpdateDate.setBounds(855, 105, 86, 14);
		mcontentPane.add(lblUpdateDate);

		mtextUpdateDate = new JTextField();
		mtextUpdateDate.setToolTipText(
				"Use this field when updating an existing record");
		mtextUpdateDate.setBounds(951, 102, 101, 20);
		mcontentPane.add(mtextUpdateDate);
		mtextUpdateDate.setColumns(10);

		mtextSearch = new JTextField();
		mtextSearch.setToolTipText(
				"Search for the specified word within Transaction description or Transaction note or select a field in the combobox to search for...");
		mtextSearch.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent arg0)
			{
				String searchSelection = null;
				String query = null;
				try
				{
					searchSelection = (String) comboBoxSearch.getSelectedItem();
					if (searchSelection.equals(""))
					{
						query = "SELECT tblTransazioni.ID_Transazione, tblCategorie.DescrizioneCategoria, tblCategorie.Entrata_Spesa, tblCausali.DescrizioneCausale, tblCausali.ID_Categoria, tblTransazioni.ID_Causale, tblTransazioni.DescrizioneTransazione, tblTransazioni.Data, tblTransazioni.Ammontare, tblTransazioni.Note "
								+ "FROM (tblCausali INNER JOIN tblCategorie ON tblCausali.ID_Categoria = tblCategorie.ID_Categoria) INNER JOIN tblTransazioni ON tblCausali.ID_Causale = tblTransazioni.ID_Causale "
								+ "WHERE tblTransazioni.DescrizioneTransazione like '%"
								+ mtextSearch.getText()
								+ "%' OR tblTransazioni.Note like '%"
								+ mtextSearch.getText() + "%' "
								+ "ORDER BY tblTransazioni.Data DESC";
						System.out.println(query);
					} else
					{
						query = "SELECT tblTransazioni.ID_Transazione, tblCategorie.DescrizioneCategoria, tblCategorie.Entrata_Spesa, tblCausali.DescrizioneCausale, tblCausali.ID_Categoria, tblTransazioni.ID_Causale, tblTransazioni.DescrizioneTransazione, tblTransazioni.Data, tblTransazioni.Ammontare, tblTransazioni.Note "
								+ "FROM (tblCausali INNER JOIN tblCategorie ON tblCausali.ID_Categoria = tblCategorie.ID_Categoria) INNER JOIN tblTransazioni ON tblCausali.ID_Causale = tblTransazioni.ID_Causale "
								+ "WHERE " + searchSelection + " like '%"
								+ mtextSearch.getText() + "%' "
								+ "ORDER BY tblTransazioni.Data DESC";
						System.out.println(query);
					}

					PreparedStatement pst = connection.prepareStatement(query);
					// pst.setString(1, mtextSearch.getText());
					// pst.setString(2, mtextSearch.getText());

					ResultSet rs = pst.executeQuery();

					mtable.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, e,
							"Error searching requested data", 0);
				}
			}
		});
		mtextSearch.setBounds(157, 191, 287, 20);
		mcontentPane.add(mtextSearch);
		mtextSearch.setColumns(10);

		comboBoxSearch = new JComboBox();
		comboBoxSearch.setModel(new DefaultComboBoxModel(
				new String[] { "", "ID_Transazione", "DescrizioneCategoria",
						"Entrata_Spesa", "DescrizioneCausale", "ID_Categoria",
						"ID_Causale", "Ammontare", "Data" }));
		comboBoxSearch.setBounds(10, 191, 137, 20);
		mcontentPane.add(comboBoxSearch);
		mtextUpdateDate.enable(false);

		datePicker.setVisible(true);

		datePicker.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// if (mtextUpdateDate.getText().length() == 0)
				// {
				Calendar selectedDate = (Calendar) datePicker.getModel()
						.getValue();
				SimpleDateFormat formatDate = new SimpleDateFormat(
						"yyyy-MM-dd");
				System.out.println(formatDate.format(selectedDate.getTime()));
				/*
				 * } else { SimpleDateFormat formatDate = new SimpleDateFormat(
				 * "yyyy-MM-dd"); Date date = null; try { date =
				 * formatDate.parse(mtextUpdateDate.getText()); } catch
				 * (ParseException prsExc) { // TODO Auto-generated catch block
				 * prsExc.printStackTrace(); } Calendar selectedDate =
				 * Calendar.getInstance(); selectedDate.setTime(date); }
				 */
			}
		});

		// populate the comboBoxSpeseEntrate with Entrate/Spese distinct values
		fillComboBox();
		// refresh table data
		refreshData();
	}

	/**
	 * Create a JComboBox object populated with custom objects of type
	 * CategoryReason from a List.
	 * 
	 * @param categoriesReasons
	 *            an ArrayList of Categories and Reasons used to populate the
	 *            combobox
	 * @return return a JComboBox object populated with CategoryReason objects
	 */
	private JComboBox createComboBox(List<CategoryReason> categoriesReasons)
	{
		final JComboBox comboBox = new JComboBox(categoriesReasons.toArray());
		comboBox.addActionListener(new ActionListener()
		{
                        @Override
			public void actionPerformed(ActionEvent e)
			{
				mtextAmount.setText("");
				mtextCategoryID.setText("");
				mtextReasonID.setText("");
				mtextTransactionDesc.setText("");
				textAreaNotes.setText("");
				mtextTransactionID.setText("");
				mtextUpdateDate.setText("");

				try
				{
					String query = "SELECT tblCategorie.DescrizioneCategoria, tblCausali.DescrizioneCausale, tblCategorie.ID_Categoria, tblCausali.ID_Causale "
							+ "FROM tblCategorie INNER JOIN tblCausali ON tblCategorie.ID_Categoria = tblCausali.ID_Categoria "
							+ "WHERE tblCategorie.DescrizioneCategoria = ? AND tblCausali.DescrizioneCausale = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					CategoryReason categoryReason = (CategoryReason) comboBox
							.getSelectedItem();
					pst.setString(1, categoryReason.getCategoryDescription());
					pst.setString(2, categoryReason.getReasonDescription());

					ResultSet rs = pst.executeQuery();

					while (rs.next())
					{
						mtextCategoryID.setText(rs.getString("ID_Categoria"));
						mtextReasonID.setText(rs.getString("ID_Causale"));
					}
					pst.close();

				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		comboBox.setRenderer(new ComboBoxRenderer());
		comboBox.addItemListener(new ItemListener()
		{
			/**
			 * Get the status of the item within the combobox object
			 * 
			 * @param e
			 *            the ItemEvent
			 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
			 */
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					CategoryReason categoryReason = (CategoryReason) comboBox
							.getSelectedItem();
					System.out.println(categoryReason.getCategoryDescription()
							+ ":" + categoryReason.getCategoryID() + "-"
							+ categoryReason.getReasonDescription() + ":"
							+ categoryReason.getReasonID());
				}
			}
		});
		return comboBox;
	}

	/**
	 * This class is used to build custom renderer for the combobox object in
	 * order to let us have multiple columns shown inside the control
	 */
	private class ComboBoxRenderer extends DefaultListCellRenderer
	{
		/**
		 * Builds the content we want to be shown inside the combobox object.
		 * 
		 * @param list
		 *            the list
		 * @param value
		 *            the value of the CategoryReason object
		 * @param index
		 *            the index of the item
		 * @param isSelected
		 *            whether the item is selected or not
		 * @param cellHasFocus
		 *            whether the cell has focus or not
		 * @return a label (Component object) with the desired content we want
		 *         to show within the combobox
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList,
		 *      java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus)
		{
			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);
			CategoryReason categoryReason = (CategoryReason) value;
			label.setText(categoryReason.getCategoryDescription() + " | "
					+ categoryReason.getReasonDescription());
			return label;
		}
	}

	/**
	 * Create an ArrayList of Categories and Reasons objects
	 * 
	 * @return a List of Categories and Reasons objects to be used to fill the
	 *         combobox control
	 */
	private List<CategoryReason> createCategoryReasonList()
	{
		List<CategoryReason> list = new ArrayList<>();
		list.add(new CategoryReason("Abbigliamento", "Vestiti Lara", 5, 141));
		list.add(new CategoryReason("Abbigliamento", "Vestiti Marco", 5, 20));
		list.add(
				new CategoryReason("Abbigliamento", "Vestiti Patrizia", 5, 21));
		list.add(new CategoryReason("Altre entrate", "Altre entrate", 26, 112));
		list.add(new CategoryReason("Assicurazione", "ENPAM", 19, 124));
		list.add(new CategoryReason("Assicurazione",
				"Polizza professionale Patrizia", 19, 103));
		list.add(new CategoryReason("Automobile", "Altro", 1, 13));
		list.add(new CategoryReason("Automobile", "Assicurazione A3", 1, 126));
		list.add(new CategoryReason("Automobile", "Assicurazione Panda", 1, 6));
		list.add(new CategoryReason("Automobile", "Assicurazione Punto", 1, 5));
		list.add(
				new CategoryReason("Automobile", "Assicurazione Toyota", 1, 4));
		list.add(new CategoryReason("Automobile", "Bollo A3", 1, 125));
		list.add(new CategoryReason("Automobile", "Bollo Panda", 1, 3));
		list.add(new CategoryReason("Automobile", "Bollo Punto", 1, 2));
		list.add(new CategoryReason("Automobile", "Bollo Toyota", 1, 1));
		list.add(new CategoryReason("Automobile", "Gasolio A3", 1, 128));
		list.add(new CategoryReason("Automobile", "Gasolio Panda", 1, 9));
		list.add(new CategoryReason("Automobile", "Gasolio Punto", 1, 8));
		list.add(new CategoryReason("Automobile", "Gasolio Toyota", 1, 7));
		list.add(new CategoryReason("Automobile", "Manutenzione A3", 1, 134));
		list.add(new CategoryReason("Automobile", "Manutenzione Panda", 1, 12));
		list.add(new CategoryReason("Automobile", "Manutenzione Punto", 1, 11));
		list.add(
				new CategoryReason("Automobile", "Manutenzione Toyota", 1, 10));
		list.add(new CategoryReason("Automobile", "Telepass", 1, 114));
		list.add(new CategoryReason("Bellezza", "Centro estetico", 29, 121));
		list.add(new CategoryReason("Bellezza", "Cosmesi", 29, 127));
		list.add(new CategoryReason("Bellezza", "Parrucchiere Marco", 29, 119));
		list.add(new CategoryReason("Bellezza", "Pettinatrice Patrizia", 29,
				120));
		list.add(new CategoryReason("Benessere", "Palestra", 31, 131));
		list.add(new CategoryReason("Carta di credito", "Carta di credito", 12,
				43));
		list.add(new CategoryReason("Casa Bacciana", "Abbellimento", 15, 64));
		list.add(new CategoryReason("Casa Bacciana", "Altre spese", 15, 67));
		list.add(new CategoryReason("Casa Bacciana", "Assicurazione", 15, 66));
		list.add(new CategoryReason("Casa Bacciana", "Gas", 15, 68));
		list.add(new CategoryReason("Casa Bacciana", "Giardinaggio", 15, 65));
		list.add(new CategoryReason("Casa Bacciana", "IMU", 15, 150));
		list.add(new CategoryReason("Casa Bacciana", "Luce", 15, 69));
		list.add(new CategoryReason("Casa Bacciana", "Manutenzione personale",
				15, 63));
		list.add(new CategoryReason("Casa Bacciana",
				"Spese gestione condominio", 15, 59));
		list.add(new CategoryReason("Casa Bacciana", "Spese riscaldamento", 15,
				60));
		list.add(new CategoryReason("Casa Bacciana", "Tassa rifiuti", 15, 62));
		list.add(new CategoryReason("Casa Bacciana", "Voce e dati", 15, 70));
		list.add(new CategoryReason("Casa Cisterna", "Abbellimento", 18, 97));
		list.add(new CategoryReason("Casa Cisterna", "Altre spese", 18, 99));
		list.add(new CategoryReason("Casa Cisterna", "Assicurazione", 18, 98));
		list.add(new CategoryReason("Casa Cisterna", "Gas", 18, 100));
		list.add(new CategoryReason("Casa Cisterna", "Luce", 18, 101));
		list.add(new CategoryReason("Casa Cisterna", "Manutenzione personale",
				18, 96));
		list.add(new CategoryReason("Casa Cisterna", "Spese riscaldamento", 18,
				93));
		list.add(new CategoryReason("Casa Cisterna", "Spese straordinarie", 18,
				94));
		list.add(new CategoryReason("Casa Cisterna", "Tassa rifiuti", 18, 95));
		list.add(new CategoryReason("Casa Cisterna", "Voce e dati", 18, 102));
		list.add(new CategoryReason("Casa Montagna", "Abbellimento", 16, 76));
		list.add(new CategoryReason("Casa Montagna", "Altre spese", 16, 78));
		list.add(new CategoryReason("Casa Montagna", "Assicurazione", 16, 77));
		list.add(new CategoryReason("Casa Montagna", "Gas", 16, 79));
		list.add(new CategoryReason("Casa Montagna", "IMU", 16, 151));
		list.add(new CategoryReason("Casa Montagna", "Luce", 16, 80));
		list.add(new CategoryReason("Casa Montagna", "Manutenzione personale",
				16, 75));
		list.add(new CategoryReason("Casa Montagna",
				"Ristrutturazione edilizia", 16, 164));
		list.add(new CategoryReason("Casa Montagna", "Spese gestione", 16, 71));
		list.add(new CategoryReason("Casa Montagna", "Spese riscaldamento", 16,
				72));
		list.add(new CategoryReason("Casa Montagna", "Spese straordinarie", 16,
				73));
		list.add(new CategoryReason("Casa Montagna", "Tassa rifiuti", 16, 74));
		list.add(new CategoryReason("Casa Montagna", "Voce e dati", 16, 81));
		list.add(new CategoryReason("Casa Rapallo", "Abbellimento", 17, 87));
		list.add(new CategoryReason("Casa Rapallo", "Altre spese", 17, 89));
		list.add(new CategoryReason("Casa Rapallo", "Assicurazione", 17, 88));
		list.add(new CategoryReason("Casa Rapallo", "Gas", 17, 90));
		list.add(new CategoryReason("Casa Rapallo", "IMU", 17, 152));
		list.add(new CategoryReason("Casa Rapallo", "Luce", 17, 91));
		list.add(new CategoryReason("Casa Rapallo", "Manutenzione personale",
				17, 86));
		list.add(new CategoryReason("Casa Rapallo", "Spese gestione condominio",
				17, 82));
		list.add(new CategoryReason("Casa Rapallo", "Spese riscaldamento", 17,
				83));
		list.add(new CategoryReason("Casa Rapallo", "Spese straordinarie", 17,
				84));
		list.add(new CategoryReason("Casa Rapallo", "Tassa rifiuti", 17, 85));
		list.add(new CategoryReason("Casa Rapallo", "Voce e dati", 17, 92));
		list.add(new CategoryReason("Casa Torino", "Abbellimento", 14, 52));
		list.add(new CategoryReason("Casa Torino", "Affitto garage La Marca",
				14, 117));
		list.add(new CategoryReason("Casa Torino", "Affitto garage Zavaroni",
				14, 153));
		list.add(new CategoryReason("Casa Torino", "Altre spese", 14, 54));
		list.add(new CategoryReason("Casa Torino", "Altre utenze", 14, 58));
		list.add(new CategoryReason("Casa Torino", "Assicurazione", 14, 53));
		list.add(new CategoryReason("Casa Torino", "Canone RAI", 14, 123));
		list.add(new CategoryReason("Casa Torino", "Gas", 14, 55));
		list.add(new CategoryReason("Casa Torino", "IMU", 14, 129));
		list.add(new CategoryReason("Casa Torino", "Luce", 14, 56));
		list.add(new CategoryReason("Casa Torino", "Manutenzione personale", 14,
				50));
		list.add(new CategoryReason("Casa Torino", "Pulizie", 14, 51));
		list.add(new CategoryReason("Casa Torino", "Ristrutturazione edilizia",
				14, 163));
		list.add(new CategoryReason("Casa Torino", "Spese gestione condominio",
				14, 46));
		list.add(new CategoryReason("Casa Torino", "Spese riscaldamento", 14,
				47));
		list.add(new CategoryReason("Casa Torino", "Spese straordinarie", 14,
				48));
		list.add(new CategoryReason("Casa Torino", "Tassa rifiuti", 14, 49));
		list.add(new CategoryReason("Casa Torino", "Voce e dati", 14, 57));
		list.add(new CategoryReason("Casa via Arduino", "Assicurazione", 33,
				147));
		list.add(new CategoryReason("Casa via Arduino", "IMU", 33, 145));
		list.add(new CategoryReason("Casa via Arduino",
				"Spese gestione condominio", 33, 138));
		list.add(new CategoryReason("Casa via Arduino", "Spese straordnarie",
				33, 139));
		list.add(new CategoryReason("Cultura", "Musei", 32, 133));
		list.add(new CategoryReason("Divertimenti", "Altro", 6, 23));
		list.add(new CategoryReason("Divertimenti", "Eventi sportivi", 6, 132));
		list.add(new CategoryReason("Divertimenti", "Serate", 6, 22));
		list.add(new CategoryReason("Donazioni", "Altro", 4, 19));
		list.add(new CategoryReason("Donazioni", "Donazioni", 4, 18));
		list.add(new CategoryReason("Educazione", "Agopuntura", 13, 140));
		list.add(new CategoryReason("Educazione", "Asilo", 13, 44));
		list.add(new CategoryReason("Educazione", "Babysitter", 13, 148));
		list.add(new CategoryReason("Educazione", "Convegni", 13, 159));
		list.add(new CategoryReason("Educazione", "Corsi", 13, 158));
		list.add(new CategoryReason("Educazione", "Libri", 13, 45));
		list.add(new CategoryReason("Educazione", "Scuola materna", 13, 149));
		list.add(new CategoryReason("Educazione", "Scuola primaria", 13, 165));
		list.add(new CategoryReason("Guadagno da Investimento",
				"Guadagno da Investimento", 27, 113));
		list.add(new CategoryReason("Hobbies", "Altro", 9, 37));
		list.add(new CategoryReason("Hobbies", "Bici", 9, 136));
		list.add(new CategoryReason("Hobbies", "Computer", 9, 35));
		list.add(new CategoryReason("Hobbies", "Nuoto", 9, 154));
		list.add(new CategoryReason("Hobbies", "Sci", 9, 36));
		list.add(new CategoryReason("Investimenti", "Azioni", 11, 40));
		list.add(new CategoryReason("Investimenti", "Fondi", 11, 39));
		list.add(new CategoryReason("Investimenti", "Obbligazioni", 11, 41));
		list.add(new CategoryReason("Investimenti", "Valuta", 11, 42));
		list.add(new CategoryReason("Mutuo", "Rata mutuo", 20, 104));
		list.add(new CategoryReason("Prelievo", "Prelievo", 10, 38));
		list.add(new CategoryReason("Regali", "Regali", 3, 17));
		list.add(new CategoryReason("Rimborso Spese sanitarie",
				"Rimborso spese Fondo Sanitario", 34, 146));
		list.add(new CategoryReason("Ristorante", "Altro", 2, 16));
		list.add(new CategoryReason("Ristorante", "Pizzeria", 2, 15));
		list.add(new CategoryReason("Ristorante", "Ristorante", 2, 14));
		list.add(new CategoryReason("Salute", "Dentista Lara", 8, 34));
		list.add(new CategoryReason("Salute", "Dentista Marco", 8, 32));
		list.add(new CategoryReason("Salute", "Dentista Patrizia", 8, 33));
		list.add(new CategoryReason("Salute", "Farmaci", 8, 135));
		list.add(new CategoryReason("Salute", "Prestazioni specialistiche Lara",
				8, 31));
		list.add(new CategoryReason("Salute",
				"Prestazioni specialistiche Marco", 8, 29));
		list.add(new CategoryReason("Salute",
				"Prestazioni specialistiche Patrizia", 8, 30));
		list.add(new CategoryReason("Salute", "Visite mediche Lara", 8, 28));
		list.add(new CategoryReason("Salute", "Visite mediche Marco", 8, 26));
		list.add(
				new CategoryReason("Salute", "Visite mediche Patrizia", 8, 27));
		list.add(new CategoryReason("Servizi/Membership",
				"Onorario commercialista", 23, 130));
		list.add(new CategoryReason("Servizi/Membership", "Ordine medici", 23,
				118));
		list.add(new CategoryReason("Servizi/Membership", "Quota associativa",
				23, 107));
		list.add(new CategoryReason("Servizi/Membership",
				"Quota visite per Studio/iDoctors", 23, 157));
		list.add(new CategoryReason("Smobilizzi", "Vendita azioni", 35, 160));
		list.add(new CategoryReason("Smobilizzi", "Vendita obbligazioni", 35,
				161));
		list.add(new CategoryReason("Smobilizzi", "Vendita quote fondi", 35,
				162));
		list.add(new CategoryReason("Spesa", "Altro", 7, 25));
		list.add(new CategoryReason("Spesa", "Iperbimbo", 7, 137));
		list.add(new CategoryReason("Spesa", "Supermercato", 7, 24));
		list.add(new CategoryReason("Stipendio", "Stipendio Marco", 24, 108));
		list.add(new CategoryReason("Stipendio", "Stipendio Patrizia altro", 24,
				110));
		list.add(new CategoryReason("Stipendio", "Stipendio Patrizia Candelo",
				24, 109));
		list.add(new CategoryReason("Tasse", "Tasse Patrizia", 22, 106));
		list.add(new CategoryReason("Trasporti", "Biglietti bus/metro", 30,
				122));
		list.add(new CategoryReason("Utenze", "Cellulare Marco", 28, 116));
		list.add(new CategoryReason("Utenze", "Cellulare Patrizia", 28, 115));
		list.add(new CategoryReason("Viaggi/Vacanze", "Spese di viaggio", 21,
				105));
		list.add(new CategoryReason("Visita specialistica", "Seduta agopuntura",
				25, 156));
		list.add(new CategoryReason("Visita specialistica", "Visita geriatrica",
				25, 155));
		list.add(new CategoryReason("Visita specialistica",
				"Visite specialistiche", 25, 111));
		return list;
	}

	/**
	 * This class defines objects of type CategoryReason used for the Expenses
	 * and Gain application
	 */
	public class CategoryReason
	{
		private String categoryDescription;
		private String reasonDescription;
		private int categoryID;
		private int reasonID;

		/**
		 * Construct an object of type CategoryReason
		 * 
		 * @param categoryDescription
		 *            the name of the Category
		 * @param reasonDescription
		 *            the name of the Reason
		 * @param categoryID
		 *            the id of the Category
		 * @param reasonID
		 *            the id of the Reason
		 */
		public CategoryReason(String categoryDescription,
				String reasonDescription, int categoryID, int reasonID)
		{
			this.categoryDescription = categoryDescription;
			this.reasonDescription = reasonDescription;
			this.categoryID = categoryID;
			this.reasonID = reasonID;
		}

		/**
		 * Used to get the name/description for a Category
		 * 
		 * @return a String representing the description/name for the Category
		 */
		public String getCategoryDescription()
		{
			return categoryDescription;
		}

		/**
		 * Used to get the name/description for a Reason
		 * 
		 * @return a String representing the description/name for the Reason
		 */
		public String getReasonDescription()
		{
			return reasonDescription;
		}

		/**
		 * Used to get the unique identifier for a Category
		 * 
		 * @return an int representing the unique identifier for the Category
		 */
		public int getCategoryID()
		{
			return categoryID;
		}

		/**
		 * Used to get the unique identifier for a Reason
		 * 
		 * @return an int representing the unique identifier for the Reason
		 */
		public int getReasonID()
		{
			return reasonID;
		}
	}
}
