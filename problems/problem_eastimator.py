# coding: utf-8
import os
from sys import platform
from collections import Iterable

# Determine current system
SYSTEM = 'cls' if 'win' in platform else 'clear'


class WrongTestError(ValueError):
    pass


class Solver(object):
    """
    Simple task wrapper.

    Any provided function must accept a list of args and return answer as a list.
    """

    def __init__(self, solver):
        self.solver = solver

    def __call__(self, *args, **kwargs):
        return self.solver(*args, **kwargs)


class Estimator(object):
    MAX_TASKS = 8
    BASE_PATH = 'problems/%d/tests'

    def __init__(self, task=None, solution: (str, Solver)=None):
        self.solution = solution
        self.task = task

    def estimate(self, task=None, solution=None, silent=True):
        if task is None:
            assert(self.task is not None)
            task = self.task
        if solution is None:
            assert(self.solution is not None)
            solution = self.solution

        if not isinstance(solution, Solver):
            raise ValueError("Provided solution is not a file or Solver object: %s" % str(solution))

        self._estimate(task, solution, silent)

    @staticmethod
    def _estimate(task, solution, silent=True):

        # TODO: Replace 0 with 1
        if task < 0 or task > Estimator.MAX_TASKS:
            raise ValueError("Provided task %d less than zero or "
                             "greater than current MAX_TASKS (%d)" % (task, Estimator.MAX_TASKS))

        if isinstance(solution, str):
            if not os.path.exists(solution):
                raise ValueError("Path %s does not exists." % solution)
            elif not os.path.isfile(str):
                raise ValueError("Path %s is not a file." % solution)

        taskfile = Estimator._fileopen(task)
        if isinstance(solution, str):
            Estimator._file_test(taskfile, solution, silent)
        else:
            Estimator._callback_test(taskfile, solution, silent)

    @staticmethod
    def _callback_test(task: Iterable, callback: Solver, silent: bool):
        for i, entry in enumerate(task):
            tsk, ans, n_tests, tskt = entry
            if not silent:
                prc = (i + 1) / n_tests * 100
                bar = '=' * (int(prc) - 1) + '>' + (100 - int(prc)) * ' '
                os.system(SYSTEM)
                print("[%s] | %d/%d (%.2f%%)" % (bar, i + 1, n_tests, prc))

            try:
                user_ans = callback(tsk)
                if (user_ans != ans):
                    raise WrongTestError("Test %d [%s] failed." % (i + 1, tskt))
            except Exception as e:
                raise WrongTestError("Test %d [%s] failed with a runtime error: `%s`" % (i + 1, tskt, e))

    @staticmethod
    def _file_test(task, file, silent: bool):
        pass

    @staticmethod
    def _cleanlines(lines):
        return [l.replace('\n', '') for l in lines]

    @staticmethod
    def _fileopen(task):
        try:
            bp = Estimator.BASE_PATH % task
            files = sorted(os.listdir(bp))
            n_tests = len(files) // 2
            for tsk, ans in zip(files[0::2], files[1::2]):
                with open(bp + '/' + tsk, 'r') as tskf:
                    tlines = tskf.readlines()

                with open(bp + '/' + ans, 'r') as ansf:
                    alines = ansf.readlines()

                yield (Estimator._cleanlines(tlines),
                       Estimator._cleanlines(alines),
                       n_tests,
                       tsk)

        except FileNotFoundError as e:
            raise FileNotFoundError("%s with BASE_PATH=\'%s\'" % (e, Estimator.BASE_PATH))


def make_tests():
    import random
    for i in range(1, 101):
        name = ('0' + str(i) if i < 10 else str(i))
        with open('0/tests/%s' % name, 'w') as f:
            with open('0/tests/%s' % name + '.a', 'w') as ff:
                for _ in range(10):
                    n = random.randint(0, 10 ** 5)
                    n = str(n)
                    f.write(n)
                    ff.write(n)
                f.write('\n')
                ff.write('\n')


def zero(n):
    n = int(n[0])
    return [str(n)]

if __name__ == "__main__":
    from .. import a1
    zr = Solver(a1.solver)
    e = Estimator(2, zr)
    e.estimate(silent=False)

    # for t, a in getattr(Estimator, '_fileopen')(1):
    #     print(t, a)
