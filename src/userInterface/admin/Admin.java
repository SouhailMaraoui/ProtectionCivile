package userInterface.admin;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import scripts.GUI;
import userInterface.Login;
import userInterface.user.User;

public class Admin
{
	static JFrame frame;
	static JPanel tabs, buttomPanel;
	static JPanel use, cas, his, cal;
	static CardLayout cl;
	static JSplitPane splitPane;

	public static void init()
	{
		frame = new JFrame();
		tabs = new JPanel();
		buttomPanel = new JPanel();
		cl = new CardLayout();

		JButton UserTab = GUI.NewButton("Utilisateurs", 22,200,40, 0, 0);
		tabs.add(UserTab);
		JButton CaserneTab = GUI.NewButton("Casernes", 22,200,40, 200, 0);
		tabs.add(CaserneTab);
		JButton ResourceTab = GUI.NewButton("Ressources", 22,200,40, 400, 0);
		tabs.add(ResourceTab);
		JButton IncidentTab = GUI.NewButton("Incidents", 22,200,40, 600, 0);
		tabs.add(IncidentTab);
		JButton CallTab = GUI.NewButton("Appels", 22,200,40, 800, 0);
		tabs.add(CallTab);
		JButton HistoryTab = GUI.NewButton("Historique", 22,200,40, 1000, 0);
		tabs.add(HistoryTab);

		JButton userMode = GUI.NewButton("Mode Utilisateur", new Color(75, 75, 75), 20, 220, 40, 1920 - 200 * 3-20, 0);
		tabs.add(userMode);
		JButton Disconnect = GUI.NewButton("Déconnecter", new Color(75, 75, 75), 20, 200, 40, 1920 - 200 * 2, 0);
		tabs.add(Disconnect);
		JButton Exit = GUI.NewButton("Quitter", new Color(75, 75, 75), 20, 200, 40, 1920 - 200, 0);
		tabs.add(Exit);
		tabs.setLayout(null);

		buttomPanel.setLayout(cl);
		use = Utilisateurs.Init();
		cas = Casernes.Init();
		his = Histories.Init();
		cal = Calls.Init();
		buttomPanel.add(use, "1");
		buttomPanel.add(cas, "2");
		buttomPanel.add(his, "3");
		buttomPanel.add(cal, "4");
		buttomPanel.add(Incidents.Init(), "5");
		buttomPanel.add(TypeResources.Init(), "6");
		cl.show(buttomPanel, "1");

		userMode.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				User.init();
			}
		});

		UserTab.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				cl.show(buttomPanel, "1");
			}
		});
		CaserneTab.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				cl.show(buttomPanel, "2");
			}
		});
		HistoryTab.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Histories.update();
				cl.show(buttomPanel, "3");
			}
		});

		CallTab.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				cl.show(buttomPanel, "4");
			}
		});
		IncidentTab.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Incidents.update();
				cl.show(buttomPanel, "5");
			}
		});
		ResourceTab.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				cl.show(buttomPanel, "6");
			}
		});

		Disconnect.addMouseListener(new MouseAdapter()
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
				Admin.frame.dispose();
				Login.main(null);
			}
		});
		Exit.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabs, buttomPanel);
		splitPane.setDividerLocation(40);
		splitPane.setEnabled(false);

		frame.add(splitPane);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}