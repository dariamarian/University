#pragma once
#include "Masina.h"
#include "RepoMasina.h"
class UndoAction
{
public:
	virtual void doUndo() = 0;
	virtual ~UndoAction() = default;
};

class UndoAdd : public UndoAction
{
private:
	Masina masinaAdaugata;
	MasinaRepo& rep;
public:
	UndoAdd(const Masina& m, MasinaRepo& rep) : masinaAdaugata{ m }, rep{ rep }{}
	void doUndo() override;
};

class UndoUpdate : public UndoAction
{
private:
	Masina masinaActualizata;
	MasinaRepo& rep;
public:
	UndoUpdate(const Masina& m, MasinaRepo& rep) : rep{ rep }, masinaActualizata{ m }{}
	void doUndo() override;
};

class UndoDelete : public UndoAction
{

private:
	Masina masinaStearsa;
	MasinaRepo& rep;
public:
	UndoDelete(const Masina& m, MasinaRepo& rep) : rep{ rep }, masinaStearsa{ m }{}
	void doUndo() override;
};