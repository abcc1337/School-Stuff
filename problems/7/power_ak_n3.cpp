#include <fstream>
#include <vector>

using namespace std;

int main()
{
  ifstream in("power.in");
  ofstream out("power.out");
  int n, k;
  in >> n >> k;
  vector<int> x(n), y(n);
  for (int i = 0; i < n; i++)
    in >> x[i] >> y[i];
  long long best = 0;
  for (int i = 0; i < n; i++)
    {
      for (int j = 0; j < n; j++)
        {
          int count = 0;
          for (int k = 0; k < n; k++)
            count += x[k] >= x[i] && y[k] >= y[j];
          if (count >= k)
            best = max(best, x[i] * (long long)y[j]);
        }
    }
  out << best << "\n";
}
