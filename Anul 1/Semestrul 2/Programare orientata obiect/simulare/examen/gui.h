#pragma once
#include "service.h"
#include <iostream>
#include <QApplication>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qlineedit.h>


using namespace std;

class Console : public QWidget {
public:
	Console(Service& srv) :srv{ srv } {
		this->InitButtons();
		this->Init();
		show_all(srv.srv_getAll());
		connect();
	}
	~Console() {};
private:
	Service& srv;
	void InitButtons();
	void Init();
	void connect();
	void sortare(int c);
	void show_all(vector <Telefon>& lista);
	void show_detalii();

	QVBoxLayout* newvl;
	QListWidget* shw;
	QWidget* w;

	QPushButton* SortByPr;
	QPushButton* SortByBr;
	QPushButton* SortByMd;
	QPushButton* Nesortat;

	QLineEdit* det;

	QHBoxLayout* hLay1;
	QHBoxLayout* hLay2;

	QVBoxLayout* vl;
	QHBoxLayout* hl;
};