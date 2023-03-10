#include "ServiceMasina.h"
#include <functional>
#include <algorithm>
#include <assert.h>
#include <iostream>
#include <iterator>
#include <random>
#include <fstream>
#include "Exceptii.h"
using namespace std;

void ServiceSpalat::addSpalat(std::string nrI)
{
	std::vector < Masina > masini = rep.FindCarsAfterNumar(nrI);
	if (masini.size() == 0)
	{
		throw MasinaRepoException("Nu exista nicio masina cu acest numar.\n");
	}
	sp.add(masini[0]);
}

void ServiceSpalat::clearSpalatServ()
{
	sp.clear();
}
int ServiceSpalat::addRandom(int numberToAdd)
{
	int rndNr = 1;
	int cateAdaugate = 0;

	string prod;
	string model;
	string tip;
	string nrI;

	while (cateAdaugate < numberToAdd) {

		std::mt19937 mt{ std::random_device{}() };
		int numarMasini = (int)numar_masiniSrv();
		std::uniform_int_distribution<> dist(numarMasini, numarMasini + numberToAdd * 2);

		try {
			rndNr = dist(mt);
			prod = "Prod" + to_string(rndNr);
			model = "Model" + to_string(rndNr);
			tip = "Tip" + to_string(rndNr);
			nrI = "Numar Inm." + to_string(rndNr);

			addMasinaSP(rndNr, nrI, prod, model, tip);
			cateAdaugate++;
		}
		catch (const exception&) {
			continue;
		}
	}
	return cateAdaugate;
}


vector<Masina> ServiceMasina::generalSort(bool(*maiMicF)(const Masina&, const Masina&)) {
	vector<Masina> v{ rep.getAll() };//fac o copie	
	for (size_t i = 0; i < v.size(); i++) {
		for (size_t j = i + 1; j < v.size(); j++) {
			if (!maiMicF(v[i], v[j])) {
				//interschimbam
				Masina aux = v[i];
				v[i] = v[j];
				v[j] = aux;
			}
		}
	}
	return v;
}

void ServiceSpalat::addMasinaSP(int ID, const string& numar_inmatriculare, const string& producator, const string& model, const string& tip) {
	Masina m{ ID,numar_inmatriculare,producator,model,tip };
	val.validate(m);
	sp.add(m);
}

void ServiceMasina::addMasina(int ID,const string& numar_inmatriculare, const string& producator, const string& model, const string& tip) {
	Masina m{ ID,numar_inmatriculare,producator,model,tip };
	val.validate(m);
	rep.store(m);
	undoActions.push_back(make_unique<UndoAdd>(m, rep));
}

void ServiceMasina::deleteMasina(int ID) {
	Masina m = rep.findCar(ID);
	val.validateID(ID);
	rep.del(ID);
	undoActions.push_back(make_unique<UndoDelete>(m, rep));
}

void ServiceMasina::updateMasina(int ID,const string& numar_inmatriculare, const string& producator, const string& model, const string& tip) {
	Masina m{ID, numar_inmatriculare,producator,model,tip };
	val.validate(m);

	try {
		Masina m_init = rep.findCar(ID);
		undoActions.push_back(make_unique<UndoUpdate>(m_init, rep));
	}
	catch (const exception&) {}

	rep.update(m);
}

const Masina& ServiceMasina::findCarSrv(int id) const
{
	return rep.findCar(id);
}

void ServiceMasina::cautaMasina(int ID) {
	val.validateID(ID);
	rep.find(ID);
}

vector<Masina> ServiceMasina::sortByNumar() {
	auto copyAll = rep.getAll();
	std::sort(copyAll.begin(), copyAll.end(), cmpNumar);
	return copyAll;
	//return generalSort(cmpType);
}

vector<Masina> ServiceMasina::sortByTip() {
	return generalSort(cmpTip);
}


vector<Masina> ServiceMasina::sortByProducatorModel() {
	return generalSort([](const Masina& m1, const Masina& m2) {
		if (m1.getProducator() == m2.getProducator()) {
			return m1.getModel() < m2.getModel();
		}
		return m1.getProducator() < m2.getProducator();
		});
}

vector<Masina> ServiceMasina::filtreaza(function<bool(const Masina&)> fct) {
	vector<Masina> rez;
	for (const auto& Masina : rep.getAll()) {
		if (fct(Masina)) {
			rez.push_back(Masina);
		}
	}
	return rez;
}

vector<Masina> ServiceMasina::filtrareProducator(const string& producator) {
	return filtreaza([producator](const Masina& m) {
		return m.getProducator() == producator;
		});
}

vector<Masina> ServiceMasina::filtrareTip(const string& tip) {
	return filtreaza([tip](const Masina& m) {
		return m.getTip() == tip;
		});
}
size_t ServiceSpalat::numar_masiniSrv()
{
	return sp.numar_masini();
}

const std::vector < Masina >& ServiceSpalat::getSpalat() const
{
	return sp.getSpalat();
}

void ServiceMasina::undo()
{
	if (undoActions.size() == 0)
		throw exception { "Nu se poate efectua undo!\n" };
	undoActions.back()->doUndo();
	undoActions.pop_back();

}
void testAdaugaCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "ab", "ab", "ab", "ab");
	assert(ctr.getAll().size() == 1);
	//adaug ceva invalid
	/*
	try {
		ctr.addMasina(-3, "", "", "", "");
		assert(false);
	}
	catch (exception&) {
		assert(true);
	}*/
}
void testStergeCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "ab", "ab", "ab", "ab");
	ctr.addMasina(40, "ab", "ab", "ab", "ab");
	assert(ctr.getAll().size() == 2);
	ctr.deleteMasina(20);
	assert(ctr.getAll().size() == 1);
}

void testModificaCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "ab", "ab", "ab", "ab");
	ctr.addMasina(40, "ab", "ab", "ab", "ab");
	assert(ctr.getAll().size() == 2);
	ctr.updateMasina(20,"ab2","ab2","ab2","ab2");
	assert(ctr.getAll().size() == 2);
}

void testCautaCtr() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20, "ab", "ab", "ab", "ab");
	ctr.addMasina(40, "ab", "ab", "ab", "ab");
	assert(ctr.getAll().size() == 2);
	ctr.cautaMasina(40);
	assert(ctr.getAll().size() == 2);
}

void testFiltrare() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20,"a0", "b0", "c0", "d0");
	ctr.addMasina(35,"a1", "b0", "c1", "d1");
	ctr.addMasina(80,"a2", "b2", "c2", "d2");
	assert(ctr.filtrareProducator("b0").size() == 2);
	assert(ctr.filtrareTip("d2").size() == 1);
}

void testSortare() {
	MasinaRepo rep;
	MasinaValidator val;
	ServiceMasina ctr{ rep,val };
	ctr.addMasina(20,"BH15DRM", "bb", "cc", "tipS");
	ctr.addMasina(45,"CJ21DRM", "bb", "dd", "tipC");
	ctr.addMasina(99,"BH13VDT", "bb2", "cc2", "tipA");

	auto firstM = ctr.sortByNumar()[0];
	assert(firstM.getNumar() == "BH13VDT");

	firstM = ctr.sortByTip()[0];
	assert(firstM.getTip() == "tipA");

	firstM = ctr.sortByProducatorModel()[0];
	assert(firstM.getModel() == "cc");
}

void testSpalatServ()
{
	MasinaRepo repo;
	MasinaValidator valid;
	Spalat sp;
	ServiceSpalat serv(repo, sp, valid);
	Masina masina1(1, "nr", "pr", "md", "tip");
	Masina masina2(2, "nr2", "pr2", "md2", "tip2");
	Masina masina3(3, "nr3", "pr3", "md3", "tip3");
	Masina masina4(4, "nr4", "pr4", "md4", "tip4");
	std::vector < Masina > masini;

	repo.store(masina1);
	repo.store(masina2);
	repo.store(masina3);
	repo.store(masina4);

	serv.addSpalat("nr");
	serv.addSpalat("nr2");
	serv.addSpalat("nr3");

	masini = serv.getSpalat();
	assert(masini.size() == 3);

	serv.clearSpalatServ();
	masini = serv.getSpalat();
	assert(masini.size() == 0);

	serv.addRandom(10);
	masini = serv.getSpalat();
	assert(masini.size() > 5);
}

void testUndo()
{
	MasinaRepo repo;
	MasinaValidator valid;
	ServiceMasina ctr{ repo,valid };
	
	ctr.addMasina(1, "iai", "pap", "mam", "tit");
	ctr.undo();
	assert(repo.size() == 0);

	ctr.addMasina(1, "iai", "pap", "mam", "tit");
	ctr.updateMasina(1, "sad", "pap", "mam", "tit");
	ctr.undo();
	assert(ctr.findCarSrv(1).getNumar() == "iai");
	
	ctr.deleteMasina(1);
	ctr.undo();
	assert(repo.size() == 1);
	
	/*
	try {
		ctr.undo();
		ctr.undo();
	}
	catch (const Exceptie& ex) {
		assert(ex.getMessage() == "Nu se poate efectua undo!\n");
	}*/
}

void testCtr() {
	testAdaugaCtr();
	testStergeCtr();
	testModificaCtr();
	testCautaCtr();
	testFiltrare();
	testSortare();
	testSpalatServ();
	testUndo();
}
