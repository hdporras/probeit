package probeIt.graphics;

import java.awt.Point;
import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import pml.PMLNode;
import pml.util.pml.PMLStatistics;
import probeIt.graphics.buttons.ViewerButton;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.graphics.interactor.NodeSelectionInteractor;
import probeIt.ui.model.ViewsModel;
import diva.canvas.JCanvas;
import diva.graph.GraphModel;


/* --Changes To Be Made--
 * 
 * !problem with check neighbor!!!!
 * 
 * 6. Eliminate repeats using fillLevels() and the beginning of the first part of drawGraph().
 * 		try using the node.URI, that the part in drawGraph() uses.
 * 
 * 7. (?)findChilrenNSG() probably has problems getting the right nodes.
 * 
 * ! when non-leaf children are repeats, it may not be able to center it around its parent... 
 * 
 *  8. Nodes are being moved sideways only according to their position in the Levels,
 *  	and not according to their drawn position and so lines are still crossing.
 *  	Need to take drawn position into account.
 *  
 *  ! Drawing two nodes twice? (in GEO)
 *  ! There's a node in the GEO example that looks like it isn't averaging the children, is there a bug?
 *  
 *  9. move Nodes in first Level (0, where all leafs are in) to be as close to other nodes 
 *  	with same parents as possible. Nodes with more parents in common should be closer.
 *  
 *  !DRAWING OUT OF BOUNDS, because assuming that a node (probably first leftmost/rightmost) is already at it's idealX.
 *  	*Different from non-leaf drawing.
 */
public class CanvasDrawer_DAGJustification
{
	private PMLNode _rootNode;
	private int maxWidth;
	private int maxHeight;
	private JustificationCanvas _canvasManager;
	//private int[] _levels;
	private NodeSetGraphic _rootGraphic;
	private int xInc = NodeSetGraphic.WIDTH + 30;
	private int yInc = NodeSetGraphic.HEIGHT + ViewerButton.HEIGHT + 50;
	
	
	/** Field for debug output
	*/
	protected final boolean verbose = false;

	/** Const to add Attributes at the Nodes
	*
	*/
	public static final String SUGIYAMA_VISITED = "SugiyamaVisited"/*#Frozen*/;

	/** Const to add the Cell Wrapper to the Nodes
	*/
	public static final String SUGIYAMA_CELL_WRAPPER = "SugiyamaCellWrapper"/*#Frozen*/;

	/** represents the size of the grid in horizontal grid elements
	*
	*/
	protected int gridAreaSize = Integer.MIN_VALUE;

	/** A List with Integer Objects. The List contains the
	*  history of movements per loop
	*  It was needed for the progress dialog
	*/
	List movements = null;
	/** Represents the movements in the current loop.
	*  It was needed for the progress dialog
	*/
	int movementsCurrentLoop = -1;
	/** Represents the maximum of movements in the current loop.
	*  It was needed for the progress dialog
	*/
	int movementsMax = Integer.MIN_VALUE;
	/** Represents the current loop number
	*  It was needed for the progress dialog
	*/
	int iteration = 0;
	public static final String KEY_HORIZONTAL_SPACING = "HorizontalSpacing";
	public static final String KEY_VERTICAL_SPACING = "VerticalSpacing";

	List graphList;
	List uriList;
	boolean[][] nodesDrawn;
	NodeSelectionInteractor _interactor;

	public CanvasDrawer_DAGJustification(JustificationCanvas canvas)
	{
		_rootNode = ViewsModel.getInstance().getJustification();
		PMLStatistics stats = new PMLStatistics(_rootNode);
		
		maxWidth = stats.getProofWidth();
		maxHeight = stats.getProofHeight();
		_canvasManager = canvas;
		_interactor = new NodeSelectionInteractor();
		uriList = new ArrayList();
	}
	
	public NodeSetGraphic getRootGraphic()
	{
		return _rootGraphic;
	}

	public JCanvas draw()
	{
		//setUpLevels();
		_rootGraphic = perform(_rootNode);//drawDAG(_rootNode, 1000, maxHeight-1);
		return _canvasManager.getCurrentDAG();
	}
	
	/**
	* Implementation.
	*
	* First of all the Algorithm searches the roots from the
	* Graph. Starting from this roots the Algorithm creates
	* levels and stores them in the member <code>levels</code>.
	* The Member levels contains List Objects and the List per level
	* contains Cell Wrapper Objects. After that the Algorithm
	* tries to solve the edge crosses from level to level and
	* goes top down and bottom up. After minimization of the
	* edge crosses the algorithm moves each node to its
	* bary center. Last but not Least the method draws the Graph.
	*
	* @see LayoutAlgorithm
	*
	*/
	public NodeSetGraphic perform(PMLNode root)
	{
		graphList = new ArrayList();
		
		System.out.println("Suriyama man...");
		
		if(_canvasManager.getDrawnNode(root) != null)
		{
			_canvasManager.getDrawnNode(root).setReferenceManyTimes();
			return _canvasManager.getDrawnNode(root);
		}
		
		if (root.getCurrentlySelectedInferenceStep().isLeaf())
		{
			System.out.println("only one node and is a parent");
			return drawOnlyChild(root);
		}

		//Object[] selectedCells = (applyToAll ? jCanvas.getRoots() : jgraph.getSelectionCells());
		//CellView[] selectedCellViews = jgraph.getGraphLayoutCache().getMapping(selectedCells);

		getGraphList(root);
		//System.out.println("OUT!!!");
		
		Point spacing = new Point();
		//Properties configuration = new Properties();
		/*  The Algorithm distributes the nodes on a grid.
		 *  For this grid you can configure the horizontal spacing.
		 *  This field specifies the configured value
		 *
		 */
		//spacing.x = Integer.parseInt(configuration.getProperty(KEY_HORIZONTAL_SPACING));

		/*  The Algorithm distributes the nodes on a grid.
		 *  For this grid you can configure the vertical spacing.
		 *  This field specifies the configured value
		 *
		 */

		//spacing.y = Integer.parseInt(configuration.getProperty(KEY_VERTICAL_SPACING));

		// search all roots
		//List roots = searchRoots(jCanvas, root); //Assume one root?

		// return if no root found
		/*if(roots.size() == 0)
			return;*/

		NodeSetGraphic nodeSetRoot = getNodeCellWrapper(root).getNodeSetGraphic();
		
		// create levels
		List levels = fillLevels(nodeSetRoot);

		// solves the edge crosses
		solveEdgeCrosses(levels);

		// move all nodes into the barycenter
		moveToBarycenter(nodeSetRoot, levels);

		Point min = findMinimumAndSpacing(root, spacing);

		//NodeSetGraphic nodeset = getNodeCellWrapper(root).nodeG;
		
		// draw the graph in the window
		return drawGraph(levels, min, spacing);

		// clean temp values from the nodes / cells
		// the clean up was made in drawGraph
		//cleanUp(selectedCellViews);
		
		//return nodeset;

	}
	
	public void getGraphList(PMLNode node)
	{
		//System.out.println("Starting...");
		NodeSetGraphic nodeG = new NodeSetGraphic(node);
		CellWrapper tempCell = new CellWrapper(nodeG);
		nodeG.cellwrapper = tempCell;
		
		if(uriList.contains(node.getURI()))	
			return;
		
		graphList.add(tempCell);
		uriList.add(node.getURI());

		//System.out.println("Adding tempCell");
		
		if(!node.getCurrentlySelectedInferenceStep().isLeaf())
		{
			for(int i=0; i<node.getCurrentlySelectedInferenceStep().getAntecedents().length; i++)
			{
				//System.out.println("In loop. i ="+ i);
				getGraphList(node.getCurrentlySelectedInferenceStep().getAntecedents()[i]);//need to make method
			}
		}
	}
	
	public CellWrapper getNodeCellWrapper(PMLNode node)
	{
		for(int i=0; i < graphList.size(); i++)
		{
			if(node.getURI().equals(((CellWrapper)graphList.get(i)).getNode().getURI()))
				return (CellWrapper)graphList.get(i);
		}
		
		return new CellWrapper(new NodeSetGraphic(node));
	}
	
	
	public CellWrapper getSugiyamaNodeCellWrapper(PMLNode node)
	{
		for(int i=0; i < graphList.size(); i++)
		{
			if(node.equals(((CellWrapper)graphList.get(i)).getNode()) && ((CellWrapper)graphList.get(i)).nodeG.sugiyamaVisited)
				return (CellWrapper)graphList.get(i);
		}
		
		return new CellWrapper(new NodeSetGraphic(node));
	}

	protected boolean nodeHasBeenVisited(PMLNode node)
	{
		for(int i=0; i < graphList.size(); i++)
		{
			PMLNode tempNode = ((CellWrapper)graphList.get(i)).getNode();
			NodeSetGraphic tempNodeSetGraphic = ((CellWrapper)graphList.get(i)).nodeG;
			
			if(node.equals(tempNode) && tempNodeSetGraphic.sugiyamaVisited)
				return true;
				
		}
		
		return false;
	}
	
	protected boolean nodeHasOtherNodesThatHaveNotBeenVisited(PMLNode node)
	{
		for(int i=0; i < graphList.size(); i++)
		{
			PMLNode tempNode = ((CellWrapper)graphList.get(i)).getNode();
			NodeSetGraphic tempNodeSetGraphic = ((CellWrapper)graphList.get(i)).nodeG;
			
			if(node.equals(tempNode) && !tempNodeSetGraphic.sugiyamaVisited)
				return true;
				
		}
		
		return false;
	}	

	/** Method fills the levels and stores them in the member levels.

	*  Each level was represended by a List with Cell Wrapper objects.
	*  These Lists are the elements in the <code>levels</code> List.
	*
	*/
	protected List fillLevels(NodeSetGraphic nodeG)
	{
		List levels = new ArrayList();
		
	 	//assuming there's only one root.
	 	fillLevels(levels, nodeG);//down-up
	 	
	 	return levels;
	}	

	
//!@#!@# Going to have to change draw to start at 0!! because leaves will now be 0 instead of length
	/** Fills the List for the specified level with a wrapper
	*  for the MyGraphCell. After that the method called for
	*  each neighbor graph cell.
	*
	*  @param level        The level for the graphCell
	*/
	protected int fillLevels(List levels, NodeSetGraphic nodeG)
	{
//		 \/ where should this go and what should it return? Why is GEO 53 painting it wrong!!!
		if(nodeG.cellwrapper == null)
		{//should have repeat if not then problem when setting cellwrappers in graphList..
			System.out.println("NodeG = "+nodeG);
			System.out.println("Cellwrapper = "+nodeG.cellwrapper);
			System.out.println("Cellwrapper is Null. returning -1...");
			return -1;
		}
		else if(nodeG.sugiyamaVisited == true)
		{
			System.out.println("Already SugiyamaVisited. level="+nodeG.cellwrapper.getLevel());
			return nodeG.cellwrapper.getLevel();
		}
		else if(nodeHasBeenVisited(nodeG.getNode()))
		{
			System.out.println("nodeHasBeenVisited() is true");
			return nodeG.cellwrapper.getLevel();
		}
		else
		{
			System.out.println("inside fillLevels()");
			int level = 0;
			
			if(!nodeG.getNode().getCurrentlySelectedInferenceStep().isLeaf())
			{
				for(int i = 0; i < nodeG.getNode().getCurrentlySelectedInferenceStep().getAntecedents().length; i++)
				{	
					
					CellWrapper childCell = getNodeCellWrapper(nodeG.getNode().getCurrentlySelectedInferenceStep().getAntecedents()[i]);//getSugiyamaNodeCellWrapper(nodeG.getNode().getCurrentlySelectedInferenceStep().getAntecedents()[i]);
						
					NodeSetGraphic nextNodeG = childCell.nodeG;
				
					//System.out.println("levels = "+levels);
					//System.out.println("NodeG = "+nextNodeG);
					
					int tempLevel = fillLevels(levels, nextNodeG) + 1;
					//System.out.println("tempLevel = "+tempLevel);
					if(tempLevel > level)
						level = tempLevel;
				}
			}
			
			// be sure that a List container exists for the current level
			if(levels.size() == level)
				levels.add(level, new ArrayList());
			

//			 mark as visited for cycle tests
			nodeG.sugiyamaVisited = true;

			// put the current node into the current level
			// get the Level List
			List currentLevel = (List)levels.get(level);

			// Create a wrapper for the node
			int numberForTheEntry = currentLevel.size();

			//System.out.println("Times it runs!!");
				
			CellWrapper cellWrapper = nodeG.cellwrapper;
			cellWrapper.setBasic(level, numberForTheEntry);

			// concat the cellWrapper to the cell for an easy access
			nodeG.cellwrapper = cellWrapper;
			
			System.out.println("URI: "+cellWrapper.getNode().getURI()+". Level: "+level);
			// put the Wrapper in the LevelList
			currentLevel.add(cellWrapper);		


			if(currentLevel.size() > gridAreaSize)
			{
				gridAreaSize = currentLevel.size();
			}
			
			return level;
		}
		// if the cell already visited return
		/*if(nodeG.sugiyamaVisited == true)
		{
			return;
		}
		
		// this is to make the highest node in the table be the one that's drawn; 
		// for the nodes that have repeats.

		if(nodeHasOtherNodesThatHaveNotBeenVisited(nodeG.getNode())) 
		{
			nodeG.sugiyamaVisited = true;
			return;
		}

		// if node has been visited through other connection return;
		if(nodeHasBeenVisited(nodeG.getNode()))
		{
			
			nodeG.sugiyamaVisited = true;
			return;
		}*/
	}
	
	
	
	//probeit already doing this?
	/** calculates the minimum for the paint area.
	*
	*/
	protected Point findMinimumAndSpacing(PMLNode node, Point spacing)
	{
		try
		{

			// variables
			/* represents the minimum x value for the paint area
			 */
			int min_x = 1000000;

			/* represents the minimum y value for the paint area
			 */
			int min_y = 1000000;

			// find the maximum & minimum coordinates

			for(int i = 0; i < graphList.size(); i++)
			{

				// the cellView and their bounds
				//CellWrapper wrapper = (CellWrapper)graphList.get(i);//Node -> NodeSetGraphic?

				//NodeSetGraphic nodeset = wrapper.getNodeSetGraphic();
	     

				// checking min area
				try
				{
					min_x = xInc;
					
					min_y = yInc;
					/*
	       			if (cellViewBounds.width > spacing.x)
	         		spacing.x = cellViewBounds.width;
	       			if (cellViewBounds.height > spacing.y)
	         		spacing.y = cellViewBounds.height;
					 */

				}
				catch(Exception e)
				{
					System.err.println("---------> ERROR in calculateValues."/*#Frozen*/);
					e.printStackTrace();
				}
			}
			// if the cell sice is bigger than the userspacing
			// dublicate the spacingfactor
			return new Point(min_x, min_y);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/** Updates the progress based on the movements count
	*
	*/
	protected void updateProgress4Movements()
	{
	 // adds the current loop count
	 movements.add(new Integer(movementsCurrentLoop));
	 iteration++;

	 // if the current loop count is higher than the max movements count
	 // memorize the new max
	 if(movementsCurrentLoop > movementsMax)
	 {
	   movementsMax = movementsCurrentLoop;
	 }

	}

	protected void solveEdgeCrosses(List levels)
	{
	 movements = new ArrayList(100);
	 movementsCurrentLoop = -1;
	 movementsMax = Integer.MIN_VALUE;
	 iteration = 0;

	 while(movementsCurrentLoop != 0)
	 {

	   // reset the movements per loop count
	   movementsCurrentLoop = 0;

	   if(verbose)
	   {
	     System.out.println("---------------------------- vor Sort"/*#Frozen*/);
	     displayEdgeCrossesValues(levels);
	   }

	   // top down
	   for(int i = 0; i < levels.size() - 1; i++)
	   {
	     movementsCurrentLoop += solveEdgeCrosses(true, levels, i);
	   }

	   // bottom up
	   for(int i = levels.size() - 1; i >= 1; i--)
	   {
	     movementsCurrentLoop += solveEdgeCrosses(false, levels, i);
	   }

	   if(verbose)
	   {
	     System.out.println("---------------------------- nach Sort"/*#Frozen*/);
	     displayEdgeCrossesValues(levels);
	   }

	   updateProgress4Movements();
	 }
	}

	/**
	*  @return movements
	*/
	protected int solveEdgeCrosses(boolean down, List levels, int levelIndex)
	{
		// Get the current level
		List currentLevel = (List)levels.get(levelIndex);
		int movements = 0;

		// restore the old sort
		Object[] levelSortBefore = currentLevel.toArray();

		// new sort
		Collections.sort(currentLevel);

		// test for movements
		for(int j = 0; j < levelSortBefore.length; j++)
		{
			if(((CellWrapper)levelSortBefore[j]).getEdgeCrossesIndicator() != ((CellWrapper)currentLevel.get(j)).getEdgeCrossesIndicator())
			{
				movements++;
	     
			}
		}

		//GraphModel model = jgraph.getModel();

		// Collecations Sort sorts the highest value to the first value
		for(int j = currentLevel.size() - 1; j >= 0; j--)
		{
			CellWrapper sourceWrapper = (CellWrapper)currentLevel.get(j);

			PMLNode sourceNode = sourceWrapper.getNode();

			int sourcePortCount;
			if(!sourceNode.getCurrentlySelectedInferenceStep().isLeaf())
				sourcePortCount = sourceNode.getCurrentlySelectedInferenceStep().getAntecedents().length;//model.getChildCount(sourceVertex);
			else
				sourcePortCount = 0;

			for(int k = 0; k < sourcePortCount; k++)
			{
				PMLNode targetNode = sourceNode.getCurrentlySelectedInferenceStep().getAntecedents()[k];
				
				/*Object sourcePort = model.getChild(sourceVertex, k);

				Iterator sourceEdges = model.edges(sourcePort);
				while(sourceEdges.hasNext())
				{
					Object edge = sourceEdges.next();*/
//* pg. 41 in manual
					// if it is a forward edge follow it
					//NodeSetGraphic targetNode = null;
					
					//Is the edge the one between sourceNode and its children? or between sourceNode and its parent?
					/*if(down && sourceNode == model.getSource(edge))
					{
						targetNode = targetNode;
					}*/
					if(!down && sourceNode == targetNode)//? would "sourceNode == targetNode" ever be true? maybe this should be excluded for probeit 
					{
						targetNode = sourceNode;//if sourceNode == targetNode then you're just setting it to itself... maybe this works differently for jgraph.
					}
					if(targetNode == null)
						continue;

					CellWrapper targetWrapper = (CellWrapper)getNodeCellWrapper(targetNode);//need Node to have a reference to NodeSetGraphic or CellWrapper. Or needs to have the attributes itself, or some other way.

					// do it only if the edge is a forward edge to a deeper level
					if(down && targetWrapper != null && targetWrapper.getLevel() > levelIndex)
					{
						targetWrapper.addToEdgeCrossesIndicator(sourceWrapper.getEdgeCrossesIndicator());
					}
					if(!down && targetWrapper != null && targetWrapper.getLevel() < levelIndex)
					{
						targetWrapper.addToEdgeCrossesIndicator(sourceWrapper.getEdgeCrossesIndicator());
					}
				//}
			}
		}

		return movements;
	}

	protected void moveToBarycenter(NodeSetGraphic nodeSG, List levels)
	{

		//================================================================
		// iterate any ReViewNodePort
	 
		for(int i = 0; i < graphList.size(); i++)
		{
			
			CellWrapper currentwrapper = (CellWrapper)graphList.get(i);

			PMLNode currentNode = currentwrapper.getNode();
			
			int childCount;
			if(!currentNode.getCurrentlySelectedInferenceStep().isLeaf())
				childCount = currentNode.getCurrentlySelectedInferenceStep().getAntecedents().length;
			else
				childCount = 0;

			for(int k = 0; k < childCount; k++)
			{
				PMLNode childNode = currentNode.getCurrentlySelectedInferenceStep().getAntecedents()[k];

	     // iterate any Edge in the port

	     /*Iterator edges = model.edges(port);
	     while(edges.hasNext())
	     {
	       Object edge = edges.next();

	       Object neighborPort = null;*/
	       // if the Edge is a forward edge we should follow this edge
//*			       // what constitutes a forward edge?
				PMLNode neighborNode;
				if(childNode == currentNode)//childNode? or currentNode?
				{
					neighborNode = childNode;
				}
				else
				{
					if(childNode == childNode)//
					{
						neighborNode = currentNode;
					}
					else//?
					{
						continue;
					}
				}


				//VertexView neighborVertexView = (VertexView)jgraph.getGraphLayoutCache().getMapping(neighborVertex, false);

				if(neighborNode == currentNode)
					continue;

				CellWrapper neighborWrapper = (CellWrapper)getNodeCellWrapper(neighborNode);
	      	
				if(currentwrapper == null || neighborWrapper == null || currentwrapper.level == neighborWrapper.level)
					continue;

				currentwrapper.priority++;

			
			}
		}

		//================================================================
		for(int j = 0; j < levels.size(); j++)
		{
			List level = (List)levels.get(j);
			for(int i = 0; i < level.size(); i++)
			{
				// calculate the initial Grid Positions 1, 2, 3, .... per Level
				CellWrapper wrapper = (CellWrapper)level.get(i);
				wrapper.setGridPosition(i);
			}
		}

		if(verbose)
		{
			System.out.println("----------------Grid Pos before top down"/*#Frozen*/);
			displayPriorities(levels);
			displayGridPositions(levels);
			System.out.println("======================================="/*#Frozen*/);
		}

		movements = new ArrayList(100);
		movementsCurrentLoop = -1;
		movementsMax = Integer.MIN_VALUE;
		iteration = 0;

		//int movements = 1;

		while(movementsCurrentLoop != 0)
		{

			// reset movements
			movementsCurrentLoop = 0;

			// top down
			for(int i = levels.size()-2; i >= 0; i--)
			{
				movementsCurrentLoop += moveToBarycenter(levels, i);
			}

			if(verbose)
			{
				System.out.println("----------------Grid Pos after top down"/*#Frozen*/);
				displayGridPositions(levels);
				System.out.println("======================================="/*#Frozen*/);
			}

			// bottom up
			for(int i = 0; i < levels.size(); i++)
			{
				movementsCurrentLoop += moveToBarycenter(levels, i);
			}

			if(verbose)
			{
				System.out.println("----------------Grid Pos after bottom up"/*#Frozen*/);
				displayGridPositions(levels);
				//displayDownPriorities();
				System.out.println("======================================="/*#Frozen*/);
			}

			this.updateProgress4Movements();
		}

	}

	protected int moveToBarycenter(List levels, int levelIndex)
	{

	 // Counter for the movements
	 int movements = 0;

	 // Get the current level
	 List currentLevel = (List)levels.get(levelIndex);
	 //GraphModel model = jgraph.getModel();

	 for(int currentIndexInTheLevel = 0; currentIndexInTheLevel < currentLevel.size(); currentIndexInTheLevel++)
	 {

	   CellWrapper sourceWrapper = (CellWrapper)currentLevel.get(currentIndexInTheLevel);

	   float gridPositionsSum = 0;
	   float countNodes = 0;

	   PMLNode currentNode = sourceWrapper.getNode();
	   
	   int childCount;
		if(!currentNode.getCurrentlySelectedInferenceStep().isLeaf())
			childCount = currentNode.getCurrentlySelectedInferenceStep().getAntecedents().length;
		else
			childCount = 0;
	   //int childCount = currentNode.getCurrentlySelectedInferenceStep().getAntecedents().length;

	   for(int i = 0; i < childCount; i++)
	   {
		   PMLNode childNode = currentNode.getCurrentlySelectedInferenceStep().getAntecedents()[i];

	     /*Iterator edges = model.edges(port);
	     while(edges.hasNext())
	     {
	       Object edge = edges.next();*/

	       // if it is a forward edge follow it
	       //Object neighborPort = null;
	       PMLNode neighborNode;
	       if(childNode == currentNode)//? childNode or currentNode?
	       {
	         neighborNode = childNode;
	       }
	       else
	       {
	         if(childNode == childNode)//? childNode or currentNode? (in place of port)
	         {
	           neighborNode = currentNode;
	         }
	         else
	         {
	           continue;
	         }
	       }

	       //Object neighborVertex = model.getParent(neighborPort);

	       //VertexView neighborVertexView = (VertexView)jgraph.getGraphLayoutCache().getMapping(neighborVertex, false);
	       CellWrapper targetWrapper = (CellWrapper)getNodeCellWrapper(neighborNode);

	       if(targetWrapper == sourceWrapper)
	         continue;
	       if(targetWrapper == null || targetWrapper.getLevel() == levelIndex)
	         continue;

	       gridPositionsSum += targetWrapper.getGridPosition();
	       countNodes++;
	     
	   	}

	   //----------------------------------------------------------
	   // move node to new x coord
	   //----------------------------------------------------------

	   if(countNodes > 0)
	   {
	     float tmp = (gridPositionsSum / countNodes);
	     int newGridPosition = Math.round(tmp);
	     boolean toRight = (newGridPosition > sourceWrapper.getGridPosition());

	     boolean moved = true;

	     while(newGridPosition != sourceWrapper.getGridPosition() && moved)
	     {
	       int tmpGridPos = sourceWrapper.getGridPosition();

	       moved = move(toRight, currentLevel, currentIndexInTheLevel, sourceWrapper.getPriority());

	       if(moved)
	         movements++;

	       if(verbose)
	       {

	         System.out.print("try move at Level " + levelIndex + " with index " + currentIndexInTheLevel + " to " + (toRight ? "Right" : "Left") + " CurrentGridPos: " + tmpGridPos + " NewGridPos: " + newGridPosition + " exact: " + NumberFormat.getInstance().format(tmp) + "..."/*#Frozen*/);
	         System.out.println(moved ? "success"/*#Frozen*/ : "can't move"/*#Frozen*/);

	       }
	     }
	   }
	 }
	 return movements;
	}

	/**@param  toRight <tt>true</tt> = try to move the currentWrapper to right; <tt>false</tt> = try to move the currentWrapper to left;
	* @param  currentLevel List which contains the CellWrappers for the current level
	* @param  currentIndexInTheLevel
	* @param  currentPriority
	*
	* @return The free GridPosition or -1 is position is not free.
	*/
	protected boolean move(boolean toRight, List currentLevel, int currentIndexInTheLevel, int currentPriority)
	{

	 CellWrapper currentWrapper = (CellWrapper)currentLevel.get(currentIndexInTheLevel);

	 boolean moved = false;
	 int neighborIndexInTheLevel = currentIndexInTheLevel + (toRight ? 1 : -1);
	 int newGridPosition = currentWrapper.getGridPosition() + (toRight ? 1 : -1);

	 // is the grid position possible?

	 if(0 > newGridPosition || newGridPosition >= gridAreaSize)
	 {
	   return false;
	 }

	 // if the node is the first or the last we can move
	 if(toRight && currentIndexInTheLevel == currentLevel.size() - 1 || !toRight && currentIndexInTheLevel == 0)
	 {

	   moved = true;

	 }
	 else
	 {
	   // else get the neighbor and ask his gridposition
	   // if he has the requested new grid position
	   // check the priority

	   CellWrapper neighborWrapper = (CellWrapper)currentLevel.get(neighborIndexInTheLevel);

	   int neighborPriority = neighborWrapper.getPriority();

	   if(neighborWrapper.getGridPosition() == newGridPosition)
	   {
	     if(neighborPriority >= currentPriority)
	     {
	       return false;
	     }
	     else
	     {
	       moved = move(toRight, currentLevel, neighborIndexInTheLevel, currentPriority);
	     }
	   }
	   else
	   {
	     moved = true;
	   }
	 }

	 if(moved)
	 {
	   currentWrapper.setGridPosition(newGridPosition);
	 }
	 return moved;
	}

	/** This Method draws the graph. For the horizontal position
	*  we are using the grid position from each graphcell.
	*  For the vertical position we are using the level position.
	*/
	protected NodeSetGraphic drawGraph(List levels, Point min, Point spacing)
	{	
		initNodesDrawn(levels);

	/*	
//		Top->Down (In Probe-It, visually Bottom-Up)
		List leafLevel = (List)levels.get(0);
		int leafLength = leafLevel.size();
		
		for(int rowCellCount = levels.size()-1; rowCellCount >= 0 ; rowCellCount--)//make sure rowCellCount and colCellCount are right!
		{

			List level = (List)levels.get(rowCellCount);

			for(int colCellCount = 0; colCellCount < level.size(); colCellCount++)
			{
				CellWrapper wrapper = (CellWrapper)level.get(colCellCount);
				NodeSetGraphic view = wrapper.getNodeSetGraphic();
				Node node = view.getNode();

				
				
				//start finding coordinates.
				if(getParents(levels, node) == null)
				{
					int x = ((leafLength-1)*xInc)/leafLength;//-1?
					int y = levels.size()
				}*/
		
		//Bottom->Up (In Probe-It, visually Top-Down)
		//Find Node Positions.
		for(int rowCellCount = 0; rowCellCount < levels.size(); rowCellCount++)//make sure rowCellCount and colCellCount are right!
		{
			List level = (List)levels.get(rowCellCount);

			for(int colCellCount = 0; colCellCount < level.size(); colCellCount++)
			{
				System.out.println("Next Node... Col: "+colCellCount);
				CellWrapper wrapper = (CellWrapper)level.get(colCellCount);
				NodeSetGraphic view = wrapper.getNodeSetGraphic();
				PMLNode node = view.getNode();
				
				
				//start finding coordinates.
				if(node.getCurrentlySelectedInferenceStep().isLeaf())
				{
					if(_canvasManager.getDrawnNode(node) != null)
						_canvasManager.getDrawnNode(node).setReferenceManyTimes();
					else
					{
						int y = (rowCellCount) * yInc;
						int x = (colCellCount) * xInc;
						
//						check previous neighbor
						if(colCellCount > 0)
						{
							NodeSetGraphic neighbor = ((CellWrapper)level.get(colCellCount-1)).nodeG;
							if(x < neighbor.getX() + xInc)
								x = neighbor.getX() + xInc;
						}
						
						view.setXY(x, y);

						//_canvasManager.drawNode(view);
						nodesDrawn[rowCellCount][colCellCount]=true;
					}
					
				}
				else
				{
					int y = (rowCellCount) * yInc;
					int x = (colCellCount) * xInc;
				
					PMLNode[] children = node.getCurrentlySelectedInferenceStep().getAntecedents(); //!!!!!
					List childNodes = findChildrenNSG(levels, children);			
					
					int childrenXavg = 0;
					int numChildren = 0;
					for(int childIndex=0; childIndex<childNodes.size(); childIndex++)
					{	
						boolean repeated=false;
						for(int checking=0; checking<childIndex; checking++)
						{
							if(((NodeSetGraphic)childNodes.get(childIndex)).getURI()/*==*/.equals(((NodeSetGraphic)childNodes.get(checking)).getURI()))
							{
								repeated=true;
							}
						}
						
						if(!repeated)
						{	//could the childNode being added point to a null NodeG, or even just a different NodeG than what is expected?
							childrenXavg += ((NodeSetGraphic)childNodes.get(childIndex)).getX(); //+ (NodeSetGraphic.WIDTH/2.0);
							numChildren ++;
						}
					}
//					check the neighbor NodeSetGraphics to make sure they are not over-lapping.
					x = childrenXavg/numChildren;
					view._idealX = x;
					
					/*
					if(colCellCount > 0 )
					   {
					  		boolean done = false;
							NodeSetGraphic neighbor = ((CellWrapper)level.get(colCellCount-1)).nodeG;
					  		
					 		if(x < neighbor.getX() + xInc)
					 			x = neighbor.getX() + xInc;
					   }
					 */
					
					//*
					if(colCellCount > 0 )
					{
						//check previous neighbor
						boolean done = false;
						NodeSetGraphic neighbor = ((CellWrapper)level.get(colCellCount-1)).nodeG;
						
						//checks for overlapping.
						while(!done && x < neighbor.getX() + xInc)
						{
							//if the current Node is has its center, further to the left 
							//than its neighbor, then swap their positions in level.

//						make sure it won't loop back and forth between this node and the neighbor.
							if(x < neighbor._idealX)
							{
								//temp and neigh might end up pointting at same node.
								level.set(colCellCount-1, view.cellwrapper);
								level.set(colCellCount, neighbor.cellwrapper);
								colCellCount--;
							}
							else
							{
								x = neighbor.getX() + xInc;
								done = true;
							}
							
							//Node at leftmost index 
							if(colCellCount > 0)
								neighbor = ((CellWrapper)level.get(colCellCount-1)).nodeG;
							else 
								done = true;
							
						}
						
							
					}//*/
					view.setXY(x, y);
					
					//_canvasManager.drawNode(view);
					nodesDrawn[rowCellCount][colCellCount]=true;
				}
			}
		}
		//* DRAWING OUT OF BOUNDS, WHY???
			 
		//get leaf Children centered as close to their parents as possible.
		List leafLevel = (List)levels.get(0);
		int leafLength = leafLevel.size();
		
		//Setup IdealX's
		for(int pos = 0; pos < leafLength; pos++)
		{
			CellWrapper wrapper = (CellWrapper)leafLevel.get(pos);
			NodeSetGraphic view = wrapper.getNodeSetGraphic();
			
			int x = view.getX();
			
			List parentNodes = getParents(levels, view);			
			
			int parentXavg = 0;
			int numParents = 0;
			for(int parentIndex=0; parentIndex < parentNodes.size(); parentIndex++)
			{	
				boolean repeated=false;
				for(int checking=0; checking < parentIndex; checking++)
				{
					if(((NodeSetGraphic)parentNodes.get(parentIndex)).getURI().equals(((NodeSetGraphic)parentNodes.get(checking)).getURI()))
					{
						repeated=true;
					}
				}
				
				if(!repeated)
				{	//could the childNode being added point to a null NodeG, or even just a different NodeG than what is expected?
					parentXavg += ((NodeSetGraphic)parentNodes.get(parentIndex)).getX(); //+ (NodeSetGraphic.WIDTH/2.0);
					numParents ++;
				}
			}
//			check the neighbor NodeSetGraphics to make sure they are not over-lapping.
			x = parentXavg/numParents;
			view._idealX = x;
		}
		
//		going from left to right
		for(int pos = 0; pos < leafLength; pos++)
		{
			CellWrapper wrapper = (CellWrapper)leafLevel.get(pos);
			NodeSetGraphic view = wrapper.getNodeSetGraphic();
			
			int x = view._idealX;
			int y = view.getY();//0
			
			if(pos > 0 )
			{
				//check previous neighbor
				boolean done = false;
				NodeSetGraphic neighbor = ((CellWrapper)leafLevel.get(pos-1)).nodeG;
				
				//checks for overlapping.
				while(!done && x < neighbor.getX() + xInc)
				{
					//if the current Node is has its center, further to the left 
					//than its neighbor, then swap their positions in level.

//				make sure it won't loop back and forth between this node and the neighbor.
					if(x < neighbor._idealX)
					{	
						leafLevel.set(pos-1, view.cellwrapper);
						leafLevel.set(pos, neighbor.cellwrapper);
						pos--;
					}
					else
					{
						x = neighbor.getX() + xInc;
						done = true;
					}
					
					//Node at leftmost index 
					if(pos > 0)
						neighbor = ((CellWrapper)leafLevel.get(pos-1)).nodeG;
					else 
						done = true;
					
				}					
			}
			
			x = (pos) * xInc;
			
			view.setXY(x, y);
		}
		
		//from right to left.
		for(int pos = leafLength-1; pos >= 0; pos--)
		{
			CellWrapper wrapper = (CellWrapper)leafLevel.get(pos);
			NodeSetGraphic view = wrapper.getNodeSetGraphic();
			
			int x = view._idealX;
			int y = view.getY();//0
			
			
			if(pos < leafLength-1 )
			{
				//check previous neighbor
				boolean done = false;
				NodeSetGraphic neighbor = ((CellWrapper)leafLevel.get(pos+1)).nodeG;
				
				//checks for overlapping.
				while(!done && x+xInc > neighbor.getX())
				{
					//if the current node is further to the left than its neighbor when its ideal center greater than its neighbor, 
					//then swap their level positions.

					//make sure it won't loop back and forth between this node and the neighbor.
					if(x > neighbor._idealX)
					{
						leafLevel.set(pos+1, view.cellwrapper);
						leafLevel.set(pos, neighbor.cellwrapper);
						pos++;
					}
					else
					{
						x = neighbor.getX() - xInc;
						done = true;
					}
					
					//Node at rightmost index 
					if(pos < leafLength-1)
						neighbor = ((CellWrapper)leafLevel.get(pos+1)).nodeG;
					else 
						done = true;
					
				}					
			}
			
			x = (pos) * xInc;
				
			view.setXY(x, y);
		}
		
		//Drawing Nodes and Lines
		for(int rowCellCount = 0; rowCellCount < levels.size(); rowCellCount++)
		{
			List level = (List)levels.get(rowCellCount);

			for(int colCellCount = 0; colCellCount < level.size(); colCellCount++)
			{
				CellWrapper wrapper = (CellWrapper)level.get(colCellCount);
				NodeSetGraphic view = wrapper.getNodeSetGraphic();
				PMLNode node = view.getNode();
				
				view.setup();
				_canvasManager.drawNode(view);
				
				if(!node.getCurrentlySelectedInferenceStep().isLeaf() && nodesDrawn[rowCellCount][colCellCount]==true)
				{
					PMLNode[] children = node.getCurrentlySelectedInferenceStep().getAntecedents(); //!!!!!
				
					List childNodes = findChildrenNSG(levels, children);
				
					//finding parent
					for (int i = 0; i < childNodes.size(); i++)
					{
						_canvasManager.makeEdge(view, (NodeSetGraphic) childNodes.get(i), true);
					}
				}
			}
		}
		
		
		CellWrapper wrapper = ((CellWrapper)((List) levels.get(levels.size()-1)).get(0));
		NodeSetGraphic view = wrapper.getNodeSetGraphic();
		
		return view;
	}
	
	//Like ChildrenNSG(), just to go backwards.
	private List getParents(List levels, NodeSetGraphic child)
	{
		List parents = new ArrayList();
		
		for(int row = 0; row < levels.size(); row++)
		{
			List curLevel = (List)levels.get(row);
			for(int col = 0; col < curLevel.size(); col++)
			{
				CellWrapper wrapper = (CellWrapper) curLevel.get(col);
				NodeSetGraphic parentGraphic = wrapper.nodeG;
				PMLNode curParent = wrapper.getNode();
				
				for(int j = 0; !curParent.getCurrentlySelectedInferenceStep().isLeaf() && j < curParent.getCurrentlySelectedInferenceStep().getAntecedents().length; j++)
					if(child.getURI().equals(curParent.getCurrentlySelectedInferenceStep().getAntecedents()[j].getURI()))
						parents.add(parentGraphic);
			}
		}
		
		return parents;
	}
//	finding parent
	// Have to overcome the fact that there might be more than one parent
	
	/**
	 * finds the parent position of the given child in the List levels.
	 * 
	 * @param levels
	 * @param parentLevel
	 * @param child
	 * @return position of parent in the level.
	 */
	private int getParentPosition(List levels, /*int parentLevel,*/ PMLNode child)
	{
		System.out.println("Starting getParentPosition()...");
		int childOccurence = 0;
		int parentPosition =-1;
		
		for(int parentLevel=0; parentLevel < levels.size(); parentLevel++)
		{
			List level = (List) levels.get(parentLevel);
			
			for(int i = 0; i < level.size(); i++)
			{
				System.out.println("In Loop. i =" + i);
				PMLNode curParent = (PMLNode)( ((CellWrapper)level.get(i)).getNode() );
				for(int j = 0; !curParent.getCurrentlySelectedInferenceStep().isLeaf() && j < curParent.getCurrentlySelectedInferenceStep().getAntecedents().length; j++)
				{
					
					if(child.equals(curParent.getCurrentlySelectedInferenceStep().getAntecedents()[j]))
					{
						childOccurence++;
						
						if(childOccurence > 1)
						{
							System.out.println("More than one occurence of the Child node in getParentPosition()");
							return -1;
						}
							
						parentPosition = i;
						
					}
				}
			}
		}
		
		if(parentPosition == -1)
			System.out.println("No Child node found in getParentPosition()");
		
		return parentPosition;
	}
	
	protected void setMaxWidth(List levels)
	{
		maxWidth = 0;
		for(int i=0; i<levels.size(); i++)
		{
			List level = (List)levels.get(i);
			if(level.size() > maxWidth)
				maxWidth=level.size();
		}
	}

	protected void initNodesDrawn(List levels)
	{
		setMaxWidth(levels);
		
		nodesDrawn = new boolean[maxHeight][maxWidth];
		
		for(int i=0; i<maxHeight; i++)
			for(int j=0; j<maxWidth; j++)
				nodesDrawn[i][j] = false;
	}
	
	/**
	 * finds the position of the child in the parent's children array.
	 * 
	 * @param parent
	 * @param child
	 * @return position or -1 if not found.
	 */
	private int getChildPosition(PMLNode parent, PMLNode child)
	{
		
		for(int i = 0; !parent.getCurrentlySelectedInferenceStep().isLeaf() && i < parent.getCurrentlySelectedInferenceStep().getAntecedents().length; i++)
		{
			if(child.equals(parent.getCurrentlySelectedInferenceStep().getAntecedents()[i]))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	
	private List findChildrenNSG(List levels, PMLNode[] children)
	{
		//List level =(List) levels.get(childrenLevel);
		List childNodeSetGraphics = new ArrayList(); //How do you instantiate a List????
		
		for(int currentLevel = 0; currentLevel < levels.size(); currentLevel++)
		{	
			List level =(List) levels.get(currentLevel);
			//if(children != null)			
			for(int i=0; i < level.size(); i++)
			{
				CellWrapper currWrapper = (CellWrapper)level.get(i);
				PMLNode currChild = currWrapper.getNode(); 
				
				for(int j=0; j < children.length; j++)
				{
					if(currChild.getURI().equals(children[j].getURI()))
					{
						
						childNodeSetGraphics.add(currWrapper.nodeG);
					}
				}
			}
		}
		
		
		return childNodeSetGraphics;
		/*for(int i=0; childNodeSetGraphics.size() < children.length && i<childrenLevel && i<levels.size(); i++)
		{
			level =(List) levels.get(i);
			for(int j=0; childNodeSetGraphics.size() < children.length && j<level.size(); j++)
			{
				CellWrapper currWrapper = (CellWrapper)level.get(j);
				Node currChild = currWrapper.getNode(); 
				for(int c=0; c < children.length && childNodeSetGraphics.size() < children.length; c++)
				{
					if(currChild.equals(children[c]))
					{
						childNodeSetGraphics.add(currWrapper.nodeG);
					}
				}
			}
		}
		
		for(int i=childrenLevel+1; childNodeSetGraphics.size() < children.length && i<levels.size(); i++)
		{
			level =(List) levels.get(i);
			for(int j=0; childNodeSetGraphics.size() < children.length && j<level.size(); j++)
			{
				CellWrapper currWrapper = (CellWrapper)level.get(j);
				Node currChild = currWrapper.getNode(); 
				for(int c=0; c < children.length && childNodeSetGraphics.size() < children.length; c++)
				{
					if(currChild.equals(children[c]))
					{
						childNodeSetGraphics.add(currWrapper.nodeG);
					}
				}
			}
		}
		
		return childNodeSetGraphics;*/
	}
	
	
/*	private NodeSetGraphic drawDAG(Node node, int _currentLevel, int level)
	{
		if(_canvasManager.getDrawnNode(node) != null)
		{
			_canvasManager.getDrawnNode(node).setReferenceManyTimes();
			return _canvasManager.getDrawnNode(node);
		}
		
		if (node.getCurrentlySelectedInferenceStep().isLeaf())
		{
			System.out.println("only one node and is a parent");
			return drawOnlyChild(node);
		}
		

		int counter = 0;
		ArrayList childNodes = new ArrayList();
		int numTest = node.getCurrentlySelectedInferenceStep().getAntecedents().length;
		for (int i = 0; i < numTest; i ++)
		{
			NodeSetGraphic aChild = null;
			try
			{
				Node aNode = node.getCurrentlySelectedInferenceStep().getAntecedents()[i];

				if ((_currentLevel > 1) && (!aNode.getCurrentlySelectedInferenceStep().isLeaf()))
				{
					aChild = drawDAG(aNode, _currentLevel - 1, level - 1);
				} else
				{
					aChild = drawChild(level, counter, numTest, aNode);
				}
				childNodes.add(aChild);
				counter++;
			} catch (Exception _e)
			{
				_e.printStackTrace();
				return null;
			}
		}
		return drawParent(node, level, childNodes);
		//return perform(_canvasManager, true, node);
	}*/

	private NodeSetGraphic drawOnlyChild(PMLNode node)
	{
		return _canvasManager.drawNode(50, 50, node);
	}
/*
	private NodeSetGraphic drawParent(Node node, int level, ArrayList childNodes)
	{
		NodeSetGraphic firstChild = (NodeSetGraphic) childNodes.get(0);
		NodeSetGraphic lastChild = (NodeSetGraphic) childNodes.get(childNodes
				.size() - 1);

		int finalX = 0;
		int lastChildX = _levels[level - 1];
		int lastParentX = _levels[level];

		if (childNodes.size() > 1)
		{
			int childrenWidth = lastChild.getX() - firstChild.getX();
			int mid = childrenWidth / 2;
			int perfectX = _levels[level - 1] - mid;

			if (perfectX > lastParentX + xInc)
			{
				finalX = perfectX;
			} else
			{
				finalX = lastParentX + xInc;
			}
			_levels[level] = finalX;
		} else
		{
			if (lastChildX > lastParentX + xInc)
			{
				finalX = lastChildX;
			} else
			{
				finalX = lastParentX + xInc;
			}
			_levels[level] = finalX;
		}

		if (finalX == 0)
			System.out.println("LAGGGG");

		int parentY = (level) * yInc;
		NodeSetGraphic parent = _canvasManager.drawNode(finalX, parentY, node);

		for (int i = 0; i < childNodes.size(); i++)
		{
			_canvasManager.makeEdge(parent, (NodeSetGraphic) childNodes.get(i));
		}
		return parent;
	}

	private NodeSetGraphic drawChild(int level, int counter, int numTest, Node aNode)
	{
		if(_canvasManager.getDrawnNode(aNode) != null)
		{
			_canvasManager.getDrawnNode(aNode).setReferenceManyTimes();
			return _canvasManager.getDrawnNode(aNode);
		}
		
		NodeSetGraphic aChild;
		int y = (level - 1) * yInc;
		if (counter == 0)
		{
			int cur_childX = _levels[level - 1];
			int cur_parX = _levels[level];

			if (cur_childX < cur_parX)
			{
				if (numTest == 1)
				{
					int perfectX = cur_parX + xInc;
					_levels[level - 1] = perfectX;
					aChild = _canvasManager.drawNode(perfectX, y, aNode);
				} else
				{
					int perfectX = cur_parX;
					_levels[level - 1] = perfectX;
					aChild = _canvasManager.drawNode(perfectX, y, aNode);
				}
			} else
			{
				int occupiedXforChild = _levels[level - 1];
				_levels[level - 1] = occupiedXforChild + xInc;
				int x = occupiedXforChild + xInc;
				aChild = _canvasManager.drawNode(x, y, aNode);
			}
		} else
		{
			int occupiedXforChild = _levels[level - 1];
			_levels[level - 1] = occupiedXforChild + xInc;
			int x = occupiedXforChild + xInc;

			aChild = _canvasManager.drawNode(x, y, aNode);
		}
		return aChild;
	}

	private void setUpLevels()
	{
		_levels = new int[maxHeight];
		for (int i = 0; i < _levels.length; i++)
			_levels[i] = -200;
	}*/
	
	/** Debugdisplay for the edge crosses indicators on the System out
	*/
	protected void displayEdgeCrossesValues(List levels)
	{
		System.out.println("----------------Edge Crosses Indicator Values"/*#Frozen*/);

		for(int i = 0; i < levels.size() - 1; i++)
		{
			// Get the current level
			List currentLevel = (List)levels.get(i);
			System.out.print("Level (" + i + "):"/*#Frozen*/);
			for(int j = 0; j < currentLevel.size(); j++)
			{
				CellWrapper sourceWrapper = (CellWrapper)currentLevel.get(j);

				System.out.print(NumberFormat.getNumberInstance().format(sourceWrapper.getEdgeCrossesIndicator()) + " - "/*#Frozen*/);
			}
			System.out.println();
		}
	}

	/** Debugdisplay for the grid positions on the System out
	*/
	protected void displayGridPositions(List levels)
	{

	 System.out.println("----------------GridPositions"/*#Frozen*/);

	 for(int i = 0; i < levels.size() - 1; i++)
	 {
	   // Get the current level
	   List currentLevel = (List)levels.get(i);
	   System.out.print("Level (" + i + "):"/*#Frozen*/);
	   for(int j = 0; j < currentLevel.size(); j++)
	   {
	     CellWrapper sourceWrapper = (CellWrapper)currentLevel.get(j);
	     System.out.print(NumberFormat.getNumberInstance().format(sourceWrapper.getGridPosition()) + " - "/*#Frozen*/);
	   }
	   System.out.println();
	 }
	}

	/** Debugdisplay for the priorities on the System out
	*/
	protected void displayPriorities(List levels)
	{

	 System.out.println("----------------down Priorities"/*#Frozen*/);

	 for(int i = 0; i < levels.size() - 1; i++)
	 {
	   // Get the current level
	   List currentLevel = (List)levels.get(i);
	   System.out.print("Level (" + i + "):"/*#Frozen*/);
	   for(int j = 0; j < currentLevel.size(); j++)
	   {
	     CellWrapper sourceWrapper = (CellWrapper)currentLevel.get(j);
	     System.out.print(sourceWrapper.getPriority() + /*" (" +
	                        sourceWrapper.nearestDownNeighborLevel + ") " +*/
	                      " - "/*#Frozen*/);
	   }
	   System.out.println();
	 }
	}
	
	public class CellWrapper implements Comparable
	{

		/** sum value for edge Crosses
		 */
		private double edgeCrossesIndicator = 0;
		/** counter for additions to the edgeCrossesIndicator
		 */
		private int additions = 0;
		/** the vertical level where the cell wrapper is inserted
		 */
		int level = 0;
		/** current position in the grid
		 */
		int gridPosition = 0;
		/** priority for movements to the barycenter
		 */
		int priority = 0;
		/** reference to the wrapped cell
		 */
		NodeSetGraphic nodeG;

		/** creates an instance and memorizes the parameters
		 */
		public CellWrapper(int level, double edgeCrossesIndicator, NodeSetGraphic aNode)
		{
			this.level = level;
			this.edgeCrossesIndicator = edgeCrossesIndicator;
			this.nodeG = aNode;
			additions++;
		}
		
		public CellWrapper(NodeSetGraphic aNode)
		{
			this.nodeG = aNode;
		}
		
		public void setBasic(int level, double edgeCrossesIndicator)
		{
			this.level =level;
			this.edgeCrossesIndicator = edgeCrossesIndicator;
			additions++;
		}

		/** returns the NodeSetGraphic
		 */
		public NodeSetGraphic getNodeSetGraphic()
		{
			return nodeG;
		}
		
		public PMLNode getNode()
		{
			return nodeG.getNode();
		}

		/** resets the indicator for edge crosses to 0
		 */
		public void resetEdgeCrossesIndicator()
		{
			edgeCrossesIndicator = 0;
			additions = 0;
		}

		/** retruns the average value for the edge crosses indicator
		 *
		 *  for the wrapped cell
		 *
		 */

		public double getEdgeCrossesIndicator()
		{
			if(additions == 0)
				return 0;
			return edgeCrossesIndicator / additions;
		}

		/** Addes a value to the edge crosses indicator
		 *  for the wrapped cell
		 *
		 */
		public void addToEdgeCrossesIndicator(double addValue)
		{
			edgeCrossesIndicator += addValue;
			additions++;
		}

		/** gets the level of the wrapped cell
		 */
		public int getLevel()
		{
			return level;
		}

		/** gets the grid position for the wrapped cell
		 */
		public int getGridPosition()
		{
			return gridPosition;
		}

		/** Sets the grid position for the wrapped cell
		 */
		public void setGridPosition(int pos)
		{
			this.gridPosition = pos;
		}

		/** increments the the priority of this cell wrapper.
		 *
		 *  The priority was used by moving the cell to its
		 *  barycenter.
		 *
		 */

		public void incrementPriority()
		{
			priority++;
		}

		/** returns the priority of this cell wrapper.
		 *
		 *  The priority was used by moving the cell to its
		 *  barycenter.
		 */
		public int getPriority()
		{
			return priority;
		}

		/**
		 * @see java.lang.Comparable#compareTo(Object)
		 */
		public int compareTo(Object compare)
		{
			if(((CellWrapper)compare).getEdgeCrossesIndicator() == this.getEdgeCrossesIndicator())
				return 0;

			double compareValue = (((CellWrapper)compare).getEdgeCrossesIndicator() - this.getEdgeCrossesIndicator());

			return (int)(compareValue * 1000);

		}
	}


	
}
