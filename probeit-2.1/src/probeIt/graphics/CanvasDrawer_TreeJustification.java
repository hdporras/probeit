package probeIt.graphics;

import java.util.ArrayList;

import pml.PMLNode;
import pml.util.pml.PMLStatistics;
import probeIt.graphics.buttons.ViewerButton;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.ui.model.ViewsModel;
import diva.canvas.JCanvas;

public class CanvasDrawer_TreeJustification
{
	private PMLNode _rootNode;
	private int maxHeight;
	private JustificationCanvas _canvasManager;
	private int[] _levels;
	private NodeSetGraphic _rootGraphic;
	private int xInc = NodeSetGraphic.WIDTH + 30;
	private int yInc = NodeSetGraphic.HEIGHT + ViewerButton.HEIGHT + 50;

	public CanvasDrawer_TreeJustification(JustificationCanvas canvas)
	{
		_rootNode = ViewsModel.getInstance().getJustification();
		PMLStatistics stats = new PMLStatistics(_rootNode);
		maxHeight = stats.getProofHeight();
		_canvasManager = canvas;	
	}

	public NodeSetGraphic getRootGraphic()
	{return _rootGraphic;}

	public JCanvas draw()
	{
		setUpLevels();
		_rootGraphic = drawDAG(_rootNode, 1000, maxHeight - 1);
		return _canvasManager.getCurrentDAG();
	}

	private NodeSetGraphic drawDAG(PMLNode node, int _currentLevel, int level)
	{
		if (node.getCurrentlySelectedInferenceStep().isLeaf())
		{
			System.out.println("only one node and is a parent");
			return drawOnlyChild(node);
		}
		
		int counter = 0;
		ArrayList childNodes = new ArrayList();
				
		int numTest = node.getCurrentlySelectedInferenceStep().getAntecedents().length;
		for (int i = 0; i < node.getCurrentlySelectedInferenceStep().getAntecedents().length; i ++)
		{
			NodeSetGraphic aChild = null;
			PMLNode aNode = node.getCurrentlySelectedInferenceStep().getAntecedents()[i];

			if ((_currentLevel > 1) && (!aNode.getCurrentlySelectedInferenceStep().isLeaf()))
				aChild = drawDAG(aNode, _currentLevel - 1, level - 1);
			else
				aChild = drawChild(level, counter, numTest, aNode);
			childNodes.add(aChild);
			counter++;
		}
		return drawParent(node, level, childNodes);
	}

	private NodeSetGraphic drawOnlyChild(PMLNode node)
	{
		return _canvasManager.drawNode(50, 50, node);
	}

	private NodeSetGraphic drawParent(PMLNode node, int level, ArrayList childNodes)
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

	private NodeSetGraphic drawChild(int level, int counter, int numTest, PMLNode aNode)
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
		_levels = new int[maxHeight];
		for (int i = 0; i < _levels.length; i++)
			_levels[i] = -NodeSetGraphic.WIDTH;
	}
}
