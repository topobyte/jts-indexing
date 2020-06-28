# About

This Java project provides various data structures for building
geometric indexes for [JTS](https://github.com/dr-jts/jts) geometries.

# License

This library is released under the terms of the GNU Lesser General Public
License.

See  [LGPL.md](LGPL.md) and [GPL.md](GPL.md) for details.

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
