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

vector<Masina> ServiceMasina::generalSort(bool(*maiMicF)(const Masina&, const Masina&)) {
	//auto v = new vector<Masina>;
	vector<Masina> v;//fac o copie	
	for (const auto& Masina : rep.getAll()) {
		v.push_back(Masina);
	}
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

void ServiceMasina::addMasina(int ID,const string& numar_inmatriculare, const string& producator, const string& model, const string& tip) {
	Masina m{ ID,numar_inmatriculare,producator,model,tip };
	//val.validate(m);
	rep.store(m);
	undoActions.push_back(make_unique<UndoAdd>(m, rep));
}

void ServiceMasina::deleteMasina(int ID) {
	Masina m = rep.findCar(ID);
	//val.validateID(ID);
	rep.del(ID);
	undoActions.push_back(make_unique<UndoDelete>(m, rep));
}

void ServiceMasina::updateMasina(int ID,const string& numar_inmatriculare, const string& producator, const string& model, const string& tip) {
	Masina m{ID, numar_inmatriculare,producator,model,tip };
	//val.validate(m);

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
	//val.validateID(ID);
	rep.find(ID);
}

vector<Masina> ServiceMasina::sortByNumar() {
	//auto copyAll = rep.getAll();
	//std::sort(copyAll.begin(), copyAll.end(), cmpNumar);
	//return copyAll;
	return generalSort(cmpNumar);
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
	//auto rez = new vector<Masina>;
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

void ServiceMasina::undo()
{
	if (undoActions.size() == 0)
		throw MasinaRepoException { "Nu se poate efectua undo!\n" };
	undoActions.back()->doUndo();
	undoActions.pop_back();

}
std::vector<Masina> ServiceMasina::addToCos(int ID) {
	Masina m = rep.findCar(ID);
	cos.adauga(m);
	return cos.lista();
}

std::vector<Masina> ServiceMasina::addRandom(int cate) {
	cos.umple(cate);
	return cos.lista();
}

std::vector<Masina> ServiceMasina::golesteCos() {
	cos.goleste();
	return cos.lista();
}

std::vector<Masina> ServiceMasina::toateDinCos() {
	return cos.lista();
}

void ServiceMasina::exportSrv(const string& file) 
{
	vector <Masina> copie = toateDinCos();
	ofstream myfile;
	myfile.open(string(file));
	myfile << "Id,Numar de inmatriculare,Producator,Model,Tip\n";
	for (auto& m : copie) {
		myfile << m.getID() << "," << m.getNumar() << "," << m.getProducator()
			<< "," << m.getModel() << "," << m.getTip() << '\n';
	}
	myfile.close();
}

void testAdaugaCtr() {
	MasinaRepo rep;
	//MasinaValidator val;
	ServiceMasina ctr{ rep };
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
	//MasinaValidator val;
	ServiceMasina ctr{ rep };
	ctr.addMasina(20, "ab", "ab", "ab", "ab");
	ctr.addMasina(40, "ab", "ab", "ab", "ab");
	assert(ctr.getAll().size() == 2);
	ctr.deleteMasina(20);
	assert(ctr.getAll().size() == 1);
}

void testModificaCtr() {
	MasinaRepo rep;
	//MasinaValidator val;
	ServiceMasina ctr{ rep };
	ctr.addMasina(20, "ab", "ab", "ab", "ab");
	ctr.addMasina(40, "ab", "ab", "ab", "ab");
	assert(ctr.getAll().size() == 2);
	ctr.updateMasina(20,"ab2","ab2","ab2","ab2");
	assert(ctr.getAll().size() == 2);
}

void testCautaCtr() {
	MasinaRepo rep;
	//MasinaValidator val;
	ServiceMasina ctr{ rep };
	ctr.addMasina(20, "ab", "ab", "ab", "ab");
	ctr.addMasina(40, "ab", "ab", "ab", "ab");
	assert(ctr.getAll().size() == 2);
	ctr.cautaMasina(40);
	assert(ctr.getAll().size() == 2);
}

void testFiltrare() {
	MasinaRepo rep;
	//MasinaValidator val;
	ServiceMasina ctr{ rep };
	ctr.addMasina(20,"a0", "b0", "c0", "d0");
	ctr.addMasina(35,"a1", "b0", "c1", "d1");
	ctr.addMasina(80,"a2", "b2", "c2", "d2");

	auto out = ctr.filtrareProducator("b0");
	assert(out.size() == 2);

	out = ctr.filtrareTip("d2");
	assert(out.size() == 1);
}

void testSortare() {
	MasinaRepo rep;
	//MasinaValidator val;
	ServiceMasina ctr{ rep };
	ctr.addMasina(20,"BH15DRM", "bb", "cc", "tipS");
	ctr.addMasina(45,"CJ21DRM", "bb", "dd", "tipA");
	ctr.addMasina(99,"BH13VDT", "bb2", "cc2", "tipC");

	auto out = ctr.sortByNumar();
	assert(out.at(0).getProducator() == "bb2");

	out = ctr.sortByTip();
	assert(out.at(0).getProducator() == "bb");

	out = ctr.sortByProducatorModel();
	assert(out.at(0).getTip() == "tipS");
}
/*
void testSpalatServ()
{
	MasinaRepo repo;
	//MasinaValidator valid;
	Spalat sp;
	ServiceSpalat serv(repo, sp);
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
*/
void testUndo()
{
	MasinaRepo repo;
	//MasinaValidator valid;
	ServiceMasina ctr{ repo};
	
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
	//testSpalatServ();
	testUndo();
}
