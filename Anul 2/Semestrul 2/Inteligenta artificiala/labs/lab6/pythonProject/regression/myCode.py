class MyLinearBivariateRegression:
    def __init__(self):
        self.w0 = 0.0
        self.w1 = 0.0
        self.w2 = 0.0

    def fit(self, x1, x2, y):
        n = len(x1)

        sx1 = sum(x1)
        x1i = [x1[i] - sx1 / n for i in range(n)]  # gdp

        sx2 = sum(x2)
        x2i = [x2[i] - sx2 / n for i in range(n)]  # freedom

        sy = sum(y)
        yi = [y[i] - sy / n for i in range(n)]  # happiness=output

        sx1x1 = sum([i * i for i in x1i])  # gdp*gdp
        sx1x2 = sum([i * j for i, j in zip(x1i, x2i)])  # gdp*freedom
        sx1y = sum([i * j for i, j in zip(x1i, yi)])  # gdp*happiness
        sx2x2 = sum([i * i for i in x2i])  # freedom*freedom
        sx2y = sum([i * j for i, j in zip(x2i, yi)])  # freedom*happiness

        self.w1 = (sx1y * sx2x2 - sx2y * sx1x2) / (sx1x1 * sx2x2 - sx1x2 ** 2)
        self.w2 = (sx2y * sx1x1 - sx1y * sx1x2) / (sx1x1 * sx2x2 - sx1x2 ** 2)
        self.w0 = sy / n - self.w1 * sx1 / n - self.w2 * sx2 / n

    def predict(self, X):
        return [self.w0 + self.w1 * x1 + self.w2 * x2 for x1, x2 in X]


def bivariateRegression(gdp, freedom, gdpTrain, freedomTrain, trainOutput):
    regressor = MyLinearBivariateRegression()
    regressor.fit(gdpTrain, freedomTrain, trainOutput)
    w0, w1, w2 = regressor.w0, regressor.w1, regressor.w2

    model = 'the learnt model: f(x) = ' + str(w0) + ' + ' + str(w1) + ' * x1' + ' + ' + str(w2) + ' * x2'
    return model
