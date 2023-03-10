def afiseaza_meniu():
    """
        afiseaza meniul cu optiuni
    """
    print("Aplicatie secvente de numere")
    print("1. Citirea unei liste de numere intregi.")
    print("2. Gasirea secventei de lungime maxima care respecta proprietatea 8.")
    print("3. Gasirea secventei de lungime maxima care respecta proprietatea 9.")
    print("4. Gasirea secventei de lungime maxima care respecta proprietatea 12.")
    print("5. Iesire din aplicatie.")


def citire_lista(l):
    """
        citeste numarul de elemente si lista
    """
    print("Introduceti numarul de elemente din lista: ")
    nr = int(input())
    print("Introduceti cele", nr, "elemente: ")
    for i in range(nr):
        n = int(input())
        l.append(n)


def test_s1():
    """
        testeaza proprietatea 8
    """
    assert secventa_1([1, 2, 11, 4, 7, 9]) == [4, 7, 9]
    assert secventa_1([0, 4, 8, 2, 14]) == [0, 4, 8, 2]
    assert secventa_1([0]) == [0]
    assert secventa_1([41]) == "nu exista"
    assert secventa_1([19, 31, 820, 10, 114, 3498]) == [10]


def secventa_1(l):
    """
        gaseste secventa corespunzatoare proprietatii 8-au toate elementele in intervalul [0, 10] dat
    """
    j = 0
    k = []
    lmax = lc = 0
    for i in range(len(l)):
        if 0 <= l[i] <= 10:
            lc = lc + 1
        else:
            lc = 0
        if lc > lmax:
            lmax = lc
            j = i
    if lmax == 0:
        return "nu exista"
    else:
        for i in range(j - lmax + 1, j + 1):
            k.append(l[i])
        return k


def test_s2():
    """
        testeaza proprietatea 9
    """
    assert secventa_2([2, 3, 2, 5, 8, 3]) == [2, 3, 2]
    assert secventa_2([1, 2, 2, 4, 2]) == [1, 2, 2, 4, 2]
    assert secventa_2([5]) == "nu exista"
    assert secventa_2([3, 12]) == "nu exista"
    assert secventa_2([3, 20, 12, 7, 3, 12]) == "nu exista"


def secventa_2(l):
    """
            gaseste secventa corespunzatoare proprietatii 9-in oricare trei elemente consecutive exista o valoare care se repeta.
    """
    if len(l) < 3:
        return "nu exista"
    j = 0
    k = []
    lc = 2
    lmax = 1
    a = l[0]
    b = l[1]
    for i in range(2, len(l)):
        c = l[i]
        if a == b or b == c or a == c:
            lc = lc + 1
        else:
            lc = 1
        if lc > lmax:
            lmax = lc
            j = i
        a = b
        b = c
    if lmax == 1:
        return "nu exista"
    else:
        for i in range(j - lmax + 1, j + 1):
            k.append(l[i])
        return k


def test_s3():
    """
        testeaza proprietatea 12
    """
    assert secventa_3([2, -3, -2, 5, -8, 3]) == [-2, 5, -8, 3]
    assert secventa_3([1, -2, 2, -4, 2]) == [1, -2, 2, -4, 2]
    assert secventa_3([5]) == "nu exista"
    assert secventa_3([3, -12]) == [3, -12]
    assert secventa_3([20, -12, -7]) == [20, -12]
    assert secventa_3([-100, -15, -2]) == "nu exista"


def secventa_3(l):
    """
            gaseste secventa corespunzatoare proprietatii 12-are oricare doua elemente consecutive sunt de semne contrare.
    """
    if len(l) < 2:
        return "nu exista"
    j = 0
    k = []
    lc = 1
    lmax = 1
    a = l[0]
    for i in range(1, len(l)):
        b = l[i]
        if (a < 0 and b > 0) or (a > 0 and b < 0):
            lc = lc + 1
        else:
            lc = 1
        if lc > lmax:
            lmax = lc
            j = i
        a = b
    if lmax == 1:
        return "nu exista"
    else:
        for i in range(j - lmax + 1, j + 1):
            k.append(l[i])
        return k


def main():
    l = []
    test_s1()
    test_s2()
    test_s3()
    while True:
        afiseaza_meniu()
        cmd = input("Introduceti optiunea dorita: ")
        if cmd == "5":
            return
        elif cmd == "1":
           citire_lista(l)
        elif cmd == "2":
            print(secventa_1(l))
        elif cmd == "3":
            print(secventa_2(l))
        elif cmd == "4":
            print(secventa_3(l))
        else:
            print("Comanda invalida")


main()
