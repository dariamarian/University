#pragma once

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

struct type_participant;

typedef struct type_participant type_participant;

type_participant* create_participant(char* nume, char*prenume, int scor);

void destroy_participant(type_participant* participant);

void set_nume(type_participant* participant, char* nume);

void set_prenume(type_participant* participant, char* prenume);

void set_scor(type_participant* participant, int scor);

char* get_nume(type_participant* participant);

char* get_prenume(type_participant* participant);

int get_scor(type_participant* participant);

void domain_test();