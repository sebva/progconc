package ch.hearc.progconc.labojava2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Barrier
{
	private static final int kNumberOfObjectsNecessaryInOrderToPassTheBarrier = 3;
	
	private Map<String, CyclicBarrier> cyclicBarriers;
	private String nom;
	private Point p1 = new Point(0, 300);
	private Point p2 = new Point(300, 400);
	
	public Barrier(String nom)
	{
		this.nom = nom;
		
		cyclicBarriers = new HashMap<String, CyclicBarrier>();
	}
	
	public void draw(Graphics gc)
	{
		Color oldColor = gc.getColor();
		
		gc.setColor(Color.RED);
		gc.drawString(nom, (p1.x + p2.x) / 2, (p1.y + p2.y) / 2 - 5);
		gc.drawLine(p1.x, p1.y, p2.x, p2.y);
		
		gc.setColor(oldColor);
	}
	
	public void iWantToPass(ObjetGraphique obj) throws InterruptedException
	{
		String key = obj.getClass().getName();
		initCyclicBarrier(key);
		
		try
		{
			cyclicBarriers.get(key).await();
		}
		catch (BrokenBarrierException ex)
		{
			// Rien
		}
	}

	private void initCyclicBarrier(String key)
	{
		if(!cyclicBarriers.containsKey(key))
			cyclicBarriers.put(key, new CyclicBarrier(kNumberOfObjectsNecessaryInOrderToPassTheBarrier));
	}

	public boolean isInZone(Rectangle rect)
	{
		return rect.intersectsLine(p1.x, p1.y, p2.x, p2.y);
	}

	public void resetBarriers()
	{
		cyclicBarriers.clear();
	}

	public void resize(int largeur, int hauteur)
	{
		p1 = new Point(0, (int) ((2.0/3.0) * hauteur));
		p2 = new Point(largeur, (int) ((1.0/3.0) * hauteur));
	}
}
