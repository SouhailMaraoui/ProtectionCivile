package userInterface.user;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;

import Database.Utilisateur;
import scripts.GUI;
import userInterface.Login;

public class Home
{
	public static JPanel Home;
	static JSplitPane splitPane;

	public static JPanel Init()
	{
		Home = new JPanel();
		Home.add(GUI.NewLabel("Bienvenue " + Login.username, Color.white, 40, 190, 200));

		JButton BNext = GUI.NewButton("Insérer un incident","res/User/Home/InsertIcon.png", 25, 500, 50, 250, 350);
		Home.add(BNext);
		JButton BManage = GUI.NewButton("Gérer les incidents","res/User/Home/ManageIcon.png", 25, 500, 50, 250, 425);
		Home.add(BManage);

		JButton BDisconnect = GUI.NewButton("Déconnecter","res/User/Home/LogoutIcon.png", Color.black, 20, 400, 50, 300, 775);
		JButton BExit = GUI.NewButton("Quitter","res/User/Home/ExitIcon.png", Color.black, 20, 400, 50, 300, 825);
		JButton BReturn = GUI.NewButton("Revenir","res/User/Home/ReturnIcon.png", Color.black, 20, 400, 50, 300, 825);

		if (Utilisateur.getGID(Login.username) == 1)
		{
			Home.add(BDisconnect);
			Home.add(BExit);
		} else
		{
			Home.add(BReturn);
		}

		BNext.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				User.panel.add(Insertion.Init(), "1");
				User.cl.show(User.panel, "1");
			}
		});

		BManage.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				User.panel.add(Manage.Init(), "3");
				User.cl.show(User.panel, "3");
			}
		});

		BDisconnect.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					Login.connection.close();
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
				User.frame.dispose();
				Login.main(null);
			}
		});
		BExit.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});
		BReturn.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				User.frame.dispose();
			}
		});

		Home.add(GUI.NewImage("res/User/Backgroundd.png", 960, 1080, 0, 0));
		Home.setLayout(null);

		return Home;
	}

}
