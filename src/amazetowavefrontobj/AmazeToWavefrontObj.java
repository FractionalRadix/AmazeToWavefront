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
        
        try {
            maze.writeToWavefrontFile("maze2.obj");
        }
        catch (IOException exc) {
            System.err.println("Tried to write maze to .obj file, but an error occurred.");
            exc.printStackTrace();
        }
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