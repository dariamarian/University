#pragma once
#include "repo.h"
#include "validation.h"
#define SORTAT 4
#define NESORTAT 5
#define FILTRU_INVALID 6

typedef struct Service {
	ListaPersoane* lista;
	int(*validare)(Persoana*);
} Service;

Service* creeareService(ListaPersoane* lista, int(*validare)(Persoana*));
void destroyElemsServ(Service* cont);
void distrugeService(Service* cont);
int getDimCont(Service* cont);
Vector* getAllElems(Service* cont);
ListaPersoane* getLista(Service* cont);
int adaugare(Service* cont, char* nume, char* prenume, int scor);
Persoana* stergere(Service* cont, char* nume, char* prenume);
Persoana* actualizare(Service* cont, char* nume, char* prenume, char* nume_nou, char* prenume_nou, int scor_nou);
Vector* filtrare(Service* cont, char* nume, int scor);
Vector* sortare(Service* cont, char* filtru);
void testService();