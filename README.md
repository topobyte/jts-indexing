# About

This Java project provides various data structures for building
geometric indexes for [JTS](https://github.com/locationtech/jts) geometries.

# License

This library is released under the terms of the GNU Lesser General Public
License.

See  [LGPL.md](LGPL.md) and [GPL.md](GPL.md) for details.

# Download

We provide access to the artifacts via our own Maven repository:

<https://mvn.topobyte.de>

The packages are available at these coordinates:

    de.topobyte:jts-indexing:0.3.0

You can also browse the repository online:

* <https://mvn.topobyte.de/de/topobyte/jts-indexing/>

# Changelog

## 0.3.0

### GeometryTesselationMap

Methods have been renamed:
* test() → covering()
* testForIntersection() → intersecting()

New methods:
* coveringEntries()
* intersectingEntries()

### Other

* Introduced OptionallyPreparedGeometry and OptionallyPreparedGeometryTesselation
