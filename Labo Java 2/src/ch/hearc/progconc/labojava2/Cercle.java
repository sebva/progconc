package ch.hearc.progconc.labojava2;

import java.awt.Graphics;

public class Cercle extends ObjetGraphique
{

	int rayon; // le rayon du cercle

	// Constructeurs
	public Cercle(int x, int y, int rayon)
	{
		super(x, y, 2 * rayon, 2 * rayon);
		this.rayon = rayon;
	}

	public int getRayon()
	{
		return rayon;
	}

	public void setRayon(int r)
	{
		rayon = r;
	}

	public String toString()
	{
		return (super.toString() + "rayon = " + rayon + "\n");
	}

	public void dessineToi(Graphics gc)
	{

		gc.drawOval(x - rayon, y - rayon, 2 * rayon, 2 * rayon);
	}
}
