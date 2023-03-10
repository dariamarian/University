#pragma once
#include <qwidget.h>
#include <qlistwidget.h>
#include <qlistview.h>
#include <qpushbutton.h>
#include <qlabel.h>
#include <qlineedit.h>
#include <qtablewidget.h>
#include "ServiceMasina.h"
#include "ValidatorMasina.h"
#include "SpalatGUI.h"
#include "Masina.h"
#include <vector>
#include "MyListModel.h"
#include <qpainter.h>

class GUIModele;
class Desenator2 : public QWidget, public Observer
{
private:
	ServiceMasina& srv;
	void connectSignals() {
		srv.addObserver(this);
	}
public:
	Desenator2(ServiceMasina& srv) : srv{ srv } { connectSignals(); }
	void paintEvent(QPaintEvent* ev) override {
		QPainter p{ this };
		int i = 50;
		for (const auto& el : srv.toateDinCos()) {
			p.drawRect(i, i, 150, 150);
			p.fillRect(i, i, 150, 150, QBrush{ Qt::cyan, Qt::SolidPattern });
			i = i + 50;
		}
	}
	void update() override {
		repaint();
	}

	~Desenator2() {
		srv.removeObserver(this);
	}
};

class GUIModele : public QWidget {
private:
	ServiceMasina& ctr;
	MasinaValidator& val;
	MyListModel* model;
	QListView* lstV;
	QWidget* w;
	QPushButton* show2;
	QPushButton* add;
	QPushButton* modify;
	QPushButton* del;
	QPushButton* filByPr;
	QPushButton* filByTip;
	QPushButton* sortNum;
	QPushButton* sortTip;
	QPushButton* sortProdMod;
	QPushButton* undo;
	QPushButton* batch;
	QPushButton* spCrud;
	QPushButton* spRO;
	QPushButton* addSP;
	QPushButton* clearSP;
	QPushButton* addRand;
	QPushButton* exportt;

	QPushButton* add1;
	QPushButton* modify1;
	QPushButton* delete1;
	QPushButton* filter;
	QPushButton* addwash;
	QPushButton* addran;
	QPushButton* exportt2;
	
	//culoare GUI
	QPalette pal = QPalette();
	
	//secondary app
	QWidget* w1;
	QWidget* w2;
	QWidget* w3;
	QWidget* w4;
	QWidget* w5;
	QWidget* w6;
	QWidget* w7;

	QLabel* lblId;
	QLineEdit* txtId;
	QLabel* lblNr;
	QLineEdit* txtNr;
	QLabel* lblProd;
	QLineEdit* txtProd;
	QLabel* lblMod;
	QLineEdit* txtMod;
	QLabel* lblTip;
	QLineEdit* txtTip;

	QLabel* lblId1;
	QLineEdit* txtId1;
	QLabel* lblNr1;
	QLineEdit* txtNr1;
	QLabel* lblProd1;
	QLineEdit* txtProd1;
	QLabel* lblMod1;
	QLineEdit* txtMod1;
	QLabel* lblTip1;
	QLineEdit* txtTip1;

	QLabel* lblId2;
	QLineEdit* txtId2;

	QLabel* lblFil;
	QLineEdit* txtFil;

	QLabel* lblNrSp;
	QLineEdit* txtNrSp;

	QLabel* lblRnd;
	QLineEdit* txtRnd;

	QLabel* lblExp;
	QLineEdit* txtExp;

	void reloadList(std::vector<Masina> cars);
public:
	void InitGUIComps();
	GUIModele(ServiceMasina& ctr, MasinaValidator& val) :ctr{ ctr }, val{ val }{
		this->InitGUIComps();
		model = new MyListModel{ ctr.getAll() };
		lstV->setModel(model);
		connect();
	}
	void connect();
	void bTask1();
	void task1();
	void bTask2();
	void task2();
	void bTask3();
	void task3();
	void task5(int cmd);
	void task6(int cmd);
	void bTask7();
	void task7();
	void task8();
	void bTask9();
	void task9();
	void bTask10();
	void task10();
	void task11();
	void task12();
	void task13();
	void task14();
};