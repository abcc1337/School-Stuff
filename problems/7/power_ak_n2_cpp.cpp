#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <tuple>
#include <functional>

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
  sort(a.begin(), a.end(), [](const tuple<int,int>& x, const tuple<int,int>& y) {
      return get<0>(x) > get<0>(y);
    });
  vector<int> v;
  for (int i = 0; i < k - 1; i++)
    v.push_back(get<1>(a[i]));
  long long best = 0;
  for (int i = k - 1; i < n; i++)
    {
      v.push_back(get<1>(a[i]));
      long long min_x = get<0>(a[i]);
      nth_element(v.begin(), v.begin() + (k - 1), v.end(), greater<int>());
      long long min_y = v[k - 1];
      best = max(best, min_x * min_y);
    }
  out << best << "\n";
}
