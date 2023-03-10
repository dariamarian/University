import math


def adauga_nr_la_final(l, nr):
    """
                adauga un numar complex la finalul listei
    """
    l.append(nr)
    # l[len(l)] = complex(nr)
    return l


def insereaza_nr_pe_pozitie(l, nr, poz):
    """
                adauga un numar complex pe pozitia data de utilizator
    """
    l.insert(poz, nr)
    # lenght = len(l)
    # for index in range(lenght, poz - 1, -1):
    #    l[index] = l[index - 1]
    # l[poz] = nr
    return l


def sterge_element_de_pe_pozitie(l, poz):
    """
                sterge un numar complex de pe pozitia data de utilizator
    """
    del l[poz]
    return l


def sterge_elemente_din_interval(l, st, dr, stack):
    """
                sterge numerele complexe din intervalul dat de utilizator
    """
    for i in range(st, dr+1, 1):
        stack.append(["insereaza_numar", st, l[st]])
        del l[st]
    # for index in range(st, dr+1):
    #    del l[index]
    return l


def inlocuieste_aparitiile(l, x, y):
    """
                inlocuieste aparitiile unui numar complex dat de utilizator cu un alt numar complex introdus de acesta
    """
    for i in range(len(l)):
        if l[i] == x:
            l[i] = y
    return l


def tipareste_parte_imaginara(l, st, dr):
    """
               tipareste partea imaginara a numerelor complexe din intervalul dat de utilizator
    """
    l2 = []
    for i in range(st, dr + 1):
        l2.append(l[i].imag)
    return l2


def tipareste_nr_cu_modul_mai_mic_decat_10(l):
    """
                tipareste numerele complexe care au modulul mai mic decat 10
    """
    k = []
    for i in range(len(l)):
        if math.sqrt(l[i].real * l[i].real + l[i].imag * l[i].imag) < 10:
            k.append(complex(l[i]))
    if len(k) == 0:
        return("Nu exista astfel de numere.")
    else:
        return k


def tipareste_nr_cu_modul_egal_cu_10(l):
    """
                    tipareste numerele complexe care au modulul egal cu 10
    """
    k = []
    for i in range(len(l)):
        if math.sqrt(l[i].real * l[i].real + l[i].imag * l[i].imag) == 10:
            k.append(complex(l[i]))
    if len(k) == 0:
        return("Nu exista astfel de numere.")
    else:
        return k


def suma_numerelor(l, st, dr):
    """
            calculeaza suma numerelor dintr-o subsecventă dată
    """
    s = 0
    for i in range(st, dr+1):
        s = complex(s + l[i])
    return s


def produsul_numerelor(l, st, dr):
    """
            calculeaza produsul numerelor dintr-o subsecventă dată
    """
    p = 1
    for i in range(st, dr+1):
        p = complex(p * l[i])
    return p


def lista_sortata(l):
    """
            sorteaza lista descrescător după partea imaginara
    """
    for i in range(len(l)-1):
        for j in range(len(l)-i-1):
            if l[j].imag < l[j+1].imag:
                l[j], l[j+1] = l[j+1], l[j]
            if l[j].imag == l[j+1].imag:
                if l[j].real < l[j+1].real:
                    l[j], l[j + 1] = l[j + 1], l[j]
    return l


def nr_prim(x):
    """
        verifica daca un numar este prim.
    """
    if x < 2:
        return False
    if x == 2:
        return True
    if x % 2 == 0:
        return False
    d = 3
    while d * d <= x:
        if x % d == 0:
            return False
        d = d + 2
    return True


def filtrare_parte_reala_prim(l):
    """
                elimina din lista numerele complexe la care partea reala este prim
    """
    l_filtrata=[]
    for element in l:
        if nr_prim(element.real) == False:
            l_filtrata.append(element)
    return l_filtrata


def filtrare_modul(l, operatiune, numar):
    """
                elimina din lista numerele complexe la care modulul este <,= sau > decât un număr dat.
    """
    l_filtrata = []
    if operatiune == "<":
        for element in l:
            if math.sqrt(element.real*element.real+element.imag*element.imag) >= numar:
                l_filtrata.append(element)
    elif operatiune == ">":
        for element in l:
            if math.sqrt(element.real*element.real+element.imag*element.imag) <= numar:
                l_filtrata.append(element)
    elif operatiune == "=":
        for element in l:
            if math.sqrt(element.real*element.real+element.imag*element.imag) != numar:
                l_filtrata.append(element)
    return l_filtrata


def undo(lista, stack):
    """
            sterge ultima operatie facuta
    """
    if stack == []:
        raise Exception("Nu se poate efectua UNDO!")
    n = len(stack) - 1
    s = stack[n]
    stack.pop()
    if s[0] == "sterge_numar" :
        lista.remove(lista[len(lista)-1])
    elif s[0] == "sterge_numar_poz":
        sterge_element_de_pe_pozitie(lista, s[1])
    elif s[0] == "insereaza_numar":
        insereaza_nr_pe_pozitie(lista, s[2], s[1])
    elif s[0] == "sterge_interval":
        for i in range(s[2]-s[1]+1):
            undo(lista, stack)
