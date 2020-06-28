// Copyright 2020 Sebastian Kuerten
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

import java.util.HashSet;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.prep.PreparedGeometry;

import com.infomatiq.jsi.Rectangle;

import de.topobyte.jsi.GenericRTree;
import de.topobyte.jsi.GenericSpatialIndex;
import de.topobyte.jsijts.JsiAndJts;

/**
 * A collection of Geometries. They will be inserted into a spatial index so
 * that some tests may be performed efficiently.
 * 
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class PreparedGeometryTesselationImpl
		implements PreparedGeometryTesselation
{

	private GenericSpatialIndex<PreparedGeometry> gsi;

	/**
	 * Create an empty tesselation.
	 */
	public PreparedGeometryTesselationImpl()
	{
		gsi = new GenericRTree<>(1, 10);
	}

	@Override
	public void add(PreparedGeometry pg)
	{
		Rectangle rect = JsiAndJts.toRectangle(pg.getGeometry());
		gsi.add(rect, pg);
	}

	@Override
	public Set<PreparedGeometry> test(Point point)
	{
		Rectangle r = JsiAndJts.toRectangle(point);
		Set<PreparedGeometry> intersections = gsi.intersects(r);
		Set<PreparedGeometry> containing = new HashSet<>();
		for (PreparedGeometry g : intersections) {
			if (g.covers(point)) {
				containing.add(g);
			}
		}
		return containing;
	}

	@Override
	public Set<PreparedGeometry> testForIntersection(Geometry geometry)
	{
		Rectangle r = JsiAndJts.toRectangle(geometry);
		Set<PreparedGeometry> intersections = gsi.intersects(r);
		Set<PreparedGeometry> hits = new HashSet<>();
		for (PreparedGeometry g : intersections) {
			if (g.intersects(geometry)) {
				hits.add(g);
			}
		}
		return hits;
	}

}
