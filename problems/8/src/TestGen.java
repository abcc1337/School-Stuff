import java.io.*;
import java.util.*;

public class TestGen implements Runnable {

	final static Random rng = new Random("qual".hashCode());
	final static File testsDesc = new File("../tests.desc");
	final static int MAX_N = 200_000;
	final static int MAX_M = 200_000;

	int testCount = 0;
	boolean isPreliminary = false;
	int currentSubtask = 0;

	public void run() {
		try {
			testsDesc.delete();
			testsDesc.createNewFile();
		} catch (IOException e) {
			System.err.println("Could not recreate file for tests descriptions");
		}
		isPreliminary = true;
		genPreliminary();
		isPreliminary = false;
		genSubtask1();
		genSubtask2();
		genSubtask3();
		genSubtask4();
	}

	void genPreliminary() {
		TestCase sample = new TestCase(new int[] { 0, 0, 0, 1, 1, 2, 2 });
		sample.addQuery(0, 1).addQuery(2, 1).addQuery(0, 2).addComment("Sample test");
		printTest(sample);
		testCount = 0;
	}

	void genSubtask1() {
		this.currentSubtask = 1;
		int MAX_N = 50;

		printRandom(2, 2);
		printRandom(3, 2);
		printRandom(5, 3);
		for (int i = 10; i <= MAX_N; i += 10) {
			printRandom(i, i);
		}

		printTest(bamboo(MAX_N).randomQueries(MAX_N).addComment("Bamboo with random queries"));
		printTest(bamboo(MAX_N).consequentQueries().addComment("Bamboo with consequent queries"));
		printTest(bamboo(MAX_N).rootQueries(MAX_N).addComment("Bamboo with root queries"));
		printTest(fullBinary(MAX_N).randomQueries(MAX_N).addComment("Binary tree with random queries"));
		printTest(fullBinary(MAX_N).consequentQueries().addComment("Binary tree with consequent queries"));
		printTest(fullBinary(MAX_N).rootQueries(MAX_N).addComment("Binary tree with root queries"));

		printTest(random(2).randomQueries(MAX_N).addComment("2 vertices with 50 queries"));
		printTest(random(MAX_N).randomQueries(2).addComment("50 vertices with 2 queries"));

		printTest(pathStar(MAX_N, MAX_N / 2).rootQueries(MAX_N).addComment("25 vertices + star"));

		{
			TestCase tc = special(MAX_N / 3 * 3 - 2);
			printTest(tc.addComment("N/3 subtrees with 3 vertices, special enumeration"));
			tc = tc.shuffleTree();
			printTest(tc.addComment("N/3 subtrees with 3 vertices, shuffled tree"));
		}
		{
			TestCase tc = special(MAX_N / 3 * 3 + 1).shuffleTree().randomQueries(MAX_N);
			printTest(tc.addComment("N/3 subtrees with 3 vertices, shuffled enumeration, random queries"));
		}

		{
			TestCase tc = kAryTree(1, 2, 3);
			tc.addQuery(0, 3);
			tc.addQuery(0, 1);
			tc.reverseOnDepth(2);
			printTest(tc.addComment("1x1x2x3 tree, all vertices on depth 2 reversed"));
		}
		{
			TestCase tc = kAryTree(1, 6, 6);
			tc.addQuery(0, 1);
			tc.addQuery(0, 3);
			tc.shuffleOnDepth(2);
			printTest(tc.addComment("1x1x6x6 tree, all vertices on depth 2 shuffled"));
		}
		{
			TestCase tc = kAryTree(1, 2, 3, 4);
			tc.addQuery(0, 4);
			tc.addQuery(0, 2);
			tc.reverseOnDepth(3);
			tc.shuffleOnDepth(4);
			printTest(tc.addComment("1x1x2x3x4 tree, all vertices on depth 3-4 reversed"));
		}
		{
			TestCase tc = kAryTree(3, 3, 3);
			for (int i = 0; i < 3; i++) {
				if (rng.nextBoolean()) {
					tc.addQuery(1 + i, 2);
				}
			}
			tc.addQuery(0, 3);
			tc.shuffleOnDepth(1);
			tc.reverseOnDepth(2);
			tc.shuffleOnDepth(3);
			printTest(tc.addComment("1x3x3x3 tree, all vertices on depth 1..3 shuffled, random queries on depth 1-2"));
		}
		{
			TestCase tc = kAryTree(3, 3, 3);
			for (int i = 0; i < 9; i++) {
				if (rng.nextBoolean()) {
					tc.addQuery(1 + 3 + i, 1);
				}
				tc.addQuery(0, 2);
			}
			tc.reverseOnDepth(1);
			tc.reverseOnDepth(2);
			tc.reverseOnDepth(3);
			printTest(tc.shuffleTree().addComment(
					"1x3x3x3 shuffled tree, all vertices on depth 1..3 shuffled, random queries on depth 2"));
		}
		{
			TestCase tc = kAryTree(3, 3, 3);
			for (int i = 0; i < 3; i++) {
				tc.addQuery(1 + i, 2);
			}
			tc.reverseOnDepth(1);
			tc.shuffleOnDepth(2);
			tc.reverseOnDepth(3);
			printTest(tc.addComment("1x3x3x3 tree, all vertices on depth 1..3 shuffled"));
		}
	}

	void genSubtask2() {
		this.currentSubtask = 2;
		int MAX_N = 3000;

		for (int i = 500; i <= MAX_N; i += 500) {
			printRandom(i, i);
		}

		printTest(bamboo(MAX_N).randomQueries(MAX_N).addComment("Bamboo with random queries"));
		printTest(bamboo(MAX_N).consequentQueries().addComment("Bamboo with consequent queries"));
		printTest(bamboo(MAX_N).rootQueries(MAX_N).addComment("Bamboo with root queries"));
		printTest(fullBinary(MAX_N).randomQueries(MAX_N).addComment("Binary tree with random queries"));
		printTest(fullBinary(MAX_N).consequentQueries().addComment("Binary tree with consequent queries"));
		printTest(fullBinary(MAX_N).rootQueries(MAX_N).addComment("Binary tree with root queries"));

		printTest(random(2).randomQueries(MAX_N).addComment("2 vertices with 3000 queries"));

		printTest(pathStar(MAX_N, MAX_N / 3).rootQueries(MAX_N).addComment("1000 vertices + star"));

		{
			TestCase tc = special(MAX_N / 3 * 3 - 2);
			printTest(tc.addComment("N/3 subtrees with 3 vertices, special enumeration"));
			tc = tc.shuffleTree();
			printTest(tc.addComment("N/3 subtrees with 3 vertices, shuffled tree"));
		}
		{
			TestCase tc = special(MAX_N / 3 * 3 - 2).shuffleTree().randomQueries(MAX_N);
			printTest(tc.addComment("N/3 subtrees with 3 vertices, shuffled tree + random queries"));
		}

		{
			TestCase tc = kAryTree(1, 50, 50);
			tc.addQuery(0, 3);
			tc.addQuery(0, 1);
			tc.reverseOnDepth(2);
			printTest(tc.addComment("1x50x50 tree, all vertices on depth 2 shuffled"));
		}
		{
			TestCase tc = kAryTree(10, 10, 10);
			for (int i = 0; i < 10; i++) {
				tc.addQuery(1 + i, 1);
				tc.addQuery(1 + i, 2);
			}
			tc.addQuery(0, 3);
			tc.shuffleOnDepth(2);
			tc.shuffleOnDepth(3);
			printTest(tc.addComment("1x10x10x10 tree, all vertices on depth 2-3 shuffled"));
		}
		{
			TestCase tc = kAryTree(5, 5, 5, 5);
			for (int i = 0; i < 5; i++) {
				tc.addQuery(1 + i, 2);
			}
			tc.addQuery(0, 3);
			tc.reverseOnDepth(3);
			printTest(tc.addComment("1x5x5x5x5 tree, all vertices on depth 3 reversed"));
		}
		{
			TestCase tc = kAryTree(5, 5, 5, 5);
			for (int i = 0; i < 5 * 5; i++) {
				tc.addQuery(1 + 5 + i, 2);
				tc.addQuery(0, 4);
			}
			tc.reverseOnDepth(4);
			tc.shuffleOnDepth(3);
			printTest(tc.addComment("1x5x5x5x5 tree, all vertices on depth 3 reversed"));
		}
	}

	void genSubtask3() {
		this.currentSubtask = 3;

		printTest(bamboo(MAX_N).randomQueries(MAX_N).addComment("Bamboo with random queries"));
		printTest(bamboo(MAX_N).randomQueries(MAX_N).addComment("Bamboo with random queries"));
		printTest(bamboo(MAX_N).randomQueries(MAX_N).addComment("Bamboo with random queries"));
		printTest(bamboo(MAX_N).consequentQueries().addComment("Bamboo with consequent queries"));
		printTest(bamboo(MAX_N).rootQueries(MAX_N).addComment("Bamboo with root queries"));
		{
			TestCase tc = bamboo(MAX_N);
			for (int i = 0; i < MAX_N; i++) {
				tc.addQuery(0, MAX_N - 1);
			}
			printTest(tc.addComment("Bamboo with 200'000 max length queries"));
		}
	}

	void genSubtask4() {
		this.currentSubtask = 4;

		printRandom(MAX_N, MAX_N);
		printRandom(2, MAX_N);
		printRandom(MAX_N, 2);

		printTest(fullBinary(MAX_N).randomQueries(MAX_N).addComment("Binary tree with random queries"));
		printTest(fullBinary(MAX_N).consequentQueries().addComment("Binary tree with consequent queries"));
		printTest(fullBinary(MAX_N).rootQueries(MAX_N).addComment("Binary tree with root queries"));
		printTest(fullBinary(MAX_N).shuffleTree().rootQueries(MAX_N)
				.addComment("Binary tree (shuffled) with root queries"));

		printTest(random(2).randomQueries(MAX_N).addComment("2 vertices with 3000 queries"));

		printTest(pathStar(MAX_N, MAX_N / 2).randomQueries(MAX_N)
				.addComment("100000 vertices + star with random queries"));
		printTest(pathStar(MAX_N, MAX_N / 2).consequentQueries()
				.addComment("100000 vertices + star with all (x, 1) queries"));
		{
			TestCase tc = pathStar(MAX_N, MAX_N / 2);
			for (int i = 0; i < MAX_N; i++) {
				tc.addQuery(0, MAX_N / 2);
			}
			printTest(pathStar(MAX_N, MAX_N / 2).consequentQueries()
					.addComment("100000 vertices + star with all (root, depth) queries"));
		}
		{
			TestCase tc = pathStar(MAX_N, MAX_N / 2);
			for (int i = 0; i < MAX_N; i++) {
				tc.addQuery(i % (MAX_N / 2), MAX_N / 2 - (i % (MAX_N / 2)));
			}
			printTest(pathStar(MAX_N, MAX_N / 2).consequentQueries()
					.addComment("100000 vertices + star with all (i, depth - depth[i]) queries"));
		}
		{
			TestCase tc = pathStar(MAX_N, MAX_N / 2);
			for (int i = 0; i < MAX_M; i++) {
				tc.addQuery(0, MAX_N / 2);
			}
			printTest(tc.addComment("100000 vertices + star with many root queries"));
		}
		{
			TestCase tc = pathStar(MAX_N, MAX_N / 2);
			for (int step = 0; step < 2; step++) {
				for (int i = 0; i < MAX_N / 2; i++) {
					tc.addQuery(i, MAX_N / 2 - i);
				}
			}
			printTest(tc.addComment("100000 vertices + star with queries from all vertices on the stem"));
		}
		{
			TestCase tc = special(MAX_N / 3 * 3 - 2);
			printTest(tc.addComment("N/3 subtrees with 3 vertices, special enumeration"));
			tc = tc.shuffleTree();
			printTest(tc.addComment("N/3 subtrees with 3 vertices, shuffled tree"));
		}
		{
			TestCase tc = special(MAX_N / 3 * 3 + 1).shuffleTree().randomQueries(MAX_N);
			printTest(tc.addComment("N/3 subtrees with 3 vertices, shuffled enumeration, random queries"));
		}
		
		{
			TestCase tc = kAryTree(1, 1000, 90);
			for (int i = 0; i < MAX_M / 2; i++) {
				tc.addQuery(0, 1);
				tc.addQuery(0, 3);
			}
			tc.shuffleQueries();
			tc.shuffleOnDepth(2);
			printTest(tc.addComment("1x1000x90 tree, all vertices on depth 2 shuffled"));
		}
		{
			TestCase tc = kAryTree(100, 100, 10);
			for (int i = 0; i < MAX_N / 2 - 1; i++) {
				tc.addQuery(i % 10 + 1, 1);
				tc.addQuery(i % 10 + 1, 2);
			}
			tc.addQuery(0, 3);
			tc.shuffleOnDepth(2);
			tc.shuffleOnDepth(3);
			printTest(tc.addComment("1x100x100x10 tree, all vertices on depth 3 shuffled"));
		}
		{
			TestCase tc = kAryTree(5, 5, 5, 5, 5, 5);
			for (int i = 0; i < 5 * 5 * 5; i++) {
				tc.addQuery(1 + 5 + 5 * 5 + i, 3);
			}
			tc.shuffleOnDepth(6);
			printTest(tc.addComment("1x5x5x5x5x5x5 tree, all vertices on depth 6 shuffled"));
		}
		{
			TestCase tc = kAryTree(100, 100, 10);
			for (int i = 0; i < MAX_N; i++) {
				tc.addQuery(i % 10 + 1, 2);
			}
			tc.shuffleOnDepth(2);
			tc.shuffleOnDepth(3);
			printTest(tc.addComment("1x100x100x10 tree, all vertices on depth 2, 3 shuffled"));
		}
		{
			TestCase tc = kAryTree(5, 5, 5, 5, 5, 5, 4);
			for (int i = 0; i < 5 * 5; i++) {
				tc.addQuery(1 + 5 + i, 4);
			}
			for (int j = 1; j <= 6; j++) {
				tc.shuffleOnDepth(j);
			}
			printTest(tc.addComment("1x5x5x5x5x5x5x4 tree, all vertices on depth 1..6 shuffled"));
		}
		{
			TestCase tc = kAryTree(5, 5, 5, 5, 5, 5, 4);
			for (int i = 0; i < MAX_N; i++) {
				tc.addQuery(1 + 5 + i % (5 * 5), 5);
			}
			tc.shuffleQueries();
			for (int j = 1; j <= 7; j++) {
				tc.shuffleOnDepth(j);
			}
			printTest(tc.addComment("1x5x5x5x5x5x5x4 tree, all vertices on depth 1..7 shuffled"));
		}
		{
			TestCase tc = kAryTree(12, 9, 52, 19);
			for (int i = 0; i < MAX_N - 1; i++) {
				tc.addQuery(1 + i % 12, 2);
			}
			tc.addQuery(0, 3);
			tc.reverseOnDepth(3);
			tc.shuffleQueries();
			printTest(tc.addComment("1x12x9x52x19 tree, all vertices on depth 1 shuffled"));
		}
		{
			TestCase tc = kAryTree(123, 52, 11, 1);
			for (int i = 0; i < 123 * 52; i++) {
				tc.addQuery(1 + 123 + i % (123 * 52), 2);
				tc.addQuery(0, 4);
			}
			tc.reverseOnDepth(4);
			tc.shuffleOnDepth(3);
			tc.shuffleQueries();
			printTest(tc.addComment("1x123x52x11x1 tree, all vertices on depth 2 shuffled"));
		}
	}

	void printRandom(int n, int m) {
		TestCase tc = random(n).randomQueries(m).addComment("Random test");
		printTest(tc);
	}

	void printTest(TestCase tc) {
		String fileName = isPreliminary ? "../preliminary" : ("../tests/subtask" + currentSubtask);
		fileName += String.format("/%02d", ++testCount);
		appendComment(fileName, tc);
		File outputFile = new File(fileName);
		outputFile.getParentFile().mkdirs();

		try (PrintWriter out = new PrintWriter(outputFile)) {
			out.println(tc.n);
			for (int i = 1; i < tc.n; i++) {
				out.print(tc.parent[i] + 1);
				if (i + 1 < tc.n) {
					out.print(' ');
				} else {
					out.println();
				}
			}
			out.println(tc.us.size());
			for (int i = 0; i < tc.us.size(); i++) {
				out.println((tc.us.get(i) + 1) + " " + (tc.ks.get(i)));
			}
		} catch (IOException e) {
			System.err.println("Could not write to file " + fileName);
		}
		System.out.printf("Printed test %s, n = %d, m = %d\n", fileName, tc.n, tc.us.size());
	}

	void appendComment(String file, TestCase tc) {
		try (FileOutputStream fos = new FileOutputStream("../tests.desc", true);
				PrintWriter out = new PrintWriter(fos)) {
			out.printf("%s\tn = %d, m = %d\t%s\n", file, tc.n, tc.us.size(), tc.comment);
		} catch (IOException e) {
			System.err.println("Could not append commment for test " + file);
		}
	}

	TestCase random(int size) {
		int[] parent = new int[size];
		for (int i = 1; i < size; i++) {
			parent[i] = rng.nextInt(i);
		}
		return new TestCase(parent);
	}

	TestCase bamboo(int size) {
		int[] parent = new int[size];
		for (int i = 1; i < size; i++) {
			parent[i] = i - 1;
		}
		return new TestCase(parent);
	}

	TestCase fullBinary(int size) {
		int[] parent = new int[size];
		for (int i = 1; i < size; i++) {
			parent[i] = (i - 1) / 2;
		}
		return new TestCase(parent);
	}

	TestCase star(int size) {
		return new TestCase(new int[size]);
	}

	TestCase pathStar(int size, int pathLength) {
		int[] parent = new int[size];
		for (int i = 1; i <= pathLength; i++) {
			parent[i] = i - 1;
		}
		for (int i = pathLength + 1; i < size; i++) {
			parent[i] = pathLength;
		}
		return new TestCase(parent);
	}

	TestCase kAryTree(int... ks) {
		int size = 1;
		int cur = 1;
		for (int i = 0; i < ks.length; i++) {
			cur *= ks[i];
			size += cur;
		}
		int[] parent = new int[size];
		cur = 1;
		size = 1;
		for (int i = 0; i < ks.length; i++) {
			cur *= ks[i];
			for (int j = size; j <= size + cur - 1; j++) {
				parent[j] = (j - size) / ks[i] + (size - cur / ks[i]);
			}
			size += cur;
		}
		return new TestCase(parent);
	}

	TestCase special(int size) {
		if (size % 3 != 1) {
			throw new AssertionError();
		}
		int k = (size - 1) / 3;
		int[] parent = new int[size];
		for (int i = 1; i <= k; i++) {
			parent[i] = 0;
			parent[i + k] = parent[i + 2 * k] = i;
		}
		TestCase tc = new TestCase(parent);
		for (int i = 1; i <= k; i++) {
			tc.addQuery(i, 1);
			tc.addQuery(i, 1);
			tc.addQuery(0, 2);
		}
		return tc.shuffleQueries();
	}

	static class TestCase {
		int n;
		int[] parent;
		List<Integer> us, ks;
		String comment = "";

		public TestCase() {
			us = new ArrayList<>();
			ks = new ArrayList<>();
		}

		public TestCase(int n) {
			this();
			this.n = n;
			this.parent = new int[n];
		}

		public TestCase(int[] parent) {
			this();
			this.n = parent.length;
			this.parent = parent.clone();
		}

		TestCase addComment(String comment) {
			this.comment += comment;
			return this;
		}

		TestCase shuffleOnDepth(int d) {
			int[] depth = new int[n];
			depth[0] = 0;
			for (int i = 1; i < n; i++) {
				depth[i] = depth[parent[i]] + 1;
			}
			List<Integer> all = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (depth[i] == d) {
					all.add(i);
				}
			}
			List<Integer> shuffled = new ArrayList<>(all);
			Collections.shuffle(shuffled, rng);
			int[] newId = new int[n];
			for (int i = 0; i < n; i++) {
				newId[i] = i;
			}
			for (int i = 0; i < all.size(); i++) {
				newId[all.get(i)] = shuffled.get(i);
			}
			renumberVertices(newId);
			return this;
		}

		TestCase reverseOnDepth(int d) {
			int[] depth = new int[n];
			depth[0] = 0;
			for (int i = 1; i < n; i++) {
				depth[i] = depth[parent[i]] + 1;
			}
			List<Integer> all = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (depth[i] == d) {
					all.add(i);
				}
			}
			List<Integer> reversed = new ArrayList<>(all);
			Collections.reverse(reversed);
			int[] newId = new int[n];
			for (int i = 0; i < n; i++) {
				newId[i] = i;
			}
			for (int i = 0; i < all.size(); i++) {
				newId[all.get(i)] = reversed.get(i);
			}
			renumberVertices(newId);
			return this;
		}

		private void renumberVertices(int[] perm) {
			int[] newParent = new int[n];
			for (int i = 1; i < n; i++) {
				newParent[perm[i]] = perm[parent[i]];
			}

			for (int i = 0; i < us.size(); i++) {
				us.set(i, perm[us.get(i)]);
			}
			this.parent = newParent;
		}

		TestCase shuffleTree() {
			int[] newId = new int[n];
			int[] queue = new int[n];
			int head = 0, tail = 0;
			List<Integer>[] outEdges = new List[n];
			for (int i = 0; i < n; i++) {
				outEdges[i] = new ArrayList<>();
			}
			for (int i = 1; i < n; i++) {
				outEdges[parent[i]].add(i);
			}

			queue[tail++] = 0;
			newId[0] = 0;
			int[] newParent = new int[n];
			while (head < tail) {
				int ptr = head + rng.nextInt(tail - head);
				{
					int tmp = queue[ptr];
					queue[ptr] = queue[head];
					queue[head] = tmp;
				}
				int cur = queue[head];
				newId[cur] = head;
				if (head != 0) {
					newParent[head] = newId[parent[cur]];
				}
				head++;

				for (int v : outEdges[cur]) {
					queue[tail++] = v;
				}
			}

			TestCase ret = new TestCase(newParent);
			for (int i = 0; i < us.size(); i++) {
				ret.addQuery(newId[us.get(i)], ks.get(i));
			}
			return ret;
		}

		private int[] calcHeights() {
			int[] height = new int[n];
			for (int i = n - 1; i > 0; i--) {
				height[parent[i]] = Math.max(height[parent[i]], height[i] + 1);
			}
			return height;
		}

		TestCase randomQueries(int count) {
			int[] height = calcHeights();
			List<Integer> possibleQueries = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (height[i] > 0) {
					possibleQueries.add(i);
				}
			}
			us.clear();
			ks.clear();

			for (int i = 0; i < count; i++) {
				int u = possibleQueries.get(rng.nextInt(possibleQueries.size()));
				int k = 1 + rng.nextInt(height[u]);
				addQuery(u, k);
			}
			return this;
		}

		TestCase rootQueries(int count) {
			int[] height = calcHeights();
			List<Integer> possibleQueries = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (height[i] > 0) {
					possibleQueries.add(i);
				}
			}

			for (int i = 0; i < count; i++) {
				int u = possibleQueries.get(rng.nextInt(possibleQueries.size()));
				int k = 1 + rng.nextInt(height[u]);
				addQuery(u, k);
			}
			return this;
		}

		TestCase consequentQueries() {
			int[] height = calcHeights();
			for (int i = 0; i < n; i++) {
				if (height[i] > 0) {
					addQuery(i, 1);
				}
			}
			return this;
		}

		TestCase shuffleQueries() {
			int seed = rng.nextInt();
			Collections.shuffle(us, new Random(seed));
			Collections.shuffle(ks, new Random(seed));
			return this;
		}

		public TestCase addQuery(int u, int k) {
			us.add(u);
			ks.add(k);
			return this;
		}
	}

	public static void main(String[] args) {
		new TestGen().run();
	}
}
