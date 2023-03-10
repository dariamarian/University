from errors.exceptions import RepositoryError
from domain.studentEntity import Student


class RepoStudent(object):

    def __init__(self):
        self._student = []

    def __len__(self):
        return len(self._student)

    def filtrare_nume(self, prefix):
        """
            filtreaza numele studentilor
        """
        l_filtrata = []
        for student in self._student:
            if student.get_nume_student().startswith(prefix):
                l_filtrata.append(str(student))
        if len(l_filtrata) == 0:
            return "nu exista"
        else:
            return l_filtrata

    def adauga_student(self, studentul):
        """
            adauga un student citit de la tastatura
        """
        for student in self._student:
            if student == studentul:
                raise RepositoryError("id student existent")
        self._student.append(studentul)

    def stergere_student_dupa_id(self, id_student):
        """
            sterge studentul corespunzator id-ului introdus
        """
        ok = True
        for student in self._student:
            if student.get_id_student() == id_student:
                return self._student.remove(student)
        if ok:
            raise RepositoryError("id student inexistent")

    def modificare_nume_student_dupa_id(self, id_student, nume_nou_student):
        """
            modifica numele studentului corespunzator id-ului introdus
        """
        ok = True
        for student in self._student:
            if student.get_id_student() == id_student:
                student.set_nume_student(nume_nou_student)
                return
        if ok:
            raise RepositoryError("id student inexistent")

    def cauta_student_dupa_id(self, id_student):
        """
            cauta studentul corespunzator id-ului introdus
        """
        ok = True
        for student in self._student:
            if student.get_id_student() == id_student:
                return student
        if ok:
            raise RepositoryError("id student inexistent")

    def cauta_student_dupa_id_recursiv_l(self, id_student):
        """
            cauta studentul corespunzator id-ului introdus recursiv
        """
        lista = self._student
        return self.cauta_student_dupa_id_recursiv(lista, id_student)

    def cauta_student_dupa_id_recursiv(self, lista, id_student):
        """
            cauta studentul corespunzator id-ului introdus recursiv
        """
        if lista == []:
            raise RepositoryError("id student inexistent")
        if lista[0].get_id_student() == id_student:
            return lista[0]
        else:
            return self.cauta_student_dupa_id_recursiv(lista[1:], id_student)

    def get_all(self):
        """
            afiseaza lista cu studentii
        """
        return (self._student)

    def view_all(self):
        strr = ""
        for st in self._student:
            strr += (str(st))
            strr += "\n"
        return strr


class RepoStudentFile(RepoStudent):
    """
        clasa pentru Repo cu fisiere in care suprascriem toate functiile din RepoStudent
    """
    def __init__(self, filepath):
        RepoStudent.__init__(self)
        self.__filepath = filepath

    def __ReadAllFromFile(self):
        with open(self.__filepath, "r") as f:
            self._student = []
            lines = f.readlines()
            lung = len(lines)
            for i in range(0, lung, 2):
                date1 = lines[i]
                date2 = lines[i+1]
                date1 = date1.strip()
                date2 = date2.strip()
                if len(date1) > 0:
                    id_student = int(date1)
                    nume_student = date2
                    stud = Student(id_student, nume_student)
                    self._student.append(stud)

    def __WriteAllToFile(self):
        with open(self.__filepath, "w") as f:
            for stud in self._student:
                f.write(f"{str(stud.get_id_student())}\n{stud.get_nume_student()}\n")

    def __AppendToFile(self, stud):
        with open(self.__filepath, "a") as f:
            f.write(f"{str(stud.get_id_student())}\n{stud.get_nume_student()}\n")

    def adauga_student(self, studentul):
        self.__ReadAllFromFile()
        RepoStudent.adauga_student(self, studentul)
        self.__AppendToFile(studentul)

    def stergere_student_dupa_id(self, id_student):
        self.__ReadAllFromFile()
        RepoStudent.stergere_student_dupa_id(self, id_student)
        self.__WriteAllToFile()

    def cauta_student_dupa_id(self, id_student):
        self.__ReadAllFromFile()
        return RepoStudent.cauta_student_dupa_id(self, id_student)

    def cauta_student_dupa_id_recursiv_l(self, id_student):
        self.__ReadAllFromFile()
        return RepoStudent.cauta_student_dupa_id_recursiv_l(self, id_student)

    def modificare_nume_student_dupa_id(self, id_student, nume_nou_student):
        self.__ReadAllFromFile()
        RepoStudent.modificare_nume_student_dupa_id(self, id_student, nume_nou_student)
        self.__WriteAllToFile()

    def get_all(self):
        self.__ReadAllFromFile()
        return RepoStudent.get_all(self)

    def __len__(self):
        self.__ReadAllFromFile()
        return RepoStudent.__len__(self)

    def filtrare_nume(self, prefix):
        self.__ReadAllFromFile()
        return RepoStudent.filtrare_nume(self, prefix)

    def view_all(self):
        self.__ReadAllFromFile()
        return RepoStudent.view_all(self)

