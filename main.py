# coding: utf-8
import time
import random

from tqdm import tqdm
import numpy as np


def timeit(f, times=1000000):
    def wrap(*args, **kwargs):
        m = 0
        for _ in range(times):
            start = time.time()
            f(*args, **kwargs)
            m += time.time() - start
        return m / times
    return wrap


def digitsum(n):
    s = 0
    while n:
        s += n % 10
        n //= 10
    return s


if __name__ == "__main__":
    pw = 10 ** 10
    step = 10e-10
    r = np.arange(0, 1, step=step)[1:]

    els = 0
    for _ in tqdm(range(pw)):
        if int(np.random.choice(r)) == int(np.random.choice(r)):
            els += 1

    print(els / pw)
