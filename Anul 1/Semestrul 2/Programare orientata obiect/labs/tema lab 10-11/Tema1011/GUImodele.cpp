#include "GUImodele.h"
#include "Masina.h"
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMessageBox>
#include <string>
#include "MyListModel.h"

void GUIModele::InitGUIComps() {
	QVBoxLayout* newvl = new QVBoxLayout();
	lstV = new QListView;
	lstV->setUniformItemSizes(true);
	w = new QWidget();
	add = new QPushButton("Add");
	modify = new QPushButton("Modify");
	del = new QPushButton("Delete");
	filByPr = new QPushButton("&FilByProducator");
	filByTip = new QPushButton("&FilByTip");
	sortNum = new QPushButton("&SortByNumar");
	sortTip = new QPushButton("&SortByTip");
	sortProdMod = new QPushButton("&SortByProducatorModel");
	undo = new QPushButton("&Undo");
	show2 = new QPushButton("&Show");
	batch = new QPushButton("&BatchMode");
	spCrud = new QPushButton("&SpalatCrud");
	spRO = new QPushButton("&SpalatReadOnly");
	addSP = new QPushButton("&AddToWash");
	clearSP = new QPushButton("&ClearWashList");
	addRand = new QPushButton("&AddRandom");
	exportt = new QPushButton("&Export");

	QHBoxLayout* hLay1 = new QHBoxLayout();
	QHBoxLayout* hLay2 = new QHBoxLayout();
	QHBoxLayout* hLay3 = new QHBoxLayout();
	QHBoxLayout* hLay4 = new QHBoxLayout();
	QHBoxLayout* hLay5 = new QHBoxLayout();
	QHBoxLayout* hLay7 = new QHBoxLayout();
	QVBoxLayout* vl = new QVBoxLayout();
	QHBoxLayout* hl = new QHBoxLayout();

	//secondary app
	w1 = new QWidget;
	w2 = new QWidget;
	w3 = new QWidget;
	w4= new QWidget;
	w5= new QWidget;
	w6 = new QWidget;
	w7 = new QWidget;

	QVBoxLayout* vL = new QVBoxLayout;
	QFormLayout* fL = new QFormLayout;
	QWidget* details1 = new QWidget;
	add1 = new QPushButton("&Add");
	QVBoxLayout* vL2 = new QVBoxLayout;
	QFormLayout* fL2 = new QFormLayout;
	QWidget* details2 = new QWidget;
	modify1 = new QPushButton("&Modify");
	QVBoxLayout* vL3 = new QVBoxLayout;
	QFormLayout* fL3 = new QFormLayout;
	QWidget* details3 = new QWidget;
	delete1 = new QPushButton("&Delete");
	QVBoxLayout* vL4 = new QVBoxLayout;
	QFormLayout* fL4 = new QFormLayout;
	QWidget* details4 = new QWidget;
	filter = new QPushButton("&Filter");

	QVBoxLayout* vL5 = new QVBoxLayout;
	QFormLayout* fL5 = new QFormLayout;
	QWidget* details5 = new QWidget;
	addwash = new QPushButton("&AddWash");
	QVBoxLayout* vL6 = new QVBoxLayout;
	QFormLayout* fL6 = new QFormLayout;
	QWidget* details6 = new QWidget;
	addran = new QPushButton("&AddRand");
	QVBoxLayout* vL7 = new QVBoxLayout;
	QFormLayout* fL7 = new QFormLayout;
	QWidget* details7 = new QWidget;
	exportt2 = new QPushButton("&Export");

	lblId = new QLabel("Id");
	txtId = new QLineEdit;
	lblNr = new QLabel("Numar de inmatriculare");
	txtNr = new QLineEdit;
	lblProd = new QLabel("Producator");
	txtProd = new QLineEdit;
	lblMod = new QLabel("Model");
	txtMod = new QLineEdit;
	lblTip = new QLabel("Tip");
	txtTip = new QLineEdit;

	lblId1 = new QLabel("Id");
	txtId1 = new QLineEdit;
	lblNr1 = new QLabel("Numar de inmatriculare");
	txtNr1 = new QLineEdit;
	lblProd1 = new QLabel("Producator");
	txtProd1 = new QLineEdit;
	lblMod1 = new QLabel("Model");
	txtMod1 = new QLineEdit;
	lblTip1 = new QLabel("Tip");
	txtTip1 = new QLineEdit;

	lblId2 = new QLabel("Id");
	txtId2 = new QLineEdit;

	lblFil = new QLabel("Producator/Tip");
	txtFil = new QLineEdit;

	lblNrSp = new QLabel("ID:");
	txtNrSp = new QLineEdit;

	lblRnd = new QLabel("Cate masini doriti sa adaugati?");
	txtRnd = new QLineEdit;

	lblExp = new QLabel("Introduceti fisierul pt export");
	txtExp = new QLineEdit;

	pal.setColor(QPalette::Window, QColor::QColor(229, 204, 255));
	w->setAutoFillBackground(true);
	w->setPalette(pal);

	w1->setAutoFillBackground(true);
	w1->setPalette(pal);

	w2->setAutoFillBackground(true);
	w2->setPalette(pal);

	w3->setAutoFillBackground(true);
	w3->setPalette(pal);

	w4->setAutoFillBackground(true);
	w4->setPalette(pal);

	w5->setAutoFillBackground(true);
	w5->setPalette(pal);

	w6->setAutoFillBackground(true);
	w6->setPalette(pal);

	w7->setAutoFillBackground(true);
	w7->setPalette(pal);

	hLay1->addWidget(add);
	hLay1->addWidget(modify);
	hLay1->addWidget(del);
	hLay2->addWidget(filByPr);
	hLay2->addWidget(filByTip);
	hLay3->addWidget(sortNum);
	hLay3->addWidget(sortTip);
	hLay3->addWidget(sortProdMod);
	hLay4->addWidget(addSP);
	hLay4->addWidget(clearSP);
	hLay4->addWidget(addRand);
	hLay4->addWidget(exportt);
	hLay5->addWidget(batch);
	hLay5->addWidget(undo);

	hLay7->addWidget(spCrud);
	hLay7->addWidget(spRO);
	vl->addLayout(hLay1);
	vl->addLayout(hLay2);
	vl->addLayout(hLay3);
	vl->addLayout(hLay4);
	vl->addLayout(hLay5);
	vl->addLayout(hLay7);
	vl->addWidget(show2);
	hl->addLayout(vl);
	hl->addWidget(lstV);
	//hl->addWidget(shw);
	w->setLayout(hl);
	w->resize(720, 300);

	//meniu adauga
	w1->setLayout(vL);
	details1->setLayout(fL);
	fL->addRow(lblId, txtId);
	fL->addRow(lblNr, txtNr);
	fL->addRow(lblProd, txtProd);
	fL->addRow(lblMod, txtMod);
	fL->addRow(lblTip, txtTip);
	vL->addWidget(details1);
	vL->addWidget(add1);

	//meniu modifica
	w2->setLayout(vL2);
	details2->setLayout(fL2);
	fL2->addRow(lblId1, txtId1);
	fL2->addRow(lblNr1, txtNr1);
	fL2->addRow(lblProd1, txtProd1);
	fL2->addRow(lblMod1, txtMod1);
	fL2->addRow(lblTip1, txtTip1);
	vL2->addWidget(details2);
	vL2->addWidget(modify1);

	//meniu sterge
	w3->setLayout(vL3);
	details3->setLayout(fL3);
	fL3->addRow(lblId2, txtId2);
	vL3->addWidget(details3);
	vL3->addWidget(delete1);

	//meniu filtreaza
	w4->setLayout(vL4);
	details4->setLayout(fL4);
	fL4->addRow(lblFil, txtFil);
	vL4->addWidget(details4);
	vL4->addWidget(filter);

	//meniu add to wash
	w5->setLayout(vL5);
	details5->setLayout(fL5);
	fL5->addRow(lblNrSp, txtNrSp);
	vL5->addWidget(details5);
	vL5->addWidget(addwash);

	//meniu add random
	w6->setLayout(vL6);
	details6->setLayout(fL6);
	fL6->addRow(lblRnd, txtRnd);
	vL6->addWidget(details6);
	vL6->addWidget(addran);

	//meniu export
	w7->setLayout(vL7);
	details7->setLayout(fL7);
	fL7->addRow(lblExp, txtExp);
	vL7->addWidget(details7);
	vL7->addWidget(exportt2);

}

void GUIModele::connect() {
	QObject::connect(show2, &QPushButton::clicked, [&]() {
		reloadList(ctr.getAll());
		});
	QObject::connect(add, &QPushButton::clicked, [&]() {
		this->task1();
		});
	QObject::connect(modify, &QPushButton::clicked, [&]() {
		this->task2();
		});
	QObject::connect(del, &QPushButton::clicked, [&]() {
		this->task3();
		});
	QObject::connect(filByPr, &QPushButton::clicked, [&]() {
		this->task5(1);
		});
	QObject::connect(filByTip, &QPushButton::clicked, [&]() {
		this->task5(2);
		});
	QObject::connect(sortNum, &QPushButton::clicked, [&]() {
		this->task6(1);
		});
	QObject::connect(sortTip, &QPushButton::clicked, [&]() {
		this->task6(2);
		});
	QObject::connect(addSP, &QPushButton::clicked, [&]() {
		this->task7();
		});
	QObject::connect(clearSP, &QPushButton::clicked, [&]() {
		this->task8();
		});
	QObject::connect(addRand, &QPushButton::clicked, [&]() {
		this->task9();
		});
	QObject::connect(exportt, &QPushButton::clicked, [&]() {
		this->task10();
		});
	QObject::connect(sortProdMod, &QPushButton::clicked, [&]() {
		this->task6(3);
		});
	QObject::connect(batch, &QPushButton::clicked, [&]() {
		this->task11();
		});
	QObject::connect(undo, &QPushButton::clicked, [&]() {
		this->task12();
		});
	QObject::connect(spCrud, &QPushButton::clicked, [&]() {
		this->task13();
		});
	QObject::connect(spRO, &QPushButton::clicked, [&]() {
		this->task14();
		});
	w->show();
}


void GUIModele::bTask1() {
	try {
		val.validateIDstring(txtId->displayText().toStdString());
		Masina mas(stoi(txtId->displayText().toStdString()), txtNr->displayText().toStdString(), txtProd->displayText().toStdString(),
			txtMod->displayText().toStdString(), txtTip->displayText().toStdString());
		val.validate(mas);
		ctr.addMasina(stoi(txtId->displayText().toStdString()), txtNr->displayText().toStdString(), txtProd->displayText().toStdString(),
			txtMod->displayText().toStdString(), txtTip->displayText().toStdString());
		reloadList(ctr.getAll());
	}
	catch (MasinaRepoException& err) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
	}
}

void GUIModele::task1() {
	txtId->setText("");
	txtNr->setText("");
	txtProd->setText("");
	txtMod->setText("");
	txtTip->setText("");
	w1->show();
	QObject::connect(add1, &QPushButton::clicked, [&]() {
		this->bTask1();
		w1->close();
		});
}

void GUIModele::bTask2() {
	try {
		val.validateIDstring(txtId1->displayText().toStdString());
		Masina mas(stoi(txtId1->displayText().toStdString()), txtNr1->displayText().toStdString(), txtProd1->displayText().toStdString(),
			txtMod1->displayText().toStdString(), txtTip1->displayText().toStdString());
		val.validate(mas);
		ctr.updateMasina(stoi(txtId1->displayText().toStdString()), txtNr1->displayText().toStdString(), txtProd1->displayText().toStdString(),
			txtMod1->displayText().toStdString(), txtTip1->displayText().toStdString());
		reloadList(ctr.getAll());
	}
	catch (MasinaRepoException& err) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
	}
}

void GUIModele::task2() {
	txtId1->setText("");
	txtNr1->setText("");
	txtProd1->setText("");
	txtMod1->setText("");
	txtTip1->setText("");
	w2->show();
	QObject::connect(modify1, &QPushButton::clicked, [&]() {
		this->bTask2();
		w2->close();
		});
}

void GUIModele::bTask3() {
	try {
		val.validateIDstring(txtId2->displayText().toStdString());
		ctr.deleteMasina(txtId2->displayText().toInt());
		reloadList(ctr.getAll());
	}
	catch (MasinaRepoException& err) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
	}
}

void GUIModele::task3() {
	txtId2->setText("");
	w3->show();
	QObject::connect(delete1, &QPushButton::clicked, [&]() {
		this->bTask3();
		w3->close();
		});
}

void GUIModele::task5(int cmd) {
	txtFil->setText("");
	w4->show();
	if (cmd == 1)
		QObject::connect(filter, &QPushButton::clicked, [&]() {
		try {
			Masina mas(22, "ok", txtFil->displayText().toStdString(), "ok", "ok");
			val.validate(mas);
			reloadList(ctr.filtrareProducator(txtFil->displayText().toStdString()));
		}
		catch (MasinaRepoException& err) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
		}
		w4->close();
			});
	else
		QObject::connect(filter, &QPushButton::clicked, [&]() {
		try {
			Masina mas(22, "ok", "ok", "ok", txtFil->displayText().toStdString());
			val.validate(mas);
			reloadList(ctr.filtrareTip(txtFil->displayText().toStdString()));
		}
		catch (MasinaRepoException& err) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
		}
		w4->close();
			});
}

void GUIModele::task6(int cmd) {
	if (cmd == 1)
		reloadList(ctr.sortByNumar());
	else if (cmd == 2)
		reloadList(ctr.sortByTip());
	else if (cmd == 3)
		reloadList(ctr.sortByProducatorModel());
}


void GUIModele::bTask7() {
	try {
		val.validateID(txtNrSp->displayText().toInt());
		ctr.addToCos(txtNrSp->displayText().toInt());
	}
	catch (MasinaRepoException& err) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
	}
}

void GUIModele::task7() {
	txtNrSp->setText("");
	w5->show();
	QObject::connect(addwash, &QPushButton::clicked, [&]() {
		this->bTask7();
		w5->close();
		});
}

void GUIModele::task8() {
	ctr.golesteCos();
}

void GUIModele::bTask9() {
	try {
		val.validateRnd(txtRnd->displayText().toStdString());
		ctr.addRandom(txtRnd->displayText().toInt());
	}
	catch (MasinaRepoException& err) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
	}
}

void GUIModele::task9() {
	txtRnd->setText("");
	w6->show();
	QObject::connect(addran, &QPushButton::clicked, [&]() {
		this->bTask9();
		w6->close();
		});
}

void GUIModele::bTask10() {
	try {
		string file = txtExp->displayText().toStdString();
		file += ".csv";
		ctr.exportSrv(file);
	}
	catch (MasinaRepoException& err) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
	}
}

void GUIModele::task10() {
	txtExp->setText("");
	w7->show();
	QObject::connect(exportt2, &QPushButton::clicked, [&]() {
		this->bTask10();
		w7->close();
		});
}

void GUIModele::task11() {
	vector<string> comenzi;
	string delim = " ";

	comenzi.push_back("add 14 inm prod model tip ");
	comenzi.push_back("update 1 inm prod model tip ");
	comenzi.push_back("del 2 ");

	vector<string> words;
	for (auto comanda : comenzi) {
		size_t pos = 0;
		words.clear();

		while ((pos = comanda.find(delim)) != string::npos) {
			words.push_back(comanda.substr(0, pos));
			comanda.erase(0, pos + delim.length());
		}
		if (words[0] == "add") {

			if (words.size() == 6)
				try {
				int id = stoi(words[1]);
				val.validateIDstring(words[1]);
				Masina mas(id, words[2], words[3], words[4], words[5]);
				val.validate(mas);
				ctr.addMasina(id, words[2], words[3], words[4], words[5]);
			}
			catch (MasinaRepoException& err) {
				QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
			}
		}
		else if (words[0] == "update") {
			if (words.size() == 6)
				try {
				val.validateIDstring(words[1]);
				Masina mas(stoi(words[1]), words[2], words[3], words[4], words[5]);
				val.validate(mas);
				ctr.updateMasina(stoi(words[1]), words[2], words[3], words[4], words[5]);
			}
			catch (MasinaRepoException& err) {
				QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
			}
		}
		else if (words[0] == "del") {
			if (words.size() == 2)
				try {
				val.validateIDstring(words[1]);
				ctr.deleteMasina(stoi(words[1]));
			}
			catch (MasinaRepoException& err) {
				QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
			}
		}
	}
	reloadList(ctr.getAll());
}

void GUIModele::task12() {
	try {
		ctr.undo();
		reloadList(ctr.getAll());
	}
	catch (MasinaRepoException& err) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(err.getMessage()));
	}
}

void GUIModele::task13() {
	CosGUICuLista* cosGUI = new CosGUICuLista{ ctr.getCos() };
	cosGUI->show();
}

void GUIModele::task14() {
	Desenator2* desen2 = new Desenator2(ctr);
	desen2->show();
}

void GUIModele::reloadList(std::vector<Masina> cars) {
	model->setCars(cars);
}