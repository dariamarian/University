from domain.eventEntity import Eveniment


class ServiceEvent(object):

    def __init__(self, validator_eveniment, repo_eveniment):
        self._validator_eveniment = validator_eveniment
        self._repo_eveniment = repo_eveniment

    def adauga_eveniment(self, data, ora, descriere):
        """
            adauga un eveniment
        :param data:
        :param ora:
        :param descriere:
        :return:
        """
        event = Eveniment(data, ora, descriere)
        self._validator_eveniment.valideaza_eveniment(event)
        self._repo_eveniment.adauga_eveniment(event)
        return event

    def get_all_evenimente(self):
        """
            returneaza lista de evenimente
        :return:
        """
        return self._repo_eveniment.get_all_evenimente()

    def view_all_evenimente(self):
        """
            afiseaza lista de evenimente
        :return:
        """
        return self._repo_eveniment.view_all_evenimente()

    def afisare_zi_curenta(self, data):
        """
            afiseaza evenimentele din ziua curenta
        :param data:
        :return:
        """
        self._repo_eveniment.afisare_zi_curenta(data)

    def afisare_zi_dorita(self, data):
        """
            afiseaza evenimentele din ziua dorita
        :param data:
        :return:
        """
        return self._repo_eveniment.afisare_zi_dorita(data)
