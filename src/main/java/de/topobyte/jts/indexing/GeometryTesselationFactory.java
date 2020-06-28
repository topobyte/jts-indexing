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

import org.locationtech.jts.geom.Geometry;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class GeometryTesselationFactory
{

	public static GeometryTesselation<Geometry> createTesselation(
			boolean usePreparedGeometries)
	{
		if (usePreparedGeometries) {
			return new PreparedGeometryTesselation();
		} else {
			return new SimpleGeometryTesselation();
		}
	}

}
