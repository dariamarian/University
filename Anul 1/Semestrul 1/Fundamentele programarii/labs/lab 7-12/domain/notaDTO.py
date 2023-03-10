class notaDTO(object):

    def __init__(self, notaID, studentID, disciplinaID, nota_student):
        self.__notaID = notaID
        self.__studentID = studentID
        self.__disciplinaID = disciplinaID
        self.__nota_student = nota_student

    def get_notaID(self):
        return self.__notaID

    def get_studentID(self):
        return self.__studentID

    def get_disciplinaID(self):
        return self.__disciplinaID

    def get_nota_student(self):
        return self.__nota_student
