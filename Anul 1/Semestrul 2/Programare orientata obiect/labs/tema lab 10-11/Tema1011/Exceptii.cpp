#include "Exceptii.h"

Exceptie::Exceptie(const string& _mesaj) : mesaj{ _mesaj }
{
}

const string& Exceptie::getMessage() const
{
	return mesaj;
}