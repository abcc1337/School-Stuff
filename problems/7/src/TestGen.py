from os import mkdir
from random import *
from math import *

seed('power')

MAXX_SMALL = 10**4
MAXX = 10**9
MAXN = 200000

logger = lambda s : print (s)

def compose(*fs):
    def c(a):
        res = a
        for f in fs[::-1]:
            res = f(res)
        return res
    return c

def shuffled(a):
    b = a[:]
    shuffle(b)
    return b

def flip(a):
    maxx = max(x for x, y in a)
    maxy = max(y for x, y in a)
    return [(maxx + 1 - x, maxy + 1 - y) for x, y in a]

rand = lambda n, x: [(randint(1, x), randint(1, x)) for i in range(n)]
stairs = lambda n: [(i, i) for i in range(1, n + 1)]
revstairs = lambda n: [(i, n+1-i) for i in range(1, n + 1)]
shiftx = lambda dx: lambda a: [(x+dx, y) for x, y in a]
shifty = lambda dy: lambda a: [(x, y+dy) for x, y in a]
shift = lambda dx, dy: compose(shiftx(dx), shifty(dy))
blur = lambda d: lambda a: [(x + randint(-d, d), y + randint(-d, d)) for x, y in a]

def circle(n, radius, start=0, end=pi/2):
    res = []
    step = (end - start) / n
    for i in range(n):
        angle = start + step * i
        res.append((int(radius * sin(angle)), int(radius * cos(angle))))
    return res

def crop(a, maxx):
    return [(min(maxx, max(1, x)), min(maxx, max(1, y))) for x, y in a]

def hyperbola(n, focus, maxx=MAXX):
    half = [((n - i), int(focus * focus / (n - i))) for i in range(1, n//2)]
    res = [(y, x) for x, y in half] + [(focus, focus)] + half
    return crop(res, maxx)

validator = lambda k, a: 1 <= k <= len(a) and all(1 <= y <= MAXX for x in a for y in x)

prevalidators = {
        1: lambda k, a: 1 <= len(a) <= 20 and all(1 <= y <= MAXX_SMALL for x in a for y in x),
        2: lambda k, a: 1 <= len(a) <= 300 and all(1 <= y <= MAXX_SMALL for x in a for y in x),
        3: lambda k, a: 1 <= len(a) <= 3000,
        4: lambda k, a: 1 <= len(a) <= MAXN and k == 2,
        5: lambda k, a: 1 <= len(a) <= MAXN
}

class Printer:
    def __init__(self, path, desc):
        self.path = "../" + path
        try:
            mkdir(self.path)
            logger("Creating directory %s"%self.path)
        except:
            pass
        self.desc = desc
        self.tests = 0

    def print(self, k, a, subtask=None, comment=None):
        a = crop(a, MAXX_SMALL if subtask and subtask <= 2 else MAXX)
        assert validator(k, a), "Main validator {} {}".format(k, a)
        assert (not subtask) or prevalidators[subtask](k, a), "Subtask validator {} {}".format(subtask, k, a)
        self.tests += 1
        sub = ("subtask" + str(subtask)) if subtask else ""
        d = self.path + "/" + sub
        try:
            mkdir(d)
            logger("Creating directory %s"%d)
        except:
            pass
        p = d + "/" + "%02d"%self.tests
        logger("Printing test %s"%p)
        out = open(p, "w")
        print(len(a), k, file=out)
        for x, y in a:
            print(x, y, file=out)
        if not comment:
            comment = ""
        comment += ", n={}, k={}".format(len(a), k)
        print("{}\t{}".format(p, comment), file=desc)

desc = open("../tests.desc", "w")

prelim = Printer("preliminary", desc)
prelim.print(3, [(3, 5), (2, 2), (2, 5), (4, 4), (5, 3)])

tests = Printer("tests", desc)
# n <= 20
tests.print(1, [(1, 1)], subtask=1, comment="Smallest test")
tests.print(1, [(1, 3), (2, 1)], subtask=1, comment="")
tests.print(1, [(1, 1), (2, 2)], subtask=1, comment="")
tests.print(2, [(1, 2), (3, 1)], subtask=1, comment="")
tests.print(2, [(2, 2), (1, 1)], subtask=1, comment="")

tests.print(5, rand(10, 10), subtask=1, comment="random test")
tests.print(1, [(MAXX_SMALL, MAXX_SMALL)], subtask=1, comment="large coordinates")
tests.print(randint(1, 10), shuffled(stairs(10)), subtask=1)
tests.print(randint(1, 10), shuffled(revstairs(10)), subtask=1)
tests.print(1, rand(20, 100), subtask=1, comment="random test")

tests.print(2, [(10, 20)] + shiftx(100)(stairs(10)), subtask=1, comment="")

tests.print(5, circle(10, 10), subtask=1, comment="arc 0-90")
tests.print(12, circle(10, 10) + circle(10, 20), subtask=1, comment="arc 0--90")

def gen(subtask, n, MAXX=MAXX):
    tests.print(1, [(1, 1)] * n, subtask=subtask, comment="small equal squares")
    tests.print(n, [(1, 1)] * n, subtask=subtask, comment="small equal squares")
    tests.print(n//2, [(MAXX, MAXX-1)] * n, subtask=subtask, comment="large equal rectabgles")
    for m in [n//3, n//2, n, n]:
        tests.print(randint(1, m), rand(m, MAXX), subtask=subtask, comment="random test")
    tests.print(n, rand(n, MAXX), subtask=subtask, comment="random, n=k")
    tests.print(n-1, rand(n, MAXX), subtask=subtask, comment="random, n=k+1")
    tests.print(1, circle(n, MAXX), subtask=subtask, comment="arc 0--90")
    tests.print(randint(1, n), circle(n, MAXX), subtask=subtask, comment="arc 0--60")
    tests.print(2 * n // 3, stairs(n // 2) + revstairs(n // 2), subtask=subtask, comment="two diagonals")
    tests.print(2, [(MAXN//2, MAXN)] + shiftx(MAXX - n//2)(stairs(n - 1)), subtask=subtask, comment="Pareto frontier=2")
    tests.print(1, [(MAXX, MAXX)] + blur(1)(hyperbola(n - 1, int(sqrt(MAXX)))), subtask=subtask, comment="Blurred hyperbola")
    tests.print(n // 3, [(MAXX, MAXX)] * (n//3 - 1) + blur(1)(hyperbola(n - n//3, int(sqrt(MAXX)))), subtask=subtask, comment="Slightly blurred hyperbola")
    tests.print(n // 3, [(MAXX, MAXX)] * (n//3 - 1) + blur(10)(hyperbola(n - n//3, int(sqrt(MAXX)))), subtask=subtask, comment="Slightly blurred hyperbola")

    sq = int(sqrt(n))
    tests.print(sq, shuffled(blur(1)(hyperbola(sq, int(sqrt(MAXX)))) * sq), subtask=subtask, comment="Multiple hyperbolae blurred")
    
def genk2(subtask, n):
    tests.print(2, [(1, 2), (2, 1)], subtask=subtask, comment="Small unequal rectangles")
    tests.print(2, [(1, 2), (1, 2)], subtask=subtask, comment="Small equal rectangles")
    tests.print(2, [(100, 99)] * MAXN, subtask=subtask, comment="Many equal rectangles")
    tests.print(2, [(MAXN//2, MAXN)] + shiftx(MAXX - n//2)(stairs(n - 1)), subtask=subtask, comment="Pareto frontier=2")
    for m in [n//3, n//2, n, n]:
        tests.print(2, rand(m, MAXX), subtask=subtask, comment="random test")
    tests.print(2, [(MAXX, MAXX)] + blur(2)(hyperbola(n - 1, int(sqrt(MAXX)))), subtask=subtask, comment="Blurred hyperbola")

gen(2, 300, MAXX=MAXX_SMALL)
gen(3, 3000)
genk2(4, MAXN)
gen(5, MAXN)
                
                
