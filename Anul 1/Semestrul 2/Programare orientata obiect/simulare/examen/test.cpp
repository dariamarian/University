#include "test.h"
#include "domain.h"
#include "repo.h"
#include "service.h"
#include <assert.h>

void testDomain()
{
	Telefon t(9870, "Apple", "Iphone13", 6800);
	assert(t.getCod() == 9870);
	assert(t.getBrand() == "Apple");
	assert(t.getModel() == "Iphone13");
	assert(t.getPret() == 6800);
}

void testReposiService()
{
	FileRepo rep{ "test.txt" };
	Service srv{ rep };
	assert(srv.srv_getAll().size() == 10);
	vector <Telefon> v;
	for (auto& t : srv.srv_getAll())
		v.push_back(t);
	srv.sortare(1, v);
	assert(v[0].getModel() == "I5");
	assert(v[1].getModel() == "Nk");
	assert(v[9].getModel() == "Iphone13");

	srv.sortare(2, v);
	assert(v[0].getBrand() == "Apple");
	assert(v[1].getBrand() == "Apple");
	assert(v[9].getBrand() == "Samsung");

	srv.sortare(3, v);
	assert(v[0].getModel() == "Fold");
	assert(v[1].getModel() == "Galaxy");
	assert(v[9].getModel() == "Note");
}

void testall()
{
	testDomain();
	testReposiService();
}