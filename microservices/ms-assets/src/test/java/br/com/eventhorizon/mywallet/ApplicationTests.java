package br.com.eventhorizon.mywallet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	private boolean isInternal() {
		return true;
	}

	@Test
	void contextLoads() {
		System.out.println(isInternal() ? "internal" : "external");

//		method(10, 10L);
//		method(1);
//		method(1, null);
//		method(1, new int[0]);
//		int[] array = new int[0];
//		int[][] matrix = new int[0][];
//		int[][][] cube = new int[0][][];
//		int[][][][] cube4d = new int[0][][][];
//
//		int[] a = new int[10];
//		int[] b = new int[] { 1, 2, 3 };
//		int[] c = { 1, 2, 3 };
//		int[] d = { };
//		int e[];
//		int f[] = { 1, 2, 3 };
	}

}
