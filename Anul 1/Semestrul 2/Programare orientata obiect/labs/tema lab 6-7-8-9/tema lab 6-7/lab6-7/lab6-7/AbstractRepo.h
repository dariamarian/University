#pragma once
#include "Masina.h"
#include <unordered_map>
#include <random>
#include <vector>
using namespace std;


class RepoMasini {
public:
	virtual size_t size();
	virtual vector<Masina>* getAll();
	
	virtual vector <Masina>& getSpalat();
	
	virtual const Masina& find(int id);
	virtual vector <Masina> FindCarsAfterNumar(string NrToFind);
	virtual bool exist(const Masina& m);

	virtual void store(const Masina& m);
	virtual void del(int id);
	virtual void update(const Masina& m);

	virtual void add(Masina& ToAdd);
	virtual void clear();
	virtual size_t numar_masini();

	virtual void rand(int nr);
	virtual ~RepoMasini() = default;
protected:
	unordered_map <int, Masina> RepoMas;
	vector <Masina> Spalat;
};

class Exception {

public:
	Exception(const string& msg) : msg_(msg) {}
	~Exception() {}

	string getMessage() const { return(msg_); }
private:
	string msg_;
};

class NewRepo : public RepoMasini {
private:
	int random;
public:
	int getRandom() {
		std::mt19937 mt{ std::random_device{}() };
		std::uniform_int_distribution<> dist(0, 1);
		this->random = dist(mt);
		return random;
	}
	size_t size() override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		return RepoMasini::size();
	}
	vector <Masina>* getAll() override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		return RepoMasini::getAll();
	}
	vector <Masina>& getSpalat() override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		return RepoMasini::getSpalat();
	}
	const Masina& find(int id) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		return RepoMasini::find(id);
	}

	vector <Masina> FindCarsAfterNumar(string NrToFind) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		return RepoMasini::FindCarsAfterNumar(NrToFind);
	}

	bool exist(const Masina& m) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		return RepoMasini::exist(m);
	}

	void store(const Masina& m) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		RepoMasini::store(m);
	}
	void del(int id) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		RepoMasini::del(id);
	}
	void update(const Masina& m) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		RepoMasini::update(m);
	}

	void add(Masina& ToAdd) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		RepoMasini::add(ToAdd);
	}
	void clear() override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		RepoMasini::clear();
	}
	void rand(int nr) override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		RepoMasini::rand(nr);
	}
	size_t numar_masini() override {
		if (getRandom() == 0)
			throw Exception("Error!\n");
		return RepoMasini::numar_masini();
	}
};