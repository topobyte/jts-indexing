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

import java.util.HashSet;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;

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
public class PreparedGeometryTesselation
		implements GeometryTesselation<Geometry>
{

	private GenericSpatialIndex<PreparedGeometry> gsi;

	/**
	 * Create an empty tesselation.
	 */
	public PreparedGeometryTesselation()
	{
		gsi = new GenericRTree<>(1, 10);
	}

	@Override
	public void add(Geometry geom)
	{
		Rectangle rect = JsiAndJts.toRectangle(geom);
		PreparedGeometry pg = PreparedGeometryFactory.prepare(geom);
		gsi.add(rect, pg);
	}

	@Override
	public Set<Geometry> covering(Geometry geometry)
	{
		Rectangle r = JsiAndJts.toRectangle(geometry);
		Set<PreparedGeometry> candidates = gsi.intersects(r);
		Set<Geometry> covering = new HashSet<>();
		for (PreparedGeometry g : candidates) {
			if (g.covers(geometry)) {
				covering.add(g.getGeometry());
			}
		}
		return covering;
	}

	@Override
	public Set<Geometry> intersecting(Geometry geometry)
	{
		Rectangle r = JsiAndJts.toRectangle(geometry);
		Set<PreparedGeometry> candidates = gsi.intersects(r);
		Set<Geometry> intersecting = new HashSet<>();
		for (PreparedGeometry g : candidates) {
			if (g.intersects(geometry)) {
				intersecting.add(g.getGeometry());
			}
		}
		return intersecting;
	}

}
