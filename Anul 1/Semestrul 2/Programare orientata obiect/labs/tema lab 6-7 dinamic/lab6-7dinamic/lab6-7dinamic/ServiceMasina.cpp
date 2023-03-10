#include "ServiceMasina.h"
#include <functional>
#include <algorithm>
#include <assert.h>


void ServiceMasina::addMasina(int ID, const string& numar_inmatriculare, const string& producator, const string& model, const string& tip) {
	Masina m{ ID,numar_inmatriculare,producator,model,tip };
	val.validate(m);
	rep.store(m);
}

void ServiceMasina::deleteMasina(int ID) {
	val.validateID(ID);
	rep.del(ID);
}

void ServiceMasina::updateMasina(int ID, const string& numar_inmatriculare, const string& producator, const string& model, const string& tip) {
	Masina m{ ID, numar_inmatriculare,producator,model,tip };
	val.validate(m);
	rep.update(m);
}

void ServiceMasina::cautaMasina(int ID) {
	val.validateID(ID);
	rep.find(ID);
}

DynamicVector<Masina> ServiceMasina::sort(function<bool(const Masina& m1, const Masina& m2)>compareFunction) const
{
	DynamicVector<Masina>v = rep.getAll();
	for (auto& el1 : v)
		for (auto& el2 : v)
			if (compareFunction(el1, el2))
			{
				Masina aux = el1;
				el1 = el2;
				el2 = aux;
			}
	return v;
}

DynamicVector<Masina> ServiceMasina::filtreaza(function<bool(const Masina&)> fct) {
	DynamicVector<Masina> rez;
	DynamicVector<Masina> v1;
	v1 = rep.getAll();
	for (const auto& Masina : v1) {
		if (fct(Masina)) {
			rez.add(Masina);
		}
	}
	return rez;
}

DynamicVector<Masina> ServiceMasina::filtrareProducator(const string& producator) {
	return filtreaza([producator](const Masina& m) {
		return m.getProducator() == producator;
		});
}

DynamicVector<Masina> ServiceMasina::filtrareTip(const string& tip) {
	return filtreaza([tip](const Masina& m) {
		return m.getTip() == tip;
		});
}

void testAdaugaCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "a", "a", "a", "a");
	assert(ctr.getAll().size() == 1);
	//adaug ceva invalid
	try {
		ctr.addMasina(-3, "", "", "", "");
		//assert(false);
	}
	catch (ValidateException&) {
		assert(true);
	}
}
void testStergeCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "a", "a", "a", "a");
	ctr.addMasina(40, "a", "a", "a", "a");
	assert(ctr.getAll().size() == 2);
	ctr.deleteMasina(20);
	assert(ctr.getAll().size() == 1);
}

void testModificaCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "a", "a", "a", "a");
	ctr.addMasina(40, "a", "a", "a", "a");
	assert(ctr.getAll().size() == 2);
	ctr.updateMasina(20, "a2", "a2", "a2", "a2");
	assert(ctr.getAll().size() == 2);
}

void testCautaCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "a", "a", "a", "a");
	ctr.addMasina(40, "a", "a", "a", "a");
	assert(ctr.getAll().size() == 2);
	ctr.cautaMasina(40);
	assert(ctr.getAll().size() == 2);
}

void testFiltrare() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "a", "b", "c", "d");
	ctr.addMasina(35, "a1", "b", "c1", "d1");
	ctr.addMasina(80, "a2", "b2", "c2", "d2");
	assert(ctr.filtrareProducator("b").size() == 2);
	assert(ctr.filtrareTip("d2").size() == 1);
}
/*
void testSortare() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "BH15DRM", "b", "c", "tipS");
	ctr.addMasina(45, "CJ21DRM", "b", "d", "tipC");
	ctr.addMasina(99, "BH13VDT", "b2", "c2", "tipA");

	auto firstM = ctr.sortByNumar()[0];
	assert(firstM.getNumar() == "BH13VDT");

	firstM = ctr.sortByTip()[0];
	assert(firstM.getTip() == "tipA");

	firstM = ctr.sortByProducatorModel()[0];
	assert(firstM.getModel() == "c");
}
*/

void testSortare() {

	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina serv{ rep,val };

	constexpr int id = 1;
	string nrI = "iai";
	string prod = "pap";
	string nrI2 = "aaa";
	string model = "mam";
	string tip = "tit";

	serv.addMasina(id, nrI, prod, model, tip);
	serv.addMasina(2, nrI2, prod, model, tip);
	DynamicVector<Masina>v = serv.sort([](const Masina& m1, const Masina& m2) {if (m1.getNumar() < m2.getNumar()) return true; else return false; });
	assert(v.size() == 2);
	v = serv.sort([](const Masina& m1, const Masina& m2) {if (m1.getTip() < m2.getTip()) return true; else return false; });
	assert(v.size() == 2);
	v = serv.sort([](const Masina& m1, const Masina& m2) {
		if (m1.getProducator() < m2.getProducator())
			return true;
		else if (m1.getProducator() == m2.getProducator())
			if (m1.getModel() < m2.getModel())
				return true;
			else return false;
		else return false;
		});
	assert(v.size() == 2);
}
void testCtr() {
	testAdaugaCtr();
	testStergeCtr();
	testModificaCtr();
	testCautaCtr();
	testFiltrare();
	testSortare();
}
