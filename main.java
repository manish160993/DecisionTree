import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class main{
	
	
	public static void main(String[] arg) {


	
	if (arg.length!=6 )  
	{ 
		System.out.println(" Please provide arguments in following order: 'Training_set' 'Validation_set' 'Test_set' 'Y/N  >> for print' 'L' 'K' !!!");
		System.exit(1);
	}
	else{
			//////////////////////// Various inputs//////////////////////////
			Constant.prune_L= Integer.parseInt((arg[0]).trim());
			Constant.prune_K= Integer.parseInt((arg[1]).trim());
			Constant.training=(arg[2]).trim(); 
			Constant.validation=(arg[3]).trim();
			Constant.test=(arg[4]).trim();
			Constant.print=(arg[5]).trim();
			
			///////////////////// Connections with other class/////////////////
			ArrayList<String> dataList=new ArrayList<String>();
			Import im=new Import();
			DecisionTree iD3= new DecisionTree();
			DecisionTree iD3_I= new DecisionTree();
			dataList=im.import1(Constant.training);
			Test testTrainingData= new Test();
			Test testValidationData= new Test();
			Test testTestData= new Test();
			Prune1 prune=new Prune1();
			DecisionTree iD4= new DecisionTree();
			DecisionTree iD4_I= new DecisionTree();
			double d;
		
		
	//---------------Training the tree for Information Gain Heuristic--------------------------------------------
			HashMap<Integer,Node> nodeMap=iD3.dataToRoot(dataList);
			iD3.splitRoot(nodeMap,1);
			//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Impu>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
	//---------------Training the tree for Impurity Gain Heuristic--------------------------------------------	
		
			HashMap<Integer,Node> nodeMap_I=iD3_I.dataToRoot(dataList);
			iD3_I.splitRoot(nodeMap_I,0);
			//System.out.println(nodeMap_I.get(1));
			
			
	// Decision Tree Printing and calculations

			
			System.out.println("Decision tree");
			System.out.println();
			System.out.println(" ------PRE-PRUNING ----");
			System.out.println("--------------------------------------Information Gain Heuristic----------------------------------------");
			if(Constant.print.equals("Y")){
				iD3.printNode(nodeMap.get(1));
				System.out.println();
				System.out.println("--------------------------------------Impurity Heuristic----------------------------------------");
				System.out.println();
				iD3_I.printNode(nodeMap_I.get(1));
			}
			System.out.println();
			System.out.println();
			System.out.println("<<<<<<<<<<Information Gain Details>>>>>>>>>>>");
			System.out.println("Total Instances on training data  : "+nodeMap.get(1).getData().size());
			System.out.println(" Total attributes on training data : "+(nodeMap.get(1).getFeatures().size()-1));
			//System.out.println(" Total number of nodes: "+(nodeMap.size()));
			//System.out.println("Total leafNodes in training data :"+calLeafNodes(nodeMap));
		
		
			//DecisionTree iD0= new DecisionTree();
		
			d=testTrainingData.testAccuracy(Constant.training,nodeMap.get(1));    
			System.out.println("Accuracy on training data :"+d+" %");
			//System.out.println();
		
		
			d=testValidationData.testAccuracy(Constant.validation,nodeMap.get(1));
			System.out.println("Accuracy on validation data :"+d+" %");
			double d_imp=d;
			
			
			
			/* 
			"To increase the accuracy this method can be used....."
			nodeMap=iD3.dataToRoot1(dataList);
			iD3.splitRoot(nodeMap,1);
			System.out.println("Total Instances on training data  : "+nodeMap.get(1).getData().size());
			System.out.println(" Total attributes on training data : "+(nodeMap.get(1).getFeatures().size()-1));
			System.out.println(" Total number of nodes: "+(nodeMap.size()));
			System.out.println("Total leafNodes in training data :"+calLeafNodes(nodeMap));

			*/
		
		
			//System.out.println();
		
			d=testTestData.testAccuracy(Constant.test,nodeMap.get(1));
			System.out.println("Accuracy on Test data :"+d+" %");
			System.out.println();
			System.out.println();
			System.out.println();
		

		
			
		
		
			System.out.println("<<<<<<<<<<Variance Impurity>>>>>>>>>>>");
			System.out.println("Total Instances on training data : "+nodeMap_I.get(1).getData().size());
			System.out.println("Total attributes on training data : "+(nodeMap_I.get(1).getFeatures().size()-1));
			//System.out.println(" Total number of nodes: "+(nodeMap_I.size()));
			//System.out.println("Total leafNodes in training data :"+calLeafNodes(nodeMap_I));
			d=testTrainingData.testAccuracy(Constant.training,nodeMap_I.get(1));    
			System.out.println("Accuracy on training data :"+d+" %");
			//System.out.println();
			d=testValidationData.testAccuracy(Constant.validation,nodeMap_I.get(1));
			System.out.println("Accuracy on validation data :"+d+" %");
			double d_imp_I=d;
		
		
			/* To improve the accuracy, this can be used......
			nodeMap_I=iD31.dataToRoot1(dataList);
			iD31.splitRoot(nodeMap_I,0);
			
			System.out.println("Total Instances on training data  : "+nodeMap_I.get(1).getData().size());
			System.out.println(" Total attributes on training data : "+(nodeMap_I.get(1).getFeatures().size()-1));
			System.out.println(" Total number of nodes: "+(nodeMap_I.size()));
			System.out.println("Total leafNodes in training data :"+calLeafNodes(nodeMap_I));
			
			*/
			
			
			d=testTestData.testAccuracy(Constant.test,nodeMap_I.get(1));
			System.out.println("Accuracy on Test data :"+d+" %");
			
			
			
			dataList=im.import1(Constant.training);
			HashMap<Integer,Node> nodeMap1=iD4.dataToRoot(dataList);
			iD4.splitRoot(nodeMap1,1);
			
			
			//dataList=im.import1(Constant.training);
			HashMap<Integer,Node> nodeMap_I1=iD4_I.dataToRoot(dataList);
			iD4_I.splitRoot(nodeMap_I1,0);
			
			System.out.println("");
			System.out.println("");
			System.out.println("Calculation of various "+ Constant.prune_L +" decision trees");
		
		
		//////////////Pruning of trees and selecting the best tree/////////////////
			for(int i=0;i<Constant.prune_L;i++){
				System.out.println("");
				DecisionTree iD5= new DecisionTree();
				DecisionTree iD5_I= new DecisionTree();
				//dataList=im.import1(Constant.training);
				HashMap<Integer,Node> nodeMap2=iD5.dataToRoot(dataList);
				HashMap<Integer,Node> nodeMap_I2=iD5_I.dataToRoot(dataList);
				iD5.splitRoot(nodeMap2,1);
				iD5_I.splitRoot(nodeMap_I2,0);
				nodeMap2=prune.pruning(nodeMap2);
				nodeMap_I2=prune.pruning(nodeMap_I2);
				d=testValidationData.testAccuracy(Constant.validation,nodeMap2.get(1));
				if(d>d_imp){
					nodeMap1.clear();
					nodeMap1.putAll(nodeMap2);
					d_imp=d;
				}
				System.out.println(d+" Accuracy Info for IG Gain");
				d=testValidationData.testAccuracy(Constant.validation,nodeMap_I2.get(1));
				if(d>d_imp_I){
					nodeMap_I1.clear();
					nodeMap_I1.putAll(nodeMap_I2);
					d_imp_I=d;
				}
				System.out.println(d+" Accuracy Info for Variance ImPu");
				System.out.println("");
			}
		
			System.out.println("");
			System.out.println(" ------POST-PRUNING ----");
			System.out.println("--------------------------------------Information Gain Heuristic----------------------------------------");
			if(Constant.print.equals("Y")){
				iD4.printNode(nodeMap.get(1));
				System.out.println();
				System.out.println("--------------------------------------Impurity Heuristic----------------------------------------");
				System.out.println();
				iD4_I.printNode(nodeMap_I.get(1));
			}
			System.out.println();
			System.out.println();
			System.out.println("<<<<<<<<<<Information Gain Details>>>>>>>>>>>");
			//System.out.println("Total Instances on training data  : "+nodeMap1.get(1).getData().size());
			//System.out.println(" Total attributes on training data : "+(nodeMap1.get(1).getFeatures().size()-1));
			//System.out.println(" Total number of nodes: "+(nodeMap1.size()));
			//System.out.println("Total leafNodes in training data :"+calLeafNodes(nodeMap1));
		
		
			//DecisionTree iD0= new DecisionTree();
		
			d=testTrainingData.testAccuracy(Constant.training,nodeMap1.get(1));    
			System.out.println("Accuracy on training data :"+d+" %");
			//System.out.println();
		
		
			d=testValidationData.testAccuracy(Constant.validation,nodeMap1.get(1));
			System.out.println("Accuracy on validation data :"+d+" %");
			//double d_imp=d;
			
			
			
			/* 
			"To increase the accuracy this method can be used....."
			nodeMap=iD3.dataToRoot1(dataList);
			iD3.splitRoot(nodeMap,1);
			System.out.println("Total Instances on training data  : "+nodeMap.get(1).getData().size());
			System.out.println(" Total attributes on training data : "+(nodeMap.get(1).getFeatures().size()-1));
			System.out.println(" Total number of nodes: "+(nodeMap.size()));
			System.out.println("Total leafNodes in training data :"+calLeafNodes(nodeMap));

			*/
		
		
			//System.out.println();
		
			d=testTestData.testAccuracy(Constant.test,nodeMap1.get(1));
			System.out.println("Accuracy on Test data :"+d+" %");
			System.out.println();
			System.out.println();
			System.out.println();
		
	//---------------Training the tree for Impurity Gain Heuristic--------------------------------------------	
		
		
		
			System.out.println("<<<<<<<<<<Variance Impurity>>>>>>>>>>>");
			
			d=testTrainingData.testAccuracy(Constant.training,nodeMap_I1.get(1));    
			System.out.println("Accuracy on training data :"+d+" %");
			
			
			d=testValidationData.testAccuracy(Constant.validation,nodeMap_I1.get(1));
			System.out.println("Accuracy on validation data :"+d+" %");
			
		
			d=testTestData.testAccuracy(Constant.test,nodeMap_I1.get(1));
			System.out.println("Accuracy on Test data :"+d+" %");
			
			/* To improve the accuracy, this can be used......
			nodeMap_I=iD31.dataToRoot1(dataList);
			iD31.splitRoot(nodeMap_I,0);
			
			System.out.println("Total Instances on training data  : "+nodeMap_I.get(1).getData().size());
			System.out.println(" Total attributes on training data : "+(nodeMap_I.get(1).getFeatures().size()-1));
			System.out.println(" Total number of nodes: "+(nodeMap_I.size()));
			System.out.println("Total leafNodes in training data :"+calLeafNodes(nodeMap_I));
			
		
		
			*/
		}
	}

	static int calLeafNodes(HashMap<Integer, Node> NodeMap){
		int leafNodes=0;
		for(int i : NodeMap.keySet()){
		//if
		//if((NodeMap.get(i).getLeft()==null)&&(NodeMap.get(i).getRight()==null))
		if((NodeMap.get(i).getPurity()))
			leafNodes++;
		}
		return leafNodes;
	}
	

}


