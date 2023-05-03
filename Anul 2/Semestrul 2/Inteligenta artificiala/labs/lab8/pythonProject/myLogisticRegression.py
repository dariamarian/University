# de la profa
import random
from math import exp
import numpy as np


def sigmoid_derivative(x):
    return sigmoid(x) * (1 - sigmoid(x))


def sigmoid(x):
    return 1 / (1 + exp(-x))


def evaluate(xi, coef):
    yi = coef[0]
    for j in range(len(xi)):
        yi += coef[j + 1] * xi[j]
    return yi


class MyLogisticRegression:
    def __init__(self):
        self.intercept_ = []
        self.coef_ = []

    def fit(self, x, y, learningRate=0.001, noEpochs=1000):
        self.coef_ = []  # will store the coefficients of the binary logistic regression models.
        labels = np.unique(y)  # find the unique labels
        for label in range(len(labels)):
            self.coef_.append([random.random() for _ in range(len(x[0]) + 1)])  # initialised random
            appearances = []  # used to store whether a sample belongs to the current class or not
            for elem in y:
                if elem == label:
                    appearances.append(1)
                else:
                    appearances.append(0)
            for epoch in range(noEpochs):
                for i in range(len(x)):  # for each sample from the training data
                    ycomputed = sigmoid(evaluate(x[i], self.coef_[label]))   # computes the predicted output using the current coefficients and the sigmoid function
                    crtError = ycomputed - appearances[i]  # compute the error for the current sample
                    for j in range(0, len(x[0])):  # update the coefficients
                        # using the gradient descent algorithm to update the j-th coefficient,
                        # the amount of adjustment is controlled by the learning rate
                        sigmoid_derivative_output = sigmoid_derivative(evaluate(x[i], self.coef_[label]))
                        self.coef_[label][j + 1] = self.coef_[label][j + 1] - learningRate * crtError * x[i][j] * sigmoid_derivative_output
                    self.coef_[label][0] = self.coef_[label][0] - learningRate * crtError * 1 * sigmoid_derivative_output
        self.intercept_ = [coef[0] for coef in self.coef_]
        self.coef_ = [coef[1:] for coef in self.coef_]

    def predictOneSample(self, sampleFeatures):
        labels = []
        for _ in range(len(self.intercept_)):
            coefficients = [self.intercept_[_]] + [c for c in self.coef_[_]]
            computedFloatValue = evaluate(sampleFeatures, coefficients)
            computed01Value = sigmoid(computedFloatValue)
            labels.append(computed01Value)
        computedLabels = np.argmax(labels)
        return computedLabels

    def predict(self, inTest):
        computedLabels = [self.predictOneSample(sample) for sample in inTest]
        return computedLabels