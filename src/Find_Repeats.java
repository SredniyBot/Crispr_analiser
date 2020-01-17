import java.util.*;

public class Find_Repeats {
    private static ArrayList<String> gen = new ArrayList<String>();
    public static ArrayList<String> spasers = new ArrayList<String>();
    public static ArrayList<String> repeats_in_a_row = new ArrayList<String>();
    private static Map<String, Set<Integer>> repeats = new HashMap<String, Set<Integer>>();
    private static String intermedia_gen;
    private static String intermedia_gen1;
    private static double hit;
    private static double hit1;

    private static double procent_of_hits =80;
    private static double procent_of_hits2 =70;
    private static String input;

	/*
	 *начало поиска повторов
	 *@param input геном кассеты
	 */
	public static void start(String input) {//
 
        input=proverka_na_durachka(input);
        Find_Repeats.input=input;
        if(input.equals("false")!=true) {
        	for (char c : input.toCharArray()) {
            gen.add(String.valueOf(c));
       }
       try_to_find();

       split_to_spasers();
       Panel.REZIM=10;
       NON_Repeats.start();
      // NON_Repeats.parse_viruses();

        }else {
        	Panel.STATUS="Wrong input Data";
        	System.out.println("ERROR");
        }
    }

	
	
	/*поиск повторов
	*/
	private static void try_to_find() {
	       		for(int count_of_nucleotids = 55;count_of_nucleotids>22;count_of_nucleotids--) {
		        			intermedia_gen1="";
		                	intermedia_gen="";
		                	hit=sravnenie(count_of_nucleotids,0,count_of_nucleotids+21,gen);
		                	for(int i = 0;i<=count_of_nucleotids;i++) {
		        				intermedia_gen =intermedia_gen+gen.get(i);
		        			}
		                	hit1=sravnenie1(count_of_nucleotids,gen.size()-1,gen.size()-1-count_of_nucleotids+21,gen);
		            		for(int i = 0;i<=count_of_nucleotids;i++) {
		        				intermedia_gen1 =intermedia_gen1+gen.get(gen.size()-1-i);
		        			}
		            		
			                for(int second_compare=count_of_nucleotids+21;second_compare<=count_of_nucleotids+55;second_compare++) {
			                	for(int first_compare=0;first_compare<gen.size()-count_of_nucleotids-second_compare;first_compare++) {
			                		if(hit<sravnenie(count_of_nucleotids,first_compare,second_compare+first_compare,gen)) {
			                			hit=sravnenie(count_of_nucleotids,first_compare,second_compare+first_compare,gen);
			                			intermedia_gen="";
			                			for(int i = 0;i<=count_of_nucleotids;i++) {
			                				intermedia_gen =intermedia_gen+gen.get(first_compare+i);
			                			}
			                		}
			                		
			                		if(hit1<=sravnenie1(count_of_nucleotids,gen.size()-1-first_compare,gen.size()-1-(second_compare+first_compare),gen)) {
			                			hit1=sravnenie1(count_of_nucleotids,gen.size()-1-first_compare,gen.size()-1-second_compare-first_compare,gen);
			                			intermedia_gen1="";
			                			for(int i = 0;i<=count_of_nucleotids;i++) {
			                				intermedia_gen1 =gen.get(gen.size()-1-(first_compare+i))+intermedia_gen1;
			                			}
			                		}
			                	}
			                }
			                if(hit/count_of_nucleotids*100>=procent_of_hits) {
				                	intermedia_gen=contein(intermedia_gen,intermedia_gen1);
				                	hit=0;
				                    Set<Integer> set = new HashSet<Integer>();
					                for(int compare =0;compare<gen.size()-intermedia_gen.length()+1;compare++) {
				                    	for(int number_of_nucleotid=0;number_of_nucleotid<intermedia_gen.length();number_of_nucleotid++) {
				                    		if(gen.get(compare+number_of_nucleotid).equals(String.valueOf(intermedia_gen.charAt(number_of_nucleotid)))) {
				                    			hit+=1;
				                        	}
				                        
				                    	}	
				                    	if(hit/count_of_nucleotids*100>=procent_of_hits2) {
				                    		set.add(compare);
				                    	}
				                    	hit=0;
				                    }
				                    repeats.put(intermedia_gen,set);
			                }
		            hit=0;
		            intermedia_gen="";
		            intermedia_gen1="";
	       		}
	       		
        intermedia_gen1="";
        intermedia_gen="";
        String res = "";
        hit=0;
        
        Set<Integer> value = new HashSet<Integer>();
        for (String item : repeats.keySet()) {
            Set<Integer> v2 = repeats.get(item);
            if (v2.size() > value.size()) {
                value = v2;
                res = item;
            } else if (v2.size() == value.size()) {
                if (res.length() < item.length()) {
                    value = v2;
                    res = item;
                }
          }
        }
        res=check_symbols_from_ends(res,value,70);
        
        System.out.println(res);
        System.out.println();
        repeats.clear();
        hit=0;
        Set<Integer> set = new HashSet<Integer>();
        for(int compare =0;compare<gen.size()-res.length()+1;compare++) {
        	for(int number_of_nucleotid=0;number_of_nucleotid<res.length();number_of_nucleotid++) {
        		if(gen.get(compare+number_of_nucleotid).equals(String.valueOf(res.charAt(number_of_nucleotid)))) {
        			hit+=1;
            	}
        	}
        	if(hit/res.length()*100>=procent_of_hits2) {
        		set.add(compare);
        	}
        	hit=0;
        }
        value=set;
        repeats.put(res,set);
        for (Integer index : value) {
            for (int i=0;i<res.length();i++) {
                intermedia_gen=intermedia_gen+gen.get(i+index);
            }
            //System.out.println(intermedia_gen);
            repeats_in_a_row.add(intermedia_gen);
            input=input.replace(intermedia_gen," ");
            intermedia_gen="";
        }
		//System.out.println(repeats_in_a_row.size());
    }

	/*метод, заполняющий лист повторов и спейсеров в правильном порядке для отображения в панели
	*/
	public static ArrayList<String> zapolnenie_massiva() {
		ArrayList<String> st=new ArrayList<String>();
		st.add(repeats_in_a_row.get(0));
		for(int i=0;i<repeats_in_a_row.size()-1;i++) {
			st.add(spasers.get(i));
			st.add(repeats_in_a_row.get(i+1));
		}//System.out.println(st.size());
		return st;
	}
    
	/*проверка на правильность входных данных
	 *@param input криспр кассета
	 */
    private static String proverka_na_durachka(String input) {
    	input=input.replace("\n","");
    	input=input.replace('a','A');
    	input=input.replace('t','T');
    	input=input.replace('c','C');
    	input=input.replace('g','G');
    	for(int i =0;i<input.length();i++) {
    		if(input.charAt(i)!='A'&&input.charAt(i)!='C'&&input.charAt(i)!='G'&&input.charAt(i)!='T') {
    			return "false";
    		}
    	}
    	return input;
    }
    
	/*
	 * поиск совпадений двух цепочек слева на право. возвращает количество попаданий
	 * @param count_of_nucleotids длинна цепочек 
	 * @param first_compare номер 1 символа 1 цепочки
	 * @param second_compare номер 1 символа 2 цепочки
	 * @param genbank строка по которой надо искать, представленная ввиде листа
	*/
    private static int sravnenie(int count_of_nucleotids,int first_compare, int second_compare,ArrayList<String> genbank) {//
    	int schetchik = 0;
	    	for(int number_of_nucleotid=0;number_of_nucleotid<count_of_nucleotids;number_of_nucleotid++) {
	            if(genbank.get(first_compare+number_of_nucleotid).equals(genbank.get(second_compare+number_of_nucleotid))) {
	            	schetchik+=1;
	            }
	    	}
    	return schetchik;
    	}
    
    
    /*
	 * поиск совпадений двух цепочек справа на лево. возвращает количество попаданий
	 * @param count_of_nucleotids длинна цепочек 
	 * @param first_compare номер 1 символа 1 цепочки
	 * @param second_compare номер 1 символа 2 цепочки
	 * @param genbank строка по которой надо искать, представленная ввиде листа
	*/
    private static int sravnenie1(int count_of_nucleotids,int first_compare, int second_compare,ArrayList<String> genbank) {
    	int schetchik = 0;
	    	for(int number_of_nucleotid=0;number_of_nucleotid<count_of_nucleotids;number_of_nucleotid++) {
	            if(genbank.get(first_compare-number_of_nucleotid).equals(genbank.get(second_compare-number_of_nucleotid))) {
	            	schetchik+=1;
	            }
	    	}
    	return schetchik;
    	}
    
    
	/*вычленение спейсеров
	*/
    private static void split_to_spasers() {
    	for(int i =1;i<input.split(" ").length;i++) {
    		spasers.add(input.split(" ")[i]);
    	}
    }
    
	/*
	*возвращает максимально большой совпавший кусок двух строк
	*@param s первая строка для сравнения
	*@param m вторая строка для сравнения
	*/
    private static String contein(String s,String m) {
    	ArrayList<String> f=new ArrayList<String>();
	    	for(int h=0;h<s.length();h++) {
	    		for(int r=m.length();r>h;r--) {
	    			//System.out.println(h+"  "+r);
	    			if(s.contains(m.substring(h,r))) {
	    				f.add(m.substring(h,r));
	    			}
	    		}
	    	}
    	String l="";
	    	for(int i =0;i<f.size();i++){
	    		if(l.length()<f.get(i).length()) {
	    			l=f.get(i);
	    		}
	    	}
    	return l;
    }
    
    
	/*
	 * проверка на правильность символов с концов. Возвращает строку с правильными символами с концов
	 * @param st строка которую нужно проверить
	 * @param set строки, похожие на st, по которым надо проверять
	 * @param procent процент попаданий, допустимый в строке
	 */
    private static String check_symbols_from_ends(String st, Set<Integer> set,double procent) {//
    	
        Set<Integer>  f = new HashSet<Integer>();
        if(!check_symbol(st,set,1,procent)) {
        	st=st.substring(2);
        	for(Integer l:set) {
        		f.add(Integer.valueOf(l.intValue()+2));
        	}
        	set.clear();
        	set.addAll(f);
        	f.clear();
        }
        if(!check_symbol(st,set,st.length()-2, procent)) {
    		st=st.substring(0,st.length()-3);
        }
    	while(!check_symbol(st,set,st.length()-1, procent)) {
    		st=st.substring(0,st.length()-2);
        }
        while(!check_symbol(st,set,0,procent)) {
        	st=st.substring(1);
        	for(Integer l:set) {
        		f.add(Integer.valueOf(l.intValue()+1));
        	}
        	set.clear();
        	set.addAll(f);
        	f.clear();
        }
        return st;
    }
    
    
    /*
	 * проверка на правильность символа. Возвращает строку с правильным символом 
	 * @param st строка которую нужно проверить
	 * @param set строки, похожие на st, по которым надо проверять
	 * @param procent процент попаданий, допустимый в строке
	 * @param number номер проверяемого символа
	 */
    private static boolean check_symbol(String st, Set<Integer> set,int number,double procent) {
    	double hit = 0;
    	for(Integer a:set) {
    		if(gen.get(a.intValue()+number).charAt(0)==st.charAt(number)) {
    			hit++;
    		}
    	}
    	if(hit/set.size()*100>=procent) {
    		return true;
    	}
    	return false;
    }
    
    
}