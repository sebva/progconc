/*
 * Automate
 * Diego Antognini & Sébastien Vaucher
 * 17 Décembre 2012
 * structures.cpp : Fichiers d'implémentation des structures paramètres
 */

#include "structures.h"

void Commande::setSolde(double s,int id)
{
	if(id == ID_VENDEUR)
		solde = s;
}

void Commande::setRecuBouteille(bool b,int id)
{
	if(id == ID_VENDEUR)
		bouteille = b;
}

void Piece::setValue(double v,int id)
{
	if(id == ID_MONNAIE)
		valeur = v;
}