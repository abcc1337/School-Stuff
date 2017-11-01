#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <tuple>
#include <queue>

using namespace std;

int main()
{
  ifstream in("power.in");
  ofstream out("power.out");
  int n, k;
  in >> n >> k;
  vector<tuple<int, int>> a(n);
  for (int i = 0; i < n; i++)
    in >> get<0>(a[i]) >> get<1>(a[i]);
  sort(a.begin(), a.end(), greater<tuple<int, int>>());
  priority_queue<int, vector<int>, greater<int>> q;
  for (int i = 0; i < k; i++)
    q.push(get<1>(a[i]));
  long long best = q.top() * (long long)get<0>(a[k - 1]);
  for (int i = k; i < n; i++)
    {
      q.push(get<1>(a[i]));
      q.pop();
      best = max(best, get<0>(a[i]) * (long long)q.top());
    }
  out << best << "\n";
}
