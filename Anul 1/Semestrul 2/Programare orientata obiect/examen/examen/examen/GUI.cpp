#include "GUI.h"

using namespace std;
void Console::Init() {
	setFixedSize(1000, 400);
	hl = new QHBoxLayout;
	setLayout(hl);
	sh = new QTableView;
	model = new MyTableModel(srv.srv_getAll());
	sh->setModel(model);

	sh->setFixedSize(550, 250);

	w = new QWidget;
	hl->addWidget(w);
	fl = new QFormLayout;
	w->setLayout(fl);

	lblTitlu = new QLabel("Titlu");
	txtTitlu = new QLineEdit;
	lblArtist = new QLabel("Artist");
	txtArtist = new QLineEdit;
	lblGen = new QLabel("Gen");
	txtGen = new QLineEdit;
	fl->addRow(lblTitlu, txtTitlu);
	fl->addRow(lblArtist, txtArtist);
	fl->addRow(lblGen, txtGen);

	add = new QPushButton("&Add");
	fl->addWidget(add);
	w->setFixedSize(250, 250);

	del = new QPushButton("&Delete");
	fl->addWidget(del);
	hl->addWidget(sh);
}

void Console::connectSignals() {
	QObject::connect(sh->selectionModel(),&QItemSelectionModel::selectionChanged, [this]() {
			int row = sh->selectionModel()->selectedIndexes().at(0).row();
			auto IdIndex = sh->model()->index(row, 0);
			int id = sh->model()->data(IdIndex, Qt::DisplayRole).toInt();
			lastid = id;
			Melodie m = srv.srv_find(id);
		});
	QObject::connect(add, &QPushButton::clicked, [&]() {
		try 
		{
			v.validate(txtTitlu->text().toStdString(), txtArtist->text().toStdString(), txtGen->text().toStdString());
			srv.srv_add(txtTitlu->text().toStdString(), txtArtist->text().toStdString(), txtGen->text().toStdString());
			refreshList();
			repaint();
		}
		catch (string& err) {
			QMessageBox::information(this, "Error", QString::fromStdString(err));
		}
		});
	QObject::connect(del, &QPushButton::clicked, [&]() {
		if (lastid == -1)
		{
			QMessageBox::information(this, "Error", "Nothing is selected"); return;
		}
		srv.srv_del(lastid);
		//lastid = -1;
		refreshList();
		repaint();
		});
}

void Console::refreshList() {
	model->setMelodii(srv.srv_getAll());
}

int MyTableModel::rowCount(const QModelIndex& parent) const {
	return lista.size();
}

int MyTableModel::columnCount(const QModelIndex& parent) const
{
	return 6;
}

QVariant MyTableModel::data(const QModelIndex& index, int role) const {
	if (role == Qt::DisplayRole) {
		Melodie m = lista[index.row()];
		int nr = -1, nr2 = -1;
		for (auto el : lista)
			if (m.getArtist() == el.getArtist())
				nr++;
		for (auto el : lista)
			if (m.getGen() == el.getGen())
				nr2++;

		switch (index.column())
		{
		case 0: return QString::number(m.getID()); break;
		case 1: return QString::fromStdString(m.getTitlu()); break;
		case 2: return QString::fromStdString(m.getArtist()); break;
		case 3: return QString::fromStdString(m.getGen()); break;
		case 4: return QString::number(nr); break;
		case 5: return QString::number(nr2); break;
		default:
			break;
		}
	}
	return QVariant();
}

void MyTableModel::setMelodii(vector<Melodie>& list) {
	lista = list;
	QModelIndex topleft = createIndex(0, 0);
	QModelIndex bottomright = createIndex(rowCount(), columnCount());
	emit dataChanged(topleft, bottomright);
	emit layoutChanged();
}

void Console::paintEvent(QPaintEvent* ev) {
	QPainter painter{ this };
	vector < Melodie > lista = srv.srv_getAll();
	vector < int > raze(4, 5);
	vector < string > genuri = { "pop", "rock", "folk", "disco" };
	vector < QPoint > centers = { QPoint{40, 40}, QPoint{width() - 40, 40}, QPoint{40, height() - 40}, QPoint{width() - 40, height() - 40} };
	std::map < string, int > evidence;
	for (const auto& m : lista)
		evidence[m.getGen()]++;
	for (int gen = 0; gen < 4; ++gen)
	{
		for (int cont = 0; cont < evidence[genuri[gen]]; ++cont)
		{
			painter.drawEllipse(centers[gen], raze[gen], raze[gen]);
			raze[gen] += 10;
		}
	}
}