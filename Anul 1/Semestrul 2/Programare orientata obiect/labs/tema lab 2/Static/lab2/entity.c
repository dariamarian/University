#include "entity.h"
#include <stdlib.h>
#include <string.h>
#include <assert.h>

Persoana* creearePersoana(char* nume, char* prenume, int scor)
{
	Persoana* p = malloc(sizeof(Persoana));
	if (p == NULL)
		return;
	p->nume = malloc(sizeof(char) * strlen(nume) + 1);
	if (p->nume != NULL)
		strcpy(p->nume, nume);
	p->prenume = malloc(sizeof(char) * strlen(prenume) + 1);
	if (p->prenume != NULL)
		strcpy(p->prenume, prenume);
	p->scor = scor;
	return p;
}

void distrugePersoana(Persoana* p) {
	if (p == NULL)
		return;
	free(p->nume);
	free(p->prenume);
	free(p);
}

char* getNume(Persoana* p) {
	if (p == NULL)
		return;
	return p->nume;
}

char* getPrenume(Persoana* p) {
	if (p == NULL)
		return;
	return p->prenume;
}

int getScor(Persoana* p)
{
	if (p == NULL)
		return -1;
	return p->scor;
}

void setNume(Persoana* p, char* nume)
{
	free(p->nume);
	p->nume = _strdup(nume);
}

void setPrenume(Persoana* p, char* prenume)
{
	free(p->prenume);
	p->prenume = _strdup(prenume);
}

void setScor(Persoana* p, int scor)
{
	p->scor = scor;
}

Persoana* copyPersoana(Persoana* p) {
	if (p == NULL)
		return;
	Persoana* p_copy = creearePersoana(p->nume, p->prenume, p->scor);
	return p_copy;
}

int equalPersoana(Persoana* p, Persoana* p1)
{
	if (strcmp(p->prenume, p1->prenume)) return 0;
	if (strcmp(p->nume, p1->nume)) return 0;
	return 1;
}

void testPersoana() {
	int scor = 50;
	char* nume = _strdup("NumeTest");
	char* prenume = _strdup("PrenumeTest");
	Persoana* p = creearePersoana(nume, prenume, scor);
	Persoana* p2 = p;
	Persoana* p3 = copyPersoana(p);
	assert(getScor(p) == scor);
	assert(strcmp(getNume(p), nume) == 0);
	assert(strcmp(getPrenume(p), prenume) == 0);
	scor = 100;
	setScor(p, scor);
	assert(getScor(p) == scor);
	Persoana* p1 = creearePersoana(nume, prenume, scor); 
	free(prenume);
	free(nume);
	nume = _strdup("Nume2test");
	prenume = _strdup("Prenume2test");
	setNume(p, nume);
	setPrenume(p, prenume);
	assert(!equalPersoana(p, p1));
	assert(!equalPersoana(p, p3));
	assert(equalPersoana(p, p2));
	free(prenume);
	free(nume);
	distrugePersoana(p);
	distrugePersoana(p1);
	distrugePersoana(p3);
}
