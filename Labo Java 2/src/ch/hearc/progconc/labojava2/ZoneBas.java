package ch.hearc.progconc.labojava2;

import java.awt.Button;
import java.awt.Panel;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
class ZoneBas extends Panel
{
	ActionListener ecouteur;

	public ZoneBas(ActionListener ecouteur)
	{

		// bouton pour quitter l'application
		Button b4 = new Button("Quitter");

		add(b4);
		b4.addActionListener(ecouteur);

		// bouton pour quitter l'application
		Button b5 = new Button("Effacer");

		add(b5);
		b5.addActionListener(ecouteur);

	}

}