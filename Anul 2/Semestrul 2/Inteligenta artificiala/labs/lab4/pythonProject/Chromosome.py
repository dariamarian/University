import random
from random import randint


class Chromosome:
    def __init__(self, problParam=None):
        self.__problParam = problParam
        self.__fitness = 0.0
        self.__representation = []
        self.__init_representation()

    @property
    def representation(self):
        return self.__representation

    @property
    def fitness(self):
        return self.__fitness

    @representation.setter
    def representation(self, chromosome=None):
        if chromosome is None:
            chromosome = []
        self.__representation = chromosome

    @fitness.setter
    def fitness(self, fit=0.0):
        self.__fitness = fit

    def crossover(self, c):
        rand = randint(0, len(self.__representation) - 1)
        newrepres = [None] * len(self.__representation)
        for i in range(rand):
            newrepres[i] = self.__representation[i]
        pos = 0
        for i in range(rand, len(self.__representation)):
            while c.__representation[pos] in newrepres:
                pos += 1
            newrepres[i] = c.__representation[pos]
        offspring = Chromosome(c.__problParam)
        offspring.representation = newrepres
        return offspring

    def mutation(self):
        firstpos = randint(0, len(self.__representation) - 1)
        secondpos = randint(0, len(self.__representation) - 1)
        while self.__representation[firstpos] != self.__representation[secondpos]:
            secondpos = randint(0, len(self.__representation) - 1)
        first_value = self.__representation[firstpos]
        self.__representation[firstpos] = self.__representation[secondpos]
        self.__representation[secondpos] = first_value

    def __str__(self):
        return 'Chromosome: ' + str(self.__representation) + ' has fit: ' + str(self.__fitness) + '.'

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__representation == c.__representation and self.__fitness == c.__fitness

    def __init_representation(self):
        self.__representation = [i for i in range(1, self.__problParam['nrOrase'] + 1)]
        random.shuffle(self.__representation)
