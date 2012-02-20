package probeIt.ui.global;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import pml.PMLNode;
import pml.util.pml.PMLStatistics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.ui.Toolbar;
import probeIt.ui.model.ViewsModel;

import diva.gui.toolbox.JPanner;
import javax.swing.*;
import probeIt.ProbeIt;
import probeIt.util.JarResourceLoader;
public class JustificationView extends JPanel {
	JustificationBuilder builder;
	private final static int PANNER_SIZE = 165;
	static Toolbar theTB;
	JScrollPane _scroll;
	JPanner _panner;
	JViewport _viewport;
	JPanel graph;
	AffineTransform _transform;
	private Point origin = null;
	//private Dimension canvasSize;
	static JustificationView instance;
	JustificationCanvas canvas;
	Cursor handCursor;
	Cursor closedHandCursor;

	public static Toolbar getToolbar()
	{return theTB;}
	
	public JustificationView() {
		super();

		//System.out.println("[JustificationView()]");

		//JarResourceLoader loader = new JarResourceLoader();
		
		
		// log that user has accessed this global view
		probeIt.ProbeIt.getInstance().getLogger().setGlobalViewBool(true);

		//PMLNode rootNode = ProbeIt.getInstance().getEmbeddedProbeIt().getJustification();

		canvas = new JustificationCanvas();
		canvas.setProvenance(ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_P());
		canvas.getJCanvas().addMouseMotionListener(new DragListener());
		canvas.getJCanvas().addMouseListener(new ClickListener());

		_scroll = new JScrollPane(canvas.getJCanvas());

		this.setLayout(new BorderLayout());

		_scroll.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		_scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_NEVER);
		_scroll.getVerticalScrollBar().addMouseMotionListener(new PannerListener());
		_scroll.getVerticalScrollBar().addMouseListener(new PannerListener1());

		_scroll.getHorizontalScrollBar().addMouseMotionListener(new PannerListener());
		_scroll.getHorizontalScrollBar().addMouseListener(new PannerListener1());

		_panner = new JPanner();
		_panner.addMouseMotionListener(new PannerListener());
		_panner.addMouseListener(new PannerListener1());
		
		_panner.setViewport(_scroll.getViewport());
		_panner.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		_panner.setMinimumSize(new Dimension(PANNER_SIZE, PANNER_SIZE));
		_panner.setPreferredSize(new Dimension(PANNER_SIZE, PANNER_SIZE));
		_panner.setMaximumSize(new Dimension(PANNER_SIZE, PANNER_SIZE));

		graph = new JPanel();
		OverlayLayout ol = new OverlayLayout(graph);
		graph.setLayout(ol);
		
		System.out.println(javax.swing.SwingUtilities.isEventDispatchThread());
		
		_panner.setOpaque(true);
		_scroll.setOpaque(true);

		//_scroll.setEnabled(false);
		_scroll.setVisible(false);
		
		// set aligment of panner and canvas scroll relative to eachother
		_scroll.setAlignmentX(new Float(0));
		_scroll.setAlignmentY(new Float(1));

		_panner.setAlignmentX(0);
		_panner.setAlignmentY(1);

		graph.add(_panner);
		graph.add(_scroll);

		add(graph, BorderLayout.CENTER);

		_viewport = _panner.getViewport();

		if (theTB == null)
		{
			theTB = new Toolbar(Toolbar.CanvasType.justificationView);
			theTB.setup();
		}

		add(theTB, BorderLayout.NORTH);

		System.out.println("Builder starting...................................................................................");
		
		builder = new JustificationBuilder(canvas, this);
		System.out.println("starting...................................................................................");
		builder.start();
		
		System.out.println("Builder ended");

		instance = this;
	}
	
	public void setPointOfInterest(int x, int y)
	{
		canvas.setPointOfInterest(x, y);
	}
	
	public void highlightNodeSet(String uri)
	{
		canvas.highlightNodeSetBlock(uri);
		_scroll.getViewport().setViewPosition(canvas.getPointOfInterest());
	}
	
	public void setInteractiveView()
	{
		System.out.println("setting view as interactive (JusificationView)");
		_scroll.setEnabled(true);
		_scroll.setVisible(true);
		System.out.println("view set (JusificationView)");
		_scroll.getViewport().setViewPosition(canvas.getPointOfInterest());
		System.out.println("view position set (JusificationView)");
	}

	/*
	 * private boolean goodAspectRatio(int width, int height) { double value =
	 * (double) ((double) width / (double) height);
	 * 
	 * if (value > 4.5) return false; else if (value < 0.15) return false; else
	 * return true; }
	 */

	public JPanner getPanner() {
		return _panner;
	}
	
	public JViewport getViewport()
	{
		return _viewport;
	}

	public static JustificationView getInstance() {
		return instance;
	}
	
	public void setNormalCursor()
	{
		this.canvas.getCurrentDAG().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void setHandCursor()
	{
		this.canvas.getCurrentDAG().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	}

	private class PannerListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) {
			repaint();
		}

		public void mouseMoved(MouseEvent e) {
			repaint();
		}
	}

	private class PannerListener1 implements MouseListener {
		public void mousePressed(MouseEvent e) {
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			repaint();
		}

		public void mouseClicked(MouseEvent e) {
			repaint();
		}

		public void mouseExited(MouseEvent e) {
			repaint();
		}

		public void mouseEntered(MouseEvent e) {
			repaint();
		}
	}

	private class ClickListener extends MouseAdapter implements MouseListener {
		// resets the origin so that dragging will not start at the same spot.
		public void mouseReleased(MouseEvent evt) {
			
			setNormalCursor();
			
			if (_viewport != null && (evt.getModifiers() & MouseEvent.BUTTON1_MASK) != 0)
			{
				origin = null;
				repaint();
			}
		}

		public void mousePressed(MouseEvent evt) {
	
			setHandCursor();
			
			if (_viewport != null && (evt.getModifiers() & MouseEvent.BUTTON3_MASK) != 0)
			{
				int x = evt.getX() - (int) (_viewport.getVisibleRect().width / 2.0);
				int y = evt.getY() - (int) (_viewport.getVisibleRect().height / 2.0);

				// check if it's out of viewing bounds
				if ((x + _viewport.getWidth()) > _viewport.getView().getSize().getWidth()) // canvasSize.width)
					x = (int) _viewport.getView().getSize().getWidth() - _viewport.getWidth();
				if (x < 0)
					x = 0;
				if ((y + _viewport.getHeight()) > _viewport.getView().getSize().getHeight())// canvasSize.height)
					y = (int) _viewport.getView().getSize().getHeight() - _viewport.getHeight();
				if (y < 0)
					y = 0;

				_viewport.setViewPosition(new Point(x, y));
				_panner.getViewport().repaint();
			}
		}

	}

	private class DragListener extends MouseAdapter implements MouseMotionListener
	{
		int changeInX;
		int changeInY;
		int x, y;

		public void mouseDragged(MouseEvent evt) {
			if (origin != null) {
				if (_viewport != null
						&& (evt.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
					System.out.println("New : " + evt.getXOnScreen() + ", "
							+ evt.getYOnScreen());

					changeInX = 2 * Math.abs(evt.getXOnScreen()
							- (int) origin.getX());
					changeInY = 2 * Math.abs(evt.getYOnScreen()
							- (int) origin.getY());

					// update with changes
					if (evt.getXOnScreen() < (int) origin.getX())
						x = (int) _viewport.getViewPosition().getX()
								+ changeInX;
					else
						x = (int) _viewport.getViewPosition().getX()
								- changeInX;

					if (evt.getYOnScreen() < (int) origin.getY())
						y = (int) _viewport.getViewPosition().getY()
								+ changeInY;
					else
						y = (int) _viewport.getViewPosition().getY()
								- changeInY;

					System.out.println("viewWidth = "
							+ _viewport.getView().getSize().getWidth()
							+ ", viewHeight = "
							+ _viewport.getView().getSize().getHeight());
					System.out.println("Width = " + _viewport.getBounds().width
							+ ", Height = " + _viewport.getBounds().height);

					// check if it's out of viewing bounds
					if ((x + _viewport.getWidth()) > _viewport.getView()
							.getSize().getWidth()) // canvasSize.width)
						x = (int) _viewport.getView().getSize().getWidth()
								- _viewport.getWidth();
					if (x < 0)
						x = 0;
					if ((y + _viewport.getHeight()) > _viewport.getView()
							.getSize().getHeight())// canvasSize.height)
						y = (int) _viewport.getView().getSize().getHeight()
								- _viewport.getHeight();
					if (y < 0)
						y = 0;// */

					_viewport.setViewPosition(new Point(x, y));

					origin = evt.getLocationOnScreen();

				}

				repaint();
			} else {
				if (_viewport != null
						&& (evt.getModifiers() & MouseEvent.BUTTON1_MASK) != 0)
					origin = evt.getLocationOnScreen();

				repaint();
			}

			System.out.println("X = " + x + ", Y = " + y);
		}
	}
}
