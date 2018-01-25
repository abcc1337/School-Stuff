#coding: utf-8
import re
import random
from itertools import chain

mpset = {0, 2, 3, 4, 5, 6,
         7, 8, 9, 10, 12, 14,
         15, 16, 18, 20, 21, 24,
         25, 27, 28, 30, 32, 35,
         36, 40, 42, 45, 48, 49,
         54, 56, 63, 64, 72, 81}


def get_pieces(l):
    pcs = []
    l = [int(i) for i in l]

    if len(l) <= 2:
        return [l]
    elif len(l) == 3:
        # [a, b, c] -> [(a, b), c], [a, (b, c)] etc.
        pcs.append(l)
        pcs.append([l[:2], l[2]])
        pcs.append([l[0], l[1:]])
        return pcs
    elif len(l) == 4:
        # [a, b, c, d] -> [(a, b), c, d], [a, (b, c), d], [(a, b), (c, d)] etc.
        # kill me
        pcs.append([l[0], l[1], l[2:]])
        pcs.append([l[0], l[1:3], l[3]])
        pcs.append([l[:2], l[2], l[3]])
        pcs.append([l[:2], l[2:]])
        return pcs
    elif len(l) == 5:
        # [a, b, c, d, e] -> [(a, b), c, (d, e)], [a, (b, c), (d, e)] etc.
        # pretty YES I want to die
        pcs.append([l[0], l[1:3], l[3:]])
        pcs.append([l[:2], l[2], l[3:]])
        pcs.append([l[0:2], l[2:4], l[4]])
        pcs.append(l)
        return pcs
    pcs = l
    return [list(zip(pcs[0::2], pcs[1::2]))]

def make_multiplication_table():
    mpset = set()
    for i in range(2, 10):
        mpset |= set(i * j for j in range(1, 10))
    return mpset


def solver(s, mpset=mpset):
    if (s[0] == "1" or  s == "0") and len(s) <= 3:
        return "NO"

    p = get_pieces(list(s))

    # TODO: DELETE
    # print(p)

    result = 0
    for o in p:
        all_in_row = 1
        for i, item in enumerate(o):
            if isinstance(item, (list, tuple)):
                if item[0] == 0 or any(j > 9 for j in item):
                    item = -1
                else:
                    item = item[0] * 10 + item[1]
            all_in_row &= item in mpset
        result |= all_in_row

    return "YES" if result else "NO"


def another_solver(s):
    if (s[0] == "1" or  s == "0") and len(s) <= 3:
        return "NO"

    p = list(map(int, iter(s)))
    # TODO: DELETE
    print(p)

    selected = 0
    i = len(p) - 1
    while i >= 0:
        cur, next = p[i], p[i - 1]
        if (next * 10 + cur) in mpset:
            i -= 1
            selected += 1
        elif cur and (next == 0):
            selected += 1
        elif cur == 0:
            selected += 1
        i -= 1

        print(cur, next, i)

    return "YES" if selected == 3 else "NO"


class Stream(object):

    def __init__(self, filename=None):
        self.filename = filename

    def write(self, *args, **kwargs):
        pass

    def writeln(self, *args):
        pass


class STDOUTStream(Stream):

    def write(self, *args):
        print(*args)

    def writeln(self, *args):
        return self.write(*args)


class FileStream(Stream):

    def __init__(self, filename):
        super().__init__(filename)

        self.file = open(filename, 'w')

    def write(self, s, *args):
        self.file.write(s)

    def writeln(self, s, *args):
        self.file.write(s + "\n")


    def __enter__(self):
        self.file.__enter__()

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.file.__exit__(exc_type, exc_val, exc_tb)

    def __del__(self):
        if not self.file.closed:
            self.file.close()


def generate_vector_multiplication_sample(length=None, label=None):
    length = random.randint(1, 6) if length is None else length
    label = label if label is not None else bool(random.randint(0, 1))

    def _gen_negative(l, p=0.5):
        s = ""
        for i in range(l):
            if random.random() < p:
                s += 0


def custom_test(solver, n=1000, stream=STDOUTStream()):
    p = 0
    for i in range(n):
        label, number = generate_vector_multiplication_sample()
        stream.writeln('[STATUS: {}] {}: {} -> pred: {} | real: {}')

if __name__ == "__main__":
    filename = "a2.txt"
    stream = STDOUTStream() # FileStream("ans.txt")
    stream2 = FileStream("ans.txt")

    with open("MOSh/{}".format(filename), 'r') as f:
        for line in f.readlines():
            line = re.sub("[\s\n ]", "", line)
            #stream.writeln(line, solver(line))
            # stream.writeln(line)
            stream2.writeln(solver(line))

    # s = "6078"
    # print(solver(s))
    # print(another_solver(s))
