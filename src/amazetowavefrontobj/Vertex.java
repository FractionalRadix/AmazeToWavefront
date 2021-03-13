package amazetowavefrontobj;

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

