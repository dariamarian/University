#include <iostream>
#include <fstream>
#include <climits>
#include <vector>
#include<bits/stdc++.h>
using namespace std;

ifstream f("10-in.txt");
//ofstream g("1-out.txt");

int v, e, w[10000][10000], nr_m;

struct muchie 
{
    int x, y, cost;
}m[10000];

struct nod 
{
    int key;
    int p;
}nodes[10000];

int extract_min(vector<pair<int, int>>& v) 
{
    int min = INT_MAX, poz;
    for (int i = 0; i < v.size(); i++) {
        if (v[i].second < min)
        {
            min = v[i].second;
            poz = i;
        }
    }
    return poz;
}

int find_nod(vector<pair<int, int>>& v, int nod) 
{
    for (int i = 0; i < v.size(); i++)
        if (v[i].first == nod)
            return 1;
    return 0;
}

void prim() 
{
    for (int i = 0; i < v; i++)
    {
        nodes[i].key = INT_MAX / 2;
        nodes[i].p = -1;
    }
    vector<pair<int, int>>q;
    for (int i = 0; i < v; i++)
        q.push_back(make_pair(i, nodes[i].key));
    while (q.size()) 
    {
        int u = extract_min(q);
        int val = q[u].first;  //val este elementul cu cheia minima
        q.erase(q.begin() + u); //stergem val din coada
        int min_muchie = INT_MAX, c1 = -1, c2 = -1;
        for (int j = 0; j < v; j++) 
        {
            if (w[val][j] != INT_MAX) 
            {//parcurgem adj[val] si pentru fiecare nod adiacent
                if (find_nod(q, j) == 1 && w[val][j] < nodes[j].key)
                {
                    //actualizam parintele si cheia (daca e cazul)
                    nodes[j].p = val;
                    nodes[j].key = w[val][j];
                    for (int k = 0; k < q.size(); k++)   //trebuie sa modificam noua cheie si in vector
                        if (q[k].first == j)
                            q[k].second = w[val][j];
                }
            }
        }
    }
}

int main()
{
    int i, j, x, y, c, s;
    f >> v >> e;
    for (i = 0; i < v; i++)
        for (j = 0; j < v; j++) w[i][j] = INT_MAX;  //initializare matrice costuri
    for (i = 1; i <= e; i++)
    {
        f >> x >> y >> c;
        w[x][y] = w[y][x] = c;
    }
    prim();

    int sum = 0;
    //determinam muchiile 

    for (int i = 0; i < v; i++) 
    {//pentru fiecare nod 
        for (int k = 0; k < v; k++) 
        {
            if (nodes[k].p == i) 
            { // determinam fii, adaugam muchia si costul
                m[nr_m++].cost = w[i][k];
                m[nr_m].x = i; m[nr_m].y = k;
                sum += w[i][k];  //adunam costul muchiei la suma totala (costul arborelui)
            }
        }
    }
    cout << sum << endl;
    cout << nr_m << endl;
    for (int i = 1; i <= nr_m; i++) 
        cout << m[i].x << " " << m[i].y << endl;
    return 0;
}
