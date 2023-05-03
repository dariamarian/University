import numpy as np
from sklearn.metrics import mean_squared_error


def univariate_regression(train_inputs, test_inputs, train_outputs, test_outputs, learning_rate, epochs):
    m = len(train_outputs)
    x = np.c_[np.ones((len(train_inputs), 1)), train_inputs]
    x_transpose = x.transpose()
    beta = np.array([0, 0])

    # iteratively update using gradient descent to minimize the mean squared error between the
    # predicted outputs and the actual outputs
    for _ in range(epochs):
        # hypothesis = beta_0 + beta_1 * x
        hypothesis = np.dot(x, beta)
        loss = hypothesis - train_outputs
        # gradient = (2/m) * X^T * (X * beta - y)
        gradient = np.dot(x_transpose, loss) / m
        beta = beta - learning_rate * gradient

    computed_outputs = [beta[0] + test_inputs[i] * beta[1] for i in range(len(test_inputs))]
    print('Outputs: ', test_outputs)
    print('Computed outputs: ', computed_outputs)

    error = mean_squared_error(test_outputs, computed_outputs)
    print('Error: ', error)


def multivariate_regression(train_inputs, test_inputs, train_outputs, test_outputs, learning_rate, epochs):
    # reshape from one dimensional to two dimensional
    happiness_new = np.reshape(train_outputs, (len(train_outputs), 1))
    # creates a new array with len(train_inputs) rows and 2 columns, where the first column contains a vector of ones
    # and the second column contains the train_inputs data
    gdp = np.c_[np.ones((len(train_inputs), 1)), train_inputs]
    # generate an initial value of vector θ from the original independent variables matrix
    theta = np.random.randn(len(gdp[0]), 1)
    # iteratively update using gradient descent
    for _ in range(epochs):
        # gradients = (2/m) * X^T * (X * theta - y)
        gradients = 2 / len(happiness_new) * np.dot(np.transpose(gdp), (np.dot(gdp, theta) - happiness_new))
        theta = theta - learning_rate * gradients

    computed_outputs = [theta[0][0] + test_inputs[i] * theta[1][0] + test_inputs[i] * theta[1][0] for i in range(len(test_inputs))]

    print('Outputs: ', test_outputs)
    print('Computed outputs: ', computed_outputs)

    error = mean_squared_error(test_outputs, computed_outputs)
    print('Error: ', error)