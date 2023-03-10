from domain.eventEntity import Eveniment


class RepoEvent(object):

    def __init__(self):
        self._evenimente = []

    def adauga_eveniment(self, evenimentul):
        """
            adauga un eveniment la lista de evenimente
        :param evenimentul:
        :return:
        """
        for eveniment in self._evenimente:
            if eveniment == evenimentul:
                return "eveniment existent"
        self._evenimente.append(evenimentul)
        print("Eveniment adaugat cu succes")

    def get_all_evenimente(self):
        """
            returneaza lista de evenimente
        :return:
        """
        return self._evenimente

    def view_all_evenimente(self):
        """
            afiseaza lista de evenimente
        :return:
        """
        strr = ""
        for event in self._evenimente:
            strr += (str(event))
        return strr

    def afisare_zi_curenta(self, data_curenta):
        """
            afiseaza evenimentele din ziua curenta
        :param data_curenta:
        :return:
        """
        lista = []
        for event in self._evenimente:
            if event.get_data_eveniment() == str(data_curenta):
                lista.append(event)
        for i in range(0, len(lista)-1):
            for j in range(i+1, len(lista)):
                camp1 = lista[i].get_ora_eveniment().split(":")
                ora = int(camp1[0])
                minut = camp1[1]
                camp2 = lista[j].get_ora_eveniment().split(":")
                ora2 = int(camp2[0])
                minut2 = camp2[1]
                if ora > ora2:
                    aux = lista[i]
                    lista[i] = lista[j]
                    lista[j] = aux
        for i in range(len(lista)):
            print(lista[i])

    def afisare_zi_dorita(self, data_dorita):
        """
            afiseaza evenimentele din ziua dorita
        :param data_dorita:
        :return:
        """
        strr = ""
        for event in self._evenimente:
            if event.get_data_eveniment() == str(data_dorita):
                strr += (str(event))
                strr += "\n"
        return strr


class RepoEventFile(RepoEvent):

    def __init__(self, filepath):
        RepoEvent.__init__(self)
        self._filepath = filepath

    def __ReadAllFromFile(self):
        """
            citeste evenimentele din fisier
        :return:
        """
        with open(self._filepath, "r") as f:
            self._evenimente = []
            lines = f.readlines()
            for line in lines:
                line.strip()
                if len(line)>0:
                    campuri=line.split(",")
                    data = campuri[0]
                    ora = campuri[1]
                    descriere = campuri[2]
                    event = Eveniment(data, ora, descriere)
                    self._evenimente.append(event)

    def __WriteAllToFile(self):
        """
            scrie evenimentele in fisier
        :return:
        """
        evenimente_str = ""
        for event in self._evenimente:
            evenimente_str += event.get_data_eveniment() + "," + event.get_ora_eveniment()+ "," + event.get_descriere_eveniment() + "\n"
        with open(self._filepath, "w") as f:
            f.write(evenimente_str)

    def __AppendToFile(self, event):
        """
            adauga un eveniment in fisier
        :param event:
        :return:
        """
        with open(self._filepath, "a") as f:
            f.write(event.get_data_eveniment() + "," + event.get_ora_eveniment() + "," + event.get_descriere_eveniment() + "\n")

    def adauga_eveniment(self, event):
        self.__ReadAllFromFile()
        RepoEvent.adauga_eveniment(self, event)
        self.__AppendToFile(event)

    def get_all_evenimente(self):
        self.__ReadAllFromFile()
        return RepoEvent.get_all_evenimente(self)

    def view_all_evenimente(self):
        self.__ReadAllFromFile()
        return RepoEvent.view_all_evenimente(self)

    def afisare_zi_curenta(self, data_curenta):
        self.__ReadAllFromFile()
        return RepoEvent.afisare_zi_curenta(self, data_curenta)

    def afisare_zi_dorita(self, data_dorita):
        self.__ReadAllFromFile()
        return RepoEvent.afisare_zi_curenta(self, data_dorita)