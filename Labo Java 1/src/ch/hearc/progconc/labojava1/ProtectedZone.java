package ch.hearc.progconc.labojava1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Classe abstraite représentant une zone protégée
 * 
 * @author Sébastien Vaucher
 * @author Diego Antognini
 */
public abstract class ProtectedZone
{
	/** Origine du rectangle */
	private Point origin;
	/** Dimension des zones protégées */
	private final static Dimension kDimension = new Dimension(300, 200);
	private final java.awt.Rectangle kZone;
	private Color color;
	// Objets graphiques contenus dans la zone
	protected Cercle circle = null;
	protected Rectangle rectangle = null;
	protected ImageGraphique image = null;
	private String zoneName;
	
	public ProtectedZone(Point origin, Color color, String zoneName)
	{
		this.origin = origin;
		this.color = color;
		this.zoneName = zoneName;
		kZone = new java.awt.Rectangle(origin, kDimension);
		
		this.init();
	}
	
	public abstract void iWantToEnter(ObjetGraphique graphicalObject) throws InterruptedException;
	public abstract void releaseCircle();
	public abstract void releaseRectangle();
	public abstract void releaseImage();
	
	public void init()
	{
		circle = null;
		rectangle = null;
		image = null;
	}

	public boolean isInZone(java.awt.Rectangle rect)
	{
		return kZone.intersects(rect);
	}
	
	public void draw(Graphics gc)
	{
		Color oldColor = gc.getColor();
		
		gc.setColor(color);
		gc.drawString(zoneName, origin.x, origin.y - 5);
		gc.drawRect(origin.x, origin.y, kDimension.width, kDimension.height);
		
		gc.setColor(oldColor);
	}

	public void exit(ObjetGraphique graphicalObject)
	{
		if (graphicalObject instanceof Cercle)
		{
			if (circle == graphicalObject)
				releaseCircle();
		}
		else if (graphicalObject instanceof Rectangle)
		{
			if (rectangle == graphicalObject)
				releaseRectangle();
		}
		else if (graphicalObject instanceof ImageGraphique)
		{
			if (image == graphicalObject)
				releaseImage();
		}
	}
}
