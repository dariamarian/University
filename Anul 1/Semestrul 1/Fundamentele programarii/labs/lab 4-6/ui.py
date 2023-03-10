import math
from functii import *
from teste import run_teste


def citire_lista(l):
    """
            citeste numarul de elemente si lista de numere complexe
    """
    print("Introduceti numarul de elemente din lista: ")
    nr = int(input())
    print("Introduceti cele", nr, "elemente: ")
    try:
        for i in range(nr):
            n = complex(input())
            l.append(n)
            # l[len(l)] = complex(n)
        print("Lista introdusa este: ", l)
    except ValueError:
        print("Numar invalid")


def adauga_nr_la_final_UI(l):
    """
            adauga un numar complex la finalul listei
    """
    try:
        nr = complex(input("Introduceti numarul complex pe care doriti sa il adaugati:"))
        l = adauga_nr_la_final(l, nr)
        return l
    except ValueError:
        print("Numar invalid")


def insereaza_nr_pe_pozitie_UI(l):
    """
            adauga un numar complex pe pozitia data de utilizator
    """
    try:
        nr = complex(input("Introduceti numarul complex pe care doriti sa il adaugati:"))
        poz = int(input("Introduceti pozitia pe care doriti sa inserati numarul: "))
        l = insereaza_nr_pe_pozitie(l, nr, poz)
        return l
    except ValueError:
        print("Numar invalid")


def sterge_element_de_pe_pozitie_UI(l):
    """
            sterge un numar complex de pe pozitia data de utilizator
    """
    try:
        poz = int(input("Introduceti pozitia de pe care doriti sa se stearga numarul: "))
        l = sterge_element_de_pe_pozitie(l, poz)
        return l
    except IndexError:
        print("Pozitie inexistenta")


def sterge_elemente_din_interval_UI(l):
    """
            sterge numerele complexe din intervalul dat de utilizator
    """
    try:
        st = int(input("Introduceti pozitia de inceput a intervalului: "))
        dr = int(input("Introduceti pozitia de sfarsit a intervalului: "))
        l = sterge_elemente_din_interval(l, st, dr)
        return l
    except ValueError:
        print("Numar invalid")


def inlocuieste_aparitiile_UI(l):
    """
            inlocuieste aparitiile unui numar complex dat de utilizator cu un alt numar complex introdus de acesta
    """
    try:
        x = complex(input("Introduceti numarul complex pe care doriti sa il inlocuiti:"))
        y = complex(input("Introduceti numarul complex cu care doriti sa il inlocuiti:"))
        l = inlocuieste_aparitiile(l, x, y)
        return l
    except ValueError:
        print("Numar invalid")


def suma_numerelor_UI(l):
    """
        calculeaza suma numerelor dintr-o subsecvent?? dat??
    """
    try:
        st = int(input("Introduceti pozitia de inceput a intervalului: "))
        dr = int(input("Introduceti pozitia de sfarsit a intervalului: "))
        s = suma_numerelor(l, st, dr)
        return s
    except ValueError:
        print("Numar invalid")


def produsul_numerelor_UI(l):
    """
        calculeaza produsul numerelor dintr-o subsecvent?? dat??
    """
    try:
        st = int(input("Introduceti pozitia de inceput a intervalului: "))
        dr = int(input("Introduceti pozitia de sfarsit a intervalului: "))
        p = produsul_numerelor(l, st, dr)
        return p
    except ValueError:
        print("Numar invalid")


def lista_sortata_UI(l):
    """
        sorteaza lista descresc??tor dup?? partea imaginara
    """
    l = lista_sortata(l)
    return l


def filtrare_parte_reala_prim_UI(l):
    """
            elimina din lista numerele complexe la care partea reala este prim
    """
    l_filtrata = filtrare_parte_reala_prim(l)
    return l_filtrata


def filtrare_modul_UI(l):
    """
            elimina din lista numerele complexe la care modulul este <,= sau > dec??t un num??r dat.
    """
    try:
        operatiune = input("Introduceti optiunea dorita < > = :")
        numar = int(input("Introduceti numarul:"))
        l_filtrata = filtrare_modul(l, operatiune, numar)
        return l_filtrata
    except ValueError:
        print("Numar invalid.")


def tipareste_partea_imaginara_UI(l):
    """
           tipareste partea imaginara a numerelor complexe din intervalul dat de utilizator
    """
    l_tiparita = []
    try:
        st = int(input("Introduceti pozitia de inceput a intervalului: "))
        dr = int(input("Introduceti pozitia de sfarsit a intervalului: "))
        l_tiparita = tipareste_parte_imaginara(l, st, dr)
        return l_tiparita
    except ValueError:
        print("Numar invalid")


def tipareste_nr_cu_modul_mai_mic_decat_10_UI(l):
    """
            tipareste numerele complexe care au modulul mai mic decat 10
    """
    l_tiparita = []
    l_tiparita = tipareste_nr_cu_modul_mai_mic_decat_10(l)
    return l_tiparita


def tipareste_nr_cu_modul_egal_cu_10_UI(l):
    """
                tipareste numerele complexe care au modulul egal cu 10
    """
    l_tiparita = []
    l_tiparita = tipareste_nr_cu_modul_egal_cu_10(l)
    return l_tiparita

def afiseaza_meniu():
    """
        afiseaza meniul cu optiuni
    """
    print("Program cu numere complexe")
    print("1. Citirea unei liste de numere complexe.")
    print("2. Adaug?? num??r complex la sf??r??itul listei.")
    print("3. Inserare num??r complex pe o pozi??ie dat??.")
    print("4. ??terge element de pe o pozi??ie dat??.")
    print("5. ??terge elementele de pe un interval de pozi??ii.")
    print("6. ??nlocuie??te toate apari??iile unui num??r complex cu un alt num??r complex.")
    print("7. Tip??re??te partea imaginara pentru numerele din list??. Se d?? intervalul de pozi??ii.")
    print("8. Tip??re??te toate numerele complexe care au modulul mai mic dec??t 10.")
    print("9. Tip??re??te toate numerele complexe care au modulul egal cu 10.")
    print("10. Calculeaza suma numerelor dintr-o subsecventa data.")
    print("11. Calculeaza produsul numerelor dintr-o subsecventa data.")
    print("12. Tip??re??te lista sortat?? descresc??tor dup?? partea imaginara.")
    print("13. Filtrare parte reala prim ??? Elimin?? din list?? numerele complexe la care partea reala este prim. ")
    print("14. Filtrare modul ??? elimina din lista numerele complexe la care modulul este <,= sau > dec??t un numar dat. ")
    print("15. UNDO")
    print("16. Iesire din aplicatie.")


def run():
    stack = []
    l = []
    # l = {}
    run_teste()
    while True:
        afiseaza_meniu()
        cmd = input("Introduceti optiunea dorita: ")
        if cmd == "16":
            return
        elif cmd == "1":
            citire_lista(l)
        elif cmd == "2":
            try:
                stack.append(["sterge_numar"])
                nr_nou = complex(input("Introduceti numarul complex pe care doriti sa il adaugati:"))
                adauga_nr_la_final(l, nr_nou)
                print(l)
            except Exception as ex:
                print(ex)
        elif cmd == "3":
            try:
                nr_nou = complex(input("Introduceti numarul complex pe care doriti sa il adaugati:"))
                poz = int(input("Introduceti pozitia pe care doriti sa inserati numarul: "))
                stack.append(["sterge_numar_poz", poz])
                insereaza_nr_pe_pozitie(l, nr_nou, poz)
                print(l)
            except Exception as ex:
                print(ex)
        elif cmd == "4":
            try:
                poz = int(input("Introduceti pozitia de pe care doriti sa se stearga numarul: "))
                nr_sters = l[poz]
                stack.append(["insereaza_numar", poz, nr_sters])
                sterge_element_de_pe_pozitie(l, poz)
                print(l)
            except Exception as ex:
                print(ex)
        elif cmd == "5":
            try:
                st = int(input("Introduceti pozitia de inceput a intervalului: "))
                dr = int(input("Introduceti pozitia de sfarsit a intervalului: "))
                sterge_elemente_din_interval(l, st, dr, stack)
                stack.append(["sterge_interval", st, dr])
                print(l)
            except Exception as ex:
                print(ex)
        elif cmd == "6":
            print("Lista actualizata este: ", inlocuieste_aparitiile_UI(l))
        elif cmd == "7":
            print("Partile imaginare ale numerelor cautate sunt: ", tipareste_partea_imaginara_UI(l))
        elif cmd == "8":
            print("Numerele care au modulul mai mic decat 10 sunt: ", tipareste_nr_cu_modul_mai_mic_decat_10_UI(l))
        elif cmd == "9":
            print("Numerele care au modulul egal cu 10 sunt: ", tipareste_nr_cu_modul_egal_cu_10_UI(l))
        elif cmd == "10":
            print("Suma numerelor este: ", suma_numerelor_UI(l))
        elif cmd == "11":
            print("Produsul numerelor este: ", produsul_numerelor_UI(l))
        elif cmd == "12":
            print("Lista sortata este: ", lista_sortata_UI(l))
        elif cmd == "13":
            print("Lista filtrata este: ", filtrare_parte_reala_prim_UI(l))
        elif cmd == "14":
            print("Lista filtrata este: ", filtrare_modul_UI(l))
        elif cmd == "15":
            print("Lista anterioara ultimei operatii este: ")
            undo(l, stack)
            print(l, '\n')
        else:
            print("Comanda invalida")