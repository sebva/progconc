/*
 * Automate
 * Diego Antognini & Sébastien Vaucher
 * 17 Décembre 2012
 * automate.h : Header du ficher d'implémentation principal
 */

#ifndef AUTOMATE_H
#define AUTOMATE_H

#include <vector>

//Touches utilisées pour activer les différentes actions
const char INSERT_COIN = 'I';
const char GET_BOTTLE = 'O';
const char QUIT = 'Q';

//Constantes (Nombre de bouteilles dans l'automate et prix d'une bouteille)
const int NB_BOTTLES = 5;
const double PRICE_BOTTLE = 2.5;

//Code de retour lorsque l'on quitte le thread VENDEUR
const int FIN_THREAD = -1;

//Les tâches en concurrence

// Insertion de la monnaie
void* MONNAIE(void* param);
// Exécution de la commande
void* DISTRIBUTEUR(void* param);
// Gestion de l'automate
void* VENDEUR(void* param);

// Initialise les conditions et le mutex
void initializeMutexCond();
// Détruit les conditions et le mutex
void destroyMutexCond();
// Crée les différents threads qui exécuteront MONNAIE, VENDEUR et DISTRIBUTEUR
void startAutomate();
// Menu affichant les différentes actions possibles pour l'utilisateur
char menuChoix();
// Vérifie que la pièce insérée est acceptable
bool isAvailablePiece(double n);
//Retourne le tableau des pièces valides
std::vector<double> getAvailablePieces();

// Permet de comparer 2 double
static bool doubleEquals(double a, double b);

// Entrée d'un nombre par l'utilisateur
template<typename T>
static void userInput(T& v);

#endif