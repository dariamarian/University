#pragma once

#include "../Domain/participant.h"

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

typedef struct repo repo;

repo* repo_initialization();

void repo_destructor(repo* array);

void repo_nume_sort(repo* array, int(*cmp)(const char*, const char*));

repo* copyRepo(repo* array);

void repo_add(repo* array, type_participant *participant);

int repo_delete(repo* array, int id);

int repo_get_size(repo* array);

void repo_scor_sort(repo* array, int(*cmp)(const int, int));

void repo_sort_3(repo* array, int(*cmp)(const int, const int), int(*cmp2)(const char*, const char*));

type_participant * repo_get_by_id(repo* array, int id);

void repo_test();

