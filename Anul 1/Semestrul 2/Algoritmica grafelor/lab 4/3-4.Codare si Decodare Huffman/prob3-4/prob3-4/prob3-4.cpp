#include <iostream>
#include <string>
#include <queue>
#include <map>
#include <bits/stdc++.h>
#include <cctype>
#include <cstring>
#include <string.h>
#include <unordered_map>
using namespace std;

int f[255];

struct Nod
{
    char c;
    int fr;
    Nod* st, * dr;
};

Nod* getNod(char c, int fr, Nod* st, Nod* dr)
{
    Nod* nod = new Nod();
    nod->c = c;
    nod->fr = fr;
    nod->st = st;
    nod->dr = dr;
    return nod;
}

struct comp
{
    bool operator()(const Nod* l, const Nod* r) const
    {
        return l->fr > r->fr || (l->fr == r->fr && l->c > r->c);
    }
};

bool frunza(Nod* radacina) {
    return radacina->st == nullptr && radacina->dr == nullptr;
}

void codare(Nod* radacina, string sir, map<char, string>& cod)
{
    if (radacina == nullptr) return;

    if (frunza(radacina))
        cod[radacina->c] = (sir != "") ? sir : "1";

    codare(radacina->st, sir + "0", cod);
    codare(radacina->dr, sir + "1", cod);
}

void decodare(Nod* radacina, int& i, string sir)
{
    if (radacina == nullptr) return;

    if (frunza(radacina))
    {
        cout << radacina->c;
        return;
    }
    i++;
    if (sir[i] == '0')
        decodare(radacina->st, i, sir);
    else
        decodare(radacina->dr, i, sir);
}

void buildHuffmanTree(string text)
{
    if (text == "")
        return;

    unordered_map<char, int> fr;

    for (char c : text)
        fr[c]++;

    priority_queue<Nod*, vector<Nod*>, comp> q;

    for (auto pair : fr) {
        q.push(getNod(pair.first, pair.second, nullptr, nullptr));
    }

    while (q.size() != 1)
    {
        Nod* st = q.top(); q.pop();
        Nod* dr = q.top(); q.pop();
        int suma = st->fr + dr->fr;
        q.push(getNod(min(st->c, dr->c), suma, st, dr));
    }
    Nod* radacina = q.top();

    map<char, string> huffman;
    codare(radacina, "", huffman);

    cout << "Codurile Huffman sunt:\n" << endl;
    for (auto pair : huffman)
        cout << pair.first << " " << pair.second << endl;

    cout << "\nMesajul care trebuie codat este:\n" << text << endl;

    string sir;
    for (char c : text) {
        sir = sir + huffman[c];
    }

    cout << "\nMesajul codat este:\n" << sir << endl;
    cout << "\nMesajul decodat este:\n";

    if (frunza(radacina))
        while (radacina->fr--)
            cout << radacina->c;
    else
    {
        int i = -1;
        while (i < (int)sir.size() - 1)
            decodare(radacina, i, sir);
    }
}
void frecventa(string s)
{
    int i, nr = 0, n = s.size();
    for (i = 0; i < n; i++)
        f[s[i] - ' ']++;
    for (i = 0; i < n; i++)
        if (f[s[i] - ' '] != 0)
        {
            nr++;
            cout << s[i] << " " << f[s[i] - ' '] << endl;
            f[s[i] - ' '] = 0;
        }
    cout << nr << endl;
}

int main()
{
    ifstream f("5-in.txt");
    string text;
    getline(f, text);
    frecventa(text);
    buildHuffmanTree(text);
    cout << endl;
    return 0;
}
