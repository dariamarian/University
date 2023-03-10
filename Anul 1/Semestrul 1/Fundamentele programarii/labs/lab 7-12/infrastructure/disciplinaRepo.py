from errors.exceptions import RepositoryError
from domain.disciplinaEntity import Discipline

class RepoDiscipline(object):

    def __init__(self):
        self._disciplina = []

    def __len__(self):
        return len(self._disciplina)

    def adauga_disciplina(self, discipline):
        """
            adauga o disciplina citita de la tastatura
        """
        for disciplina in self._disciplina:
            if disciplina == discipline:
                raise RepositoryError("id disciplina existent")
        self._disciplina.append(discipline)

    def stergere_disciplina_dupa_id(self, id_disciplina):
        """
            sterge disciplina corespunzatoare id-ului introdus
        """
        ok = True
        for disciplina in self._disciplina:
            if disciplina.get_id_disciplina() == id_disciplina:
                return self._disciplina.remove(disciplina)
        if ok:
            raise RepositoryError("id disciplina inexistent")

    def modificare_nume_disciplina_dupa_id(self, id_disciplina, nume_nou_disciplina):
        """
            modifica numele disciplinei corespunzatoare id-ului introdus
        """
        ok = True
        for disciplina in self._disciplina:
            if disciplina.get_id_disciplina() == id_disciplina:
                disciplina.set_nume_disciplina(nume_nou_disciplina)
                return
        if ok:
            raise RepositoryError("id disciplina inexistent")

    def modificare_profesor_disciplina_dupa_id(self, id_disciplina, profesor_nou_disciplina):
        """
            modifica profesorul disciplinei corespunzatoare id-ului introdus
        """
        ok = True
        for disciplina in self._disciplina:
            if disciplina.get_id_disciplina() == id_disciplina:
                disciplina.set_profesor_disciplina(profesor_nou_disciplina)
                return
        if ok:
            raise RepositoryError("id disciplina inexistent")

    def cauta_disciplina_dupa_id(self, id_disciplina):
        """
            cauta disciplina corespunzatoare id-ului introdus
        """
        ok = True
        for disciplina in self._disciplina:
            if disciplina.get_id_disciplina() == id_disciplina:
                return disciplina
        if ok:
            raise RepositoryError("id disciplina inexistent")

    def cauta_disciplina_dupa_id_recursiv_l(self, id_disciplina):
        """
            cauta disciplina corespunzatoare id-ului introdus recursiv
        """
        lista = self._disciplina
        return self.cauta_disciplina_dupa_id_recursiv(lista, id_disciplina)

    def cauta_disciplina_dupa_id_recursiv(self, lista, id_disciplina):
        """
            cauta disciplina corespunzatoare id-ului introdus recursiv
        """
        if lista == []:
            raise RepositoryError("id disciplina inexistent")
        if lista[0].get_id_disciplina() == id_disciplina:
            return lista[0]
        else:
            return self.cauta_disciplina_dupa_id_recursiv(lista[1:], id_disciplina)

    def get_all(self):
        """
            afiseaza lista cu discipline
        """
        return (self._disciplina)

    def view_all(self):
        strr = ""
        for st in self._disciplina:
            strr += (str(st))
            strr += "\n"
        return strr


class RepoDisciplineFile(RepoDiscipline):
    """
        clasa pentru Repo cu fisiere in care suprascriem toate functiile din RepoDiscipline
    """
    def __init__(self, filepath):
        RepoDiscipline.__init__(self)
        self.__filepath = filepath

    def __ReadAllFromFile(self):
        with open(self.__filepath, "r") as f:
            self._disciplina = []
            lines = f.readlines()
            lung = len(lines)
            for i in range(0, lung, 3):
                date1 = lines[i]
                date2 = lines[i+1]
                date3 = lines[i+2]
                date1 = date1.strip()
                date2 = date2.strip()
                date3 = date3.strip()
                if len(date1) > 0:
                    id_disciplina = int(date1)
                    nume_disciplina = date2
                    profesor_disciplina = date3
                    disc = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)
                    self._disciplina.append(disc)

    def __WriteAllToFile(self):
        with open(self.__filepath, "w") as f:
            for disc in self._disciplina:
                f.write(f"{str(disc.get_id_disciplina())}\n{disc.get_nume_disciplina()}\n{disc.get_profesor_disciplina()}\n")

    def __AppendToFile(self, disc):
        with open(self.__filepath, "a") as f:
            f.write(f"{str(disc.get_id_disciplina())}\n{disc.get_nume_disciplina()}\n{disc.get_profesor_disciplina()}\n")

    def adauga_disciplina(self, discipline):
        self.__ReadAllFromFile()
        RepoDiscipline.adauga_disciplina(self, discipline)
        self.__AppendToFile(discipline)

    def stergere_disciplina_dupa_id(self, id_disciplina):
        self.__ReadAllFromFile()
        RepoDiscipline.stergere_disciplina_dupa_id(self, id_disciplina)
        self.__WriteAllToFile()

    def cauta_disciplina_dupa_id(self, id_disciplina):
        self.__ReadAllFromFile()
        return RepoDiscipline.cauta_disciplina_dupa_id(self, id_disciplina)

    def cauta_disciplina_dupa_id_recursiv_l(self, id_disciplina):
        self.__ReadAllFromFile()
        return RepoDiscipline.cauta_disciplina_dupa_id_recursiv_l(self, id_disciplina)

    def modificare_nume_disciplina_dupa_id(self, id_disciplina, nume_nou_disciplina):
        self.__ReadAllFromFile()
        RepoDiscipline.modificare_nume_disciplina_dupa_id(self, id_disciplina, nume_nou_disciplina)
        self.__WriteAllToFile()

    def modificare_profesor_disciplina_dupa_id(self, id_disciplina, profesor_nou_disciplina):
        self.__ReadAllFromFile()
        RepoDiscipline.modificare_profesor_disciplina_dupa_id(self, id_disciplina, profesor_nou_disciplina)
        self.__WriteAllToFile()

    def get_all(self):
        self.__ReadAllFromFile()
        return RepoDiscipline.get_all(self)

    def __len__(self):
        self.__ReadAllFromFile()
        return RepoDiscipline.__len__(self)

    def view_all(self):
        self.__ReadAllFromFile()
        return RepoDiscipline.view_all(self)

