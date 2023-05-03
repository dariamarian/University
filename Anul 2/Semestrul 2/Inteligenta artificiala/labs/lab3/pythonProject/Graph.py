import networkx as nx

class Graph:
    def __init__(self, gml_file_path):
        self._nx_graph = self._readGML(gml_file_path)

    def _readGML(self, name):
        return nx.read_gml(name, label='id')

    def getParams(self):
        return {'noNodes': self._nx_graph.number_of_nodes(),
                'mat': nx.adjacency_matrix(self._nx_graph).toarray().tolist(),
                'degrees': [j for i, j in sorted(list(self._nx_graph.degree()), key=lambda x: x[0])],
                'noEdges': self._nx_graph.number_of_edges(),
                'edges': list(self._nx_graph.edges())}

    @property
    def nx_graph(self):
        return self._nx_graph