package ch.hearc.progconc.labojava2;

import java.awt.Graphics;

public class Rectangle extends ObjetGraphique
{

	// Constructeurs
	public Rectangle(int x, int y, int width, int height, Barrier barrier)
	{
		super(x, y, width, height, barrier);

	}

	public void dessineToi(Graphics gc)
	{

		gc.drawRect(x - (largeur / 2), y - (hauteur / 2), largeur, hauteur);
	}
}
