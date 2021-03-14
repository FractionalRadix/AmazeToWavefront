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
                        {
                            // Add two half-width panels that extend in the direction of the X-axis.
                            maze.addPanel2(rowNr, colNr - 0.5, rowNr, colNr + 0.0);
                            maze.addPanel2(rowNr, colNr - 0.0, rowNr, colNr + 0.5);
                            // Add two half-width panels that extend in the direction of the Y-axis.
                            maze.addPanel1(rowNr - 0.5, colNr, rowNr + 0.0, colNr);
                            maze.addPanel1(rowNr + 0.0, colNr, rowNr + 0.5, colNr);
                        }
                        break;
                    case '-':
                        {
                            maze.addPanel2(rowNr, colNr - 0.5, rowNr, colNr + 0.5);
                        }
                        break;
                    case '|':
                        {
                            maze.addPanel1(rowNr - 0.5, colNr, rowNr + 0.5, colNr);
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