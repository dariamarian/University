#pragma once
#include <string>
#include "Melodie.h"
#include <vector>

using std::vector;
using std::string;
using std::ostream;

class Validator {
public:
	/*
	* Functia valideaza o melodie
	* Param de intrare: const string& titlu - titlul melodiei
						const string& artist - artistul melodiei
						const string& gen - genul melodiei
	*/
	void validate(const string& titlu, const string& artist, const string& gen);
};

void testValidator();