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


def last_bit_fib(n):
    a, b = 1, 1
    for _ in range(n):
        a, b = b % 10, (a + b) % 10
    return a


def fib(n):
    a, b = 1, 1
    for _ in range(n):
        a, b = b, a + b
    return a


def solve(stream):
    pass


if __name__ == "__main__":
    pass


