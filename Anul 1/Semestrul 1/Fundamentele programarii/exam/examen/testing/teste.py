from domain.eventEntity import Eveniment
from validation.validators import ValidatorEvent


class Teste(object):

    def run_all_tests(self):
        self.adauga_eveniment()
        #self.valideaza_eveniment()

    def adauga_eveniment(self):
        lista = []
        data = "12.01.2020"
        ora = "15:30"
        descriere = "Examen Fp"
        event = Eveniment(data, ora, descriere)
        lista.append(event)
        assert(len(lista) == 1)

    def valideaza_eveniment(self):
        data = "12.01.2020"
        ora = "15:30"
        descriere = "Examen Fp"
        valid_eveniment = ValidatorEvent
        event = Eveniment(data, ora, descriere)
        assert(valid_eveniment.valideaza_eveniment(event))