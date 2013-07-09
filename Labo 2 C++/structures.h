/*
 * Automate
 * Diego Antognini & Sébastien Vaucher
 * 17 Décembre 2012
 * structures.h : Header des structures paramètres
 */

#ifndef STRUCTURES_H
#define STRUCTURES_H

//Identifiant des threads pour protéger les ressources
#define ID_VENDEUR 1
#define ID_MONNAIE 2
#define ID_DISTRIBUTEUR 3

//Paramètre utilisé dans le thread DISTRIBUTEUR et dans le paramètre de VENDEUR
class Commande
{
public:
	Commande(double s, bool b) : solde(s), bouteille(b) {}
	double getSolde() const { return solde; }
	double getBouteille() const { return bouteille; }

	void setSolde(double s, int id);
	void setRecuBouteille(bool b, int id);//Détermine si le système peut donner une bouteille ou non
private:
	double solde;
	bool bouteille;
};

//Paramètre utilisé dans le thread MONNAIE
class Piece
{
public:
	Piece(double v):valeur(v) {}
	double getValue() const { return valeur; }
	void setValue(double v,int id);
private:
	double valeur;
};

//Paramètre utilisé dans le thread VENDEUR
class Gestion
{
public:
	Gestion(Piece* p,Commande* d):piece(p),distrib(d){}
	Piece* getPiece() const {return piece;}
	Commande* getDistrib() const {return distrib;}

private:
	//Ressource partagée entre le thread VENDEUR (read only) et MONNAIE (write only)
	Piece* piece;
	//Ressource partagée entre le thread VENDEUR (write only) et DISTRIBUTEUR (read only)
	Commande* distrib;
};

#endif