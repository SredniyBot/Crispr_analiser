import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NON_Repeats {
	 private static ArrayList<String[]> spasers = new ArrayList<String[]>();
	 private static double procent_of_hits=40;
	 public static Set<String> bacteriophages= new HashSet<>();
	 private static String output="";
	 static {
		 String key;
		  output="Spacers:\n";
		 for(int number_of_spacer =0;number_of_spacer<Find_Repeats.spasers.size();number_of_spacer++) {
			 key=Find_Repeats.spasers.get(number_of_spacer);
			 String[] s = {key,swap(key),complimentar(key),swap(complimentar(key))};
			 spasers.add(s);
			 output+=s[0]+"\n";
		 }	 
		 
		 for(int number_of_spacer=0;number_of_spacer<spasers.size();number_of_spacer++) {
				 for(int one_of_four_variants = 0;one_of_four_variants<4;one_of_four_variants++) {
					 System.out.println(spasers.get(number_of_spacer)[one_of_four_variants]);
				 }
				 System.out.println();
				 System.out.println();
			 }
			try(FileWriter writer = new FileWriter("spacer.txt", false)){
		        writer.write(output);
		        writer.flush();
		    } catch(IOException ex){
		        System.out.println(ex.getMessage());
		        Panel.ERROR_LIST.add("spacer.txt is not alloweded");
		    }
		 
		
	 }
	 
	/*
	 * получение результатов бласта и начало парсинга
	*/
	 public static void start() {
		 if(!Program.wide_search) {
			 for(String[] d:spasers) {
				 try {
					 Map<String,String> Genome=BlastApi.request(d[0]);
					 if(!Genome.isEmpty()) {
					 bacteriophages.addAll(Genome.keySet());
					 for(String k:Genome.keySet()) {
						 try(FileWriter writer = new FileWriter("spacer.txt", false)){
							 output+= k;
						        writer.write(output);
						        writer.flush();
						    }
						    catch(IOException ex){
						        System.out.println(ex.getMessage());
						        Panel.ERROR_LIST.add("spacer.txt is not alloweded");
						    }
						 Panel.STATUS="Alignment of spacer and genome";
						 search(Genome.get(k),d);
						 
						 
					 }
				 }else{
					 System.out.println("Lost connection");
				 }} catch (InterruptedException e) {
					//System.out.println("Problem with blast");
					Panel.ERROR_LIST.add("THREAD_ERROR");
				}
			 }
			 PAM_finder.try_to_find();
			 
		 }else {
			 
			 
			 
		 }
		 Panel.STATUS="Done";
	 }
	 
	 
	 
	 
	 
	 

	 
	 
	/*
	 * поиск спейсеров по геномам. заносит предполагаемые памы в лист и текстоывый документ
	 * @param genome геном по которому надо искать 
	 * @param spacer массив инстанций 1 спейсера
	 */
	public static void search(String genome,String [] spacer) {
		int length_of_string=spacer[0].length()/2-2;
		genome=genome.replace("\n", "").replace('g','G').replace('c','C').replace('t','T').replace('a','A').replace("\r","");
		for(int variant_of_spacer=0;variant_of_spacer<4;variant_of_spacer++) {
			ArrayList<ArrayList<Integer>> arr =new ArrayList<>();
			for(int tab=0;tab<spacer[variant_of_spacer].length()-length_of_string;tab++) {
				String small_spacer=spacer[variant_of_spacer].substring(tab,tab+length_of_string);
				int location=0;
				ArrayList<Integer> place_of_spacer= new ArrayList<>();
				while(genome.substring(location+1).contains(small_spacer)) {
					place_of_spacer.add(genome.indexOf(small_spacer, location+1));
					//System.out.print(genome.substring(genome.indexOf(small_spacer, location+1),genome.indexOf(small_spacer, location+1)+20).length());
					location=genome.indexOf(small_spacer, location+1);
				}
			arr.add(place_of_spacer);
			}
			double hit=0;
			Set<String> pams= new HashSet<>();
			Set<String> pams1= new HashSet<>();
			for(Integer s:arr.get(0)){
				for(int u=1;u<arr.size();u++) {
					if(arr.get(u).contains(s+u)) {
						hit++;
					}
				}
				if(hit/(arr.size()-1)*100>=procent_of_hits) {
					if(variant_of_spacer==0) {
						output+=" {type of spacer:"+variant_of_spacer+" spacer:"+s+" "+genome.substring(s,s+spacer[variant_of_spacer].length())+" "+(s+spacer[variant_of_spacer].length()-1)+" seq with PAM:"+genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10)+"/"+swap(genome.substring(s-10,s))+" }";
						pams.add(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10));
						System.out.print(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10));
						
						pams1.add(swap(genome.substring(s-10,s)));
						System.out.println(swap(genome.substring(s-10,s)));
						
					}else if(variant_of_spacer==1) {
						output+=" {type of spacer:"+variant_of_spacer+" spacer:"+s+" "+genome.substring(s,s+spacer[variant_of_spacer].length())+" "+(s+spacer[variant_of_spacer].length()-1)+" seq with PAM:"+swap(genome.substring(s-10,s))+"/"+genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10)+" }";
						pams.add(swap(genome.substring(s-10,s)));
						System.out.println(swap(genome.substring(s-10,s))+" swapped");
						
						
						pams1.add(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10));
						System.out.println(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10)+" swapped");
						
					}else if(variant_of_spacer==2) {
						output+=" {type of spacer:"+variant_of_spacer+" spacer:"+s+" "+genome.substring(s,s+spacer[variant_of_spacer].length())+" "+(s+spacer[variant_of_spacer].length()-1)+" seq with PAM:"+complimentar(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10))+"/"+complimentar(genome.substring(s-10,s))+" }";
						pams.add(complimentar(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10)));
						System.out.println(complimentar(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10)+" comp"));
						
						
						pams1.add(swap(complimentar(genome.substring(s-10,s))));
						System.out.println(swap(complimentar(genome.substring(s-10,s)+" comp")));
						
						
					}else if(variant_of_spacer==3) {
						output+=" {type of spacer:"+variant_of_spacer+" spacer:"+s+" "+genome.substring(s,s+spacer[variant_of_spacer].length())+" "+(s+spacer[variant_of_spacer].length()-1)+" seq with PAM:"+complimentar(swap(genome.substring(s-10,s)))+"/"+complimentar(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10))+" }";
						pams.add(complimentar(swap(genome.substring(s-10,s))));
						System.out.println(complimentar(swap(genome.substring(s-10,s)))+" comp swapped");
						
						
						
						pams1.add(complimentar(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10)));
						System.out.println(complimentar(genome.substring(s+spacer[variant_of_spacer].length(),s+spacer[variant_of_spacer].length()+10))+" comp swapped");
					}
				}
				hit=0;
			}
			PAM_finder.add_pams(pams);
			PAM_finder.add_pams1(pams1);
		}
		output+="\n";
	}
	 
	
	
	
	
	
	
	 
	/*
	 * возвращающий строку задом на перед
	 * @param input строка для переворачивания
	*/
	 public static String swap(String input) {
	        String res = "";
	        for (int i = 0; i < input.length(); i++) {
	            res = input.charAt(i) + res;
	        }
	        return res;
	    }
	 
	/*
	 * возвращающий строку компрлиментарную данной
	 * @param input строка для комплиментации
	*/
	 public static String complimentar(String input) {//метод 
		 char[]s=input.toCharArray();
		 for(int i = 0;i<input.length();i++) {
			 if(s[i]=='A') {
				 s[i]='T';
			 }else if(s[i]=='T') {
				 s[i]='A';
			 }else if(s[i]=='G') {
				 s[i]='C';
			 }else if(s[i]=='C') {
				 s[i]='G';
			 }
		 }
		 input = String.valueOf(s);
		 return input;
	 }
	 
	 
	 
}
