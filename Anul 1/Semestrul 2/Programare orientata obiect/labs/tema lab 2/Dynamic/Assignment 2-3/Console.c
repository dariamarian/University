#include "Console.h"
#include "Service.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

Console* CreateConsole(ParticipantService* services)
{
    Console* console = (Console*)malloc(sizeof(Console));

	if (console == NULL)
		return NULL;

    console->participantServices = services;

    return console;
}

void DestroyConsole(Console* console)
{
    free(console);
}

void MainLoop(Console* console)
{
	int done = 0;
	char command[100];

	PrintMenuUi();

	while (!done)
	{
		printf("Please input your command:\n");
		scanf_s("%s", &command, 100);
		strcat(command, "\0");

		if (strlen(command) != 1)
			printf("Invalid command!");
		else
		{
			switch (command[0])
			{
				case '1':
				{
					AddParticipantUi(console);
					break;
				}
				case '2':
				{
					RemoveParticipantUi(console);
					break;
				}
				case '3':
				{
					UpdateParticipantUi(console);
					break;
				}
				case '4':
				{
					FilterByScore(console);
					break;
				}
				case '5':
				{
					FilterByName(console);
					break;
				}
				case '6':
				{
					SortByScoreAsc(console);
					break;
				}
				case '7':
				{
					SortByScoreDesc(console);
					break;
				}
				case '8':
				{
					SortByNameAsc(console);
					break;
				}
				case '9':
				{
					SortByNameDesc(console);
					break;
				}
				case 'p':
				{
					PrintParticipantsUi(console);
					break;
				}
				case '0':
				{
					done = 1;
					break;
				}
				default:
				{
					printf("Invalid command!");
					break;
				}
			}
		}
	}
}

void PrintMenuUi()
{
	printf("1.Add a new participant\n");
	printf("2.Remove an existing participant\n");
	printf("3.Update an existing participant\n");
	printf("4.Filter participants by score\n");
	printf("5.Filter participants by name\n");
	printf("6.Sort by score asc\n");
	printf("7.Sort by score desc\n");
	printf("8.Sort by name asc\n");
	printf("9.Sort by name desc\n");
	printf("p.Print all Participants\n");
	printf("0.Exit\n");
}

void PrintParticipantsUi(Console* console)
{
	char string[256];
	for (int i = 0; i < GetLength(console->participantServices->participantRepository); ++i)
	{
		strcpy(string, ToString(GetElementOnPosition(console->participantServices->participantRepository, i)));
		printf("%s\n", string);
	}
}

void UpdateParticipantUi(Console* console)
{
	printf("What would you like to modify?(N for nume, P for prenume, S for scor)\n");
	char modificationString[20];
	scanf_s("%s", &modificationString,20);
	strcat(modificationString, "\0");
	if	(strlen(modificationString) != 1 || 
		(modificationString[0] != 'N' && modificationString[0] != 'n' &&
		modificationString[0] != 'P' && modificationString[0] != 'p' &&
		modificationString[0] != 'S' && modificationString[0] != 's'))
	{
		printf("Invalid command!\n");
		return;
	}

	char nume[51];
	printf("Please input the name of the participant you would like to update\n");
	scanf_s("%s", &nume,51);

	switch (modificationString[0])
	{
		case 'n':
		case 'N':
		{
			char newnume[51];
			printf("Please input the new name\n");
			scanf_s("%s", &newnume,51);
			strcat(modificationString, "\0");

			int result = UpdateParticipantnume(console->participantServices, nume, newnume);
			if (result == 1)
			{
				printf("Participant not found!\n");
				return;
			}
			break;
		}
		case 'p':
		case 'P':
		{
			char newprenume[51];
			printf("Please input the new prenume\n");
			scanf_s("%s", &newprenume,51);
			strcat(modificationString, "\0");

			int result = UpdateParticipantprenume(console->participantServices, nume, newprenume);
			if (result == 1)
			{
				printf("Participant not found!\n");
				return;
			}
			break;
			
		}
		case 's':
		case 'S':
		{
			int newscor;
			printf("Please input the new scor\n");
			scanf_s("%d", &newscor);
			if (Validscor(newscor) == 0)
			{
				printf("Scor invalid\n");
				break;
			}
			int result = UpdateParticipantscor(console->participantServices, nume, newscor);
			if (result == 1)
			{
				printf("Participant not found!\n");
				return;
			}
			break;
		}
	}
	
}

void RemoveParticipantUi(Console* console)
{
	char nume[51];
	printf("Please input the name of the participant you would like to remove\n");
	scanf_s("%s", &nume,51);
	
	int result = RemoveParticipant(console->participantServices, nume);
	if (result == 1)
	{
		printf("Participant not found!\n");
		return;
	}
}

void AddParticipantUi(Console* console)
{
	char nume[51], prenume[51];
	int scor;
	printf("Please input a nume, prenume, and a scor\n");
	scanf_s("%s", &nume,51);
	scanf_s("%s", &prenume,51);
	scanf_s("%d", &scor);
	
	int result = AddParticipant(console->participantServices, nume, prenume, scor);
	if (result == 1)
	{
		printf("Invalid input!\n");
		return;
	}
}

void FilterByScore(Console* console)
{
	int scor;
	printf("Introduceti scorul dupa care se doreste filtrarea:\n");
	scanf_s("%d", &scor);
	int valid = Validscor(scor);

	if (!valid)
	{
		printf("Invalid scor!\n");
		return;
	}

	Participant results[51];
	int size;
	GetParticipantsByScore(console->participantServices, scor, results, &size);

	char participantString[201];
	int ok = 0;
	for (int i = 0; i < size; ++i)
	{
		strcpy(participantString, ToString(results[i]));
		printf("%s\n", participantString);
		ok = 1;
	}
	if (ok==0)
	{
		printf("Lista vida\n");
		return;
	}
}

void FilterByName(Console* console)
{
	char litere[51];
	printf("Introduceti litera dupa care se doreste filtrarea:\n");
	scanf_s("%s", &litere,51);
	litere[50] = 0;
	if (strlen(litere)>1)
	{
		printf("Litera invalida!\n");
		return;
	}
	char litera = litere[0];

	Participant results[51];
	int size;
	GetParticipantsByName(console->participantServices, litera, results, &size);

	char participantString[201];
	int ok = 0;
	for (int i = 0; i < size; ++i)
	{
		strcpy(participantString, ToString(results[i]));
		printf("%s\n", participantString);
		ok = 1;
	}
	if (ok == 0)
	{
		printf("Lista vida\n");
		return;
	}
}

int CompareScorUI(int a, int b)
{
	if (a > b)
		return 1;
	else
		return 0;
}

int CompareNameUI(char *a, char *b)
{
	if (strcmp(a, b) > 0)
		return 1;
	else
		return 0;
}

void SortByScoreAsc(Console* console)
{
	Participant results[51];
	int size;
	GetParticipants(console->participantServices, results, &size);

	SortByScorAsc(results, size, CompareScorUI);
	char participantString[201];
	for (int i = 0; i < size; ++i)
	{
		strcpy(participantString, ToString(results[i]));
		printf("%s\n", participantString);
	}
}

void SortByScoreDesc(Console* console)
{
	Participant results[51];
	int size;
	GetParticipants(console->participantServices, results, &size);

	SortByScorDesc(results, size, CompareScorUI);
	char participantString[201];
	for (int i = 0; i < size; ++i)
	{
		strcpy(participantString, ToString(results[i]));
		printf("%s\n", participantString);
	}
}

void SortByNameAsc(Console* console)
{
	Participant results[51];
	int size;
	GetParticipants(console->participantServices, results, &size);

	SortByNamAsc(results, size, CompareNameUI);
	char participantString[201];
	for (int i = 0; i < size; ++i)
	{
		strcpy(participantString, ToString(results[i]));
		printf("%s\n", participantString);
	}
}

void SortByNameDesc(Console* console)
{
	Participant results[51];
	int size;
	GetParticipants(console->participantServices, results, &size);

	SortByNamDesc(results, size, CompareNameUI);
	char participantString[201];
	for (int i = 0; i < size; ++i)
	{
		strcpy(participantString, ToString(results[i]));
		printf("%s\n", participantString);
	}
}
