from domain.disciplinaEntity import Discipline


class ServiceDiscipline(object):

    def __init__(self, valid_discipline, repo_discipline):
        """
            initializeaza service-ul
        """
        self.__valid_discipline = valid_discipline
        self.__repo_discipline = repo_discipline

    def adauga_discipline(self, id_disciplina, nume_disciplina, profesor_disciplina):
        """
            adauga o disciplina citita de la tastatura si o valideaza
        """
        disciplina = Discipline(id_disciplina, nume_disciplina, profesor_disciplina)
        self.__valid_discipline.valideaza(disciplina)
        self.__repo_discipline.adauga_disciplina(disciplina)
        return disciplina

    def stergere_discipline(self, id_disciplina):
        """
            sterge disciplina corespunzatoare id-ului introdus si valideaza id-ul
        """
        self.__valid_discipline.valideaza_id(id_disciplina)
        self.__repo_discipline.stergere_disciplina_dupa_id(id_disciplina)

    def modificare_nume_disciplina(self, id_disciplina, nume_nou_disciplina):
        """
            modifica disciplina corespunzatoare id-ului introdus si valideaza id-ul
        """
        self.__valid_discipline.valideaza_id(id_disciplina)
        self.__repo_discipline.modificare_nume_disciplina_dupa_id(id_disciplina, nume_nou_disciplina)

    def modificare_profesor_disciplina(self, id_disciplina, profesor_nou_disciplina):
        """
            modifica disciplina corespunzatoare id-ului introdus si valideaza id-ul
        """
        self.__valid_discipline.valideaza_id(id_disciplina)
        self.__repo_discipline.modificare_profesor_disciplina_dupa_id(id_disciplina, profesor_nou_disciplina)

    def cautare_disciplina(self, id_disciplina):
        """
            cauta disciplina corespunzatoare id-ului introdus si valideaza id-ul
        """
        self.__valid_discipline.valideaza_id(id_disciplina)
        self.__repo_discipline.cauta_disciplina_dupa_id_recursiv_l(id_disciplina)

    def get_all_discipline(self):
        """
            afiseaza lista cu disciplinele
        """
        return self.__repo_discipline.get_all()

    def view_all_dis(self):
        return self.__repo_discipline.view_all()

