package ch.hearc.progconc.labojava1;

import java.awt.Color;
import java.awt.Point;
import java.util.concurrent.Semaphore;

/**
 * 
 * @author Sébastien Vaucher
 * @author Diego Antognini
 * 
 */
public class ProtectedZoneSemaphore extends ProtectedZone
{
	private Semaphore semaphoreRectangle;
	private Semaphore semaphoreCircle;
	private Semaphore semaphoreImage;
	
	private static final Color kColor = Color.RED;
	private static final String kName = "Semaphore";
	private static final int kNumberOfObjectsInCriticalSection = 1;

	public ProtectedZoneSemaphore(Point origin)
	{
		super(origin, kColor, kName);
		semaphoreCircle = new Semaphore(kNumberOfObjectsInCriticalSection);
		semaphoreImage = new Semaphore(kNumberOfObjectsInCriticalSection);
		semaphoreRectangle = new Semaphore(kNumberOfObjectsInCriticalSection);
	}

	@Override
	public void iWantToEnter(ObjetGraphique graphicalObject)
	{
		try
		{
			if (graphicalObject instanceof Cercle)
			{
				if(graphicalObject != circle)
				{
					semaphoreCircle.acquire();
					circle = (Cercle) graphicalObject;
				}
			}
			else if (graphicalObject instanceof Rectangle)
			{
				if(graphicalObject != rectangle)
				{
					semaphoreRectangle.acquire();
					rectangle = (Rectangle) graphicalObject;
				}
			}
			else if (graphicalObject instanceof ImageGraphique)
			{
				if(graphicalObject != image)
				{
					semaphoreImage.acquire();
					image = (ImageGraphique) graphicalObject;
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void releaseCircle()
	{
		semaphoreCircle.release();
		circle = null;
	}

	@Override
	public void releaseRectangle()
	{
		semaphoreRectangle.release();
		rectangle = null;
	}

	@Override
	public void releaseImage()
	{
		semaphoreImage.release();
		image = null;
	}
}
