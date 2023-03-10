#pragma once

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

int is_score(char *string);

int is_name(char *string);

int is_id(char *string);

int is_key(char* string);

int is_order(char* string);

void valid_test();