import java.io.*;
import java.util.*;

public class TestGen implements Runnable {

	final static Random rng = new Random("mining".hashCode());
	final static File testsDesc = new File("../tests.desc");
	final static int MAX_W = 100_000;
	final static int MAX_S = 4;
	final static int MAX_Q = 100;
	final static int MAX_T = 100;

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
		genPreliminary();
		genSubtask1();
		genSubtask2();
		genSubtask3();
		genSubtask4();
		genSubtask5();
		genSubtask6();
	}

	void genPreliminary() {
		isPreliminary = true;
		TestCase sample = new TestCase(4, 3, 1,
			new Point[] {new Point(1, 1), new Point(3, 2)},
			new Group[] {new Group(0, 4, 1), new Group(1, 9, 1), new Group(0, 12, 2)});
		sample.addComment("Sample test");
		printTest(sample);
		testCount = 0;
		isPreliminary = false;
	}

	void printUsualTests(int MAX_W, int MAX_S, int MAX_Q) {
		if (this.currentSubtask == 1) {
			printTest(genRandom(1, 1, MAX_S, rng.nextInt(MAX_Q) + 1, 1));
			printTest(genRandom(1, 1, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T));
			printTest(genRandom(1, 2, MAX_S, rng.nextInt(MAX_Q) + 1, 1));
			printTest(genRandom(2, 1, MAX_S, rng.nextInt(MAX_Q) + 1, 1));
			printTest(genRandom(2, 2, MAX_S, rng.nextInt(MAX_Q) + 1, 2));
			printTest(genRandom(3, 4, MAX_S, rng.nextInt(MAX_Q) + 1, 2));
			printTest(genRandom(7, 5, MAX_S, rng.nextInt(MAX_Q) + 1, 4));
		}
		printTest(genMax(MAX_W, MAX_W, MAX_S, MAX_Q, MAX_T));
		printTest(genRandom(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, 1));
		printTest(genRandom(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, 10));
		printTest(genRandom(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T));
		printTest(genRandom(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, rng.nextInt(MAX_T) + 1));
  		printTest(genRandomPerfect(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, false, false));
		printTest(genRandomPerfectExtra(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, false, false));
		printTest(genRandomAntiHall(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, true, false));
  		printTest(genRandomPerfect(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, rng.nextInt(MAX_T) + 1, false, false, false));
		printTest(genRandomPerfectExtra(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, rng.nextInt(MAX_T) + 1, false, false, false));
		printTest(genRandomAntiHall(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, rng.nextInt(MAX_T) + 1, false, false, false));
	}

	void genSubtask1() {
		this.currentSubtask = 1;
		printUsualTests(20, 1, 1);
	}

	void genSubtask2() {
		this.currentSubtask = 2;
		printUsualTests(20, 2, 1);
	}

	void genSubtask3() {
		this.currentSubtask = 3;
		printUsualTests(20, 3, 1);
	}

	void genSubtask4() {
		this.currentSubtask = 4;
		printUsualTests(20, 3, 100);
	}

	void genSubtask5() {
		this.currentSubtask = 5;
		printUsualTests(100000, 1, 100);
	}

	void genSubtask6() {
		this.currentSubtask = 6;
		int MAX_S;

		for (MAX_S = 2; MAX_S <= 3; MAX_S++) {
			printTest(genRandomPerfect(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, false, false));
			printTest(genRandom(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, rng.nextInt(MAX_T) + 1));
			printTest(genRandom(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T));
  			printTest(genRandomPerfectExtra(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, false, false));
  			printTest(genRandomPerfectExtra(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, false, false));
			printTest(genRandomAntiHall(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, rng.nextInt(MAX_T) + 1, false, true, false));
			printTest(genRandomAntiHall(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, true, false));
			printTest(genRandomAntiHall(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, true, true));
		}

		MAX_S = 4;
		printTest(genRandomPerfect(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, false, false));
		for (int i = 0; i < 2; i++) {
			printTest(genRandom(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, rng.nextInt(MAX_T) + 1));
			printTest(genRandom(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T));
  		}
  		for (int i = 0; i < 2; i++) {
	  		printTest(genRandomPerfectExtra(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, false, false));
  			printTest(genRandomPerfectExtra(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, false, false));
			printTest(genRandomAntiHall(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, true, false));
			printTest(genRandomAntiHall(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, true, false));
			printTest(genRandomAntiHall(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, true, true));
		}
		printTest(genRandomPerfectExtra(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, false, false));
		printTest(genRandomPerfectExtra(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, false, false));
		printTest(genRandomAntiHall(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, false, true, false));
		printTest(genRandomAntiHall(rng.nextInt(MAX_W) + 1, rng.nextInt(MAX_W) + 1, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T, true, true, false));
  		printTest(genManyCells(MAX_W, MAX_W, MAX_S, rng.nextInt(MAX_Q) + 1, MAX_T));
	}

	TestCase genMax(int w, int h, int s, int q, int t) {
		TestCase tc = new TestCase(w, h, q);
		tc.addComment("Maximal test");
		buildPointsRandom(tc, s);
		Group[] g = new Group[t];
		for (int i = 0; i < t; i++) {
			int b = rng.nextInt(s);
			long n = (long) w * h * q;
			int m = Math.max(w, h) - 1;
			g[i] = new Group(b, n, m);
		}
		tc.g = g;
		return tc;
	}

	TestCase genManyCells(int w, int h, int s, int q, int t) {
		TestCase tc = new TestCase(w, h, q);
		tc.addComment("Test with many cells");
		tc.p = new Point[s];
		for (int i = 0; i < tc.p.length; i++) {
			tc.p[i] = new Point(rng.nextInt(tc.w / 5) + w / 2 - w / 10 + 1, rng.nextInt(tc.h / 5) + h / 2 - h / 10 + 1);
		}
		Group[] g = new Group[t];
		for (int i = 0; i < t; i++) {
			int b = i % s;
			long n = Math.abs(rng.nextLong()) % ((long) 2 * w * h * q / t + 1) + 1;
			int m = rng.nextInt(Math.max(w, h) / 5) + Math.max(w, h) / 5 + 1;
			g[i] = new Group(b, n, m);
		}
		tc.g = g;
		return tc;
	}

	TestCase genRandom(int w, int h, int s, int q, int t) {
		TestCase tc = new TestCase(w, h, q);
		tc.addComment("Random test");
		buildPointsRandom(tc, s);
		Group[] g = new Group[t];
		for (int i = 0; i < t; i++) {
			int b = rng.nextInt(s);
			long n = Math.abs(rng.nextLong()) % ((long) 3 * w * h * q / t + 1) + 1;
			int m = rng.nextInt((Math.max(w, h) + 1) / 2) + Math.max(w, h) / 2;
			g[i] = new Group(b, n, m);
		}
		tc.g = g;
		return tc;
	}

	TestCase genRandomPerfect(int w, int h, int s, int q, int t, boolean unique, boolean equal, boolean manyCells) {
		TestCase tc = new TestCase(w, h, q);
		tc.addComment("Random test with perfect matching");
		buildPointsRandom(tc, s);
		buildGroupsRandom(tc, t, equal, manyCells);
		buildPerfectMatching(tc, unique);
		tc.shuffleGroups();
		return tc;
	}

	TestCase genRandomPerfectExtra(int w, int h, int s, int q, int t, boolean unique, boolean equal, boolean manyCells) {
		TestCase tc = new TestCase(w, h, q);
		tc.addComment("Random test with perfect matching and extra groups");
		int extra = rng.nextInt(t / 10 + 1);
		buildPointsRandom(tc, s);
		buildGroupsRandom(tc, t - extra, equal, manyCells);
		buildPerfectMatching(tc, unique);
		tc.g[tc.g.length - 1].n += Math.abs(rng.nextLong()) % (w * 1L * h * q / t + 1);
		TestCase fake = new TestCase(w, h, q, tc.p);
		buildGroupsRandom(fake, extra, equal, manyCells);
		tc.g = Arrays.copyOf(tc.g, t);
		for (int i = 0; i < extra; i++) {
			tc.g[t - extra + i] = fake.g[i];
			tc.g[t - extra + i].n = Math.abs(rng.nextLong()) % (w * 1L * h * q / t + 1) + 1;
		}
		return tc;
	}

	TestCase genRandomAntiHall(int w, int h, int s, int q, int t, boolean unique, boolean equal, boolean manyCells) {
		TestCase tc = new TestCase(w, h, q);
		tc.addComment("Test with at least one Hall's theorem disproof");
		buildPointsRandom(tc, s);
		tc.p = new Point[s];
		for (int i = 0; i < tc.p.length; i++) {
			int from_w = Math.max(1, w / 2 - w / 5);
			int to_w = Math.max(1, Math.min(w, w / 2 + w / 5));
			int from_h = Math.max(1, h / 2 - h / 5);
			int to_h = Math.max(1, Math.min(h, h / 2 + h / 5));
			tc.p[i] = new Point(from_w + rng.nextInt(to_w - from_w + 1), from_h + rng.nextInt(to_h - from_h + 1));
		}
		buildGroupsRandom(tc, t, equal, manyCells);
		List<Group> good = new ArrayList<>();
		List<Group> bad = new ArrayList<>();
		int[] last = new int[s];
		int[] next = new int[s];
		int[] posInList = new int[t];
		for (int i = 0; i < s; i++) {
			List<Integer> here = new ArrayList<>();
			here.add(-1);
			for (int j = 0; j < t; j++) {
				if (tc.g[j].b == i) {
					here.add(tc.g[j].m);
				}
			}
			Collections.sort(here);
			int bound = here.get(rng.nextInt(here.size()));
			last[i] = -1;
			next[i] = -1;
			for (int j = 0; j < t; j++) {
				if (tc.g[j].b == i) {
					if (tc.g[j].m <= bound) {
						if (tc.g[j].m == bound) {
							last[i] = j;
						}
						posInList[j] = good.size();
						good.add(tc.g[j]);
					} else {
						if (next[i] == -1 || tc.g[j].m < tc.g[next[i]].m) {
							next[i] = j;
						}
						posInList[j] = bad.size();
						bad.add(tc.g[j]);
					}
				}
			}
		}
		tc.g = good.toArray(new Group[0]);
		buildPerfectMatching(tc, unique);
		Group[] newGroups = new Group[t];
		for (int i = 0; i < tc.g.length; i++) {
			newGroups[i] = tc.g[i];
		}
		for (int i = 0; i < bad.size(); i++) {
			newGroups[tc.g.length + i] = bad.get(i);
		}
		tc.g = newGroups;
		buildPerfectMatching(tc, unique);
		long bound = -1;
		for (int i = 0; i < s; i++) {
			if (next[i] != -1) {
				long curBound = tc.g[good.size() + posInList[next[i]]].n - 1;
				if (bound == -1 || curBound < bound) {
					bound = curBound;
				}
			}
		}
		if (bound == -1) {
			return genRandomAntiHall(w, h, s, q, t, unique, equal, manyCells);
		}
		bound = Math.abs(rng.nextLong()) % Math.max(bound, 1L) + 1;
		long toAdd = bound;
		for (int i = 0; i < s; i++) {
			if (next[i] != -1) {
				tc.g[good.size() + posInList[next[i]]].n -= bound;
			}
			if (last[i] != -1) {
				long add = Math.abs(rng.nextLong()) % (toAdd + 1);
				tc.g[posInList[last[i]]].n += add;
				toAdd -= add;
			}
		}
		for (int i = 0; i < tc.g.length; i++) {
			tc.g[i].n = Math.max(tc.g[i].n, 1L);
		}
		tc.shuffleGroups();
		return tc;
	}

	TestCase buildPointsRandom(TestCase tc, int s) {
		tc.p = new Point[s];
		for (int i = 0; i < tc.p.length; i++) {
			tc.p[i] = new Point(rng.nextInt(tc.w) + 1, rng.nextInt(tc.h) + 1);
		}
		return tc;
	}

	TestCase buildGroupsRandom(TestCase tc, int t, boolean equal, boolean manyCells) {
		tc.addComment(" [e=" + equal + "]");
		tc.addComment(" [m=" + manyCells + "]");
		tc.g = new Group[t];
		for (int i = 0; i < tc.g.length; i++) {
			int b = (equal ? (i % tc.p.length) : rng.nextInt(tc.p.length));
			int distToBorder = Math.min(Math.min(tc.p[b].x - 1, tc.w - tc.p[b].x),
										Math.min(tc.p[b].y - 1, tc.h - tc.p[b].y));
			int distToCellos = Math.max(Math.max(tc.p[b].x - 1, tc.w - tc.p[b].x),
										Math.max(tc.p[b].y - 1, tc.h - tc.p[b].y));
			int m = rng.nextInt(manyCells ? (distToBorder + 1) : (distToCellos + 1));
			if (m == 0) {
				m++;
			}
			tc.g[i] = new Group(b, 0, m);
		}
		tc.shuffleGroups();
		return tc;
	}

	TestCase buildPerfectMatching(TestCase tc, boolean unique) {
		tc.addComment(" [u=" + unique + "]");
		List<Integer> xs = new ArrayList<>();
		List<Integer> ys = new ArrayList<>();
		xs.add(0);
		xs.add(tc.w);
		ys.add(0);
		ys.add(tc.h);
		for (int i = 0; i < tc.g.length; i++) {
			xs.add(Math.max(0,    tc.p[tc.g[i].b].x - tc.g[i].m));
			xs.add(Math.min(tc.w, tc.p[tc.g[i].b].x + tc.g[i].m + 1));
			ys.add(Math.max(0,    tc.p[tc.g[i].b].y - tc.g[i].m));
			ys.add(Math.min(tc.h, tc.p[tc.g[i].b].y + tc.g[i].m + 1));
		}
		boolean[] nonZero = new boolean[tc.g.length];
		for (int i = 0; i < tc.g.length; i++) {
			nonZero[i] = (tc.g[i].n != 0L);
		}
		Collections.sort(xs);
		Collections.sort(ys);
		for (int x = 0; x < xs.size() - 1; x++) {
			for (int y = 0; y < ys.size() - 1; y++) {
				long cellSize = (xs.get(x + 1) - xs.get(x)) * 1L * (ys.get(y + 1) - ys.get(y)) * tc.q;
		    	if (cellSize == 0L) {
					continue;
				}
				List<Integer> candidates = new ArrayList<>();
				int[] best = new int[tc.p.length];
				Arrays.fill(best, -1);
				boolean hasNonZero = false;
				for (int i = 0; i < tc.g.length; i++) {
					if (xs.get(x) >= tc.p[tc.g[i].b].x - tc.g[i].m && xs.get(x + 1) <= tc.p[tc.g[i].b].x + tc.g[i].m + 1) {
						if (ys.get(y) >= tc.p[tc.g[i].b].y - tc.g[i].m && ys.get(y + 1) <= tc.p[tc.g[i].b].y + tc.g[i].m + 1) {
							if (nonZero[i]) {
								hasNonZero = true;
							}
							candidates.add(i);
							if (best[tc.g[i].b] == -1 || tc.g[best[tc.g[i].b]].m > tc.g[i].m) {
								best[tc.g[i].b] = i;
							}
						}
					}
				}
				if (hasNonZero) {
					continue;
				}
				if (unique) {
					candidates.clear();
					for (int i = 0; i < tc.p.length; i++) {
						if (best[i] != -1) {
							candidates.add(best[i]);
						}
					}
				}
				if (candidates.isEmpty()) {
					continue;
				}
				Collections.shuffle(candidates);
				long[] separators = new long[candidates.size() + 1];
				for (int i = 1; i < candidates.size(); i++) {
					separators[i] = Math.abs(rng.nextLong()) % (cellSize + 1);
				}
				separators[0] = 0;
				separators[candidates.size()] = cellSize;
				Arrays.sort(separators);
				for (int i = 0; i < candidates.size(); i++) {
					tc.g[candidates.get(i)].n += separators[i + 1] - separators[i];
				}
			}
		}
		for (int i = 0; i < tc.g.length; i++) {
			tc.g[i].n = Math.max(tc.g[i].n, 1L);
		}
		return tc;
	}

	void printTest(TestCase tc) {
		String fileName = isPreliminary ? "../preliminary" : ("../tests/subtask" + currentSubtask);
		fileName += String.format("/%02d", ++testCount);
		appendComment(fileName, tc);
		File outputFile = new File(fileName);
		outputFile.getParentFile().mkdirs();

		try (PrintWriter out = new PrintWriter(outputFile)) {
			out.println(tc.w + " " + tc.h + " " + tc.p.length + " " + tc.q);
			for (int i = 0; i < tc.p.length; i++) {
				out.println(tc.p[i].x + " " + tc.p[i].y);
			}
			out.println(tc.g.length);
			for (int i = 0; i < tc.g.length; i++) {
				out.println((tc.g[i].b + 1) + " " + Math.min(tc.w * 1L * tc.h * tc.q, Math.max(1L, tc.g[i].n)) + " " + Math.min(tc.g[i].m, Math.max(tc.w, tc.h) - 1));
			}
		} catch (IOException e) {
			System.err.println("Could not write to file " + fileName);
		}
		System.out.printf("Printed test %s, (w, h, s, q, t) = (%d, %d, %d, %d, %d)\n", fileName, tc.w, tc.h, tc.p.length, tc.q, tc.g.length);
	}

	void appendComment(String file, TestCase tc) {
		try (FileOutputStream fos = new FileOutputStream("../tests.desc", true);
				PrintWriter out = new PrintWriter(fos)) {
			out.printf("%s\t(w, h, s, q, t) = (%d, %d, %d, %d, %d)\t%s\n", file, tc.w, tc.h, tc.p.length, tc.q, tc.g.length, tc.comment);
		} catch (IOException e) {
			System.err.println("Could not append commment for test " + file);
		}
	}

	static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class Group {
		int b;
		long n;
		int m;

		public Group(int b, long n, int m) {
			this.b = b;
			this.n = n;
			this.m = m;
		}
	}

	static class TestCase {
		int w;
		int h;
		int q;
		Point[] p;
		Group[] g;
		String comment = "";

		public TestCase(int w, int h, int q) {
			this.w = w;
			this.h = h;
			this.q = q;
		}

		public TestCase(int w, int h, int q, Point[] p) {
			this(w, h, q);
			this.p = p.clone();
		}

		public TestCase(int w, int h, int q, Group[] g) {
			this(w, h, q);
			this.g = g.clone();
		}

		public TestCase(int w, int h, int q, Point[] p, Group[] g) {
			this(w, h, q);
			this.p = p.clone();
			this.g = g.clone();
		}

		TestCase addComment(String comment) {
			this.comment += comment;
			return this;
		}

		TestCase shuffleGroups() {
			for (int i = 0; i < g.length; i++) {
				int j = rng.nextInt(i + 1);
				Group tmp = g[i];
				g[i] = g[j];
				g[j] = tmp;
			}
			return this;
		}
	}

	public static void main(String[] args) {
		new TestGen().run();
	}
}
