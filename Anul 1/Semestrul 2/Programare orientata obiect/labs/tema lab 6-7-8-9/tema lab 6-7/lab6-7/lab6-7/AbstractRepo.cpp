#include "AbstractRepo.h"
#include "Masina.h"

size_t RepoMasini::size() {
	return this->RepoMas.size();
}

size_t RepoMasini::numar_masini() {
	return this->Spalat.size();
}


vector <Masina>* RepoMasini::getAll() {
	vector <Masina>* rez = new vector<Masina>();
	for (auto& m : RepoMas)
	{
		rez->push_back(m.second);
	}
	return rez;
}

vector<Masina>& RepoMasini::getSpalat() {
	return Spalat;
}

const Masina& RepoMasini::find(int id) {
	for (auto& m : RepoMas) {
		if (m.second.getID() == id) {
			return m.second;
		}
	}
	throw Exception("Nu exista aceasta masina.");
}

vector < Masina > RepoMasini::FindCarsAfterNumar(std::string NrToFind)
{
	vector < Masina > toReturn;
	for (auto& iterator : RepoMas)
	{
		if (iterator.second.getNumar() == NrToFind)
		{
			toReturn.push_back(iterator.second);
		}
	}
	return toReturn;
}
bool RepoMasini::exist(const Masina& m) {
	try {
		find(m.getID());
		return true;
	}
	catch (Exception&) { return false; }
}
void RepoMasini::store(const Masina& m) {
	if (exist(m)) {
		throw Exception("Exista deja masina cu acest id.");
	}
	this->RepoMas.insert({ m.getID(), m });
}

void RepoMasini::del(int id) {
	int ok = 0, i = -1;
	for (auto& mas : RepoMas) {
		i++;
		if (mas.second.getID() == id) {
			RepoMas.erase(mas.second.getID());
			ok = 1;
			break;
		}
	}
	if (!ok)
		throw (Exception("Masina inexistenta!\n"));
}

void RepoMasini::update(const Masina& m) {
	int ok = 0, i = -1;
	for (auto& mas : RepoMas) {
		i++;
		if (mas.second.getID() == m.getID()) {
			ok = 1;
			RepoMas.at(mas.second.getID()) = m;
			break;
		}
	}
	if (!ok)
		throw (Exception("Masina inexistenta!\n"));
}

void RepoMasini::add(Masina& ToAdd) {
	Spalat.push_back(ToAdd);
}

void RepoMasini::clear() {
	Spalat.clear();
}

void RepoMasini::rand(int nr) {
	if (RepoMas.size() == 0)
		throw ((Exception)("Nu exista masini de adaugat in reteta!\n"));
	std::mt19937 mt{ std::random_device{}() };
	std::uniform_int_distribution<> dist(0, int(RepoMas.size()) - 1);

	for (int i = 0; i < nr; i++) {
		int val = dist(mt);
		Spalat.push_back(RepoMas[val]);
	}
}