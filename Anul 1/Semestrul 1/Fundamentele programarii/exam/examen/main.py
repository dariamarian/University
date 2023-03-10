from validation.validators import ValidatorEvent
from business.eventService import ServiceEvent
from infrastructure.eventRepo import RepoEvent, RepoEventFile
from presentation.ui import Consola
from testing.teste import Teste
import datetime

if __name__ == '__main__':

    filepathEvenimente = "infrastructure/evenimente.txt"
    validor_eveniment = ValidatorEvent()
    repo_eveniment = RepoEventFile(filepathEvenimente)
    service_eveniment = ServiceEvent(validor_eveniment, repo_eveniment)

    ui = Consola(service_eveniment)
    teste = Teste()
    teste.run_all_tests()
    ui.run()