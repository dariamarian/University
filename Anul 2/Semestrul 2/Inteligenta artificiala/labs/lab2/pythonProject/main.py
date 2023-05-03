import os
import numpy as np
import networkx as nx
import matplotlib.pyplot as plt


# de la profa
def plotNetwork(G, communities):
    np.random.seed(123)  # to freeze the graph's view (networks uses a random view)

    pos = nx.spring_layout(G)  # compute graph layout
    plt.figure(figsize=(4, 4))  # image is 8 x 8 inches
    nx.draw_networkx_nodes(G, pos, node_size=50, cmap=plt.cm.RdYlBu, node_color=list(communities.values()))
    nx.draw_networkx_edges(G, pos, alpha=0.3)
    nx.draw_networkx_labels(G, pos)
    plt.show()


def greedyCommunitiesDetectionByTool(Graph, no_of_components_to_split=2):
    communities = dict.fromkeys(Graph.nodes, 0)

    conex_components = nx.algorithms.components.number_connected_components(Graph)

    while no_of_components_to_split > conex_components and conex_components < Graph.number_of_nodes():
        # calculam valoarea de centralitate
        centrality = nx.algorithms.centrality.edge_betweenness_centrality(Graph)
        # sortam dupa centralitati
        sorted_edges = sorted(centrality.items(), key=lambda item: item[1], reverse=True)[0]
        # stergem muchia cu cea mai mare centralitate
        Graph.remove_edge(*sorted_edges[0])
        conex_components = nx.algorithms.components.number_connected_components(Graph)

    index = 1
    for community in [c for c in sorted(nx.connected_components(Graph), key=len, reverse=True)]:
        for node in community:
            communities[node] = index
        index += 1

    return communities


def printOptions():
    print('Choose: ')
    print('1 - dolphins')
    print('2 - football')
    print('3 - karate')
    print('4 - krebs')
    print('5 - les miserables')
    print('6 - word adjacencies')
    print('7 - lobster')
    print('8 - books about politics')
    print('9 - exemplu')
    print('10 - primary school')
    print('0 - exit')


def choose():
    printOptions()
    while True:
        cmd = input('>>> ')
        if cmd == '1':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'dolphins', 'dolphins.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '2':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'football', 'football.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '3':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'karate', 'karate.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '4':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'krebs', 'krebs.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '5':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'lesmis', 'lesmis.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '6':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'adjnoun', 'adjnoun.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '7':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'lobster', 'lobster.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '8':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'polbooks', 'polbooks.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '9':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'exemplu', 'exemplu.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '10':
            crtDir = os.getcwd()
            filePath = os.path.join(crtDir, 'real', 'primaryschool', 'primaryschool.gml')
            Graph = nx.read_gml(filePath, label='id')
        elif cmd == '0':
            break

        G_copy = Graph.copy()
        # print(Graph.edges(data=True))
        # print(Graph.nodes)

        plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))


def run():
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'dolphins', 'dolphins.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'football', 'football.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'karate', 'karate.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'krebs', 'krebs.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'lesmis', 'lesmis.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'adjnoun', 'adjnoun.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'lobster', 'lobster.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'polbooks', 'polbooks.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'exemplu', 'exemplu.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'real', 'primaryschool', 'primaryschool.gml')
    Graph = nx.read_gml(filePath, label='id')
    G_copy = Graph.copy()
    plotNetwork(G_copy, greedyCommunitiesDetectionByTool(Graph, 2))


if __name__ == "__main__":
    # choose()
    run()
