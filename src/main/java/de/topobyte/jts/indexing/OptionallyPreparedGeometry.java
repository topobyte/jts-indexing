package de.topobyte.jts.indexing;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;

public class OptionallyPreparedGeometry
{

	private Geometry geometry;
	private PreparedGeometry prepared = null;

	public OptionallyPreparedGeometry(PreparedGeometry prepared)
	{
		this.prepared = prepared;
		this.geometry = prepared.getGeometry();
	}

	public OptionallyPreparedGeometry(Geometry geometry)
	{
		this.geometry = geometry;
	}

	public Geometry getGeometry()
	{
		return geometry;
	}

	public PreparedGeometry getPrepared()
	{
		return prepared;
	}

	public boolean isPrepared()
	{
		return prepared != null;
	}

	public void prepare()
	{
		if (prepared != null) {
			return;
		}
		prepared = PreparedGeometryFactory.prepare(geometry);
	}

	public boolean covers(Geometry other)
	{
		if (isPrepared()) {
			return prepared.contains(other);
		}
		return geometry.contains(other);
	}

	public boolean intersects(Geometry other)
	{
		if (isPrepared()) {
			return prepared.intersects(other);
		}
		return geometry.intersects(other);
	}

	public Geometry intersection(Geometry other)
	{
		return geometry.intersection(other);
	}

}
