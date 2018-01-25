# coding: utf-8


class Node(object):

    def __init__(self, v, left=None, right=None, parent=None):
        self.c = [v]
        self.v = v

        self.left = left
        self.right = right
        self.parent = parent

    def __len__(self):
        return len(self.c)

    def __eq__(self, other):
        return self.v == other

    def __lt__(self, other):
        return self.c[0] < other

    def __gt__(self, other):
        return self.c[0] > other


class Tree(object):

    def __init__(self, node=Node):
        self._node_cls = node
        self.tree = None

    def add(self, v):
        if self.tree is None:
            self.tree = self._node_cls(v)
            return self

        p = None
        n = self.tree
        while n is not None and n:
            p = n
            if n < p.:
                n = n.right

    # TODO: find nearest
    def find(self, e):
        n = self.tree
        while n is not None and n:
            if n < e:
                n = n.right
            elif n > e:
                n = n.left
            else:
                return n
        return None

    @staticmethod
    def find_min(node):
        return Tree._find_any(node, "left")

    @staticmethod
    def find_max(node):
        return Tree._find_any(node, "right")

    @staticmethod
    def _find_any(node, attr):
        if node is None:
            return None
        while getattr(node, attr) is not None and node:
            node = getattr(node, attr)
        return node


def chaos(lst, input=None):
    output = input if input is not None else []
    if not lst: return output

    # prev = lst[0]
    # for item in lst[1:]:
    #    lower =
    return


if __name__ == "__main__":
    # n = int(input())
    s = list(map(int, '7 7 7 1 1'.split()))
    print(s)
    print(chaos(s))

