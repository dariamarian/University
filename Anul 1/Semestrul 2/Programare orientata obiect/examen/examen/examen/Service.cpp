#include "Service.h"
#include <algorithm>
#include <unordered_set>
#include <cassert>

struct byArtist {
	inline bool operator()(Melodie& m1, Melodie m2) {
		return m1.getArtist() < m2.getArtist();
	}
};

vector<Melodie>& Service::sortare(vector<Melodie>& list) {
	sort(list.begin(), list.end(), byArtist());
	return list;
}

vector<Melodie>& Service::srv_getAll() {
	return sortare(rep.rep_getAll());
}

Melodie& Service::srv_find(int id) {
	int index = 0;
	for (auto& el : rep.rep_getAll()) {
		if (el.getID() == id)
			break;
		index++;
	}
	return rep.rep_getAll()[index];
}

int findID(const vector< Melodie >& lista)
{
	unordered_set<int> d;
	for (const auto& m : lista)
		d.insert(m.getID());
	int index = 1;
	while (true)
	{
		if (d.find(index) == d.end()) 
			return index;
		index++;
	}
}

void Service::srv_add(string titlu, string artist, string gen)
{
	int newID = findID(rep.rep_getAll());
	Melodie m{ newID, titlu,artist, gen };
	rep.rep_add(m);
}

void Service::srv_del(int id) {
	rep.rep_del(id);
}

void testAdaugaSrv() {
	FileRepo rep{ "test.txt" };
	Service srv{ rep };
	rep.clearRepo();
	srv.srv_add("ab", "ab", "rock");
	assert(srv.srv_getAll().size() == 1);
	srv.srv_add("ab", "ab", "pop");
	assert(srv.srv_getAll().size() == 2);
}
void testStergeSrv() {
	FileRepo rep{ "test.txt" };
	Service srv{ rep };
	rep.clearRepo();
	srv.srv_add("ab", "ab", "rock");
	srv.srv_add("ab", "ab", "pop");
	assert(srv.srv_getAll().size() == 2);
	srv.srv_del(1);
	assert(srv.srv_getAll().size() == 1);
}

void testCautaSrv() {
	FileRepo rep{ "test.txt" };
	Service srv{ rep };
	rep.clearRepo();
	srv.srv_add("ab", "ab", "rock");
	srv.srv_add("abc", "ab", "pop");
	assert(srv.srv_getAll().size() == 2);
	Melodie m = srv.srv_find(1);
	assert(m.getTitlu() == "ab");
}

void testSortSrv() {
	FileRepo rep{ "test.txt" };
	Service srv{ rep };
	rep.clearRepo();
	srv.srv_add("ab", "b", "rock");
	srv.srv_add("abc", "ab", "pop");
	srv.srv_add("abcd", "dab", "folk");
	assert(srv.srv_getAll().size() == 3);
	vector<Melodie>& l = rep.rep_getAll();
	vector<Melodie>& l2 = srv.sortare(l);
	assert(l2.at(0).getArtist() == "ab");
	assert(l2.at(1).getArtist() == "b");
}

void testService()
{
	testAdaugaSrv();
	testStergeSrv();
	testCautaSrv();
	testSortSrv();
}