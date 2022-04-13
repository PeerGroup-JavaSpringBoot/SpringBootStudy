package challengeMission;

import java.util.ArrayList;

public class arraylist {
	public static void main(String[] args) {
			
			ArrayList<String> items = new ArrayList<String>();
			
			items.add("Item1");
			items.add("Item2");
			items.add("Item3");
			items.add("Item4");
			items.add("Item5");
			
			System.out.println("idx item");
			
			for(int i =0 ; i<items.size();i++) {
				System.out.println(" "+ i + "  "+ items.get(i));
			}
			}
		}
