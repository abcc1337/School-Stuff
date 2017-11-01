import random
import os
import shutil
import math

num_of_subtasks = 4

if os.path.exists('../tests'):
    print('Directory "tests" already exists')
    shutil.rmtree('../tests', ignore_errors=False, onerror=None)

try:
    os.makedirs('../tests')
except OSError:
    pass

for i in range(num_of_subtasks):
    name = "subtask" + str(i + 1)
    try:
        os.makedirs('../tests/' + name)
    except OSError:
        pass

if os.path.exists('../preliminary'):
    print('Directory "preliminary" already exists')
    shutil.rmtree('../preliminary', ignore_errors=False, onerror=None)

try:
    os.makedirs('../preliminary')
except OSError:
    pass

descf = open("../tests.desc", "w")

random.seed(239)

test_number = 1

def write_test(n, a, b, c, subtask, desc=""):
    global test_number
    name = "0" * (2 - len(str(test_number))) + str(test_number)

    adr = ""

    if (subtask == 0):
        adr = "../preliminary/" + name
    else:
        adr = "../tests/subtask" + str(subtask) + "/" + name

    outp = open(adr, "w")

    descf.write(adr + " n = %d, a = %d, b = %d, c = %d;" % (n, a, b, c) + " " + desc + "\n")

    outp.write(str(n) + " " + str(a) + " " + str(b) + " " + str(c) + "\n")

    if (subtask == 0):
        print("Preliminary test #" + str(test_number) + " has been generated")
    else:
        print("Test #" + str(test_number) + " has been generated")

    test_number += 1
    outp.close()



write_test(72, 2, 1, 1, 0, "example")

test_number = 1

def gen_random(maxn, maxa, maxb, maxc, subtask):
    write_test(random.randint(1, maxn), random.randint(1, maxa), random.randint(1, maxb), random.randint(1, maxc), subtask, "")

def gen_pow(maxn, subtask, maxa, maxb, maxc, maxsum):
    end = random.randint(0, 1000) * random.randint(0, 1000) // 1000 + 10
    tmp = int(math.log(maxn / end, 2)) - 1
    a = 0
    b = 0
    c = 0
    for i in range(tmp):
        if (a + b + c == maxsum or (a == maxa and b == maxb and c == maxc)):
            break
        
        arr = []
        if (a < maxa):
            arr.append(0)
        if (b < maxb):
            arr.append(1)
        if (c < maxc):
            arr.append(2)
        r = random.choice(arr)
        
        if (r == 0):
            a += 1
            end = end * 2;
        elif (r == 1):
            b += 1
            end = end * 2 - 1
        else:
            c += 1
            end = end * 2 + 1
    write_test(end, a, b, c, subtask, "random test")

write_test(57, 0, 2, 4, 1, "handmade test")
write_test(10 ** 9, 7, 0, 0, 1, "handmade test")
write_test(10 ** 9, 0, 7, 0, 1, "handmade test")
write_test(10 ** 9, 0, 0, 7, 1, "handmade test")
write_test(10 ** 9, 2, 2, 2, 1, "handmade test")
write_test(10 ** 9, 4, 2, 1, 1, "handmade test")
write_test(10 ** 9, 2, 4, 1, 1, "handmade test")
now = 10 ** 9
for i in range(5):
    gen_pow(now, 1, 7, 7, 7, 7)
    gen_pow(now, 1, 0, 7, 7, 7)
    gen_pow(now, 1, 7, 0, 7, 7)
    gen_pow(now, 1, 7, 7, 0, 7)
    gen_pow(now, 1, 0, 7, 7, 7)
    now //= 2

gen_pow(10**9, 1, 7, 0, 0, 7)
gen_pow(10**9, 1, 0, 7, 0, 7)
gen_pow(10**9, 1, 0, 0, 7, 7)

now = 10 ** 18
for i in range(10):
    gen_pow(now, 2, 60, 60, 0, 100500)
    now //= 2

gen_pow(10**18, 2, 60, 0, 0, 100500)
gen_pow(10**18, 2, 0, 60, 0, 100500)

now = 10 ** 18
for i in range(10):
    gen_pow(now, 3, 60, 0, 60, 100500)
    now //= 2

gen_pow(10**18, 3, 60, 0, 0, 100500)
gen_pow(10**18, 3, 0, 0, 60, 100500)

now = 10 ** 18
for i in range(10):
    gen_pow(now, 4, 60, 60, 60, 100500)
    now //= 2

gen_pow(10**18, 4, 60, 0, 0, 100500)
gen_pow(10**18, 4, 0, 60, 0, 100500)
gen_pow(10**18, 4, 0, 0, 60, 100500)
gen_pow(10**18, 4, 60, 60, 0, 100500)
gen_pow(10**18, 4, 60, 0, 60, 100500)
gen_pow(10**18, 4, 0, 60, 60, 100500)
