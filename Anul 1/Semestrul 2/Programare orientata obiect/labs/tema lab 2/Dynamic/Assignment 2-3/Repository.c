#include "Repository.h"
#include <stdlib.h>
#include <assert.h>
#include <string.h>

Repository* CreateRepo(int maxLength)
{
    Repository* repo = (Repository*)malloc(sizeof(Repository));

    if (repo == NULL)
        return NULL;

    repo->array = CreateArray(maxLength);

    return repo;
}

Repository DuplicateRepo(Repository source)
{
    Repository newRepo;

    DynamicArray* newArray = CreateArray(1);
    DuplicateArray(*source.array, newArray);

    newRepo.array = newArray;
    
    return newRepo;
}

void DestroyRepo(Repository* repo)
{
    DestroyArray(repo->array);
    repo->array = NULL;
    
    free(repo);
}

void AddNewParticipant(Repository* repo, Participant newParticipant)
{
    AddElement(repo->array, newParticipant);
}

void RemoveNewParticipant(Repository* repo, int position)
{
    RemoveElement(repo->array, position);
}

void UpdateParticipantnumeRepo(Repository* repo, int position, char* newnume)
{
    SetNume(&repo->array->elements[position], newnume);
}

void UpdateParticipantprenumeRepo(Repository* repo, int position, char* newprenume)
{
    SetPrenume(&repo->array->elements[position], newprenume);
}

void UpdateParticipantscorRepo(Repository* repo, int position, int newscor)
{
    SetScor(&repo->array->elements[position], newscor);
}

int GetLength(Repository* repo)
{
    return repo->array->length;
}

Participant GetElementOnPosition(Repository* repo, int position)
{
    if (position > repo->array->length)
        return CreateParticipant("Invalid", "Invalid", 0);
    return repo->array->elements[position];
}

void InitRepo(Repository* repository)
{
    AddNewParticipant(repository, CreateParticipant("Marian", "Daria", 75));
    AddNewParticipant(repository, CreateParticipant("Molnar", "Eveline", 90));
    AddNewParticipant(repository, CreateParticipant("Mogage", "Nicolae", 100));
    AddNewParticipant(repository, CreateParticipant("Vasile", "Mihai", 50));
    AddNewParticipant(repository, CreateParticipant("Berciu", "Liviu", 99));
    AddNewParticipant(repository, CreateParticipant("Popescu", "Andreea", 43));
    AddNewParticipant(repository, CreateParticipant("Moldoveanu", "Anamaria", 5));
}

void RunAllTestsRepo()
{
    TestCreateDestroyRepo();
    TestAdd();
    TestRemove();
    TestUpdaters();
    TestCopying();
    TestGetters();
}

void TestCreateDestroyRepo()
{
    Repository* repo = CreateRepo(12);
    assert(repo != NULL);
    assert(repo->array->maxLength == 12);
    assert(repo->array->length == 0);

    DestroyRepo(repo);
}

void TestAdd()
{
    Repository* repo = CreateRepo(1);

    Participant noname = CreateParticipant("Nume", "Prenume", 25);
    AddNewParticipant(repo, noname);
    assert(Equal(repo->array->elements[0], noname));
    assert(repo->array->length == 1);

    Participant noname2 = CreateParticipant("Nume2", "Prenume2", 100);
    AddNewParticipant(repo, noname2);
    assert(Equal(repo->array->elements[1], noname2));
    assert(repo->array->maxLength == 2);
    assert(repo->array->length == 2);

    DestroyRepo(repo);
}

void TestRemove()
{
    Repository* repo = CreateRepo(1);

    Participant noname = CreateParticipant("Nume", "Prenume", 25);
    AddNewParticipant(repo, noname);
    Participant noname2 = CreateParticipant("Nume2", "Prenume2", 100);
    AddNewParticipant(repo, noname2);
    Participant noname3= CreateParticipant("Nume3", "Prenume3", 78);
    AddNewParticipant(repo, noname3);

    RemoveNewParticipant(repo, 1);
    assert(Equal(repo->array->elements[0], noname));
    assert(Equal(repo->array->elements[1], noname3));
    assert(repo->array->length == 2);
    assert(repo->array->maxLength == 4);

    AddNewParticipant(repo, noname2);
    RemoveNewParticipant(repo, 0);
    assert(Equal(repo->array->elements[0], noname3));
    assert(Equal(repo->array->elements[1], noname2));
    assert(repo->array->length == 2);
    assert(repo->array->maxLength == 4);

    DestroyRepo(repo);
}

void TestUpdaters()
{
    Repository* repo = CreateRepo(3);

    Participant noname = CreateParticipant("Nume", "Prenume", 25);
    AddNewParticipant(repo, noname);
    Participant noname2 = CreateParticipant("Nume2", "Prenume2", 100);
    AddNewParticipant(repo, noname2);
    Participant noname3 = CreateParticipant("Nume3", "Prenume3", 78);
    AddNewParticipant(repo, noname3);
    
    UpdateParticipantnumeRepo(repo, 0, "NumeNou");
    assert(strcmp(repo->array->elements[0].nume, "NumeNou") == 0);

    UpdateParticipantscorRepo(repo, 0, 100);
    assert(repo->array->elements[0].scor == 100);

    DestroyRepo(repo);
}

void TestCopying()
{
    Repository* repo = CreateRepo(1);

    Participant noname = CreateParticipant("Nume", "Prenume", 25);
    AddNewParticipant(repo, noname);
    Participant noname2 = CreateParticipant("Nume2", "Prenume2", 100);
    AddNewParticipant(repo, noname2);
    Participant noname3 = CreateParticipant("Nume3", "Prenume3", 78);
    AddNewParticipant(repo, noname3);

    Repository repoCopy = DuplicateRepo(*repo);
    assert(repoCopy.array->length == repo->array->length);
    assert(repoCopy.array->maxLength == repo->array->maxLength);

    for (int i = 0; i < repo->array->length; ++i)
    {
        assert(Equal(repo->array->elements[i], repoCopy.array->elements[i]));
    }
       
    DestroyRepo(repo);
    DestroyArray(repoCopy.array);
}

void TestGetters()
{
    Repository* repo = CreateRepo(1);

    Participant noname = CreateParticipant("Nume", "Prenume", 25);
    AddNewParticipant(repo, noname);
    Participant noname2 = CreateParticipant("Nume2", "Prenume2", 100);
    AddNewParticipant(repo, noname2);
    Participant noname3 = CreateParticipant("Nume3", "Prenume3", 78);
    AddNewParticipant(repo, noname3);

    assert(GetLength(repo) == 3);
    assert(Equal(GetElementOnPosition(repo, 0), noname));
    assert(Equal(GetElementOnPosition(repo, 1), noname2));
    assert(Equal(GetElementOnPosition(repo, 2), noname3));

    Participant invalidParticipant = CreateParticipant("Invalid", "Invalid", 0);
    assert(Equal(GetElementOnPosition(repo, 100), invalidParticipant));

    DestroyRepo(repo);
}

