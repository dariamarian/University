from errors.exceptions import ValidationError


class ValidatorNota(object):

    def valideaza(self, nota):
        """
            arunca exceptia ValidationError daca id-urile sau nota nu corespund
        """
        errors = ""
        if nota.get_notaID() < 0:
            errors += "id invalid\n"
        if nota.get_studentID() < 0:
            errors += "id student invalida\n"
        if nota.get_disciplinaID() < 0:
            errors += "id student invalida\n"
        if nota.get_nota_student() < 0 or nota.get_nota_student() > 10:
            errors += "nota invalida\n"
        if len(errors) > 0:
            raise ValidationError(errors)

    def valideaza_id(self, id_nota):
        errors = ""
        if id_nota < 0:
            errors += "id invalid\n"

