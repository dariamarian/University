#pragma once
#pragma once
#include<QAbstractListModel>
#include <qcolor.h>
#include "Masina.h"
#include <vector>
#include <qdebug.h>
class MyListModel :public QAbstractListModel {
	std::vector<Masina> cars;
public:
	MyListModel(const std::vector<Masina>& cars) :cars{ cars } {
	}

	int rowCount(const QModelIndex& parent = QModelIndex()) const override {
		return cars.size();
	}

	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			qDebug() << "get row:" << index.row();
			auto id = cars[index.row()].getID();
			auto nr = cars[index.row()].getNumar();
			auto pr = cars[index.row()].getProducator();
			auto md = cars[index.row()].getModel();
			auto tp = cars[index.row()].getTip();
			return QString::fromStdString(to_string(id)) + " " + QString::fromStdString(nr) + " " + QString::fromStdString(pr) + " " + QString::fromStdString(md) + " " + QString::fromStdString(tp);
		}
		return QVariant{};
	}
	void setCars(const vector<Masina>& cars) {
		this->cars = cars;
		auto topLeft = createIndex(0, 0);
		auto bottomR = createIndex(rowCount(), 0);
		emit dataChanged(topLeft, bottomR);
	}
};