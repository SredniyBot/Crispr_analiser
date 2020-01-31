import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PAM_finder {
	public static ArrayList<String> pams = new ArrayList<String>();
	public static ArrayList<String> pams1 = new ArrayList<String>();
	public static Map<String,Integer> pam_rating= new  HashMap<String,Integer>();
	public static int procent=55;


	/*��������� ���� � ������
	 * @param Strings_with_pams ������ Set �0 �������� �� 10 ����������� � ������� ���������������� ��������� ���
	*/
	public static void add_pams(Set<String> Strings_with_pams) {
		for(String s:Strings_with_pams) {
			pams.add(s);
		}
	}
	public static void add_pams1(Set<String> Strings_with_pams) {
		for(String s:Strings_with_pams) {
			pams1.add(s);
		}
	}
	
	/*����������� ��� ����
	*/
	public static void try_to_find() {
			int pam_size=9;
			boolean size_found=false;
			for(int i=9;i>=3&&!size_found;i--) {
				int A=0;
				int T=0;
				int G=0;
				int C=0;
				for(String s:pams) {
					if(s.charAt(i)=='A') {
						A++;
					}else if(s.charAt(i)=='T') {
						T++;
					}else if(s.charAt(i)=='G') {
						G++;
					}else if(s.charAt(i)=='C') {
						C++;
					}
					
				}
				
				if((float)A/(float)pams.size()*100>=procent||(float)T/(float)pams.size()*100>=procent||
						(float)G/(float)pams.size()*100>=procent||(float)C/(float)pams.size()*100>=procent) {
					System.out.println(A+"  "+T+"  "+G+"  "+C+"  "+pams.size()+"  ");
					size_found=true;
				}
				pam_size=i;
			}
			ArrayList<String> g= new ArrayList<String>();
			for(String i:pams) {
				g.add(i.substring(0,pam_size+1));
			}

		//logo(g);

		
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			resultPanel.Error("THREAD_ERROR");
			e.printStackTrace();
		}
		pam_size=9;
		size_found=false;
		for(int i=9;i>=3&&!size_found;i--) {
			int A=0;
			int T=0;
			int G=0;
			int C=0;
			for(String s:pams1) {
				if(s.charAt(i)=='A') {
					A++;
				}else if(s.charAt(i)=='T') {
					T++;
				}else if(s.charAt(i)=='G') {
					G++;
				}else if(s.charAt(i)=='C') {
					C++;
				}
				
			}
			
			if((float)A/(float)pams1.size()*100>=procent||(float)T/(float)pams1.size()*100>=procent||
					(float)G/(float)pams1.size()*100>=procent||(float)C/(float)pams1.size()*100>=procent) {
				System.out.println(A+"  "+T+"  "+G+"  "+C+"  "+pams1.size()+"  ");
				size_found=true;
			}
			pam_size=i;
		}
		ArrayList<String> h= new ArrayList<String>();
		for(String i:pams1) {
			h.add(i.substring(0,pam_size+1));
		}
		WebLogo.logos(h,g);
	}
	
	
}
