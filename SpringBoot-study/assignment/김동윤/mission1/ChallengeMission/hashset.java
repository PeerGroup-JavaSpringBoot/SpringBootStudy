package challengeMission;

import java.util.HashSet;

public class hashset {
	public static void main(String[] args) {
			
		HashSet<String> items = new HashSet<String>();
			
			items.add("Item1");
			items.add("Item2");
			items.add("Item3");
			items.add("Item4");
			items.add("Item5");
			
			System.out.println("idx item");
			
			int i = 1;
			for (String items1 : items) {
			    System.out.println(" "+i + "  "+ items1);
			    i+=1;
			}
		}
}