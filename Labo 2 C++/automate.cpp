/*
 * Automate
 * Diego Antognini & Sébastien Vaucher
 * 17 Décembre 2012
 * automate.cpp : Fichier d'implémentation principal
 */

#include "automate.h"
#include "structures.h"

#include <cmath>
#include <ctype.h>
#include <iomanip>
#include <iostream>
#include <pthread.h>
#include <vector>

using namespace std;

// Exclusion mutuelle globale
pthread_mutex_t mutex;

pthread_cond_t inserer;
pthread_cond_t distribuer;
pthread_cond_t vendeur;

void* MONNAIE(void* param)
{
	//Permettre l'arrêt du thread depuis un autre
	pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, NULL);
	pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL);

	while(true)
	{
		pthread_mutex_lock(&mutex);
		//On attend que le thread VENDEUR donne le signal d'insertion d'une pièce
		pthread_cond_wait(&inserer, &mutex);

		double n = 0;
		system("CLS");
		cout << "Les pièces acceptées sont les suivantes : ";

		static vector<double> availablePieces = getAvailablePieces();

		for(vector<double>::iterator it = availablePieces.begin() ; it!=availablePieces.end() ; it++)
			cout << *it << ",";

		cout << endl;
		do
		{
			cout << "Insérez une pièce  : ";

			userInput(n);

			//On vérifie que la pièce existe
			if(!isAvailablePiece(n))
			{
				cerr << "Pièce refusée" << endl;
				n = 0;
			}
			else
				cout << "La pièce de " << n << " a été acceptée !" << endl;
		} while (n <= 0);

		//On met à jour la variable contenant la pièce
		((Piece*)param)->setValue(n,ID_MONNAIE);//WRITE ONLY
		system("PAUSE");

		//On annonce au thread VENDEUR que l'action est terminée
		pthread_cond_signal(&vendeur);
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

void* DISTRIBUTEUR(void* param)
{
	//Permettre l'arrêt du thread depuis un autre
	pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, NULL);
	pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL);

	while(true)
	{
		pthread_mutex_lock(&mutex);
		//On attend que le thread VENDEUR donne le signal de la distribution de la bouteille
		pthread_cond_wait(&distribuer, &mutex);

		//On récupère le paramètre
		Commande* ach = (Commande*)param;

		system("CLS");
		cout << "Monnaie restante : " << ach->getSolde() << endl;
		//Si une bouteille a été achetée (c-à-d que le solde est suffisant et qu'il reste au moins 1 bouteille
		if(ach->getBouteille())
			cout << "Vous recevez 1 bouteille." << endl;
		else
			cout << "Vous ne recevez aucune bouteille." << endl;
		system("PAUSE");

		//On annonce au thread VENDEUR que l'action est terminée
		pthread_cond_signal(&vendeur);
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

void* VENDEUR(void* param)
{
	int nbBottles = NB_BOTTLES;
	//On récupère le paramètre
	Gestion* gest = (Gestion*)param;

	while(true)
	{
		system("CLS");
		cout << "Solde : " << setprecision(2) << fixed << gest->getDistrib()->getSolde() << endl;
		cout << "Bouteilles restantes : " << nbBottles << endl;
		cout << "Prix bouteille : " << PRICE_BOTTLE << endl << endl;

		pthread_mutex_lock(&mutex);

		double newSolde = 0;

		switch(menuChoix())
		{
            case GET_BOTTLE:
                gest->getDistrib()->setRecuBouteille(false,ID_VENDEUR);

                //Si le solde est suffisant et qu'il reste assez de bouteilles, on effectue la commande tout en déduisant le solde
                //On met à jour la ressource partagée
                if(gest->getDistrib()->getSolde() >= PRICE_BOTTLE && nbBottles > 0)
				{
					newSolde = gest->getDistrib()->getSolde() - PRICE_BOTTLE;

                    gest->getDistrib()->setSolde(newSolde,ID_VENDEUR);
                    nbBottles--;
                    gest->getDistrib()->setRecuBouteille(true,ID_VENDEUR);
                }

                //On annonce au thread DISTRIBUTEUR qu'il peut exécuter la commande
                pthread_cond_signal(&distribuer);
                //On attend que le thread DISTRIBUTEUR ait fini sa tâche
                pthread_cond_wait(&vendeur,&mutex);
                break;

            case INSERT_COIN:
                //On annonce au thread MONNAIE qu'il peut lancer la procédure d'insertion d'argent
                pthread_cond_signal(&inserer);
                //On attend que le thread DISTRIBUTEUR ait fini sa tâche
                pthread_cond_wait(&vendeur,&mutex);

				newSolde = gest->getDistrib()->getSolde() + gest->getPiece()->getValue();

                //On met à jour le solde en récupérant la pièce fraîchement insérée
                gest->getDistrib()->setSolde(newSolde, ID_VENDEUR);
                break;

            case QUIT:
                system("CLS");
				cout << endl << "Monnaie rendue : " << gest->getDistrib()->getSolde() << endl << endl;
				system("PAUSE");
				//On quitte le thread avec le code de retour
				pthread_exit((void*)FIN_THREAD);
			break;
		}

		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

void initializeMutexCond()
{
	pthread_mutex_init(&mutex, NULL);
	pthread_cond_init (&inserer, NULL);
	pthread_cond_init (&distribuer, NULL);
	pthread_cond_init (&vendeur, NULL);
}

void destroyMutexCond()
{
	pthread_mutex_destroy(&mutex);
	pthread_cond_destroy(&inserer);
	pthread_cond_destroy(&distribuer);
	pthread_cond_destroy(&vendeur);
}

void startAutomate()
{
	initializeMutexCond();

	pthread_t threads[3];

	// Création des ressources partagées

	//MONNAIE
	Piece piece = Piece(0.0);

	//DISTRIBUTEUR
	Commande dis = Commande(0.0,false);

	//VENDEUR
	//Référence à la ressource de MONNAIE
	//Référence à la ressource de DISTRIBUTEUR
	Gestion ven = Gestion(&piece,&dis);

	// Création des threads
	pthread_create(&threads[0], NULL, VENDEUR, &ven);
	pthread_create(&threads[1], NULL, DISTRIBUTEUR, &dis);
	pthread_create(&threads[2], NULL, MONNAIE, &piece);

	//On attend seulement que le thread VENDEUR finisse. C'est vendeur qui gère les 2 autres threads
	//On récupère le résultat de retour du thread VENDEUR
	void* status;
	pthread_join(threads[0], &status);

	//Si VENDEUR est terminé (dans le cas où l'utilisateur a choisi l'action "Quitter", on supprime les 2 autres threads
	if((int)status == FIN_THREAD)
	{
		pthread_cancel(threads[1]);
		pthread_cancel(threads[2]);
	}

	destroyMutexCond();
}

char menuChoix()
{
	char c = 0;

	cout << "Pour insérer une pièce de monnaie, pressez la touche '" << INSERT_COIN << "'." <<endl;
	cout << "Pour obtenir une bouteille, pressez la touche '" << GET_BOTTLE << "'." <<endl;
	cout << "Pour quitter, pressez la touche '" << QUIT << "'." <<endl << endl;

	do
	{
		cout << "Choix : ";

		userInput(c);

		if(!isupper(c))//Si la touche est en minuscule, on la passe en majuscule
			c = toupper(c);
	} while(c != INSERT_COIN && c != GET_BOTTLE && c != QUIT);

	return c;
}

bool doubleEquals(double a,double b)
{
	double e = 1e-3;// Pas besoin d'une meilleure précision, la pièce minimale est de 0.05
	return fabs(a - b) < e;
}

vector<double> getAvailablePieces()
{
	static vector<double> availablePieces;

	if(availablePieces.size() == 0)
	{
		//Ajout des différentes pièces existantes
		availablePieces.push_back(0.05);
		availablePieces.push_back(0.10);
		availablePieces.push_back(0.20);
		availablePieces.push_back(0.50);
		availablePieces.push_back(1.00);
		availablePieces.push_back(2.00);
		availablePieces.push_back(5.00);
	}
	return availablePieces;
}

bool isAvailablePiece(double piece)
{
	static vector<double> availablePieces = getAvailablePieces();

	// Regarde si la pièce passée en paramètre est acceptée
	for(vector<double>::iterator it = availablePieces.begin() ; it != availablePieces.end() ; it++)
	{
		if(doubleEquals(piece, *it))
			return true;
	}
	return false;
}

template<typename T>
void userInput(T& v)
{
	//Dans le cas où le nombre entré n'est pas un chiffre,on nettoye les bits d'erreurs
	cin.ignore(cin.rdbuf()->in_avail());
	cin >> v;
	if(!cin || cin.rdbuf()->in_avail() != 1)
	{
		cin.clear();
		v=0;
	}
}