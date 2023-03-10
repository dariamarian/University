#pragma once
#include "Service.h"

typedef struct
{
	ParticipantService* participantServices;
} Console;

Console* CreateConsole(ParticipantService* services);
void DestroyConsole(Console* console);
int CompareScorUI(int a, int b);
int CompareNameUI(char* a, char* b);
void MainLoop(Console* console);
void PrintMenuUi();
void PrintParticipantsUi(Console* console);
void FilterByScore(Console* console);
void FilterByName(Console* console);
void SortByScoreAsc(Console* console);
void SortByScoreDesc(Console* console);
void SortByNameAsc(Console* console);
void SortByNameDesc(Console* console);
void UpdateParticipantUi(Console* console);
void RemoveParticipantUi(Console* console);
void AddParticipantUi(Console* console);
