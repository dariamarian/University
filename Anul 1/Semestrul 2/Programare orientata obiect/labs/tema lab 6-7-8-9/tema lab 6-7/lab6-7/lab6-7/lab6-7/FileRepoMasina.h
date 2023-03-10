#define _CRT_SECURE_NO_WARNINGS
#pragma once
#include "RepoMasina.h"

class FileRepoMasina : public MasinaRepo
{
private:

	std::string filepath;
	void readFromFile();
	void writeToFile();

public:
	
	FileRepoMasina(std::string FileName);

	void store(const Masina& m) override;
	void del(int id) override;
	void update(const Masina& m) override;
};
void testeFileRepo();