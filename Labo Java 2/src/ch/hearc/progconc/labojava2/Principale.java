package ch.hearc.progconc.labojava2;

/**
 * <h1>Implémentation</h1>
 * Nous avons représenté la barrier avec la classe Barrier qui contient autant de CyclicBarrier que de type d'objets rentrant. <br />
 * Pour cela, nous les stockons dans une Map.
 * 
 * Lorsqu'un objet touche la barrière, représenté par une ligne, celui-ci se appelle la méthode iWantToPass, qui va le mettre en attente.<br />
 * Une fois que 3 objets du même types sont présents, la barrière les libère,les laisse repartir et attent à nouveau 3 objets du même type.
 * <br />
 * Pour éviter toute incohérence, nous générons les objets en dehors de la barrière.
 * <br />
 * Lorsque l'on clique sur le bouton "effacer", il se passe les choses suivantes :
 * <br />1) Nous interrompons tous les threads des objets graphiques
 * <br />2) Nous supprimons tous les objets du vector contenant les objets
 * <br />3) Nous réinitalisons la barrière, avec la méthode resetBarrier, appelant la méthode clear de la classe CyclicBarrier.
 * 
 * 
 * <h1>Vérification du fonctionnement</h1>
 * Pour tester l'ensemble de notre programme, nous avons dessiné notre CyclicBarrier, puis avons généré un certain nombre d'objets.
 * <br /> Pour la barrière, il doit avoir au maximum 3 objets graphiques du même type ! Une fois ce nombre atteint, les
 * objets repartent correctement s'en s'arrêter à nouveau et les autres objets du même type arrivants, s'arrêtent en attendant d'être 3(sans compter ceux qui viennet de partir).<br />
 * Pour le deuxième test, nous cliquons sur le bouton "Effacer" et refaisons la même manipulation. 
 * <br />Si l'ensemble marche à nouveau sans erreur, c'est que le test est concluant.
 * 
 * Les 2 cas fonctionnent correctement.<br /><br />
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