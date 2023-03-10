#include <assert.h>

#include "MDO.h"
#include "IteratorMDO.h"

#include <exception>
#include <vector>
#include <iostream>

using namespace std;

bool relatie1(TCheie cheie1, TCheie cheie2) {
    if (cheie1 <= cheie2) {
        return true;
    }
    else {
        return false;
    }
}

void testAll() {
    MDO dictOrd = MDO(relatie1);
    assert(dictOrd.dim() == 0);
    assert(dictOrd.vid()); 
    dictOrd.adauga(1, 2);
    dictOrd.adauga(1, 3);
    assert(dictOrd.dim() == 2);
    assert(!dictOrd.vid());
    vector<TValoare> v = dictOrd.cauta(1);
    assert(v.size() == 2);
    v = dictOrd.cauta(3);
    assert(v.size() == 0);
    IteratorMDO it = dictOrd.iterator();
    dictOrd.adauga(1, 9);
    dictOrd.adauga(2, 8);
    dictOrd.adauga(3, 6);
    it.prim();
    int i = 0;
    while (it.valid()) {
        i++;
        if (i == 4)
            return;
        TElem e = it.element();
        it.urmator();
    }
    it.revinoKPasi(2);
    TElem elem = it.element();
    assert(elem.first == 1 && elem.second == 3);

    assert(dictOrd.sterge(1, 2) == true);
    assert(dictOrd.sterge(1, 3) == true);
    assert(dictOrd.sterge(2, 1) == false);
    assert(dictOrd.sterge(1, 9) == true);
    assert(dictOrd.sterge(2, 8) == true);
    assert(dictOrd.sterge(3, 6) == true);
    assert(dictOrd.vid());
}

