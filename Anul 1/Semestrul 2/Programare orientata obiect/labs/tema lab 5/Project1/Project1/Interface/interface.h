#pragma once

#include "../Service/service.h"

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

typedef struct interface interface;

interface* interface_initialization(service* srv);

void interface_destructor(interface* ui);

void interface_run(interface* ui);
