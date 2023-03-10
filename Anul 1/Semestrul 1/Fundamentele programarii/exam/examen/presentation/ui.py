import datetime


class Consola(object):

    def __init__(self, service_eveniment):
        self._service_eveniment = service_eveniment

    def _adauga_eveniment(self):
        """
            adauga un eveniment
        :return:
        """
        data = input("Introduceti data: ")
        ora = input("Introduceti ora: ")
        descriere = input("Introduceti descriere: ")
        self._service_eveniment.adauga_eveniment(data, ora, descriere)

    def _view_all_evenimente(self):
        """
            afiseaza evenimentele
        :return:
        """
        print(self._service_eveniment.view_all_evenimente())

    def _afisare_zi_curenta(self):
        """
            afiseaza evenimentele din ziua curenta
        :return:
        """
        an = str(datetime.date.today().year)
        luna = str(datetime.date.today().month)
        zi = str(datetime.date.today().day)
        data2 = zi + ".0" + luna + "." + an
        self._service_eveniment.afisare_zi_curenta(data2)

    def _afisare_zi_dorita(self):
        """
            afiseaza evenimentele din ziua dorita
        :return:
        """
        data = input("Introduceti data dorita: ")
        print(self._service_eveniment.afisare_zi_dorita(data))

    def _meniu(self):
        """
            printeaza meniul
        :return:
        """
        print("1. Adauga eveniment")
        print("2. Afiseaza evenimentele din data dorita")
        print("3. Export evenimente")
        print("4. Exit")

    def run(self):
        """
            ruleaza programul
        :return:
        """
        while True:
            print("Evenimentele din ziua curenta sunt: ")
            self._afisare_zi_curenta()
            self._meniu()
            cmd = int(input("Introduceti comanda dorita: "))
            if cmd == 4:
                return
            elif cmd == 1:
                self._adauga_eveniment()
            elif cmd == 2:
                self._afisare_zi_dorita()
            else:
                print("comanda invalida")

