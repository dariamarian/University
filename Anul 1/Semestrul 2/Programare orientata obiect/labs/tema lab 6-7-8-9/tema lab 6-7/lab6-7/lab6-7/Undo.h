#pragma once
#include "Masina.h"
#include "RepoMasina.h"
#include "AbstractRepo.h"
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
	//MasinaRepo& rep;
	RepoMasini& rep;
public:
	UndoAdd(const Masina& m, RepoMasini& rep) : masinaAdaugata{ m }, rep{ rep }{}
	void doUndo() override;
};

class UndoUpdate : public UndoAction
{
private:
	Masina masinaActualizata;
	RepoMasini& rep;
public:
	UndoUpdate(const Masina& m, RepoMasini& rep) : rep{ rep }, masinaActualizata{ m }{}
	void doUndo() override;
};

class UndoDelete : public UndoAction
{

private:
	Masina masinaStearsa;
	RepoMasini& rep;
public:
	UndoDelete(const Masina& m, RepoMasini& rep) : rep{ rep }, masinaStearsa{ m }{}
	void doUndo() override;
};