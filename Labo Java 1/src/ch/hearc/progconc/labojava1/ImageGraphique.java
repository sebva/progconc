package ch.hearc.progconc.labojava1;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageGraphique extends ObjetGraphique
{

	private Image MonImage;
	private String nom;
	private Canvas parent;

	// Constructeurs
	public ImageGraphique(int x, int y, String nom, Canvas parent)
	{
		// Largeur et hauteur non connues à la création
		super(x, y, 0, 0);
		MonImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(nom));
		this.parent = parent;

		// A faire, bloquer jusqu'à ce que l'image ait une taille et positionner cette taille !!!
	}

	public String toString()
	{
		return (super.toString() + "nom = " + nom + "\n");
	}

	public void dessineToi(Graphics gc)
	{
		gc.drawImage(MonImage, x - (MonImage.getWidth(parent) / 2), y - (MonImage.getHeight(parent) / 2), parent);
	}
}
