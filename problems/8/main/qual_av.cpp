#include <bits/stdc++.h>

using namespace std;

typedef pair<int, int> segment;

int const MAX_N = 200000;

int n;
vector<int> tree[MAX_N];
vector<int> verticesByDepth[MAX_N];
vector<segment> levelSegments[MAX_N];

int dfsTime = 0;
int timeIn[MAX_N], timeOut[MAX_N], depth[MAX_N];

void dfs(int u, int d) {
	timeIn[u] = dfsTime++;
	depth[u] = d;
	verticesByDepth[d].push_back(u);
	for (int t = 0; t < (int) tree[u].size(); t++) {
		dfs(tree[u][t], d + 1);
	}
	timeOut[u] = dfsTime;
}

void initializeTree() {
	for (int i = 1; i < n; i++) {
		int parent;
		scanf("%d", &parent);
		tree[parent - 1].push_back(i);
	}
	dfs(0, 0);
}

void removeIncluded() {
	for (int level = 0; level < n; level++) {
		std::sort(levelSegments[level].begin(), levelSegments[level].end(), 
			[](const segment &o1, const segment &o2) {
		    	if (o1.first == o2.first) {
		    		return o1.second > o2.second;
		    	} else {
		    		return o1.first < o2.first;
		    	}
			}
		);
		vector<segment> result;
		for (segment s : levelSegments[level]) {
			while (result.size() > 0 && result.back().second >= s.second) {
				result.pop_back();
			}
			result.push_back(s);
		}
		levelSegments[level] = result;
	}
}

segment findSegment(int depth, int tIn, int tOut) {
	int from, to;
	{
		int left = -1, right = (int) verticesByDepth[depth].size();
		while (left < right - 1) {
			int mid = (left + right) / 2;
			if (timeIn[verticesByDepth[depth][mid]] >= tIn) {
				right = mid;
			} else {
				left = mid;
			}
		}
		from = right;
	}
	{
		int left = -1, right = (int) verticesByDepth[depth].size();
		while (left < right - 1) {
			int mid = (left + right) / 2;
			if (timeOut[verticesByDepth[depth][mid]] <= tOut) {
				left = mid;
			} else {
				right = mid;
			}
		}
		to = left;
	}
	return segment(from, to);
}

void findBestSegment() {
	vector<int> color(n, -1);
	int colors = 0;
	for (int i = 0; i < n; i++) {
		for (segment s : levelSegments[i]) {
			// cerr << i << " " << s.first << " " << s.second << endl;
			for (int j = s.first; j <= s.second; j++) {
				color[verticesByDepth[i][j]] = colors;
			}
			colors++;
		}
	}
	vector<int> colorCount(colors);
	int curColors = 0;
	int r = 0;
	int bestL = 0, bestR = n;
	for (int l = 0; l < n; l++) {
		while (r < n && curColors < colors) {
			if (color[r] != -1) {
				if (colorCount[color[r]] == 0) {
					curColors++;
				}
				colorCount[color[r]]++;
			}
			r++;
		}
		if (curColors == colors) {
			if (r - l < bestR - bestL) {
				bestL = l;
				bestR = r;
			}
		}
		if (color[l] != -1) {
			colorCount[color[l]]--;
			if (colorCount[color[l]] == 0) {
				curColors--;
			}
		}
	}
	printf("%d %d\n", bestL + 1, bestR);
}

int main() {
	freopen("qual.in", "r", stdin);
	freopen("qual.out", "w", stdout);

	scanf("%d", &n);
	initializeTree();

	int m;
	scanf("%d", &m);
	for (int i = 0; i < m; i++) {
		int u, k;
		scanf("%d%d", &u, &k);
		u--;
		levelSegments[depth[u] + k].push_back(findSegment(depth[u] + k, timeIn[u], timeOut[u]));
	}
	removeIncluded();
	findBestSegment();
	return 0;
}
