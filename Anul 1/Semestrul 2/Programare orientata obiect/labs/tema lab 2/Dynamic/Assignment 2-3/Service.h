#pragma once
#include "Repository.h"

typedef struct
{
	Repository* participantRepository;
} ParticipantService;

/// <summary>
/// Constructor for a ParticipantService object
/// </summary>
/// <param name="repository">The repository on which it operates</param>
/// <returns>A new ParticipantService object</returns>
ParticipantService* CreateServices(Repository* repository);

/// <summary>
/// Destructor for the ParticipantService object
/// </summary>
/// <param name="services">The object to be destroyed</param>
void DestroyServices(ParticipantService* services);

/// <summary>
/// Adds a new participant to the repository
/// </summary>
/// <param name="services">The services object</param>
/// <param name="nume">The nume of the participant</param>
/// <param name="prenume">The nume of the prenume the participant belongs to</param>
/// <param name="scor">The scor of the participant</param>
/// <returns>0 if the operation was succesful, 1 otherwise</returns>
int AddParticipant(ParticipantService* services, char nume[], char prenume[], int scor);

/// <summary>
/// Removes a new participant from the repository
/// </summary>
/// <param name="services">The services object</param>
/// <param name="nume">The nume of the participant</param>
/// <returns>0 if the operation was succesful, 1 otherwise</returns>
int RemoveParticipant(ParticipantService* services, char nume[]);

/// <summary>
/// Updates the nume of a participant from the repository
/// </summary>
/// <param name="services">The services object</param>
/// <param name="nume">The old nume of the participant</param>
/// <param name="newnume">The new nume of the participant</param>
/// <returns>0 if the operation was succesful, 1 otherwise</returns>
int UpdateParticipantnume(ParticipantService* services, char nume[], char newnume[]);

/// <summary>
/// Updates the prenume of a participant from the repository
/// </summary>
/// <param name="services">The services object</param>
/// <param name="nume">The old nume of the participant</param>
/// <param name="newprenume">The new prenume of the participant</param>
/// <returns>0 if the operation was succesful, 1 otherwise</returns>
int UpdateParticipantprenume(ParticipantService* services, char nume[], char newprenume[]);

/// <summary>
/// Updates the scor of a participant from the repository
/// </summary>
/// <param name="services">The services object</param>
/// <param name="nume">The old nume of the participant</param>
/// <param name="newscor">The new scor of the participant</param>
/// <returns>0 if the operation was succesful, 1 otherwise</returns>
int UpdateParticipantscor(ParticipantService* services, char nume[], int newscor);

int CompareScor(int a, int b);

int CompareName(char *a, char *b);

/// <summary>
/// Sorts an array of Participants ascendingly by their scor
/// </summary>
/// <param name="array">An array of Participants</param>
/// <param name="size">The size of the array</param>
void SortByScorAsc(Participant* array, int size, int (*CompareScor)(int, int));

/// <summary>
/// Sorts an array of Participants descendingly by their scor
/// </summary>
/// <param name="array">An array of Participants</param>
/// <param name="size">The size of the array</param>
void SortByScorDesc(Participant* array, int size, int (*CompareScor)(int, int));

/// <summary>
/// Sorts an array of Participants ascendingly by their nume
/// </summary>
/// <param name="array">An array of Participants</param>
/// <param name="size">The size of the array</param>
void SortByNamAsc(Participant* array, int size, int (*CompareName)(char*, char*));

/// <summary>
/// Sorts an array of Participants descendingly by their nume
/// </summary>
/// <param name="array">An array of Participants</param>
/// <param name="size">The size of the array</param>
void SortByNamDesc(Participant* array, int size, int (*CompareName)(char*, char*));

/// <summary>
/// Filters all Participants from the repo whose names start with the letter
/// </summary>
/// <param name="services">The services object</param>
/// <param name="litera">The letter to search by</param>
/// <param name="result">The resulting Participants of the search</param>
/// <param name="size">The size of the array containing the resulting Participants</param>

void GetParticipantsByName(ParticipantService* services, char litera, Participant* result, int* size);

/// <summary>
/// Filters all Participants from the repo whose score is less than a value
/// </summary>
/// <param name="services">The services object</param>
/// <param name="scor">The score to search by</param>
/// <param name="result">The resulting Participants of the search</param>
/// <param name="size">The size of the array containing the resulting Participants</param>
void GetParticipantsByScore(ParticipantService* services, int scor, Participant* result, int* size);

/// <summary>
/// Filters all Participants from the repo
/// </summary>
/// <param name="services">The services object</param>
/// <param name="result">The resulting Participants of the search</param>
/// <param name="size">The size of the array containing the resulting Participants</param>
void GetParticipants(ParticipantService* services, Participant* result, int* size);

void RunAllTestsService();
void TestCreateDestroyService();
void TestAddService();
void TestRemoveService();
void TestUpdateService();
void TestFilterByScoreService();
void TestFilterByNameService();
void TestFilterService();
void TestSortByScorAsc();
void TestSortByScorDesc();
void TestSortByNameAsc();
void TestSortByNameDesc();