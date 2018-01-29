import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class main{

    public static void main(String[] args) {

	Scanner scan =new Scanner(System.in);
	String s[]=new String[5];
	System.out.println("Please enter the 3 links of dataset and 2 prune values");
	int k=0;
	while(k<5){
		s[k]=scan.nextLine();
		k++;
	}
	Constant.training=s[0];
	Constant.validation=s[1];
	Constant.test=s[2];
	//Constant.pruneLeft= Double.parseDouble(s[3]);
	//Constant.pruneLeft= Double.parseDouble(s[4]);
	ArrayList<String> dataList=new ArrayList<String>();
	Import im=new Import();
	DecisionTree iD3= new DecisionTree();
	dataList=im.import1(Constant.training);
	HashMap<Integer,Node> nodeMap=iD3.dataToRoot(dataList);
	
	iD3.splitRoot(nodeMap);
	System.out.println(nodeMap.get(1));
	System.out.println("Decission tree before pruning ");
	    System.out.println();
	      //btree.printNode(mapNodes.get(1));
	    System.out.println();
	    System.out.println();
	    System.out.println(" ------PRE-PRUNING ----");
	    System.out.println("Total Instances on training data : "+nodeMap.get(1).getData().size());
	    System.out.println(" Total attributes on training data : "+(nodeMap.get(1).getFeatures().size()-1));
	    System.out.println(" Total number of nodes: "+(nodeMap.size()));
	    int leafNodes=0;
	    for(int i : nodeMap.keySet())
	    {
	    	      if((nodeMap.get(i).getLeft()==null)&&(nodeMap.get(i).getRight()==null))
	    	    	  leafNodes++;
	      }
	      System.out.println("Total leafNodes in training data :"+leafNodes);
	
	
	//HashMap<Integer, Node> mapNodes=iD3.importRoot(Constant.training_path);
	//iD3.splitNodes(mapNodes);
	//int root=iD3.calculateEntropy(dataList);
	//System.out.println("etropy"+iD3.calculateEntropy(b));
	
	
		


}


}