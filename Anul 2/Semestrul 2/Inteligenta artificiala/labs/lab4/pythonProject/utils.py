import sys
from math import sqrt

import networkx as nx

INF = sys.maxsize


def read(fileName):
    f = open(fileName, "r")
    n = int(f.readline())
    matrice = []
    for i in range(n):
        matrice.append([])
        line = f.readline()
        elements = line.split(" ")
        for j in range(n):
            matrice[-1].append(int(elements[j]))
    nrEdges = 0
    for i in range(n):
        for j in range(n):
            if matrice[i][j] != 0:
                if j > i:
                    nrEdges += 1
            else:
                matrice[i][j] = INF
    graph = {'nrOrase': n,
             'matrice': matrice,
             'nrEdges': nrEdges,
             'sursa': int(f.readline()),
             'destinatie': int(f.readline())}
    f.close()
    return graph


def readtsp(fileName):
    graph = nx.Graph()
    with open(fileName, "r") as inFile:
        for i in range(100000):
            node, longitude, latitude = inFile.readline().strip().split()
            graph.add_node(int(node) - 1, x=int(longitude), y=int(latitude))
    return graph


def calculateDistance(graph, path):
    matrice = graph['matrice']
    length = 0
    for index in range(len(path) - 1):
        length += matrice[path[index] - 1][path[index + 1] - 1]
    length += matrice[path[0] - 1][path[-1] - 1]
    return length


def calculateDistance2(graph, path):
    matrice = graph['matrice']
    length = 0
    for index in range(len(path) - 1):
        length += matrice[path[index] - 1][path[index + 1] - 1]
    return length


def euclidianDistance(node1, node2):
    return sqrt((node1['x'] - node2['x']) ** 2 + (node1['y'] - node2['y']) ** 2)


def fitnessFunction(graph, chromosome):
    weigthSum = 0
    size = len(chromosome)
    for count in range(size - 1):
        weigthSum += euclidianDistance(graph.nodes[chromosome[count]], graph.nodes[chromosome[count + 1]])
    return weigthSum + euclidianDistance(graph.nodes[chromosome[0]], graph.nodes[chromosome[size - 1]])
