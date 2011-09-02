package net.mad.ads.base.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompareTest {
	public static void main (String []args) throws Exception {
		List<Float> fl = new ArrayList<Float>();
		fl.add(0.1f);
		fl.add(0.0f);
		fl.add(0.0f);
		fl.add(0.2f);
		fl.add(-1f);
		
		System.out.println(fl);
		
		Collections.sort(fl, new Comparator<Float>() {

			@Override
			public int compare(Float o1, Float o2) {
				if (o1 > o2) {
					return -1;
				} else if (o2 > o1) {
					return 1;
				}
				return 0;
			}
		});
		
		System.out.println(fl);
		Collections.reverse(fl);
		System.out.println(fl);
		
		float sma = -2f;
		for (Float f : fl) {
			if (f == -1f) {
				System.out.print(f);
			} else if (sma == -2f) {
				System.out.print(f);
				sma = f;
			} else if (f == sma) {
				System.out.print(f);
			} else if (f > sma) {
				break;
			}
			System.out.print(" ");
		}
	}
}
