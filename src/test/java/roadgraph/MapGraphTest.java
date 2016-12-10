package roadgraph;

import geography.GeographicPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MapGraphTest {

    private MapGraph mapGraph;

    @BeforeEach
    void setup() {
        mapGraph = new MapGraph();
    }

    @Test
    void testAddVertex() {
        GeographicPoint pointToAdd = new GeographicPoint(5, 10);

        boolean pointAdded = mapGraph.addVertex(pointToAdd);

        assertTrue(pointAdded);
        assertEquals(1, mapGraph.getNumVertices());
    }

    @Test
    void testAddVertexNullCase() {
        boolean pointAdded = mapGraph.addVertex(null);

        assertFalse(pointAdded);
        assertEquals(0, mapGraph.getNumVertices());
    }

    @Test
    void testAddVertexAlreadyAddedCase() {
        GeographicPoint pointToAdd = new GeographicPoint(5, 10);
        mapGraph.addVertex(pointToAdd);

        boolean pointAdded = mapGraph.addVertex(pointToAdd);

        assertFalse(pointAdded);
        assertEquals(1, mapGraph.getNumVertices());
    }

    @Test
    void testGetVertices() {
        GeographicPoint pointToAdd = new GeographicPoint(5, 10);
        mapGraph.addVertex(pointToAdd);

        GeographicPoint anotherPointToAdd = new GeographicPoint(20, 40);
        mapGraph.addVertex(anotherPointToAdd);

        Set<GeographicPoint> vertices = mapGraph.getVertices();

        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(pointToAdd));
        assertTrue(vertices.contains(anotherPointToAdd));
    }

    @Test
    void testAddEdgeMissingPoints() {
        GeographicPoint firstPoint = new GeographicPoint(5, 10);
        GeographicPoint secondPoint = new GeographicPoint(20, 40);

        assertThrows(IllegalArgumentException.class, () -> {
            mapGraph.addEdge(firstPoint, secondPoint, "High Street", "residential", 10);
        });
    }

    @Test
    void testAddEdgeNullPoints() {
        assertThrows(IllegalArgumentException.class, () -> mapGraph.addEdge(null, null, "High Street", "residential", 10));
    }

    @Test
    void testAddEdgeNullRoadName() {
        GeographicPoint firstPoint = new GeographicPoint(5, 10);
        mapGraph.addVertex(firstPoint);

        GeographicPoint secondPoint = new GeographicPoint(20, 40);
        mapGraph.addVertex(secondPoint);

        assertThrows(IllegalArgumentException.class, () -> mapGraph.addEdge(firstPoint, secondPoint, null, "residential", 10));
    }

    @Test
    void testAddEdgeNullRoadType() {
        GeographicPoint firstPoint = new GeographicPoint(5, 10);
        mapGraph.addVertex(firstPoint);

        GeographicPoint secondPoint = new GeographicPoint(20, 40);
        mapGraph.addVertex(secondPoint);

        assertThrows(IllegalArgumentException.class, () -> mapGraph.addEdge(firstPoint, secondPoint, "High Street", null, 10));
    }

    @Test
    void testAddEdgeNegativeLength() {
        GeographicPoint firstPoint = new GeographicPoint(5, 10);
        mapGraph.addVertex(firstPoint);

        GeographicPoint secondPoint = new GeographicPoint(20, 40);
        mapGraph.addVertex(secondPoint);

        assertThrows(IllegalArgumentException.class, () -> mapGraph.addEdge(firstPoint, secondPoint, "High Street", "residential", -5));
    }

    @Test
    void testAddEdge() {
        GeographicPoint firstPoint = new GeographicPoint(5, 10);
        mapGraph.addVertex(firstPoint);

        GeographicPoint secondPoint = new GeographicPoint(20, 40);
        mapGraph.addVertex(secondPoint);

        mapGraph.addEdge(firstPoint, secondPoint, "High Street", "residential", 10);

        assertEquals(1, mapGraph.getNumEdges());
    }
}