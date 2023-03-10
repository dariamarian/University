#pragma once
#include <qwidget.h>
#include <qboxlayout.h>
#include <qtableview.h>
#include "Service.h"
#include "Validator.h"
#include <algorithm>
#include <qlineedit.h>
#include <qpushbutton.h>
#include <qobject.h>
#include <qpainter.h>
#include <qmessagebox.h>
#include <qformlayout.h>
#include <qlabel.h>

class MyTableModel : public QAbstractTableModel {
private:
	vector<Melodie> lista;
public:
	MyTableModel(vector<Melodie>& list) : lista{ list } {}
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role) const override;
	void setMelodii(vector <Melodie>& list);
};

class Console : public QWidget {
private:
	Service& srv;
	Validator& v;
	QTableView* sh;
	MyTableModel* model;
	QHBoxLayout* hl;
	QWidget* w;
	QPushButton* del;
	int lastid = -1;

	//adaugare
	QFormLayout* fl;
	QLabel* lblTitlu;
	QLineEdit* txtTitlu;
	QLabel* lblArtist;
	QLineEdit* txtArtist;
	QLabel* lblGen;
	QLineEdit* txtGen;
	QPushButton* add;
public:
	Console(Service& srv, Validator& v) : srv{ srv }, v{ v } {
		Init();
		connectSignals();
		refreshList();
	}
	void Init();
	void connectSignals();
	void refreshList();
	void paintEvent(QPaintEvent* ev) override;
};