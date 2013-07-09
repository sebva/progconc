/*
 * Lamport's bakery algorithm
 * Diego Antognini & Sébastien Vaucher
 * 28 octobre 2012
 * bakery.h : Header du ficher d'implémentation principal
 */

#ifndef BAKERY_H
#define BAKERY_H

// Nombre de threads
int nbThreads;
// Compteur commun des différents threads
int counter;
// Quand le compteur atteint countTo, on arrête le programme
int countTo;
// Tableau de nbThreads cases où chaque case représente si le thread est en pleine attribution de ticket (true)
// ou s'il possède déjà son ticket (false)
int* choices;
// Tableau de nbThreads cases où chaque case représente le numéro de ticket du thread.
// Si la valeur vaut 0, le thread ne veut pas rentrer dans une section critique.
int* counters;

// Initialise les variables globales.
void initializeGlobals(int, int);
// Initialise le tableau des tickets et celui des choix.
void initializeCountersAndChoices();
// Libère la mémoire utilisée par les tableaux counters et choices.
void freeMemory();

// Demande l'entrée en section critique. Il ne peut y avoir qu'un seul thread en section critique.
// L'appel est bloquant : la méthode retourne lorsque la section critique est libre pour l'appelant.
void lock(const int);
// Libère la section critique, ce qui permettra à un autre thread d'y entrer.
void unlock(const int);

// Code que doit exécuter chaque thread. Contient la section critique.
void* taskCode(void*);
// Crée les différents threads qui exécuteront la méthode taskCode()
void createAndUseThreads();

#endif