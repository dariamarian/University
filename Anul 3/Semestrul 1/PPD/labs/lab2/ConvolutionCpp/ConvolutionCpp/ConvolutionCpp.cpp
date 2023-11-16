#include <iostream>
#include <fstream>
#include <thread>
#include <vector>
#include <mutex>
#include <queue>
#include <condition_variable>
#include <time.h>
#include <barrier>

using namespace std;
using namespace chrono;

int N, M, n, m, p;

const int N_MAX = 10000;
const int M_MAX = 10000;
const int K_MAX = 6;

vector<vector<int>> matrix;
vector<vector<int>> kernel;

class MyBarrier {
private:
    mutex m;
    condition_variable cv;
    int counter;
    int waiting;
    int thread_count;
public:
    MyBarrier(int count) : thread_count(count), counter(0), waiting(0) {}
    void wait() {
        unique_lock<mutex> lk(m);
        ++counter;
        ++waiting;
        cv.wait(lk, [&] {return counter >= thread_count; });
        cv.notify_one();
        --waiting;
        if (waiting == 0) {
            counter = 0;
        }
        lk.unlock();
    }
};

void read(string path) {
    ifstream fin(path);

    fin >> N >> M;

    matrix = vector<vector<int>>(N, vector<int>(M));

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
            fout << matrix[i][j] << " ";
        }
        fout << endl;
    }

    fout.close();
}

int calculateConvolution(vector<int> values, int j, int kernelRow) {
    return values[max(j - 1, 0)] * kernel[kernelRow][0] + values[j] * kernel[kernelRow][1] + values[min(M - 1, j + 1)]
        * kernel[kernelRow][2];
}


void sequential(int offset) {
    vector<int> previousLine;
    vector<int> currentLine;

    previousLine = matrix[0];
    currentLine = matrix[0];

    vector<int> buffer;

    auto startTime = high_resolution_clock::now();
    for (int i = 0; i < N; i++) {
        buffer.clear();
        for (int j = 0; j < M; j++) {
            int output = 0;
            output = calculateConvolution(previousLine, j, 0) + calculateConvolution(currentLine, j, 1) + calculateConvolution(matrix[min(N - 1, i + 1)], j, 2);

            buffer.push_back(output);
        }

        matrix[i].assign(buffer.begin(), buffer.end());

        previousLine.assign(currentLine.begin(), currentLine.end());
        currentLine.assign(matrix[min(N - 1, i + 1)].begin(), matrix[min(N - 1, i + 1)].end());
    }

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

void parallel(MyBarrier& barrier, int offset, int start, int end) {
    vector<int> previousLine;
    vector<int> currentLine;

    if (start > 0) {
        previousLine = matrix[start - 1];
    }
    else {
        previousLine = matrix[start];
    }

    currentLine = matrix[start];

    vector<int> bufferUp;
    vector<int> bufferDown;
    bufferUp.clear();
    bufferDown.clear();

    for (int i = start; i < end; i++) {
        for (int j = 0; j < M; j++) {
            int output = 0;
            output = calculateConvolution(previousLine, j, 0) + calculateConvolution(currentLine, j, 1) +
                calculateConvolution(matrix[min(N - 1, i + 1)], j, 2);

            if (i == end - 1) {
                bufferDown.push_back(output);
            }
            else if (i == start) {
                bufferUp.push_back(output);
            }
            else {
                matrix[i][j] = output;
            }
        }

        previousLine = currentLine;
        currentLine = matrix[min(N - 1, i + 1)];
    }

    barrier.wait();

    matrix[start] = bufferUp;
    matrix[end - 1] = bufferDown;
}

void parallelization(MyBarrier& barrier, int offset) {
    vector<thread> t;

    int start = 0, end = 0;
    int segment = N / p;
    int rest = N % p;

    auto startTime = high_resolution_clock::now();

    for (size_t i = 0; i < p; i++) {
        start = end;
        end = start + segment;
        if (rest > 0)
        {
            end++;
            rest--;
        }
        thread thr = thread(parallel, ref(barrier), offset, start, end);
        t.push_back(std::move(thr));
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

    MyBarrier barrier{ p };

    read("C:/Users/daria/Github/University/Anul 3/Semestrul 1/PPD/labs/lab2/ConvolutionCpp/ConvolutionCpp/input.txt");

    int offset = (n - 1) / 2;

    if (p == 0) {
        sequential(offset);
    }
    else {
        parallelization(barrier,offset);
    }
    write("output.txt");

    if (p == 0) {
        write("valid.txt");
    }
    else {
        check_valid("output.txt", "valid.txt");
    }
}