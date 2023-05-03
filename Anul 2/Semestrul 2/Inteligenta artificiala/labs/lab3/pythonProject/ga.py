from random import randint

from RealChromosome import Chromosome


class GA:
    def __init__(self,problParam=None):
        self.__problParam = problParam
        self.__population = []

    @property
    def population(self):
        return self.__population

    def initialisation(self):
        for _ in range(0, self.__problParam['nrCromozomi']):
            c = Chromosome(self.__problParam)
            self.__population.append(c)

    def evaluation(self):
        for c in self.__population:
            c.fitness = self.__problParam['functie'](c.repres, self.__problParam)

    def bestChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness < best.fitness):
                best = c
        return best

    def selection(self):
        pos1 = randint(0, self.__problParam['nrCromozomi'] - 1)
        pos2 = randint(0, self.__problParam['nrCromozomi'] - 1)
        if (self.__population[pos1].fitness < self.__population[pos2].fitness):
            return pos1
        else:
            return pos2

    def oneGenerationElitism(self):
        newPop = [self.bestChromosome()]
        for _ in range(self.__problParam['nrCromozomi'] - 1):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            newPop.append(off)
        self.__population = newPop
        self.evaluation()