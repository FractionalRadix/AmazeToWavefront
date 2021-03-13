package amazetowavefrontobj;

import java.util.Arrays;

/** 
 * Keeps track of a single polyline.
 * @author FractionalRadix
 */
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
