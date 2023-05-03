from classification import evalClassification, lossClassification, loss2, multiClassLoss, multiTargetLoss
from regression import compute_error, regression
from utils import readFile, readFromCSV, readFile2, readTxt, readMultiClass, readMultiTarget


def main():
    fileName = 'data/flowers.csv'
    dic = readFile(fileName)
    print('\nClassification:')
    accuracy, precision, recall = evalClassification(dic[0], dic[1], ['Daisy', 'Rose', 'Tulip'])
    print('Accuracy: ', accuracy, '\nPrecision: ', precision, '\nRecall: ', recall)

    fileName = 'data/sport.txt'
    dic = readFile(fileName)
    print('\nRegression:')
    mae, rmse = compute_error(dic)
    print('MAE: ', mae, '\nRMSE: ', rmse)

    sports = readFromCSV('data/sport.txt')
    sports.pop(0)
    L1Weights, L1Waists, L1Pulses, L2Weights, L2Waists, L2Pulses = regression(sports)
    print('\nL1_weights: ', L1Weights)
    print('L1_waists: ', L1Waists)
    print('L1_pulses: ', L1Pulses)

    print('L2_weights: ', L2Weights)
    print('L2_waists: ', L2Waists)
    print('L2_pulses: ', L2Pulses)

    probBinary = 'data/probabilities-binary.txt'
    trueBinary = 'data/true-binary.txt'
    r1, p1 = readFile2(probBinary)
    real, predicted = readTxt(trueBinary)
    loss = lossClassification(real, predicted)
    loss1 = loss2(real, predicted, r1, p1)
    print('Loss for the binary classification is', loss, loss1)

    probMultiClass = 'data/probabilities-multi-class.txt'
    trueMultiClass = 'data/true-multi-class.txt'
    npx = readMultiClass(probMultiClass)
    npx1 = readMultiClass(trueMultiClass)
    multiClassLoss2 = multiClassLoss(npx, npx1)
    print('Multi class is', multiClassLoss2)

    probMultiTarget = 'data/probabilities-multi-target.txt'
    trueMultiTarget = 'data/true-multi-target.txt'
    npx2 = readMultiTarget(probMultiTarget)
    npx3 = readMultiTarget(trueMultiTarget)
    multiTargetLoss2 = multiTargetLoss(npx2, npx3)
    print('Multi target loss is', multiTargetLoss2)


main()
