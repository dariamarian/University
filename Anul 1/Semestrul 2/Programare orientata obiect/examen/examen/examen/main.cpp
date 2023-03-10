#include <QtWidgets/QApplication>
#include "GUI.h"

void testAll() {
    testValidator();
    testRepo();
    testService();
}

int main(int argc, char* argv[])
{
    testAll();
    QApplication a(argc, argv);
    FileRepo repo{ "melodii.txt" };
    Service srv{ repo };
    Validator val;
    Console c{ srv, val };
    c.show();
    return a.exec();
}
