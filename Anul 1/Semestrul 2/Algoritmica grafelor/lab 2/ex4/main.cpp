#include <iostream>
#include <fstream>
using namespace std;

ifstream fin("graf.txt");

const int MaxN = 102, inf = 9999;
int n, madiacenta[MaxN][MaxN], x, y, s;
int color[MaxN], d[MaxN], pi[MaxN];

void push(int coada[], int& k, int numar) {
    k = k + 1;
    coada[k] = numar;
}

int pop(int coada[], int& k) {
    int rezultat = coada[1];
    for (int i = 1; i < k - 1; i++)
        coada[i] = coada[i + 1];
    k = k - 1;
    return rezultat;
}
int size(int k) {
    return k;
}

void BFS(int s)
{
    int Q[MaxN], k = 0, u;
    for (int u = 1; u <= n; u++)
    {
        if (u != s)
        {
            color[u] = 1;
            d[u] = inf;
            pi[u] = -1;
        }
    }
    color[s] = 2;
    d[s] = 0;
    pi[s] = 0;
    push(Q, k, s);
    while (size(k) != 0)
    {
        u = pop(Q, k);
        for (int i = 1; i <= n; i++)
        {
            if (madiacenta[u][i] == 1)
            {
                if (color[i] == 1)
                {
                    color[i] = 2;
                    d[i] = d[u] + 1;
                    pi[i] = u;
                    push(Q, k, i);
                }
            }
        }
        color[u] = 3;
    }

}
int main()
{
    fin >> n;
    while (fin >> x >> y)
        madiacenta[x][y] = 1;
    int i, j, k;
    cout << "Introduceti nodul de start:";
    cin >> s;
    BFS(s);
    for (int i = 1; i <= n; i++)
        if (d[i] != inf)
            cout<< i <<" " << d[i] << "\n";
    fin.close();
    return 0;
}
