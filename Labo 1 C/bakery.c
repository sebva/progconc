/*
 * Lamport's bakery algorithm
 * Diego Antognini & Sébastien Vaucher
 * 28 octobre 2012
 * bakery.c : Fichier d'implémentation principal
 */

#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include "bakery.h"

#define false 0
#define true 1

int* choices = 0;
int* counters = 0;
int nbThreads = 0;
int countTo = 0;
int counter = 0;

void initializeGlobals(int _nbThreads, int _countTo)
{
	nbThreads = _nbThreads;
	countTo = _countTo;
}

void initializeCountersAndChoices()
{
	int i = 0;
	counters = (int*)malloc(sizeof(int) * nbThreads);
	choices = (int*)malloc(sizeof(int) * nbThreads);

	// On contrôle que l'allocation de mémoire a réussi
	if(counters == NULL || choices == NULL)
		exit(EXIT_FAILURE);

	for(i = 0; i < nbThreads; i++)
	{
		counters[i] = 0;
		choices[i] = false;
	}
}

void freeMemory()
{
	free(counters);
	counters = NULL;

	free(choices);
	choices = NULL;
}

void lock(const int id)
{
	int max = counters[0];
	int i = 0;

	// Le thread n°id entre en mode attribution de ticket
	choices[id] = true;

	// On recherche le plus grand numéro de ticket parmi les threads
	for(i = 0; i < nbThreads; i++)
		if(counters[i] > max)
			max = counters[i];

	// On attribut un ticket plus grand que le dernier, le thread n°id devient le dernier
	counters[id] = max + 1;

	// Fin du mode attribution de ticket
	choices[id] = false;

	// Si plusieurs threads exécutent ces calculs en même temps, il est possible que certains d'entre-eux
	// possèdent le même n° de ticket. C'est pourquoi il faut vérifier que tous les autres tickets ont
	// des numéros inférieurs à celui actuel.

	for(i = 0; i < nbThreads; i++)
	{
		// Si le thread est en mode attribution du ticket, on attend d'avoir le n° du ticket
		while(choices[i]);

		/*Condition 1 : Avoir un numéro de ticket pour rentrer dans la section critique
			ET
				Condition 2 : On regarde si le thread de la boucle a une priorité supérieur au thread actuel
				OU
				Condition 3 : Ils ont le même numéro de ticket
					ET
					Condition 4 : Dans ce cas la priorité va au thread ayant l'indice le plus petit
		*/
		while(counters[i]/*(1)*/ && ((counters[i] < counters[id])/*(2)*/ || ((counters[i] == counters[id])/*(3)*/ && (i < id)/*(4)*/)));
	}
}

void unlock(const int id)
{
	// On met à 0 le numéro de ticket, donc le thread ne veut pas rentrer en section critique
	counters[id] = 0;
}

void* taskCode(void* argument)
{
	// On récupère l'identifiant du thread
	int tid = *((int *) argument);

	while(counter < countTo)
	{
		// On demande l'entrée en section critique
		lock(tid);
		// Début section critique
		if(counter < countTo)
		{
			// On incrémente le compteur et on l'affiche
			counter++;
			printf("Thread %d\tTicket %d\tCounter : %d\n", tid, counters[tid], counter);
		}
		// Fin section critique

		//On libère la section critique
		unlock(tid);
	}

	return NULL;
}

void createAndUseThreads()
{
	int i = 0;
	// On créé le tableau des threads et le tableau des arguments
	pthread_t* threads = (pthread_t*)malloc(sizeof(pthread_t) * nbThreads);
	int* threads_args = (int*)malloc(sizeof(int) * nbThreads);

	// Création des nbThreads threads
	for (i = 0; i < nbThreads; i++) {
		threads_args[i] = i;
		pthread_create(threads + i, NULL, taskCode, threads_args + i);
	}

	// On attend que tous les threads aient fini leurs tâches
	for (i = 0; i < nbThreads; i++)
		pthread_join(threads[i], NULL);

	// Libération de la mémoire occupée par les tableaux dynamiques
	free(threads);
	threads = NULL;
	free(threads_args);
	threads_args = NULL;
}