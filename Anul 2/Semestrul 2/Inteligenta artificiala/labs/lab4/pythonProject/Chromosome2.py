from random import randint


class Chromosome2:
    def __init__(self, problem_param=None):
        self.__problParam = problem_param
        self.__representation = []
        self.__fitness = 0.0
        self._init_representation()

    @property
    def representation(self):
        return self.__representation

    @property
    def fitness(self):
        return self.__fitness

    @representation.setter
    def representation(self, l=None):
        if l is None:
            l = []
        self.__representation = l

    @fitness.setter
    def fitness(self, fit=0.0):
        self.__fitness = fit

    def crossover(self, c):
        if len(self.__representation) == 2 or len(c.__representation) == 2:
            offspring = Chromosome2(c.__problParam)
            offspring.representation = c.__representation
            return offspring

        common = []
        for index in range(min(len(self.__representation), len(c.representation))):
            if self.__representation[index] == c.__representation[index]:
                common.append(index)

        randindex = randint(0, len(common) - 1)
        rand = common[randindex]

        newrepres = self.__representation[:rand + 1]
        for index in range(rand + 1, len(c.__representation)):
            if c.__representation[index] not in newrepres:
                newrepres.append(c.__representation[index])

        child = Chromosome2(self.__problParam)
        child.__representation = newrepres

        return child

    def mutation(self):
        if len(self.__representation) == 2:
            return

        randindex = randint(1, len(self.__representation) - 2)
        before = self.__representation[randindex - 1]
        after = self.__representation[randindex + 1]
        used = self.__representation[:randindex] + self.__representation[randindex + 2:]
        newrepres = [before]
        while newrepres[-1] != after:
            newnode = randint(1, self.__problParam['nrOrase'])
            while newnode in used:
                newnode = randint(1, self.__problParam['nrOrase'])
            newrepres.append(newnode)
            used.append(newnode)
        self.__representation = self.__representation[:randindex - 1] + newrepres + self.__representation[randindex + 2:]

    def __str__(self):
        return 'Chromosome: ' + str(self.__representation) + ' has fit: ' + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__representation == c.__representation and self.__fitness == c.__fitness

    def _init_representation(self):
        self.__representation.append(self.__problParam["sursa"])
        while self.__representation[-1] != self.__problParam["destinatie"]:
            newNode = randint(1, self.__problParam["nrOrase"])
            while newNode in self.__representation:
                newNode = randint(1, self.__problParam["nrOrase"])
            self.__representation.append(newNode)
