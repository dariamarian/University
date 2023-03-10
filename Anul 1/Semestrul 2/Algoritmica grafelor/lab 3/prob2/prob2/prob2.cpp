#include <iostream>
#include <fstream>
#include <queue>
#include <climits>

using namespace std;

ifstream fin("johnson.in");
ofstream fout("johnson.out");

const int NMax = 1005;
const int oo = INT_MAX / 2;
int n, m, s, D[NMax], viz[NMax], h[NMax], Dist[NMax][NMax];
//D[] - lista de drumuri minime
//viz[] - starea unui nod daca a fost vizitat sau nu

//comparator pentru coada
struct CompDist
{
    bool operator ()(int x, int y)
    {
        return D[x] > D[y];
    }
};


vector < pair < int, int > > G[NMax]; //lista de adiacenta cu pondere
priority_queue < int, vector < int >, CompDist > Q;//coada cu prioritati cu comparator creat

void Citire()
{
    fin >> n >> m;
    for (int i = 0; i < m; i++)
    {
        int x, y, w;
        fin >> x >> y >> w;
        G[x].push_back(make_pair(y, w));
    }
    fin.close();
}

void Dijkstra(int nodStart)
{
    int i, j;
    for (i = 0; i < n; i++)
        D[i] = oo;

    Q.push(nodStart);
    D[nodStart] = 0;
    viz[nodStart] = 0;
    while (!Q.empty())
    {
        int nodCurent = Q.top();
        Q.pop();
        viz[nodCurent] = 0;

        for (i = 0; i < G[nodCurent].size(); i++)
        {
            int Vecin = G[nodCurent][i].first;
            int Pondere = G[nodCurent][i].second;
            if (D[nodCurent] + Pondere < D[Vecin])
            {
                D[Vecin] = D[nodCurent] + Pondere;
                if (viz[Vecin] == 0)
                {
                    viz[Vecin] = 1;
                    Q.push(Vecin);
                }
            }
        }
    }
}

void Afisare()
{
    int i, j, nodCurent, Vecin, Pondere;
    for (nodCurent = 0; nodCurent < n; nodCurent++)
        for (i = 0; i < G[nodCurent].size(); i++)
        {
            Vecin = G[nodCurent][i].first;
            Pondere = G[nodCurent][i].second;
            fout << nodCurent << " " << Vecin << " " << Pondere << "\n";
        }
    for (i = 0; i < n; i++, fout << "\n")
        for (j = 0; j < n; j++)
        {
            if (Dist[i][j] == oo)
                fout << "INF ";
            else
                fout << Dist[i][j] << " ";
        }
    fout.close();
}

void Initializare_Start(int nodStart)
{
    for (int i = 0; i < n; i++)
        D[i] = oo;
    D[s] = 0;
}


bool Bellman_Ford(int nodStart)
{
    int i, nodCurent, k, Vecin, Pondere;
    Initializare_Start(nodStart);
    for (k = 1; k <= n - 1; k++)
        for (nodCurent = 0; nodCurent <= n; nodCurent++)
            for (i = 0; i < G[nodCurent].size(); i++)
            {
                Vecin = G[nodCurent][i].first;
                Pondere = G[nodCurent][i].second;
                if (D[nodCurent] + Pondere < D[Vecin])
                    D[Vecin] = D[nodCurent] + Pondere;
            }
    for (nodCurent = 0; nodCurent <= n; nodCurent++)
        for (i = 0; i < G[nodCurent].size(); i++)
        {
            Vecin = G[nodCurent][i].first;
            Pondere = G[nodCurent][i].second;
            if (D[nodCurent] + Pondere < D[Vecin])
                return false;
        }
    return true;
}

void ConstruireNoiGVE()
{
    for (int i = 0; i < m; i++)
        G[n].push_back(make_pair(i, 0));
}

void Reponderare()
{
    int i, nodCurent, Vecin, Pondere;
    for (nodCurent = 0; nodCurent <= n; nodCurent++)
        for (i = 0; i < G[nodCurent].size(); i++)
        {
            Vecin = G[nodCurent][i].first;
            Pondere = G[nodCurent][i].second;
            G[nodCurent][i].second = Pondere + h[nodCurent] - h[Vecin];
        }
}

void Johnson()
{
    int i, j;
    ConstruireNoiGVE();
    if (Bellman_Ford(n) == false)//verificat cicluri negative
    {
        fout << "-1\n";
        fout.close();
        exit(0);
    }
    for (i = 0; i <= n; i++)
        h[i] = D[i];//D[I] = dist de la s(nodul n in cazul nostru) la i - determinat cu Bellman_Ford
    Reponderare();
    for (i = 0; i < n; i++)
    {
        Dijkstra(i);
        for (j = 0; j < n; j++)
            if (D[j] - h[i] + h[j] < Dist[i][j])
                Dist[i][j] = D[j] - h[i] + h[j];
    }
}

void InitDist()
{
    int i, j;
    for (i = 0; i < n; i++)
        for (j = 0; j < n; j++)
            Dist[i][j] = oo;
}

int main()
{
    Citire();
    InitDist();
    Johnson();
    Afisare();
    return 0;
}