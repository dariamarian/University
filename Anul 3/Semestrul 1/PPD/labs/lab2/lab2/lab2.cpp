#include <iostream>
#include <thread>
#include <valarray>
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

void liniar_vector_sum(int start, int end, const int* a, const int* b, int* c, const int p) {

    for (int i = start; i < end; i++)
        c[i] = a[i] + b[i];
}

int main() {
    const int p = 8;
    thread th[p];
    int* a = new int[n];
    int* b = new int[n];
    int* c = new int[n];

    initializeArray(a, b, n);

    int end = n / p;
    int start = 0;
    int reminder = n % p;
    auto t_start = std::chrono::high_resolution_clock::now();

    for (int i = 0; i < p; i++) {
        end = start + n / p + (i < reminder ? 1 : 0);
        reminder--;
        th[i] = thread(liniar_vector_sum, start, end, a, b, c, p);
        start = end;
    }
    for (int i = 0; i < p; i++) {
        th[i].join();
    }
    auto t_end = std::chrono::high_resolution_clock::now();
    double elapsed_time_ms = std::chrono::duration<double, std::milli>(t_end - t_start).count();

    cout << "elapsed_time_ms = " << elapsed_time_ms << "\n";

    return 0;
}