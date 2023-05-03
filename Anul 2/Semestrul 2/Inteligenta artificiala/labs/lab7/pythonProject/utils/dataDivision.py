import numpy as np


def divideUnivariable(gdp, happiness):
    np.random.seed(5)
    indexes = [i for i in range(len(gdp))]
    trainSample = np.random.choice(indexes, int(0.8 * len(gdp)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainGDP = [gdp[i] for i in trainSample]
    trainHappiness = [happiness[i] for i in trainSample]

    testGDP = [gdp[i] for i in testSample]
    testHappiness = [happiness[i] for i in testSample]
    return trainGDP, trainHappiness, testGDP, testHappiness


def divideMultivariable(gdp, freedom, happiness):
    np.random.seed(5)
    indexes = [i for i in range(len(gdp))]
    trainSample = np.random.choice(indexes, int(0.8 * len(gdp)), replace=False)
    validationSample = [i for i in indexes if not i in trainSample]

    trainInputs = [[gdp[i], freedom[i]] for i in trainSample]
    trainOutputs = [happiness[i] for i in trainSample]

    gdpTrainInputs = [[gdp[i]] for i in trainSample]
    freedomTrainInputs = [[freedom[i]] for i in trainSample]
    happinessTrainOutputs = [[happiness[i]] for i in trainSample]

    gdpTestOutputs = [[gdp[i]] for i in validationSample]
    freedomTestOutputs = [[freedom[i]] for i in validationSample]
    happinessTestOutputs = [[happiness[i]] for i in validationSample]

    validationInputs = [[gdp[i], freedom[i]] for i in validationSample]
    validationOutputs = [happiness[i] for i in validationSample]

    return trainInputs, trainOutputs, validationInputs, validationOutputs, gdpTrainInputs, freedomTrainInputs, happinessTrainOutputs, gdpTestOutputs, freedomTestOutputs, happinessTestOutputs