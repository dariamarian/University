import os

import networkx as nx
from random import uniform

import numpy as np


def generateNewValue(lim1, lim2):
    return uniform(lim1, lim2)


def binToInt(x):
    val = 0
    for bit in x:
        val = val * 2 + bit
    return val


def citeste():
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', 'dolphins', 'dolphins.gml')
    filePath = os.path.join(crtDir, 'data', 'football', 'football.gml')
    filePath = os.path.join(crtDir, 'data', 'karate', 'karate.gml')
    filePath = os.path.join(crtDir, 'data', 'krebs', 'krebs.gml')
    filePath = os.path.join(crtDir, 'data', 'lesmis', 'lesmis.gml')
    filePath = os.path.join(crtDir, 'data', 'netscience.gml')
    filePath = os.path.join(crtDir, 'data', 'adjnoun', 'adjnoun.gml')
    # filePath = os.path.join(crtDir, 'data', 'com-dblp.gml')
    g = nx.read_gml(filePath, label='id')
    dict = {}
    i = 0
    for nod in g.nodes:
        dict[nod] = i
        i += 1
    mat = [[0 for _ in range(len(dict))] for _ in range(len(dict))]

    for pereche in g.edges:
        mat[dict[pereche[0]]][dict[pereche[1]]] = 1
        mat[dict[pereche[1]]][dict[pereche[0]]] = 1

    grade = []
    for el in g.degree:
        grade.append(el[1])

    retea = {}
    retea['nrNoduri'] = len(g.nodes)
    retea['mat'] = mat
    retea['nrMuchii'] = len(g.edges)
    retea['grade'] = grade

    return retea


def modularity(communities, param):
    nrNoduri = param['nrNoduri']
    mat = param['mat']
    grade = param['grade']
    nrMuchii = param['nrMuchii']
    M = 2 * nrMuchii
    Q = 0.0
    for i in range(0, nrNoduri):
        for j in range(0, nrNoduri):
            if communities[i] == communities[j]:
                Q += (mat[i][j] - grade[i] * grade[j] / M)
    return Q * 1 / M


def nrComunitati(bestCrom):
    nrCom = []
    for i in bestCrom.repres:
        if i not in nrCom:
            nrCom.append(i)
    return len(nrCom)


def functie(ga, parametrii, bestCromFitness, nrCom):
    for i in range(parametrii['nrIteratii']):
        ga.oneGenerationElitism()
        bestCrom = ga.bestChromosome()
        bestCromFitness.append(bestCrom.fitness)
        nrCom.append(nrComunitati(bestCrom))


def apartenentaComunitate(bestCrom):
    for i in range(0, len(bestCrom.repres)):
        print(i, " -> ", bestCrom.repres[i])
