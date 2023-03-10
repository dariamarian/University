#pragma once

#include <iostream>
#include <exception>
#include <functional>

#define init_cap 5
template <typename TElem>
class Iterator2Vector;

template <typename TElem>
class DynamicVector
{
private:
	int lg;
	int cap;
	TElem* elems;

	void resize();

public:

	friend class Iterator2Vector<TElem>;
	DynamicVector();
	DynamicVector(const DynamicVector& ot);
	~DynamicVector();

	DynamicVector& operator=(const DynamicVector& ot);

	void add(const TElem& el);
	void remove(int pos);
	void erase(Iterator2Vector<TElem>& Where);
	int size() const;
	TElem& operator[](int pos) const;

	//Iterator2Vector<TElem> set(int pos, const TElem& el);

	//Iterator2Vector<TElem>find(const TElem& el) const;
	//Iterator2Vector<TElem>find_if(const TElem& el, function< bool(const TElem& e1, const TElem& e2) >compareFunction ) const ;




	Iterator2Vector<TElem> begin();
	Iterator2Vector<TElem> end();
};


//aici incepe iteratorul
template<typename TElem>
class Iterator2Vector
{
private:
	int pos = 0;
	DynamicVector<TElem>& v;
public:
	friend class DynamicVector<TElem>;

	Iterator2Vector(DynamicVector<TElem>& _v) noexcept;
	Iterator2Vector(DynamicVector<TElem>& _v, int _pos) noexcept;

	TElem& operator*() const;
	Iterator2Vector& operator++();
	bool operator==(const Iterator2Vector& ot) const;
	bool operator!=(const Iterator2Vector& ot) const;
	//Iterator2Vector& operator=(const Iterator2Vector& ot); poate modifica operatorul deci nu facem asa ceva

};



template<typename TElem>
DynamicVector<TElem>::DynamicVector(const DynamicVector<TElem>& ot) {
	elems = new TElem[ot.cap];
	if (ot.lg <= ot.cap)
		for (int i = 0; i < ot.lg; i++) {
			elems[i] = ot.elems[i];
		}
	lg = ot.lg;
	cap = ot.cap;
}

template<typename TElem>
DynamicVector<TElem>::~DynamicVector()
{
	delete[] elems;
}


template<typename TElem>
DynamicVector<TElem>& DynamicVector<TElem>::operator=(const DynamicVector<TElem>& ot)
{
	if (this == &ot)
		return *this;

	TElem* new_elems = new TElem[ot.cap];
	if (ot.lg <= ot.cap)
		for (int i = 0; i < ot.lg; i++) {
			new_elems[i] = ot.elems[i];
		}
	delete[] elems;
	elems = new_elems;

	lg = ot.lg;
	cap = ot.cap;
	return *this;
}

template<typename TElem>
void DynamicVector<TElem>::add(const TElem& el)
{
	if (lg == cap)
		resize();

	this->elems[lg++] = el;
}

template<typename TElem>
void DynamicVector<TElem>::remove(int pos)
{
	if (pos < lg && pos >= 0) {
		for (int i = pos; i < lg - 1; i++)
			elems[i] = elems[i + 1];
		lg--;
	}
}

template<typename TElem>
void DynamicVector<TElem>::erase(Iterator2Vector<TElem>& Where)
{

	int position = Where.pos;

	if (position < lg && position >= 0) {
		for (int i = position; i < lg - 1; i++)
			elems[i] = elems[i + 1];
		lg--;
	}

}

template<typename TElem>
int DynamicVector<TElem>::size() const
{
	return this->lg;
}

template<typename TElem>
inline TElem& DynamicVector<TElem>::operator[](int pos) const
{
	if (pos < lg && pos >= 0)
		return elems[pos];

	else throw std::exception();
}


template<typename TElem>
Iterator2Vector<TElem> DynamicVector<TElem>::begin()
{
	return Iterator2Vector<TElem>(*this);
}

template<typename TElem>
Iterator2Vector<TElem> DynamicVector<TElem>::end()
{
	return Iterator2Vector<TElem>(*this, lg);
}

template<typename TElem>
void DynamicVector<TElem>::resize()
{
	int cap_new = this->cap * 2;
	TElem* new_elems = new TElem[cap_new];

	for (int i = 0; i < lg; i++) {
		new_elems[i] = elems[i];
	}

	delete[] elems;
	this->cap = cap_new;
	elems = new_elems;

}

template<typename TElem>
DynamicVector<TElem>::DynamicVector()
{
	elems = new TElem[init_cap];
	lg = 0;
	cap = init_cap;
}



template<typename TElem>
Iterator2Vector<TElem>::Iterator2Vector(DynamicVector<TElem>& _v) noexcept : v{ _v } {}


template<typename TElem>
Iterator2Vector<TElem>::Iterator2Vector(DynamicVector<TElem>& _v, int _pos) noexcept : v{ _v }, pos{ _pos }{}


template<typename TElem>
TElem& Iterator2Vector<TElem>::operator*() const
{
	return v.elems[pos];
}

template<typename TElem>
Iterator2Vector<TElem>& Iterator2Vector<TElem>::operator++()
{
	pos++;
	return *this;
}

template<typename TElem>
bool Iterator2Vector<TElem>::operator==(const Iterator2Vector& ot) const
{
	return (pos == ot.pos);
}

template<typename TElem>
bool Iterator2Vector<TElem>::operator!=(const Iterator2Vector& ot) const
{
	return (pos != ot.pos);
}
