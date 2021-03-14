package amazetowavefrontobj;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Maze
{
    private List<Vertex> vertices = new ArrayList<>();
    private List<Polyline> polylines = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();
    
    public Maze()
    {
        
    }
    
    public int addVertex(double x, double y, double z)
    {
        Vertex newVertex = new Vertex(x, y, z);  
        
        int pos;
        
        pos = vertices.indexOf(newVertex);
        if (pos >= 0) {
             // Remember that Wavefront's list of vertices is 1-based, not 0-based.
            return pos + 1;
        }

        vertices.add(newVertex);
        pos = vertices.size();
        return pos;
    }
    
    public int addFace( int v0, int v1, int v2)
    {
        Face face = new Face(v0, v1, v2);
        faces.add(face);
        return faces.size();
    }
    
    public int addPolyline(int... vertexIDs)
    {
        Polyline polyline = new Polyline(vertexIDs);
        polylines.add(polyline);
        return polylines.size();
    }
    
    /**
     * Add a panel that extends in the direction of the x axis.
     * @param x0
     * @param y0
     * @param x1
     * @param y1 Unused, added for completeness.
     */
    public void addPanel1(double x0, double y0, double x1, double y1)
    {
        int v1 = addVertex(x0, y0, 0);
        int v2 = addVertex(x0, y0, 1);
        int v3 = addVertex(x1, y0, 0);
        int v4 = addVertex(x1, y0, 1);
        
        addFace(v1, v3, v2);
        addFace(v3, v4, v2);
    }
    
    /**
     * Add a panel that extends in the direction of the y axis.
     * @param x0
     * @param y0
     * @param x1 Unused, added for completeness.
     * @param y1 
     */
    public void addPanel2(double x0, double y0, double x1, double y1)
    {
        int v1 = addVertex(x0, y0, 0);
        int v2 = addVertex(x0, y0, 1);  
        int v3 = addVertex(x0, y1, 0);
        int v4 = addVertex(x0, y1, 1);
        addFace(v1, v3, v2);
        addFace(v3, v4, v2);
    }
    
    public void printAsWavefront() {
        vertices.forEach(vertex -> {
            System.out.println(vertex.exportToWavefront());
        });
        faces.forEach(face -> {
            System.out.println(face.exportToWavefront());
        });
        polylines.forEach(polyline -> {
            System.out.println(polyline.exportToWavefront());
        });
    }

    public void writeToWavefrontFile(String filename) throws IOException {
        try (Writer fw = new PrintWriter(new File(filename))) {
            // Cannot use functional style, because the contents can throw an I/O Exception.
            // (There are ways around that... but they make the code harder to understand).
            String separator = System.getProperty("line.separator");
            for (Vertex vertex : vertices) {
                fw.write(vertex.exportToWavefront() + separator);
            }
            for (Face face : faces) {
                fw.write(face.exportToWavefront() + separator);
            }
            for (Polyline polyline : polylines) {
                fw.write(polyline.exportToWavefront() + separator);
            }
        }
    }
}
