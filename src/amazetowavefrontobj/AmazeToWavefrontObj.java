/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amazetowavefrontobj;

import java.io.*;
import java.nio.file.*;
import java.util.*;
        
/**
 *
 * @author FractionalRadix
 */
public class AmazeToWavefrontObj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<char[]> rows = readFile(args[0]);
        
        print(rows);
        
        Maze maze = buildMaze(rows);
        
        maze.printAsWavefront();
    }
    
    static List<char[]> readFile(String filename)
    {
        List<char[]> rows = new ArrayList<>();
        try {
            Path path = Paths.get(filename);
            List<String> contents = Files.readAllLines(path);

            
            int nrOfRows = contents.size();
            for (int rowNum = 0; rowNum < nrOfRows; rowNum++) {
                String row = contents.get(rowNum);
                rows.add(row.toCharArray());
            }
            
            return rows;
                    
        } catch (IOException exc) {
            System.err.println("Encountered a problem trying to read the file.");
            exc.printStackTrace(System.err);
            return null;
        }
    }
    
    static void print(List<char[]> rows)
    {        
        for (char[] curRow : rows) {
            System.out.println();
            for (char curChar : curRow) {
                System.out.print(curChar);
            }
        }
    }
    
    static Maze buildMaze(List<char[]> rows) 
    {
        Maze maze = new Maze();
        
        // Increasing columns = increasing y axis
        // Increasing rows = increasing x axis
        for (int rowNr = 0; rowNr < rows.size(); rowNr++)
        {
            char[] cols = rows.get(rowNr);
            for (int colNr = 0; colNr < cols.length; colNr++) {

                switch(cols[colNr])
                {
                    case '+':
                       //TODO!+
                        break;
                    case '-':
                        {
                            int bottomLeftIdx  = maze.addVertex( rowNr, colNr , 0 );
                            int bottomRightIdx = maze.addVertex( rowNr, colNr + 1.0, 0 );
                            int topLeftIdx     = maze.addVertex( rowNr, colNr , 1 );
                            int topRightIdx    = maze.addVertex( rowNr, colNr + 1.0, 1 );

                            // Counter-clockwise
                            maze.addFace(bottomLeftIdx, bottomRightIdx, topLeftIdx);
                            maze.addFace(bottomRightIdx, topLeftIdx, topRightIdx);

                            //maze.addPolyline(bottomLeftIdx, bottomRightIdx, topRightIdx, topLeftIdx);
                        }
                    break;
                    case '|':
                        {
                            int bottomLeftIdx = maze.addVertex( rowNr, colNr, 0);
                            int bottomRightIdx = maze.addVertex( rowNr + 1, colNr, 0);
                            int topLeftIdx = maze.addVertex( rowNr, colNr, 1);
                            int topRightIdx = maze.addVertex( rowNr + 1, colNr, 1);
                            
                            maze.addFace(bottomLeftIdx, bottomRightIdx, topLeftIdx);
                            maze.addFace(bottomRightIdx, topLeftIdx, topRightIdx);
                        }
                        break;
                    case ' ':
                        // Just empty space, do nothing.
                        break;
                }
                
            }
        }
              
        return maze;
    }
}

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
        Vertex vertex = new Vertex(x, y, z);
        vertices.add(vertex);
        return vertices.size();
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
    
    public void printAsWavefront() {
        vertices.forEach(v -> {
            System.out.println(v.exportToWavefront());
        });
        faces.forEach(f -> {
            System.out.println(f.exportToWavefront());
        });
        polylines.forEach(l -> {
            System.out.println(l.exportToWavefront());
        });
    }
}

class Polyline
{
    private int[] vertexIDs;
    
    public Polyline(int... vertexIDs)
    {
        this.vertexIDs = Arrays.copyOf(vertexIDs, vertexIDs.length);
    }
    
    public String exportToWavefront() {
        StringBuilder builder = new StringBuilder("l");
        for(int vertexID : vertexIDs) {
            builder.append(' ');
            builder.append(vertexID);
        }
        return builder.toString();
    }
}

/**
 * Stores a single triangle.
 * @author FractionalRadix
 */
class Face
{
    private int[] vertexIDs = new int[3];
    
    public Face(int v0, int v1, int v2)
    {
        vertexIDs[0] = v0;
        vertexIDs[1] = v1;
        vertexIDs[2] = v2;        
    }
    
    public String exportToWavefront() {
       return "f " + vertexIDs[0] + " " + vertexIDs[1] + " " + vertexIDs[2];
    }
}

/**
 * Keep track of a single Vertex.
 * @author FractionalRadix
 */
class Vertex
{
    private double x, y, z, w;
    
    public Vertex(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vertex(double x, double y, double z) {
        this(x, y, z, 1.0);
    }
    
    public String exportToWavefront() {
       return "v " + x + " " + y + " " + z + " " + w;
       // Could use String.join but it's harder to read:
       // return String.join(" ", "v ", ""+x,  ""+y,  ""+z,  ""+w);
    }
}
