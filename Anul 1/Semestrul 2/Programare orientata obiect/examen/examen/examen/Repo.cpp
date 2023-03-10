#include "Repo.h"
#include <fstream>
#include <sstream>
#include <cassert>

void FileRepo::ReadFromFile()
{
	ifstream in(filename);
	if (!in.is_open())
		return;

	while (!in.eof()) {
		int id;
		string titlu;
		string artist;
		string gen;
		in >> id >> titlu >> artist >> gen;
		if (titlu != "" && artist != "" && (gen == "rock" || gen=="pop" || gen=="folk" || gen=="disco")) {
			Melodie m(id, titlu, artist, gen);
			all.push_back(m);
		}
	}
	in.close();
}

void FileRepo::WriteToFile()
{
	ofstream out(filename);

	if (!out.is_open())
		return;

	for (const auto& m : all)
	{
		out << m.getID() << " " << m.getTitlu() << " " << m.getArtist() << " " << m.getGen() << "\n";
	}
	out.close();
}

vector <Melodie>& FileRepo::rep_getAll() {
	return all;
}

void FileRepo::rep_add(Melodie& m)
{
	all.push_back(m);
	WriteToFile();
}

void FileRepo::rep_del(int id)
{
	int ind = 0;
	for (auto& el : all) {
		if (el.getID() == id)
		{
			all.erase(all.begin() + ind);
			break;
		}
		ind++;
	}
	WriteToFile();
}

void FileRepo::clearRepo()
{
	all.clear();
}

void testAdauga() {
	FileRepo rep{ "test.txt" };
	rep.clearRepo();
	Melodie m{ 20,"a","a", "pop", };
	rep.rep_add(m);
	Melodie m2{ 24,"bb","a", "rock", };
	rep.rep_add(m2);
	assert(rep.rep_getAll().size() == 2);
	Melodie m3{ 24,"bb","a", "invalid", };
	rep.rep_add(m3);
	assert(rep.rep_getAll().size() == 3);
}

void testSterge() {
	FileRepo rep{ "test.txt" };
	rep.clearRepo();
	Melodie m{ 20,"a","a", "pop",};
	rep.rep_add(m); 
	Melodie m2{ 24,"bb","a", "rock", };
	rep.rep_add(m2);
	assert(rep.rep_getAll().size() == 2);
	rep.rep_del(20);
	assert(rep.rep_getAll().size() == 1);
}

void testRepo()
{
	testAdauga();
	testSterge();
}
