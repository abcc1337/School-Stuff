# coding: utf-8
import time
import random


def timeit(f, times=1000000):
    def wrap(*args, **kwargs):
        m = 0
        for _ in range(times):
            start = time.time()
            f(*args, **kwargs)
            m += time.time() - start
        return m / times
    return wrap



def task5(kxy):
    N, x, y = map(int, kxy)
    return [str(x + y + N)]


class Point(object):

    def __init__(self, x, type_):
        self.start_x = x
        self.x = x
        self.t = type_
        self.dead = False

    def move(self, time):
        self.x += (self.t * time)

    def kill(self):
        self.dead = True

    def __xor__(self, other):
        if self.t == other.t:
            return False
        return self.t > other.x

    """
        --------------------
        x     x2   z1      z

        0 +  3          7    10 -
        t = 3
    """

if __name__ == "__main__":
    n = int(input())
    moments = 0
    points = []

    for _ in range(n):
        points.append(Point(*map(int, input().split())))

    points.sort(key=lambda x: x.x)
    moments = int(input())
    for t in map(int, input().split()):
        s = 0
        points[0].move(t)
        for i in range(1, len(points)):
            points[i].move(t)

            if not (points[i].dead or points[i - 1].dead) and points[i] ^ points[i - 1]:
                points[i].kill()
                points[i - 1].kill()

            if not points[i - 1].dead:
                s += 1

        points.sort(key = lambda x: x.x)
        print(s)

    quit(0)
    from problems.problem_eastimator import Solver, Estimator

    s = Solver(task5)
    es = Estimator(5, s)
    es.estimate(silent=False)
