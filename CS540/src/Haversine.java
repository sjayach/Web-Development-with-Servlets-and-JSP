public class Haversine { 
   public static void main(String[] args) { 
      double L1 = 34.076642;
      double G1 = -118.03942;
      double L2 = 34.0861;
      double G2 = 118.0347;

      // convert to radians
      L1 = Math.toRadians(L1);
      L2 = Math.toRadians(L2);
      G1 = Math.toRadians(G1);
      G2 = Math.toRadians(G2);

      // do the spherical trig calculation
      double angle = Math.acos(Math.sin(L1) * Math.sin(L2)  +
                               Math.cos(L1) * Math.cos(L2) * Math.cos(G1 - G2));

      // convert back to degrees
      angle = Math.toDegrees(angle);

      // each degree on a great circle of Earth is 69.1105 miles
      double distance = 69.1105 * angle;

      System.out.println(distance + " miles");
   }

}