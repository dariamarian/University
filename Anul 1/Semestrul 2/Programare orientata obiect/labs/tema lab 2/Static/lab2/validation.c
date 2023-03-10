#include "validation.h"
#include <assert.h>

int validator(Persoana* p)
{
	if (p->scor <= 0 || p->scor >= 100) return SCOR_INVALID;
	if (!strlen(p->nume) || !strlen(p->prenume)) return STRING_VID;
	return SUCCES;
}

void testValidator()
{
	Persoana* p = creearePersoana("nume_validator", "prenume_validator", 0);
	assert(validator(p) == SCOR_INVALID);
	Persoana* p1 = creearePersoana("nume_validator", "prenume_validator", 110);
	assert(validator(p1) == SCOR_INVALID);
	Persoana* p2 = creearePersoana("", "prenume_validator", 80);
	assert(validator(p2) == STRING_VID);
	Persoana* p3 = creearePersoana("nume_validator", "prenume_validator", 50);
	assert(validator(p3) == SUCCES);
	distrugePersoana(p);
	distrugePersoana(p1);
	distrugePersoana(p2);
	distrugePersoana(p3);
}