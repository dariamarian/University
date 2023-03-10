#include "Service.h"
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <stdbool.h>

ParticipantService* CreateServices(Repository* repository)
{
	ParticipantService* services = (ParticipantService*)malloc(sizeof(ParticipantService));
	
	if (services == NULL)
		return NULL;

	services->participantRepository = repository;

	return services;
}

void DestroyServices(ParticipantService* services)
{
	free(services);
}

int AddParticipant(ParticipantService* services, char nume[], char prenume[], int scor)
{
	if (!ValidParameters(nume, prenume, scor)) return 1;
	Participant newParticipant = CreateParticipant(nume, prenume, scor);

	AddNewParticipant(services->participantRepository, newParticipant);

	return 0;
}

int RemoveParticipant(ParticipantService* services, char nume[])
{
	int found = 1;
	for (int i = 0; i < GetLength(services->participantRepository); ++i)
	{
		if (strcmp(nume, GetNume(GetElementOnPosition(services->participantRepository, i))) == 0)
		{
			found = 0;
			RemoveNewParticipant(services->participantRepository, i);
		}
	}
	
	return found;
}


int CompareScor(int a, int b)
{
	if (a > b)
		return 1;
	else
		return 0;
}

int CompareName(char *a, char *b)
{
	if (strcmp(a, b) > 0)
		return 1;
	else
		return 0;
}
/*
void TestCompare()
{
	assert(CompareScor(30, 50) == 0);
	assert(CompareScor(50, 30) == 1);
	assert(CompareName("Daria", "Eveline") == 1);
	assert(CompareName("Eveline", "Daria") == 0);
}
*/
void SortByScorAsc(Participant* array, int size, int (*CompareScor)(int, int))
{
	for (int i = 0; i < size - 1; ++i)
		for (int j = i + 1; j < size; ++j)
			if (CompareScor(GetScor(array[i]),GetScor(array[j]))==1)
			{
				Participant aux = array[i];
				array[i] = array[j];
				array[j] = aux;
			}
}

void SortByScorDesc(Participant* array, int size, int (*CompareScor)(int, int))
{
	for (int i = 0; i < size - 1; ++i)
		for (int j = i + 1; j < size; ++j)
			if (CompareScor(GetScor(array[i]),GetScor(array[j]))==0)
			{
				Participant aux = array[i];
				array[i] = array[j];
				array[j] = aux;
			}
}
void SortByNamAsc(Participant* array, int size, int (*CompareName)(char*, char*))
{
	for (int i = 0; i < size - 1; ++i)
		for (int j = i + 1; j < size; ++j)
		{
			char c1[50];
			strcpy(c1, GetNume(array[i]));
			char c2[50];
			strcpy(c2, GetNume(array[j]));
			if (CompareName(c1, c2)==1)
			{
				Participant aux = array[i];
				array[i] = array[j];
				array[j] = aux;
			}
		}		
}
void SortByNamDesc(Participant* array, int size, int (*CompareName)(char*, char*))
{
	for (int i = 0; i < size - 1; ++i)
		for (int j = i + 1; j < size; ++j)
		{
			char c1[50];
			strcpy(c1, GetNume(array[i]));
			char c2[50];
			strcpy(c2, GetNume(array[j]));
			if (CompareName(c1, c2) == 0)
			{
				Participant aux = array[i];
				array[i] = array[j];
				array[j] = aux;
			}
		}
}

int UpdateParticipantscor(ParticipantService* services, char nume[], int newscor)
{
	int found = 1;
	for (int i = 0; i < GetLength(services->participantRepository); ++i)
	{
		if (strcmp(nume, GetNume(GetElementOnPosition(services->participantRepository, i))) == 0)
		{
			found = 0;
			UpdateParticipantscorRepo(services->participantRepository, i, newscor);
		}
	}

	return found;
}

int UpdateParticipantnume(ParticipantService* services, char nume[], char newnume[])
{
	int found = 1;
	for (int i = 0; i < GetLength(services->participantRepository); ++i)
	{
		if (strcmp(nume, GetNume(GetElementOnPosition(services->participantRepository, i))) == 0)
		{
			found = 0;
			UpdateParticipantnumeRepo(services->participantRepository, i, newnume);
		}
	}

	return found;
}

int UpdateParticipantprenume(ParticipantService* services, char nume[], char newprenume[])
{
	int found = 1;
	for (int i = 0; i < GetLength(services->participantRepository); ++i)
	{
		if (strcmp(nume, GetNume(GetElementOnPosition(services->participantRepository, i))) == 0)
		{
			found = 0;
			UpdateParticipantprenumeRepo(services->participantRepository, i, newprenume);
		}
	}

	return found;
}

void GetParticipantsByScore(ParticipantService* services, int scor, Participant* result, int* size)
{
	*size = 0;
	for (int i = 0; i < GetLength(services->participantRepository); ++i)
	{
		if (GetElementOnPosition(services->participantRepository, i).scor < scor)
		{
			result[*size] = GetElementOnPosition(services->participantRepository, i);
			(*size)++;
		}
	}
}

void GetParticipantsByName(ParticipantService* services, char litera, Participant* result, int* size)
{
	*size = 0;
	for (int i = 0; i < GetLength(services->participantRepository); ++i)
	{
		
		if (GetNume(GetElementOnPosition(services->participantRepository, i))
			[0] == litera)
		{
			result[*size] = GetElementOnPosition(services->participantRepository, i);
			(*size)++;
		}
	}
}

void GetParticipants(ParticipantService* services, Participant* result, int* size)
{
	*size = 0;
	for (int i = 0; i < GetLength(services->participantRepository); ++i)
	{
			result[*size] = GetElementOnPosition(services->participantRepository, i);
			(*size)++;
	}
}

void RunAllTestsService()
{
	TestCreateDestroyService();
	TestAddService();
	TestRemoveService();
	TestUpdateService();
	TestFilterByScoreService();
	TestFilterByNameService();
	TestFilterService();
	TestSortByScorAsc();
	TestSortByScorDesc();
	TestSortByNameAsc();
	TestSortByNameDesc();
}

void TestCreateDestroyService()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);

	assert(services->participantRepository->array->maxLength == repo->array->maxLength);
	assert(services->participantRepository->array->length == repo->array->length);
	assert(services->participantRepository->array->elements == repo->array->elements);

	DestroyServices(services);
	DestroyRepo(repo);
}

void TestAddService()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	int result = AddParticipant(services, "Nume", "Prenume", 25);

	assert(result == 0);

	result = AddParticipant(services, "", "Prenume", 100);

	assert(result == 1);

	result = AddParticipant(services, "Nume", "", 100);

	assert(result == 1);

	DestroyServices(services);
	DestroyRepo(repo);
}

void TestRemoveService()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	int result = RemoveParticipant(services, "Moldovan");

	assert(result == 1);

	result = RemoveParticipant(services, "Molnar");

	assert(result == 0);

	DestroyServices(services);
	DestroyRepo(repo);
}

void TestUpdateService()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	int result = UpdateParticipantnume(services, "Moldovan", "Something");

	assert(result == 1);

	result = UpdateParticipantnume(services, "Molnar", "Something");

	assert(result == 0);

	result = UpdateParticipantprenume(services, "Marian", "Georgiana");

	assert(result == 0);

	result = UpdateParticipantscor(services, "Marian", 10);

	assert(result == 0);

	DestroyServices(services);
	DestroyRepo(repo);
}

void TestFilterByScoreService()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);
	
	Participant result[51];
	int size;

	GetParticipantsByScore(services, 60, result, &size);

	assert(size == 3);
	assert(Equal(result[0], repo->array->elements[3]) == 1);
	assert(Equal(result[1], repo->array->elements[5]) == 1);
	assert(Equal(result[2], repo->array->elements[6]) == 1);


	DestroyServices(services);
	DestroyRepo(repo);
}

void TestFilterByNameService()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	Participant result[51];
	int size;

	GetParticipantsByName(services, 'M', result, &size);

	assert(size == 4);
	assert(Equal(result[0], repo->array->elements[0]) == 1);
	assert(Equal(result[1], repo->array->elements[1]) == 1);
	assert(Equal(result[2], repo->array->elements[2]) == 1);
	assert(Equal(result[3], repo->array->elements[6]) == 1);


	DestroyServices(services);
	DestroyRepo(repo);
}

void TestFilterService()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	Participant result[51];
	int size;

	GetParticipants(services, result, &size);

	assert(size == 7);
	assert(Equal(result[0], repo->array->elements[0]) == 1);
	assert(Equal(result[1], repo->array->elements[1]) == 1);
	assert(Equal(result[2], repo->array->elements[2]) == 1);
	assert(Equal(result[3], repo->array->elements[3]) == 1);
	assert(Equal(result[4], repo->array->elements[4]) == 1);
	assert(Equal(result[5], repo->array->elements[5]) == 1);
	assert(Equal(result[6], repo->array->elements[6]) == 1);


	DestroyServices(services);
	DestroyRepo(repo);
}


void TestSortByScorAsc()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	Participant result[51];
	int size;

	GetParticipants(services, result, &size);

	SortByScorAsc(result, size, CompareScor);

	int sorted = 1;
	for (int i = 0; i < size - 1; ++i)
	{
		if (result[i].scor > result[i + 1].scor)
			sorted = 0;
	}

	assert(sorted == 1);

	DestroyServices(services);
	DestroyRepo(repo);
}

void TestSortByScorDesc()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	Participant result[51];
	int size;

	GetParticipants(services, result, &size);

	SortByScorDesc(result, size, CompareScor);

	int sorted = 1;
	for (int i = 0; i < size - 1; ++i)
	{
		if (result[i].scor < result[i + 1].scor)
		{
			sorted = 0;
		}
	}

	assert(sorted == 1);

	DestroyServices(services);
	DestroyRepo(repo);
}

void TestSortByNameAsc()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	Participant result[51];
	int size;

	GetParticipants(services, result, &size);

	SortByNamAsc(result, size, CompareName);

	int sorted = 1;
	for (int i = 0; i < size - 1; ++i)
	{
		if (strcmp(result[i].nume, result[i+1].nume) > 0)
		{
			sorted = 0;
		}
	}

	assert(sorted == 1);

	DestroyServices(services);
	DestroyRepo(repo);
}

void TestSortByNameDesc()
{
	Repository* repo = CreateRepo(1);

	ParticipantService* services = CreateServices(repo);
	InitRepo(repo);

	Participant result[51];
	int size;

	GetParticipants(services, result, &size);

	SortByNamDesc(result, size, CompareName);

	int sorted = 1;
	for (int i = 0; i < size - 1; ++i)
	{
		if (strcmp(result[i].nume, result[i + 1].nume) < 0)
		{
			sorted = 0;
		}
	}

	assert(sorted == 1);

	DestroyServices(services);
	DestroyRepo(repo);
}

