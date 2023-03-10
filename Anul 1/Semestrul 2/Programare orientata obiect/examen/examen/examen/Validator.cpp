#include "Validator.h"
#include <cassert>

void Validator::validate(const string& titlu, const string& artist, const string& gen)
{
	string err = "";
	if (titlu.empty())
		err += "Titlu invalid!\n";
	if (artist.empty())
		err += "Artist invalid!\n";
	if (gen != "rock" && gen != "pop" && gen != "folk" && gen != "disco")
		err += "Gen invalid!\n";
	if (err.size() > 0)
		throw string(err);
}

void testValidator() {
	string err;
	Validator v;
	v.validate("dd", "dsd", "pop");
	try {
		v.validate("dd", "dsd", "pop2");
	}
	catch (string& err) {
		assert(err == "Gen invalid!\n");
	}
	try {
		v.validate("", "", "pop2");
	}
	catch (string& err) {
		assert(err == "Titlu invalid!\nArtist invalid!\nGen invalid!\n");
	}
}