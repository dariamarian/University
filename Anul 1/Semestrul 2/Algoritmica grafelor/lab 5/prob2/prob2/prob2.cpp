#include <iostream>
#include <fstream>
#include <queue>
#include <vector>
using namespace std;

ifstream fin("4-in.txt");

const int inf = 1000000000;

int varfuri, arce, s = 0, t;
vector<vector<int>> capacity, flow;
vector<int> inaltime, exces;
queue<int> arceExces;

void pompare(int u, int v) {
    int d = min(exces[u], capacity[u][v] - flow[u][v]);
    flow[u][v] += d;
    flow[v][u] -= d;
    exces[u] -= d;
    exces[v] += d;
    if (d > 0 && exces[v] == d) {
        arceExces.push(v);
    }
}

void initializare() {
    fin >> varfuri >> arce;
    inaltime.assign(varfuri, 0);
    inaltime[s] = varfuri;
    flow.assign(varfuri, vector<int>(varfuri, 0));
    capacity.assign(varfuri, vector<int>(varfuri, 0));
    exces.assign(varfuri, 0);
    exces[s] = inf;
    int u, v, cap;
    t = varfuri - 1;
    while (fin >> u >> v >> cap) {
        flow[u][v] = 0;
        flow[v][u] = cap;
        capacity[u][v] = cap;
        capacity[v][u] = cap;
    }
    for (int i = 0; i < varfuri; i++) {
        if (i != s) {
            pompare(s, i);
        }
    }
}

void inaltare(int u) {
    int d = inf;
    for (int i = 0; i < varfuri; i++) {
        if (capacity[u][i] - flow[u][i] > 0) {
            d = min(d, inaltime[i]);
        }
    }
    if (d < inf) {
        inaltime[u] = d + 1;
    }
}

void descarcare(int u) {
    int k = 0;
    while (exces[u] > 0) {
        if (k < varfuri) {
            if (capacity[u][k] - flow[u][k] > 0 && inaltime[u] == inaltime[k] + 1) {
                pompare(u, k);
            }
            else {
                k++;
            }
        }
        else {
            inaltare(u);
            k = 0;
        }
    }
}

void pompareTopologica() {
    initializare();
    int u;
    while (!arceExces.empty()) {
        u = arceExces.front();
        arceExces.pop();
        if (u != s && u != t) {
            descarcare(u);
        }
    }
    int maxFlow = 0;
    for (int i = 0; i < varfuri; i++) {
        maxFlow += flow[i][t];
    }
    cout << maxFlow;
}

int main() {
    pompareTopologica();
    fin.close();
    return 0;
}