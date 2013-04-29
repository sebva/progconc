package ch.hearc.progconc.labojava2;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
class ZoneDroite extends Panel
{

	public ZoneDroite(ActionListener ecouteur)
	{
		// 1 colonne, n lignes...
		setLayout(new GridLayout(0, 1));

		// bouton pour ajouter un cercle
		Button b1 = new Button("Ajouter cercle");
		add(b1);
		b1.addActionListener(ecouteur);

		// bouton pour ajouter un rectangle
		Button b2 = new Button("Ajouter rectangle");
		add(b2);
		b2.addActionListener(ecouteur);

		// bouton pour ajouter une image
		Button b3 = new Button("Ajouter image");
		add(b3);
		b3.addActionListener(ecouteur);

		// bouton pour changer la couleur des objets animés
		Button b4 = new Button("Changer couleur");
		add(b4);
		b4.addActionListener(ecouteur);
	}

}