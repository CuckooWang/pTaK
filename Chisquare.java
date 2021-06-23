package github;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jsc.distributions.ChiSquared;

public class Chisquare {
	public static void main(String[] args) throws IOException{
		Chisquare cs = new Chisquare();
		BufferedReader br1 = new BufferedReader(new FileReader("HCC1_norm.txt"));
		BufferedReader br2 = new BufferedReader(new FileReader("NAT1_norm.txt"));
		BufferedReader br3 = new BufferedReader(new FileReader("psite_ELM.igps.txt"));
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("Chisuqare_out.txt"));
		String s1;
		Map<String, Double> hm1 = new HashMap<String, Double>();
		Map<String, Double> hm2 = new HashMap<String, Double>();
		
		while((s1 = br1.readLine()) != null){
			String[] sp1 = s1.split("\\t");
			String id = sp1[0];
			String ind = sp1[1];
			String pepinf = id + "\t" + ind;
			double pos = Double.parseDouble(sp1[4]);
			if(!hm1.containsKey(pepinf)){
				hm1.put(pepinf, pos);
				hm2.put(pepinf, 0.0);
			}else{
				hm1.put(pepinf, hm1.get(pepinf)+pos);
			}
		}
		while((s1 = br2.readLine()) != null){
			String[] sp1 = s1.split("\t");
			String id = sp1[0];
			String ind = sp1[1];
			String pepinf = id + "\t" + ind;
			double neg = Double.parseDouble(sp1[4]);
			if(!hm2.containsKey(pepinf)){
				hm2.put(pepinf, neg);
				hm1.put(pepinf, 0.0);
			}else{
				hm2.put(pepinf, hm2.get(pepinf)+neg);
			}
		}
		
		String s2;
		Set<String> hs = new HashSet<String>();
		Map<String, Double> hmk1 = new HashMap<String, Double>();
		Map<String, Double> hmk2 = new HashMap<String, Double>();
		double POS = 0.0;
		double NEG = 0.0;
		while((s2 = br3.readLine()) != null){
			String[] sp2 = s2.split("\t");
			String id = sp2[0];
			String ind = sp2[1];
			String pepinf = id + "\t" + ind;
			String inf = sp2[7] + "\t" + sp2[9];
			String repinf = pepinf + "\t" + inf;
			if(hs.contains(repinf)){
				continue;
			}else{
				hs.add(repinf);
				if(!hm1.containsKey(pepinf)){
					continue;
				}
				POS += hm1.get(pepinf);
				NEG += hm2.get(pepinf);
				
				if(!hmk1.containsKey(inf)){
					hmk1.put(inf,hm1.get(pepinf));
					hmk2.put(inf,hm2.get(pepinf));
				}else{
					hmk1.put(inf,hmk1.get(inf) + hm1.get(pepinf));
					hmk2.put(inf,hmk2.get(inf) + hm2.get(pepinf));
				}
			}
		}
		System.out.println("POS:" + POS);
		System.out.println("NEG:" + NEG);

		Iterator<String> it = hmk1.keySet().iterator();
		while(it.hasNext()){
			String kin = it.next();
			String[] sp1 = kin.split("\t");
			String ka = sp1[0];
			//System.out.println(kin);
			double[] chiout = cs.chiSquare(hmk1.get(kin), hmk2.get(kin), POS, NEG);
			double x2 = chiout[0];
			double p = chiout[1];
			double eval = (hmk1.get(kin)/POS)/(hmk2.get(kin)/NEG);
			
			bw1.write(kin + "\t" + hmk1.get(kin) + "\t" + hmk2.get(kin) + "\t" + eval + "\t" + x2 + "\t" + p);
			bw1.newLine();
		}
		
		bw1.flush();
		bw1.close();
	}
		
	
	public double[] chiSquare(double a,double b,double c1,double d1){
		double c = c1 - a;
		double d = d1 - b;
		double p = 1.0;
		Chisquare cs = new Chisquare();
		double T1 = (a+b)*(a+c)/(a+b+c+d);
		double T2 = (a+b)*(b+d)/(a+b+c+d);
		double T3 = (c+a)*(c+d)/(a+b+c+d);
		double T4 = (c+d)*(b+d)/(a+b+c+d);
		double x2;
		if(T1 >= 5.0 && T2 >= 5.0 && T3 >= 5.0 && T4 >= 5.0){
			x2 = (Math.pow((a*d - b*c), 2)*(a+b+c+d))/((a+b)*(c+d)*(a+c)*(b+d));
			p = ChiSquared.upperTailProb(x2, 1.0);
		}else{
			x2 = (Math.pow(Math.abs(a*d - b*c)-(a+b+c+d)/2, 2)*(a+b+c+d))/((a+b)*(c+d)*(a+c)*(b+d));
			p = ChiSquared.upperTailProb(x2, 1.0);
		}
		double[] chiout = {x2,p};
		return chiout;
	}
}
