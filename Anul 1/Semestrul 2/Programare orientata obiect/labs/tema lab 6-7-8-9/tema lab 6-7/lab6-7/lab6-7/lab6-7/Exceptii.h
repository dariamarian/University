#pragma once
#include <exception>
#include <string>

using std::exception;
using std::string;

class Exceptie
{
private:
	string mesaj;

public:
	Exceptie(const string&);
	const string& getMessage() const;

};
