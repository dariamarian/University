#pragma once
#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <assert.h>

typedef struct {
	char* nume, * prenume;
	int scor;
}Persoana;

Persoana* creeazaPersoana(char* nume, char* prenume, int scor);
void distrugePersoana(Persoana* p);
Persoana* copyPersoana(Persoana* p);
char* getNume(Persoana* p);
char* getPrenume(Persoana* p);
int getScor(Persoana* p);
void setNume(Persoana* p, char* nume);
void setPrenume(Persoana* p, char* prenume);
void setScor(Persoana* p, int scor);
int equalPersoana(Persoana* p, Persoana* p1);
void testPersoana();