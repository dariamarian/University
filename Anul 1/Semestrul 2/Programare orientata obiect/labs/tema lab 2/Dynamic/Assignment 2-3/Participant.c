#include "Participant.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include <stdlib.h>

Participant CreateParticipant(char nume[], char prenume[], int scor)
{
    Participant new_participant;
    strcpy(new_participant.nume, nume);
    strcpy(new_participant.prenume, prenume);
    new_participant.scor = scor;
    return new_participant;
}

char* GetNume(Participant participant)
{
    char* toReturn = (char*)calloc(strlen(participant.nume) + 1, sizeof(char));
    if (NULL == toReturn)
        return NULL;
    strcpy(toReturn, participant.nume);
    return toReturn;
}

char* GetPrenume(Participant participant)
{
    char* toReturn = (char*)calloc(strlen(participant.prenume) + 1, sizeof(char));
    if (NULL == toReturn)
        return NULL;
    strcpy(toReturn, participant.prenume);
    return toReturn;
}

int GetScor(Participant participant)
{
    return participant.scor;
}

void SetNume(Participant* participant, char newnume[])
{
    strcpy(participant->nume, newnume);
}

void SetPrenume(Participant* participant, char newprenume[])
{
    strcpy(participant->prenume, newprenume);
}

void SetScor(Participant* participant, int newscor)
{
    participant->scor = newscor;
}

int Equal(Participant firstParticipant, Participant secondParticipant)
{
    if (strcmp(firstParticipant.nume, secondParticipant.nume) == 0 && strcmp(firstParticipant.prenume, secondParticipant.prenume) == 0 &&
        firstParticipant.scor == secondParticipant.scor == 1)
        return 1;
    return 0;
}

void Assign(Participant sourceParticipant, Participant* destinationParticipant)
{
    destinationParticipant->scor = sourceParticipant.scor;
    strcpy(destinationParticipant->nume, sourceParticipant.nume);
    strcpy(destinationParticipant->prenume, sourceParticipant.prenume);
}

char* ToString(Participant participant)
{
    char final_string[256] = "nume: ";
    strcat(final_string, participant.nume);
    strcat(final_string, " | prenume: ");
    strcat(final_string, participant.prenume);
    strcat(final_string, " | scor: ");

    char scor_string[51]={0};
    sprintf(scor_string, "%d", participant.scor);
    

    strcat(final_string, scor_string);

    char* toReturn = (char*)calloc(strlen(final_string) + 1, sizeof(char));
    
    if (toReturn == NULL)
        return NULL;

    strcpy(toReturn, final_string);
    return toReturn;
}

int ValidParameters(char nume[], char prenume[], int scor)
{
    if (!Validprenume(prenume) || !Validnume(nume) || !Validscor(scor)) return 0;
    return 1;
}

int Validprenume(char prenume[])
{
    if (strlen(prenume) == 0) return 0;
    return 1;
}

int Validnume(char nume[])
{
    if (strlen(nume) == 0) return 0;
    return 1;
}

int Validscor(int scor)
{
    if (scor <=0 || scor > 100) return 0;
    return 1;
}


void RunAllTestsParticipant()
{
    TestCreateParticipantAndGetters();
    TestSetters();
    TestEquality();
    TestAssignment();
    TestToString();
    TestValidation();
}

void TestCreateParticipantAndGetters()
{
    Participant c1 = CreateParticipant("Nume", "Prenume", 25);

    char* nume = GetNume(c1);
    char* prenume = GetPrenume(c1);
    assert(strcmp("Nume", nume) == 0);
    assert(strcmp("Prenume", prenume) == 0);
    assert(GetScor(c1) == 25);
}

void TestSetters()
{
    Participant c1 = CreateParticipant("Nume", "Prenume", 25);

    SetScor(&c1, 100);
    SetNume(&c1, "Nume2");

    assert(strcmp("Nume2", GetNume(c1)) == 0);
    assert(strcmp("Prenume", GetPrenume(c1)) == 0);
    assert(GetScor(c1) == 100);
}

void TestEquality()
{
    Participant c1 = CreateParticipant("Nume", "Prenume", 25);
    Participant c2 = CreateParticipant("Nume", "Prenume", 25);
    Participant c3 = CreateParticipant("Nume", "Prenume", 26);

    assert(Equal(c1, c2) == 1);
    assert(Equal(c1, c3) == 0);
}

void TestAssignment()
{
    Participant c1 = CreateParticipant("Nume", "Prenume", 25);
    Participant c2 = CreateParticipant("Nume2", "Prenume2", 100);

    Assign(c2, &c1);
    assert(strcmp("Nume2", GetNume(c1)) == 0);
    assert(strcmp("Prenume2", GetPrenume(c1)) == 0);
    assert(GetScor(c1) == 100);
}

void TestToString()
{
    Participant c1 = CreateParticipant("Nume", "Prenume", 25);

    char* participantString = ToString(c1);
    assert(strstr(participantString, "Nume") != NULL);
    assert(strstr(participantString, "Prenume") != NULL);
    assert(strstr(participantString, "25") != NULL);
}

void TestValidation()
{
    assert(ValidParameters("Nume","Prenume", 10000) == 0);
    assert(ValidParameters("Nume", "Prenume", 100) == 1);
    assert(ValidParameters("Nume", "", 25) == 0);
    assert(ValidParameters("", "Prenume", 10) == 0);
    assert(ValidParameters("Nume", "Prenume", 43) == 1);
}
