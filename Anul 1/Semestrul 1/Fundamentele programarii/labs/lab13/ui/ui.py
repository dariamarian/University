from functions.functions import *


def main():
    suma = 0
    n = int(input("Introduceti n (numarul de monede):"))
    lista = []
    lista_rez = []
    i = 0
    for i in range(n):
        x = int(input("Introduceti valoarea monedei: "))
        lista.append(x)
    S = int(input("Introduceti S:"))
    lista.sort(key=lambda x: x, reverse=False)
    print("Metode:")
    print("1.Backtracking iterativ")
    print("2.Backtracking recursiv")
    cmd = int(input("Alegeti metoda:"))
    if cmd == 1:
        backtracking_iterativ(lista, lista_rez, S)
    elif cmd == 2:
        backtracking_recursiv(lista, lista_rez, S, suma, 0)
    else:
        print("Comanda invalida")
