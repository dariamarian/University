// Assignment 2-3.c : This file contains the 'main' function. Program execution begins and ends there.
//

#define _CRTDBG_MAP_ALLOC
#include <stdio.h>
#include <crtdbg.h>
#include "Console.h"

int main()
{
    RunAllTestsParticipant();
    RunAllTestsRepo();
    RunAllTestsService();

    Repository* repository = CreateRepo(10);
    ParticipantService* service = CreateServices(repository);
    Console* console = CreateConsole(service);

    InitRepo(repository);

    MainLoop(console);

    DestroyConsole(console);
    DestroyServices(service);
    DestroyRepo(repository);

    _CrtDumpMemoryLeaks();
    return 0;
}

