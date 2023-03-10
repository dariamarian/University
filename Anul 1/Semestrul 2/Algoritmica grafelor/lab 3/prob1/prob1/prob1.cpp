#include <fstream>
#include <vector>
#include <queue>
using namespace std;

#define inf 0x3f3f3f3f

ifstream fin("dijkstra.in");
ofstream fout("dijkstra.out");

int n, nod, E;
int D[100001];
priority_queue< pair<int, int>, vector< pair<int, int> >, greater< pair<int, int> > > Q;
vector < vector < pair <int, int> > > G(100001);

void dijkstra(int nod) {
    for (int i = 0; i < n; ++i)
        D[i] = inf;
    D[nod] = 0;
    Q.push({ 0, nod });
    while (!Q.empty()) {
        int dist = Q.top().first;
        nod = Q.top().second;
        Q.pop();
        if (dist > D[nod])
            continue;
        for (auto x : G[nod])
            if (D[x.first] > dist + x.second)
            {
                D[x.first] = dist + x.second;
                Q.push({ D[x.first], x.first });
            }
    }
    for (int i = 0; i < n; ++i)
        if (D[i] == inf) fout << "INF ";
        else fout << D[i] << ' ';
}

int main()
{
    fin >> n >> E >> nod;
    int x, y, c;
    for (int i = 0; i < E; i++)
    {
        fin >> x >> y >> c;
        G[x].push_back({ y, c });
    }
    dijkstra(nod);
    return 0;
}
