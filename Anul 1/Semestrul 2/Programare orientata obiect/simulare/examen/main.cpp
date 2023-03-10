#include "examen.h"
#include <QtWidgets/QApplication>
#include "gui.h"
#include "service.h"
#include "repo.h"
#include "test.h"

int main(int argc, char* argv[])
{
	testall();
	QApplication a(argc, argv);
	FileRepo rep {"telefoane.txt"};
	Service srv{ rep };
	Console c{ srv };
	return a.exec();
}