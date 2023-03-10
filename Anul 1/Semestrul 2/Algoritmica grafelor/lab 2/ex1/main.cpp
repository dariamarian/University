#include<iostream>
#include<fstream>

using namespace std;
ifstream f("graf.txt");

typedef struct
{
    int i, j;
} Muchie;
int n, x, y, nrm = 0, m[50][50], vecini[50];
Muchie muchii[100];

void citireGraf()
{
    int i, j;
    Muchie muchie;
    f>> n;
    while (f>> i >> j)
    {
        muchie.i = i;
        muchie.j = j;
        muchii[nrm] = muchie;
        nrm++;
    }
}

void adiacenta()
{
    int i, j, k;
    for (i = 1; i <= n; i++)
        vecini[i] = 0;
    for (i = 0; i <= n; i++)
        for (j = 0; j <= n; j++)
            m[i][j] = 0;
    for (k = 0; k < nrm; k++)
    {
        m[muchii[k].i][vecini[muchii[k].i]] = muchii[k].j;
        vecini[muchii[k].i]++;
    }
}

void deleteFromQueue(int x, int queue[], int &lg)
{
    int i;
    for (i = 0; queue[i] != x; i++)
        ;
    while (i < lg)
    {
        queue[i] = queue[i + 1];
        i++;
    }
    lg--;
}

int notInQueue(int y, int queue[], int lg)
{
    int ok = 1;
    for (int i = 0; i < lg; i++)
        if (queue[i] == y)
            ok = 0;
    return ok;
}

void moore(int sursa, int drum[])
{
    int x, y, inf = 10000, queue[50], lg = 0;
    for (int i = 1; i <= nrm; i++)
        drum[i] = inf;
    queue[lg] = sursa;
    lg++;
    while (lg > 0)
    {
        x = queue[0];
        deleteFromQueue(x, queue, lg);
        for (int i = 0; i < vecini[x]; i++)
        {
            y = m[x][i];
            if (drum[y] == inf)
            {
                if (drum[x] != inf)
                    drum[y] = drum[x] + 1;
                else
                    drum[y] = 1;
                if (notInQueue(y, queue, lg) == 1)
                {
                    queue[lg] = y;
                    lg++;
                }
            }
        }
    }
}

void drumMinim(int sursa, int destinatie, int drum[])
{
    int x, y, inf = 10000, parinte[50], queue[50], lg = 0;
    for (int i = 1; i <= n; i++)
        parinte[i] = 0;
    for (int i = 1; i <= nrm; i++)
        drum[i] = inf;
    lg = 0;
    queue[lg] = sursa;
    lg++;
    while (lg > 0)
    {
        x = queue[0];
        deleteFromQueue(x, queue, lg);
        for (int i = 0; i < vecini[x]; i++)
        {
            y = m[x][i];
            if (parinte[y] == 0)
                parinte[y] = x;
            if (drum[y] == inf)
            {
                if (drum[x] != inf)
                    drum[y] = drum[x] + 1;
                else
                    drum[y] = 1;
                if (notInQueue(y, queue, lg) == 1)
                {
                    queue[lg] = y;
                    lg++;
                }
            }
        }
    }
    if (sursa == destinatie && drum[destinatie] == inf)
    {
        cout << "Nu exista drum!\n";
        return;
    }
    int fiu = destinatie, drumMinim[10], k = 0;
    for (int i = 0; i < drum[destinatie]; i++)
    {
        drumMinim[k] = fiu;
        k++;
        fiu = parinte[fiu];
    }
    if (drum[destinatie] > 0)
    {
        cout << sursa << " ";
        for (int i = k - 1; i >= 0; i--)
            cout << drumMinim[i] << " ";
    }
    else
        cout << "Nu exista drum!";
    cout << endl;
}

int main()
{
    int sursa, destinatie, drum[50];
    citireGraf();
    adiacenta();
    cout << "Sursa: ";
    cin >> sursa;
    cout << "Destinatie: ";
    cin >> destinatie;
    cout << endl;
    drumMinim(sursa, destinatie, drum);
    return 0;
}
