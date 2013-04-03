package ch.hearc.progconc.labojava1;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageGraphique extends ObjetGraphique
{

	private Image kImage;
	private String nom;
	private Canvas parent;

	// Constructeurs
	public ImageGraphique(int x, int y, String nom, MyCanvas parent)
	{
		// Largeur et hauteur non connues à la création
		super(x, y, 0, 0, parent.getProtectedZones());
		try
		{
			kImage = ImageIO.read(getClass().getResource(nom));
		}
		catch (IOException ex)
		{
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		hauteur = kImage.getHeight(null);
		largeur = kImage.getWidth(null);
		this.parent = parent;

		// A faire, bloquer jusqu'à ce que l'image ait une taille et positionner cette taille !!!
	}

	public String toString()
	{
		return (super.toString() + "nom = " + nom + "\n");
	}

	public void dessineToi(Graphics gc)
	{
		gc.drawImage(kImage, x - (largeur / 2), y - (hauteur / 2), parent);
	}
}
