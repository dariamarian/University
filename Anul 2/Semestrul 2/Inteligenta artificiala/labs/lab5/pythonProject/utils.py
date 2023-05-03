import csv

import numpy as np


def readFile(fileName):
    with open(fileName, 'r') as f:
        words = f.readline().strip().split(',')
        dic = {}
        for index in range(len(words)):
            dic[index] = []
        for line in f:
            values = line.strip().split(',')
            for index in range(len(values)):
                if fileName == 'data/sport.txt':
                    dic[index].append(int(values[index]))
                elif fileName == 'data/flowers.csv':
                    dic[index].append(values[index])

    return dic


def readFromCSV(fileName):
    with open(fileName, 'r') as f:
        csvreader = csv.reader(f)
        dt = []
        for row in csvreader:
            dt += [row]
        return dt


def getRealValues(subList):
    realWeights = [r[0] for r in subList]
    realWaist = [r[1] for r in subList]
    realPulse = [r[2] for r in subList]

    return realWeights, realWaist, realPulse


def getPredictedValues(subListP):
    predictedWeights = [p[0] for p in subListP]
    predictedWaist = [p[1] for p in subListP]
    predictedPulse = [p[2] for p in subListP]

    return predictedWeights, predictedWaist, predictedPulse


def getSubLists(outputs):
    real = []
    predicted = []
    for item in outputs:
        for i in range(3):
            real += [int(item[i])]
        for j in range(3, len(item)):
            predicted += [int(item[j])]

    subList = [real[n:n + 3] for n in range(0, len(real), 3)]
    subListP = [predicted[n:n + 3] for n in range(0, len(predicted), 3)]
    return subList, subListP


def readTxt(fileName):
    with open(fileName, 'r') as f:
        lines = f.readlines()
        real = []
        predicted = []
        for line in lines:
            real += [int(line.strip()[0])]
            predicted += [int(line.strip()[2])]
        return real, predicted


def readFile2(fileName):
    with open(fileName) as f:
        real = []
        predicted = []
        for line in f.readlines():
            real += [float(line.strip().split()[0])]
            predicted += [float(line.strip().split()[1])]
        return real, predicted


def readMultiClass(fileName):
    with open(fileName) as f:
        realProbs = []
        for line in f.readlines():
            probs = list(map(float, line.strip().split()))
            realProbs += probs
        subList1 = [realProbs[n:n + 3] for n in range(0, len(realProbs), 3)]
        return np.array(subList1, dtype=float)


def readMultiTarget(fileName):
    with open(fileName) as f:
        realProbs = []
        for line in f.readlines():
            probs = list(map(float, line.strip().split()))
            realProbs += probs
        subList1 = [realProbs[n:n + 5] for n in range(0, len(realProbs), 5)]
        return np.array(subList1, dtype=float)
