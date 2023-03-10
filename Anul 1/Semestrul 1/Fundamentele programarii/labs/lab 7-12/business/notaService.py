from domain.notaEntity import Nota
from domain.notaDTO import notaDTO
from domain.medieEntity import Medie
from domain.mediediscEntity import Medie_Dis
from sort.sorters import BubbleSort, ShellSort


class ServiceNota(object):

    def __init__(self, valid_nota, valid_discipline, repo_nota, repo_discipline, repo_student):
        self.__valid_nota = valid_nota
        self.__valid_discipline = valid_discipline
        self.__repo_nota = repo_nota
        self.__repo_discipline = repo_discipline
        self.__repo_student = repo_student

    def adauga_nota(self, id_nota, id_student, id_disciplina, nota_student):
        """
            adauga o nota unui student
        """
        st = self.__repo_student.cauta_student_dupa_id(id_student)
        disc = self.__repo_discipline.cauta_disciplina_dupa_id(id_disciplina)
        if st is not None and disc is not None:
            nota = notaDTO(id_nota, id_student, id_disciplina, nota_student)
            self.__valid_nota.valideaza(nota)
            self.__repo_nota.adauga_nota(nota)
            return nota
        else:
            raise Exception("Nu exista student sau disciplina cu id-ul dat\n")

    def get_all_note(self):
        """
            afiseaza lista cu notele
        """
        notaDTOs = self.__repo_nota.get_all()
        note = []
        for notaDTO in notaDTOs:
            student = self.__repo_student.cauta_student_dupa_id(notaDTO.get_studentID())
            disciplina = self.__repo_discipline.cauta_disciplina_dupa_id(notaDTO.get_disciplinaID())
            nota = Nota(notaDTO.get_notaID(), student, disciplina, notaDTO.get_nota_student())
            note.append(nota)
        return note

    def view_all_note(self):
        return self.__repo_nota.view_all()

    def lista_ordonata_srv(self, id_disciplina, note):
        """
            returneaza lista de studenți și notele lor la o disciplină dată, ordonat: alfabetic după nume, după notă.
        """
        self.__valid_discipline.valideaza_id(id_disciplina)
        lista_note = []
        for nota in note:
            id_discipline = nota.get_id_disciplina(nota.get_disciplina())
            if id_discipline == id_disciplina:
                id_nota = nota.get_id_nota()
                student = nota.get_student()
                disc = nota.get_disciplina()
                nota_stud = nota.get_nota_student()
                nota2 = Nota(id_nota, student, disc, nota_stud)
                lista_note.append(nota2)
        BubbleSort(lista_note, key=lambda x: (x.get_nume_student(x.get_student()), x.get_nota_student()))
        #ShellSort(lista_note, len(lista_note), key = lambda x: (x.get_nume_student(x.get_student()), x.get_nota_student()))
        #lista_note = sorted(lista_note, key=lambda x: (x.get_nume_student(x.get_student()), x.get_nota_student()), reverse=False)
        return lista_note

    def primii_20_la_suta(self, studenti, note):
        """
            returneaza primii 20% din studenți ordonati dupa media notelor la toate disciplinele (nume și notă)
        """
        lista_medie = []
        for studentul in studenti:
            suma_notelor = 0
            nr_notelor = 0
            for nota in note:
                if nota.get_student() == studentul:
                    suma_notelor += nota.get_nota_student()
                    nr_notelor += 1
            if nr_notelor != 0:
                media_notelor = suma_notelor / nr_notelor
                medie = Medie(studentul.get_nume_student(), media_notelor)
                lista_medie.append(medie)
        ShellSort(lista_medie, len(lista_medie), key=lambda x: x.get_medie(), reverse=False)
        #lista_medie = sorted(lista_medie, key=lambda x: x.get_medie(), reverse=False)
        return lista_medie

    def top_50_srv(self, discipline, note):
        """
            afiseaza top 50% discipline cu cea mai mare medie a notelor(notele de la fiecare student per disciplina),
            sortate alfabetic dupa numele profesorului
        """
        lista_medie = []
        lista_2 = []
        for disciplina in discipline:
            suma_notelor = 0
            nr_notelor = 0
            for nota in note:
                if nota.get_disciplina() == disciplina:
                    suma_notelor += nota.get_nota_student()
                    nr_notelor += 1
            if nr_notelor != 0:
                media_notelor = suma_notelor / nr_notelor
                medie = Medie_Dis(disciplina, media_notelor)
                lista_medie.append(medie)
        BubbleSort(lista_medie, key=lambda x: x.get_medie(), reverse=True)
        #lista_medie = sorted(lista_medie, key=lambda x: x.get_medie(), reverse=True)
        len2 = int(len(lista_medie) / 2)
        for i in range(len2):
            lista_2.append(lista_medie[i])
        #lista_2 = sorted(lista_2, key=lambda x: x.get_profesor_disciplina(x.get_disciplina()), reverse=False)
        BubbleSort(lista_2, key=lambda x: x.get_profesor_disciplina(x.get_disciplina()), reverse=False)
        for i in range(len(lista_2)):
            print(str(lista_2[i]))

