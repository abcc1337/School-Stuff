inf = open("delivery.in", "r")
ouf = open("delivery.out", "w")

k = int(inf.readline())
x = int(inf.readline())
y = int(inf.readline())

cnt = y // x

lo = cnt * x
hi = cnt * (x + k - 1)

if lo <= y <= hi:
    ans = y
else:
    ans = (cnt + 1) * x

print(ans, file=ouf)