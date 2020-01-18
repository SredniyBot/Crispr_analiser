import java.io.*;
import java.net.*;
import java.util.HashMap;

class FindProtein {
    public static HashMap<String,HashMap<String,String>> map=new HashMap<String, HashMap<String,String>>();


    public static void input(String s) {
        s+="\n";
        while(s.contains("_")) {
            for(int i =1;i<=4;i++) {
                try {
                    if(!output("https://www.ncbi.nlm.nih.gov/ipg/?term=cas9+"+
                            s.substring(0,s.indexOf("\n")-1)
                            +i).contains("following term was not found")) {
                        map.put(s.substring(0,s.indexOf("\n")), find(s.substring(0,s.indexOf("\n")-1)+i));
                        s=s.substring(s.indexOf("\n")+1);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ExportText.write(map);
    }


    public static HashMap<String, String> find(String casset) {
        HashMap<String, String> cas = new HashMap<String, String>();
        try {

            String s=output("https://www.ncbi.nlm.nih.gov/ipg/?term=cas9+"+casset);
            if(s.contains("following term was not found")){

            }else {
                while(s.contains("QUERY=")) {
                    s=s.substring(s.indexOf("QUERY=")+6);
                    String f=output("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=.."+s.substring(0,s.indexOf("&"))+"&rettype=fasta&retmode=text");
                    f=f.substring(f.indexOf("\n")).replaceAll("\n", "");
                    cas.put(f,s.substring(0,s.indexOf("&")) );
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cas;
    }


    public static String output(String link) throws IOException {
        String s=null;
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
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
        return s;
    }
}