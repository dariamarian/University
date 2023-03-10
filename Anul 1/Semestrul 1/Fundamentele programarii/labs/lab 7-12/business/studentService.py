from domain.studentEntity import Student


class ServiceStudent(object):

    def __init__(self, valid_student, repo_student):
        """
            initializeaza service-ul
        """
        self.__valid_student = valid_student
        self.__repo_student = repo_student

    def adauga_student(self, id_student, nume_student):
        """
            adauga un student citit de la tastatura si il valideaza
        """
        student = Student(id_student, nume_student)
        self.__valid_student.valideaza(student)
        self.__repo_student.adauga_student(student)
        return student

    def stergere_student(self, id_student):
        """
            sterge studentul corespunzator id-ului introdus si valideaza id-ul
        """
        self.__valid_student.valideaza_id(id_student)
        self.__repo_student.stergere_student_dupa_id(id_student)

    def modificare_student(self, id_student, nume_nou_student):
        """
            modifica studentul corespunzator id-ului introdus si valideaza id-ul
        """
        self.__valid_student.valideaza_id(id_student)
        s = Student(id_student, nume_nou_student)
        self.__valid_student.valideaza(s)
        self.__repo_student.modificare_nume_student_dupa_id(id_student, nume_nou_student)

    def cautare_student(self, id_student):
        """
            cauta studentul corespunzator id-ului introdus si valideaza id-ul
        """
        self.__valid_student.valideaza_id(id_student)
        self.__repo_student.cauta_student_dupa_id_recursiv_l(id_student)

    def get_all_studenti(self):
        """
            afiseaza lista cu studentii
        """
        return self.__repo_student.get_all()

    def view_all_st(self):
        return self.__repo_student.view_all()

    def filtrare_studenti(self, prefix):
        return self.__repo_student.filtrare_nume(prefix)

