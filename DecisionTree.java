import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class DecisionTree{
	Test t=new Test();
	public Node root=new Node();
	Import im=new Import();
	
	//ArrayList<String> dataList=new ArrayList<String>();
	HashMap<Integer,String> nodeFeatureKey=new HashMap<Integer,String>();
	HashMap<Integer,Node> nodeMap=new HashMap<Integer,Node>();
	HashMap<Integer,Node> nodeMap1=new HashMap<Integer,Node>();
	HashMap<Integer,Node> dataToRoot(ArrayList<String> CSV){
		root.features=im.Features(CSV);
		root.index=1;
		root.data=im.getInstances(CSV);
		root.parent=null;
		nodeMap.put(root.index,root);
		//System.out.println(root.data.size()+"rootsize1");
	return nodeMap;
	}
	/* Can be used to increase accuracy
	HashMap<Integer,Node> dataToRoot1(ArrayList<String> CSV){
		root.features=im.Features(CSV);
		root.index=1;
		//root.data=im.getInstances(CSV);
		root.parent=null;
		nodeMap1.put(root.index,root);
		root.data=t.add(root);
		//im.print();
		//for(String s:t.testCases)
		//{root.data.add(s);
	//System.out.println(s);
		//}
		System.out.println(root.data.size()+"rootsize");
	return nodeMap1;
	}
	*/
	
	
	///// Split the nodes and build decision tree/////
	void splitRoot(HashMap<Integer,Node> nodeMap,int whichGain){
		int max= Collections.max(nodeMap.keySet());
		int gain=0;
		for(int i=1;i<=max;i++){
			if(nodeMap.get(i)==null)
				continue;
			
		//System.out.println("In node index :"+i);
		//System.out.println("Attributes are :"+mapNodes.get(i).getAttributeKey()+" no of attribute are :"+mapNodes.get(i).getAttributeKey().size());
			Node currentNode= nodeMap.get(i);
			currentNode.setTotalZeroes(noOfZeroes(currentNode.data));
			currentNode.setTotalOnes(noOfOnes(currentNode.data));
			currentNode.setPurity(getNodeLabel(currentNode));
			if(whichGain==1)
					currentNode.setDecisionKey(calInfoGain(currentNode));
				else
					currentNode.setDecisionKey(calImpurityGain(currentNode));
			nodeFeatureKey=new HashMap<Integer,String>(currentNode.getFeatures());
			nodeFeatureKey.remove(currentNode.getDecisionKey());
			if(nodeFeatureKey.size()==1)
				currentNode.setPurity(true);
			if(!currentNode.getPurity()){
				
				
				
				//System.out.println(currentNode.decisionKey+"key");
				
					
				//System.out.print(currentNode.getDecisionKey()+" " );
				if((!getLeftData(currentNode).isEmpty())&&(getLeftData(currentNode).size()!=(currentNode.getData().size()))){
					currentNode.setLeft(new Node());
					currentNode.getLeft().setParent(currentNode);
					currentNode.getLeft().setIndex(2*currentNode.getIndex());
					currentNode.getLeft().setFeatures(nodeFeatureKey);
					currentNode.getLeft().setData(getLeftData(currentNode));
				
					nodeMap.put(currentNode.getLeft().getIndex(), currentNode.getLeft());
				}
				if((!getRightData(currentNode).isEmpty())&&(getRightData(currentNode).size()!=(currentNode.getData().size()))){
					currentNode.setRight(new Node());
					currentNode.getRight().setParent(currentNode);
					currentNode.getRight().setIndex(2*currentNode.getIndex()+1);
					currentNode.getRight().setFeatures(nodeFeatureKey);
					currentNode.getRight().setData(getRightData(currentNode));
				
					nodeMap.put(currentNode.getRight().getIndex(), currentNode.getRight());
				}
				
			}
			
	
			max= Collections.max(nodeMap.keySet());
			//it = nodeMap.entrySet().iterator();
		}
			
		
	}
	
	ArrayList<String> getLeftData(Node currentNode){
		ArrayList<String> leftData=new ArrayList<String>();
		for(String lines:currentNode.getData()){
			String columns[]=lines.split(",");
			if(columns[currentNode.getDecisionKey()].equalsIgnoreCase("0"))
			{
				leftData.add(lines);
			}
		}
		return leftData;
	}
	ArrayList<String> getRightData(Node currentNode){
		ArrayList<String> rightData=new ArrayList<String>();
		for(String lines:currentNode.getData()){
			String columns[]=lines.split(",");
			if(columns[currentNode.getDecisionKey()].equalsIgnoreCase("1"))
			{
				rightData.add(lines);
			}
		}
		return rightData;
	}
	
	void printNode(Node currentNode)
	{
		int depth=0;
		if(!currentNode.getPurity())
		{   
			System.out.println();
			System.out.print(printDash(currentNode));
			System.out.print(currentNode.getSplit());
			System.out.print(" = ");
			System.out.print("0 :");
			
			if(currentNode.getLeft()!=null)
			  printNode(currentNode.getLeft());
			else
				System.out.print(currentNode.getLabel());
	
			System.out.println();
			System.out.print(printDash(currentNode));
			System.out.print(currentNode.getSplit());
			System.out.print(" = ");
			System.out.print("1 :");
			
			if(currentNode.getRight()!=null)
		        printNode(currentNode.getRight());
			else
				System.out.print(currentNode.getLabel());
			
		}
		else
			System.out.print(currentNode.getLabel());
		
	}
	   
	private String printDash(Node currentNode) {
		String dash;
		int depth=0;
		int index=currentNode.getIndex();
		
		while(index>1){
			if (index%2==0){
				index=index/2;
				depth++;
				continue;
			}
		    if (index%2!=0){
		    	index=(index-1)/2;
		    	depth++;
		    	continue;
		    }
		}
		dash="";
		for(int i=0;i<depth;i++){
			dash+="|";
		}
		return dash;
	}
	
	int calInfoGain(Node currentNode){
		HashMap<Integer,Double> calculateChildInformationGain=new HashMap<Integer,Double>();
		double parentEntropy=calculateEntropy(currentNode.totalZeroes,currentNode.totalOnes);
		for (int index : currentNode.getFeatures().keySet())
		{
			if(index!=(root.getFeatures().size()-1))
			{
			
			ArrayList<String> featureZero=new ArrayList<String>();
			ArrayList<String> featureOne=new ArrayList<String>();
			for (int line=0; line< currentNode.getData().size();line++)
			{
				String[] columns= currentNode.getData().get(line).split(",");
				if( columns[index].equalsIgnoreCase("0")) {
					featureZero.add(currentNode.data.get(line));
				}
				if(columns[index].equalsIgnoreCase("1")) {
					featureOne.add(currentNode.data.get(line));
				}
				
			}
			double childEntropy=calculateChildEntropy(featureZero,featureOne);
			double IG=parentEntropy-childEntropy;
			//System.out.println("Index:"+index+" ----> childInfoGain: "+ IG);
			calculateChildInformationGain.put(index,parentEntropy-childEntropy);

			}
		}
		int decisionKey=findDecisionKey(currentNode,calculateChildInformationGain);
		return decisionKey;
		
	}
	int calImpurityGain(Node currentNode){
		HashMap<Integer,Double> calculateChildImpurityGain=new HashMap<Integer,Double>();
		double parentImpurity=calculateImpurity(currentNode.totalZeroes,currentNode.totalOnes);
		for (int index : currentNode.getFeatures().keySet())
		{
			if(index!=(root.getFeatures().size()-1))
			{
			
			ArrayList<String> featureZero=new ArrayList<String>();
			ArrayList<String> featureOne=new ArrayList<String>();
			for (int line=0; line< currentNode.getData().size();line++)
			{
				String[] columns= currentNode.getData().get(line).split(",");
				if( columns[index].equalsIgnoreCase("0")) {
					featureZero.add(currentNode.data.get(line));
				}
				if(columns[index].equalsIgnoreCase("1")) {
					featureOne.add(currentNode.data.get(line));
				}
				
			}
			double childImpurity=calculateChildImpurty(featureZero,featureOne);
			double IG=parentImpurity-childImpurity;
			
			//System.out.println("Index:"+index+" ----> childInfoGain: "+ IG);
			calculateChildImpurityGain.put(index,parentImpurity-childImpurity);

			}
		}
		int decisionKey=findDecisionKey(currentNode,calculateChildImpurityGain);
		return decisionKey;
		
	}
	
	int findDecisionKey(Node currentNode,HashMap<Integer,Double> calculateGain){
		double maxIG=Collections.max(calculateGain.values());
		
		for(int index : calculateGain.keySet()) {
			if(calculateGain.get(index)==maxIG&&(index!=(root.features.size()-1))&&!calculateGain.get(index).isNaN())
			{
				
				currentNode.decisionKey=index;
				currentNode.split=currentNode.features.get(index);
				//System.out.println("SplitKey : "+currentNode.split);
				//System.out.println(maxIG+" "+currentNode.getSplit());
				return currentNode.decisionKey;
			}
			
		}
		//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<default decsi"+currentNode.decisionKey);
		return currentNode.decisionKey;
	
		
		//System.out.println("Max IG: "+maxIG);
	}
	
	
	
	double calculateChildEntropy(ArrayList<String> featureZero,ArrayList<String> featureOne){
		double value0forFeatureZero=noOfZeroes(featureZero);
		double value1forFeatureZero=noOfOnes(featureZero);
		double value0forFeatureOne=noOfZeroes(featureOne);
		double value1forFeatureOne=noOfOnes(featureOne);
		double fractAttribute0=(value0forFeatureZero+value1forFeatureZero)/(value0forFeatureZero+value1forFeatureZero+value0forFeatureOne+value1forFeatureOne);
		double fractAttribute1=(value0forFeatureOne+value1forFeatureOne)/(value0forFeatureZero+value1forFeatureZero+value0forFeatureOne+value1forFeatureOne);
		double childEntropy=0;
		if((value0forFeatureZero!=0)&&(value1forFeatureZero!=0)&&(value0forFeatureOne!=0)&&(value1forFeatureOne!=0)){
		    childEntropy=(fractAttribute0)*(-(value0forFeatureZero/(value0forFeatureZero+value1forFeatureZero))*((Math.log(value0forFeatureZero/(value0forFeatureZero+value1forFeatureZero)))/Math.log(2)) - (value1forFeatureZero/(value0forFeatureZero+value1forFeatureZero))*(Math.log((value1forFeatureZero/(value0forFeatureZero+value1forFeatureZero)))/Math.log(2)))+(fractAttribute1)*(-(value0forFeatureOne/(value0forFeatureOne+value1forFeatureOne))*(Math.log((value0forFeatureOne/(value0forFeatureOne+value1forFeatureOne)))/Math.log(2)) - (value1forFeatureOne/(value0forFeatureOne+value1forFeatureOne))*(Math.log((value1forFeatureOne/(value0forFeatureOne+value1forFeatureOne)))/Math.log(2)));
		}
		if(((value0forFeatureZero==0)||(value1forFeatureZero==0))&&(value0forFeatureOne!=0)&&(value1forFeatureOne!=0)){
		   	childEntropy=(fractAttribute1)*(-(value0forFeatureOne/(value0forFeatureOne+value1forFeatureOne))*(Math.log((value0forFeatureOne/(value0forFeatureOne+value1forFeatureOne)))/Math.log(2)) - (value1forFeatureOne/(value0forFeatureOne+value1forFeatureOne))*(Math.log((value1forFeatureOne/(value0forFeatureOne+value1forFeatureOne)))/Math.log(2)));
		}
	    if((value0forFeatureZero!=0)&&(value1forFeatureZero!=0)&&((value0forFeatureOne==0)||(value1forFeatureOne==0))){
	    	childEntropy=(fractAttribute0)*(-(value0forFeatureZero/(value0forFeatureZero+value1forFeatureZero))*((Math.log(value0forFeatureZero/(value0forFeatureZero+value1forFeatureZero)))/Math.log(2)) - (value1forFeatureZero/(value0forFeatureZero+value1forFeatureZero))*(Math.log((value1forFeatureZero/(value0forFeatureZero+value1forFeatureZero)))/Math.log(2)));
	    }	
	return childEntropy;
	}
	double calculateChildImpurty(ArrayList<String> featureZero,ArrayList<String> featureOne){
		double value0forFeatureZero=noOfZeroes(featureZero);
		double value1forFeatureZero=noOfOnes(featureZero);
		double value0forFeatureOne=noOfZeroes(featureOne);
		double value1forFeatureOne=noOfOnes(featureOne);
		double totalAttribute0=(value0forFeatureZero+value1forFeatureZero);
		double totalAttribute1=(value0forFeatureOne+value1forFeatureOne);
		double childImpurity=0;
		if((value0forFeatureZero!=0)&&(value1forFeatureZero!=0)&&(value0forFeatureOne!=0)&&(value1forFeatureOne!=0)){
		    childImpurity=((totalAttribute0/(totalAttribute0+totalAttribute1))*(value0forFeatureZero*value1forFeatureZero)/(totalAttribute0*totalAttribute0))+((totalAttribute1/(totalAttribute0+totalAttribute1))*(value0forFeatureOne*value1forFeatureOne)/(totalAttribute1*totalAttribute1));
		}
		if(((value0forFeatureZero==0)||(value1forFeatureZero==0))&&(value0forFeatureOne!=0)&&(value1forFeatureOne!=0)){
		   	childImpurity=((totalAttribute1/(totalAttribute0+totalAttribute1))*(value0forFeatureOne*value1forFeatureOne)/(totalAttribute1*totalAttribute1));
		}
	    if((value0forFeatureZero!=0)&&(value1forFeatureZero!=0)&&((value0forFeatureOne==0)||(value1forFeatureOne==0))){
	    	childImpurity=((totalAttribute0/(totalAttribute0+totalAttribute1))*(value0forFeatureZero*value1forFeatureZero)/(totalAttribute0*totalAttribute0));
		}	
		//System.out.println(childImpurity+" CI");
	return childImpurity;
	}
	
	boolean getNodeLabel(Node currentNode){
		if(currentNode.totalZeroes==0||currentNode.totalOnes==0)
		{
			currentNode.setPurity(true);
			//System.out.print("true vali line");
			if(currentNode.totalOnes==0)
				currentNode.setLabel("0");
			if(currentNode.totalZeroes==0)
				currentNode.setLabel("1");
			
		}
		else {
			currentNode.setPurity(false);
			if(currentNode.totalZeroes>=currentNode.totalOnes)
				currentNode.setLabel("0");
			else
				currentNode.setLabel("1");
		}
		
		return currentNode.getPurity();
	    }
	
	double calculateEntropy(double totalZeroes,double totalOnes){
		double zero=totalZeroes; double one=totalOnes;
		double totalEntropy = -(zero/(zero+one))*(Math.log((zero/(zero+one)))/Math.log(2))-(one/(zero+one))*(Math.log((one/(zero+one)))/Math.log(2));
		return totalEntropy;
	}
	double calculateImpurity(double totalZeroes,double totalOnes){
		double zero=totalZeroes; double one=totalOnes;
		double totalImpurty = ((zero*one)/((zero+one)*(zero+one)));
		return totalImpurty;
	}
	
	double noOfZeroes(ArrayList<String> data){
		double sum=0;
		for(String lines:data){
			String columns[]=lines.split(",");
			if(columns[columns.length-1].equalsIgnoreCase("0"))
				sum++;
		}
		//System.out.println(root.features.size()+"rootsize"+" ");
		return sum;
	}
	
	double noOfOnes(ArrayList<String> data){
		double sum=0;
		for(String lines:data){
			String columns[]=lines.split(",");
			if(columns[columns.length-1].equalsIgnoreCase("1"))
				sum++;
		}
		//System.out.println(root.features.size()+"rootsize"+" ");
		return sum;
	}
}


