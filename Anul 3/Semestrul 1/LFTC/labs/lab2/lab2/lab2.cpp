#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std;

int main() {
    ifstream fin("../input.txt");
    ofstream fout("../output.txt");

    vector<string> atoms;
    string line;
    while (getline(fin, line)) {
        istringstream lineStream(line);
        string element;
        while (lineStream >> element) {
            atoms.push_back(element);
        }
    }

    fin.close();

    for (const string& element : atoms) {
        fout << element << endl;
    }

    fout.close();

    return  0;
}