#include "Tema1011.h"
#include <QtWidgets/QApplication>
#include "UI.h"
#include "ServiceMasina.h"
#include "RepoMasina.h"
#include "ValidatorMasina.h"
#include "FileRepoMasina.h"
#include "SpalatGUI.h"

void testAll() {
	testeRepo();
	testeFileRepo();
	testCtr();
	testValidator();
}

#include <QTreeView>
//#include <QDirModel>
#include <QListView>
#include <QStringListModel>
#include <QVBoxLayout>
#include <QComboBox>
#include <qdebug.h>
#include "GUImodele.h"

int ROWS = 10000;
int COLS = 100;

int main(int argc, char* argv[])
{
	FileRepoMasina rep{ "masini.txt" };
	MasinaValidator val;
	Spalat sp;
	ServiceMasina ctr{ rep };
	QApplication a(argc, argv);
	//Console c { ctr, val };
	GUIModele gui{ ctr, val };
	gui.show();
	return a.exec();

}