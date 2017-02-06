import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class is used to present a Login form to the user
 * 
 * @version: v.1.1 - 13 dic 2015 19:27:51
 * @author: Marco Canavese
 */

public class Login
{
	private JFrame mframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Login window = new Login();
					window.mframe.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTextField mtextUsername;
	private JPasswordField mpasswordField;
	private JPasswordField mtextPassword;

	/**
	 * Create the application.
	 */
	public Login()
	{
		initialize();
		try
		{
			connection = DBConnection.getConnection();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		mframe = new JFrame();
		mframe.setTitle("Login");
		mframe.setBounds(100, 100, 500, 300);
		mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mframe.getContentPane().setLayout(null);

		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(126, 140, 88, 14);
		mframe.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(126, 172, 67, 14);
		mframe.getContentPane().add(lblPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					String query = "SELECT * FROM users WHERE user = ? AND password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, mtextUsername.getText());
					pst.setString(2,
							String.valueOf(mtextPassword.getPassword()));

					ResultSet rs = pst.executeQuery();
					int count = 0;
					while (rs.next())
					{
						count = count + 1;

					}

					if (count == 1)
					{
						JOptionPane.showMessageDialog(null,
								"Valid credentials");
						mframe.dispose();
						ExpensesMenu expensesMenu = new ExpensesMenu();
						expensesMenu.setVisible(true);
					} else if (count > 1)
					{
						JOptionPane.showMessageDialog(null,
								"Duplicate credentials");
					} else
					{
						JOptionPane.showMessageDialog(null,
								"Wrong username or password",
								"Credentials check", 2, null);
					}
					pst.close();
					rs.close();

				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnLogin.setBounds(213, 215, 89, 23);
		mframe.getContentPane().add(btnLogin);

		mtextUsername = new JTextField();
		mtextUsername.setBounds(185, 137, 147, 20);
		mframe.getContentPane().add(mtextUsername);
		mtextUsername.setColumns(10);

		mpasswordField = new JPasswordField();
		mpasswordField.setBounds(335, 169, -149, 20);
		mframe.getContentPane().add(mpasswordField);

		mtextPassword = new JPasswordField();
		mtextPassword.setBounds(185, 169, 147, 20);
		mframe.getContentPane().add(mtextPassword);

		JLabel label = new JLabel("");
		label.setBounds(20, 27, 46, 14);
		mframe.getContentPane().add(label);

		JLabel lblLoginImage = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/login.png"))
				.getImage();
		lblLoginImage.setIcon(new ImageIcon(img));
		lblLoginImage.setBounds(10, 11, 135, 118);
		mframe.getContentPane().add(lblLoginImage);
	}
}
