import os

from sklearn.metrics import mean_squared_error

from regression.myCode import bivariateRegression
from regression.toolRegression import toolRegression
from utils.data_division import divideData
from utils.reader import loadAsDF, loadData

if __name__ == '__main__':
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', 'v3_world-happiness-report-2017.csv')

    # gdp, freedom, happiness = loadData(filePath, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')
    gdp, freedom, happiness = loadAsDF(filePath)

    trainGdp, trainFreedom, trainInput, trainOutput, validationGdp, validationFreedom, validationInput, validationOutput = \
        divideData(gdp, freedom, happiness)
    model = bivariateRegression(gdp, freedom, trainGdp, trainFreedom, trainOutput)
    print('regression with my code:\n', model)

    toolModel, regressor = toolRegression(trainInput, trainOutput)
    computedValidationOutputs = regressor.predict([x for x in validationInput])
    print('regression with tool:\n', toolModel)

    error = 0.0
    for t1, t2 in zip(computedValidationOutputs, validationOutput):
        error += (t1 - t2) ** 2
    error = error / len(validationOutput)
    print("\nprediction error (manual): ", error)

    error = mean_squared_error(validationOutput, computedValidationOutputs)
    print("prediction error (tool): ", error)
