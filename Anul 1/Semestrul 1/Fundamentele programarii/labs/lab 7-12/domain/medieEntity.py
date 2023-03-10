class Medie(object):
    """
            Creeaza clasa Medie
            Definim atributele nume_student si medie
            Avem get-ere pentru extragerea caracteristicilor unei medii
    """
    def __init__(self, nume_student, medie):
        self.__nume_student = nume_student
        self.__medie = medie

    def get_nume_student(self):
        return self.__nume_student

    def get_medie(self):
        return self.__medie

    def __str__(self):
        """
            ajuta la afisarea mai frumoasa a listei
        """
        return "Studentul: " + str(self.__nume_student) + " are media notelor: " + str(self.__medie)

