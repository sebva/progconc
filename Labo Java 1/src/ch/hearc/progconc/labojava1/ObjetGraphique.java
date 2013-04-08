package ch.hearc.progconc.labojava1;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class ObjetGraphique implements Runnable
{

	protected int x, y; // position du centre
	protected int largeur, hauteur; // Boite englobante de l'objet
	static int zone_l, zone_h; // taille de la zone ou l'objet a le droit de se promener
	static int nbObjetsCrees = 0;
	private final ProtectedZone[] pz;
	private Thread thread;

	// Constructeurs
	public ObjetGraphique(int x, int y, int largeur, int hauteur, ProtectedZone[] pz)
	{
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.pz = pz;
		nbObjetsCrees++;

		thread = new Thread(this);
		thread.start();
	}

	/** Renvoie le nombre d'instances d'ObjectGraphique */
	static public int getNbObjetsCrees()
	{
		return nbObjetsCrees;
	}

	// Méthodes publiques
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getHauteur()
	{
		return hauteur;
	}

	public int getLargeur()
	{
		return largeur;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Rectangle getRect()
	{
		return new java.awt.Rectangle(x - (largeur / 2), y- (hauteur / 2), largeur,hauteur);
	}
	
	public void setHauteur(int hauteur)
	{
		this.hauteur = hauteur;
	}

	public void setLargeur(int largeur)
	{
		this.largeur = largeur;
	}

	public void move(int x, int y) throws InterruptedException
	{
		for (ProtectedZone p : pz)
		{
			if(p.isInZone(getRect()))
				p.iWantToEnter(this);
			else
				p.exit(this);
		}
		this.x = x;
		this.y = y;
	}
	
	public void interruptThread()
	{
		thread.interrupt();
	}

	public abstract void dessineToi(Graphics gc);

	/** Réglage de la zone rectangulaire dans laquelle vont se déplacer les objets */
	static public void setZoneEvolution(int w, int h)
	{
		zone_l = w;
		zone_h = h;
	}

	public void run()
	{
		int incX = 1, incY = 1;

		try
		{
			while (true)
			{
				int x = this.x, y = this.y;
	
				// On calcule la nouvelle position de l'objet
	
				x += Math.rint(Math.random() * incX);
				y += Math.rint(Math.random() * incY);
	
				// Pour ne pas que l'objet sorte de l'ecran
				if ((incX == 1) && ((x + (largeur / 2)) > zone_l))
					incX = -1;
				//System.out.println(largeur);
	
				if ((incX == -1) && (x - (largeur / 2) < 0))
					incX = 1;
	
				if ((incY == 1) && (y + (hauteur / 2) > zone_h))
					incY = -1;
	
				if ((incY == -1) && (y - (hauteur / 2) < 0))
					incY = 1;
	
				// On déplace l'objet
				move(x, y);
	
				// On "dort" tempsEntreDeplacement millisecondes
				Thread.sleep(10);
			}
		}
		catch (InterruptedException e)
		{
			//System.out.println(Thread.currentThread().getName() + " interrompu");
		}

	}

}
