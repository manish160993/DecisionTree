import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class DecisionTree{

	Node root=new Node();
	Import im=new Import();
	//ArrayList<String> dataList=new ArrayList<String>();
	HashMap<Integer,String> nodeFeatureKey=new HashMap<Integer,String>();
	HashMap<Integer,Node> nodeMap=new HashMap<Integer,Node>();
	HashMap<Integer,Node> dataToRoot(ArrayList<String> CSV){
		root.features=im.Features(CSV);
		root.index=1;
		root.data=im.getInstances(CSV);
		root.parent=null;
		nodeMap.put(root.index,root);
	return nodeMap;
	}
	
	void splitRoot(HashMap<Integer,Node> nodeMap){
		int max= Collections.max(nodeMap.keySet());
		//while (it.hasNext()) {
			//Map.Entry pair = (Map.Entry)it.next();
			//System.out.println(pair.getKey() + " = " + pair.getValue());
			//it.remove(); // avoids a ConcurrentModificationException
			//Node currentNode=(Node)pair.getValue();
			int i;
		
		for(i=1;i<=max;i++){
			if(nodeMap.get(i)==null)
				continue;
		//System.out.println("In node index :"+i);
		//System.out.println("Attributes are :"+mapNodes.get(i).getAttributeKey()+" no of attribute are :"+mapNodes.get(i).getAttributeKey().size());
			Node currentNode= nodeMap.get(i);
			currentNode.setTotalZeroes(noOfZeroes(currentNode.data));
			currentNode.setTotalOnes(noOfOnes(currentNode.data));
			boolean isPure=getNodeLabel(currentNode);
			if(!isPure){
				currentNode.setDecisionKey(calInfoGain(currentNode));
				System.out.println(currentNode.decisionKey+"key");
				nodeFeatureKey=new HashMap<Integer,String>(currentNode.getFeatures());
				nodeFeatureKey.remove(currentNode.getDecisionKey());
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
			
			//currentNode.entropy.put(currentNode.index,e);
			System.out.println(currentNode.totalZeroes+"  -----  "+currentNode.totalOnes+"------------"+currentNode.decisionKey+" max:"+max);
			//break;
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
	
	int calInfoGain(Node currentNode){
		HashMap<Integer,Double> calculateChildInformationGain=new HashMap<Integer,Double>();
		double parentEntropy=calculateEntropy(currentNode.totalZeroes,currentNode.totalOnes);
		for (int index : currentNode.features.keySet())
		{
			if(index!=(root.features.size()-1))
			{
			
			ArrayList<String> featureZero=new ArrayList<String>();
			ArrayList<String> featureOne=new ArrayList<String>();
			for (int line=0; line< currentNode.data.size();line++)
			{
				String[] columns= currentNode.data.get(line).split(",");
				if( columns[index].equalsIgnoreCase("0")) {
					featureZero.add(currentNode.data.get(line));
				}
				if(columns[index].equalsIgnoreCase("1")) {
					featureOne.add(currentNode.data.get(line));
				}
				
			}
			double childEntropy=calculateChildEntropy(featureZero,featureOne);
			double IG=parentEntropy-childEntropy;
			System.out.println("Index:"+index+" ----> childInfoGain: "+ IG);
			calculateChildInformationGain.put(index,parentEntropy-childEntropy);

			}
		}
		int decisionKey=findDecisionKey(currentNode,calculateChildInformationGain);
		return decisionKey;
		
	}
	
	int findDecisionKey(Node currentNode,HashMap<Integer,Double> calculateChildInformationGain){
		int decisionKey=-1; double max=0;
		Iterator it = calculateChildInformationGain.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			if((double)pair.getValue()>max){
				max=(double)pair.getValue();
				decisionKey=(int)pair.getKey();
			}
		}
		return decisionKey;
		
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
	
	boolean getNodeLabel(Node currentNode){
		if(currentNode.totalZeroes==0||currentNode.totalOnes==0)
		{
			System.out.print("true vali line");
			if(currentNode.totalOnes==0)
				currentNode.setLabel("0");
			if(currentNode.totalZeroes==0)
				currentNode.setLabel("1");
			currentNode.setPurity(true);
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


