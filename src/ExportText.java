import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ExportText {

    public static void write(HashMap<String, HashMap<String, String>> data) {
        String output = "";
        String fa = "";
        for(String value1 : data.keySet()) {
            output+=" "+value1+"\n";
            for(String value2 : data.get(value1).keySet()) {
                fa+=">"+data.get(value1).get(value2)+"\n"+value2+"\n";
                output+=" "+data.get(value1).get(value2)+"\n";
                output+=" "+value2+"\n";
            }
        }
        try(FileWriter writer = new FileWriter("result.txt", false)){
            writer.write(output);
            System.out.println(output);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }


        try(FileWriter writer = new FileWriter("forTrees.fa", false)){
            writer.write(fa);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }





}