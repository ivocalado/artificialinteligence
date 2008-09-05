import java.util.*;
import java.awt.*;
import java.applet.*;

class Graph
// contains a Matrix of distances for a graph.
{	protected int N;
	double M[][];
	final static double INFTY=1e50;
	public Graph (int n)
	{	N=n;
		M=new double[n][n];
		int i,j;
		// initially disconnect all points
		for (i=0; i<n; i++)
			for (j=0; j<n; j++)
				{	if (i!=j) connect(i,j,INFTY);
					else connect(i,j,0);
				}
	}
	void connect (int i, int j, double x) { M[i][j]=x; }
	final double distance (int i, int j) { return M[i][j]; }
	final int size () { return N; }
}

class Points
// contains a vector of points with x- and y-coordinate
{   int N;
	public double X[],Y[];
	public Points (int n)
	{	X=new double[n];
		Y=new double[n];
		N=n;
	}
	public void random (Random r)
	// generate random points
	{	int i;
		for (i=0; i<N; i++)
		{	X[i]=r.nextDouble();
			Y[i]=r.nextDouble();
		}
	}
	public void square (int n, int m)
	// generate points in a nxm grid
	{	int i,j,k=0;
		for (i=0; i<n; i++)
			for (j=0; j<m; j++)
			{	X[k]=i/(double)(n-1);
				Y[k]=j/(double)(m-1);
				k++;
			}
	}
	public final int size () { return N; }
	public void extend (double xn, double yn)
	// add onother point to the vector
	{	double x[]=new double[N+1];
		double y[]=new double[N+1];
		int i;
		for (i=0; i<N; i++) { x[i]=X[i]; y[i]=Y[i]; }
		x[N]=xn; y[N]=yn;
		X=x; Y=y; N++;
	}
}

class PlaneGraph extends Graph
// A graph, which can be initialized with points.
{	final double sqr (double x) { return x*x; }
	public PlaneGraph (Points p)
	{	super(p.size());
		int i,j;
		for (i=0; i<N; i++)
			for (j=0; j<N; j++)
				connect(i,j,
				Math.sqrt(sqr(p.X[i]-p.X[j])+sqr(p.Y[i]-p.Y[j])));
	}		
}

class Path
// A path in a graph.
// From[i] is the index of the point leading to i.
// To[i] the index of the point after i.
// The path can optimize itself in a graph.
{   Graph G;
	int N;
	double L;
	public int From[],To[];
	public Path (Graph g)
	{	N=g.size();
		G=g;
		From=new int[N];
		To=new int[N];
	}
	public Object clone ()
	// return a clone path
	{	Path p=new Path(G);
		p.L=L;
		int i;
		for (i=0; i<N; i++)
		{	p.From[i]=From[i];
			p.To[i]=To[i];
		}
		return p;
	}
	public void random (Random r)
	// random path.
	{	int i,j,i0,j0,k;
		for (i=0; i<N; i++) To[i]=-1;
		for (i0=i=0; i<N-1; i++)
		{	j=(int)(r.nextLong()%(N-i));
			To[i0]=0;
			for (j0=k=0; k<j; k++)
			{	j0++;
				while (To[j0]!=-1) j0++;
			}
			while (To[j0]!=-1) j0++;
			To[i0]=j0; From[j0]=i0;
			i0=j0;
		}
		To[i0]=0; From[0]=i0;
		getlength();
	}
	public double length () { return L; }
	public boolean improve ()
	// try to find another path with shorter length
	// using removals of points j and inserting i,j,i+1
	{   int i,j,h;
		double d1,d2;
		double H[]=new double[N];
		for (i=0; i<N; i++)
			H[i]=-G.distance(From[i],i)-G.distance(i,To[i])
				+G.distance(From[i],To[i]);
		for (i=0; i<N; i++)
		{	d1=-G.distance(i,To[i]);
			j=To[To[i]];
			while (j!=i)
			{   d2=H[j]+G.distance(i,j)+G.distance(j,To[i])+d1;
				if (d2<-1e-10)
				{	h=From[j];
					To[h]=To[j]; From[To[j]]=h;
					h=To[i]; To[i]=j; To[j]=h;
					From[h]=j; From[j]=i;
					getlength();
					return true;
				}
	            j=To[j];
			}
		}
		return false;
	}
	public boolean improvecross ()
	// improve the path locally, using replacements
	// of i,i+1 and j,j+1 with i,j and i+1,j+1
	{   int i,j,h,h1,hj;
		double d1,d2,d;
		for (i=0; i<N; i++)
		{	d1=-G.distance(i,To[i]);
			j=To[To[i]];
			d2=0;
	        d=0;
			while (To[j]!=i)
			{   d+=G.distance(j,From[j])-G.distance(From[j],j);
				d2=d1+G.distance(i,j)+d+G.distance(To[i],To[j])
					-G.distance(j,To[j]);
				if (d2<-1e-10)
				{   h=To[i]; h1=To[j];
					To[i]=j;
					To[h]=h1; From[h1]=h;
					hj=i;
					while (j!=h)
					{   h1=From[j];
						To[j]=h1;
						From[j]=hj;
						hj=j;
						j=h1;
					}
	                From[j]=hj;
					getlength();
					return true;
				}
				j=To[j];
			}
		}
		return false;
	}
	void getlength ()
	// compute the length of the path
	{	L=0;
		int i;
		for (i=0; i<N; i++)
		{	L+=G.distance(i,To[i]);
		}
	}
	void localoptimize ()
	// find a local optimum starting from this path
	{	do
		{	while (improve());
		} while (improvecross());
	}
}

class Plot extends Panel
// Show a plane graph, a path and its length.
{	Points P=null;
	Path Pa=null;
	public PlaneGraph G;
	String Message=new String("");
	final int col (double x) { return (int)(x*200+10); }
	final int row (double y) { return (int)(y*200+10); }
	void drawpoints (Graphics g)
	// draw the points
	{	if (P==null) return;
		int i,c,r;
		g.setColor(Color.red);
		for (i=0; i<P.size(); i++)
		{	c=col(P.X[i]); r=row(P.Y[i]);
			g.drawRect(c-1,r-1,3,3);
		}
	}
	void frame (Graphics g)
	// delete background and draw a frame
	{	g.setColor(getBackground());
		g.fillRect(0,0,size().width,size().height);
		g.setColor(Color.green);
		g.drawRect(2,2,216,216);
	}
	void drawmessage (Graphics g)
	// draw a message to the right of the frame
	{	g.setColor(Color.black);
		if (Pa!=null)
		{	Double l=new Double(Pa.length());
			g.drawString(Message+", Length="+l.toString(),240,20);
		}
		else g.drawString(Message,240,20);
	}
	void drawpath (Graphics g)
	// connect the points of the graph, following the path.
	{	if (Pa==null || P==null) return;
		g.setColor(Color.black);
		int i=0,j=Pa.To[i];
		while (j!=0)
		{   g.drawLine(col(P.X[i]),row(P.Y[i]),
				col(P.X[j]),row(P.Y[j]));
			j=Pa.To[j]; i=Pa.From[j];
		}
		g.drawLine(col(P.X[i]),row(P.Y[i]),
			col(P.X[j]),row(P.Y[j]));
	}
	public void paint (Graphics g)
	// paint everything
	{	frame(g);
		drawpath(g);
		drawpoints(g);
		drawmessage(g);
	}
	public void set (Points p)
	{	P=p;
		G=new PlaneGraph(p);
		Integer I=new Integer(p.size());
		Message=new String(I.toString()+" Points");
	}
	public void set (Path pa)
	{	Pa=pa;
	}
	public void clear ()
	// remove the points and the graph.
	{	P=null;
		Pa=null;
		G=null;
	}
	public boolean mouseUp (Event e, int x, int y)
	// lets the user add a new point.
	{	x-=10; y-=10;
		if (x<0 || x>=200 || y<0 || y>=200) return false;
		Pa=null;
		if (P==null)
		{	P=new Points(1);
			P.X[0]=x/200.0; P.Y[0]=y/200.0;
		}
		else P.extend(x/200.0,y/200.0);
		set(P);
		repaint();
		return true;
	}
}

public class tsp extends Applet
// Contains a plot of the graph and the path,
// and several buttons.
{	Plot Plotarea;
	Button Clear;
	Button Randomize;
	Button Square;
	Button LocalOptimize;
	Button Optimize;
	GridBagLayout gridbag=new GridBagLayout();
	int nrandom=10,niter=10,nw,nh;
	Random R=new Random();
	TextField NSquare,NRSquare,NIterations;
	public void init ()
	// layout the applet
	{	Plotarea=new Plot();
		Panel panel1=new Panel();
		Panel panel2=new Panel();
		Clear=new Button("   Clear   ");
		Randomize=new Button("  Random  ");
		NRSquare=new TextField("",4);
		Square=new Button("Rectangle Grid");
		NSquare=new TextField("",4);
		LocalOptimize=new Button("Local Optimum");
		Optimize=new Button(" Optimum ");
		NIterations=new TextField("",4);
		panel1.add(Clear);
		panel1.add(Randomize);
		panel1.add(NRSquare);
		panel1.add(Square);
		panel1.add(NSquare);
		panel2.add(LocalOptimize);
		panel2.add(Optimize);
		panel2.add(new Label(" Iterations:"));
		panel2.add(NIterations);
		setLayout(gridbag);
		constrain(Plotarea,0,0,1,1,
			GridBagConstraints.BOTH,GridBagConstraints.CENTER,
			1.0,1.0);
		add(Plotarea);
		constrain(panel1,0,2,1,1,
			GridBagConstraints.NONE,GridBagConstraints.CENTER,
			1.0,0.0);
		add(panel1);
		constrain(panel2,0,3,1,1,
			GridBagConstraints.NONE,GridBagConstraints.CENTER,
			1.0,0.0);
		add(panel2);
	}
	int param (String s, int d)
	// read a parameter named s with default value d
	{	String h=getParameter(s);
		int i;
		try { i=Integer.parseInt(h); }
			catch (NumberFormatException e) { return d; }
		return i;
	}
	void constrain (Component c, int gx, int gy, int gw, int gh,
		int fill, int anchor, double wx, double wy)
	// helper function to handle constrains
	{	GridBagConstraints g=new GridBagConstraints();
		g.gridx=gx; g.gridy=gy; g.gridwidth=gw; g.gridheight=gh;
		g.fill=fill; g.anchor=anchor;
		g.weightx=wx; g.weighty=wy;
		gridbag.setConstraints(c,g);
	}
	public boolean action (Event e, Object a)
	// react to buttons
	{	if (e.target==Randomize)
		// create random points
		{	try
			{	nrandom=Integer.parseInt(NRSquare.getText());
			}
			catch (Exception ex)
			{	nrandom=param("points",20);
			}
			Points p=new Points(nrandom);
			showStatus(p.size()+" Points generated");
			p.random(R);
			Plotarea.clear();
			Plotarea.set(p);
			Plotarea.repaint();
			return true;
		}
		if (e.target==Square)
		// create grid points
		{	try
			{	nw=nh=Integer.parseInt(NSquare.getText());
			}
			catch (Exception ex)
			{	nw=param("columns",4);
				nh=param("rows",4);
			}
			Points p=new Points(nw*nh);
			showStatus(p.size()+" Points generated");
			p.square(nw,nh);
			Plotarea.clear();
			Plotarea.set(p);
			Plotarea.repaint();
			return true;
		}
		if (e.target==Clear)
		// clear all points
		{	Plotarea.clear();
			Plotarea.repaint();
			return true;
		}
		if (e.target==LocalOptimize)
		// find a local optimum
		{	if (Plotarea.G==null) return true;
			showStatus("Seeking local Optimum");
			Path pa=new Path(Plotarea.G);
			pa.random(R);
			pa.localoptimize();
			Plotarea.set(pa);
			Plotarea.repaint();
			showStatus("Local optimum found");
			return true;
		}
		if (e.target==Optimize)
		// try to find a global optimum
		{	if (Plotarea.G==null) return true;
			showStatus("Seeking Optimum");
			Path pa=new Path(Plotarea.G),pmin=null;
			double lmin=1e50;
			int count=0;
			getiterations();
			do
			{	pa.random(R);
				pa.localoptimize();
				if (pa.length()<lmin-1e-10)
				{	pmin=(Path)pa.clone();
					lmin=pa.length();
					Plotarea.set(pmin);
					Plotarea.paint(Plotarea.getGraphics());
					count=0;
				}
				else count++;
			} while (count<niter);
			showStatus("Optimum after "+niter+" Iterations");
			return true;
		}
		return false;
	}
	public void getiterations ()
	{	try
		{	niter=Integer.parseInt(NIterations.getText());
		}
		catch (Exception ex)
		{	niter=param("iterations",20);
		}
	}

	public String getAppletInfo()
	// return the information for this applet
	{	return "Travelling Salesman by R. Grothmann";
	}
	public String[][] getParameterInfo ()
	// return information on its parameters
	{	String[][] info=
		{
		{"points","int","Number of random points"},
		{"iterations","int","Number tries to find the best tour"},
		{"columns","int","Number ofcolumns for grid"},
		{"rows","int","Number of rows for grid"}
		};
		return info;
	}
}

