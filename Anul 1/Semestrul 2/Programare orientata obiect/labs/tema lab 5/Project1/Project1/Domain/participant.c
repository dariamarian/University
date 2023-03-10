#include "participant.h"
#include "stdlib.h"
#include "string.h"
#include <assert.h>

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

struct type_participant{
    char *nume, *prenume;
    int scor;
};

type_participant* create_participant(char* nume, char*prenume, int scor){
    type_participant* participant = malloc(sizeof(type_participant));

    //if (participant == NULL)
    //    return NULL;

    participant->nume = malloc(strlen(nume) + 1);
    //if (participant->nume == NULL)
    //    return NULL;
    strcpy(participant->nume, nume);

    participant->prenume = malloc(strlen(prenume) + 1);
    //if (participant->prenume == NULL)
    //    return NULL;
    strcpy(participant->prenume, prenume);

    participant->scor = scor;

    return participant;
}

void destroy_participant(type_participant* participant){
    free(participant->nume);
    free(participant->prenume);
    free(participant);
}

void set_nume(type_participant* participant, char* nume){
    free(participant->nume);
    
    participant->nume = malloc(strlen(nume) + 1);
    //if (participant->nume == NULL)
    //    return;
    strcpy(participant->nume, nume);
}

void set_prenume(type_participant* participant, char* prenume){
    free(participant->prenume);

    participant->prenume = malloc(strlen(prenume) + 1);
    //if (participant->prenume == NULL)
    //    return;
    strcpy(participant->prenume, prenume);
}

void set_scor(type_participant* participant, int scor){
    participant->scor = scor;
}

char* get_nume(type_participant* participant){
    return participant->nume;
}

char* get_prenume(type_participant* participant){
    return participant->prenume;
}

int get_scor(type_participant* participant){
    return participant->scor;
}

void domain_test(){
    type_participant *participant = create_participant("Morozan", "Dragos", 78);
    assert(!strcmp(get_nume(participant), "Morozan"));
    assert(!strcmp(get_prenume(participant), "Dragos"));
    assert(get_scor(participant) == 78);

    set_nume(participant, "Matei");
    set_prenume(participant, "Otniel");
    set_scor(participant, 100);

    assert(!strcmp(get_nume(participant), "Matei"));
    assert(!strcmp(get_prenume(participant), "Otniel"));
    assert(get_scor(participant) == 100);

    destroy_participant(participant);
}