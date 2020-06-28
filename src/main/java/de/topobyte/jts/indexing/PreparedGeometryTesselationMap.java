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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;

/**
 * A version of GeometryTesselationMap that also exposes the prepared geometries
 * for further computation.
 * 
 * @param <T>
 *            the type of values to be associated to geometries.
 * 
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class PreparedGeometryTesselationMap<T>
{

	PreparedGeometryTesselationImpl gt;
	Map<Geometry, T> geometryToThing = new HashMap<>();

	public PreparedGeometryTesselationMap()
	{
		gt = new PreparedGeometryTesselationImpl();
	}

	/**
	 * Add <code>geom</code> to the tesselation and map <code>thing</code> to
	 * this geometry. Thus a test for a point covered by <code>geom</code> will
	 * return a set containing <code>thing</code>.
	 * 
	 * @param geom
	 *            the geometry to register <code>thing</code> for.
	 * @param thing
	 *            the value associated to <code>geom</code>.
	 */
	public void add(Geometry geom, T thing)
	{
		PreparedGeometry pg = PreparedGeometryFactory.prepare(geom);
		gt.add(pg);
		geometryToThing.put(geom, thing);
	}

	/**
	 * Add <code>geom</code> to the tesselation and map <code>thing</code> to
	 * this geometry. Thus a test for a point covered by <code>geom</code> will
	 * return a set containing <code>thing</code>.
	 * 
	 * @param geom
	 *            the geometry to register <code>thing</code> for.
	 * @param thing
	 *            the value associated to <code>geom</code>.
	 */
	public void add(PreparedGeometry geom, T thing)
	{
		gt.add(geom);
		geometryToThing.put(geom.getGeometry(), thing);
	}

	/**
	 * Return the set of values, whose associated geometries cover the given
	 * point.
	 * 
	 * @param point
	 *            the point to test for.
	 * @return all values that cover the point.
	 */
	public Set<Entry<PreparedGeometry, T>> test(Point point)
	{
		Set<PreparedGeometry> test = gt.test(point);
		Set<Entry<PreparedGeometry, T>> testSet = new HashSet<>();
		for (PreparedGeometry g : test) {
			testSet.add(new Entry<PreparedGeometry, T>(g,
					geometryToThing.get(g.getGeometry())));
		}
		return testSet;
	}

	/**
	 * Return the set of values, whose associated geometries intersect the given
	 * geometry.
	 * 
	 * @param geometry
	 *            the geometry to test for.
	 * @return all values that intersect the geometry.
	 */
	public Set<Entry<PreparedGeometry, T>> testForIntersections(
			Geometry geometry)
	{
		Set<PreparedGeometry> test = gt.testForIntersection(geometry);
		Set<Entry<PreparedGeometry, T>> testSet = new HashSet<>();
		for (PreparedGeometry g : test) {
			testSet.add(new Entry<PreparedGeometry, T>(g,
					geometryToThing.get(g.getGeometry())));
		}
		return testSet;
	}

	/**
	 * @return a collection of all elements that have been added to this
	 *         tesselation.
	 */
	public Collection<T> values()
	{
		return geometryToThing.values();
	}

}
