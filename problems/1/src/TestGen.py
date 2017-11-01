import random
import math
random.seed("memory limit test #56")

def rnd(a, b):
	return random.randint(a, b)

class TestGen:
	def __init__(self):
		self.test_number = 0
		self.subtask = ""
		self.desc_file = open("../tests.desc", "w")

	def generate_samples(self):			 
		self.print_test(7, 3, 2, 3, [1, 19, 20, 50], "preliminary")

	def generate_first_group_of_tests(self, folder):
		MAX_N = 10
		MAX_XY = 10
		MAX_Q = 1
		MAX_A = 100
		self.subtask = folder
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N//2), -1, rnd(1, MAX_XY//2), rnd(1, MAX_XY//2), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N//2), -1, rnd(1, MAX_XY//2), rnd(1, MAX_XY//2), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N//2), -1, rnd(1, MAX_XY//2), rnd(1, MAX_XY//2), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N//2), -1, rnd(1, MAX_XY//2), rnd(1, MAX_XY//2), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N//2), -1, rnd(1, MAX_XY//2), rnd(1, MAX_XY//2), MAX_Q, MAX_A))

	def generate_second_group_of_tests(self, folder):
		MAX_N = 10**7
		MAX_XY = 10**9
		MAX_Q = 1
		MAX_A = 10**7
		self.subtask = folder
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), rnd(1, 100), rnd(1, 1000), rnd(1, 100), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), rnd(1, 100), rnd(1, 1000), rnd(1, 100), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), rnd(1, 10000), rnd(1, 1000), rnd(1, 100), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, MAX_N), rnd(1, 1000000), rnd(1, 1000), rnd(1, 100), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, 1000), -1, rnd(1, 1000), rnd(1, 1000), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, 1000), -1, rnd(1, 1000), rnd(1, 1000), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, 1000), -1, rnd(1, 1000000000), rnd(1, 1000), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, 1000), -1, rnd(1, 1000000000), rnd(1, 1000), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1, 1000), -1, rnd(1, 1000000000), rnd(1, 1000000000), MAX_Q, MAX_A))

	def generate_third_group_of_tests(self, folder):
		MAX_N = 10**9
		MAX_XY = 10**9
		MAX_Q = 1000
		MAX_A = 10**18
		self.subtask = folder
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), -1, MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1000, 2000), rnd(1, 200), rnd(1, 100), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(1000, 2000), rnd(1, 200), rnd(1, 100), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(1, 1, rnd(1, 1000000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(10, 4, rnd(1, 1000000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(100, 4, rnd(1, 1000000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(301, 7, rnd(1, 1000000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10000, 20000), rnd(1, 200), rnd(1, 1000000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10000, 20000), rnd(200, 1000), rnd(1, 100000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10000, 20000), rnd(5000, 20000), rnd(1, 1000000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**6, 2*10**6), rnd(1, 10), rnd(1, 10000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**6, 2*10**6), rnd(10, 100), rnd(1, 1000000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**6, 2*10**6), rnd(100, 1000), rnd(1, 1000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(1, 5), rnd(1, 10000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(5, 100), rnd(1, 10000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(10000, 10**6), rnd(1, 10000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(10**8, 10**9), rnd(1, 10000), -1, rnd(MAX_Q-20, MAX_Q), MAX_A))

	def generate_fourth_group_of_tests(self, folder):
		MAX_N = 10**9
		MAX_XY = 10**9
		MAX_Q = 1000
		MAX_A = 10**18
		self.subtask = folder
		self.print_test(*self.generate_test(rnd(1, MAX_N), -1, rnd(1, MAX_XY), rnd(1, MAX_XY), MAX_Q, MAX_A))
		self.print_test(*self.generate_test(rnd(1000, 2000), rnd(1, 200), rnd(1, 100), rnd(1, 100), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(1000, 2000), rnd(1, 200), rnd(1, 100), rnd(1, 100), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(1, 1, rnd(1, 1000000), rnd(1, 1000000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(10, 4, rnd(1, 1000000), rnd(1, 1000000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(100, 4, rnd(1, 1000000), rnd(1, 1000000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(301, 7, rnd(1, 1000000), rnd(1, 1000000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10000, 20000), rnd(1, 200), rnd(1, 1000000), rnd(1, 1000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10000, 20000), rnd(200, 1000), rnd(1, 100000), rnd(1, 1000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10000, 20000), rnd(5000, 20000), rnd(1, 1000000), rnd(1, 1000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**6, 2*10**6), rnd(1, 10), rnd(1, 10000), rnd(1, 10000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**6, 2*10**6), rnd(10, 100), rnd(1, 1000000), rnd(1, 1000000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**6, 2*10**6), rnd(100, 1000), rnd(1, 1000), rnd(1, 1000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(1, 5), rnd(1, 10000), rnd(1, 10000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(5, 100), rnd(1, 10000), rnd(1, 10000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(10000, 10**6), rnd(1, 10000), rnd(1, 10000), rnd(MAX_Q-20, MAX_Q), MAX_A))
		self.print_test(*self.generate_test(rnd(10**8, 10**9), rnd(10**8, 10**9), rnd(1, 10000), rnd(1, 10000), rnd(MAX_Q-20, MAX_Q), MAX_A))

	def generate_test(self, n, k, x, y, q, maxa):
		if k < 1 or k > n:
			k = rnd(1, n)
		if y < 0:
			y = x
		return n, k, x, y, [self.gen_a(n, k, x, y, maxa) for i in range(q)]

	def gen_a(self, n, k, x, y, maxa):
		pc = n*y + (n//k)*(x-y)
		p = rnd(0, (maxa - 1) // pc)
		a = p * pc
			
		maxfloor = n
		while True:
			r = rnd(0, 7)
			if r == 0:
				floor = 1
			elif r == 1:
				floor = maxfloor
			elif r == 2 and maxfloor >= k:
				floor = rnd(1, maxfloor//k) * k
			else:
				floor = rnd(1, maxfloor)
			na = a + (floor-1)*y + ((floor-1)//k)*(x-y)
			if na >= maxa:
				maxfloor = floor - 1
				continue
			break
		a = na

		r = rnd(0, 5)
		if r == 0:
			a += 0
		elif r == 1 and a + (x if floor % k == 0 else y) < maxa:
			a += (x if floor % k == 0 else y) - 1
		else:
			a += rnd(0, min((x if floor % k == 0 else y), maxa - a) - 1)
		return a + 1


	def print_test(self, n, k, x, y, a, dirname="tests"):
		self.test_number += 1
		print("Generating test %d" % self.test_number)

		q = len(a)
		description = "n = %d, k = %d, x = %d, y = %d, q = %d, maxa = %d" % (n, k, x, y, q, max(a))
		
		folder = "../" + dirname + self.subtask
		test_name = folder + "/{0:0=2d}".format(self.test_number)
		if not os.path.exists(folder):
			os.makedirs(folder)
		test_file = open(test_name, "w")
		print(n, k, x, y, file=test_file)
		print(q, file=test_file)
		print(*a, file=test_file)
		print(test_name + "\t" + description, file=self.desc_file)
		test_file.close()

	def generate_all_tests(self):
		self.generate_samples() 
		self.test_number = 0
		
		self.generate_first_group_of_tests("/subtask1")
		self.generate_second_group_of_tests("/subtask2")
		self.generate_third_group_of_tests("/subtask3")
		self.generate_fourth_group_of_tests("/subtask4")


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

