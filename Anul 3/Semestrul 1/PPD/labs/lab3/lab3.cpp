#include <iostream>
#include <fstream>
#include <string>
#include <random>
#include <chrono>
#include <vector>
#include "mpi.h"

using namespace std;
using namespace std::chrono;

string fileName = "input.txt";
// T1 - inainte de inceperea citirii datelor si pana la finalizarea scrierii in fisier
// T2 - doar intervalul in care se calculeaza
steady_clock::time_point start1;
steady_clock::time_point start2;
duration<double, ::milli> duration1;
duration<double, ::milli> duration2;
ifstream fin(fileName);

const int N = 1000;
const int M = 1000;
const int n = 3;
int halfN = n / 2;

int flatBigMatrix[N * M];
int matrix[N][M];
int kernel[n][n];

void readFromFile(bool);
void writeToFile(int matrix[N][M]);
void copyVector(int[], int[]);
bool verifyResult();
bool verifyResultScatter();

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

// varianta 1
int main(int argc, char* argv[])
{
	srand(time(0));

	int myid, numprocs, namelen;
	char processor_name[MPI_MAX_PROCESSOR_NAME];
	int temp;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &myid);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
	MPI_Get_processor_name(processor_name, &namelen);

	// master process
	if (myid == 0)
	{
		int cat = N / (numprocs - 1);
		int rest = N % (numprocs - 1);
		int start = 0;
		int end;

		start1 = high_resolution_clock::now();
		readFromFile(false);

		// citeste matricea kernel si o transmite prin broadcast tuturor celorlalte procese
		MPI_Bcast(&kernel, n * n, MPI_INT, 0, MPI_COMM_WORLD);

		start2 = high_resolution_clock::now();

		// citeste din fisier N/(p-1) linii
		for (int procIndex = 1; procIndex < numprocs; procIndex++)
		{
			end = start + cat;
			if (rest > 0)
			{
				end++;
				rest--;
			}
			for (int i = start; i < end; i++)
			{
				for (int j = 0; j < M; j++)
				{
					fin >> temp;
					matrix[i][j] = temp;
				}
			}
			// trimite procesului i liniile citite
			MPI_Send(&start, 1, MPI_INT, procIndex, 0, MPI_COMM_WORLD);
			MPI_Send(&end, 1, MPI_INT, procIndex, 0, MPI_COMM_WORLD);
			MPI_Send(matrix[start], (end - start) * M, MPI_INT, procIndex, 0, MPI_COMM_WORLD);
			start = end;
		}

		// primeste de la fiecare (alt) process valori modificate ale matricei
		for (int procIndex = 1; procIndex < numprocs; procIndex++)
		{
			MPI_Recv(&start, 1, MPI_INT, procIndex, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			MPI_Recv(&end, 1, MPI_INT, procIndex, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			MPI_Recv(matrix[start], (end - start) * M, MPI_INT, procIndex, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		}

		auto endTime = high_resolution_clock::now();
		duration2 = endTime - start2;
		cout << duration2.count() << '\n';

		writeToFile(matrix);

		endTime = high_resolution_clock::now();
		duration1 = endTime - start1;
		cout << duration1.count() << '\n';
		verifyResult();
	}
	// worker processes
	else
	{
		// primeste de la procesul 0 matricea kernel (broadcast)
		MPI_Bcast(&kernel, n * n, MPI_INT, 0, MPI_COMM_WORLD);

		int start, end;
		// primeste date de la procesul 0
		MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		MPI_Recv(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		int* auxmatrix = new int[(end - start) * M];
		MPI_Recv(auxmatrix, (end - start) * M, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

		int sum;
		int columnIndex;

		int buffer[3][M];

		if (myid == 1)
		// first process
		{
			// it sends a portion of the auxmatrix to the next process
			MPI_Send(&auxmatrix[(end - start - 1) * M], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD);
			// it then receives data from the next process into buffer[2]
			MPI_Recv(&buffer[2], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		}
		else if (myid < numprocs - 1)
		{
			// it receives data from the previous process into buffer[0]
			MPI_Recv(buffer[0], M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

			// it sends the entire auxmatrix to the previous process
			MPI_Send(auxmatrix, M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD);

			// it sends data to the next process
			MPI_Send(&auxmatrix[(end - start - 1) * M], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD);

			// it receives data from the next process into buffer[2]
			MPI_Recv(&buffer[2], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		}
		else
		// last process
		{
			// it receives data from the previous process into buffer[0]
			MPI_Recv(buffer[0], M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

			// it sends the entire auxmatrix to the previous process
			MPI_Send(auxmatrix, M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD);
		}

		if (myid == 1)
		// first process
		{
			// the first process needs to have the first row of the input matrix
			copyVector(buffer[0], auxmatrix);
		}
		else if (myid == numprocs - 1)
		// last process
		{
			// the last process needs to have the last row of the input matrix
			copyVector(buffer[2], &auxmatrix[(end - start - 1) * M]);
		}

		// calculeaza noile valori pentru celulele primite
		for (int i = start; i < end; i++)
		{
			// the current process needs to have the current and the previous row
			copyVector(buffer[1], auxmatrix + ((i - start) * M));
			for (int j = 0; j < M; j++)
			{
				sum = 0;
				for (int k = 0; k < 2; k++)
				{
					for (int l = 0; l < n; l++)
					{
						// columnIndex is used to ensure that the indices are within the bounds 
						columnIndex = min(max(j + l - halfN, 0), M - 1);
						sum += buffer[k][columnIndex] * kernel[k][l];
					}
				}
				for (int l = 0; l < n; l++)
				{
					columnIndex = min(max(j + l - halfN, 0), M - 1);
					// if is the last row, the buffer[2] is used instead of the auxmatrix 
					if (i >= end - 1) {
						sum += buffer[2][columnIndex] * kernel[2][l];
						continue;
					}
					sum += auxmatrix[(i - start + 1) * M + columnIndex] * kernel[2][l];
				}
				auxmatrix[(i - start) * M + j] = sum;
			}
			copyVector(buffer[0], buffer[1]);
		}

		// trimite procesului 0 matricea modificata
		MPI_Send(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(auxmatrix, (end - start) * M, MPI_INT, 0, 0, MPI_COMM_WORLD);

		delete[] auxmatrix;
	}

	MPI_Finalize();
	return 0;
}
//
//// varianta 2
//int main(int argc, char* argv[])
//{
//	srand(time(0));
//
//	int myid, numprocs, namelen;
//	char processor_name[MPI_MAX_PROCESSOR_NAME];
//	int temp;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_rank(MPI_COMM_WORLD, &myid);
//	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
//	MPI_Get_processor_name(processor_name, &namelen);
//
//	if (myid == 0)
//	// master process
//	{
//		start1 = high_resolution_clock::now();
//		readFromFile(true);
//	}
//
//	// stores the size of each batch of data
//	int batchSize = N / numprocs;
//	// stores the current process's batch of data
//	int* auxBatchMatrix = new int[batchSize * M];
//	// stores the processed batch of data
//	int* auxBigMatrix = new int[batchSize * M];
//	start2 = high_resolution_clock::now();
//	MPI_Scatter(flatBigMatrix, batchSize * M, MPI_INT, auxBatchMatrix, batchSize * M, MPI_INT, 0, MPI_COMM_WORLD);
//	MPI_Bcast(&kernel, n * n, MPI_INT, 0, MPI_COMM_WORLD);
//	int buffer[3][M];
//
//	if (myid == 0)
//	// first process
//	{
//		// sends the last row of the current process's batch of data to the next process
//		MPI_Send(&auxBatchMatrix[(batchSize - 1) * M], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD);
//		// receives the first row of the next process's batch of data from the next process
//		MPI_Recv(&buffer[2], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//		// the first process needs to have the first row of the input matrix
//		copyVector(buffer[0], auxBatchMatrix);
//	}
//	else if (myid < numprocs - 1)
//	{
//		// receives the first row of the previous process's batch of data from the previous process
//		MPI_Recv(buffer[0], M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//		// sends the entire auxBatchMatrix to the previous process
//		MPI_Send(auxBatchMatrix, M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD);
//
//		// sends the last row of the current process's batch of data to the next process
//		MPI_Send(&auxBigMatrix[(batchSize - 1) * M], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD);
//		// receives the first row of the next process's batch of data from the next process
//		MPI_Recv(&buffer[2], M, MPI_INT, myid + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//	}
//	else
//	// last process
//	{
//		// receives the first row of the previous process's batch of data from the previous process
//		MPI_Recv(buffer[0], M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
//		// sends the entire auxBatchMatrix to the previous process
//		MPI_Send(auxBatchMatrix, M, MPI_INT, myid - 1, 0, MPI_COMM_WORLD);
//		// the last process needs to have the last row of the input matrix
//		copyVector(buffer[2], &auxBatchMatrix[(batchSize - 1) * M]);
//	}
//
//	// same as in the first version, but we use batchSize instead of end - start
//	for (int i = 0; i < batchSize; i++)
//	{
//		copyVector(buffer[1], auxBatchMatrix + (i * M));
//		for (int j = 0; j < M; j++)
//		{
//			auxBigMatrix[i * M + j] = 0;
//			for (int k = 0; k < 2; k++)
//			{
//				for (int l = 0; l < n; l++)
//				{
//					int columnIndex = min(max(j + l - halfN, 0), M - 1);
//					auxBigMatrix[i * M + j] += buffer[k][columnIndex] * kernel[k][l];
//				}
//			}
//			for (int l = 0; l < n; l++)
//			{
//				int columnIndex = min(max(j + l - halfN, 0), M - 1);
//				if (i >= batchSize - 1) {
//					auxBigMatrix[i * M + j] += buffer[2][columnIndex] * kernel[2][l];
//					continue;
//				}
//				auxBigMatrix[i * M + j] += auxBatchMatrix[(i + 1) * M + columnIndex] * kernel[2][l];
//			}
//		}
//		copyVector(buffer[0], buffer[1]);
//	}
//
//	MPI_Gather(auxBigMatrix, batchSize * M, MPI_INT, flatBigMatrix, batchSize * M, MPI_INT, 0, MPI_COMM_WORLD);
//
//	if (myid == 0)
//	{
//		auto endTime2 = high_resolution_clock::now();
//		duration2 = endTime2 - start2;
//		cout << duration2.count() << '\n';
//		ofstream fout("output.txt");
//		for (int i = 0; i < N; i++)
//		{
//			for (int j = 0; j < M; j++)
//			{
//				fout << flatBigMatrix[i * M + j] << " ";
//			}
//			fout << '\n';
//		}
//		auto endTime1 = high_resolution_clock::now();
//		duration1 = endTime1 - start1;
//		cout << duration1.count() << '\n';
//		verifyResultScatter();
//	}
//
//	MPI_Finalize();
//	return 0;
//}

void readFromFile(bool isScatter)
{
	int temp;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			fin >> temp;
			kernel[i][j] = temp;
		}
	}
	if (!isScatter)
	{
		return;
	}
	for (int i = 0; i < N * M; i++) {
		fin >> temp;
		flatBigMatrix[i] = temp;
	}
}

void writeToFile(int matrix[N][M])
{
	::ofstream fout("output.txt");
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			fout << matrix[i][j] << " ";
		}
		fout << '\n';
	}
	fout.close();
}

void copyVector(int newVector[], int oldVector[])
{
	for (int i = 0; i < M; i++)
	{
		newVector[i] = oldVector[i];
	}
}

bool verifyResult()
{
	std::ifstream fin("output_lab2.txt");
	int temp;
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			fin >> temp;
			if (matrix[i][j] != temp)
			{
				return false;
			}
		}
	}
	return true;
}

bool verifyResultScatter()
{
	std::ifstream fin("output_lab2.txt");
	int temp;
	for (int i = 0; i < N * M; i++) {
		fin >> temp;
		if (flatBigMatrix[i] != temp)
		{
			return false;
		}
	}
	return true;
}

//ctrl-k-c - comment
//ctrl k-u - uncomment