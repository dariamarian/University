#include "ValidatorMasina.h"
#include <assert.h>
#include <sstream>

void MasinaValidator::validate(const Masina& m) {
	vector<string> msgs;
	if (m.getID() <= 0) msgs.push_back("ID invalid");
	if (m.getNumar().size() == 0 || m.getNumar().size() > 50) msgs.push_back("Numar de inmatriculare invalid");
	if (m.getProducator().size() == 0 || m.getProducator().size() > 50) msgs.push_back("Producator invalid");
	if (m.getModel().size() == 0 || m.getModel().size() > 50) msgs.push_back("Model invalid");
	if (m.getTip().size() == 0 || m.getTip().size() > 50) msgs.push_back("Tip invalid");
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}
}

void MasinaValidator::validateID(int id) {
	vector<string> msgs;
	if (id <= 0) msgs.push_back("ID invalid");
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}
}

ostream& operator<<(ostream& out, const ValidateException& ex) {
	for (const auto& msg : ex.msgs) {
		out << msg << " ";
	}
	return out;
}

void testValidator() {
	MasinaValidator v;
	Masina m{-1,"","","",""};
	try {
		v.validate(m);
	}
	catch (const ValidateException& ex) {
		std::stringstream sout;
		sout << ex;
		auto mesaj = sout.str();
		assert(mesaj.find("invalid") >= 0);

	}
	int id = -3;
	try {
		v.validateID(id);
	}
	catch (const ValidateException& ex) {
		std::stringstream sout;
		sout << ex;
		auto mesaj = sout.str();
		assert(mesaj.find("invalid") >= 0);
	}
}


