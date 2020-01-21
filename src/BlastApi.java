import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BlastApi {

    public static ArrayList<String> number_of_genome=new ArrayList<String>();
	private static boolean PreviousRidfound=false;
	/*
	 * ���������� ������� �������� ���������-��� �����
	 * @param query ������� ������� ��� �����������
	*/
	public static Map<String,String> request(String query) throws InterruptedException {
    	Map<String,String> Genome=new HashMap<String,String>();
        /*
        * QUERY: GGGTGGTTGGCTGACGCATCGCAATATTAA
DATABASE: unclassified bacteriophages (taxid:12333), bacteriophages (taxid:38018) ; and exclude: bacteria (taxid:2)
PROGRAM: blastn
FORMAT_TYPE: JSON2
        * */
		 resultPanel.Status("BLASTing spacers");
        try {
			boolean avaliable = false;
			String mbRID=checkForPreviousAttempts(query);
			String RID;
			String s;
        	if(PreviousRidfound&&checkRID(mbRID)){
				RID=mbRID;
				avaliable = true;
			}else {

				s = firsth_output(query);
				//System.out.println(s);
				s = s.substring(s.lastIndexOf("QBlastInfoBegin") + 16, s.lastIndexOf("QBlastInfoEnd")).replace("/n", "").replace(" ", "");
				RID = s.substring(4, s.lastIndexOf("RTOE")).replace("/n", "").replace(" ", "");
				writeRid(query,mbRID,RID.replace("\n",""));
				System.out.println(RID);
				String RTOE = s.substring(s.lastIndexOf("RTOE") + 5, s.length() - 1).replace("/n", "").replace(" ", "");

				Thread.sleep(Integer.parseInt(RTOE) * 1000);
				s = output("https://blast.ncbi.nlm.nih.gov/blast/Blast.cgi?CMD=Get&FORMAT_OBJECT=SearchInfo&RID=" + RID, "GET");
				boolean g = true;
				int attemp = 0;
				while (g) {
					attemp++;
					Thread.sleep(10000);
					s = output("https://blast.ncbi.nlm.nih.gov/blast/Blast.cgi?CMD=Get&FORMAT_OBJECT=SearchInfo&RID=" + RID, "GET");
					if (s.contains("Status=WAITING")) {
						System.out.println("wait");
					} else if (s.contains("Status=FAILED")) {
						g = false;
						System.out.println("FAILED+6");

					} else if (s.contains("Status=UNKNOWN")) {
						g = false;
						System.out.println("UNKNOWN");
					} else if (s.contains("Status=READY")) {
						//System.out.println(s);
						if (s.contains("ThereAreHits=yes")) {
							//System.out.println(s);
							g = false;
							avaliable = true;
						} else {
							//System.out.println(s);
							System.out.println("No results");
							avaliable = false;
							g = false;
						}
					}
					if (attemp >= 50) {
						g = false;
						avaliable = false;
					}
				}
			}
            if(avaliable) {
            	Date date = new Date();
            	removeRID(query);
				writeRid(query,date.getYear()+","+ date.getMonth()+"."+date.getDay()+"-"+date.getHours(),RID.replace("\n",""));
				System.out.println(RID);
				s=output("https://blast.ncbi.nlm.nih.gov/blast/Blast.cgi?CMD=Get&FORMAT_TYPE=Text&RID="+RID,"GET");
            	//System.out.println(s);
            resultPanel.Status("Downloading viral genome");
            //System.out.println(s);
            s=s.substring(s.indexOf("ALIGNMENTS"));
           
            while(s.contains(">")) {
            	s=s.substring(s.indexOf(">")+1);
            	System.out.println((s.substring(0,s.indexOf("\n"))).substring(0,s.indexOf(" ")));

            	number_of_genome.add((s.substring(0,s.indexOf("\n"))).substring(0,s.indexOf(" ")));
            }

            for(int i=0;i<number_of_genome.size();i++) {
            	Thread.sleep(300);
            	String m=output("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nuccore&id="+number_of_genome.get(i)+"&rettype=fasta&retmode=text","GET");
            	Genome.put(m.substring(0,m.indexOf("\n")),m.substring(m.indexOf("\n")));
            }
            return Genome;
            }
            } catch (IOException e) {
            e.printStackTrace();
            resultPanel.Error("BLAST_API_ERROR");
            return null;
        }
		return Genome;
    }
	
	
	private static String checkForPreviousAttempts(String query){
		String fileName = "src/res/Data/Rids";
		String content = "";
		Date date = new Date();
		String s =date.getYear()+","+ date.getMonth()+"."+date.getDay()+"-"+date.getHours();
		try {
			content = Files.lines(Paths.get(fileName)).reduce("", String::concat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(content.contains(query)){
			content=content.substring(content.indexOf(query)-1);
			if(subS(content,"[",",").equals(String.valueOf(date.getYear()))&&subS(content,",",".").equals(String.valueOf(date.getMonth()))&&
					(subS(content,".","-").equals(String.valueOf(date.getDay()))||
							(date.getYear()<Integer.valueOf(subS(content,"-","]")).intValue()&&Integer.valueOf(subS(content,".","-")).intValue()-date.getDay()<=1))){
				PreviousRidfound=true;
				return subS(content,"{","}");
			}else{
				removeRID(query);
			}
		}
		PreviousRidfound=false;
		return s;
	}

	private static boolean checkRID(String RID){
		try {
			return output("https://blast.ncbi.nlm.nih.gov/blast/Blast.cgi?CMD=Get&FORMAT_TYPE=Text&RID="+RID,"GET").contains("ALIGNMENTS");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void writeRid(String query,String date,String RID){
		String fileName = "src/res/Data/Rids";
		String content = null;
		try {
			content = Files.lines(Paths.get(fileName)).reduce("", String::concat);
		} catch (IOException e) {
			e.printStackTrace();
		}



		try(FileWriter writer = new FileWriter("src/res/Data/Rids", false))
		{
			writer.write(content+"<"+query+">"+"["+date+"]"+"{"+RID+"}\n");
			writer.append('\n');
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void removeRID(String query){
		String fileName = "src/res/Data/Rids";
		String content = "";
		try {
			content = Files.lines(Paths.get(fileName)).reduce("", String::concat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (content.contains(query)) {
			String w = content.substring(content.indexOf(query)-1);
			w=w.substring(0,w.indexOf("}")+1);
			content=content.replace(w,"");
		}


		try(FileWriter writer = new FileWriter("src/res/Data/Rids", false))
		{
			writer.write(content);
			writer.append('\n');
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String subS(String st, String s1, String s2){
		return st.substring(st.indexOf(s1)+1,st.indexOf(s2));
	}











	/*
	 * ���������� ���������� ���������� �� ������
	 * @param link ������ ��� ��������� ����������
	 * @param method ����� �������
	*/
	public static String output(String link,String method) throws IOException {
		  String s=null;
		try {
		 URL url = new URL(link);
		
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod(method);
         connection.setDoOutput(true);
         connection.setDoInput(true);
         connection.setUseCaches(false);
         InputStream inputStream = connection.getInputStream();
         ByteArrayOutputStream result = new ByteArrayOutputStream();
         byte[] buffer = new byte[1024];
         int length;
         while ((length = inputStream.read(buffer)) != -1) {
             result.write(buffer, 0, length);
         }
          s=result.toString("UTF-8");
		} catch (MalformedURLException e) {
			resultPanel.Error("REQUEST_ERROR");
			e.printStackTrace();
		}
		return s;
	}
	
	
	
	/*�������� ���������� �� ������ 
	 * @param query �������, ������� ���� �����������
	*/
	public static String firsth_output(String query) throws IOException {
		ByteArrayOutputStream result = null;
		try {
		URL url = new URL("https://blast.ncbi.nlm.nih.gov/blast/Blast.cgi");
		
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
       
			String h = "QUERY=" + query + "&DATABASE=nt"+ "&PROGRAM=blastn"+"&Q_MENU=bacteriophage (taxid:38018)"+ "&NUM_ORG=2"   +"&EQ_MENU1=bacteria (taxid:2)" +"&ORG_EXCLUDE1=on" 
										 +"&MEGABLAST=on"+"&Content-Type=application/x-www-form-urlencoded"+"&CMD=Put"+"&FILTER=L"+"&FILTER=m"+"&FORMAT_NUM_ORG=1";
        
			//String m = "QUERY=" + query + "&DATABASE=nt"+ "&PROGRAM=blastn" +"&MEGABLAST=on"+"&Content-Type=application/x-www-form-urlencoded"+"&CMD=Put";
			//System.out.println(h);
       //  (txid38018 [ORGN]) NOT (txid2 [ORGN])
     
       // map.put("SHORT_QUERY_ADJUST", "on");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        try {
            connection.getOutputStream().write(h.getBytes());
        } catch (IOException e) {
        	resultPanel.Status("CONNECTION_ERROR");
            e.printStackTrace();
        }

        InputStream inputStream = connection.getInputStream();
         result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
		} catch (MalformedURLException e1) {
			resultPanel.Status("CONNECTION_ERROR");
			e1.printStackTrace();
		}
		return result.toString("UTF-8");
	}

}
