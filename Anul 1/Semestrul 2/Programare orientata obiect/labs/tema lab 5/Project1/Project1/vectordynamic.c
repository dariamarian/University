#include "vectordynamic.h"
#include <malloc.h>
#include <assert.h>

VectorDinamic* create(int cap) {
	VectorDinamic* v = (VectorDinamic*)malloc(sizeof(VectorDinamic));
	if (v == NULL)
		return v;
	v->capacity = cap;
	v->lg = 0;
	v->elems = (Element*)malloc(sizeof(Element) * cap);
	return v;
}

void resize(VectorDinamic* v) {

	int newCap = v->capacity * 2;
	Element* newList = (Element*)calloc(newCap, sizeof(Element));
	if (newList != NULL && v->lg < newCap) {
		for (int i = 0; i < v->lg; i++)
			newList[i] = v->elems[i];

		free(v->elems);
		v->elems = newList;
		v->capacity = newCap;
	}
	else return;
}

void destroy(VectorDinamic** v) {
	free((*v)->elems);
	free(*v);
	*v = NULL;
}

void add(VectorDinamic* v, Element e) {
	if (v->lg == v->capacity)
		resize(v);
	v->elems[v->lg++] = e;
}

int del(VectorDinamic* v, int pos) {
	if (pos < 0 || pos >= v->lg || v->lg == 0)
		return 0;

	for (int i = pos; i < v->lg; i++)
		v->elems[i] = v->elems[i + 1];

	v->lg--;
	return 1;
}

int update(VectorDinamic* v, int pos, Element e) {

	if (pos < 0 || pos >= v->lg || v->lg == 0)
		return 0;

	v->elems[pos] = e;

	return 1;
}

Element getElement(VectorDinamic* v, int pos) {

	if (pos < 0 || pos >= v->lg || v->lg == 0)
		return 0;

	return v->elems[pos];
}

int len(VectorDinamic* v) {
	return v->lg;
}

VectorDinamic* copy(VectorDinamic* v) {
	VectorDinamic* copyv = create(v->capacity);
	for (int i = 0; i < v->lg; i++)
		copyv->elems[i] = v->elems[i];
	copyv->lg = v->lg;
	return copyv;
}

void test_vector() {
	VectorDinamic* v = create(1);
	int a = 13;
	add(v, &a);
	assert(getElement(v, 0) == &a);
	assert(getElement(v, -1) == 0);
	destroy(&v);

	v = create(1);
	a = 13;
	add(v, &a);
	del(v, 0);
	int x = del(v, -1);
	assert(x == 0);
	assert(v->lg == 0);
	destroy(&v);

	v = create(1);
	int b = 13;
	add(v, &b);
	a = 14;
	update(v, 0, &a);
	x = update(v, -1, &a);
	assert(x == 0);
	assert(v->elems[0] == &a);
	destroy(&v);

	v = create(1);
	a = 131;
	add(v, &a);
	VectorDinamic* copie = copy(v);

	assert(len(copie) == 1);
	destroy(&v);
	destroy(&copie);

	v = create(1);
	assert(v->lg == 0);
	assert(v->capacity == 1);
	destroy(&v);

	v = create(1);
	destroy(&v);

	v = create(1);
	x = 33;

	add(v, &x);
	add(v, &x);
	add(v, &x);

	assert(len(v) == 3);
	destroy(&v);

	v = create(1);
	x = 33;

	add(v, &x);
	add(v, &x);
	add(v, &x);

	assert(len(v) == 3);
	destroy(&v);
}