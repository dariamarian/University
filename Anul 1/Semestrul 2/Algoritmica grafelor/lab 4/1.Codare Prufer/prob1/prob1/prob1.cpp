#include <iostream>
#include <vector>
#include <fstream>
#include <deque>
#include <string>

using namespace std;

const int Maxx = 1e5 + 1;
int father[Maxx];
int freq[130];

typedef struct {
    int left;
    int right;
    int val;
}node;

bool dead[Maxx];
bool vis[Maxx];

vector<int> puffer;

void clear(bool v[]) {
    for (int i = 0; i < 100000; ++i) {
        v[i] = false;
    }
}

int main() {

    int n, root;
    ifstream fin("8-in.txt");
    fin >> n;
    for (int i = 0; i < n; ++i) {
        fin >> father[i];
        if (father[i] == -1) {
            root = i;
            continue;
        }
        vis[father[i]] = 1;
    }

    for (int k = 0; k < n - 1; ++k) {
        int mn = n;
        for (int i = 0; i < n; ++i) {
            if (vis[i] == 0 && dead[i] == 0) {
                mn = i;
                break;
            }
        }
        puffer.push_back(father[mn]);
        dead[mn] = 1;
        int young = father[mn];
        bool ok = 1;
        for (int i = 0; i < n; ++i) {
            if (young == father[i] && dead[i] == 0) {
                ok = 0;
            }
        }
        if (ok) {
            vis[father[mn]] = 0;
        }
    }
    cout << n - 1 << "\n";
    for (auto& node : puffer) {
        cout << node << " ";
    }
    cout << "\n";
    return 0;
}