#include "IteratorMDO.h"
#include "MDO.h"

IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
	curent = { 0, 1 };
	moveToNext();
}

void IteratorMDO::moveToNext()
{
	//teta(n)
	while (curent.first < dict.size && dict.tabela[curent.first].second == NULL_TELEM)
	{
		curent.first = curent.first + 1;
	}
}

void IteratorMDO::prim() {
	//teta(n)
	curent = { 0, 1 };
	moveToNext();
}

void IteratorMDO::urmator() {
	//teta(1)
	if (curent.first >= dict.size)
	{
		throw std::exception();
		return;
	}
	curent.second = curent.second + 1;
	curent.first = curent.first + 1;
}

bool IteratorMDO::valid() const {
	//teta(1)
	return (curent.first < dict.size);
}

// Transformati iteratorul pentru a putea face k pasi în urma. Adaugati operatia urmatoare în 
// clasa IteratorMultidictionarOrdonat :
// 
// muta cursorul iteratorului a.i. sa refere a k-a pereche în urma, incepand de la cea curenta. 
// Iteratorul devine nevalid în cazul în care exista mai putin de k perechi anterioare perechii 
// curente in multidictionar. 
// arunca exceptie in cazul in care iteratorul este nevalid sau k este zero ori negativ. 

void IteratorMDO::revinoKPasi(int k)
{
	//complexitate generala:O(n)
	if(k <= 0)
		throw std::exception();
	if(k > dict.count)
		throw std::exception();
	if(k > curent.first)
		throw std::exception();
	for (int i = 0; i < k; i++)
	{
		curent.first = curent.first - 1;
		curent.second = curent.second - 1;
	}
}

TElem IteratorMDO::element() const {
	//teta(1)
	if (curent.first >= dict.size)
	{
		return pair <TCheie, TValoare>(-1, -1);
	}
	return dict.tabela[curent.first];
}
