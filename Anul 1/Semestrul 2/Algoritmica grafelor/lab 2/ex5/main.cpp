#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("graf.txt");

const int N = 41;
const int INF = 0x3F3F3F3F;

int n;
bool t[N][N], g[N][N];

enum COLOR { ALB=0, GRI, NEGRU };
void __dfs_visit(int a)
{
	static int time = 0;
	static int d[N], f[N], color[N], p[N];

	time++;
	d[a] = time;
	color[a] = GRI;
	for (int b = 1; b <= n; ++b) {
		if (color[b] == ALB && g[a][b]) {
			cout << b << "\n";
			p[b] = a;
			__dfs_visit(b);
		}
	}
	color[a] = NEGRU;
	time++;
	f[a] = time;
}

void dfs_visit() {
	cout << "-Dfs visit-\n";
	cout << "start = ";
	int s;
	cin >> s;
	__dfs_visit(s);
	cout << "\n";
}


int main() {
	fin >> n;
	int a, b;
	while (fin >> a >> b) {
		g[a][b] = t[a][b] = true;
	}
	dfs_visit();
	return 0;
}
