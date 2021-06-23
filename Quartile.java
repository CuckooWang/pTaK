package github;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Quartile {
	@SuppressWarnings("unchecked")
	public static Map<String,Double> outlier(Map<String,Double> score){
		List<String> ids = new ArrayList<String>();
		Iterator<String> it1 = score.keySet().iterator();
		while(it1.hasNext()){
			String key = it1.next();
			ids.add(key);
		}
		Collections.sort(ids, new Comparator(){
			public int compare(Object o1, Object o2) {
				String a = (String)o1;
				String b = (String)o2;
				if(score.get(a) < score.get(b)){
					return -1;
				}else if(score.get(a) > score.get(b)){
					return 1;
				}else{
					return 0;
				}
			}
		});
		double f1 = (ids.size()+1.0)/4.0;
		double f2 = 2.0*(ids.size()+1.0)/4.0;
		double f3 = 3.0*(ids.size()+1.0)/4.0;
		double Q1 = (f1-(int)f1)*score.get(ids.get((int) Math.ceil(f1))) + (1.0+(int)f1-f1)*score.get(ids.get((int) Math.ceil(f1)-1));
		double Q2 = (f2-(int)f2)*score.get(ids.get((int) Math.ceil(f2))) + (1.0+(int)f2-f2)*score.get(ids.get((int) Math.ceil(f2)-1));
		double Q3 = (f3-(int)f3)*score.get(ids.get((int) Math.ceil(f3))) + (1.0+(int)f3-f3)*score.get(ids.get((int) Math.ceil(f3)-1));
		
		double range = Q3-Q1;
		double down = Q1 - 3*range;
		double up = Q3 + 3*range;
		
		Map<String,Double> newscore = new HashMap<String,Double>();
		for(int i=0;i<ids.size();i++){
			double s = score.get(ids.get(i));
			if(s > up || s < down){
				continue;
			}else{
				newscore.put(ids.get(i), s);
			}
		}
		
		return newscore;
	}
}
