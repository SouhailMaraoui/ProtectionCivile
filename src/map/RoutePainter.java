
package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;


import org.jxmapviewer.painter.Painter;

/********************************************************************************************************
 * La classe RoutePainter permet de dessiner l'itinéraire entre une caserne et
 * l'emplacement de l'incident.
 * 
 * @author A.S.E.D.S
 * @version 7.02
 ********************************************************************************************************/
public class RoutePainter implements Painter<JXMapViewer>
{
	private Color color = Color.RED;
	private boolean antiAlias = true;

	private List<GeoPosition> track;

	/**
	 * Constructeur paramétré.
	 * 
	 * @param track la piste à dessiner (une liste des coordonnées).
	 * 
	 */
	public RoutePainter(List<GeoPosition> track)
	{
		// copy the list so that changes in the
		// original list do not have an effect here
		this.track = new ArrayList<GeoPosition>(track);
	}

	/**
	 * Permets de dessiner sur la carte.
	 *
	 * @param g   le graphique en deux dimensions à interpréter.
	 * @param map la carte à interpréter.
	 * @param w   largeur de la zone à dessiner.
	 * @param h   hauteur de la zone à dessiner.
	 */
	@Override
	public void paint(Graphics2D g, JXMapViewer map, int w, int h)
	{
		g = (Graphics2D) g.create();

		// convert from viewport to world bitmap
		Rectangle rect = map.getViewportBounds();
		g.translate(-rect.x, -rect.y);

		if (antiAlias)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// do the drawing
		/*g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(4));*/

		drawRoute(g, map);

		// do the drawing again
		g.setColor(color);
		g.setStroke(new BasicStroke(10));

		drawRoute(g, map);

		g.dispose();
	}

	/**
	 * Permets de dessiner l'itinéraire sur la carte.
	 * 
	 * @param g   le graphique en deux dimensions à interpréter.
	 * @param map la carte à interpréter.
	 */
	private void drawRoute(Graphics2D g, JXMapViewer map)
	{
		int lastX = 0;
		int lastY = 0;

		boolean first = true;
		double sum=0;
		int sumsum=0;
		int i=1;
		
		for (GeoPosition gp : track)
		{
			
			// convert geo-coordinate to world bitmap pixel
			Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
			double lenght=Math.abs(Math.cos(gp.getLongitude()*Math.PI/180) / Math.pow(2, map.getZoom() + 8) * 100000000);
			//double lenght=75;
			if (first)
			{
				first = false;
				lastX = (int) pt.getX();
				lastY = (int) pt.getY();
			} 
			else
			{
				double diffX=pt.getX()-lastX;
				double diffY=pt.getY()-lastY;
				double dis=Math.pow(Math.pow(diffX, 2)+Math.pow(diffY, 2), 0.5f);
				sumsum+=dis;
				sum+=dis;

				while(sum>lenght)
				{
					double distanceToDraw =dis+lenght-sum;
					sum=sum-lenght;

					if(distanceToDraw>lenght)
					{
						sum=distanceToDraw-lenght;
						distanceToDraw=lenght;
						
					}
					
					int endPointX=(int)(lastX+(distanceToDraw*diffX)/dis);
					int endPointY=(int)(lastY+(distanceToDraw*diffY)/dis);
					
					g.drawLine(lastX, lastY,endPointX,endPointY);
					lastX=endPointX;
					lastY=endPointY;
					if(i==0) 
					{
						g.setColor(new Color(1f,0,0,1f));
						i=1;
					}
					else if(i==1) 
					{
						g.setColor(new Color(0,0,0,1f));
						i=0;
					}
				}
				
				if(sum<=lenght)
				{
					g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
					lastX = (int) pt.getX();
					lastY = (int) pt.getY();
				}
				
			}
		}
	}
}
