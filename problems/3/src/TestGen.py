import random
import math
random.seed('regional-2017/day1/data')

class TestGen:
    def __init__(self):
        self.test_number = 0
        self.subtask = ''
        self.desc_file = open('../tests.desc', 'w')

    def generate_samples(self):             
        self.print_test(5, [[1, 2], [2, 3], [3, 4], [3, 5], [4, 5]], 'sample, n = 5, m = 5', 'preliminary')

    def generate_random_graph(self, maxn, maxm=None):
        n = maxn
        edges = []
        p = [i for i in range(n)]
        random.shuffle(p)
        was = set()
        for i in range(1, n):
            j = random.randint(0, i - 1)
            edges.append([p[j] + 1, p[i] + 1])
            was.add((p[j], p[i]))
            was.add((p[i], p[j]))
        mm = n - 1
        if maxm is not None:
            mm = min(mm, maxm - (n - 1))
        es = random.randint(0, mm)
        for e in range(es):
            v = random.randint(0, n - 1)
            u = random.randint(0, n - 1)
            while v == u or (v, u) in was:
                v = random.randint(0, n - 1)
                u = random.randint(0, n - 1)
            edges.append([v + 1, u + 1])
            was.add((v, u))
            was.add((u, v))
        self.print_test(n, edges, 'random graph, n = %d, m = %d' % (n, len(edges)))

    def generate_random_tree(self, maxn):
        n = maxn
        edges = []
        p = [i for i in range(n)]
        random.shuffle(p)
        for i in range(1, n):
            j = random.randint(0, i - 1)
            edges.append([p[j] + 1, p[i] + 1])
        self.print_test(n, edges, 'random tree, n = %d, m = %d' % (n, len(edges)))

    def generate_cycle(self, maxn):
        n = maxn
        edges = []
        p = [i for i in range(n)]
        random.shuffle(p)
        for i in range(1, n):
            j = random.randint(0, i - 1)
            edges.append([p[i - 1] + 1, p[i] + 1])
        edges.append([p[0] + 1, p[-1] + 1])
        self.print_test(n, edges, 'cycle, n = %d' % n)

    def generate_bamboo(self, maxn):
        n = maxn
        p = [i for i in range(n)]
        random.shuffle(p)
        edges = []
        for i in range(1, n):
            edges.append([p[i - 1] + 1, p[i] + 1])
        self.print_test(n, edges, 'bamboo, n = %d' % n)

    def generate_bamboo_with_edge(self, maxn):
        n = maxn
        p = [i for i in range(n)]
        random.shuffle(p)
        edges = []
        used = set()
        for i in range(1, n):
            edges.append([p[i - 1] + 1, p[i] + 1])
            used.add((p[i - 1], p[i]))
            used.add((p[i], p[i - 1]))
        v = random.randint(0, n - 1)
        u = random.randint(0, n - 1)
        while v == u or (v, u) in used:
            v = random.randint(0, n - 1)
            u = random.randint(0, n - 1)
        edges.append([v + 1, u + 1])
        self.print_test(n, edges, 'bamboo, n = %d' % n)

    def generate_bamboo_with_trash(self, maxn):
        n0 = 2 * maxn // 3
        n = maxn
        p = [i for i in range(n)]
        random.shuffle(p)
        edges = []
        for i in range(1, n0):
            edges.append([p[i - 1] + 1, p[i] + 1])
        for i in range(n0, n):
            j = random.randint(0, i - 1)
            edges.append([p[i] + 1, p[j] + 1])
        self.print_test(n, edges, 'almost bamboo, n = %d' % n)

    def random_component(self, vp, n, me, ec, add):
        p = [i for i in range(n)]
        random.shuffle(p)
        edges = []
        vs = []
        for i in range(n):
            vs.append(vp[i + add])
        used = set()
        for i in range(1, n):
            j = random.randint(0, i - 1)
            v = vp[p[j] + add]
            u = vp[p[i] + add]
            edges.append([v, u])
            used.add((p[j], p[i]))
            used.add((p[i], p[j]))
        es = random.randint(0, min(n * (n - 1) // 2 - (n - 1), ec - (n - 1) - me))
        for e in range(es):
            v = random.randint(0, n - 1)
            u = random.randint(0, n - 1)
            while v == u or (v, u) in used:
                v = random.randint(0, n - 1)
                u = random.randint(0, n - 1)
            used.add((v, u))
            used.add((u, v))
            edges.append([vp[v + add], vp[u + add]])
        return vs, edges

    def generate_components_tree(self, maxn, maxm):
        vs = []
        n = maxn
        ec = maxm
        oldn = n
        add = 0
        edges = []
        vp = [(i + 1) for i in range(n)]
        random.shuffle(vp)
        while n > 0:
            m = random.randint(1, n)
            n -= m
            vertices, es = self.random_component(vp, m, n, ec, add)
            ec -= len(es) + 1
            vs.append(vertices)
            edges.extend(es)
            add += m
        p = [i for i in range(len(vs))]
        random.shuffle(p)
        for i in range(1, len(p)):
            j = random.randint(0, i - 1)
            V = vs[p[j]][random.randint(0, len(vs[p[j]]) - 1)]
            U = vs[p[i]][random.randint(0, len(vs[p[i]]) - 1)]
            edges.append([V, U])
        self.print_test(oldn, edges, 'big test, several components connected with bridges, n = %d, m = %d' % (oldn, len(edges)))

    def generate_many_small_components(self, maxn, maxm):
        if maxn * 2 > maxm:
            maxn //= 2
        n = maxn
        oldn = n
        p = [(i + 1) for i in range(n)]
        random.shuffle(p)
        edges = []
        add = 0
        ec = maxm
        vs = []
        while n > 0:
            m = random.randint(3, 4)
            n -= m
            if n < 3:
                m += n
                n = 0
            vertices, es = self.random_component(p, m, n, ec, add)
            ec -= len(es) + 1
            vs.append(vertices)
            edges.extend(es)
            add += m
        for i in range(1, len(vs)):
            edges.append([vs[0][-1], vs[i][0]])
        self.print_test(oldn, edges, 'many small components n = %d, m = %d' % (n, len(edges)))

    def generate_hand_tests(self):
        pass

    def generate_first_group_of_tests(self, folder):
        self.subtask = folder
        ns = [4, 5, 6, 7, 8, 9, 10]
        self.generate_hand_tests()
        for n in ns:
            self.generate_random_graph(n, 45)
        self.generate_bamboo(10)
        self.generate_bamboo_with_trash(10)
        self.generate_bamboo_with_edge(10)
        self.generate_random_tree(10)
        for i in range(3):
            self.generate_components_tree(10, 45)
        self.generate_cycle(10)
        self.generate_many_small_components(10, 45)

    def generate_second_group_of_tests(self, folder):
        self.subtask = folder
        ns = [5, 10, 20, 100, 500, 1000, 10**4, 5 * 10**4, 2 * 10**5]
        for n in ns:
            self.generate_random_tree(n)
        self.generate_bamboo(2 * 10**5)

    def generate_third_group_of_tests(self, folder):
        self.subtask = folder
        ns = [5, 10, 20, 100, 500, 1000]
        for n in ns:
            self.generate_random_graph(n, 5000)
        self.generate_bamboo(1000)
        self.generate_bamboo_with_trash(1000)
        self.generate_bamboo_with_edge(1000)
        for n in ns:
            self.generate_components_tree(n, 5000)
        self.generate_cycle(1000)
        self.generate_many_small_components(1000, 5000)
        
    def generate_fourth_group_of_tests(self, folder):
        self.subtask = folder
        ns = [5, 10, 20, 100, 500, 1000, 10**4, 5 * 10**4, 2 * 10**5]
        for n in ns:
            self.generate_random_graph(n, 2 * 10**5)
        self.generate_bamboo(2 * 10**5)
        self.generate_bamboo_with_trash(2 * 10**5)
        self.generate_bamboo_with_edge(2 * 10**5)
        for n in ns:
            self.generate_components_tree(n, 2 * 10**5)
        self.generate_cycle(2 * 10**5)
        self.generate_many_small_components(2 * 10**5, 2 * 10**5)

    def print_test(self, n, edges, description, dirname='tests'):
        self.test_number += 1
        print('Generating test %d: n = %d, m = %d' % (self.test_number, n, len(edges)))
        
        folder = '../' + dirname + self.subtask
        test_name = folder + '/{0:0=2d}'.format(self.test_number)
        if not os.path.exists(folder):
            os.makedirs(folder)
        test_file = open(test_name, 'w')
        print(n, len(edges), file=test_file)
        if dirname == 'tests':
            random.shuffle(edges)
        print('\n'.join(' '.join(str(element) for element in edge) for edge in edges), file=test_file)
        print(test_name + ' ' + description, file=self.desc_file)
        test_file.close()

    def generate_all_tests(self):
        self.generate_samples() 
        self.test_number = 0
        
        #First group of tests
        self.generate_first_group_of_tests('/subtask1')
        
        #Second group of tests
        self.generate_second_group_of_tests('/subtask2')
        
        #Third group of tests
        self.generate_third_group_of_tests('/subtask3')

        #Fourth group of tests
        self.generate_fourth_group_of_tests('/subtask4')

import os, shutil
if os.path.exists('../tests'):
    shutil.rmtree('../tests', ignore_errors=False, onerror=None)
if os.path.exists('../preliminary'):
    shutil.rmtree('../preliminary', ignore_errors=False, onerror=None)
try:
    os.makedirs('../tests')
    os.makedirs('../preliminary')
except OSError:
    pass

import time
start = time.time()
writer = TestGen()
writer.generate_all_tests()
finish = time.time()
print('All tests were generated. Elapsed time: ', finish - start)
