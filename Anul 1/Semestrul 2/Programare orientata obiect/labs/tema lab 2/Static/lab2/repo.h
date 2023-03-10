#pragma once
#include "entity.h"
#include "vector.h"
#define PERSOANA_EXISTA_DEJA -2
#define PERSOANA_NU_EXISTA -1
#define SUCCES 0

typedef struct {
	Vector* vect;
} ListaPersoane;

ListaPersoane* creeareLista();
void destroyElemsRepo(ListaPersoane* lista);
void distrugeLista(ListaPersoane* lista);
int getDimList(ListaPersoane* lista);
Vector* getVect(ListaPersoane* lista);
int adaugaPersoana(ListaPersoane* lista, Persoana* p);
Persoana* stergePersoana(ListaPersoane* lista, Persoana* p);
Persoana* modificaPersoana(ListaPersoane* lista, Persoana* p_vechi, Persoana* p_nou);
Persoana* setPersoana(ListaPersoane* lista, int poz, Persoana* p_nou);
int cautaPersoana(ListaPersoane* lista, Persoana* p);
Persoana* getPersoana(ListaPersoane* lista, int poz);
void testRepo();