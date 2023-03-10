#include "repo.h"
#include <stdio.h>

ListaPersoane* creeareLista()
{
	ListaPersoane* list = malloc(sizeof(ListaPersoane));
	list->vect = creeareVector(0, equalPersoana, copyPersoana, distrugePersoana);
	return list;
}

void destroyElemsRepo(ListaPersoane* lista) {
	destroyElems(lista->vect);
}

void distrugeLista(ListaPersoane* lista)
{
	distrugeVector(lista->vect);
	free(lista);
}

int getDimList(ListaPersoane* lista)
{
	return getDim(lista->vect);
}

Vector* getVect(ListaPersoane* lista) {
	return copyVector(lista->vect, distrugePersoana);
}

int adaugaPersoana(ListaPersoane* lista, Persoana* p)
{
	int poz = cautaPersoana(lista, p);
	if (poz != -1) return PERSOANA_EXISTA_DEJA;
	adaugaElem(lista->vect, p);
	return SUCCES;
}

Persoana* stergePersoana(ListaPersoane* lista, Persoana* p)
{
	int poz = cautaPersoana(lista, p);
	if (poz == -1) return (Persoana*)PERSOANA_NU_EXISTA;
	return stergeElem(lista->vect, p);
}

Persoana* modificaPersoana(ListaPersoane* lista, Persoana* p_vechi, Persoana* p_nou)
{
	int poz = cautaPersoana(lista, p_vechi);
	if (poz == -1) return (Persoana*)PERSOANA_NU_EXISTA;
	Persoana* to_delete = setElem(lista->vect, poz, copyPersoana(p_nou));
	return to_delete;
}

Persoana* setPersoana(ListaPersoane* lista, int poz, Persoana* p_nou)
{
	if (poz < 0 || poz > getDim(lista->vect)) return (Persoana*)PERSOANA_NU_EXISTA;
	Persoana* to_delete = setElem(lista->vect, poz, p_nou);
	return to_delete;
}

int cautaPersoana(ListaPersoane* lista, Persoana* p)
{
	return cautaElem(lista->vect, p);
}

Persoana* getPersoana(ListaPersoane* lista, int poz)
{
	return getElem(lista->vect, poz);
}

void testRepo()
{
	ListaPersoane* list = creeareLista(0);
	assert(getDimList(list) == 0);
	Persoana* p = creearePersoana("Numep", "Prenumep", 80);
	Persoana* p1 = creearePersoana("Numep1", "Prenumep1", 55);
	assert(adaugaPersoana(list, p) == SUCCES);
	assert(adaugaPersoana(list, p1) == SUCCES);
	assert(adaugaPersoana(list, p1) == PERSOANA_EXISTA_DEJA);
	assert(equalPersoana(getPersoana(list, 0), p));
	assert(equalPersoana(getPersoana(list, 1), p1));
	assert(cautaPersoana(list, p) == 0);
	assert(getDimList(list) == 2);
	Persoana* to_delete = stergePersoana(list, p);
	assert((int)to_delete != PERSOANA_NU_EXISTA);
	Persoana* not_found = stergePersoana(list, p);
	assert((int)not_found == PERSOANA_NU_EXISTA);
	assert(cautaPersoana(list, p) == PERSOANA_NU_EXISTA);
	Persoana* temp2 = modificaPersoana(list, p, p1);
	assert((int)temp2 == PERSOANA_NU_EXISTA);
	Persoana* temp4 = modificaPersoana(list, p1, p);
	assert((int)temp4 != PERSOANA_NU_EXISTA);
	Persoana* temp3 = setPersoana(list, 3, p);
	assert((int)temp3 == PERSOANA_NU_EXISTA);
	Persoana* to_delete2 = setPersoana(list, 0, p1);
	assert((int)to_delete2 != PERSOANA_NU_EXISTA);
	Vector* copieVect = getVect(list);
	destroyElems(copieVect);
	distrugeVector(copieVect);
	distrugePersoana(to_delete);
	distrugePersoana(to_delete2);
	destroyElemsRepo(list);
	distrugeLista(list);
}