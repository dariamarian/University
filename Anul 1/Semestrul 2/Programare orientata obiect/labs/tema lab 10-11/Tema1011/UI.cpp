#include "ui.h"

void Console::meniu() {
	cout << "Meniu:\n";
	cout << "1. adauga\n";
	cout << "2. sterge\n";
	cout << "3. modifica\n";
	cout << "4. cauta\n";
	cout << "5. tipareste\n";
	cout << "6. sort by numar\n";
	cout << "7. sort by tip\n";
	cout << "8. sort by producator+model\n";
	cout << "9. filtrare producator\n";
	cout << "10. filtrare tip\n";
	cout << "11. adauga dupa numar de inmatriculare\n";
	cout << "12. goleste lista\n";
	cout << "13. genereaza random\n";
	cout << "14. export\n";
	cout << "15. batch mode\n";
	cout << "16. undo\n";
	cout << "0. iesire\n";
	cout << "Dati comanda : ";
}

void Console::create_lista_subst() {
	tipuri.clear();
	for (auto& elem : ctr.getAll())
	{
		int i = 0, ok = 0;
		for (auto& tip : tipuri) {
			if (tip.first == elem.getTip())
				tipuri[i].second++, ok = 1;
			i++;
		}
		if (!ok)
			tipuri.push_back(make_pair(elem.getTip(), 1));
	}
}

void Console::create_widget() {
	for (auto& tip : tipuri) {
		QPushButton* bt = new QPushButton(QString::fromStdString(tip.first));
		int n = tip.second;
		QObject::connect(bt, &QPushButton::clicked, [n, this]() {
			QMessageBox::warning(this, "warning", QString::number(n));
			});
		newvl->addWidget(bt);
	}
}

void Console::curata_widget(QLayout* layout) {
	if (layout == NULL)
		return;
	QLayoutItem* item;
	while ((item = layout->takeAt(0))) {
		if (item->layout()) {
			curata_widget(item->layout());
			delete item->layout();
		}
		if (item->widget()) {
			delete item->widget();
		}
		delete item;
	}
}

void Console::InitButtons() {
	newvl = new QVBoxLayout();
	shw = new QListWidget;
	//shw2 = new QTableWidget(this);
	//shw2->setRowCount(ctr.getAll().size());
	//shw2->setColumnCount(5);
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
	show = new QPushButton("&Show");
	batch = new QPushButton("&BatchMode");
	spCrud = new QPushButton("&SpalatCrud");
	spRO = new QPushButton("&SpalatReadOnly");
	addSP = new QPushButton("&AddToWash");
	clearSP = new QPushButton("&ClearWashList");
	addRand = new QPushButton("&AddRandom");
	exportt = new QPushButton("&Export");

	hLay1 = new QHBoxLayout();
	hLay2 = new QHBoxLayout();
	hLay3 = new QHBoxLayout();
	hLay4 = new QHBoxLayout();
	hLay5 = new QHBoxLayout();
	hLay7 = new QHBoxLayout();
	vl = new QVBoxLayout();
	hl = new QHBoxLayout();

	//secondary app
	w1 = new QWidget;
	w2 = new QWidget;
	w3 = new QWidget;
	w4 = new QWidget;
	w5 = new QWidget;
	w6 = new QWidget;
	w7 = new QWidget;

	vL = new QVBoxLayout;
	fL = new QFormLayout;
	details1 = new QWidget;
	add1 = new QPushButton("&Add");
	vL2 = new QVBoxLayout;
	fL2 = new QFormLayout;
	details2 = new QWidget;
	modify1 = new QPushButton("&Modify");
	vL3 = new QVBoxLayout;
	fL3 = new QFormLayout;
	details3 = new QWidget;
	delete1 = new QPushButton("&Delete");
	vL4 = new QVBoxLayout;
	fL4 = new QFormLayout;
	details4 = new QWidget;
	filter = new QPushButton("&Filter");

	vL5 = new QVBoxLayout;
	fL5 = new QFormLayout;
	details5 = new QWidget;
	addwash = new QPushButton("&AddWash");
	vL6 = new QVBoxLayout;
	fL6 = new QFormLayout;
	details6 = new QWidget;
	addran = new QPushButton("&AddRand");
	vL7 = new QVBoxLayout;
	fL7 = new QFormLayout;
	details7 = new QWidget;
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
}

void Console::Init() {
	//meniu principal
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

	this->create_lista_subst();
	this->create_widget();

	buton->setLayout(newvl);

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
	vl->addWidget(show);
	hl->addLayout(vl);
	//hl->addWidget(shw2);
	hl->addWidget(shw);
	hl->addWidget(buton);
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
void Console::bTask1() {
	try {
		val.validateIDstring(txtId->displayText().toStdString());
		Masina mas(stoi(txtId->displayText().toStdString()), txtNr->displayText().toStdString(), txtProd->displayText().toStdString(),
			txtMod->displayText().toStdString(), txtTip->displayText().toStdString());
		val.validate(mas);
		ctr.addMasina(stoi(txtId->displayText().toStdString()), txtNr->displayText().toStdString(), txtProd->displayText().toStdString(),
			txtMod->displayText().toStdString(), txtTip->displayText().toStdString());
		shw->addItem("Masina adaugata cu succes!\n");
		//shw2->setItem(0,0,new QTableWidgetItem("Masina adaugata cu succes!\n"));
	}
	catch (MasinaRepoException& err) {
		shw->addItem(QString::fromStdString(err.getMessage()));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
	}
	catch (string& x) {
		shw->addItem(QString::fromStdString(x));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
	}
	this->create_lista_subst();
	this->curata_widget(newvl);
	this->create_widget();
}

void Console::task1() {
	txtId->setText("");
	txtNr->setText("");
	txtProd->setText("");
	txtMod->setText("");
	txtTip->setText("");
	w1->show();
	QObject::connect(add1, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		this->bTask1();
		w1->close();
		});
}

void Console::bTask2() {
	try {
		val.validateIDstring(txtId1->displayText().toStdString());
		Masina mas(stoi(txtId1->displayText().toStdString()), txtNr1->displayText().toStdString(), txtProd1->displayText().toStdString(),
			txtMod1->displayText().toStdString(), txtTip1->displayText().toStdString());
		val.validate(mas);
		ctr.updateMasina(stoi(txtId1->displayText().toStdString()), txtNr1->displayText().toStdString(), txtProd1->displayText().toStdString(),
			txtMod1->displayText().toStdString(), txtTip1->displayText().toStdString());
		//shw2->setItem(0,0, new QTableWidgetItem("Masina modificata cu succes!\n"));
		shw->addItem("Masina modificata cu succes!\n");
	}
	catch (MasinaRepoException& err) {
		shw->addItem(QString::fromStdString(err.getMessage()));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
	}
	catch (string& x) {
		shw->addItem(QString::fromStdString(x));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
	}
	this->create_lista_subst();
	this->curata_widget(newvl);
	this->create_widget();
}

void Console::task2() {
	txtId1->setText("");
	txtNr1->setText("");
	txtProd1->setText("");
	txtMod1->setText("");
	txtTip1->setText("");
	w2->show();
	QObject::connect(modify1, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		this->bTask2();
		w2->close();
		});
}

void Console::bTask3() {
	try {
		val.validateIDstring(txtId2->displayText().toStdString());
		ctr.deleteMasina(txtId2->displayText().toInt());
		//shw2->setItem(0,0, new QTableWidgetItem("Masina stearsa cu succes!\n"));
		shw->addItem("Masina stearsa cu succes!\n");
	}
	catch (MasinaRepoException& err) {
		shw->addItem(QString::fromStdString(err.getMessage()));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
	}
	catch (string& x) {
		shw->addItem(QString::fromStdString(x));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
	}
	this->create_lista_subst();
	this->curata_widget(newvl);
	this->create_widget();
}

void Console::task3() {
	txtId2->setText("");
	w3->show();
	QObject::connect(delete1, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		this->bTask3();
		w3->close();
		});
}

void Console::task5(int cmd) {
	txtFil->setText("");
	w4->show();
	if (cmd == 1)
		QObject::connect(filter, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		try {
			Masina mas(22, "ok", txtFil->displayText().toStdString(), "ok", "ok");
			val.validate(mas);
			showRez(ctr.filtrareProducator(txtFil->displayText().toStdString()));
		}
		catch (string x) {
			//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
			shw->addItem(QString::fromStdString(x));
		}
		w4->close();
			});
	else
		QObject::connect(filter, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		try {
			Masina mas(22, "ok", "ok", "ok", txtFil->displayText().toStdString());
			val.validate(mas);
			showRez(ctr.filtrareTip(txtFil->displayText().toStdString()));
		}
		catch (string x) {
			//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
			shw->addItem(QString::fromStdString(x));
		}
		w4->close();
			});
}

void Console::task6(int cmd) {
	//shw2->clear();
	shw->clear();
	if (cmd == 1)
		showRez(ctr.sortByNumar());
	else if (cmd == 2)
		showRez(ctr.sortByTip());
	else if (cmd == 3)
		showRez(ctr.sortByProducatorModel());
}


void Console::bTask7() {
	try {
		val.validateID(txtNrSp->displayText().toInt());
		ctr.addToCos(txtNrSp->displayText().toInt());
		//shw2->setItem(0,0, new QTableWidgetItem("Masina adaugata cu succes!\n"));
		shw->addItem("Masina adaugata cu succes!\n");
	}
	catch (MasinaRepoException& err) {
		shw->addItem(QString::fromStdString(err.getMessage()));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
	}
	catch (string& x) {
		shw->addItem(QString::fromStdString(x));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
	}
}

void Console::task7() {
	//shw2->clear();
	shw->clear();
	txtNrSp->setText("");
	w5->show();
	QObject::connect(addwash, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		this->bTask7();
		w5->close();
		});
}

void Console::task8() {
	//shw2->clear();
	shw->clear();
	ctr.golesteCos();
	//shw2->setItem(0,0, new QTableWidgetItem("Lista de masini s-a golit!\n"));
	shw->addItem("Lista de masini s-a golit!\n");
}

void Console::bTask9() {
	try {
		val.validateRnd(txtRnd->displayText().toStdString());
		ctr.addRandom(txtRnd->displayText().toInt());
		//shw2->setItem(0,0, new QTableWidgetItem("Masini adaugate cu succes!\n"));
		shw->addItem("Masini adaugate cu succes!\n");
	}
	catch (MasinaRepoException& err) {
		shw->addItem(QString::fromStdString(err.getMessage()));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
	}
	catch (string& x) {
		shw->addItem(QString::fromStdString(x));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
	}
}

void Console::task9() {
	//shw2->clear();
	shw->clear();
	txtRnd->setText("");
	w6->show();
	QObject::connect(addran, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		this->bTask9();
		w6->close();
		});
}

void Console::bTask10() {
	try {
		string file = txtExp->displayText().toStdString();
		file += ".csv";
		ctr.exportSrv(file);
		//shw2->setItem(0,0, new QTableWidgetItem("Lista de masini salvata cu succes!\n"));
		shw->addItem("Lista de masini salvata cu succes!\n");
	}
	catch (MasinaRepoException& err) {
		shw->addItem(QString::fromStdString(err.getMessage()));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
	}
	catch (string& x) {
		shw->addItem(QString::fromStdString(x));
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
	}
}

void Console::task10() {
	//shw2->clear();
	shw->clear();
	txtExp->setText("");
	w7->show();
	QObject::connect(exportt2, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		this->bTask10();
		w7->close();
		});
}

void Console::task11() {
	//shw2->clear();
	shw->clear();
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
				shw->addItem(QString::fromStdString(err.getMessage()));
				//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
			}
			catch (string& x) {
				shw->addItem(QString::fromStdString(x));
				//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
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
				shw->addItem(QString::fromStdString(err.getMessage()));
				//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
			}
			catch (string& x) {
				shw->addItem(QString::fromStdString(x));
				//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
			}
		}
		else if (words[0] == "del") {
			if (words.size() == 2)
				try {
				val.validateIDstring(words[1]);
				ctr.deleteMasina(stoi(words[1]));
			}
			catch (MasinaRepoException& err) {
				shw->addItem(QString::fromStdString(err.getMessage()));
				//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
			}
			catch (string& x) {
				shw->addItem(QString::fromStdString(x));
				//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(x)));
			}
		}
		else
			//shw2->setItem(0,0, new QTableWidgetItem("Comanda invalida!\n"));
			shw->addItem("Comanda invalida!\n");
	}
	//shw2->setItem(0,0, new QTableWidgetItem("Batch Mode finalizat!\n"));
	shw->addItem("Batch Mode finalizat!\n");
}

void Console::showRez(vector <Masina> rez) {
	if (rez.size() == 0)
		shw->addItem("Nu exista masini!\n");
	for (int i = 0; i < rez.size(); i++)
		showMas(rez[i]);
}

void Console::showMas(Masina& m) {
	shw->addItem(QString::fromStdString("Id: "
		+ to_string(m.getID()) + " | Numar de inmatriculare: " + m.getNumar()
		+ " | Producator: " + m.getProducator() + " | Model: "
		+ m.getModel() + " | Tip: " + m.getTip() + '\n'));
}

void Console::showAll() {
	if (ctr.getAll().size() == 0)
		shw->addItem("Nu exista masini!\n");
	vector <Masina> Mas = ctr.getAll();
	for (auto& Masina : Mas)
		showMas(Masina);
}

void Console::task12() {
	//shw2->clear();
	shw->clear();
	try {
		ctr.undo();
		//shw2->setItem(0,0, new QTableWidgetItem("Undo realizat cu succes!\n"));
		shw->addItem("Undo realizat cu succes!\n");
	}
	catch (MasinaRepoException& err) {
		//shw2->setItem(0,0, new QTableWidgetItem(QString::fromStdString(err.getMessage())));
		shw->addItem(QString::fromStdString(err.getMessage()));
	}
	this->create_lista_subst();
	this->curata_widget(newvl);
	this->create_widget();
}

void Console::task13() {
	CosGUICuLista* cosGUI = new CosGUICuLista{ ctr.getCos() };
	cosGUI->show();
	/*
	CosGUILabel* cosGUI2 = new CosGUILabel{ ctr.getCos() };
	cosGUI2->show();

	CosGUICuTabel* cosGUI3 = new CosGUICuTabel{ ctr.getCos() };
	cosGUI3->show();

	CosGUICuTabel* cosGUI4 = new CosGUICuTabel{ ctr.getCos() };
	cosGUI4->show();

	CosGUICuTabel* cosGUI5 = new CosGUICuTabel{ ctr.getCos() };
	cosGUI5->show();*/
}

void Console::task14() {
	Desenator* desen = new Desenator(ctr);
	desen->show();
}
void Console::connect() {
	QObject::connect(show, &QPushButton::clicked, [&]() {
		//shw2->clear();
		shw->clear();
		this->showAll();
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