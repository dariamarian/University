#pragma once
#include <string>
#include <iostream>

using std::string;
using std::cout;

class Melodie
{
	int ID;
	string titlu;
	string artist;
	string gen;

public:
	Melodie(int id, string t, string a, string g) :ID{ id }, titlu{ t }, artist{ a }, gen{ g } {}
	/*
	* Returneaza id-ul melodiei
	*/
	int getID() const
	{
		return ID;
	}
	/*
	* Returneaza titlul melodiei
	*/
	string getTitlu() const
	{
		return titlu;
	}
	/*
	* Returneaza artistul melodiei
	*/
	string getArtist() const
	{
		return artist;
	}
	/*
	* Returneaza genul melodiei
	*/
	string getGen() const
	{
		return gen;
	}
};