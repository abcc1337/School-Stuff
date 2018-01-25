# coding: utf-8
from a1 import *

class ValueWrap(object):

    def __init__(self, v, exc=None):
        self.v = v
        self._exc = exc

    def unwrap(self):
        if self._exc is not None:
            raise self._exc

    def __str__(self):
        return "ValueWrap({}, exc=({})".format(self.v, self._exc)


class Node(object):


    def __init__(self, v, left=None, right=None, parent=None, tree=None):
        self.c = [v]
        self.v = v

        self.left = left
        self.right = right
        self.parent = parent
        self.tree = tree

    def set_v(self, v):
        self.c = [v]
        self.v = v

    def get(self):
        e = self.c.pop()
        if len(self.c) == 0:
            return ValueWrap(e, IndexError)
        return ValueWrap(e)

    def is_node_part(self, node):
        if node == self.left:
            return 1
        elif node == self.right:
            return 0
        return -1

    def new_sample(self, v=None):
        self.c.append(v or self.v)

    def __len__(self):
        return len(self.c)

    def __eq__(self, other):
        return self.v == other

    def __lt__(self, other):
        return self.c[0] < other

    def __gt__(self, other):
        return self.c[0] > other

    def __str__(self):
        return 'Node: v = {}, amount = {}'.format(self.v, len(self.c))


class Tree(object):

    def __init__(self, node=Node):
        self._node_cls = node
        self.tree = None

    def add(self, v):
        if self.tree is None:
            self.tree = self._node_cls(v, tree=self)
            return self

        p = None
        n = self.tree
        while n is not None and n:
            p = n
            if v < p.v:
                n = n.left
            elif v > p.v:
                n = n.right
            else:
                p.new_sample()
                return self

        if v < p.v:
            p.left = self._node_cls(v, parent=p, tree=self)
        else:
            p.right = self._node_cls(v, parent=p, tree=self)

        return self

    def remove(self, v):
        self._remove(v, self.tree)
        return self

    def _remove(self, v, node):
        if node is None:
            return

        if node < v:
            self._remove(v, node.left)
        elif node > v:
            self._remove(v, node.right)
        else:
            if node.p is None:
                self.tree = None
                return

            if node.left is None:
                add = node.right
            elif node.right is None:
                add  = node.left
            else:
                # Find min
                m = self._find_any(node.right, "left")
                node.set_v(m.v)
                self._remove(m.v, node.right)
                return

            res = node.p.is_node_part(node)
            if res == 1:
                node.p.left = add
            elif res == 0:
                node.p.right = add
            else:
                raise ValueError("Error with {} : {}", v, node)

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

    def extract_min(self):
        return self._extract_any(self.tree, "left")

    def extract_max(self):
        return self._extract_any(self.tree, "right")

    def _extract_any(self, node, side):
        v = self._find_any(node, side, True)
        try:
            v.unwrap()
        except IndexError:
            self.remove(v.v)
        finally:
            return v.v

    def find_min(self):
        return self._find_any(self.tree, "left", False)

    def find_max(self):
        return self._find_any(self.tree, "right", False)

    def _find_any(self, node, attr, fix=False):
        if node is None:
            return None
        while getattr(node, attr) is not None and node:
            node = getattr(node, attr)

        return node if not fix else node.get()

    def print(self):
        if self.tree is None: return
        print('T:', self.tree)
        self._print_any(self.tree, "left")
        self._print_any(self.tree, "right")

    def _print_any(self, node, side):
        n = getattr(node, side)
        while n is not None:
            print("{}: {}".format(side[0].upper(), n))
            n = getattr(n, side)

def chaos(lst, input=None):
    output = input if input is not None else []
    if not lst: return output

    # prev = lst[0]
    # for item in lst[1:]:
    #    lower =
    return


if __name__ == "__main__":
    """
    # n = int(input())
    s = list(map(int, '7 7 7 1 1'.split()))
    print(s)
    t = Tree()


    t.add(0)
    t.add(1)
    t.add(2)
    t.add(3)
    # t.add(-1)
    t.add(1)
    t.print()

    print()
    print(t.find_min())
    print(t.find_max())
    print()

    print(t.extract_max())
    t.print()
    print(t.find_max())
    """