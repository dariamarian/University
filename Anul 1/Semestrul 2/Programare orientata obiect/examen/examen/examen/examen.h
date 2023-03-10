#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_examen.h"

class examen : public QMainWindow
{
    Q_OBJECT

public:
    examen(QWidget *parent = nullptr);
    ~examen();

private:
    Ui::examenClass ui;
};
