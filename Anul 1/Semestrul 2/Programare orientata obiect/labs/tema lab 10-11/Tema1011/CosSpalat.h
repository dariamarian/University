#pragma once
#include <vector>
#include <algorithm>
#include <random>    // std::default_random_engine
#include <chrono>    // std::chrono::system_clock

#include "Masina.h"
#include "Observer.h"
#include "RepoMasina.h"
using std::vector;
/*
  Clasa care implementeaza ideea de cos
  Extinde observable, astfel cei interesati se notifica in caz ca s-a schimbat ceva la Cos
*/
class CosSpalat :public Observable {
	vector<Masina> inCos;
	const MasinaRepo& rep;
public:
	CosSpalat(const MasinaRepo& rep) :rep{ rep } {}

	void adauga(const Masina& m) {
		inCos.push_back(m);
		//notificam cand se schimba ceva in cos
		notify();
	}
	void goleste() {
		inCos.clear();
		//notificam can dse schimba ceva in cos
		notify();
	}
	/*
	Umple cosul aleator
	*/
	void umple(int cate) {
		int seed = std::chrono::system_clock::now().time_since_epoch().count();
		vector<Masina> all = rep.getAll();
		std::shuffle(all.begin(), all.end(), std::default_random_engine(seed)); //amesteca vectorul v
		while (inCos.size() < cate && all.size() > 0) {
			inCos.push_back(all.back());
			all.pop_back();
		}
		//notificam can dse schimba ceva in cos
		notify();
	}

	const vector<Masina>& lista() const {
		return inCos;
	}
};