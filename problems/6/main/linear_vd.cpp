#include "bits/stdc++.h"
#define TASK_NAME "linear"

using namespace std;

int main() {
    freopen(TASK_NAME ".in", "r", stdin);
    freopen(TASK_NAME ".out", "w", stdout);
    
    int n; scanf("%d", &n);

    vector<int> x, d;
	
    for (int i = 0; i < n; i++)
    {
        int xi, di; scanf("%d %d", &xi, &di);
        x.push_back(xi);
        d.push_back(di);
    }

    vector<int> xStack, dStack, times;
    for (int i = 0; i < n; i++)
    {
        xStack.push_back(x[i]);
        dStack.push_back(d[i]);
        if (xStack.size() > 1 && (dStack[dStack.size() - 2] - dStack[dStack.size() - 1] == 2))
        {
            times.push_back(xStack[xStack.size() - 1] - xStack[xStack.size() - 2]);
            xStack.pop_back(); xStack.pop_back(); dStack.pop_back(); dStack.pop_back();
        }
    }
    
    sort(times.begin(), times.end());

    int m; scanf("%d", &m);
    size_t index = 0;
    for (int i = 0; i < m; i++)
    {
        int query; scanf("%d", &query);
	while (index < times.size() && times[index] <= 2*query) index++;
	printf("%d\n", (int)(n - 2*index));		
    }
    return 0;
}