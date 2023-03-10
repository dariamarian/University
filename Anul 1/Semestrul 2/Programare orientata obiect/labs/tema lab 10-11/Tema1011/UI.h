#pragma once
#include "ServiceMasina.h"
#include "ValidatorMasina.h"
#include "SpalatGUI.h"
#include <iostream>
#include <QApplication>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qtablewidget.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qmessagebox.h>
#include <qpainter.h>

using namespace std;

class Console;
class Desenator : public QWidget, public Observer
{
private:
	ServiceMasina& srv;
	void connectSignals() {
		srv.addObserver(this);
	}
public:
	Desenator(ServiceMasina& srv) : srv{ srv } { connectSignals(); }
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

	~Desenator() {
		srv.removeObserver(this);
	}
};

class Console : public QWidget {
public:
	void InitButtons();
	void Init();
	void bTask1();
	Console(ServiceMasina& ctr, MasinaValidator& val) :ctr{ ctr }, val{ val }{
		buton = new QWidget();
		this->InitButtons();
		this->Init();
	}
	void connect();
	void meniu();
	void create_lista_subst();
	void create_widget();
	void curata_widget(QLayout* newvl);
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
	void showRez(vector <Masina>);
	void showMas(Masina& m);
	void showAll();
	~Console() { delete buton; };
private:
	vector <pair<string, int>> tipuri;
	ServiceMasina& ctr;
	MasinaValidator& val;
	//main app
	QVBoxLayout* newvl;
	QWidget* buton;
	QListWidget* shw;
	//QTableWidget* shw2;
	QWidget* w ;
	QPushButton* add ;
	QPushButton* modify;
	QPushButton* del;
	QPushButton* filByPr;
	QPushButton* filByTip;
	QPushButton* sortNum;
	QPushButton* sortTip;
	QPushButton* sortProdMod;
	QPushButton* undo ;
	QPushButton* show ;
	QPushButton* batch;
	QPushButton* spCrud;
	QPushButton* spRO;
	QPushButton* addSP;
	QPushButton* clearSP;
	QPushButton* addRand;
	QPushButton* exportt;

	QHBoxLayout* hLay1;
	QHBoxLayout* hLay2;
	QHBoxLayout* hLay3;
	QHBoxLayout* hLay4;
	QHBoxLayout* hLay5;
	QHBoxLayout* hLay7;

	QVBoxLayout* vl;
	QHBoxLayout* hl;

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

	QVBoxLayout* vL;
	QFormLayout* fL;
	QWidget* details1;
	QPushButton* add1;
	QVBoxLayout* vL2;
	QFormLayout* fL2;
	QWidget* details2;
	QPushButton* modify1;
	QVBoxLayout* vL3;
	QFormLayout* fL3;
	QWidget* details3;
	QPushButton* delete1;
	QVBoxLayout* vL4;
	QFormLayout* fL4;
	QWidget* details4;
	QPushButton* filter;

	QVBoxLayout* vL5;
	QFormLayout* fL5;
	QWidget* details5;
	QPushButton* addwash;
	QVBoxLayout* vL6;
	QFormLayout* fL6;
	QWidget* details6;
	QPushButton* addran;
	QVBoxLayout* vL7;
	QFormLayout* fL7;
	QWidget* details7;
	QPushButton* exportt2;


	QLabel* lblId;
	QLineEdit* txtId ;
	QLabel* lblNr ;
	QLineEdit* txtNr;
	QLabel* lblProd;
	QLineEdit* txtProd;
	QLabel* lblMod ;
	QLineEdit* txtMod;
	QLabel* lblTip ;
	QLineEdit* txtTip;

	QLabel* lblId1;
	QLineEdit* txtId1;
	QLabel* lblNr1;
	QLineEdit* txtNr1;
	QLabel* lblProd1 ;
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

};