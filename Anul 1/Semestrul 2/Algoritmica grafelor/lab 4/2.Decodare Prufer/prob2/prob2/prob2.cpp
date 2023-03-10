#include <iostream>
#include <vector>
#include <fstream>
#include <deque>

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

    ifstream fin("7-in.txt");
    int n;
    fin >> n;
    deque<int> dq;
    dq.clear();
    for (int i = 0; i < n; ++i) {
        int x; fin >> x;
        dq.push_back(x);
        father[i] = -1;
    }
    father[n] = -1;
    for (int i = 0; i < n; ++i) {
        int son = dq.front();
        clear(vis);
        for (auto& it : dq) {
            vis[it] = 1;
        }
        int mn = -1;
        for (int j = 0; j <= n; ++j) {
            if (vis[j] == 0) {
                mn = j;
                break;
            }
        }
        father[mn] = son;
        dq.pop_front();
        dq.push_back(mn);
    }
    cout << n + 1 << "\n";
    for (int i = 0; i <= n; ++i) {
        cout << father[i] << " ";
    }
    cout << "\n";
    return 0;

}