package ch.hearc.progconc.labojava1;

import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class AjoutComposant extends Frame implements ActionListener
{
	MyCanvas c;
	Label message = new Label("Zone de messages");

	static int nombreObject;

	public AjoutComposant(String titre)
	{
		super(titre);

		// Fenêtre d'animation au centre
		c = new MyCanvas();
		add("Center", c);

		// Fenêtre avec les boutons de contrôle à gauche
		ZoneBas zoneBas = new ZoneBas(this);
		add("South", zoneBas);

		// Fenêtre avec les boutons de contrôle à droite
		ZoneDroite zoneDroite = new ZoneDroite(this);
		add("East", zoneDroite);

		// Zone de message en haut
		add("North", message);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		message.setText("Nb elements : " + ObjetGraphique.getNbObjetsCrees());
	}

	/** Appelé lors d'un click sur un des boutons de contrôle */
	public void actionPerformed(ActionEvent evt)
	{
		String arg = evt.getActionCommand();
		if (arg.equals("Ajouter cercle"))
		{

			c.ajouteObjetDessinable(new Cercle((int) (Math.rint(Math.random() * c.getWidth())), (int) (Math.rint(Math.random() * c.getHeight())), 30, c.getProtectedZones()));
			c.repaint();
			message.setText((String) ("Nb elements : " + ObjetGraphique.getNbObjetsCrees()));
		}
		else if (arg.equals("Ajouter image"))
		{
			c.ajouteObjetDessinable(new ImageGraphique((int) (Math.rint(Math.random() * c.getWidth())), (int) (Math.rint(Math.random() * c.getHeight())),
					"duke.gif", c));
			message.setText("Nb elements : " + ObjetGraphique.getNbObjetsCrees());
			c.repaint();

		}
		else if (arg.equals("Ajouter rectangle"))
		{
			c.ajouteObjetDessinable(new Rectangle((int) (Math.rint(Math.random() * c.getWidth())), (int) (Math.rint(Math.random() * c.getHeight())), 30, 20, c.getProtectedZones()));

			message.setText("Nb elements : " + ObjetGraphique.getNbObjetsCrees());
			c.repaint();
		}
		else if (arg.equals("Changer couleur"))
		{
			c.changeCouleur();
			c.repaint();
		}
		else if (arg.equals("Effacer"))
		{
			c.clear();
			ObjetGraphique.nbObjetsCrees = 0;
			message.setText("Nb elements : " + ObjetGraphique.getNbObjetsCrees());
			c.repaint();

		}
		else if (arg.equals("Quitter"))
		{
			System.exit(0);
		}
	}

}
