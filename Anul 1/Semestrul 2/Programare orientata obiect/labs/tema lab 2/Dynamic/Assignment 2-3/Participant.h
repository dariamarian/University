/*
*	Module containing the definition and operations of a participant
*/
#pragma once
#include <stddef.h>

typedef struct
{
	char nume[50];
	char prenume[50];
	int scor;
} Participant;

/// <summary>
/// Constructor for a Participant object
/// </summary>
Participant CreateParticipant(char nume[], char prenume[], int scor);

/// <summary>
/// Getter for the name of a participant
/// </summary>
char* GetNume(Participant participant);

/// <summary>
/// Getter for the prenume of a participant
/// </summary>
char* GetPrenume(Participant participant);

/// <summary>
/// Getter for the score of a participant
/// </summary>
int GetScor(Participant participant);

/// <summary>
/// Setter for the name of a participant
/// </summary>
void SetNume(Participant* participant, char newnume[]);


/// <summary>
/// Setter for the prenume of a participant
/// </summary>
void SetPrenume(Participant* participant, char newprenume[]);

/// <summary>
/// Setter for the score of a participant
/// </summary>
void SetScor(Participant* participant, int newscor);

/// <summary>
/// Checks if two participants have the same nume, prenume and scor
/// </summary>
int Equal(Participant firstParticipant, Participant secondParticipant);

/// <summary>
/// Assigns the values of a participant to another
/// </summary>
void Assign(Participant sourceParticipant, Participant* destinationParticipant);

/// <summary>
/// Turns the given participant into a string
/// </summary>
char* ToString(Participant participant);


/// <summary>
///	Checks the validity of the attributes of a participant
/// </summary>
int ValidParameters(char nume[], char prenume[], int scor);

/// <summary>
///	Checks the validity of the prenume of a participant
/// </summary>
int Validprenume(char prenume[]);

/// <summary>
///	Checks the validity of the nume of a participant
/// </summary>
/// <param name="nume">The nume of the participant</param>
/// <returns>1 if the nume is valid, 0 otherwise</returns>
int Validnume(char nume[]);

/// <summary>
///	Checks the validity of the scor of a participant
/// </summary>
int Validscor(int scor);


void RunAllTestsParticipant();
void TestCreateParticipantAndGetters();
void TestSetters();
void TestEquality();
void TestAssignment();
void TestToString();
void TestValidation();