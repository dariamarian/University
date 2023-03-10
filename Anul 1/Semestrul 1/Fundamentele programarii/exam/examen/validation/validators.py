from errors.exceptions import ValidationError, RepositoryError


class ValidatorEvent(object):
    """
        clasa de validare
    """

    def valideaza_eveniment(self, eveniment):
        """
            valideaza un eveniment in functie de formatul datei si a orei respectiv validitatea acestora
        :param eveniment:
        :return:
        """
        errors = ""
        campuri = eveniment.get_data_eveniment().split(".")
        if len(campuri) == 3:
            zi_data = campuri[0]
            luna_data = campuri[1]
            an_data = campuri[2]
            if int(zi_data) < 1 or int(zi_data) > 31:
               errors += "ziua invalida\n"
            if int(luna_data) <1 or int(luna_data)>12:
                errors += "luna invalida\n"
            if int(an_data) < 0:
                errors += "an invalid\n"
        else:
            errors+= "format data invalid"
        campuri = eveniment.get_ora_eveniment().split(":")
        if len(campuri) == 2:
            ora = campuri[0]
            minut = campuri[1]
            if int(ora) < 0 or int(ora) > 23:
                errors += "ora invalida"
            if int(minut) < 0 or int(minut) > 60:
                errors += "minut invalid"
        else:
            errors += "format ora invalid"
        if eveniment.get_descriere_eveniment() == "":
            errors += "descriere invalida"
        if len(errors) > 0:
            raise ValidationError(errors)

