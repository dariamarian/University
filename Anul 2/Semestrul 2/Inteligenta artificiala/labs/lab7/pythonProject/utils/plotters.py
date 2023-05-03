from matplotlib import pyplot as plt


def plotUnivariable(trainGDP, trainHappiness, testGDP, testHappiness):
    plt.plot(trainGDP, trainHappiness, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(testGDP, testHappiness, 'g^', label='testing data')  # test data are plotted by green and a triangle sign
    plt.title('train and test data')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()


def plotOutputsUnivariable(testGDP, testHappiness, computedOutputs):
    plt.plot(testGDP, computedOutputs, 'yo',
             label='computed test data')  # computed test data are plotted yellow red and circle sign
    plt.plot(testGDP, testHappiness, 'g^', label='real test data')  # real test data are plotted by green triangles
    plt.title('computed test and real test data')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()


def plotMultivariable(gdp, freedom, happiness):
    ax = plt.axes(projection='3d')
    ax.plot3D(gdp, freedom, happiness, 'ro')
    ax.set_xlabel('GDP')
    ax.set_ylabel('Freedom')
    ax.set_zlabel('Happiness')
    plt.show()