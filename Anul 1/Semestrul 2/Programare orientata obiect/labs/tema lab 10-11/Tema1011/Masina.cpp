#include "Masina.h"

bool cmpNumar(const Masina& m1, const Masina& m2) {
	return m1.getNumar() < m2.getNumar();
}

bool cmpTip(const Masina& m1, const Masina& m2) {
	return m1.getTip() < m2.getTip();
}

