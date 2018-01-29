import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Node{
	int index;
	ArrayList<String> data=null;
	HashMap<Integer,String> features=null;
	Node parent;
	Node left=null;
	Node right=null;
	String splitKey=null;
	HashMap<Integer,Double> entropy;
	int decisionKey;
	double totalZeroes;
	double totalOnes;
	boolean purity=false;
	String label;
	
	
	void setPurity(boolean purity){
	this.purity=purity;
	}
	boolean getPurity(){
		return this.purity;
	}
	////////////////////////////////////////
	void setLabel(String label){
		this.label=label;
	}
	String getLabel(){
		return this.label;
	}
	///////////////////////////////
	void setIndex(int index){
		this.index=index;
	}
	int getIndex(){
		return this.index;
	}
	///////////////////////////////////
	void setParent(Node parent){
		this.parent=parent;
	}
	Node getParent(){
		return this.parent;
	}
	////////////////////////////////
	void setLeft(Node left){
		this.left=left;
	}
	Node getLeft(){
		return this.left;
	}
	/////////////////////////////////////
	void setRight(Node right){
		this.right=right;
	}
	Node getRight(){
		return this.right;
	}
	//////////////////////////////////////
	void setData(ArrayList<String> data){
		this.data=data;
	}
	ArrayList<String> getData(){
		return this.data;
	}
	////////////////////////////////////
	void setDecisionKey(int decisionKey){
		this.decisionKey=decisionKey;
	}
	int getDecisionKey(){
		return this.decisionKey;
	}
	//////////////////////////////////
	void setTotalZeroes(double totalZeroes){
		this.totalZeroes=totalZeroes;
	}
	double getTotalZeroes(){
		return this.totalZeroes;
	}
	/////////////////////////////////
	void setTotalOnes(double totalOnes){
		this.totalOnes=totalOnes;
	}
	double getTotalOnes(){
		return this.totalOnes;
	}
	////////////////////////////////////
	HashMap<Integer,String> getFeatures(){
		return this.features;
	}
	void setFeatures(HashMap<Integer,String> features){
		this.features=features;
	}
}