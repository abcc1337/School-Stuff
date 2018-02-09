# coding: utf-8
import random

import bintrees as T
import networkx as nx
from tqdm import tqdm


class CashbackLimitReachedError(Exception):

    def __new__(cls, self):
        cls.card = self


class Purchase(object):
    _NUMBER = 0

    def __init__(self, cash, uid=None):
        self.w = cash
        self.uid = str(uid or Purchase._NUMBER)
        Purchase._NUMBER += 1

    def __gt__(self, other):
        if not isinstance(other, Purchase):
            raise ValueError("Can not compare {} and Purchase".format(other.__class__))
        return self.w > other.w

    def __lt__(self, other):
        if not isinstance(other, Purchase):
            raise ValueError("Can not compare {} and Purchase".format(other.__class__))
        return self.w < other.w

    def __eq__(self, other):
        if not isinstance(other, Purchase):
            raise ValueError("Can not compare {} and Purchase".format(other.__class__))
        return self.w == other.w

    def __hash__(self):
        return hash(self.__str__())

    def __repr__(self):
        return str(self)

    def __str__(self):
        return "<Purchase[{uid}] = {w}>".format(w=self.w, uid=self.uid)


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

        self._salt = ''.join(chr(random.randint(64, 128)) for _ in range(25))

    def get_cashback_for_purchase(self, cash):
        return (cash if self.left - cash > 0 else self.left) * self.p

    def update_card_limit(self, cash):
        if self.left - cash < 0:
            self.left = 0
        else:
            self.left -= cash
        return self.left

    def update_card_limit_graph(self, cash, node, G: nx.DiGraph):
        self.update_card_limit(cash)

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

    @property
    def max_cashback(self):
        return self.left * self.p

    def __lt__(self, other: "Card"):
        if not isinstance(other, Card):
            raise ValueError("Can not compare {} and Card".format(other.__class__))
        return self.max_cashback < other.max_cashback

    def __gt__(self, other):
        if not isinstance(other, Card):
            raise ValueError("Can not compare {} and Card".format(other.__class__))
        return self.max_cashback > other.max_cashback

    def __eq__(self, other):
        if not isinstance(other, Card):
            raise ValueError("Can not compare {} and Card".format(other.__class__))
        return self.max_cashback == other.max_cashback

    def __ge__(self, other):
        if not isinstance(other, Card):
            raise ValueError("Can not compare {} and Card".format(other.__class__))
        return self.max_cashback >= other.max_cashback

    def __le__(self, other):
        if not isinstance(other, Card):
            raise ValueError("Can not compare {} and Card".format(other.__class__))
        return self.max_cashback <= other.max_cashback

    def __hash__(self):
        return hash(str(self) + self._salt)

    def __repr__(self):
        return str(self)

    def __str__(self):
        return "<Card [{}] | lim={}, left={}, p={}, CB = {}>".format(
            self.uid, self.limit,
            self.left, self.p,
            self.max_cashback
        )

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

def colorize(t, color):
    class bcolors:
        HEADER = '\033[95m'
        OKBLUE = '\033[94m'
        OKGREEN = '\033[92m'
        WARNING = '\033[93m'
        FAIL = '\033[91m'
        ENDC = '\033[0m'
        BOLD = '\033[1m'
        UNDERLINE = '\033[4m'
    return "{}{}{}".format(getattr(bcolors, color.upper()), t, bcolors.ENDC)



def print_card_info(cards, info="", n=25):
    print('|{}{}{}|'.format(
        '-' * (n - len(info) // 2),
        colorize(info, "okgreen"),
        '-' * (n - len(info) // 2 - len(info) % 2)
    ))
    for c in cards:
        print("| {}".format(c))
    print('|{}|'.format('-' * (n * 2)))


def calculate_policy(cards, purchases, verbose=False):
    for c in cards:
        t.insert(hash(c), c)
    purchases = sorted(purchases, reverse=True)
    policy = {p: None for p in purchases}

    def act(*args):
        global t
        h, card = t.max_item()
        # print(card)
        policy[p] = card.uid
        if card.update_card_limit(p.w) == 0:
            t.remove(h)
        t = T.RBTree(t.items())
        return h, card

    if verbose:
        def make_verbose(f):
            def wrap(p, i):
                print_card_info(cards, info="> P = {}, ITER = {} <".format(p, i))
                result = f()
                print_card_info(cards, info="> P = {}, ITER = {} {} <".format(p, i, "END"))
                print(colorize('#' * 52, "fail"))
                return result
            return wrap
        act = make_verbose(act)

    for i, p in enumerate(purchases):
        try:
            __, card = act(p, i)
        except ValueError as e:
            if str(e) == 'Tree is empty':
                policy[p] = card.uid
                t.insert(hash(card), card)

    return ' '.join(str(policy[p]) for p in sorted(policy, key=lambda x: x.uid))


def make_purchases_list(s):
    return list(map(lambda x: Purchase(int(x)), s.split(' ')))


if __name__ == "__main__":
    from MOSh.solutions.a1 import FileStream

    """3
100 10.0
1000 1.1
100 2.2
11
1 2 3 5 10 23 71 34 11 55 29"""

    t = T.RBTree() #T.BinaryTree()
    cards = [
        Card(1, 100, 10.0 / 100),
        Card(2, 1000, 1.1 / 100),
        Card(3, 100, 2.2 / 100)
    ]

    for c in cards:
        t.insert(hash(c), c)

    purchases = make_purchases_list("1 2 3 5 10 23 71 34 11 55 29")
    print(calculate_policy(cards, purchases, True))


    """
    policy = {p: None for p in purchases}

    for i, p in enumerate(purchases):
        try:
            print_card_info(cards, info="> P = {}, ITER = {} <".format(p, i))
            h, card = t.max_item()
            policy[p] = card.uid
            if card.update_card_limit(p.w) == 0:
                 t.remove(h)
            print_card_info(cards, info="> P = {}, ITER = {} {} <".format(p, i, "END"))
            print(colorize('#' * 52, "fail"))
        except ValueError as e:
            if str(e) == 'Tree is empty':
                policy[p] = card.uid
                t.insert(hash(card), card)

    print([(p, policy[p]) for p in sorted(policy, key=lambda x: x.uid)])
    """