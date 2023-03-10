#include "Undo.h"

void UndoAdd::doUndo()
{
	rep.del(masinaAdaugata.getID());
}

void UndoUpdate::doUndo()
{
	rep.del(masinaActualizata.getID());
	rep.store(Masina{ masinaActualizata.getID(),masinaActualizata.getNumar(),masinaActualizata.getProducator(),masinaActualizata.getModel(),masinaActualizata.getTip() });
}

void UndoDelete::doUndo()
{
	rep.store(masinaStearsa);
}