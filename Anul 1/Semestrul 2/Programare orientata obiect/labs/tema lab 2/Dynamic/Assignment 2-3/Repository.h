#pragma once
#include "DynamicArray.h"
#include <stddef.h>

typedef struct
{
	DynamicArray* array;
} Repository;

/// <summary>
/// Constructor for a Repository object
/// </summary>
/// <param name="maxLength">The maximum size of the repository</param>
/// <returns>The new Repository object</returns>
Repository* CreateRepo(int maxLength);

/// <summary>
/// Copy constructor for a Repository object
/// </summary>
/// <param name="source">The repo to be copied</param>
/// <returns>A copy of the given repository</returns>
Repository DuplicateRepo(Repository source);

/// <summary>
/// Destructor for a repository
/// </summary>
/// <param name="repo">The repo to be destroyed</param>
void DestroyRepo(Repository* repo);

/// <summary>
/// Adds a new element to the repository
/// </summary>
/// <param name="repo">The repository where to add the element</param>
/// <param name="newParticipant">The new participant to be added</param>
void AddNewParticipant(Repository* repo, Participant newParticipant);

/// <summary>
/// Removes an element from the repository
/// </summary>
/// <param name="repo">The repository from where to remove the element</param>
/// <param name="position">The position of the element</param>
void RemoveNewParticipant(Repository* repo, int position);

/// <summary>
/// Updates the nume of an element of the repo
/// </summary>
/// <param name="repo">The repo that contains the element</param>
/// <param name="position">The position of the element</param>
/// <param name="newnume">The new nume of the element</param>
void UpdateParticipantnumeRepo(Repository* repo, int position, char* newnume);

/// <summary>
/// Updates the prenume of an element of the repo
/// </summary>
/// <param name="repo">The repo that contains the element</param>
/// <param name="position">The position of the element</param>
/// <param name="newprenume">The new prenume of the element</param>
void UpdateParticipantprenumeRepo(Repository* repo, int position, char* newprenume);

/// <summary>
/// Updates the scor of an element of the repo
/// </summary>
/// <param name="repo">The repo that contains the element</param>
/// <param name="position">The position of the element</param>
/// <param name="newnume">The new scor of the element</param>
void UpdateParticipantscorRepo(Repository* repo, int position, int newscor);


/// <summary>
/// Getter for the length of the repo
/// </summary>
/// <param name="repo">The repository</param>
/// <returns>The length of the repository</returns>
int GetLength(Repository* repo);

/// <summary>
/// Indexing operator for the repository
/// </summary>
/// <param name="repo">The repository</param>
/// <param name="position">The position of the element</param>
/// <returns>The element on the given position in the repo</returns>
Participant GetElementOnPosition(Repository* repo, int position);

/// <summary>
/// Adds some initial values to the repo
/// </summary>
/// <param name="repo">The repo to be modified</param>
void InitRepo(Repository* repo);

void RunAllTestsRepo();
void TestCreateDestroyRepo();
void TestAdd();
void TestRemove();
void TestUpdaters();
void TestCopying();
void TestGetters();