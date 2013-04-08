package ch.hearc.progconc.labojava1;

import java.awt.Color;
import java.awt.Point;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author Sébastien Vaucher
 * @author Diego Antognini
 * 
 */
public class ProtectedZoneReetrantLock extends ProtectedZone
{
	private ReentrantLock lockCircle;
	private ReentrantLock lockRectangle;
	private ReentrantLock lockImage;
	
	private static final Color kColor = Color.BLUE;
	private static final String kName = "ReentrantLock";
	
	public ProtectedZoneReetrantLock(Point origin)
	{
		super(origin, kColor, kName);
	}

	@Override
	public void iWantToEnter(ObjetGraphique graphicalObject) throws InterruptedException
	{	
		if (graphicalObject instanceof Cercle)
		{
			if(graphicalObject != circle)
			{
				lockCircle.lockInterruptibly();
				circle = (Cercle) graphicalObject;
			}
		}
		else if (graphicalObject instanceof Rectangle)
		{
			if(graphicalObject != rectangle)
			{
				lockRectangle.lockInterruptibly();
				rectangle = (Rectangle) graphicalObject;
			}
		}
		else if (graphicalObject instanceof ImageGraphique)
		{
			if(graphicalObject != image)
			{
				lockImage.lockInterruptibly();
				image = (ImageGraphique) graphicalObject;
			}
		}
	}

	@Override
	public void releaseCircle()
	{
		circle = null;
		lockCircle.unlock();
	}

	@Override
	public void releaseRectangle()
	{
		rectangle = null;
		lockRectangle.unlock();
	}

	@Override
	public void releaseImage()
	{
		image = null;
		lockImage.unlock();
	}
	
	@Override
	public void init()
	{
		super.init();
		lockCircle = new ReentrantLock();
		lockRectangle = new ReentrantLock();
		lockImage = new ReentrantLock();
	}
}
