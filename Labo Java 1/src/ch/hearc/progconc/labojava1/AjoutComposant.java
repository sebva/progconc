package ch.hearc.progconc.labojava1;

import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class AjoutComposant extends Frame implements ActionListener {
	MyCanvas c;
	Label message = new Label("Zone de messages");

	static int nombreObject;
	private final ProtectedZone[] pz = {
			new ProtectedZoneReetrantLock(new Point(500, 100)),
			new ProtectedZoneSemaphore(new Point(50, 200)) };

	public AjoutComposant(String titre) {
		super(titre);

		// Fenêtre d'animation au centre
		c = new MyCanvas(pz);
		add("Center", c);

		// Fenêtre avec les boutons de contrôle à gauche
		ZoneBas zoneBas = new ZoneBas(this);
		add("South", zoneBas);

		// Fenêtre avec les boutons de contrôle à droite
		ZoneDroite zoneDroite = new ZoneDroite(this);
		add("East", zoneDroite);

		// Zone de message en haut
		add("North", message);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		message.setText("Nb elements : " + ObjetGraphique.getNbObjetsCrees());
	}

	private int generateX() {
		return (int) (Math.rint(Math.random() * c.getWidth()));
	}

	private int generateY() {
		return (int) (Math.rint(Math.random() * c.getHeight()));
	}

	/** Appelé lors d'un click sur un des boutons de contrôle */
	public void actionPerformed(ActionEvent evt) {
		String arg = evt.getActionCommand();
		if (arg.equals("Ajouter cercle")) {
			Cercle cercle = new Cercle(0, 0, 30, c.getProtectedZones());

			boolean isCollision = false;

			do {
				isCollision = false;
				cercle.setX(generateX());
				cercle.setY(generateY());

				java.awt.Rectangle rect = cercle.getRect();

				for (ProtectedZone p : pz) {
					isCollision = p.isInZone(rect);
					if (isCollision)
						break;
				}
			} while (isCollision);

			c.ajouteObjetDessinable(cercle);
			c.repaint();
			message.setText((String) ("Nb elements : " + ObjetGraphique
					.getNbObjetsCrees()));
		} else if (arg.equals("Ajouter image")) {
			ImageGraphique image = new ImageGraphique(0, 0, "duke.gif", c);

			boolean isCollision = false;

			do {
				isCollision = false;
				image.setX(generateX());
				image.setY(generateY());

				java.awt.Rectangle rect = image.getRect();

				for (ProtectedZone p : pz) {
					isCollision = p.isInZone(rect);
					if (isCollision)
						break;
				}
			} while (isCollision);

			c.ajouteObjetDessinable(image);
			message.setText("Nb elements : "
					+ ObjetGraphique.getNbObjetsCrees());
			c.repaint();

		} else if (arg.equals("Ajouter rectangle")) {
			Rectangle rectangle = new Rectangle(0, 0, 30, 20,
					c.getProtectedZones());

			boolean isCollision = false;

			do {
				isCollision = false;
				rectangle.setX(generateX());
				rectangle.setY(generateY());

				java.awt.Rectangle rect = rectangle.getRect();

				for (ProtectedZone p : pz) {
					isCollision = p.isInZone(rect);
					if (isCollision)
						break;
				}
			} while (isCollision);

			c.ajouteObjetDessinable(rectangle);

			message.setText("Nb elements : "
					+ ObjetGraphique.getNbObjetsCrees());
			c.repaint();
		} else if (arg.equals("Changer couleur")) {
			c.changeCouleur();
			c.repaint();
		} else if (arg.equals("Effacer")) {
			c.clear();
			ObjetGraphique.nbObjetsCrees = 0;
			message.setText("Nb elements : "
					+ ObjetGraphique.getNbObjetsCrees());
			c.repaint();

		} else if (arg.equals("Quitter")) {
			System.exit(0);
		}
	}

}
