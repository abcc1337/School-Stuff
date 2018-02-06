# coding: utf-8
import bintrees as T
from a1 import *


def chaos(lst, input=None):
    output = input if input is not None else []
    if not lst: return output

    # prev = lst[0]
    # for item in lst[1:]:
    #    lower =
    return



class DataWrapper(object):

    def __init__(self, v, n=1):
        self.v = v
        self.n = n

    def __len__(self):
        return self.n

    def get(self):
        if self.n > 0:
            self.n -= 1
            return self.n
        raise ValueError("No items are left")

    def __iadd__(self, other):
        if self == other:
            self.n += 1
        else:
            raise ValueError("Items do not match: DW({}) != {}".format(self.n, other))
        return self

    def __str__(self):
        return "DataWrapper(v = {}, n = {})".format(self.v, self.n)

    def __repr__(self):
        return str(self)

    def __lt__(self, other):
        return self.v < other

    def __gt__(self, other):
        return self.v > other

    def __eq__(self, other):
        return self.v == other


def solveb(s):
    t = T.BinaryTree()
    wrappers = {}
    for e in s:
        if e in wrappers:
            wrappers[e] += e
        else:
            wrappers[e] = DataWrapper(e)
            t.insert(e, wrappers[e])
    r = ""
    for i in range(len(s)):
        f = t.min_item if i % 2 == 1 else t.max_item
        key, wrapper = f()
        wrapper.get()
        if len(wrapper) == 0:
            t.remove(key)
        r += str(key) + " "

    return r.strip()


def solvec(s):
    from random import shuffle
    def check_s(s, th=0.9):
        th = 1 / (10 ** len(str(len(s)))) * (len(s) + 1)
        v  = 0
        for i in range(1, len(s) - 1):
            if not ((s[i] <= s[i + 1] and s[i - 1] <= s[i]) or
               (s[i] >= s[i + 1] and s[i - 1] >= s[i])):
                v += 1 #return True
        # print(s, v / len(s), th)
        print(s, v / len(s), th)
        return v / len(s) < th

    while check_s(s):
        shuffle(s)

    return s


if __name__ == "__main__":
    # # n = int(input())
    # t = Tree()
    line = '1 1 7 7 7' #'1 2 3'
    line = list(map(int, line.split()))
    #
    # [t.add(e) for e in line]
    # t.print()
    #
    # for i in range(len(line)):
    #     print(t.extract_max() if i % 2 == 0 else t.extract_min())
    #     #t.print()
    #     print()
    #
    # print(solveb(line))

    # solveb = solvec
    filename = "b0.txt"
    stream = STDOUTStream() # FileStream("ans.txt")
    stream2 = FileStream(filename)
    import re

    with open("MOSh/{}".format(filename), 'r') as f:
        for line in f.readlines():
            if not re.sub(r'[\s\n ]+', "", line):
                stream2.writeln("")
                continue
            line = list(map(int, line.split()))
            s = ''.join(str(e) for e in solveb(line))
            stream.writeln(s)
            stream2.writeln(s)
