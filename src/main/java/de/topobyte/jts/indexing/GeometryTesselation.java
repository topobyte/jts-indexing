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

import org.locationtech.jts.geom.Geometry;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public interface GeometryTesselation<T>
{

	/**
	 * Add the specified geometry to the tesselation.
	 * 
	 * @param geom
	 *            the geometry to add.
	 */
	public abstract void add(T geom);

	/**
	 * Retrieve the set of Geometries that this geometry is covered by.
	 * 
	 * @param geometry
	 *            the geometry to test for.
	 * @return the set of geometries this geometry is covered by.
	 */
	public abstract Set<T> test(Geometry geometry);

	/**
	 * Return all geometries of this tesselation that intersect the given
	 * testing geometry.
	 * 
	 * @param geometry
	 *            the geometry to test for intersections with this tesselation.
	 * @return all intersecting geometries
	 */
	public abstract Set<T> testForIntersection(Geometry geometry);

}