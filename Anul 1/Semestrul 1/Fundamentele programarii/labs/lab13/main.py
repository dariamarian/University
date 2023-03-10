"""
    Pentru lista de monede cu valorile a1,....,an, si o valoare S. Tipariti toate modalitatile de a
    plati suma S cu monedele disponibile. Tipariti un mesaj daca suma nu se poate plati.

    spatiu de cautare = vectorul lista care contine valorile monedelor
    solutia = vectorul lista_rez
    solutie candidat = x=(x0, x1, ..., xk), unde xi este o moneda care adunata cu celelalte dau suma S
    conditie consistent = x=(x0, x1, ..., xk) e consistent daca suma_curenta este mai mica decat S
    conditie solutie = x=(x0, x1, ..., xk) e solutie daca e consistent si suma_curenta este egala cu S
"""
from ui.ui import main


def run():
    main()


run()
