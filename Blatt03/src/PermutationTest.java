import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=1;

	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}


	@Test
	void testPermutation() {
		initialize();
		// TODO
		assertEquals(n1,p1.original.length);
		assertEquals(n2,p2.original.length);

		assertNotNull(p1.allDerangements);
		assertNotNull(p2.allDerangements);
		assertTrue(p1.allDerangements.isEmpty());
		assertTrue(p2.allDerangements.isEmpty());

		int[] sorted1 = Arrays.copyOf(p1.original, n1);
		Arrays.sort(sorted1);
		for(int i=0; i<n1 - 1; i++) {
			assertTrue(sorted1[i] < sorted1[i+1], "Dup p1");
		}

		int[] sorted2 = Arrays.copyOf(p2.original, n2);
		Arrays.sort(sorted2);
		for(int i=0; i<n2 - 1; i++) {
			assertTrue(sorted2[i] < sorted2[i+1], "Dup p2");
		}
	}


	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
		p1.derangements();
		p2.derangements();

		if (cases == 0) {
			assertEquals(9, p1.allDerangements.size());
			assertEquals(265, p2.allDerangements.size());
		}
		else {
			assertEquals(0, p1.allDerangements.size());
			assertEquals(0, p2.allDerangements.size());
			return;
		}

		for( int[] perm : p1.allDerangements) {
			for (int i=0; i < n1 ; i++) {
				assertNotEquals(p1.original[i], perm[i]);
			}
		}

		for( int[] perm : p2.allDerangements) {
			for (int i=0; i < n2 ; i++) {
				assertNotEquals(p2.original[i], perm[i]);
			}
		}

	}

	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
		p1.derangements();
		p2.derangements();

		if (cases == 0) {
			assertFalse(p1.allDerangements.isEmpty());
			assertFalse(p2.allDerangements.isEmpty());
		}
		else {
			assertTrue(p1.allDerangements.isEmpty());
			assertTrue(p2.allDerangements.isEmpty());
			return;
		}

		for(int[] perm : p1.allDerangements){
			int[] sortedOriginal = Arrays.copyOf(p1.original, n1);
			int[] sortedPerm = Arrays.copyOf(perm, n1);
			Arrays.sort(sortedOriginal);
			Arrays.sort(sortedPerm);
			assertArrayEquals(sortedOriginal, sortedPerm);
		}

		for( int[] perm : p2.allDerangements){
			int[] sortedOriginal = Arrays.copyOf(p2.original, n2);
			int[] sortedPerm = Arrays.copyOf(perm, n2);
			Arrays.sort(sortedOriginal);
			Arrays.sort(sortedPerm);
			assertArrayEquals(sortedOriginal, sortedPerm);
		}
	}

	void setCases(int c) {
		this.cases=c;
	}

	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;

		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
}


