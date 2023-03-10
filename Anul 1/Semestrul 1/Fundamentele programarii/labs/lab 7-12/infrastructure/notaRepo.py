from errors.exceptions import RepositoryError
from domain.notaDTO import notaDTO


class RepoNota(object):

    def __init__(self):
        self._note = []

    def __len__(self):
        return len(self._note)

    def adauga_nota(self, nota_adaugata):
        """
            Functie care adauga o nota
        """
        for nota in self._note:
            if nota == nota_adaugata:
                raise RepositoryError("id nota existent")
        self._note.append(nota_adaugata)


    def get_all(self):
        """
            afiseaza lista cu notele
        """
        return self._note

    def view_all(self):
        strr = ""
        for nota in self._note:
            id_student = nota.get_studentID()
            id_disciplina = nota.get_disciplinaID()
            id_nota = nota.get_notaID()
            nota_student = nota.get_nota_student()
            strr += ("Studentul cu ID-ul "+ str(id_student) + " are nota " + str(nota_student) + " la disciplina cu ID-ul " + str(id_disciplina))
            strr += "\n"
        return strr


class RepoNotaFile(RepoNota):
    """
        clasa pentru Repo cu fisiere in care suprascriem toate functiile din RepoNote
    """
    def __init__(self, filepath):
        RepoNota.__init__(self)
        self.__filepath = filepath

    def __ReadAllFromFile(self):
        with open(self.__filepath, "r") as f:
            self._note = []
            lines = f.readlines()
            lung = len(lines)
            for i in range(0, lung, 4):
                date1 = lines[i]
                date2 = lines[i + 1]
                date3 = lines[i + 2]
                date4 = lines[i + 3]
                date1 = date1.strip()
                date2 = date2.strip()
                date3 = date3.strip()
                date4 = date4.strip()
                if len(date1) > 0:
                    id_nota = int(date1)
                    id_student = int(date2)
                    id_disciplina = int(date3)
                    nota_student = int(date4)
                    nota = notaDTO(id_nota, id_student, id_disciplina, nota_student)
                    self._note.append(nota)

    def __WriteAllToFile(self):
        with open(self.__filepath, "w") as f:
            for nota in self._note:
                f.write(f"{str(nota.get_notaID())}\n{str(nota.get_studentID())}\n{str(nota.get_disciplinaID())}\n{str(nota.get_nota_student())}\n")

    def __AppendToFile(self, nota):
        with open(self.__filepath, "a") as f:
            f.write(f"{str(nota.get_notaID())}\n{str(nota.get_studentID())}\n{str(nota.get_disciplinaID())}\n{str(nota.get_nota_student())}\n")

    def adauga_nota(self, nota_adaugata):
        self.__ReadAllFromFile()
        RepoNota.adauga_nota(self, nota_adaugata)
        self.__AppendToFile(nota_adaugata)

    def get_all(self):
        self.__ReadAllFromFile()
        return RepoNota.get_all(self)

    def __len__(self):
        self.__ReadAllFromFile()
        return RepoNota.__len__(self)

    def view_all(self):
        self.__ReadAllFromFile()
        return RepoNota.view_all(self)
