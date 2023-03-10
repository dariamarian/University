#pragma once
#include <qwidget.h>
#include <qtimer.h>
#include <QtWidgets/QHBoxLayout>
#include <qpushbutton.h>
#include <qlistwidget.h>
#include <qtablewidget.h>
#include <qstring.h>
#include <qlabel.h>
#include <vector>
#include "CosSpalat.h"
#include "Observer.h"

class CosGUICuLista : public QWidget, public Observer {
private:
	CosSpalat& cos;
	QListWidget* lst;
	QPushButton* btn;
	QPushButton* btnRandom;
	void loadList(const std::vector<Masina>& cars) {
		lst->clear();
		for (auto& m : cars) {
			lst->addItem(QString::fromStdString("Id: "
				+ to_string(m.getID()) + " | Numar de inmatriculare: " + m.getNumar()
				+ " | Producator: " + m.getProducator() + " | Model: "
				+ m.getModel() + " | Tip: " + m.getTip() + '\n'));
		}
	}
	void initGUI() {
		QHBoxLayout* ly = new QHBoxLayout;
		lst = new QListWidget;
		ly->addWidget(lst);
		btn = new QPushButton("Clear cos");
		ly->addWidget(btn);

		btnRandom = new QPushButton("Add random 3");
		ly->addWidget(btnRandom);
		setLayout(ly);
	}
	void connectSignals() {
		cos.addObserver(this);
		QObject::connect(btn, &QPushButton::clicked, [&]() {
			cos.goleste();
			loadList(cos.lista());
			});
		QObject::connect(btnRandom, &QPushButton::clicked, [&]() {
			cos.umple(3);
			loadList(cos.lista());
			});
		/*
		QTimer* t = new QTimer{ this };
		t->start(1000);
		QObject::connect(t, &QTimer::timeout, [this]() {
			loadList(cos.lista());
		});*/

	}
public:
	CosGUICuLista(CosSpalat& cos) :cos{ cos } {
		initGUI();
		connectSignals();
		loadList(cos.lista());
	}

	void update() override {
		loadList(cos.lista());
	}
	~CosGUICuLista() {
		cos.removeObserver(this);
	}

};

class CosGUILabel :public QLabel, public Observer {
	CosSpalat& cos;
public:
	CosGUILabel(CosSpalat& cos) :cos{ cos } {
		setAttribute(Qt::WA_DeleteOnClose); //daca vreau sa se distruga fereastra imediat dupa inchidere
		cos.addObserver(this);
		update();
		/*
		QTimer* t=new QTimer{this};
		t->start(1000);
		QObject::connect(t, &QTimer::timeout, [this]() {
			update();
		});
		*/
	}

	void update() /*override */ {
		setText("Cosul contine:" + QString::number(cos.lista().size()));
	}
	~CosGUILabel() {
		cos.removeObserver(this);
	}
};

class CosGUICuTabel : public QWidget, public Observer {
private:
	CosSpalat& cos;
	QTableWidget* lst;
	QPushButton* btn;
	QPushButton* btnRefresh = new QPushButton{ "refresh" };
	QPushButton* btnRandom;

	void loadTable(const std::vector<Masina>& cars) {
		lst->clear();
		lst->setRowCount(cars.size());
		lst->setColumnCount(5);
		for (int i = 0; i < cars.size(); i++) 
		{
			lst->setItem(i, 0, new QTableWidgetItem(QString::fromStdString("Id: " + to_string(cars[i].getID()))));
			lst->setItem(i, 1, new QTableWidgetItem(QString::fromStdString("| Numar: " + cars[i].getNumar())));
			lst->setItem(i, 2, new QTableWidgetItem(QString::fromStdString("| Producator: " + cars[i].getProducator())));
			lst->setItem(i, 3, new QTableWidgetItem(QString::fromStdString("| Model: " + cars[i].getModel())));
			lst->setItem(i, 4, new QTableWidgetItem(QString::fromStdString("| Tip: " + cars[i].getTip())));
		}
	}

	void initGUI() {
		QHBoxLayout* ly = new QHBoxLayout;
		lst = new QTableWidget;
		ly->addWidget(lst);
		btn = new QPushButton("Clear cos");
		ly->addWidget(btn);

		btnRandom = new QPushButton("Add random 4");
		ly->addWidget(btnRandom);
		ly->addWidget(btnRefresh);
		setLayout(ly);
	}
	void connectSignals() {
		cos.addObserver(this);
		QObject::connect(btn, &QPushButton::clicked, [&]() {
			cos.goleste();
			loadTable(cos.lista());
			});
		QObject::connect(btnRandom, &QPushButton::clicked, [&]() {
			cos.umple(4);
			loadTable(cos.lista());
			});
		QObject::connect(btnRefresh, &QPushButton::clicked, [this]() {
			loadTable(cos.lista());
			});

		/*
		QTimer* t = new QTimer{ this };
		t->start(1000);
		QObject::connect(t, &QTimer::timeout, [this]() {
			loadTable(cos.lista());
		});
		*/
	}
public:
	CosGUICuTabel(CosSpalat& cos) :cos{ cos } {
		initGUI();
		connectSignals();
		loadTable(cos.lista());
	}

	void update() override {
		loadTable(cos.lista());
	}

	~CosGUICuTabel() {
		cos.removeObserver(this);
	}

};
