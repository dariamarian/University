from prezentare.user_interface import Consola

from validation.studentValidator import ValidatorStudent
from validation.disciplinaValidator import ValidatorDiscipline
from validation.notaValidator import ValidatorNota

from infrastructure.studentRepo import RepoStudent, RepoStudentFile
from infrastructure.disciplinaRepo import RepoDiscipline, RepoDisciplineFile
from infrastructure.notaRepo import RepoNota, RepoNotaFile

from business.studentService import ServiceStudent
from business.disciplinaService import ServiceDiscipline
from business.notaService import ServiceNota

from testare.teste import Teste


if __name__ == '__main__':

    filepathStudenti = "infrastructure/students.txt"
    filepathDiscipline = "infrastructure/disciplines.txt"
    filepathNote = "infrastructure/note.txt"

    valid_student = ValidatorStudent()
    valid_discipline = ValidatorDiscipline()
    valid_nota = ValidatorNota()

    repo_student = RepoStudentFile(filepathStudenti)
    repo_discipline = RepoDisciplineFile(filepathDiscipline)
    repo_nota = RepoNotaFile(filepathNote)

    srv_student = ServiceStudent(valid_student, repo_student)
    srv_discipline = ServiceDiscipline(valid_discipline, repo_discipline)
    srv_nota = ServiceNota(valid_nota, valid_discipline, repo_nota, repo_discipline, repo_student)

    ui = Consola(srv_student, srv_discipline, srv_nota)
    teste = Teste()
    teste.run_all_tests()
    ui.run()
