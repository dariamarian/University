#pragma once

#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include "service.h"

typedef struct UI {
	Service* cont;
} UI;

UI* creeareUI(Service* cont);
void distrugeUI(UI* ui);
void adaugarePersoana(UI* ui);
void stergerePersoana(UI* ui);
void modificarePersoana(UI* ui);
void filtrarePersoana(UI* ui);
void sortarePersoana(UI* ui);
void meniu(UI* ui);