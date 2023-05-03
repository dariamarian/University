import math
import random


class ACO:
    class Edge:
        def __init__(self, a, b, weight, initial_pheromone):
            self.a = a
            self.b = b
            self.weight = weight
            self.pheromone = initial_pheromone

    class Ant:
        def __init__(self, alpha, beta, num_nodes, edges):
            self.alpha = alpha
            self.beta = beta
            self.num_nodes = num_nodes
            self.edges = edges
            self.tour = None
            self.distance = 0.0

        def _select_node(self):
            roulette_wheel = 0.0
            unvisited_nodes = [node for node in range(self.num_nodes) if
                               node not in self.tour]
            heuristic_total = 0.0
            for unvisited_node in unvisited_nodes:
                if (self.edges[self.tour[-1]][unvisited_node] != None):
                    heuristic_total += self.edges[self.tour[-1]][unvisited_node].weight
            for unvisited_node in unvisited_nodes:
                if (self.edges[self.tour[-1]][unvisited_node] != None):
                    roulette_wheel += pow(
                        self.edges[self.tour[-1]][unvisited_node].pheromone,
                        self.alpha) * \
                                      pow((heuristic_total / self.edges[self.tour[-1]][
                                          unvisited_node].weight), self.beta)
            random_value = random.uniform(0.0, roulette_wheel)
            wheel_position = 0.0
            for unvisited_node in unvisited_nodes:
                if (self.edges[self.tour[-1]][unvisited_node] != None):
                    wheel_position += pow(
                        self.edges[self.tour[-1]][unvisited_node].pheromone,
                        self.alpha) * \
                                      pow((heuristic_total / self.edges[self.tour[-1]][
                                          unvisited_node].weight), self.beta)
                if wheel_position >= random_value:
                    return unvisited_node

        def find_tour(self):
            self.tour = [random.randint(0, self.num_nodes - 1)]
            while len(self.tour) < self.num_nodes:
                self.tour.append(self._select_node())
            return self.tour

        def get_distance(self):
            self.distance = 0.0
            for i in range(self.num_nodes):
                if (self.edges[self.tour[i]][self.tour[(i + 1) % self.num_nodes]] != None):
                    self.distance += self.edges[self.tour[i]][
                        self.tour[(i + 1) % self.num_nodes]].weight
            return self.distance

    # def __init__(self, mode='ACS', colony_size=10, elitist_weight=1.0,
    #              min_scaling_factor=0.001, alpha=1.0, beta=3.0,
    #              rho=0.1, pheromone_deposit_weight=1.0, initial_pheromone=1.0,
    #              steps=100, nodes=None, labels=None):
    # 	self.mode = mode
    # 	self.colony_size = colony_size
    # 	self.elitist_weight = elitist_weight
    # 	self.min_scaling_factor = min_scaling_factor
    # 	self.rho = rho
    # 	self.pheromone_deposit_weight = pheromone_deposit_weight
    # 	self.steps = steps
    # 	self.num_nodes = len(nodes)
    # 	self.nodes = nodes
    # 	if labels is not None:
    # 		self.labels = labels
    # 	else:
    # 		self.labels = range(1, self.num_nodes + 1)
    # 	self.edges = [[None] * self.num_nodes for _ in range(self.num_nodes)]
    # 	for i in range(self.num_nodes):
    # 		for j in range(i + 1, self.num_nodes):
    # 			self.edges[i][j] = self.edges[j][i] = self.Edge(i, j, math.sqrt(
    # 				pow(self.nodes[i][0] - self.nodes[j][0], 2.0) + pow(
    # 					self.nodes[i][1] - self.nodes[j][1], 2.0)),
    # 			                                                initial_pheromone)
    # 	self.ants = [self.Ant(alpha, beta, self.num_nodes, self.edges) for _ in
    # 	             range(self.colony_size)]
    # 	self.global_best_tour = None
    # 	self.global_best_distance = float("inf")

    def __init__(self, mode='ACS', colony_size=10, elitist_weight=1.0,
                 min_scaling_factor=0.001, alpha=1.0, beta=3.0,
                 rho=0.1, pheromone_deposit_weight=1.0, initial_pheromone=1.0,
                 steps=100, num_nodes=None, input_edges=None, labels=None):
        self.mode = mode
        self.colony_size = colony_size
        self.elitist_weight = elitist_weight
        self.min_scaling_factor = min_scaling_factor
        self.rho = rho
        self.pheromone_deposit_weight = pheromone_deposit_weight
        self.steps = steps
        self.num_nodes = num_nodes

        if labels is not None:
            self.labels = labels
        else:
            self.labels = range(1, self.num_nodes + 1)
        self.edges = [[None] * self.num_nodes for _ in range(self.num_nodes)]
        for edge in input_edges:
            print(edge)
            self.edges[edge[0]][edge[1]] = self.edges[edge[1]][edge[0]] = self.Edge(
                edge[0], edge[1], edge[2], initial_pheromone)

        self.ants = [self.Ant(alpha, beta, self.num_nodes, self.edges) for _ in
                     range(self.colony_size)]
        self.global_best_tour = None
        self.global_best_distance = float("inf")

    def _add_pheromone(self, tour, distance, weight=1.0):
        pheromone_to_add = self.pheromone_deposit_weight / distance
        for i in range(self.num_nodes):
            if (self.edges[tour[i]][tour[(i + 1) % self.num_nodes]] != None):
                self.edges[tour[i]][
                    tour[(i + 1) % self.num_nodes]].pheromone += weight * pheromone_to_add

    def _acs(self):
        for step in range(self.steps):
            for ant in self.ants:
                self._add_pheromone(ant.find_tour(), ant.get_distance())
                if ant.distance < self.global_best_distance:
                    self.global_best_tour = ant.tour
                    self.global_best_distance = ant.distance
            for i in range(self.num_nodes):
                for j in range(i + 1, self.num_nodes):
                    if self.edges[i][j] != None:
                        self.edges[i][j].pheromone *= (1.0 - self.rho)

    def _elitist(self):
        for step in range(self.steps):
            for ant in self.ants:
                self._add_pheromone(ant.find_tour(), ant.get_distance())
                if ant.distance < self.global_best_distance:
                    self.global_best_tour = ant.tour
                    self.global_best_distance = ant.distance
            self._add_pheromone(self.global_best_tour, self.global_best_distance,
                                weight=self.elitist_weight)
            for i in range(self.num_nodes):
                for j in range(i + 1, self.num_nodes):
                    self.edges[i][j].pheromone *= (1.0 - self.rho)

    def _max_min(self):
        for step in range(self.steps):
            iteration_best_tour = None
            iteration_best_distance = float("inf")
            for ant in self.ants:
                ant.find_tour()
                if ant.get_distance() < iteration_best_distance:
                    iteration_best_tour = ant.tour
                    iteration_best_distance = ant.distance
            if float(step + 1) / float(self.steps) <= 0.75:
                self._add_pheromone(iteration_best_tour, iteration_best_distance)
                max_pheromone = self.pheromone_deposit_weight / iteration_best_distance
            else:
                if iteration_best_distance < self.global_best_distance:
                    self.global_best_tour = iteration_best_tour
                    self.global_best_distance = iteration_best_distance
                self._add_pheromone(self.global_best_tour, self.global_best_distance)
                max_pheromone = self.pheromone_deposit_weight / self.global_best_distance
            min_pheromone = max_pheromone * self.min_scaling_factor
            for i in range(self.num_nodes):
                for j in range(i + 1, self.num_nodes):
                    self.edges[i][j].pheromone *= (1.0 - self.rho)
                    if self.edges[i][j].pheromone > max_pheromone:
                        self.edges[i][j].pheromone = max_pheromone
                    elif self.edges[i][j].pheromone < min_pheromone:
                        self.edges[i][j].pheromone = min_pheromone

    def run(self):
        print('Started : {0}'.format(self.mode))
        if self.mode == 'ACS':
            self._acs()
        elif self.mode == 'Elitist':
            self._elitist()
        else:
            self._max_min()
        print('Ended : {0}'.format(self.mode))
        print('Sequence : {0}'.format(
            ' - '.join(str(self.labels[i]) for i in self.global_best_tour)))
        print('Total distance travelled to complete the tour : {0}\n'.format(
            round(self.global_best_distance, 2)))

    def getNodesFromFile(filename):
        with open(filename, "r") as file:
            lines = file.readlines()
            _nodes = []
            for line in lines:
                node, longitude, latitude = line.strip().split()
                _nodes.append((float(longitude), float(latitude)))
            return _nodes

    def getEdgesFromFile(filename):
        with open(filename, "r") as file:
            lines = file.readlines()
            _edges = []
            _num_nodes = 0
            for line in lines:
                edge1, edge2, weight, year = line.strip().split()
                _edges.append((int(edge1) - 1, int(edge2) - 1, float(weight)))
                _num_nodes = max(_num_nodes, int(edge1), int(edge2))
            return _num_nodes, _edges


if __name__ == '__main__':
    _colony_size = 10
    _steps = 100

    # _nodes = ACO.getNodesFromFile('dijbouti.tsp')
    # acs = ACO(mode='ACS', colony_size=_colony_size, steps=_steps,
    #           nodes=_nodes)
    # acs.run()

    _num_nodes, _edges = ACO.getEdgesFromFile('insecta-ant-colony1.edges')

    acs = ACO(mode='ACS', colony_size=_colony_size, steps=_steps, num_nodes=_num_nodes,
              input_edges=_edges)
    acs.run()
