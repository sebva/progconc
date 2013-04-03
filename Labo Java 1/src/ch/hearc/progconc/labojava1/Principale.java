package ch.hearc.progconc.labojava1;

/**
 * <h1>Implémentation</h1>
 * Pour la gestion des 2 zones, nous avons créé une classe parent abstraite "ProtectedZone", représentant une zone. Dans cette classe, l'on retrouve l'emplacement ainsi que ses dimensions,
 * sa couleur, le rectangle représentant l'ensemble de la zone, son nom puis un attribut par type. Concernant les méthodes non-abstract, on peut y voir une méthode "isInZone" recevant un object graphique et 
 * retournant un booléean indiquant si un objet se retrouve à l'intérieur. 
 * <br />Il y a encore une autre méthode non-abstract, la méthode "exit" qui permet à un objet graphique de sortir de la zone et en laisser un autre (du même type) entrer.
 * <br /><br />
 * Pour les méthodes abstracts il y a :
 * <br />- iWantToEnter, permettant de faire entrer un objet dans la zone
 * <br />- releaseXXX, permettant de libérer un objet de type XXX de la zone.
 * 
 * <br /><br />Nous avons ensuite 2 classes enfants : ProtectedZoneReetrantLock, représentant la zone utilisant le ReetrantLock, puis ProtectedZoneSemaphore, représentant la zone utilisant le Semaphore.
 * <br />
 * Ces 2 classes implémentent les méthodes abstraitent, en utilisant les méthodes associés au bon outil de programmation concurrente.
 * <br />A l'ajout d'un objet, ReetrantLock utilisera lockInterruptibly(), Semaphore un acquire(), et l'attribut correspondant au type d'objet se voit attribué par l'objet entrant.
 * <br />A la sortie d'un objet, celui-ci est mis à null puis on utilise, dans ReetrantLock unlock() et dans Semaphore release().
 * <br /><br />
 * Lorsque l'on clique sur le bouton "effacer", il se passe les choses suivantes :
 * <br />1) Nous interrompons tous les threads des objets graphiques
 * <br />2) Nous supprimons tous les objets du vector contenant les objets
 * <br />3) Nous réinitalisons les zones protégées, avec la méthode init(), qui est aussi utilisée dans le constructeur
 * 
 * 
 * <h1>Vérification Fonctionnement</h1>
 * Pour tester l'ensemble de nos programmes, nous avons dessiné nos 2 zones de contrôles (Semaphore + ReentrantLock), 
 * puis avons généré un grand nombre d'objets.<br /> Pour chacune de ces zones, il devait y avoir, au maximum, un object graphique de chaque type
 * à l'intérieur. Les autres objets n'étant pas dedans attendent à l'extérieur.<br />
 * Pour le deuxième test, nous cliquons sur le bouton "Effacer" et refaisons la même manipulation. 
 * <br />Si l'ensemble marche à nouveau sans erreur, 
 * c'est que le test est aussi concluant.
 * 
 * Les 2 cas fonctionnent correctement
 * 
 * @author Diego Antognini
 * @author Sébastien Vaucher
 */
class Principale
{
	public static void main(String args[])
	{

		AjoutComposant maFrame = new AjoutComposant("Ajouter Graphique");
		maFrame.setSize(1024,768);
		maFrame.setVisible(true);
	}
}