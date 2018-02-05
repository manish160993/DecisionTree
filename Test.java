import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Test{
	HashMap<Integer,String> testFeatures = new HashMap<Integer,String>();
	ArrayList<String> testData =new ArrayList<String>();
	ArrayList<String> trueFalse=new ArrayList<String>();
	Import im=new Import();
	//DecisionTree iD3= new DecisionTree();
	
	double testAccuracy(String fileCSV,Node node){
		ArrayList<String> lines=new ArrayList<String>();
		
		lines.clear();
		testData.clear();
		testFeatures.clear();
		double trueClassified=0;
		double falseClassified=0;
		double accuracy=0;
	 
		lines=im.import1(fileCSV);
		testData=im.getInstances(lines);
		testFeatures=im.Features(lines);
		
		trueFalse=checkTrueFalse(testFeatures,testData,node);
		
		for(int i=0;i<trueFalse.size();i++){	
			if(trueFalse.get(i).equalsIgnoreCase("1"))
				trueClassified++;
			else
				falseClassified++;
		}
		//System.out.println("True classified:>>>>"+trueClassified+" size"+trueFalse.size()+" "+testData.size());
		accuracy= (trueClassified/(trueClassified+falseClassified))*100;
		if(fileCSV.equalsIgnoreCase(Constant.validation)){
			//System.out.println("Total Instances on validation data : "+testData.size());
			//System.out.println(" Total attributes on validation data :" +(testFeatures.size()-1));
		}
		if(fileCSV.equalsIgnoreCase(Constant.test)){
			//System.out.println("Total Instances on test data : "+testData.size());
			//System.out.println(" Total attributes on test data :" +(testFeatures.size()-1));
		}
	 
	 return accuracy;
	}
	
	ArrayList<String> checkTrueFalse(HashMap<Integer,String> testFeatures,ArrayList<String> testData,Node node){
		String estimated=null;
		int classIndex=0;
		int testFeatureIndex=0;
		int i=0;
		ArrayList<String> trueFalse=new ArrayList<String>();
		for (Integer index : testFeatures.keySet())
		{
			//System.out.print(index+ " ");
			
			if(testFeatures.get(index).equalsIgnoreCase("Class"))
			{
				classIndex=index;
				//System.out.print(index+ " classindex ");
			}
		}
		//System.out.println();
		for(String line : testData)
		{
			Node currentNode = node;
			while(!(currentNode.getPurity())) {
				//System.out.println(" hi i am in current node"+(i++));
				//if(currentNode.leftChild!=null||currentNode.rightChild!=null)
				//{
				String s=currentNode.getSplit();
				
				for (Integer index : testFeatures.keySet())
				{
					if(testFeatures.get(index).equalsIgnoreCase(s))
					{
						testFeatureIndex=index;
					}
				}
				String [] columnValues=line.split(",");
				if(columnValues[testFeatureIndex].equalsIgnoreCase("0"))
				{
					if(currentNode.getLeft()!=null)
						currentNode=currentNode.getLeft();
					else 
					{
						estimated=currentNode.getLabel();
					break;
					}	
				}
				if(columnValues[testFeatureIndex].equalsIgnoreCase("1"))
				{
					if(currentNode.getRight()!=null)
						currentNode=currentNode.getRight();
					else {
						estimated=currentNode.getLabel();
						break;
					}	
				}	
			}
				estimated= currentNode.getLabel();
				String []colValues= line.split(",");
				if(estimated.equals(colValues[classIndex]))
				{trueFalse.add("1");
				
				}
				else
				{
					trueFalse.add("0");
					//Constant.testCases.add(line);
				}
					
			}
			//System.out.println(Constant.testCases.size()+ " testcase");
			//System.out.println(testData.size()+ " testDatae");
		//System.out.println("I am truely classified for : "+trueFalseClass.size());
		
		return trueFalse;		
	}
	
	
	////////////////Used to increase accuracy ... 
	ArrayList<String> add(Node root){
		int k=0;
		for(String s:Constant.testCases)
		{
			k++;
			//System.out.println(s);
			root.data.add(s);
		}
		System.out.println("Added data>>"+k);
		Constant.testCases.clear();
		return root.data;
	}
	void print(){
		for(String s:Constant.testCases)
			System.out.println(s);
	}
	
}
		
		