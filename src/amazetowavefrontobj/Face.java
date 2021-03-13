package amazetowavefrontobj;

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