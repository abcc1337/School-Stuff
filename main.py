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


def S(a1, d, n):
    return (2 * a1 + d * (n - 1)) * n // 2


def task2_calculator(nabc):
    n, a, b, c = map(int, nabc[0].split())
    n = ((n + 1 + S(0, 2, b)) // 2 ** b)
    n //= (2 * a) + (a == 0)
    n = ((n - 1 - S(0, 2, c)) // 2 ** c)
    return [str(n)]


if __name__ == "__main__":
    from problems.problem_eastimator import Solver, Estimator

    s = Solver(task2_calculator)
    es = Estimator(2, s)
    es.estimate(silent=False)
