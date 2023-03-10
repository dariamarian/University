#include "service.h"
#include <algorithm>

struct byPret {
	inline bool operator()(Telefon& t1, Telefon t2) {
		return t1.getPret() < t2.getPret();
	}
};

struct byBrand {
	inline bool operator()(Telefon& t1, Telefon t2) {
		return t1.getBrand() < t2.getBrand();
	}
};

struct byModel {
	inline bool operator()(Telefon& t1, Telefon t2) {
		return t1.getModel() < t2.getModel();
	}
};

void Service::sortare(int c, vector<Telefon>& list) {
	if (c == 1)
		sort(list.begin(), list.end(), byPret());
	else if(c==2)
		sort(list.begin(), list.end(), byBrand());
	else if(c==3)
		sort(list.begin(), list.end(), byModel());
}
