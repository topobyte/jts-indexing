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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.prep.PreparedGeometry;

/**
 * A more useful extension of {@link GeometryTesselation} that allows a value to
 * be mapped to each added geometry.
 * 
 * @param <T>
 *            the type of values to be associated to geometries.
 * 
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class GeometryTesselationMap<T>
{

	public static class Entry<U>
	{

		private OptionallyPreparedGeometry geometry;
		private U thing;

		public Entry(OptionallyPreparedGeometry geometry, U thing)
		{
			this.geometry = geometry;
			this.thing = thing;
		}

		public OptionallyPreparedGeometry getGeometry()
		{
			return geometry;
		}

		public U getThing()
		{
			return thing;
		}

	}

	private boolean usePreparedGeometries;
	private OptionallyPreparedGeometryTesselation gt;
	private Set<T> things = new HashSet<>();
	private Map<Geometry, Entry<T>> geometryToThing = new HashMap<>();

	public GeometryTesselationMap()
	{
		this(false);
	}

	public GeometryTesselationMap(boolean usePreparedGeometries)
	{
		this.usePreparedGeometries = usePreparedGeometries;
		gt = new OptionallyPreparedGeometryTesselation();
	}

	/**
	 * Add <code>geom</code> to the tesselation and map <code>thing</code> to
	 * this geometry. Thus a test for a geometry covered by <code>geom</code>
	 * will return a set containing <code>thing</code>.
	 * 
	 * @param geom
	 *            the geometry to register <code>thing</code> for.
	 * @param thing
	 *            the value associated to <code>geom</code>.
	 */
	public void add(Geometry geom, T thing)
	{
		OptionallyPreparedGeometry optionallyPrepared = new OptionallyPreparedGeometry(
				geom);
		if (usePreparedGeometries) {
			optionallyPrepared.prepare();
		}
		gt.add(optionallyPrepared);

		things.add(thing);

		Entry<T> entry = new Entry<>(optionallyPrepared, thing);
		geometryToThing.put(geom, entry);
	}

	/**
	 * Add <code>geom</code> to the tesselation and map <code>thing</code> to
	 * this geometry. Thus a test for a geometry covered by <code>geom</code>
	 * will return a set containing <code>thing</code>.
	 * 
	 * @param prepared
	 *            the geometry to register <code>thing</code> for.
	 * @param thing
	 *            the value associated to <code>geom</code>.
	 */
	public void add(PreparedGeometry prepared, T thing)
	{
		OptionallyPreparedGeometry optionallyPrepared = new OptionallyPreparedGeometry(
				prepared);
		gt.add(optionallyPrepared);

		things.add(thing);

		Entry<T> entry = new Entry<>(optionallyPrepared, thing);
		geometryToThing.put(prepared.getGeometry(), entry);
	}

	/**
	 * Return the set of values, whose associated geometries cover the given
	 * geometry.
	 * 
	 * @param geometry
	 *            the geometry to test for.
	 * @return all values that cover the geometry.
	 */
	public Set<T> covering(Geometry geometry)
	{
		Set<OptionallyPreparedGeometry> test = gt.covering(geometry);
		Set<T> results = new HashSet<>();
		for (OptionallyPreparedGeometry g : test) {
			results.add(geometryToThing.get(g.getGeometry()).getThing());
		}
		return results;
	}

	/**
	 * Return the set of value entries, whose associated geometries intersect
	 * the given geometry.
	 * 
	 * @param geometry
	 *            the geometry to test for.
	 * @return all values that intersect the geometry.
	 */
	public Set<T> intersecting(Geometry geometry)
	{
		Set<OptionallyPreparedGeometry> test = gt.intersecting(geometry);
		Set<T> results = new HashSet<>();
		for (OptionallyPreparedGeometry g : test) {
			results.add(geometryToThing.get(g.getGeometry()).getThing());
		}
		return results;
	}

	/**
	 * Return the set of value entries, whose associated geometries cover the
	 * given geometry.
	 * 
	 * @param geometry
	 *            the geometry to test for.
	 * @return all values that cover the geometry.
	 */
	public Set<Entry<T>> coveringEntries(Geometry geometry)
	{
		Set<OptionallyPreparedGeometry> test = gt.covering(geometry);
		Set<Entry<T>> results = new HashSet<>();
		for (OptionallyPreparedGeometry g : test) {
			results.add(geometryToThing.get(g.getGeometry()));
		}
		return results;
	}

	/**
	 * Return the set of values, whose associated geometries intersect the given
	 * geometry.
	 * 
	 * @param geometry
	 *            the geometry to test for.
	 * @return all values that intersect the geometry.
	 */
	public Set<Entry<T>> intersectingEntries(Geometry geometry)
	{
		Set<OptionallyPreparedGeometry> test = gt.intersecting(geometry);
		Set<Entry<T>> results = new HashSet<>();
		for (OptionallyPreparedGeometry g : test) {
			results.add(geometryToThing.get(g.getGeometry()));
		}
		return results;
	}

	/**
	 * @return a collection of all elements that have been added to this
	 *         tesselation.
	 */
	public Collection<T> values()
	{
		return Collections.unmodifiableCollection(things);
	}

}
