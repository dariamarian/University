from errors.exceptions import ValidationError, RepositoryError

from domain.studentEntity import Student
from domain.disciplinaEntity import Discipline
from domain.notaEntity import Nota

from infrastructure.studentRepo import RepoStudent
from infrastructure.disciplinaRepo import RepoDiscipline
from infrastructure.notaRepo import RepoNota

import random
import string


class Consola(object):

    def __init__(self, srv_student, srv_discipline, srv_nota):
        self.__srv_student = srv_student
        self.__srv_discipline = srv_discipline
        self.__srv_nota = srv_nota

    def __ui_adauga_student(self):
        """
            adauga un student citit de la tastatura
        """
        try:
            id_student = int(input("id student: "))
        except ValueError:
            print("id numeric invalid")
            return
        nume_student = input("Nume student: ")
        print(self.__srv_student.adauga_student(id_student, nume_student))
        print("Student adaugat cu succes")

    def __ui_sterge_student(self):
        """
            sterge studentul corespunzator id-ului introdus
        """
        try:
            id_student = int(input("id student: "))
        except ValueError:
            print("id numeric invalid")
            return
        self.__srv_student.stergere_student(id_student)
        print("Student sters cu succes")

    def __ui_modifica_student(self):
        """
            modifica studentul corespunzator id-ului introdus
        """
        try:
            id_student = int(input("id student: "))
        except ValueError:
            print("id numeric invalid")
            return
        nume_nou_student = input("Nume nou student: ")
        self.__srv_student.modificare_student(id_student, nume_nou_student)
        print("Student modificat cu succes")

    def __ui_cauta_student(self):
        """
           cauta studentul corespunzator id-ului introdus
        """
        try:
            id_student = int(input("id student: "))
        except ValueError:
            print("id numeric invalid")
            return
        self.__srv_student.cautare_student(id_student)
        print("Student gasit cu succes")

    def __ui_view_all_stud(self):
        """
            afiseaza lista cu studentii
        """
        print(self.__srv_student.view_all_st())

    def __ui_adauga_disciplina(self):
        """
            adauga o disciplina citita de la tastatura
        """
        try:
            id_disciplina = int(input("id disciplina: "))
        except ValueError:
            print("id disciplina invalid")
            return
        nume_disciplina = input("Nume disciplina: ")
        profesor_disciplina = input("Profesor disciplina: ")
        print(self.__srv_discipline.adauga_discipline(id_disciplina, nume_disciplina, profesor_disciplina))
        print("Disciplina adaugata cu succes")

    def __ui_sterge_disciplina(self):
        """
            sterge disciplina corespunzatoare id-ului introdus
        """
        try:
            id_disciplina = int(input("id disciplina: "))
        except ValueError:
            print("id numeric invalid")
            return
        self.__srv_discipline.stergere_discipline(id_disciplina)
        print("Disciplina stearsa cu succes")

    def __ui_modifica_nume_disciplina(self):
        """
            modifica disciplina corespunzatoare id-ului introdus
        """
        try:
            id_disciplina = int(input("id disciplina: "))
        except ValueError:
            print("id numeric invalid")
            return
        nume_nou_disciplina = input("Nume nou disciplina: ")
        self.__srv_discipline.modificare_nume_disciplina(id_disciplina, nume_nou_disciplina)
        print("Disciplina modificata cu succes")

    def __ui_modifica_profesor_disciplina(self):
        """
            modifica disciplina corespunzatoare id-ului introdus
        """
        try:
            id_disciplina = int(input("id disciplina: "))
        except ValueError:
            print("id numeric invalid")
            return
        profesor_nou_disciplina = input("Profesor nou disciplina: ")
        self.__srv_discipline.modificare_profesor_disciplina(id_disciplina, profesor_nou_disciplina)
        print("Disciplina modificata cu succes")

    def __ui_cauta_disciplina(self):
        """
            cauta disciplina corespunzatoare id-ului introdus
        """
        try:
            id_disciplina = int(input("id disciplina: "))
        except ValueError:
            print("id numeric invalid")
            return
        self.__srv_discipline.cautare_disciplina(id_disciplina)
        print("Disciplina gasita cu succes")

    def __ui_view_all_dis(self):
        """
            afiseaza lista cu disciplinele
        """
        print(self.__srv_discipline.view_all_dis())

    def __ui_adauga_nota(self):
        """
            adauga o nota unui student
        """
        try:
            id_nota = int(input("id nota: "))
        except ValueError:
            print("id numeric invalid")
            return
        id_student = int(input("Introduceti id-ul studentului caruia ii atribuiti nota: "))
        id_disciplina = int(input("Introduceti id-ul disciplinei pentru care atribuiti nota: "))
        try:
            nota_student = int(input("Introduceti nota: "))
        except ValueError:
            print("nota invalida")
            return
        self.__srv_nota.adauga_nota(id_nota, id_student, id_disciplina, nota_student)
        print("Nota adaugata cu succes")

    def __ui_view_all_note(self):
        """
            afiseaza lista cu notele
        """
        print(self.__srv_nota.view_all_note())

    def __ui_filtrare(self):
        """
             filtreaza lista dupa numele studentilor
        """
        prefix = str(input("Dati prefix: "))
        print(self.__srv_student.filtrare_studenti(prefix))

    def __get_id_random(self):
        id = random.randint(1, 1000)
        return id

    def __get_nota_random(self):
        nota = random.randint(1, 10)
        return nota

    def __get_string_random(self):
        length = random.randint(1,20)
        letters = string.ascii_lowercase
        result_str = ''.join(random.choice(letters) for i in range(length))
        return result_str

    def __ui_genereaza_random_studenti(self):
        nr = int(input("Introduceti numarul de studenti: "))
        for i in range(nr):
            id_student = self.__get_id_random()
            nume_student = self.__get_string_random()
            self.__srv_student.adauga_student(id_student, nume_student)
        print("Studenti generati cu succes")

    def __ui_genereaza_random_discipline(self):
        nr = int(input("Introduceti numarul de discipline: "))
        for i in range(nr):
            id_disciplina = self.__get_id_random()
            nume_disciplina = self.__get_string_random()
            profesor_disciplina = self.__get_string_random()
            self.__srv_discipline.adauga_discipline(id_disciplina, nume_disciplina, profesor_disciplina)
        print("Discipline generate cu succes")

    def __ui_genereaza_random_nota(self):
        l_studenti = self.__srv_student.get_all_studenti()
        l_discipline = self.__srv_discipline.get_all_discipline()
        nr = int(input("Introduceti numarul de note: "))
        for i in range(nr):
            id_nota = self.__get_id_random()
            student_random = l_studenti[random.randint(0, len(l_studenti)-1)]
            id_student = student_random.get_id_student()
            disciplina_random = l_discipline[random.randint(0, len(l_discipline)-1)]
            id_disciplina = disciplina_random.get_id_disciplina()
            nota_student = self.__get_nota_random()
            self.__srv_nota.adauga_nota(id_nota, id_student, id_disciplina, nota_student)
        print("Note generate cu succes")
        print(self.__srv_nota.view_all_note())

    def __ui_statistica_1(self):
        """
            afiseaza lista de studenți și notele lor la o disciplină dată, ordonat: alfabetic după nume, după notă.
        """
        note = self.__srv_nota.get_all_note()
        try:
            id = int(input("Introduceti ID-ul disciplinei dorite: "))
        except ValueError:
            print("id numeric invalid")
            return
        discipline = self.__srv_discipline.get_all_discipline()
        ok = True
        for disciplina in discipline:
            if disciplina.get_id_disciplina() == id:
                ok = False
        if ok:
            raise RepositoryError("id disciplina inexistent")
        lista = self.__srv_nota.lista_ordonata_srv(id, note)
        for i in range(len(lista)):
            print(str(lista[i]))

    def __ui_statistica_2(self):
        """
            afiseaza primii 20% din studenți ordonati dupa media notelor la toate disciplinele (nume și notă)
        """
        note = self.__srv_nota.get_all_note()
        studenti = self.__srv_student.get_all_studenti()
        print("Primii 20% din studenți ordonati dupa media notelor la toate disciplinele sunt:")
        lista_medie = self.__srv_nota.primii_20_la_suta(studenti, note)
        len2 = int(len(lista_medie) / 5)
        for i in range(len2):
            print(str(lista_medie[i]))

    def __ui_statistica_3(self):
        """
            afiseaza top 50% discipline cu cea mai mare medie a notelor(notele de la fiecare student per disciplina),
            sortate alfabetic dupa numele profesorului
        """
        discipline = self.__srv_discipline.get_all_discipline()
        note = self.__srv_nota.get_all_note()
        self.__srv_nota.top_50_srv(discipline, note)

    def meniu_principal(self):
        """
            afiseaza meniul principal
        """
        print("Comenzile posibile sunt:")
        print("1.Gestioneaza lista de studenti")
        print("2.Gestioneaza lista de discipline")
        print("3.Atribuie o nota unui student")
        print("4.Genereaza random note")
        print("5.Afiseaza notele")
        print("6.Statistica 1")
        print("7.Statistica 2")
        print("8.Statistica 3")
        print("9.Exit")

    def meniu_studenti(self):
        """
            afiseaza meniul studentilor
        """
        print("1.Adauga student")
        print("2.Afiseaza studenti")
        print("3.Modificare nume student")
        print("4.Sterge student")
        print("5.Cautare student")
        print("6.Filtrare")
        print("7.Genereaza studenti random")
        print("8.Inapoi")

    def meniu_discipline(self):
        """
            afiseaza meniul disciplinelor
        """
        print("1.Adauga disciplina")
        print("2.Afiseaza discipline")
        print("3.Modificare nume disciplina")
        print("4.Modificare profesor disciplina")
        print("5.Sterge disciplina")
        print("6.Cautare disciplina")
        print("7.Genereaza discipline random")
        print("8.Inapoi")

    def run_studenti(self):
        while True:
            self.meniu_studenti()
            cmd2 = input("Introduceti optiunea dorita: ")
            if cmd2 == "8":
                return
            elif cmd2 == "":
                continue
            elif cmd2 == "1":
                try:
                    self.__ui_adauga_student()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd2 == "2":
                self.__ui_view_all_stud()
            elif cmd2 == "3":
                try:
                    self.__ui_modifica_student()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd2 == "4":
                try:
                    self.__ui_sterge_student()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd2 == "5":
                try:
                    self.__ui_cauta_student()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd2 == "6":
                try:
                    self.__ui_filtrare()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd2 == "7":
                try:
                    self.__ui_genereaza_random_studenti()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            else:
                print("comanda nevalida!\n")

    def run_discipline(self):
        while True:
            self.meniu_discipline()
            cmd3 = input("Introduceti optiunea dorita: ")
            if cmd3 == "8":
                return
            elif cmd3 == "":
                continue
            elif cmd3 == "1":
                try:
                    self.__ui_adauga_disciplina()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd3 == "2":
                self.__ui_view_all_dis()
            elif cmd3 == "3":
                try:
                    self.__ui_modifica_nume_disciplina()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd3 == "4":
                try:
                    self.__ui_modifica_profesor_disciplina()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd3 == "5":
                try:
                    self.__ui_sterge_disciplina()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd3 == "6":
                try:
                    self.__ui_cauta_disciplina()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd3 == "7":
                try:
                    self.__ui_genereaza_random_discipline()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            else:
                print("comanda nevalida!\n")

    def run(self):
        """
            ruleaza meniul principal de tip consola
        """
        while True:
            self.meniu_principal()
            cmd = input("Introduceti optiunea dorita: ")
            if cmd == "9":
                return
            elif cmd == "":
                continue
            elif cmd == "1":
                self.run_studenti()
            elif cmd == "2":
                self.run_discipline()
            elif cmd == "3":
                try:
                    self.__ui_adauga_nota()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd == "4":
                try:
                    self.__ui_genereaza_random_nota()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd == "5":
                try:
                    self.__ui_view_all_note()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd == "6":
                try:
                    self.__ui_statistica_1()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd == "7":
                try:
                    self.__ui_statistica_2()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            elif cmd == "8":
                try:
                    self.__ui_statistica_3()
                except ValidationError as ve:
                    print("eroare validation: " + str(ve))
                except RepositoryError as re:
                    print("eroare repo: " + str(re))
            else:
                print("Comanda invalida")


class Consola2(object):

    def __init__(self, srv_student_f, srv_discipline_f, srv_nota_f):
        self.__srv_student_f = srv_student_f
        self.__srv_discipline_f = srv_discipline_f
        self.__srv_nota_f = srv_nota_f


