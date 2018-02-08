# coding: utf-8
import networkx as nx


class CashbackLimitReachedError(Exception):

    def __new__(cls, self):
        cls.card = self


class Purchase(object):
    _NUMBER = 0

    def __init__(self, cash, uid=None):
        self.w = cash
        self.uid = str(uid or self._NUMBER)
        self._NUMBER += 1

    def __hash__(self):
        return hash(self.__str__())

    def __repr__(self):
        return str(self)

    def __str__(self):
        return self.uid


def _all_simple_paths_graph(G, source, target, cutoff=None):
    if cutoff < 1:
        return
    visited = [source]
    stack = [iter(G[source])]
    while stack:
        children = stack[-1]
        child = next(children, None)
        if child is None:
            stack.pop()
            visited.pop()
        elif len(visited) < cutoff:
            if child == target:
                yield visited + [target]
            elif child not in visited:
                visited.append(child)
                stack.append(iter(G[child]))
        else:  # len(visited) == cutoff:
            if child == target or target in children:
                yield visited + [target]
            stack.pop()
            visited.pop()


class Observer(object):

    def __init__(self, cards):
        self.cards = []#[Card()]
        self.graph = nx.DiGraph()
        self.purchases = []

    def add_purchase(self, p):
        self.purchases.append(p)
        self.graph.add_node(p, weight=p.w)

        for node, attrs in self.graph.nodes(data=True):
            for c in self.cards:
                self.graph.add_edge(
                    p,
                    node,
                    directed=True,
                    weight=c.get_cashback_for_purchase(attrs['weight'])
                )

    def on_card_limit(self):
        pass


class Card(object):

    def __init__(self, uid, limit, p):
        self.uid = uid
        self.limit = limit
        self.p = p

    def get_cashback_for_purchase(self, cash):
        return cash * self.p

    def update_card_limit(self, cash):
        cb = cash

        if self.limit - cash < 0:
            cb = self.limit
            self.limit = 0

        return cb * self.p


if __name__ == "__main__":

    g = nx.DiGraph()

    p1 = Purchase(10, "1")
    g.add_node(p1, weight=p1.w)

    p2 = Purchase(7, "2")
    g.add_node(p2, weight=p2.w)

    g.add_edge(p1, p2)
    print(g.nodes(data=True))
