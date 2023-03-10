from errors.exceptions import ValidationError


class ValidatorStudent(object):

    def valideaza(self, student):
        """
            arunca exceptia ValidationError daca stringurile sunt goale sau id-ul nu corespunde
        """
        errors = ""
        if student.get_id_student() < 0:
            errors += "id invalid\n"
        if student.get_nume_student() == "":
            errors += "nume invalid\n"
        if len(errors) > 0:
            raise ValidationError(errors)

    def valideaza_id(self, id_student):
        errors = ""
        if id_student < 0:
            errors += "id invalid\n"

