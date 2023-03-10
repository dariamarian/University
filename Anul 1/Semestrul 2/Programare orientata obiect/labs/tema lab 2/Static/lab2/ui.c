#include "ui.h"
_CRT_SECURE_NO_WARNINGS;
UI* creeareUI(Service* cont)
{
	UI* ui = (UI*)malloc(sizeof(UI));
	ui->cont = cont;
	return ui;
}

void distrugeUI(UI* ui) {
	free(ui);
}

void afisareEroare(int cod_eroare) {
	switch (cod_eroare) {
	case PERSOANA_EXISTA_DEJA: printf("\nEroare repo: -- Persoana exista deja\n"); break;
	case PERSOANA_NU_EXISTA: printf("\nEroare repo: -- Persoana nu exista\n"); break;
	case STRING_VID: printf("\nEroare la validare: -- String vid.\n"); break;
	case SCOR_INVALID: printf("\nEroare la validare: -- Scor invalid.\n"); break;
	default: break;
	}
}

void adaugarePersoana(UI* ui)
{
	char scor_str[50], nume[50], prenume[50];
	printf("Introduceti persoana:\n");
	printf("Nume: ");
	scanf("%s", &nume);
	printf("Prenume: ");
	scanf("%s", &prenume);
	printf("Scor: ");
	scanf("%s", &scor_str);
	int scor = atoi(scor_str);
	if (scor == -1) { printf("\nScor Invalid.\n"); return; }
	int eroare = adaugare(ui->cont, nume, prenume, scor);
	afisareEroare(eroare);
}

void stergerePersoana(UI* ui)
{
	char nume[50], prenume[50];
	printf("Introduceti nume si prenume.");
	printf("\nNume: ");
	scanf("%s", &nume);
	printf("\nPrenume: ");
	scanf("%s", &prenume);
	int eroare = (int)stergere(ui->cont, nume, prenume);
	afisareEroare(eroare);
}

void modificarePersoana(UI* ui)
{
	char nume_vechi[50], prenume_vechi[50], scor_str[50], nume[50], prenume[50];
	printf("Introduceti nume si prenume existente: \n");
	printf("\nNume: ");
	scanf("%s", &nume_vechi);
	printf("\nPrenume: ");
	scanf("%s", &prenume_vechi);
	printf("Introduceti persoana noua: \n");
	printf("\nNume: ");
	scanf("%s", &nume);
	printf("\nPrenume: ");
	scanf("%s", &prenume);
	printf("\Scor: ");
	scanf("%s", &scor_str);
	int scor = atoi(scor_str);
	if (scor == -1) { printf("\nScor invalid.\n"); return; }
	int eroare = (int)actualizare(ui->cont, nume_vechi, prenume_vechi, nume, prenume, scor);
	afisareEroare(eroare);
}

void afisareLista(Vector* lista) {
	Persoana* p;
	for (int i = 0; i < getDim(lista); ++i) {
		p = getElem(lista, i);

		printf("%s, %s, %d\n", getNume(p), getPrenume(p), getScor(p));
	}
	destroyElems(lista);
	distrugeVector(lista);
}

void filtrarePersoana(UI* ui)
{
	char scor_str[50], nume[50];
	printf("Introduceti filtru: \n");
	printf("Scor: ");
	scanf("%s", &scor_str);
	printf("\nNume: ");
	scanf("%s", &nume);
	printf("\n");
	int scor = atoi(scor_str);
	if (scor == -1) { printf("\nPret Invalid.\n"); return; }
	Vector* lista_f = filtrare(ui->cont, nume, scor);
	afisareLista(lista_f);
}

void sortarePersoana(UI* ui)
{
	char filtru[50];
	printf("Introduceti filtru: ");
	scanf("%s", &filtru);
	Vector* lista_f = sortare(ui->cont, filtru);
	afisareLista(lista_f);
}

void meniu(UI* ui)
{
	printf("====== Meniu ======\n");
	printf("1. Adaugare Persoana.\n");
	printf("2. Stergere Persoana.\n");
	printf("3. Modificare Persoana.\n");
	printf("4. Sortare Persoane.\n");
	printf("5. Filtrare Persoane.\n");
	printf("6. Afisare Persoane.\n");
	printf("0. Iesire.\n");
	char comanda[255];
	while (1) {
		printf("Introduceti comanda: ");
		scanf("%s", &comanda);
		if (*comanda == '1') adaugarePersoana(ui);
		else if (*comanda == '2') stergerePersoana(ui);
		else if (*comanda == '3') modificarePersoana(ui);
		else if (*comanda == '4') sortarePersoana(ui);
		else if (*comanda == '5') filtrarePersoana(ui);
		else if (*comanda == '6') afisareLista(getAllElems(ui->cont));
		else if (*comanda == '0') break;
		else printf("\nComanda Invalida\n");
	}
}