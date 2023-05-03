from Chromosome2 import Chromosome2
from Chromosome import Chromosome
from ga import GA
from utils import read, readtsp, calculateDistance, calculateDistance2, fitnessFunction
import random


def mainfunction(graph, populationSize=50, noGenerations=100, evaluationFunction=calculateDistance,
                 chromosome=Chromosome):
    genetic_parameters = {
        "popSize": populationSize,
        "nrGen": noGenerations,
        "function": evaluationFunction,
        "chromosome": chromosome
    }
    problem_parameters = graph
    problem_parameters["function"] = evaluationFunction
    ga = GA(genetic_parameters, problem_parameters)
    ga.initialisation()
    ga.evaluation()
    bests = []
    last = []

    for generation in range(genetic_parameters["nrGen"]):
        ga.oneGenerationElitism()
        best_chromosome = ga.bestChromosome()
        last = ga.population
        bests.append(best_chromosome)

    the_best = bests[0]
    for best in bests:
        if best.fitness > the_best.fitness:
            the_best = best

    the_bests = [the_best]

    # pt a afisa mai multe solutii
    for candidate in last:
        if candidate.fitness == the_best.fitness and candidate not in the_bests:
            the_bests.append(candidate)

    return the_bests


def mainfunction2(graph, populationSize=100, noGenerations=100, evaluationFunction=calculateDistance2, chromosome=Chromosome2):
    genetic_parameters = {
        "popSize": populationSize,
        "nrGen": noGenerations,
        "function": evaluationFunction,
        "chromosome": chromosome
    }
    problem_parameters = graph
    problem_parameters["function"] = evaluationFunction
    ga = GA(genetic_parameters, problem_parameters)
    ga.initialisation()
    ga.evaluation()
    bests = []
    last = []

    for generation in range(genetic_parameters["nrGen"]):
        ga.oneGenerationElitism()
        best_chromosome = ga.bestChromosome()
        last = ga.population
        bests.append(best_chromosome)

    the_best = bests[0]
    for best in bests:
        if best.fitness > the_best.fitness:
            the_best = best

    the_bests = [the_best]

    # pt a afisa mai multe solutii
    for candidate in last:
        if candidate.fitness == the_best.fitness and candidate not in the_bests:
            the_bests.append(candidate)

    return the_bests


if __name__ == "__main__":
    graph = read("data/medium_tsp.txt")
    result = mainfunction(graph, 100, 50)
    for res in result:
        print(res)
    result = mainfunction2(graph, 100, 50)
    for res in result:
        print(res)


