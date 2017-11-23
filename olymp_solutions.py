# coding: utf-8
from collections import defaultdict


class Graph:

    def __init__(self):
        self.nodes = set()
        self.edges = defaultdict(list)
        self.distances = {}

    def add_node(self, value):
        self.nodes.add(value)

    def add_edge(self, from_node, to_node, distance):
        self.edges[from_node].append(to_node)
        self.edges[to_node].append(from_node)
        self.distances[(from_node, to_node)] = distance

    def __str__(self):
        return "{}\n{}".format(self.nodes, self.edges)


def dijsktra(graph: Graph, initial):
    visited = {initial: 0}
    path = {}

    nodes = set(graph.nodes)

    while nodes:
        min_node = None
        for node in nodes:
            if node in visited:
                if min_node is None:
                    min_node = node
                elif visited[node] < visited[min_node]:
                    min_node = node

        if min_node is None:
            break

        nodes.remove(min_node)
        current_weight = visited[min_node]

        for edge in graph.edges[min_node]:
            weight = current_weight + graph.distances[(min_node, edge)]
            if edge not in visited or weight < visited[edge]:
                visited[edge] = weight
                path[edge] = min_node

    return visited, path


if __name__ == "__main__":
    n, m = map(int, input().split())
    g = Graph()

    for _ in range(m):
        _, *stamps = input().split()

        for i in range(1, len(stamps), 2):
            g.add_node(stamps[i - 1])
            g.add_node(stamps[i + 1])
            g.add_edge(stamps[i - 1], stamps[i + 1], int(stamps[i]))
            g.add_edge(stamps[i + 1], stamps[i - 1], int(stamps[i]))

    print(dijsktra(g, '1'))

