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


def all_simple_paths_graph(G, source, target, cutoff=None):
    if cutoff is None:
        cutoff = len(G) - 1
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
            G[visited[-1]][child]['card'].update_card_limit(child.w, child, G)
            G.remove_node(child)
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
        self.cards = cards
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
                    weight=c.get_cashback_for_purchase(attrs['weight']),
                    card=c
                )


class Card(object):

    def __init__(self, uid, limit, p):
        self.uid = uid
        self.limit = limit
        self.left = limit
        self.p = p

    def get_cashback_for_purchase(self, cash):
        return (cash if self.left - cash > 0 else self.left) * self.p

    def update_card_limit(self, cash, node, G: nx.DiGraph):
        if self.left - cash < 0:
            self.left = 0
        else:
            self.left -= cash

        for frm, to, attrs in G.edges(data=True):
            if attrs['card'] != self and to != node:
                continue
            if self.left != 0:
                G.add_edge(
                    frm, to,
                    weight=self.get_cashback_for_purchase(cash),
                    card=self
                )
            else:
                G.remove_edge(frm, to)


"""
    1) Покупки - вершины графа
    2) Каждая вершина соединена с каждой всеми картами
       => полносвязный направленный граф, карты - рёбра
    3) Вес каждого ребра - кэшбэк по карте для соотвествующей вершины
    4) При посещении вершины:
           1) Обновляется лимит на карте
           2) Обновляется вес каждого ребра с этой картой
           3) Посещённая вершина удаляется
"""


if __name__ == "__main__":
    cards = [
        Card(1, 10, 1.0 / 100),
        Card(2, 1, 10.0 / 100),
        Card(3, 2, 10.0 / 100)
    ]
    purchases = [
        Purchase(1),
        Purchase(1),
        Purchase(3),
        Purchase(10),
    ]
    o = Observer(cards)

    for p in purchases:
        o.add_purchase(p)
