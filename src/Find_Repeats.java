import java.util.*;

public class Find_Repeats {
	private static ArrayList<String> gen = new ArrayList<String>();
	public static ArrayList<String> spacers = new ArrayList<String>();
	public static ArrayList<String> repeats_in_a_row = new ArrayList<String>();
	private static Map<String, Set<Integer>> repeats = new HashMap<String, Set<Integer>>();
	private static String intermedia_gen,intermedia_gen1;
	private static double hit,hit1;

	private static double percent_of_hits =80;
	private static double percent_of_hits2 =70;
	private static String input;

	/*
	 *������ ������ ��������
	 *@param input ����� �������
	 */
	public static void start(String input) {
		input= checkData(input);
		Find_Repeats.input=input;
		for (char c : input.toCharArray()) {
			gen.add(String.valueOf(c));
		}
		try_to_find();
		split_to_spacers();
		resultPanel.paint_repeats_and_spacers(fillArray());
		NON_Repeats.start();
	}

	/*����� ��������
	 */
	private static void try_to_find() {
		for(int count_of_nucleotides = 55;count_of_nucleotides>22;count_of_nucleotides--) {
			intermedia_gen1="";
			intermedia_gen="";
			hit= compare(count_of_nucleotides,0,count_of_nucleotides+21,gen);
			for(int i = 0;i<=count_of_nucleotides;i++) {
				intermedia_gen =intermedia_gen+gen.get(i);
			}
			hit1= compare1(count_of_nucleotides,gen.size()-1,gen.size()-1-count_of_nucleotides+21,gen);
			for(int i = 0;i<=count_of_nucleotides;i++) {
				intermedia_gen1 =intermedia_gen1+gen.get(gen.size()-1-i);
			}

			for(int second_compare=count_of_nucleotides+21;second_compare<=count_of_nucleotides+55;second_compare++) {
				for(int first_compare=0;first_compare<gen.size()-count_of_nucleotides-second_compare;first_compare++) {
					if(hit< compare(count_of_nucleotides,first_compare,second_compare+first_compare,gen)) {
						hit= compare(count_of_nucleotides,first_compare,second_compare+first_compare,gen);
						intermedia_gen="";
						for(int i = 0;i<=count_of_nucleotides;i++) {
							intermedia_gen =intermedia_gen+gen.get(first_compare+i);
						}
					}

					if(hit1<= compare1(count_of_nucleotides,gen.size()-1-first_compare,gen.size()-1-(second_compare+first_compare),gen)) {
						hit1= compare1(count_of_nucleotides,gen.size()-1-first_compare,gen.size()-1-second_compare-first_compare,gen);
						intermedia_gen1="";
						for(int i = 0;i<=count_of_nucleotides;i++) {
							intermedia_gen1 =gen.get(gen.size()-1-(first_compare+i))+intermedia_gen1;
						}
					}
				}
			}
			if(hit/count_of_nucleotides*100>= percent_of_hits) {
				intermedia_gen= contain(intermedia_gen,intermedia_gen1);
				hit=0;
				Set<Integer> set = new HashSet<Integer>();
				for(int compare =0;compare<gen.size()-intermedia_gen.length()+1;compare++) {
					for(int number_of_nucleotid=0;number_of_nucleotid<intermedia_gen.length();number_of_nucleotid++) {
						if(gen.get(compare+number_of_nucleotid).equals(String.valueOf(intermedia_gen.charAt(number_of_nucleotid)))) {
							hit+=1;
						}

					}
					if(hit/count_of_nucleotides*100>= percent_of_hits2) {
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
			if(hit/res.length()*100>= percent_of_hits2) {
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

	/*�����, ����������� ���� �������� � ��������� � ���������� ������� ��� ����������� � ������
	 */
	public static ArrayList<String> fillArray() {
		ArrayList<String> st=new ArrayList<String>();
		st.add(repeats_in_a_row.get(0));
		for(int i=0;i<repeats_in_a_row.size()-1;i++) {
			st.add(spacers.get(i));
			st.add(repeats_in_a_row.get(i+1));
		}//System.out.println(st.size());
		return st;
	}

	/*�������� �� ������������ ������� ������
	 *@param input ������ �������
	 */
	private static String checkData(String input) {
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

	public static boolean rightData(String Data){
		if(Data.length()>=80&&Data.contains("a")||Data.contains("A")||Data.contains("g")||Data.contains("G")
				||Data.contains("c")||Data.contains("C")||Data.contains("t")||Data.contains("T")){
			if(!checkData(Data).equals("false")){
				return true;
			}
		}
		return false;
	}
	/*
	 * ����� ���������� ���� ������� ����� �� �����. ���������� ���������� ���������
	 * @param count_of_nucleotids ������ �������
	 * @param first_compare ����� 1 ������� 1 �������
	 * @param second_compare ����� 1 ������� 2 �������
	 * @param genbank ������ �� ������� ���� ������, �������������� ����� �����
	 */
	private static int compare(int count_of_nucleotids, int first_compare, int second_compare, ArrayList<String> genbank) {//
		int schetchik = 0;
		for(int number_of_nucleotid=0;number_of_nucleotid<count_of_nucleotids;number_of_nucleotid++) {
			if(genbank.get(first_compare+number_of_nucleotid).equals(genbank.get(second_compare+number_of_nucleotid))) {
				schetchik+=1;
			}
		}
		return schetchik;
	}


	/*
	 * ����� ���������� ���� ������� ������ �� ����. ���������� ���������� ���������
	 * @param count_of_nucleotids ������ �������
	 * @param first_compare ����� 1 ������� 1 �������
	 * @param second_compare ����� 1 ������� 2 �������
	 * @param genbank ������ �� ������� ���� ������, �������������� ����� �����
	 */
	private static int compare1(int count_of_nucleotids, int first_compare, int second_compare, ArrayList<String> genbank) {
		int schetchik = 0;
		for(int number_of_nucleotid=0;number_of_nucleotid<count_of_nucleotids;number_of_nucleotid++) {
			if(genbank.get(first_compare-number_of_nucleotid).equals(genbank.get(second_compare-number_of_nucleotid))) {
				schetchik+=1;
			}
		}
		return schetchik;
	}


	/*���������� ���������
	 */
	private static void split_to_spacers() {
		for(int i =1;i<input.split(" ").length;i++) {
			spacers.add(input.split(" ")[i]);
		}
	}

	/*
	 *���������� ����������� ������� ��������� ����� ���� �����
	 *@param s ������ ������ ��� ���������
	 *@param m ������ ������ ��� ���������
	 */
	private static String contain(String s, String m) {
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
	 * �������� �� ������������ �������� � ������. ���������� ������ � ����������� ��������� � ������
	 * @param st ������ ������� ����� ���������
	 * @param set ������, ������� �� st, �� ������� ���� ���������
	 * @param procent ������� ���������, ���������� � ������
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
	 * �������� �� ������������ �������. ���������� ������ � ���������� ��������
	 * @param st ������ ������� ����� ���������
	 * @param set ������, ������� �� st, �� ������� ���� ���������
	 * @param procent ������� ���������, ���������� � ������
	 * @param number ����� ������������ �������
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