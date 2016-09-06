// Copyright 2016 Sebastian Kuerten
//
// This file is part of jts-indexing.
//
// jts-indexing is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// jts-indexing is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with jts-indexing. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.jts.indexing;

import java.util.Set;

import com.infomatiq.jsi.Rectangle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import de.topobyte.jsi.GenericRTree;
import de.topobyte.jsi.GenericSpatialIndex;
import de.topobyte.jsijts.JsiAndJts;

/**
 * A collection of Geometries. They will be inserted into a spatial index so
 * that some tests may be performed efficiently.
 * 
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class NearestNeighbourTesselation<T>
{

	private GenericSpatialIndex<Entry<T>> gsi;

	/**
	 * Create an empty tesselation.
	 */
	public NearestNeighbourTesselation()
	{
		gsi = new GenericRTree<>(1, 10);
	}

	public void add(Geometry geom, T data)
	{
		Rectangle rect = JsiAndJts.toRectangle(geom);
		gsi.add(rect, new Entry<>(geom, data));
	}

	public Set<Entry<T>> within(Coordinate c, double sx, double sy)
	{
		double x = c.x;
		double y = c.y;
		Rectangle query = new Rectangle((float) (x - sx / 2),
				(float) (y + sy / 2), (float) (x + sx / 2),
				(float) (y - sy / 2));
		Set<Entry<T>> intersections = gsi.intersects(query);
		return intersections;
	}

	public static class Entry<S>
	{
		private Geometry geometry;
		private S data;

		public Entry(Geometry geometry, S data)
		{
			this.geometry = geometry;
			this.data = data;
		}

		public S getData()
		{
			return data;
		}

		public Geometry getGeometry()
		{
			return geometry;
		}
	}

}
