#include "ValidatorMasina.h"
#include <assert.h>
#include <sstream>

void MasinaValidator::validate(const Masina& m) {
	
	string err = "";
	if (m.getID() <= 0) 
		err+="ID invalid!";
	if (m.getNumar() == "" or m.getNumar().size() > 7)
		err += "Numar de inmatriculare invalid!\n";
	if (m.getProducator() == "" or m.getProducator().size() > 50)
		err += "Producator invalid!\n";
	for (int i = 0; i < m.getProducator().size(); i++)
		if ((m.getProducator()[i] < 'a' || m.getProducator()[i] > 'z') && (m.getProducator()[i] < 'A' || m.getProducator()[i] > 'Z')) {
			err += "Producator invalid!\n";
			break;
		}
	if (m.getModel() == "" or m.getModel().size() > 15)
		err += "Model invalid!\n";
	if (m.getTip() == "" or m.getTip().size() > 25)
		err += "Tip invalid!\n";
	for (int i = 0; i < m.getTip().size(); i++)
		if ((m.getTip()[i] < 'a' || m.getTip()[i] > 'z') && (m.getTip()[i] < 'A' || m.getTip()[i] > 'Z')) {
			err += "Tip invalid!\n";
			break;
		}
	if (err != "")
		throw err;
	/*
	vector<string> msgs;
	if (m.getID() <= 0) msgs.push_back("ID invalid");
	if (m.getNumar().size() == 0 || m.getNumar().size() > 50) msgs.push_back("Numar de inmatriculare invalid");
	if (m.getProducator().size() == 0 || m.getProducator().size() > 50) msgs.push_back("Producator invalid");
	if (m.getModel().size() == 0 || m.getModel().size() > 50) msgs.push_back("Model invalid");
	if (m.getTip().size() == 0 || m.getTip().size() > 50) msgs.push_back("Tip invalid");
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}*/
}

void MasinaValidator::validateID(int id) {
	string err = "";
	if (id <= 0) err+="ID invalid\n";
	if (err != "") 
		throw err;
}

void MasinaValidator::validateRnd(string nr) {
	string err = "";
	for (int i = 0; i < nr.size(); i++)
		if (nr[i] < '0' || nr[i] > '9')
			throw string("Numar invalid!\n");
	if (nr.size() > 4)
		throw string("Numar prea mare!\n");
	if (nr == "")
		throw string("Numar invalid!\n");
	if (err != "")
		throw err;
}

void MasinaValidator::validateIDstring(string id) {

	string err = "";
	if (id == "" or id.size() > 6)
		err += "Id invalid!\n";
	for (int i = 0; i < id.size(); i++)
		if (id[i] < '0' || id[i] > '9') {
			err += "Id invalid!\n";
			break;
		}
	if (err != "")
		throw err;
}

/*
ostream& operator<<(ostream& out, const ValidateException& ex) {
	for (const auto& msg : ex.msgs) {
		out << msg << " ";
	}
	return out;
}
*/

void testValidator() {
	string err;
	MasinaValidator v;
	Masina mas{ 33,"dd","dsd","ds","sds" };
	v.validate(mas);
	try {
		Masina mas{ 33,"","dsd","ds","sds" };
		v.validate(mas);
	}
	catch (string& err) {
		assert(err == "Numar de inmatriculare invalid!\n");
	}
	try {
		Masina mas{ 15,"","","","" };
		v.validate(mas);
	}
	catch (string& err) {
		assert(err == "Numar de inmatriculare invalid!\nProducator invalid!\nModel invalid!\nTip invalid!\n");
	}
	
	v.validateID(34);
	try {
		v.validateID(-3);
	}
	catch (string& err) {
		assert(err == "ID invalid\n");
	}

	string id = "";
	try {
		v.validateIDstring(id);
	}
	catch (string& err) {
		assert(err == "Id invalid!\n");
	}

	v.validateRnd("45");
	try {
		v.validateRnd("");
	}
	catch (string& err) {
		assert(err == "Numar invalid!\n");
	}
}