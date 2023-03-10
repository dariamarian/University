//fluxul maxim de la o sursa la o destinatie, Alg Ford-Fulkerson
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

ifstream fin("ex.txt");


int n, m, source, sink, maxFlux;
vector < vector < int > > Adj;
vector < vector < int > > resAdj;
vector < int > parent;

void Init_Matrix(vector < vector < int > >& Matrix) {
	for (int i = 0; i < n; i++) {
		vector < int > row;
		Matrix.push_back(row);
		for (int j = 0; j < n; j++) {
			Matrix[i].push_back(0);
		}
	}
}

void Read() {
	int x, y, w;
	fin >> n >> m;
	Init_Matrix(Adj);
	for (int i = 1; i <= m; i++) {
		fin >> x >> y >> w;
		Adj[x][y] = w;
	}
	source = 0;
	sink = n - 1;
	fin.close();
}

bool BFS(vector < vector < int > >& resAdj, int& source, int& sink, vector < int >& parent) {
	int n = resAdj.size();
	//lista pt toate nodurile vizitate
	bool visited[1005] = { false };

	//queue pt bfs
	queue < int > Q;
	Q.push(source);
	visited[source] = true;
	parent[source] = -1;

	while (!Q.empty()) {
		int u = Q.front();
		Q.pop();
		for (int i = 0; i < n; i++) {
			int v = i;
			int cap = resAdj[u][v];
			//cautam vecin nevizitat cu cap > 0
			if (!visited[v] && cap > 0) {
				//gasit
				Q.push(v);
				parent[v] = u;
				visited[v] = true;
			}
		}
	}
	//daca s-a vizitat "sink" am gasit un drum
	if (visited[sink]) return true;
	return false;
}

void Ford_Fulkerson() {
	//graful rezidual(ca ce original)
	int n = Adj.size();
	for (int i = 0; i < n; i++) {
		vector < int > row;
		resAdj.push_back(row);
		for (int j = 0; j < Adj[i].size(); j++) {
			resAdj[i].push_back(Adj[i][j]);
		}
	}

	//lista de parinti goala
	for (int i = 0; i < n; i++) {
		parent.push_back(-1);
	}

	//bfs cat timp gasim drumuri de la source la sink
	while (BFS(resAdj, source, sink, parent)) {
		//fluxul maxim pe acest drum
		int pathFlux = 1e9;
		//mergem pe drumul curent
		int v = sink;
		while (v != source) {
			int u = parent[v];
			//update pathFlux daca e mai mic
			int cap = resAdj[u][v];
			pathFlux = min(pathFlux, cap);
			//mai departe
			v = u;
		}
		//update cap reziduale a muchiilor
		v = sink;
		while (v != source) {
			int u = parent[v];
			resAdj[u][v] -= pathFlux;
			resAdj[v][u] += pathFlux;
			v = u;
		}
		//adaugarea fluxului curent la total
		maxFlux += pathFlux;
	}
}

void Print() {
	cout << maxFlux << "\n";
}

int main() {
	Read();
	Ford_Fulkerson();
	Print();
	return 0;
}