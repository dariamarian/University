#include "gui.h"

void Console::InitButtons() {
	newvl = new QVBoxLayout();
	shw = new QListWidget;
	w = new QWidget();

	SortByPr = new QPushButton("&Sortare dupa pret");
	SortByBr = new QPushButton("&Sortare dupa brand");
	SortByMd = new QPushButton("&Sortare dupa model");
	Nesortat = new QPushButton("&Nesortat");

	det = new QLineEdit;
	det->setReadOnly(true);

	hLay1 = new QHBoxLayout();
	hLay2 = new QHBoxLayout();
	vl = new QVBoxLayout();
	hl = new QHBoxLayout();
}

void Console::Init() {
	hLay1->addWidget(SortByPr);
	hLay1->addWidget(SortByBr);
	hLay2->addWidget(SortByMd);
	hLay2->addWidget(Nesortat);
	vl->addLayout(hLay1);
	vl->addLayout(hLay2);
	hl->addLayout(vl);
	hl->addWidget(shw);
	hl->addWidget(det);
	w->setLayout(hl);
	w->resize(720, 300);
}

void Console::sortare(int c)
{
	vector <Telefon> lista;
	for (auto& t : srv.srv_getAll())
		lista.push_back(t);
	if (c == 1)
		srv.sortare(1, lista);
	else if (c == 2)
		srv.sortare(2, lista);
	else if (c == 3)
		srv.sortare(3, lista);
	show_all(lista);
}

void Console::show_detalii() {
	auto selectedItems = shw->selectedItems();
	if (!selectedItems.isEmpty()) {
		auto item = selectedItems.at(0);
		QString info = item->data(Qt::UserRole).toString();
		det->setText(info);
	}
}

void Console::show_all(vector<Telefon>& lista) {
	shw->clear();
	if (lista.size() == 0)
		shw->addItem("Nu exista telefoane!");
	else {
		for (auto& t : lista) {
			QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(t.tostring()));

			item->setData(Qt::UserRole, QString::fromStdString(t.tostring2()));

			if (t.getBrand() == "Samsung")
				item->setData(Qt::BackgroundRole, QBrush{ Qt::red});
			else if (t.getBrand() == "Huawei")
				item->setData(Qt::BackgroundRole, QBrush{ Qt::yellow});
			else if (t.getBrand() == "Apple")
				item->setData(Qt::BackgroundRole, QBrush{ Qt::blue});
			else 
				item->setData(Qt::BackgroundRole, QBrush{ Qt::black});
			shw->addItem(item);
		}
	}
}

void Console::connect() {
	QObject::connect(shw, &QListWidget::itemSelectionChanged, [&]() {
		show_detalii();
		});
	QObject::connect(SortByPr, &QPushButton::clicked, [&]() {
		sortare(1);
		});
	QObject::connect(SortByBr, &QPushButton::clicked, [&]() {
		sortare(2);
		});
	QObject::connect(SortByMd, &QPushButton::clicked, [&]() {
		sortare(3);
		});
	QObject::connect(Nesortat, &QPushButton::clicked, [&]() {
		sortare(4);
		});
	w->show();
}