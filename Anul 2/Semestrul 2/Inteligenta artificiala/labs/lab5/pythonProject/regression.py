from math import sqrt
from utils import getSubLists, getRealValues, getPredictedValues


def compute_error(dict):
    mae = 0  # mean absolute error
    rmse = 0  # root mean squared error
    for index in range(3):
        mae += sum(abs(r - c) for r, c in zip(dict[index], dict[index + 3])) / len(dict[index])
        rmse += sqrt(sum((r - c) ** 2 for r, c in zip(dict[index], dict[index + 3])) / len(dict[index]))
    return mae, rmse


def regression(outputs):
    subList, subListP = getSubLists(outputs)
    realWeights, realWaist, realPulse = getRealValues(subList)
    predictedWeights, predictedWaist, predictedPulse = getPredictedValues(subListP)

    L1Weights = sum(abs(r - p) for r, p in zip(realWeights, predictedWeights)) / len(realWeights)
    L1Waists = sum(abs(r - p) for r, p in zip(realWaist, predictedWaist)) / len(realWaist)
    L1Pulses = sum(abs(r - p) for r, p in zip(realPulse, predictedPulse)) / len(realPulse)

    L2Weights = sqrt(sum((r - p) ** 2 for r, p in zip(realWeights, predictedWeights)) / len(realWeights))
    L2Waists = sqrt(sum((r - p) ** 2 for r, p in zip(realWaist, predictedWaist)) / len(realWaist))
    L2Pulses = sqrt(sum((r - p) ** 2 for r, p in zip(realPulse, predictedPulse)) / len(realPulse))

    return L1Weights, L1Waists, L1Pulses, L2Weights, L2Waists, L2Pulses
