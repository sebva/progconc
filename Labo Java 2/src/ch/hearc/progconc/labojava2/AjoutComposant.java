package ch.hearc.progconc.labojava2;

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

	private int generateX()
	{
		return (int) (Math.rint(Math.random() * c.getWidth()));
	}

	private int generateY()
	{
		return (int) (Math.rint(Math.random() * c.getHeight()));
	}

	private void checkIsInZone(ObjetGraphique og)
	{
		boolean isCollision;
		do
		{
			isCollision = false;
			og.setX(generateX());
			og.setY(generateY());
		} while (isCollision);
	}

	/** Appelé lors d'un click sur un des boutons de contrôle */
	public void actionPerformed(ActionEvent evt)
	{
		String arg = evt.getActionCommand();
		if (arg.equals("Ajouter cercle"))
		{
			Cercle cercle = new Cercle(0, 0, 30);

			checkIsInZone(cercle);

			c.ajouteObjetDessinable(cercle);
			c.repaint();
			message.setText((String) ("Nb elements : " + ObjetGraphique.getNbObjetsCrees()));
		}
		else if (arg.equals("Ajouter image"))
		{
			ImageGraphique image = new ImageGraphique(0, 0, "duke.gif", c);

			checkIsInZone(image);

			c.ajouteObjetDessinable(image);
			message.setText("Nb elements : " + ObjetGraphique.getNbObjetsCrees());
			c.repaint();

		}
		else if (arg.equals("Ajouter rectangle"))
		{
			Rectangle rectangle = new Rectangle(0, 0, 30, 20);

			checkIsInZone(rectangle);

			c.ajouteObjetDessinable(rectangle);

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
