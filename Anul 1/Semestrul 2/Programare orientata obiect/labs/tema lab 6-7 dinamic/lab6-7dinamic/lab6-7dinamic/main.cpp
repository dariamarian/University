#include "UI.h"
#include "ServiceMasina.h"
#include "RepoMasina.h"
#include "ValidatorMasina.h"
#include <vector>
#include <assert.h>

void adaugaCateva(ServiceMasina& ctr) {
	ctr.addMasina(1, "BH15DRM", "Volkswagen", "Touareg", "bun");
	ctr.addMasina(2, "BH14SGD", "Ford", "Focus", "vechi");
	ctr.addMasina(3, "HD26PAZ", "BMW", "I5", "nou");
	ctr.addMasina(4, "CJ13MGN", "Mercedes", "E-Class", "ca nou");
	ctr.addMasina(5, "B27INF", "Nissan", "Juke", "vechi");
	ctr.addMasina(6, "SV99IDK", "Mercedes", "S-Class", "in stare buna");
}

/*
  Functie folosit in teste
  primeste vector prin valoare -> copy constructor
  returneaza prin valoare -> copy constructor sau move constructor
*/
template <typename MyVector>
MyVector testCopyIterate(MyVector v) {
	int totalID = 0;
	for (auto el : v) {
		totalID += el.getID();
	}
	Masina m{ totalID,"total","tt","t","tttt"};
	v.add(m);
	return v;
}

template <typename MyVector>
void addCars(MyVector& v, int cate) {
	for (int i = 0; i < cate; i++) {
		Masina m{i, std::to_string(i) + "_numar",std::to_string(i) + "_producator",std::to_string(i) + "_model", std::to_string(i) + "_tip" };
		v.add(m);
	}
}

/*
Testare constructori / assignment
E template pentru a refolosi la diferite variante de VectorDinamic din proiect
*/
template <typename MyVector>
void testCreateCopyAssign() {
	MyVector v;//default constructor
	addCars(v, 100);
	assert(v.size() == 100);
	assert(v[50].getNumar() == "50_numar");

	MyVector v2{ v };//constructor de copiere
	assert(v2.size() == 100);
	assert(v2[50].getTip() == "50_tip");

	MyVector v3;//default constructor
	v3 = v;//operator=   assignment
	assert(v3.size() == 100);
	assert(v3[50].getTip() == "50_tip");

	auto v4 = testCopyIterate(v3);
	assert(v4.size() == 101);
	assert(v4[50].getTip() == "50_tip");
}

/*
  Test pentru move constructor si move assgnment
*/
template <typename MyVector>
void testMoveConstrAssgnment() {
	std::vector<MyVector> v;
	//adaugam un vector care este o variabila temporara
	//se v-a apela move constructor
	v.push_back(MyVector{});

	//inseram, la fel se apeleaza move costructor de la vectorul nostru
	v.insert(v.begin(), MyVector{});

	assert(v.size() == 2);

	MyVector v2;
	addCars(v2, 50);

	v2 = MyVector{};//move assignment

	assert(v2.size() == 0);

}
template <typename MyVector>
void testAll2() {
	testCreateCopyAssign<MyVector>();
	testMoveConstrAssgnment<MyVector>();
}

void testAll() {
	testeRepo();
	testCtr();
	testValidator();
}

int main() {
	//testAll2<VectDinNewDelete>();
	//testAll2<VectDinSmartPointer>();
	//testAll2<VectDinNewDeleteT<Masina>>();
	testAll2<DynamicVector<Masina>>();
	testAll();
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	adaugaCateva(ctr);//adaug cateva masini
	ConsolUI ui{ ctr };
	ui.start();
	_CrtDumpMemoryLeaks();
	return 0;
}