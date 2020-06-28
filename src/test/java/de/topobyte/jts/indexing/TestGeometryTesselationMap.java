package de.topobyte.jts.indexing;

import java.util.Set;

import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import de.topobyte.jts.indexing.GeometryTesselationMap.Entry;

public class TestGeometryTesselationMap
{

	@Test
	public void test()
	{
		GeometryFactory factory = new GeometryFactory();

		Point point = factory.createPoint(new Coordinate(0, 0));

		GeometryTesselationMap<String> map = new GeometryTesselationMap<>();
		map.add(point, "foo");

		Set<Entry<String>> test = map.coveringEntries(point);
		for (Entry<String> entry : test) {
			String thing = entry.getThing();
			OptionallyPreparedGeometry optionallyPrepared = entry.getGeometry();
			System.out.println(thing);
			System.out.println(optionallyPrepared);
		}
	}

}
