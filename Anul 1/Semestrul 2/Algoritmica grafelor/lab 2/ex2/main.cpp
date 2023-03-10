#include<iostream>
#include<fstream>

using namespace std;
ifstream f("graf.in");

int m[100][100]= {0}, nr, i, j, k, x, y;

int main()
{
    f>>nr;
    while(f>>x and f>>y)
        m[x][y]=1;
    for(i = 1; i <= nr; i++)
        m[i][i]=1;
    cout<<"Matricea inchiderii tranzitive:\n";
    for(k = 1; k <= nr; k++)
        for(i = 1; i <= nr; i++)
            for(j = 1; j <= nr; j++)
                if(m[i][j] == 0)
                    m[i][j] = (m[i][k] && m[k][j]);
    for(i = 1; i <= nr; i++)
    {
        for(j = 1; j <= nr; j++)
            cout<<m[i][j]<<" ";
        cout<<endl;
    }
    return 0;
}
