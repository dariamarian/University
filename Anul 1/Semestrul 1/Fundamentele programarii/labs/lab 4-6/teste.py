from functii import *


def test_adauga():
    """
            Testeaza functia de adaugare.
    """
    assert adauga_nr_la_final([(5 + 3j), (2 + 4j), (8 + 3j)], (10 + 1j)) == [(5 + 3j), (2 + 4j), (8 + 3j), (10 + 1j)]
    assert adauga_nr_la_final([(1 + 1j), (4 + 3j), (11 + 2j)], (1 + 1j)) == [(1 + 1j), (4 + 3j), (11 + 2j), (1 + 1j)]
    assert adauga_nr_la_final([(11 + 9j)], (4 + 2j)) == [(11 + 9j), (4 + 2j)]


def test_insereaza():
    """
            Testeaza functia de inserare.
    """
    assert insereaza_nr_pe_pozitie([(5 + 3j), (2 + 4j), (8 + 3j)], (10 + 1j), 2) == [(5 + 3j), (2 + 4j), (10 + 1j), (8 + 3j)]
    assert insereaza_nr_pe_pozitie([(4 + 2j), (9 + 9j), (10 + 3j)], (2 + 11j), 0) == [(2 + 11j), (4 + 2j), (9 + 9j), (10 + 3j)]
    assert insereaza_nr_pe_pozitie([(3 + 0j), (7 + 8j), (13 + 1j)], (10 + 10j), 3) == [(3 + 0j), (7 + 8j), (13 + 1j), (10 + 10j)]


def test_sterge_poz():
    """
            Testeaza functia de stergere.
    """
    assert sterge_element_de_pe_pozitie([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)], 2) == [(5 + 3j), (2 + 4j), (9 + 7j)]
    assert sterge_element_de_pe_pozitie([(2 + 1j), (4 + 2j), (7 + 2j)], 0) == [(4 + 2j), (7 + 2j)]
    assert sterge_element_de_pe_pozitie([(4 + 8j), (9 + 7j), (3 + 11j), (7 + 2j)], 3) == [(4 + 8j), (9 + 7j), (3 + 11j)]


def test_sterge_interval():
    """
            Testeaza functia de stergere.
    """
    assert sterge_elemente_din_interval([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)], 1, 3) == [(5 + 3j)]
    assert sterge_elemente_din_interval([(4 + 8j), (9 + 7j), (3 + 11j), (7 + 2j), (9 + 3j), (2 + 1j)], 2, 3) == [(4 + 8j), (9 + 7j), (9 + 3j), (2 + 1j)]
    assert sterge_elemente_din_interval([(2 + 9j), (5 + 5j), (7 + 13j), (6 + 6j), (10 + 12j), (4 + 3j)], 1, 5) == [(2 + 9j)]


def test_inlocuieste():
    """
            Testeaza functia de inlocuire.
    """
    assert inlocuieste_aparitiile([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)], (2 + 4j), (8 + 2j)) == [(5 + 3j), (8 + 2j), (8 + 3j), (9 + 7j)]
    assert inlocuieste_aparitiile([(2 + 8j)], (2 + 8j), (8 + 2j)) == [(8 + 2j)]
    assert inlocuieste_aparitiile([(2 + 2j), (2 + 4j), (2 + 2j), (2 + 2j)], (2 + 2j), (9 + 7j)) == [(9 + 7j), (2 + 4j), (9 + 7j), (9 + 7j)]


def test_tipareste1():
    """
            Testeaza functia de tiparire a partilor imaginare.
    """
    assert tipareste_parte_imaginara([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)], 1, 3) == [4.0, 3.0, 7.0]
    assert tipareste_parte_imaginara([(2 + 2j), (2 - 14j), (2 + 2j), (2 - 2j)], 0, 2) == [2.0, -14.0, 2.0]
    assert tipareste_parte_imaginara([(-3+2j), (6+8j), (112+43j), (54-3j), (12+1j), (9+1j), (8+6j), (54-3j)], 1, 5) == [8.0, 43.0, -3.0, 1.0, 1.0]


def test_tipareste2():
    """
            Testeaza functia de tiparire a numerelor complexe care au modulul mai mic decat 10
    """
    assert tipareste_nr_cu_modul_mai_mic_decat_10([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)]) == [(5 + 3j), (2 + 4j), (8 + 3j)]
    assert tipareste_nr_cu_modul_mai_mic_decat_10([(2 + 2j), (2 - 14j), (2 + 2j), (2 - 2j)]) == [(2 + 2j), (2 + 2j), (2 - 2j)]
    assert tipareste_nr_cu_modul_mai_mic_decat_10([(-3 + 2j), (6 + 8j), (112 + 43j), (54 - 3j), (12 + 1j), (9 + 1j), (8 + 6j), (54 - 3j)]) == [(-3 + 2j), (9 + 1j)]


def test_tipareste3():
    """
            Testeaza functia de tiparire a numerelor complexe care au modulul egal cu 10
    """
    assert tipareste_nr_cu_modul_egal_cu_10([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)]) == "Nu exista astfel de numere."
    assert tipareste_nr_cu_modul_egal_cu_10([(2 + 2j), (8 - 6j), (2 + 2j), (6 + 8j)]) == [(8 - 6j),  (6 + 8j)]
    assert tipareste_nr_cu_modul_egal_cu_10([(-3 + 2j), (6 + 8j), (112 + 43j), (54 - 3j), (12 + 1j), (9 + 1j), (8 + 6j), (54 - 3j)]) == [(6 + 8j), (8 + 6j)]


def test_suma():
    """
            Testeaza functia care calculeaza suma numerelor.
    """
    assert suma_numerelor([(3 + 2j), (2 + 4j), (5 + 3j)], 0, 2) == 10 + 9j
    assert suma_numerelor([(8 + 4j), (1 + 3j)], 0, 1) == 9 + 7j
    assert suma_numerelor([(9 + 7j), (2 + 4j), (3 + 2j)], 1, 1) == 2 + 4j


def test_produs():
    """
            Testeaza functia care calculeaza produsul numerelor.
    """
    assert produsul_numerelor([(3 + 2j), (2 + 4j), (5 + 3j)], 0, 2) == -58 + 74j
    assert produsul_numerelor([(8 + 2j), (1 + 3j)], 0, 1) == 2 + 26j
    assert produsul_numerelor([(9 + 7j), (2 + 4j), (3 + 2j)], 1, 1) == 2 + 4j


def test_sortare():
    """
            Testeaza functia care sorteaza lista.
    """
    assert lista_sortata([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)]) == [(9 + 7j), (2 + 4j), (8 + 3j), (5 + 3j)]
    assert lista_sortata([(-3+2j), (6+8j), (112+43j), (54-3j), (12+1j), (9+1j), (8+6j), (54-3j)]) == [(112+43j), (6+8j), (8+6j), (-3+2j), (12+1j), (9+1j), (54-3j), (54-3j)]
    assert lista_sortata([(5 + 3j), (4 + 3j), (8 + 3j), (9 + 7j)]) == [(9 + 7j), (8 + 3j), (5 + 3j), (4 + 3j)]


def test_filtrare_prim():
    """
            Testeaza functia care filtreaza numerele cu partea reala numar prim.
    """
    assert filtrare_parte_reala_prim([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)]) == [(8 + 3j), (9 + 7j)]
    assert filtrare_parte_reala_prim([(2 + 8j), (2 + 8j), (8 +2j)]) == [(8 + 2j)]
    assert filtrare_parte_reala_prim([(1 + 2j), (2 + 5j), (4 + 9j), (7 + 8j), (8 + 7j)]) ==[(1 + 2j), (4 + 9j), (8 + 7j)]


def test_filtrare_modul():
    """
            Testeaza functia care filtreaza numerele in functie de modulul lor.
    """
    assert filtrare_modul([(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)], "<", 10) == [(9 + 7j)]
    assert filtrare_modul([(2 + 3j), (4 + 6j), (8 + 6j)], "=", 10) == [(2 + 3j), (4 + 6j)]
    assert filtrare_modul([(13 + 2j), (2 + 7j), (6 + 4j), (7 + 8j), (8 + 7j)], ">", 9) == [(2 + 7j), (6 + 4j)]


def test_undo():
    """
            Testeaza functia de undo
    """
    l = [(5 + 3j), (2 + 4j), (9 + 7j)]
    stack = [(["sterge_numar"]), (["insereaza_numar", 2, 8+3j])]
    undo(l, stack)
    assert (l == [(5 + 3j), (2 + 4j), (8 + 3j), (9 + 7j)])
    assert (stack == [(["sterge_numar"])])


def run_teste():
    test_adauga()
    test_insereaza()
    test_sterge_poz()
    test_inlocuieste()
    test_tipareste1()
    test_tipareste2()
    test_tipareste3()
    test_suma()
    test_produs()
    test_sortare()
    test_filtrare_prim()
    test_filtrare_modul()
    test_undo()