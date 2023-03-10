#include "Matrice.h"

#include <exception>

using namespace std;


Matrice::Matrice(int m, int n) {
    if (n <= 0 || m <= 0) {
        throw exception();
    }
    this->cap = m * n;
    this->dim = 0;
    this->primLiber = 0;
    this->urm = new int[cap];
    this->ant = new int[cap];
    this->e = new TValue[cap];
    this->primul = -1;

    for (int i = 0; i < cap; i++) {
        this->urm[i] = i + 1;
    }

    this->prim = nullptr;
    this->linii = m;
    this->coloane = n;

}

Matrice::~Matrice(){
    /*	 teta(1)	*/
    delete[] this->urm;
    delete[] this->ant;
    delete[] this->e;
}


int Matrice::nrLinii() const {
    /* teta(1) */
    return this->linii;
}


int Matrice::nrColoane() const {
    /* teta(1) */
    return this->coloane;
}


TElem Matrice::element(int i, int j) const {
    /* O(nrElem) */
    if (i < 0 || i >= this->linii || j < 0 || j >= this->coloane)
        throw exception();

    if (this->prim == nullptr)
        return NULL_TELEMENT;
    Nod* curent = prim;
    while (curent != nullptr) {
        if (curent->el.first.first == i && curent->el.first.second == j)
            return curent->el.second;
        curent = curent->urm;
    }
    return NULL_TELEMENT;
}

TElem Matrice::modifica(int i, int j, TElem e) {
    /* O(nrElem) */
    if (i < 0 || i >= this->linii || j < 0 || j >= this->coloane)
        throw exception();

    int ex_value = element(i, j);
    if (ex_value != NULL_TELEMENT) { // daca elementul se gaseste in lista, trebuie modificat
        Nod* curent = prim;
        while (curent != nullptr) {
            if (curent->el.first.first == i && curent->el.first.second == j) {
                curent->el.second = e;
                return ex_value;
            }
            curent = curent->urm;
        }
    }
    else { // altfel trebuie adaugat
        this->dim++;
        if (this->prim == nullptr) { //elementul trebuie adaugat pe prima pozitie
            this->prim = new Nod;
            adauga(this->prim->el);
            this->prim->el.first.first = i;
            this->prim->el.first.second = j;
            this->prim->el.second = e;
            return ex_value;
        }
        else {
            Nod* curent = prim;
            Nod* anterior = prim;
            while (curent != nullptr) {
                if (curent->el.first.first > i || (curent->el.first.first == i && curent->el.first.second > j)) {
                    if (prim == curent) { //elementul trebuie adaugat pe prima pozitie din cauza relatiei
                        Nod* nou = new Nod;
                        adauga(nou->el);
                        nou->el.first.first = i;
                        nou->el.first.second = j;
                        nou->el.second = e;
                        nou->urm = prim;
                        prim->ant = nou;
                        prim = nou;
                    }
                    else { // elementul trebuie adaugat intre doua poz existente
                        Nod* nou = new Nod;
                        adauga(nou->el);
                        nou->el.first.first = i;
                        nou->el.first.second = j;
                        nou->el.second = e;
                        nou->ant = curent->ant;
                        curent->ant->urm = nou;
                        nou->urm = curent;
                        curent->ant = nou;
                    }
                    return ex_value;
                }
                anterior = curent;
                curent = curent->urm;
            }
            if (curent == nullptr) { // elementul trebuie adaugat pe ultima pozitie
                Nod* nou = new Nod;
                adauga(nou->el);
                nou->el.first.first = i;
                nou->el.first.second = j;
                nou->el.second = e;
                anterior->urm = nou;
                nou->ant = anterior;
                return ex_value;
            }
        }
    }
}

int Matrice::aloca() {
    /* teta(1) */
    int i = this->primLiber;
    this->primLiber = this->urm[primLiber];
    return i;
}

void Matrice::dealoca(int i) {
    /* teta(1) */
    this->urm[i] = this->primLiber;
    this->primLiber = i;
}

int Matrice::creeazaNod(TValue e) {
    /* teta(1) */
    int i = aloca();
    if (i != -1) {
        this->e[i] = e;
        this->urm[i] = -1;
    }
    return i;
}

void Matrice::adauga(TValue e) {
    /* teta(1) */
    int i = creeazaNod(e);
    if (i != -1) {
        ant[i] = primul;
        urm[i] = primul;
        primul = i;
    }
}

// redimensioneaza o matrice la o anumita dimensiune. In cazul in care dimensiunile sunt mai mici decat 
// dimensiunile actuale, elemente de pe pozitiile care nu mai sunt existente vor disparea. In cazul in care 
// dimensiunile sunt mai mari decat dimensiunile actuale, toate elementele noi (de pe pozitii anterior inexistente)
// vor fi  NULL_TELEM.  

// arunca exceptie in cazul in care noile dimensiuni sunt negative.  

void Matrice::redimensioneaza(int numarNouLinii, int numarNouColoane)
{
    /* O(nrElem) */
    if (numarNouLinii < 0 || numarNouColoane < 0)
        throw exception();
    int* urm_new;
    int* ant_new;
    TValue* e_new;

    this->cap = numarNouColoane * numarNouLinii;
    linii = numarNouLinii;
    coloane = numarNouColoane;
    this->dim = 0;
    e_new = new TValue[cap];
    urm_new = new int[cap];
    ant_new = new int[cap];

    for (int i = 0; i < cap; i++) {
        e_new[i] = e[i];
        urm_new[i] = urm[i];
        ant_new[i] = ant[i];
    }

    delete[] e;
    delete[] urm;
    delete[] ant;


    this->e = e_new;
    this->urm = urm_new;
    this->ant = ant_new;
    this->prim = nullptr;
}