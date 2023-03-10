class Eveniment(object):
    """
        clasa Eveniment
        atributele data, ora, descriere de tip string
        definim getteri pentru acestea
    """

    def __init__(self, data, ora, descriere):
        self._data = data
        self._ora = ora
        self._descriere = descriere

    def get_data_eveniment(self):
        return self._data

    def get_ora_eveniment(self):
        return self._ora

    def get_descriere_eveniment(self):
        return self._descriere

    def __str__(self):
        return self._data + " " + self._ora + " " + self._descriere
