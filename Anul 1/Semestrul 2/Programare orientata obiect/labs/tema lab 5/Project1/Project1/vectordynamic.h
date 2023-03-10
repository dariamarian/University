#pragma once
#include <stdlib.h>

typedef void* Element;
typedef struct {
	int lg;
	int capacity;
	Element* elems;
}VectorDinamic;

VectorDinamic* create(int cap);
void destroy(VectorDinamic** v);
VectorDinamic* copy(VectorDinamic* v);
void resize(VectorDinamic* v);
void add(VectorDinamic* v, Element e);
int del(VectorDinamic* v, int pos);
int update(VectorDinamic* v, int pos, Element e);
Element getElement(VectorDinamic* v, int pos);
int len(VectorDinamic* v);
void test_vector();