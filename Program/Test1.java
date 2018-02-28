import java.util.ArrayList;
import java.util.HashMap;

public class Test1 {
	HashMap<Integer,String> testDataattribute=new HashMap<Integer,String>();
	ArrayList<String> testDataValues=new ArrayList<String>();
	ArrayList<String> lines = new ArrayList<String>();
 public double testAccuracy(String filePath, Node node)
 {
	 
	 
	
	 Import imp=new Import();
	 
	 lines=imp.import1(filePath);
	 double noOftrueClassified=0;
	 double noOfFalseClassified=0;
	 double perCentofAccuracy=0;
	 
	 
	 testDataattribute=getTestDataAttribute(lines);
	 testDataValues=getTestDataValues(lines);
	 ArrayList<String> trueFalseClass=estimateClass(testDataattribute,testDataValues,node);
	 for (int i=0;i<trueFalseClass.size();i++)
	 {
		 if(trueFalseClass.get(i).equalsIgnoreCase("1"))
		 {
		 noOftrueClassified++;System.out.print(noOftrueClassified+" : true value"+ trueFalseClass.size());}
			 
		 else
			 noOfFalseClassified++;
	 }
	 System.out.println("");
	 perCentofAccuracy= (noOftrueClassified/(noOftrueClassified+noOfFalseClassified))*100;
	 if(filePath.equalsIgnoreCase(Constant.validation))
	 {
		 System.out.println("Total Instances on validation data : "+testDataValues.size());
	     System.out.println(" Total attributes on validation data :" +(testDataattribute.size()-1));
	 }
	 if(filePath.equalsIgnoreCase(Constant.test))
	 {
		 System.out.println("Total Instances on test data : "+testDataValues.size());
	     System.out.println(" Total attributes on test data :" +(testDataattribute.size()-1));
	 }
	 
	 
	 return perCentofAccuracy;
	 
 }

private ArrayList<String> estimateClass(HashMap<Integer, String> testDataattribute2, ArrayList<String> testDataValues2, Node node) {
	
	String estimatedClass=null;
	int classIndex=0;
	int testDataattributeindex=0;
	int i=0;
	ArrayList<String> trueFalseClass=new ArrayList<String>();
	for (Integer SplitIndex : testDataattribute2.keySet())
	{
		
		if(testDataattribute2.get(SplitIndex).equalsIgnoreCase("Class"))
		{
			classIndex=SplitIndex;
		}
	}
	for(String line : testDataValues2)
	{
		Node currentNode = node;
		while(!(currentNode.purity)) {
			//System.out.println(" hi i am in current node"+(i++));
			//if(currentNode.leftChild!=null||currentNode.rightChild!=null)
			//{
			String s=currentNode.split;
			
			for (Integer SplitIndex : testDataattribute2.keySet())
			{
				if(testDataattribute2.get(SplitIndex).equalsIgnoreCase(s))
				{
					testDataattributeindex=SplitIndex;
				}
			}
			String [] columnValues=line.split(",");
			if(columnValues[testDataattributeindex].equalsIgnoreCase("0"))
			{
				if(currentNode.getLeft()!=null)
					currentNode=currentNode.left;
				else 
				{
					estimatedClass=currentNode.getLabel();
				break;
				}
					
					
			}
			if(columnValues[testDataattributeindex].equalsIgnoreCase("1"))
			{
				if(currentNode.getRight()!=null)
					currentNode=currentNode.right;
				else {estimatedClass=currentNode.getLabel();
				break;}
					
			}
			
			}
			
		    estimatedClass= currentNode.getLabel();
		    String []colValues= line.split(",");
			if(estimatedClass.equalsIgnoreCase(colValues[classIndex]))
				trueFalseClass.add("1");
			else
				trueFalseClass.add("0");
		}
		
	//System.out.println("I am truely classified for : "+trueFalseClass.size());
	return trueFalseClass;		
	}// TODO Auto-generated method stub
	

private ArrayList<String> getTestDataValues(ArrayList<String> lines) {
	for (int i=1;i<lines.size();i++)
	{
		testDataValues.add(lines.get(i));
	}
	return testDataValues;
}

private HashMap<Integer, String> getTestDataAttribute(ArrayList<String> lines) {
	String[] line =lines.get(0).split(",");// TODO Auto-generated method stub
	for(int i=0;i<line.length;i++)
	{
		testDataattribute.put(i, line[i].trim().toString());
	}
	return testDataattribute;
}
}
