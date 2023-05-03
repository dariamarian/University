import numpy as np
from numpy import log


def evalClassification(realLabels, computedLabels, labelNames):
    accuracy = sum([1 if realLabels[i] == computedLabels[i] else 0 for i in range(len(realLabels))]) / len(realLabels)

    precision = []
    recall = []

    for label in labelNames:
        TP = 0
        FP = 0
        FN = 0
        for real, computed in zip(realLabels, computedLabels):
            if real == label:
                if computed == label:
                    TP += 1
                else:
                    FN += 1
            else:
                if computed == label:
                    FP += 1
        precision.append(TP / (TP + FP))
        recall.append(TP / (TP + FN))

    return accuracy, precision, recall


def lossClassification(real, predicted):
    entropy = 0
    epsilon = np.finfo(np.float32).eps  # ca sa nu ia log de 0
    for rl, pr in zip(real, predicted):
        entropy += -(int(rl) * log(int(pr) + epsilon) + (1 - int(rl)) * log(1 - int(pr) + epsilon))
    return entropy / len(real)


def multiClassLoss(probArr, predArr):
    epsilon = np.finfo(np.float32).eps
    return -np.mean(np.sum(probArr * np.log(predArr + epsilon), axis=1))


def multiTargetLoss(probArr, predArr):
    epsilon = np.finfo(np.float32).eps
    return -np.mean(np.sum(probArr * np.log(predArr + epsilon) + (1 - probArr) * np.log(1 - predArr + epsilon), axis=1))


def loss2(real, predicted, realProb, predictedProb):
    entropy = 0
    for rProb, pProb, in zip(realProb, predictedProb):
        for rl, pd in zip(real, predicted):
            entropy += -(rl * log(pProb) + (1 - rl) * log(1 - pProb))
    return entropy / len(real)
