#pragma once
#include <string>
#include "Masina.h"
#include <vector>

using std::vector;
using std::string;
using std::ostream;

/*
class ValidateException {
	//vector<string> msgs;
public:
	ValidateException(const vector<string>& errors) :msgs{ errors } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const ValidateException& ex);
};

ostream& operator<<(ostream& out, const ValidateException& ex);
*/

class MasinaValidator {
public:
	/*valideaza masina*/
	void validate(const Masina& m);

	/*valideaza ID - ul */
	void validateID(int id);

	void validateIDstring(string id);

	void validateRnd(string nr);
};


void testValidator();