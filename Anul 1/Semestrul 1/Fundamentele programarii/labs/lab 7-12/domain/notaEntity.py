class Nota(object):
    """
            Creeaza clasa Nota
            Definim atributele ID nota, student, disciplina si nota_student
            Avem get-ere pentru extragerea caracteristicilor unei note
    """
    def __init__(self, id_nota, student, disciplina, nota_student):
        self.__id_nota = id_nota
        self.__student = student
        self.__disciplina = disciplina
        self.__nota_student = nota_student

    def get_id_nota(self):
        return self.__id_nota

    def get_student(self):
        return self.__student

    def get_id_student(self, student):
        return student.get_id_student()

    def get_nume_student(self, student):
        return student.get_nume_student()

    def get_disciplina(self):
        return self.__disciplina

    def get_id_disciplina(self, disciplina):
        return disciplina.get_id_disciplina()

    def get_nume_disciplina(self, disciplina):
        return disciplina.get_nume_disciplina()

    def get_profesor_disciplina(self, disciplina):
        return disciplina.get_profesor_disciplina()

    def get_nota_student(self):
        return self.__nota_student

    def __eq__(self, other):
        """
            verifica egalitatea id-urilor
        """
        return self.__id_nota == other.__id_nota

    def __str__(self):
        """
            ajuta la afisarea mai frumoasa a listei
        """
        return "Studentul: " + str(self.__student) + " are nota " + str(self.__nota_student) + " la disciplina " + str(self.__disciplina)

