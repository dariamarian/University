import random
import unittest

from errors.exceptions import ValidationError, RepositoryError

from domain.studentEntity import Student
from domain.disciplinaEntity import Discipline
from domain.notaEntity import Nota

from validation.studentValidator import ValidatorStudent
from validation.disciplinaValidator import ValidatorDiscipline
from validation.notaValidator import ValidatorNota

from infrastructure.studentRepo import RepoStudent, RepoStudentFile
from infrastructure.disciplinaRepo import RepoDiscipline, RepoDisciplineFile
from infrastructure.notaRepo import RepoNota, RepoNotaFile

from business.studentService import ServiceStudent
from business.disciplinaService import ServiceDiscipline
from business.notaService import ServiceNota

from sort.sorters import BubbleSort, ShellSort

import unittest


class Teste(unittest.TestCase):

    def run_all_tests(self):
        print("Incepem testele")
        self.__test_creeaza_student()
        self.__test_valideaza_student()
        self.__test_adauga_student_repo()
        self.__test_adauga_student_srv()
        self.__test_sterge_student_repo()
        self.__test_sterge_student_srv()
        self.__test_modifica_student_repo()
        self.__test_modifica_student_srv()
        self.__test_cauta_student()

        self.__test_creeaza_disciplina()
        self.__test_valideaza_disciplina()
        self.__test_adauga_disciplina_repo()
        self.__test_adauga_disciplina_srv()
        self.__test_sterge_disciplina_repo()
        self.__test_sterge_disciplina_srv()
        self.__test_modifica_disciplina_repo()
        self.__test_modifica_disciplina_srv()
        self.__test_cauta_disciplina()

        self.__test_creeaza_nota()

        self.__test_statistica1()
        self.__test_statistica2()

        self.__test_bubble_sort()
        self.__test_shell_sort()
        print("Am finalizat testele")

    def __test_bubble_sort(self):
        vector = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        random.shuffle(vector)
        BubbleSort(vector)
        assert(vector == [0, 1, 2, 3, 4, 5, 6, 7, 8, 9])
        vector = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        random.shuffle(vector)
        BubbleSort(vector, reverse=True)
        assert (vector == [9, 8, 7, 6, 5, 4, 3, 2, 1, 0])

    def __test_shell_sort(self):
        vector = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        random.shuffle(vector)
        ShellSort(vector, len(vector))
        assert (vector == [0, 1, 2, 3, 4, 5, 6, 7, 8, 9])

    def __test_creeaza_student(self):
        id_student = 123
        nume_student = "Pop"
        student = Student(id_student, nume_student)

        self.assertTrue(student.get_id_student() == id_student)
        self.assertTrue(student.get_nume_student() == nume_student)

    def __test_valideaza_student(self):
        id_student = 123
        nume_student = "Pop"

        id_invalid = -34
        nume_invalid = ""

        valid_student = ValidatorStudent()

        student = Student(id_student, nume_student)
        self.assertTrue(ValidationError, valid_student.valideaza(student))

        student = Student(id_invalid, nume_student)
        self.assertRaises(ValidationError, lambda: valid_student.valideaza(student))

        student = Student(id_student, nume_invalid)
        self.assertRaises(ValidationError, lambda: valid_student.valideaza(student))

        student = Student(id_invalid, nume_invalid)
        self.assertRaises(ValidationError, lambda: valid_student.valideaza(student))

    def test_creeaza_repo_student_vid(self):
        with open("testare/test_studenti.txt", "w") as f:
            f.write("")
        repo = RepoStudentFile("testare/test_studenti.txt")

        self.assertTrue(repo.__len__() == 0)
        return repo

    def __test_adauga_student_repo(self):
        repo = self.test_creeaza_repo_student_vid()

        id_student = 124
        nume_student = "Pop"

        student = Student(id_student, nume_student)
        repo.adauga_student(student)

        self.assertTrue(repo.__len__() == 1)

    def __test_adauga_student_srv(self):
        repo = RepoStudent()
        valid = ValidatorStudent()
        srv = ServiceStudent(valid, repo)

        all = srv.get_all_studenti()
        self.assertTrue(len(all) == 0)

        id_student = 124
        nume_student = "Pop"

        srv.adauga_student(id_student, nume_student)

        all = srv.get_all_studenti()
        self.assertTrue(len(all) == 1)

    def __test_sterge_student_repo(self):
        repo = self.test_creeaza_repo_student_vid()
        id_student = 123
        nume_student = "Pop"
        student = Student(id_student, nume_student)
        repo.adauga_student(student)

        id_student2 = 10
        nume_student2 = "Popa"
        student2 = Student(id_student2, nume_student2)
        repo.adauga_student(student2)

        self.assertTrue(repo.__len__() == 2)

        id1 = student.get_id_student()
        repo.stergere_student_dupa_id(id1)
        self.assertTrue(repo.__len__() == 1)

    def __test_sterge_student_srv(self):
        repo = RepoStudent()
        valid = ValidatorStudent()
        srv = ServiceStudent(valid, repo)

        all = srv.get_all_studenti()
        self.assertTrue(len(all) == 0)

        id_student = 123
        nume_student = "Pop"
        srv.adauga_student(id_student, nume_student)
        all = srv.get_all_studenti()
        self.assertTrue(len(all) == 1)

        srv.stergere_student(id_student)
        all = srv.get_all_studenti()
        self.assertTrue(len(all) == 0)

    def __test_modifica_student_repo(self):
        repo = self.test_creeaza_repo_student_vid()

        id_student = 123
        nume_student = "Pop"
        student = Student(id_student, nume_student)
        repo.adauga_student(student)
        self.assertTrue(repo.__len__() == 1)

        nume_nou_student = "Popa"

        repo.modificare_nume_student_dupa_id(id_student, nume_nou_student)
        studentul = student.get_nume_student()
        self.assertTrue(studentul != nume_nou_student)

    def __test_modifica_student_srv(self):
        repo = RepoStudent()
        valid = ValidatorStudent()
        srv = ServiceStudent(valid, repo)

        id_student = 123
        nume_student = "Pop"
        srv.adauga_student(id_student, nume_student)

        inv_nume_student = ""
        self.assertRaises(ValidationError, lambda: srv.modificare_student(id_student, inv_nume_student))

    def __test_cauta_student(self):
        repo = self.test_creeaza_repo_student_vid()

        id_student = 123
        nume_student = "Pop"
        studentul = Student(id_student, nume_student)
        repo.adauga_student(studentul)

        self.assertTrue(repo.__len__() == 1)

    def __test_creeaza_disciplina(self):
        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"
        disc = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)

        self.assertTrue(disc.get_id_disciplina() == id_disciplina)
        self.assertTrue(disc.get_nume_disciplina() == nume_disciplina)
        self.assertTrue(disc.get_profesor_disciplina() == profesor_disciplina)

    def __test_valideaza_disciplina(self):
        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"

        id_invalid = -34
        nume_invalid = ""
        profesor_invalid = ""

        valid_disciplina = ValidatorDiscipline()

        disc = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)
        self.assertTrue(ValidationError, valid_disciplina.valideaza(disc))

        disc = Discipline(id_invalid, nume_disciplina, profesor_disciplina)
        self.assertRaises(ValidationError, lambda: valid_disciplina.valideaza(disc))

        disc = Discipline(id_invalid, nume_invalid, profesor_disciplina)
        self.assertRaises(ValidationError, lambda: valid_disciplina.valideaza(disc))

        disc = Discipline(id_invalid, nume_disciplina, profesor_invalid)
        self.assertRaises(ValidationError, lambda: valid_disciplina.valideaza(disc))

    def test_creeaza_repo_disciplina_vid(self):
        with open("testare/test_discipline.txt", "w") as f:
            f.write("")
        repo = RepoDisciplineFile("testare/test_discipline.txt")

        self.assertTrue(repo.__len__() == 0)
        return repo

    def __test_adauga_disciplina_repo(self):
        repo = self.test_creeaza_repo_disciplina_vid()

        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"

        disc = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)
        repo.adauga_disciplina(disc)

        self.assertTrue(repo.__len__() == 1)

    def __test_adauga_disciplina_srv(self):
        repo = RepoDiscipline()
        valid = ValidatorDiscipline()
        srv = ServiceDiscipline(valid, repo)

        all = srv.get_all_discipline()
        self.assertTrue(len(all) == 0)

        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"

        srv.adauga_discipline(id_disciplina, nume_disciplina, profesor_disciplina)

        all = srv.get_all_discipline()
        self.assertTrue(len(all) == 1)

    def __test_sterge_disciplina_repo(self):
        repo = self.test_creeaza_repo_disciplina_vid()

        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"
        disc = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)
        repo.adauga_disciplina(disc)

        id_disciplina2 = 938
        nume_disciplina2 = "Mate"
        profesor_disciplina2 = "Popescu"
        disc = Discipline(id_disciplina2, nume_disciplina2, profesor_disciplina2)
        repo.adauga_disciplina(disc)

        self.assertTrue(repo.__len__() == 2)

        id1 = disc.get_id_disciplina()
        repo.stergere_disciplina_dupa_id(id1)
        self.assertTrue(repo.__len__() == 1)

    def __test_sterge_disciplina_srv(self):
        repo = RepoDiscipline()
        valid = ValidatorDiscipline()
        srv = ServiceDiscipline(valid, repo)

        all = srv.get_all_discipline()
        self.assertTrue(len(all) == 0)

        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"
        srv.adauga_discipline(id_disciplina, nume_disciplina, profesor_disciplina)

        all = srv.get_all_discipline()
        self.assertTrue(len(all) == 1)

        srv.stergere_discipline(id_disciplina)
        all = srv.get_all_discipline()
        self.assertTrue(len(all) == 0)

    def __test_modifica_disciplina_repo(self):
        repo = self.test_creeaza_repo_disciplina_vid()

        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"
        disc = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)
        repo.adauga_disciplina(disc)
        self.assertTrue(repo.__len__() == 1)

        nume_nou_disciplina = "Bio"
        repo.modificare_nume_disciplina_dupa_id(id_disciplina, nume_nou_disciplina)
        disc2 = disc.get_nume_disciplina()
        self.assertTrue(disc2 != nume_nou_disciplina)

        profesor_nou_disciplina = "Marian"
        repo.modificare_profesor_disciplina_dupa_id(id_disciplina, profesor_nou_disciplina)
        disc2 = disc.get_profesor_disciplina()
        self.assertTrue(disc2 != profesor_nou_disciplina)

    def __test_modifica_disciplina_srv(self):
        repo = RepoDiscipline()
        valid = ValidatorDiscipline()
        srv = ServiceDiscipline(valid, repo)

        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"
        srv.adauga_discipline(id_disciplina, nume_disciplina, profesor_disciplina)

        inv_nume_disciplina = ""
        #self.assertRaises(ValidationError, lambda: srv.modificare_nume_disciplina(id_disciplina, inv_nume_disciplina))

    def __test_cauta_disciplina(self):
        repo = self.test_creeaza_repo_disciplina_vid()

        id_disciplina = 167
        nume_disciplina = "Info"
        profesor_disciplina = "Ionescu"
        disc = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)
        repo.adauga_disciplina(disc)

        self.assertTrue(repo.__len__() == 1)

    def __test_creeaza_nota(self):
        id_nota = 1
        student = "[123] Georgiana"
        disciplina = "[1123] Info Profesorul: Popescu"
        nota_student = 8
        nota = Nota(id_nota, student, disciplina, nota_student)
        self.assertTrue(nota.get_id_nota() == id_nota)
        self.assertTrue(nota.get_student() == student)
        self.assertTrue(nota.get_disciplina() == disciplina)
        self.assertTrue(nota.get_nota_student() == nota_student)

    def test_creeaza_repo_note_vid(self):
        repo = RepoNota()
        self.assertTrue(repo.__len__() == 0)
        return repo

    def __test_statistica1(self):
        repo_nota = RepoNota()
        repo_discipline = RepoDiscipline()
        repo_student = RepoStudent()

        valid_nota = ValidatorNota()
        valid_discipline = ValidatorDiscipline()

        srv = ServiceNota(valid_nota, valid_discipline, repo_nota, repo_discipline, repo_student)

        repo_stu = self.test_creeaza_repo_student_vid()
        repo_dis = self.test_creeaza_repo_disciplina_vid()
        repo_note = self.test_creeaza_repo_note_vid()

        note = srv.get_all_note()

        id = 12
        nume = "Daria"
        student1 = Student(id, nume)

        id2 = 123
        nume2 = "Alex"
        student2 = Student(id2, nume2)

        id3 = 1234
        nume3 = "Vlad"
        student3 = Student(id3, nume3)

        id4 = 12345
        nume4 = "Andra"
        student4 = Student(id4, nume4)

        repo_stu.adauga_student(student1)
        repo_stu.adauga_student(student2)
        repo_stu.adauga_student(student3)
        repo_stu.adauga_student(student4)

        id_disc = 45
        nume_disc = "Info"
        prof = "Popescu"
        disc1 = Discipline(id_disc, nume_disc, prof)

        id_disc2 = 126
        nume_disc2 = "Mate"
        prof2 = "Ion"
        disc2 = Discipline(id_disc2, nume_disc2, prof2)

        repo_dis.adauga_disciplina(disc1)
        repo_dis.adauga_disciplina(disc2)

        id_nota = 1
        nota_student = 4
        nota1 = Nota(id_nota, student1, disc1, nota_student)

        id_nota2 = 2
        nota_student2 = 8
        nota2 = Nota(id_nota2, student2, disc1, nota_student2)

        id_nota3 = 3
        nota_student3 = 6
        nota3 = Nota(id_nota3, student3, disc2, nota_student3)

        id_nota4 = 4
        nota_student4 = 7
        nota4 = Nota(id_nota4, student4, disc1, nota_student4)

        id_nota5 = 5
        nota_student5 = 7
        nota5 = Nota(id_nota5, student4, disc2, nota_student5)

        repo_note.adauga_nota(nota1)
        repo_note.adauga_nota(nota2)
        repo_note.adauga_nota(nota3)
        repo_note.adauga_nota(nota4)
        repo_note.adauga_nota(nota5)

        id_cautat = 45
        lista = srv.lista_ordonata_srv(id_cautat, note)
        self.assertTrue(len(lista) == 0)

    def __test_statistica2(self):
        repo_nota = RepoNota()
        repo_discipline = RepoDiscipline()
        repo_student = RepoStudent()

        valid_nota = ValidatorNota()
        valid_discipline = ValidatorDiscipline()
        valid_student = ValidatorStudent()

        srv = ServiceNota(valid_nota, valid_discipline, repo_nota, repo_discipline, repo_student)
        srv_stud = ServiceStudent(valid_student, repo_student)

        studenti = srv_stud.get_all_studenti()
        note = srv.get_all_note()

        repo_stu = self.test_creeaza_repo_student_vid()
        repo_dis = self.test_creeaza_repo_disciplina_vid()
        repo_note = self.test_creeaza_repo_note_vid()

        id = 12
        nume = "Daria"
        student1 = Student(id, nume)

        id2 = 123
        nume2 = "Alex"
        student2 = Student(id2, nume2)

        id3 = 1234
        nume3 = "Vlad"
        student3 = Student(id3, nume3)

        id4 = 12345
        nume4 = "Andra"
        student4 = Student(id4, nume4)

        id5 = 123456
        nume5 = "Andrei"
        student5 = Student(id5, nume5)

        id6 = 1234567
        nume6 = "Denis"
        student6 = Student(id6, nume6)

        id7 = 12345678
        nume7 = "Roxana"
        student7 = Student(id7, nume7)

        id8 = 123456789
        nume8 = "Georgiana"
        student8 = Student(id8, nume8)

        id9 = 1234567890
        nume9 = "Razvan"
        student9 = Student(id9, nume9)

        id10 = 12345678901
        nume10 = "Oana"
        student10 = Student(id10, nume10)

        id_disc = 45
        nume_disc = "Info"
        prof = "Popescu"
        disc1 = Discipline(id_disc, nume_disc, prof)

        id_disc2 = 126
        nume_disc2 = "Mate"
        prof2 = "Ion"
        disc2 = Discipline(id_disc2, nume_disc2, prof2)

        id_disc3 = 678
        nume_disc3 = "Logica"
        prof3 = "Ionescu"
        disc3 = Discipline(id_disc3, nume_disc3, prof3)

        repo_stu.adauga_student(student1)
        repo_stu.adauga_student(student2)
        repo_stu.adauga_student(student3)
        repo_stu.adauga_student(student4)
        repo_stu.adauga_student(student5)
        repo_stu.adauga_student(student6)
        repo_stu.adauga_student(student7)
        repo_stu.adauga_student(student8)
        repo_stu.adauga_student(student9)
        repo_stu.adauga_student(student10)

        repo_dis.adauga_disciplina(disc1)
        repo_dis.adauga_disciplina(disc2)
        repo_dis.adauga_disciplina(disc3)

        id_nota = 1
        nota_student = 4
        nota1 = Nota(id_nota, student1, disc1, nota_student)

        id_nota2 = 2
        nota_student2 = 8
        nota2 = Nota(id_nota2, student1, disc3, nota_student2)

        id_nota3 = 3
        nota_student3 = 6
        nota3 = Nota(id_nota3, student2, disc3, nota_student3)

        id_nota4 = 4
        nota_student4 = 7
        nota4 = Nota(id_nota4, student3, disc2, nota_student4)

        id_nota5 = 5
        nota_student5 = 7
        nota5 = Nota(id_nota5, student4, disc3, nota_student5)

        id_nota6 = 6
        nota_student6 = 2
        nota6 = Nota(id_nota6, student4, disc3, nota_student6)

        id_nota7 = 7
        nota_student7 = 10
        nota7 = Nota(id_nota7, student5, disc1, nota_student7)

        id_nota8 = 8
        nota_student8 = 2
        nota8 = Nota(id_nota8, student6, disc2, nota_student8)

        id_nota9 = 9
        nota_student9 = 5
        nota9 = Nota(id_nota9, student6, disc1, nota_student9)

        id_nota10 = 10
        nota_student10 = 9
        nota10 = Nota(id_nota10, student7, disc3, nota_student10)

        id_nota11 = 11
        nota_student11 = 8
        nota11 = Nota(id_nota11, student8, disc2, nota_student11)

        id_nota12 = 12
        nota_student12 = 7
        nota12 = Nota(id_nota12, student8, disc1, nota_student12)

        id_nota13 = 13
        nota_student13 = 3
        nota13 = Nota(id_nota13, student8, disc3, nota_student13)

        id_nota14 = 14
        nota_student14 = 8
        nota14 = Nota(id_nota14, student9, disc1, nota_student14)

        id_nota15 = 15
        nota_student15 = 10
        nota15 = Nota(id_nota15, student10, disc2, nota_student15)

        repo_note.adauga_nota(nota1)
        repo_note.adauga_nota(nota2)
        repo_note.adauga_nota(nota3)
        repo_note.adauga_nota(nota4)
        repo_note.adauga_nota(nota5)
        repo_note.adauga_nota(nota6)
        repo_note.adauga_nota(nota7)
        repo_note.adauga_nota(nota8)
        repo_note.adauga_nota(nota9)
        repo_note.adauga_nota(nota10)
        repo_note.adauga_nota(nota11)
        repo_note.adauga_nota(nota12)
        repo_note.adauga_nota(nota13)
        repo_note.adauga_nota(nota14)
        repo_note.adauga_nota(nota15)

        lista_medie = srv.primii_20_la_suta(studenti, note)
        self.assertTrue(len(lista_medie) == 0)

