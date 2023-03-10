
#include "validators.h"
#include <ctype.h>
#include <stdlib.h>
#include "string.h"
#include <assert.h>

char name_connectors[] = "- '";
int is_id(char *string){
    int index = 0;
    while(string[index] != 0)
    {
        if(!isdigit(string[index]))
            return 0;
        index++;
    }
    return 1;
}

int is_score(char *string){
    int index = 0;
    while(string[index] != '\0')
    {
        if(!isdigit(string[index]))
            return 0;
        index++;
    }
    int number = atoi(string);
    if(number > 100 || number < 10)
        return 0;
    return 1;
}

int is_name(char *string){
    if(strlen(string) > 50 || strlen(string)<1)
        return 0;
    int index = 0, i;
    while(string[index] != 0)
    {
        if(!isalpha(string[index])){
            for(i = 0; name_connectors[i] != '\0';i++)
                if(string[index] == name_connectors[i])
                    break;
            if(name_connectors[i] == '\0')
                return 0;
        }
        index++;
    }
    return 1;
}

int is_key(char* string){
    if(strcmp(string, "nume") == 0 || strcmp(string, "scor")== 0)
        return 1;
    return 0;
}

int is_order(char* string){
    if(strcmp(string, "+") == 0 || strcmp(string, "-")== 0)
        return 1;
    return 0;
}

void valid_test(){
    assert(is_order("d") == 0);
    assert(is_order("+") == 1);

    assert(is_key("d") == 0);
    assert(is_key("nume") == 1);

    assert(is_id("1"));
    assert(!is_id("abc"));

    assert(is_score("1") == 0);
    assert(is_score("11"));
    assert(is_score("1a") == 0);

    assert(is_name("ASD"));
    assert(is_name("1") == 0);
    assert(is_name("ABA 1") == 0);

}