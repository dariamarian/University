from ACO import ACO, Graph, distance
from utils import read, write, readDynamic


def main():
    tsp = read('data/static/dj38.tsp')
    cities = tsp['NODE_COORD_SECTION']
    rank = int(tsp['DIMENSION'])
    cost_matrix = []
    for i in range(rank):
        row = []
        for j in range(rank):
            row.append(distance(cities[i], cities[j]))
        cost_matrix.append(row)
    aco = ACO(ant_count=10, iterations=50, alpha=1.0, beta=10.0, rho=0.5, q=10)
    graph = Graph(cost_matrix, rank)
    route, route_length = aco.solve(graph)
    write(rank, route, route_length, 'output.txt')

    tsp = read('data/static/qa194.tsp')
    cities = tsp['NODE_COORD_SECTION']
    rank = int(tsp['DIMENSION'])
    cost_matrix = []
    for i in range(rank):
        row = []
        for j in range(rank):
            row.append(distance(cities[i], cities[j]))
        cost_matrix.append(row)
    aco = ACO(ant_count=10, iterations=50, alpha=1.0, beta=10.0, rho=0.5, q=10)
    graph = Graph(cost_matrix, rank)
    route, route_length = aco.solve(graph)
    write(rank, route, route_length, 'output2.txt')


if __name__ == '__main__':
    main()