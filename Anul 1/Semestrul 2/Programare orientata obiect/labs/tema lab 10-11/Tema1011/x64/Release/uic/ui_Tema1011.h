/********************************************************************************
** Form generated from reading UI file 'Tema1011.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_TEMA1011_H
#define UI_TEMA1011_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Tema1011Class
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *Tema1011Class)
    {
        if (Tema1011Class->objectName().isEmpty())
            Tema1011Class->setObjectName(QString::fromUtf8("Tema1011Class"));
        Tema1011Class->resize(600, 400);
        menuBar = new QMenuBar(Tema1011Class);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        Tema1011Class->setMenuBar(menuBar);
        mainToolBar = new QToolBar(Tema1011Class);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        Tema1011Class->addToolBar(mainToolBar);
        centralWidget = new QWidget(Tema1011Class);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        Tema1011Class->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(Tema1011Class);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        Tema1011Class->setStatusBar(statusBar);

        retranslateUi(Tema1011Class);

        QMetaObject::connectSlotsByName(Tema1011Class);
    } // setupUi

    void retranslateUi(QMainWindow *Tema1011Class)
    {
        Tema1011Class->setWindowTitle(QCoreApplication::translate("Tema1011Class", "Tema1011", nullptr));
    } // retranslateUi

};

namespace Ui {
    class Tema1011Class: public Ui_Tema1011Class {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_TEMA1011_H
