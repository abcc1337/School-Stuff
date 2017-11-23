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


class State(object):

    def __init__(self, ambs ,n_abms):
        self.ambs = ambs
        self.n = n_abms

    def is_lost(self):
        return self.n < min(self.ambs) or self.n <= 0

    def copy(self):
        return State(self.ambs, self.n)


class Simulation(object):

    def __init__(self, s1: State, s2: State):
        self.s1 = s1
        self.s2 = s2

    def simulate(self):
        while not self.s1.is_lost() and not self.s1.is_lost():
            action = self.select_best_action(self.s1.ambs)
            self.s1.n -= action
            self.s2.n -= action
            print(self.s1.n, self.s2.n, action)

            # if self.s2.is_lost():
            #    return True

            action = self.select_best_action(self.s2.ambs)
            self.s1.n -= action
            self.s2.n -= action

            print(self.s1.n, self.s2.n, action)

            # if self.s1.is_lost():
            #    return False
        return self.s2.is_lost()

    def select_best_action(self, actions):
        for amb in actions:
            v = self.s1.n - amb
            if any(filter(lambda x: v % x == 0, self.s1.ambs)):
                return amb
        return random.choice(actions)


if __name__ == "__main__":
    # c1, c2 = map(Container, eval(input()))
    rng = range(1, 4 * 10 ** 4 + 1)
    c1, c2 = [[2, 3, 4], [2, 3]]

    i = 2
    l = []
    d = iter(c1)
    for i in range(1, 11 + 1):
        s1, s2 = State(c1, i), State(c2, i)
        s = Simulation(s1, s2)
        if s.simulate():
            l.append(i)
        print()
    print(l)