import os
from utils import reader
from regression import normalisation, toolRegression, myCode
import numpy as np


def main():
    crt_dir = os.getcwd()
    file_path = os.path.join(crt_dir, 'data', 'v1_world-happiness-report-2017.csv')

    gdp_per_capita, freedom, outputs = reader.load_data(file_path, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')
    # print('GRP per Capita: ', gdp_per_capita[:5])
    # print('Freedom: ', freedom[:5])
    # print('Outputs: ', outputs[:5])

    indexes = [i for i in range(len(gdp_per_capita))]
    train_sample = np.random.choice(indexes, int(0.8 * len(gdp_per_capita)), replace=False)
    test_sample = [i for i in indexes if not i in train_sample]

    first_train_inputs = [gdp_per_capita[i] for i in train_sample]
    second_train_inputs = [freedom[i] for i in train_sample]
    train_outputs = [outputs[i] for i in train_sample]

    first_test_inputs = [gdp_per_capita[i] for i in test_sample]
    second_test_inputs = [freedom[i] for i in test_sample]
    test_outputs = [outputs[i] for i in test_sample]

    normalisation.normalize(first_train_inputs, second_train_inputs, first_test_inputs, second_test_inputs, train_outputs, test_outputs)

    print('Using tools:\nUnivariate:')
    toolRegression.univariate_regression_tool(first_train_inputs, train_outputs, first_test_inputs, test_outputs)
    print('Multivariate:')
    toolRegression.multivariate_regression_tool(first_train_inputs, second_train_inputs, first_test_inputs, second_test_inputs, train_outputs, test_outputs)
    print('\nUsing developed code:\nUnivariate:')
    myCode.univariate_regression(first_train_inputs, first_test_inputs, train_outputs, test_outputs, learning_rate=0.01, epochs=200)
    print('Multivariate:')
    myCode.multivariate_regression(first_train_inputs, first_test_inputs, train_outputs, test_outputs, learning_rate=0.01, epochs=200)


main()