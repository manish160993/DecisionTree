import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Import{
	
	public ArrayList<String> ValueList = new ArrayList<String>();
	ArrayList<String> import1(String s){
        ArrayList<String> data=new ArrayList<String>();
		try {
			BufferedReader br = null;
			String line = "";
            br = new BufferedReader(new FileReader(s));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                data.add(line);
				}
			}catch(Exception e){
			System.out.println(e);
			}
		return data;

	}
	HashMap<Integer,String> Features(ArrayList<String> a){
		HashMap<Integer,String> featureKey=new HashMap();
		String columns[]=a.get(0).split(",");
		for(int i=0;i<columns.length;i++){
			featureKey.put(i,columns[i]);
		}
		return featureKey;
	}
	
	public ArrayList<String> getInstances(ArrayList<String> lines) {
		

		for (int line = 1; line < lines.size(); line++) {
			ValueList.add(lines.get(line));

		}

		return ValueList;

	}
		
}