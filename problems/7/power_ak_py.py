from heapq import *
fin = open("power.in")
n, k = map(int, fin.readline().split())
a = [tuple(map(int, fin.readline().split())) for _ in range(n)]
a = sorted(a)[::-1]
h = [x[1] for x in a[:k]]
heapify(h)
best = h[0] * a[k-1][0]
for x, y in a[k:]:
    heappush(h, y)
    heappop(h)
    best = max(best, x * h[0])
print(best, file=open("power.out", "w"))
