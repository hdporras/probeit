package probeIt.graphics;

import java.util.ArrayList;
import probeIt.graphics.figure.ForestNodeSetGraphic;
import pml.PMLNode;
import pml.util.pml.PMLStatistics;
import pml.impl.serializable.PMLInferenceStep;
import probeIt.graphics.buttons.ViewerButton;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.ui.model.ViewsModel;
import diva.canvas.JCanvas;



public class CanvasDrawer_ForestJustification
{
	private PMLNode _rootNode;
	private int forestHeight;
	private JustificationCanvas _canvasManager;
	private int[] _levels;
	private NodeSetGraphic _rootGraphic;
	private int xInc = NodeSetGraphic.WIDTH + 30;
	private int yInc = NodeSetGraphic.HEIGHT + ViewerButton.HEIGHT + 50;

	public CanvasDrawer_ForestJustification(JustificationCanvas canvas)
	{
		_rootNode = ViewsModel.getInstance().getJustification();
		PMLStatistics stats = new PMLStatistics(_rootNode);
		_canvasManager = canvas;	
		forestHeight = stats.getForestHeight();
	}

	public NodeSetGraphic getRootGraphic()
	{return _rootGraphic;}

	public JCanvas draw()
	{
		setUpLevels();
		_rootGraphic = drawDAG(_rootNode, 1000, forestHeight-1);
		return _canvasManager.getCurrentDAG();
	}

	private NodeSetGraphic drawDAG(PMLNode node, int _currentLevel, int level)
	{
		if(node.getNumberOfInferenceSteps() <= 0)
		{return drawOnlyChild(node);}
	
		else if(node.getNumberOfInferenceSteps() >= 1 && PMLStatistics.isForestLeaf(node))
		{
			System.out.println("this should only be called for a proof with a single IS and no antecedents for each***********************************************************************************");
			return drawOnlyChild(node);
		}
		
		else
		{
			ArrayList childNodes;
			PMLInferenceStep anIS;
			int counter = 0;
			ArrayList[] anteGroups = new ArrayList[node.getNumberOfInferenceSteps()];
			for(int i = 0; i < node.getNumberOfInferenceSteps(); i ++)
			{
				childNodes = new ArrayList();
				anIS = node.getInferenceSteps().get(i);
				NodeSetGraphic aChild = null;
				
				if(anIS.getAntecedents() != null && anIS.getAntecedents().length > 0)
				{
					
					int numTest = anIS.getAntecedents().length;
					for(int j = 0; j < anIS.getAntecedents().length; j ++)
					{
						PMLNode aNode = anIS.getAntecedents()[j];
						
						if ((_currentLevel > 1) && (!PMLStatistics.isForestLeaf(aNode)))
							aChild = drawDAG(aNode, _currentLevel - 1, level - 1);
						else
							aChild = this.drawChild(level, counter, numTest, aNode);
						
						childNodes.add(aChild);
						counter ++;
					}
				}
				anteGroups[i] = childNodes;
			}
			
			if(node.getNumberOfInferenceSteps() > 1)
				return drawForestParent(node, level, anteGroups);
			else
				return drawParent(node, level, anteGroups);
		}
	}

	private NodeSetGraphic drawOnlyChild(PMLNode node)
	{
		return _canvasManager.drawNode(50, 50, node);
	}

	private NodeSetGraphic drawForestParent(PMLNode node, int level, ArrayList[] anteGroups)
	{
		ArrayList childNodes = new ArrayList();
		for(int i = 0; i < anteGroups.length; i ++)
		{
			for(int j = 0; j < anteGroups[i].size(); j ++)
				childNodes.add(anteGroups[i].get(j));
		}
		
		NodeSetGraphic firstChild = (NodeSetGraphic) childNodes.get(0);
		NodeSetGraphic lastChild = (NodeSetGraphic) childNodes.get(childNodes.size() - 1);

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
		
		NodeSetGraphic parent = _canvasManager.drawForestNode(finalX, parentY, node);
		ForestNodeSetGraphic forestParent = (ForestNodeSetGraphic)parent;
		
		for (int i = 0; i < anteGroups.length; i++)
		{
			for(int j = 0; j < anteGroups[i].size(); j ++)
				_canvasManager.makeForestEdge(forestParent, (NodeSetGraphic)anteGroups[i].get(j), i);
		}
		return parent;
	}
	
	private NodeSetGraphic drawParent(PMLNode node, int level, ArrayList[] anteGroups)
	{
		ArrayList childNodes = new ArrayList();
		for(int i = 0; i < anteGroups.length; i ++)
		{
			for(int j = 0; j < anteGroups[i].size(); j ++)
				childNodes.add(anteGroups[i].get(j));
		}
		
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

	private NodeSetGraphic drawChild(int level, int counter, int numTest,
			PMLNode aNode)
	{
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
		_levels = new int[forestHeight];
		for (int i = 0; i < _levels.length; i++)
			_levels[i] = -NodeSetGraphic.WIDTH;
	}
}
