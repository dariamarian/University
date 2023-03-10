#pragma once
#include <string>
#include <iostream>

using std::string;
using std::cout;


class Masina
{
public:
	Masina() = default;
	Masina(int id, std::string nr, std::string p, std::string m, std::string t) :ID{ id }, numar_inmatriculare{ nr }, producator{ p }, model{ m }, tip{ t } {
	}

	int getID() const noexcept{
		return ID;
	}
	std::string getNumar() const {
		return numar_inmatriculare;
	}
	std::string getProducator() const {
		return producator;
	}
	std::string getModel() const {
		return model;
	}
	std::string getTip() const {
		return tip;
	}
private:
	int ID;
	std::string numar_inmatriculare;
	std::string producator;
	std::string model;
	std::string tip;

};