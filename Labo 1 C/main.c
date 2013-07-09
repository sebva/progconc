/*
 * Lamport's bakery algorithm
 * Diego Antognini & Sébastien Vaucher
 * 28 octobre 2012
 * main.c : Point d'entrée du programme
 *
 *	==IMPLÉMENTATION==
 *
 *	1) Mémoire partagée :
 *		Le programme contient une zone de mémoire partagée, utilisée par tous les threads.
 *		nbThreads : Nombre de threads voulus
 *		counter : Compteur commun des threads
 *		countTo : Valeur maximale du compteur à laquelle le programme va se terminer
 *		choices[] : Tableau de booléens contenant le choix des threads (nécessaire à l'algorithme du boulanger)
 *		counters[] : Tableau d'entiers contenant le numéro du ticket de chaque thread (nécessaire à l'algorithme du boulanger)
 *
 *	2) Méthodes :
 *		- Trois méthodes pour la création et l'initialisation des tableaux et variables nécessaires à l'algorithme
 *		- Une méthode pour la libération de la mémoire utilisée par les tableaux dynamiques
 *		- Deux méthodes pour la gestion de la section critique (lock() et unlock())
 *		- Une méthode qui indique les actions à effectuer par les threads (taskCode)
 *		- Une méthode qui permet la création des threads et leur utilisation
 *
 *	==VÉRIFICATION DU FONCTIONNEMENT==
 *	En classe, nous avons vu que des des incohérences dans l'ordre d'exécution se produisent si l'on 
 *	demande à plusieurs threads d'incrémenter un compteur commun (Exemple : 7-8-12-9-13-10-11-...). 
 *	L'idée est de refaire le même test, en variant la limite du compteur et le nombre de threads. Si 
 *	l'algorithme est correct, nous obtenons un comptage correct. Cependant, plus le nombre de threads 
 *	est grand, plus longtemps il faudra attendre, mais dans tous les cas il n'y a pas d'incohérence !
 *
 *	==SOURCE==
 *	http://fr.wikipedia.org/wiki/Algorithme_de_la_boulangerie
 */

#include "bakery.h"
#include <stdio.h>
#include <conio.h>
#include <stdlib.h>

// Lit un nombre de l'entrée standard
int getNumber(char *, int, int);

int main(void)
{
	int nbThreads = getNumber("Nombre de threads ?", 1, 100);
	int countTo = getNumber("Jusqu'à quelle valeur le programme doit-il compter ?", 1, INT_MAX);

	initializeGlobals(nbThreads, countTo);
	initializeCountersAndChoices();
	createAndUseThreads();
	freeMemory();

	printf("Appuyez sur une touche pour sortir ...");
	_getch();
	return EXIT_SUCCESS;
}

// Lit un nombre entre lowerLimit et upperLimit de l'entrée standard
int getNumber(char *prompt, int lowerLimit, int upperLimit)
{
	int number = lowerLimit -1, res = 0;
	puts(prompt);	
	do
	{
		printf("Choix [%d, %d] : ", lowerLimit, upperLimit);
		_flushall();
		res = scanf_s("%9d", &number);
	} while (res != 1 || number < lowerLimit || number > upperLimit);

	return number;
}
