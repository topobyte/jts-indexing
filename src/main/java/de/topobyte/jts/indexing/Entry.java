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

public class Entry<S, T>
{

	private S geometry;
	private T thing;

	public Entry(S geometry, T thing)
	{
		this.geometry = geometry;
		this.thing = thing;
	}

	public S getGeometry()
	{
		return geometry;
	}

	public T getThing()
	{
		return thing;
	}

}
