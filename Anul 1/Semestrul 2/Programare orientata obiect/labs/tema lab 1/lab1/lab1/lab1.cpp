#include <stdio.h>
#include <math.h>

void fractie(int k, int m, int n, int v[]) 
{
	/*Functia determina cifrele din scrierea fractiei subunitare si le afiseaza.
	Parametri de intrare: k numaratorul, m numitorul, n numarul de zecimale si un vector v.
	Rezultat: afiseaza 0 urmat de cifrele cautate.
	*/
	int ok = 1;
	if (k > m)
	{
		printf("Nu este fractie subunitara.\n");
		ok = 0;
	}
	if (ok==1)
	{ 
	float x = 0, var;
	x = k * pow(10, n);
	x = x / m;
	x = (int)x;
	int cx = x, t = 0;
	while (cx != 0) 
	{
		int u = cx % 10;
		v[t] = u;
		t++;
		cx /= 10;
	}
	int ok = 0;
	for (int i = t - 1; i >= 0; i--) 
	{
		if (t - 1 == n - 1 && ok == 0) {
			printf("0");
			ok = 1;
		}
		if (i == n - 1) {
			printf(".");
		}
		printf("%d", v[i]);
	}
	}
}

void afisare_cifre()
{
	/*
	Determina primele n cifre din scrierea fractiei subunitare k/m = 0.c1c2c3..., pentru k si 
	m numere naturale date.
	Parametri de intrare: nu exista.
	Rezultat: afisarea numerelor cautate.
	*/
	int k, m, n, v[100];
	scanf_s("%d", &n);
	scanf_s("%d", &k);
	scanf_s("%d", &m);
	fractie(k, m, n, v);
}

int prim(int n)
{
	/*
	Functie care verifica daca un numar este prim.
	Parametri de intrare: n-numarul verificat.
	Rezultat: returneaza 1 daca numarul este prim, respectiv 0 in caz contrar.
	*/
	int d;
	if (n < 2)
		return 0;
	if (n % 2 == 0 && n != 2)
		return 0;
	for (d = 3; d * d <= n; d = d + 2)
		if (n % d == 0)
			return 0;
	return 1;
}

void afisare_nrprime()
{
	/*
	Functie care afiseaza numerele prime mai mici decat un numar n citit de la tastatura cu ajutorul
	functiei prim.
	Parametri de intrare: nu exista.
	Rezultat: afisarea numerelor cautate.
	*/
	int n, i, ok=0;
	printf("Introduceti un numar: ");
	scanf_s("%d", &n);
	printf("Numerele prime mai mici decat %d sunt: ", n);
	for (i = 2; i < n; i++)
		if (prim(i) == 1)
		{
			printf("%d ", i);
			ok = 1;
		}
	if (ok == 0)
		printf("Nu exista numere prime mai mici decat %d.", n);
}
int main()
{
	/*
	Functia principala care afiseaza un meniu si permite efectuarea repetata a operatiei.
	*/
	int cmd;
	while (1)
	{
		printf("\nMeniu\n");
		printf("1.Afisarea numerelor prime mai mici decat un numar dat.\n");
		printf("2.Afisarea primelor n cifre din scrierea fractiei subunitare.\n");
		printf("3.Iesire.\n");
		printf("Introduceti comanda dorita: ");
		scanf_s("%d", &cmd);
		if (cmd == 3)
			return 0;
		else if (cmd == 1)
			afisare_nrprime();
		else
			afisare_cifre();
	}
	return 0;
}
