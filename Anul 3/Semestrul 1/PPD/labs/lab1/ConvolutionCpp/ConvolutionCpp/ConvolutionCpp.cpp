#include <iostream>
#include <fstream>
#include <thread>
#include <vector>

using namespace std;
using namespace chrono;

int N, M, n, m, p;

const int N_MAX = 10000;
const int M_MAX = 10000;
const int K_MAX = 6;

//int matrix[N_MAX][M_MAX];
//int kernel[K_MAX][K_MAX];
//int finalMatrix[N_MAX][M_MAX];

vector<vector<int>> matrix;
vector<vector<int>> kernel;
vector<vector<int>> finalMatrix;

void read(string path) {
    ifstream fin(path);

    fin >> N >> M;

    matrix = vector<vector<int>>(N, vector<int>(M));
    finalMatrix = vector<vector<int>>(N, vector<int>(M));

    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            fin >> matrix[i][j];
        }
    }

    fin >> n >> m;

    kernel = vector<vector<int>>(n, vector<int>(m));

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            fin >> kernel[i][j];
        }
    }

    fin.close();
}

void write(string path) {
    ofstream fout(path);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fout << finalMatrix[i][j] << " ";
        }
        fout << endl;
    }

    fout.close();
}

int singlePixelConvolution(int x, int y, int lineOffset, int columnOffset)
{
    int output = 0;
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < m; j++)
        {
            int ii = x - lineOffset + i;
            int jj = y - columnOffset + j;

            if (ii < 0) ii = 0;
            else if (ii >= N) ii = N - 1;

            if (jj < 0) jj = 0;
            else if (jj >= M) jj = M - 1;

            output += matrix[ii][jj] * kernel[i][j];
        }
    }
    return output;
}

void sequential(int lineOffset, int columnOffset) {
    auto startTime = high_resolution_clock::now();

    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            finalMatrix[i][j] = singlePixelConvolution(i, j, lineOffset, columnOffset);

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

void parallel(int lineOffset, int columnOffset, int start, int end) {
    if (N > M) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < M; j++) {
                finalMatrix[i][j] = singlePixelConvolution(i, j, lineOffset, columnOffset);
            }
        }
    }
    else {
        for (int i = 0; i < N; i++) {
            for (int j = start; j < end; j++) {
                finalMatrix[i][j] = singlePixelConvolution(i, j, lineOffset, columnOffset);
            }
        }
    }
}

void parallelization(int lineOffset, int columnOffset) {
    vector<thread> t;

    int start = 0, end = 0;
    int mx = max(N, M);
    int segment = mx / p;
    int rest = mx % p;

    auto startTime = high_resolution_clock::now();

    for (size_t i = 0; i < p; i++) {
        start = end;
        end = start + segment;
        if (rest > 0)
        {
            end++;
            rest--;
        }
        thread thr = thread(parallel, lineOffset, columnOffset, start, end);
        t.push_back(move(thr));
    }

    for (auto& th : t)
    {
        if (th.joinable())
            th.join();
    }

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

void check_valid(string path_t, string path_v) {
    ifstream fin_t(path_t);
    ifstream fin_v(path_v);

    int x, y;
    while (fin_t >> x && fin_v >> y) {
        if (x != y) {
            throw exception();
        }
    }

    if (fin_t >> x || fin_v >> x) {
        throw exception();
    }
}

int main(int argc, char** argv) {
    p = atoi(argv[1]);

    read("input.txt");

    int lineOffset = (N - 1) / 2;
    int columnOffset = (M - 1) / 2;

    if (p == 0) {
        sequential(lineOffset, columnOffset);
    }
    else {
        parallelization(lineOffset, columnOffset);
    }

    write("output.txt");

    if (p == 0) {
        write("valid.txt");
    }
    else {
        check_valid("output.txt", "valid.txt");
    }
}
