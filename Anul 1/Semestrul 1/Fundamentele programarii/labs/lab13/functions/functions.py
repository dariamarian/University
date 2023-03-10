def afisare_rezultat(lista_rez):
    """
        functie care afiseaza solutia
    :param lista_rez:
    :return:
    """
    for i in range(len(lista_rez)):
        print(lista_rez[i], end=' ')
    print()


def backtracking_iterativ(lista, lista_rez, S):
    """
        metoda iterativa
    :param lista:
    :param lista_rez:
    :param S:
    :return:
    """
    ok = 0
    start = 0
    i = 0
    suma_curenta = 0
    while start < len(lista):
        if i >= len(lista):
            suma_curenta = 0
            lista_rez = []
            start += 1
            i = start
        if start >= len(lista):
            break
        if lista[i] + suma_curenta == S:
            lista_rez.append(lista[i])
            ok = 1
            afisare_rezultat(lista_rez)
            lista_rez.pop()
            i += 1
        elif lista[i] + suma_curenta < S:
            lista_rez.append(lista[i])
            suma_curenta += lista[i]
            i += 1
        else:
            suma_curenta -= lista_rez[-1]
            if len(lista_rez) >= 1:
                lista_rez.pop()
    if ok == 0:
        print("Suma nu se poate plati cu monedele introduse.")


def backtracking_recursiv(lista, lista_rez, S, suma_curenta, indice):
    """
        metoda recursiva
    :param lista:
    :param lista_rez:
    :param S:
    :param suma_curenta:
    :param indice:
    :return:
    """
    for i in range(indice, len(lista)):
        if suma_curenta+lista[i] == S:
            lista_rez.append(lista[i])
            afisare_rezultat(lista_rez)
            lista_rez.pop()
            return
        elif suma_curenta+lista[i] < S:
            lista_rez.append(lista[i])
            backtracking_recursiv(lista, lista_rez, S, suma_curenta+lista[i], i+1)
            lista_rez.pop()
        else:
            return
