package github;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tool.Quartile;

public class RawDataNormalization {
	public static void main(String[] args) throws IOException{
		BufferedReader br1 = new BufferedReader(new FileReader("HCC1_rawdata.txt"));
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("HCC1_norm.txt"));
		
		String s1;
		Set<String> prosite = new HashSet<String>();	
		Map<String, Double> hm1 = new HashMap<String, Double>();
		while((s1 = br1.readLine()) != null){
			String[] sp1 = s1.split("\t");
			if(sp1[0].startsWith("Proteins") || (sp1[0].equals(sp1[1]))){
				continue;
			}else{
				String aa = sp1[15];
				double inten1 = Double.parseDouble(sp1[24]);
				String[] sp10 = sp1[0].split(";");
				String[] sp11 = sp1[1].split(";");
				for(int i=0;i<sp10.length;i++){
					if(sp10[i].startsWith("CON_") || sp10[i].startsWith("REV_")){
						System.out.println(sp10[i]);
						continue;
					}else{
						String pro = null;
						if(sp10[i].contains("|")){
							String[] sp2 = sp10[i].split("\\|");
							pro = sp2[1];
						}else{
							pro = sp10[i];
						}
						
						String pos = sp11[i];
						String inf = pro + "\t" + pos + "\t" + aa;
						if(!hm1.containsKey(inf) && inten1 != 0.0){
							prosite.add(inf);
							hm1.put(inf, inten1);
						}else if(hm1.containsKey(inf) && inten1 != 0.0){
							hm1.put(inf, hm1.get(inf) + inten1);
						}else{
							continue;
						}
					}
				}
			}
		}
		Map<String, Double> hm10 = Quartile.outlier(hm1);
		double sum = sumscore(hm10);
		double num = hm10.keySet().size();
		
		Iterator<String> it1 = prosite.iterator();
		while(it1.hasNext()){
			String inf = it1.next();
			if(hm10.containsKey(inf)){
				double inten = hm10.get(inf)*num/sum;
				bw1.write(inf + "\t" + hm1.get(inf) + "\t" + String.format("%.5f", inten));
				bw1.newLine();
			}else{
				System.out.println("outlier:" + inf + "\t" + hm1.get(inf));
			}
		}
		bw1.flush();
		bw1.close();
		br1.close();	
	}
	
	public static double sumscore(Map<String,Double> score){
		double sum = 0.0;
		Iterator<String> it1 = score.keySet().iterator();
		while(it1.hasNext()){
			String key = it1.next();
			double s = score.get(key);
			sum += s;
		}
		return sum;
	}
}
