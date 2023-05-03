import networkx as nx
import numpy as np
from matplotlib import pyplot as plt

from ga import GA
from utils import modularity, nrComunitati, apartenentaComunitate, functie, citeste


def main():
    parametrii = citeste()
    parametrii['nrCromozomi'] = 3
    parametrii['nrIteratii'] = 5
    parametrii['functie'] = modularity
    ga = GA(parametrii)
    ga.initialisation()

    bestCromFitness = []
    nrCom = []
    functie(ga, parametrii, bestCromFitness, nrCom)

    bestCrom = ga.bestChromosome()
    print("Numarul de comunitati: ", nrComunitati(bestCrom))
    print('Apartenenta la comunitate a fiecarui nod: ')
    apartenentaComunitate(bestCrom)

    A = np.matrix(citeste()["mat"])
    G = nx.from_numpy_array(A)
    pos = nx.spring_layout(G)  # compute graph layout
    plt.figure(figsize=(8, 8))  # image is 8 x 8 inches
    nx.draw_networkx_nodes(G, pos, node_size=100, cmap=plt.cm.RdYlBu, node_color=bestCrom.repres)
    nx.draw_networkx_edges(G, pos, alpha=0.3)
    plt.show()


main()
