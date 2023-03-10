#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

std::vector<std::vector<int>> vec;
std::vector<std::vector<int>> cicl;
int v, e, x, y, nr, s;

void euler(int k)
{
	std::vector<int> ci;
	while (vec[k].size() > 0)
	{
		int nr2 = 0, auxnr2 = 0;
		while (auxnr2 < vec[k].size()) {
			if (vec[vec[k][auxnr2]].size() > vec[vec[k][nr2]].size()) nr2 = auxnr2;
			auxnr2++;
		}
		int x = vec[k][nr2];
		vec[k].erase(vec[k].begin() + nr2);
		int cont = 0;
		for (auto& it : vec[x]) {
			if (it == k) {
				vec[x].erase(vec[x].begin() + cont);
				break;
			}
			cont++;
		}
		ci.push_back(k);
		k = x;
	}
	cicl.push_back(ci);
}

int main() {
	ifstream fin("1-in.txt");

	nr = 0;

	fin >> v >> e;
	vec.resize(v);
	for (int i = 0; i < e; i++) {
		fin >> x >> y;
		vec[x].push_back(y);
		vec[y].push_back(x);
	}

	int k = 0;
	while (k < v) {
		while (vec[k].size() > 0) euler(k);
		k++;
	}

	for (int i = 0; i < cicl[0].size(); i++) {
		int nr = cicl[0][i];
		int j = 1;
		while (j < cicl.size() && cicl[j][0] != nr)j++;
		if (j >= cicl.size()) {
			cout << nr << " ";
		}
		else {
			for (auto it : cicl[j]) cout << it << " ";
			cicl.erase(cicl.begin() + j);
			cout << nr << " ";
		}
	}

	return 0;
}