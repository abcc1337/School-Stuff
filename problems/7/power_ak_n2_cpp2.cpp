#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <tuple>
#include <functional>

using namespace std;

size_t k;
void insert(vector<int>& v, int val)
{
  auto pos = lower_bound(v.begin(), v.end(), val, greater<int>());
  v.insert(pos, val);
  while (v.size() > k)
    v.pop_back();
}

int main()
{
  ifstream in("power.in");
  ofstream out("power.out");
  int n;
  in >> n >> k;
  vector<tuple<int, int>> a(n);
  for (int i = 0; i < n; i++)
    in >> get<0>(a[i]) >> get<1>(a[i]);
  sort(a.begin(), a.end(), [](const tuple<int,int>& x, const tuple<int,int>& y) {
      return get<0>(x) > get<0>(y);
    });
  vector<int> v;
  for (size_t i = 0; i < k - 1; i++)
    insert(v, get<1>(a[i]));
  long long best = 0;
  for (int i = k - 1; i < n; i++)
    {
      insert(v, get<1>(a[i]));
      long long min_x = get<0>(a[i]);
      long long min_y = v[k - 1];
      best = max(best, min_x * min_y);
    }
  out << best << "\n";
}
