from errors.exceptions import ValidationError


class ValidatorDiscipline(object):

    def valideaza(self, disciplina):
        """
            arunca exceptia ValidationError daca stringurile sunt goale sau id-ul nu corespunde
        """
        errors = ""
        if disciplina.get_id_disciplina() < 0:
            errors += "\nid invalid"
        if disciplina.get_nume_disciplina() == "":
            errors += "\nnume invalid"
        if disciplina.get_profesor_disciplina() == "":
            errors += "\nprofesor invalid"
        if len(errors) > 0:
            raise ValidationError(errors)

    def valideaza_id(self, id_disciplina):
        errors = ""
        if id_disciplina < 0:
            errors += "id invalid\n"
