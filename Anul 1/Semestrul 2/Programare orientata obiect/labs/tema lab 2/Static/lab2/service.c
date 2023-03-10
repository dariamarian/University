#include "service.h"

Service* creeareService(ListaPersoane* lista, int(*validare)(Persoana*))
{
	Service* cont = (Service*)malloc(sizeof(Service));
	cont->lista = lista;
	cont->validare = validare;
	return cont;
}

void destroyElemsServ(Service* cont) {
	destroyElemsRepo(cont->lista);
}

void distrugeService(Service* cont) {
	distrugeLista(cont->lista);
	free(cont);
}

int getDimCont(Service* cont)
{
	return getDimList(cont->lista);
}

Vector* getAllElems(Service* cont) {
	return getVect(cont->lista);
}

ListaPersoane* getLista(Service* cont)
{
	return cont->lista;
}

int adaugare(Service* cont, char* nume, char* prenume, int scor)
{
	Persoana* p = creearePersoana(nume, prenume, scor);
	int val = cont->validare(p);
	if (val) {
		distrugePersoana(p);
		return val;
	}
	val = adaugaPersoana(cont->lista, p);
	if (val) {
		distrugePersoana(p);
		return val;
	}
	return SUCCES;
}

Persoana* stergere(Service* cont, char* nume, char* prenume)
{
	Persoana* p = creearePersoana(nume, prenume, 1);
	int val = cont->validare(p);
	if (val) {
		distrugePersoana(p);
		return (Persoana*)val;
	}
	Persoana* to_delete = stergePersoana(cont->lista, p);
	distrugePersoana(p);
	return to_delete;
}

Persoana* actualizare(Service* cont, char* nume, char* prenume, char* nume_nou, char* prenume_nou, int scor_nou)
{
	Persoana* p_nou = creearePersoana(nume_nou, prenume_nou, scor_nou);
	Persoana* p_vechi = creearePersoana(nume, prenume, 1);
	int val = cont->validare(p_vechi);
	if (val) {
		distrugePersoana(p_nou);
		distrugePersoana(p_vechi);
		return (Persoana*)val;
	}
	val = cont->validare(p_nou);
	if (val) {
		distrugePersoana(p_nou);
		distrugePersoana(p_vechi);
		return (Persoana*)val;
	}
	Persoana* to_delete = modificaPersoana(cont->lista, p_vechi, p_nou);
	distrugePersoana(p_nou);
	distrugePersoana(p_vechi);
	return to_delete;
}

Vector* filtrare(Service* cont, char* nume, int scor)
{
	Persoana* p;
	char* nume_temp;
	Vector* lista_filtrata = creeareVector(0, equalPersoana, copyPersoana, distrugePersoana);

	for (int i = 0; i < getDimCont(cont); ++i) {
		p = getPersoana(cont->lista, i);
		nume_temp = getNume(p);
		if (strlen(nume)) if (strcmp(nume, nume_temp)) continue;
		if (scor > 0) if (getScor(p) != scor) continue;
		adaugaElem(lista_filtrata, p);
	}
	return lista_filtrata;
}

void swappersoane(Vector* lista, int poz, int poz1) {
	Persoana* p1 = getElem(lista, poz1);
	Persoana* p = setElem(lista, poz, p1);
	setElem(lista, poz1, p);
}

int verificaSortare(Vector* vect, char* filtru) {
	if (strcmp(filtru, "nume") && strcmp(filtru, "scor")) return FILTRU_INVALID;
	Persoana* p, * p1;
	for (int i = 0; i < getDim(vect) - 1; ++i) {
		p = getElem(vect, i);
		p1 = getElem(vect, i + 1);
		if (!strcmp(filtru, "nume"))
		{
			char* nume = getNume(p);
			char* nume1 = getNume(p1);
			if (strcmp(nume, nume1) > 0) return NESORTAT;
		}
		else if (!strcmp(filtru, "scor")) if (getScor(p) > getScor(p1)) return NESORTAT;
	}
	return SORTAT;
}

Vector* sortare(Service* cont, char* filtru)
{
	Vector* lista_sortata = getVect(cont->lista);
	Persoana* p, * p1;
	char* nume, * nume1;
	for (int i = 0; i < getDim(lista_sortata) - 1; ++i) {
		for (int j = i + 1; j < getDim(lista_sortata); ++j) {
			p = getElem(lista_sortata, i);
			p1 = getElem(lista_sortata, j);
			if (!strcmp(filtru, "nume")) {
				nume = getNume(p);
				nume1 = getNume(p1);
				if (strcmp(nume1, nume) < 0) swappersoane(lista_sortata, i, j);
			}
			else if (!strcmp(filtru, "scor")) {
				if (getScor(p1) < getScor(p)) swappersoane(lista_sortata, i, j);
			}
			else return lista_sortata;
		}
	}
	return lista_sortata;
}

void testService()
{
	ListaPersoane* list = creeareLista();
	Vector* list_f;
	Service* cont = creeareService(list, validator);
	assert(adaugare(cont,"numeTest", "prenumeTest", 50) == SUCCES);
	assert(adaugare(cont,"MnumeTest1", "prenumeTest1", 70) == SUCCES);
	assert(adaugare(cont,"numeTest", "prenumeTest", 50) == PERSOANA_EXISTA_DEJA);
	assert(adaugare(cont,"numeTest1", "prenumeTest1", 105) == SCOR_INVALID);
	assert(getDimCont(cont) == 2);
	Persoana* temp = actualizare(cont, "numeTest2", "prenumeTest2", "numeTestModificat", "prenumeTestModificat", 75);
	assert((int)temp != PERSOANA_NU_EXISTA);
	Persoana* temp2 = stergere(cont, "numeTest3", "prenumeTest3");
	assert((int)temp2 != PERSOANA_NU_EXISTA);
	assert(adaugare(cont, "MnumeTest", "prenumeTest", 10) == SUCCES);
	assert(adaugare(cont, "numeTest2", "prenumeTest2", 45) == SUCCES);
	list_f = filtrare(cont, "M", 0);
	assert(getDim(list_f) == 1);
	distrugeVector(list_f);
	list_f = filtrare(cont, 0, 45);
	assert(getDim(list_f) == 1);
	distrugeVector(list_f);
	list_f = sortare(cont, "prenume");
	assert(verificaSortare(list_f, "prenume") == FILTRU_INVALID);
	destroyElems(list_f);
	distrugeVector(list_f);
	list_f = sortare(cont, "scor");
	assert(verificaSortare(list_f, "scor") == SORTAT);
	destroyElems(list_f);
	distrugeVector(list_f);
	list_f = sortare(cont, "nume");
	assert(verificaSortare(list_f, "nume") == SORTAT);
	destroyElems(list_f);
	distrugeVector(list_f);
	ListaPersoane* copie_lista = getLista(cont);
	Vector* copie_vector = getAllElems(cont);
	destroyElems(copie_vector);
	distrugeVector(copie_vector);
	Persoana* poz_c, * poz;
	for (int i = 0; i < getDimList(copie_lista); ++i) {
		poz_c = getPersoana(copie_lista, i);
		poz = getPersoana(cont->lista, i);
		assert(equalPersoana(poz_c, poz));
	}
	distrugePersoana(temp);
	distrugePersoana(temp2);
	destroyElemsRepo(cont->lista);
	distrugeService(cont);
}