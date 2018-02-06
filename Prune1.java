import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.*;

//import javax.swing.text.html.HTMLDocument.Iterator;

public class Prune1 {
	
	
	
	HashMap<Integer, Node> pruning(HashMap<Integer, Node> mapNodesCopy1)
	{
		//System.out.println(mapNodesCopy.keySet());
		HashMap<Integer, Node> afterPruningMapNodes=new HashMap<Integer,Node>();
		ArrayList<Integer> leafNodesIndex=new ArrayList<Integer>();
		ArrayList<Integer> eligibleNodestobePruned=new ArrayList<Integer>();
		ArrayList<Integer> NodestobePruned=new ArrayList<Integer>();
	
	
		HashMap<Integer, Node> mapNodesCopy2 =new HashMap<Integer,Node>();
		mapNodesCopy2.putAll(mapNodesCopy1);
		Random ran= new Random();
		int M=ran.nextInt(Constant.prune_K);
		//System.out.println("M: "+M);
		//System.out.println(mapNodesCopy1.size()+" SIZE>>>>>");
		//System.out.println("Total nodes to be pruned "+totalNodesTobPruned);
		for(int i: mapNodesCopy2.keySet())
		{
			if((mapNodesCopy2.get(i).getLeft()==null)&&(mapNodesCopy2.get(i).getRight()==null))
				leafNodesIndex.add(i);
		}//System.out.println("Pure nodes are: "+leafNodesIndex);
		eligibleNodestobePruned.addAll(mapNodesCopy2.keySet());//add all node indixes
		//System.out.println("1 "+eligibleNodestobePruned);
		eligibleNodestobePruned.remove(0);// arraylists stars from 0 so here 0 refers too root node
		//eligibleNodestobePruned.removeAll(leafNodesIndex);
		for(Integer i:leafNodesIndex)
			eligibleNodestobePruned.remove(new Integer(i));
		
		//System.out.println(eligibleNodestobePruned.size()+" "+leafNodesIndex.size());
		//System.out.println("2 "+eligibleNodestobePruned);
		//eligibleNodestobePruned.removeAll(leafNodesIndex);
		//System.out.println("3 "+eligibleNodestobePruned);
		//System.out.println("Elegile nodes to be pruned");
		//for(Integer i:eligibleNodestobePruned)
			//	System.out.print(i+" ");
			//System.out.println();
		int count=M;
		int removeIndex;
		if(count>eligibleNodestobePruned.size())
			count=eligibleNodestobePruned.size();
		//Random ran= new Random();
		while(count>0)
		{
			removeIndex=eligibleNodestobePruned.get(ran.nextInt(eligibleNodestobePruned.size()));
			//System.out.print(removeIndex+" ");
			if(eligibleNodestobePruned.contains(removeIndex)){
			if(NodestobePruned.contains(removeIndex))
				continue;
			else
				NodestobePruned.add(removeIndex);
			count--;
			}
			//if(count==totalNodesTobPruned)
				//break;
		}
		//System.out.println("Remove index");
		
		//System.out.println("5 "+NodestobePruned);
		//System.out.println("6 "+NodestobePruned.size());
		//System.out.println("Before pruning :"+mapNodesCopy.size());
		removeNodefromList(NodestobePruned,mapNodesCopy2);
		
		///System.out.println("After pruning :"+mapNodesCopy.size());
		//System.out.println(mapNodesCopy2.size()+" SIZE>>>>>");
		
		return mapNodesCopy1;
	}
	private HashMap<Integer, Node> removeOrphaneNodes(HashMap<Integer, Node> mapNodesCopy) {
		//System.out.println(" After list pruning map is :"+mapNodesCopy.keySet());
		//java.util.Iterator<Integer> itr= mapNodesCopy.keySet().iterator();
		int maxKey= Collections.max(mapNodesCopy.keySet());
		for(int index=2;index<=maxKey;index++)// TODO Auto-generated method stub
		{
			
			if(mapNodesCopy.get(index)!=null)
			{
			if(index!=1)
			{
				//System.out.println("Index in orphan :"+index);
				
				if (mapNodesCopy.get(index).parent==null)
				{
					if(mapNodesCopy.get(index).getLeft()!=null) {
						mapNodesCopy.get(index).getLeft().setParent(null);
						mapNodesCopy.get(index).setLeft(null);
						
					}
					if(mapNodesCopy.get(index).getRight()!=null) {
						mapNodesCopy.get(index).getRight().setParent(null);
						mapNodesCopy.get(index).setRight(null);
						
					}
					mapNodesCopy.remove(index);
					//System.out.println(" map :"+mapNodesCopy.keySet());
				}
			}
		}
			maxKey= Collections.max(mapNodesCopy.keySet());
			}
		
		return mapNodesCopy;
	}
	private HashMap<Integer, Node> removeNodefromList(ArrayList<Integer> nodestobePruned2, HashMap<Integer, Node> mapNodesCopy2) {
		Node n=null;
		
		// TODO Auto-generated method stub
		for (int i:nodestobePruned2)
		{    
     //n=mapNodesCopy.get(i);
     //System.out.println("For index"+i);
    // System.out.println(mapNodesCopy.keySet());
     //System.out.println("Parent is "+n.getParentNode().getNodeIndex());
     //System.out.println("Left child : "+n.getLeftChild().getNodeIndex());
     //System.out.println(" Right Child :"+ n.getRightChild().getNodeIndex());
			//System.out.println(i+" "+mapNodesCopy.get(i));
			if ((i%2==0)&&(mapNodesCopy2.get(i).getParent()!=null))
			    mapNodesCopy2.get(i).getParent().setLeft(null);
				
			if((i%2!=0)&&(mapNodesCopy2.get(i).getParent()!=null))
	             mapNodesCopy2.get(i).getParent().setRight(null);
			
			if((mapNodesCopy2.get(i).getLeft()!=null)) {
				mapNodesCopy2.get(i).getLeft().setParent(null);
				mapNodesCopy2.get(i).setLeft(null);
			}
			if(mapNodesCopy2.get(i).getRight()!=null)
			{
			mapNodesCopy2.get(i).getRight().setParent(null);
			mapNodesCopy2.get(i).setRight(null);
			}
			mapNodesCopy2.get(i).setPurity(false);
			mapNodesCopy2.remove(i);
		}
		
		return mapNodesCopy2;
	}
}