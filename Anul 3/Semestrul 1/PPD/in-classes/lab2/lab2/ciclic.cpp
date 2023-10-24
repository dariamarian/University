#include <iostream>
#include <thread>
#include <valarray>
#include <ctime>
#include <thread>
#include <chrono>

using namespace std;

const int n = 10000;
//int a[n], b[n], c[n];

void initializeArray(int* a, int* b, const int n) {
    for (int i = 0; i < n; i++) {
        a[i] = rand() % 100;
        b[i] = rand() % 100;
    }
}

void ciclic_vector_sum(int id, const int* a, const int* b, int* c, const int p) {
    for (int i = id; i < n; i = i + p) {
        c[i] = a[i] + b[i];
    }
}

int main() {
    const int p = 8;
    thread th_c[p];
    int* a = new int[n];
    int* b = new int[n];
    int* c = new int[n];
    initializeArray(a, b, n);

    auto t_start = std::chrono::high_resolution_clock::now();

    for (int i = 0; i < p; i++) {
        th_c[i] = thread(ciclic_vector_sum, i, a, b, c, p);
    }
    for (int i = 0; i < p; i++) {
        th_c[i].join();
    }

    auto t_end = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end - t_start).count();

    cout << "elapsed_time_ms = " << elapsed_time_ms << "\n";

    return 0;
}