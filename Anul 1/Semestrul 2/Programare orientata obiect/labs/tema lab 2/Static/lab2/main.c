#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
#include <crtdbg.h>  
#include <stdio.h>
#include "ui.h"
#include "entity.h"


int main() {
	//testPersoana();
	//testVector();
	//testValidator();
	//testRepo();
	//testService();
	ListaPersoane* repo = creeareLista();
	Service* cont = creeareService(repo, validator);

	//adaugaPersoana(cont, "Marian", "Daria", 55);


	UI* ui = creeareUI(cont);
	meniu(ui);
	distrugeService(cont);
	distrugeUI(ui);
	_CrtDumpMemoryLeaks();
	return 0;
}