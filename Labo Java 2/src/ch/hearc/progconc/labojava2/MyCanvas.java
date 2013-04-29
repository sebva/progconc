package ch.hearc.progconc.labojava2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

/**
 * Cette classe fournit un canvas affichant des objets graphiques Dessinable à intervalles réguliers
 */
@SuppressWarnings("serial")
class MyCanvas extends Canvas implements Runnable
{

	private Vector<ObjetGraphique> listeObjetsDessinables = new Vector<ObjetGraphique>();
	// Pour le double buffer
	private Image offScrImage;
	private Graphics offScrGC;
	private int largeur, hauteur;
	// temps en millisecondes entre chaque frame d'animation

	// Changement de couleurs
	private Random alea;
	
	private Barrier barrier;

	public MyCanvas(final Barrier barrier)
	{
		this.barrier = barrier;
		
		// Classe anonyme interne pour gérer l'événement resize()
		ComponentListener resizeCallback = new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				// Définition de la zone d'évolution des cercles.
				largeur = getSize().width;
				hauteur = getSize().height;

				ObjetGraphique.setZoneEvolution(largeur, hauteur);
				barrier.resize(largeur, hauteur);

				// Le double buffer = une image mémoire de la taille de la
				// zone d'évolution
				// des cercles
				offScrImage = createImage(largeur, hauteur);

				// Récupération d'un contexte graphique pour dessiner dans le
				// double buffer.
				offScrGC = offScrImage.getGraphics();
				offScrGC.setColor(Color.red);

				// System.out.println("Canvas Drawing1 retaillée : width = " + largeur + "height = " + hauteur);
			}
		}; // Fin de la classe anonyme

		// On indique qu'en cas de retaillage du canvas, c'est l'objet
		// resizeCallback
		// qui va traiter l'événement
		addComponentListener(resizeCallback);
		alea = new Random((new Date()).getTime());
		new Thread(this).start();
	}

	/**
	 * Ajoute à la liste des objets affiché à chaque frame d'animation un objet de plus
	 */
	public void ajouteObjetDessinable(ObjetGraphique objet)
	{
		listeObjetsDessinables.addElement(objet);
	}

	/**
	 * On surcharge update() car on fait du double buffer. On ne veut pas effacer à la fois la fenêtre graphique de l'applet, ce que fait par défaut update(),
	 * et effacer en plus le double buffer dans paint()
	 */
	public void update(Graphics g)
	{
		paint(g);
	}

	/** Modifie la couleur du tracé des objets fil de fer */
	public void changeCouleur()
	{
		offScrGC.setColor(new Color(Math.abs(alea.nextInt()) % 256, Math.abs(alea.nextInt()) % 256, Math.abs(alea.nextInt()) % 256));
	}

	/** Supprime tous les objets graphiques du canevas et réinitialise les zones protégées */
	public void clear()
	{
		// Interruption des threads des objets graphiques
		for (ObjetGraphique og : listeObjetsDessinables)
		{
			og.interruptThread();
		}
		listeObjetsDessinables.removeAllElements();
		barrier.resetBarriers();
	}

	/**
	 * La fonction qui effectue l'affichage des objets graphiques que doit gérer cette classe
	 */
	public void paint(Graphics gc)
	{
		// appelé automatiquement par l'AWT, ou sur demande par repaint()
		Enumeration<ObjetGraphique> liste = listeObjetsDessinables.elements();
		ObjetGraphique objetDessinable;

		// On efface le double buffer
		offScrGC.clearRect(0, 0, largeur, hauteur);

		// On affiche les objets animés
		while (liste.hasMoreElements())
		{
			objetDessinable = (ObjetGraphique) liste.nextElement();

			// On dessine un cercle dans le double buffer
			objetDessinable.dessineToi(offScrGC);
		}
		
		barrier.draw(offScrGC);

		// On recopie le double buffer dans le canvas
		gc.drawImage(offScrImage, 0, 0, this);
	}

	public void run()
	{

		while (true)
		{
			// On demande à redessiner les cercles
			repaint();

			// On "dort" tempsEntreFrame millisecondes
			try
			{
				Thread.sleep(16);
			}
			catch (InterruptedException e)
			{
				System.out.println("Erreur dans le sleep(tempsEntreFrames);");
				e.printStackTrace();
			}
		}
	}

}