#include "UI.h"
#include "ServiceMasina.h"
#include "RepoMasina.h"
#include "FileRepoMasina.h"
#include "ValidatorMasina.h"
#include "AbstractRepo.h"
#include <random>
#define _CRT_SECURE_NO_WARNINGS

void testAll() {
	//testeRepo();
	testeFileRepo();
	testCtr();
	testValidator();
}

int main() {
	testAll();
	//FileRepoMasina rep{"masini.txt"};
	//MasinaRepo repo;
	NewRepo rep;
	MasinaValidator val;
	Spalat sp;
	ServiceMasina ctr{ rep,val };
	ServiceSpalat spl{ rep,sp,val };
	ConsolUI ui{ ctr,spl };
	ui.start();
	return 0;
}