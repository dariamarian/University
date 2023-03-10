#include <iostream>
#include <fstream>

using namespace std;

ifstream f("in.txt");
int viz[1000], C[200][200],mat_ad[11][11]= {0};
#define inf 0x3f3f3f3f
void dfs(int x, int n)
{
    viz[x] = 1;
    for(int i = 1 ; i <= n; i++)
        if (mat_ad[x][i] == 1 && viz[i] == 0) dfs(i, n);
}

void Floyd_Warshall(int n)
{
    for (int k = 1; k <= n; ++k)
        for (int i = 1; i <= n; ++i)
            for (int j = 1; j <= n; ++j)
                if (C[i][k] != inf && C[k][j] != inf)
                    if(C[i][j] > C[i][k] + C[k][j])
                        C[i][j] = C[i][k] + C[k][j];
}

void afisare(int a[11][11],int n,int m)
{
    int i,j;
    for(i=1; i<=n; i++)
    {
        for(j=1; j<=m; j++)
            cout<<a[i][j]<<" ";
        cout<<endl;
    }
}

int main()
{
    int i,j,n,v1,v2,nr_muchii=1,mat_inc[11][11]= {0};
    f>>n;
    for (int i = 1 ; i <= n ; i++)
    {
        for (int j = 1 ; j <= n; j++)
            if(i == j) C[i][j] = 0;
            else C[i][j] = inf;
    }
    while(f>>v1>>v2)
    {
        mat_ad[v1][v2]=mat_ad[v2][v1]=1;
        mat_inc[v1][nr_muchii]=mat_inc[v2][nr_muchii]=1;
        C[v1][v2] = C[v2][v1] = 1;
        nr_muchii++;
    }
    nr_muchii--;


    cout<<"Matricea de adiacenta este:"<<endl;
    afisare(mat_ad,n,n);


    cout<<"Lista de adiacenta este:"<<endl;
    for(i=1; i<=n; i++)
    {
        cout<<i<<": ";
        for(j=1; j<=n; j++)
            if(mat_ad[i][j] == 1)
                cout<<j<<" ";
        cout<<endl;
    }


    cout<<"Matricea de incidenta este:"<<endl;
    for(i=1; i<=n; i++)
    {
        for(j=1; j<=nr_muchii; j++)
            cout<<mat_inc[i][j]<<" ";
        cout<<endl;
    }


    cout << "Lista de varfuri izolate este: ";
    int ok2=0;
    for (i=1; i<=n; i++)
    {
        int ok=0;
        for (j=1; j<=n; j++)
            if(mat_ad[i][j]==1)
                ok ++;
        if(ok==0)
        {
            cout<<i<< " ";
            ok2=1;
        }
    }
    if(ok2==0)
        cout<<"Nu exista varfuri izolate.";
    cout<<endl;


    int c=0, regular=0;
    for(int j=1; j<=n; j++)
        if(mat_ad[1][j]==1)
            c++;
    for (int i=2; i<=n; i++)
    {
        int ok=0;
        for (int j=1; j<=n; j++)
            if(mat_ad[i][j]==1)
                ok ++;
        if(ok!=c)
            regular++;
    }
    if(regular == 0)
        cout << "Graful dat este regular.";
    else
        cout << "Graful dat este neregular.";


    cout << endl << "Matricea distantelor este: " << endl;
    Floyd_Warshall(n);

    for (int i = 1 ; i <= n ; ++i)
    {
       for (int j = 1 ; j <= n ; ++j)
            if(C[i][j] == inf)
                cout << -1 << " ";
            else
                cout << C[i][j] << " ";
        cout <<endl;
    }


    cout << "Conexitate: ";
    dfs(1, n);
    int ok = 0;
    for(int i = 1 ; i <= n ; i++)
        if(viz[i] == 0)
            ok ++;
    if(ok == 0)
        cout << "Graf conex.";
    else
        cout << "Graf neconex.";
    cout<<endl;
    return 0;
}
