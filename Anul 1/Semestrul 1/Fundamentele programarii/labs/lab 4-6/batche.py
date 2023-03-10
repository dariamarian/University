from functii import adauga_nr_la_final, sterge_element_de_pe_pozitie, filtrare_parte_reala_prim


def menu():
    """
        printez meniul
    """
    print("Alegeti una dintre optiunile: \n 'add', 'remove', 'filter', 'print', 'exit' .")


def init_lista():
    """
        adaug 5 valori in lista
    """
    return [(23+4j), (8-6j), (9+8j), (12-3j), (150+23j)]


def split_option(option):
    """
        imparte sirul
    """
    option = option.strip()
    words = option.split(sep=',')
    parametri_sep = []
    for i in words:
        parametri_sep += i.split(" ")
    return parametri_sep


def run_batch():
    lista = init_lista()
    menu()
    option = input("Ce doriti sa faceti? Introduceti un sir: ")
    split_option(option)
    lista_option = list(split_option(option))
    i = 0
    while True:
        while i < (len(lista_option)):
            if lista_option[i] == 'add':
                nr_nou = complex(lista_option[i + 1])
                lista = adauga_nr_la_final(lista, nr_nou)
                i += 2
            elif lista_option[i] == 'remove':
                poz = int(lista_option[i + 1])
                lista = sterge_element_de_pe_pozitie(lista, poz)
                i += 2
            elif lista_option[i] == 'filter':
                lista = filtrare_parte_reala_prim(lista)
                print(lista)
                i += 1
            elif lista_option[i] == 'print':
                print(lista)
                i += 1
            elif lista_option[i] == 'exit':
                return
