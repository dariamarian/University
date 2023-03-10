#pragma once

#include "../Repozitory/repozitory.h"
#include "../Domain/participant.h"
#include "../Validators/validators.h"
#include "../vectordynamic.h"

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

typedef struct service service;

service * service_initialization(repo* repo);

void destroyHistory(VectorDinamic* v);

void appendHistory(service* srv);

int undo(service* srv);

int service_sort(service* srv, char* key, char* order);

int service_sort3(service* srv);

char* service_show(service* srv, char* scor);

char* service_show2(service* srv, char* scor);

void service_destructor(service *srv);

int service_add(service* srv, char *nume, char *prenume, char *scor);

int service_modify(service* srv, char *id, char *nume, char *prenume, char *scor);

int service_delete(service* srv, char *id);

char* service_debug(service* srv);

void service_test();