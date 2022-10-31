import java.util.*;

public class Geometry {
    public static City[] lowerHull = new City[1000];
    public static City[] upperHull = new City[1000]; 

    public static double cross(City O, City A, City B){
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    } 

    public static boolean ccw(City p, City q, City r){
        return cross(p, q, r) > 0; 
    } 

    public static City[] convexHull(City[] P){
        if(P.length > 2){
            int n = P.length, upperLength = 0, lowerLength = 0;
            // lowerHull = new City[n];
            // upperHull = new City[n];

            Arrays.sort(P);
            
            lowerHull[0] = P[0]; 
            lowerHull[1] = P[1]; 
            lowerLength = 2;
            for (int i = 2; i < n; i++){
                while (lowerLength >= 2 && !ccw(lowerHull[lowerLength-2], lowerHull[lowerLength-1], P[i])){
                    lowerLength--;
                }
                lowerHull[lowerLength] = P[i];
                lowerLength++;
            }

            upperHull[0] = P[n-1];
            upperHull[1] = P[n-2];
            upperLength = 2;
            for(int i = n-3; i >= 0; i--){
                while (upperLength >= 2 && !ccw(upperHull[upperLength-2], upperHull[upperLength-1], P[i])){
                    upperLength--;
                }
                upperHull[upperLength] = P[i];
                upperLength++;
            }

            City[] result = new City[2*n];
            int t = 0;
            for(int i=0; i<lowerLength-1; i++){
                result[t] = lowerHull[i];
                t++;
            }
            for(int i=0; i<upperLength-1; i++){
                result[t] = upperHull[i];
                t++;
            }
            if(t>1){
                result = Arrays.copyOfRange(result, 0, t);
            }
            return result;

        }else if(P.length <= 2){
            return P.clone();
        }else{
            return null;
        }
    }

    public static double distTwoPoints(City a, City b){
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public static double getConvexHullPerimeter(City[] hull){
        double perimeter = 0.0;

        for(int i = 0; i<hull.length-1; i++){
            // System.out.println("Distance between points " + (i+1) + " & " + (i+2) + ": " + Geometry.distTwoPoints(hull[i], hull[i+1]));
            perimeter += distTwoPoints(hull[i], hull[i + 1]);
        }
        // System.out.println("Distance between 1 & " + (hull.length) + ": " + Geometry.distTwoPoints(hull[0], hull[hull.length-1]));
        perimeter += distTwoPoints(hull[0], hull[hull.length-1]);
        return perimeter;
    }
    public static double getArea(City a, City b){
        double distx = Math.abs(a.x - b.x);
        double disty = Math.abs(a.y - b.y);
        double rightTriArea = (distx * disty)/2;
        
        City temp = new City();
        if(a.y > b.y){
            temp = a;
            a = b;
            b = temp;
        }
        City c = new City(b.x, a.y);
        City d = new City(a.x, 0);
        double SqArea = distTwoPoints(a, c) * distTwoPoints(a, d);
        return rightTriArea + SqArea;
    }
    public static double getHullArea(City[] hull){
        double sum = 0;
        for(int i = 0; hull[i+1] != null; i++){
            sum += getArea(hull[i], hull[i+1]);
        }
        return sum;
    }
    public static double getConvexHullArea(){
        return getHullArea(upperHull) - getHullArea(lowerHull);
    }

    public static int closestPoint(City[] hull, City P){
        double shortestDist = Integer.MAX_VALUE;
        int point = -1;
        for (int i = 0; i < hull.length; i++){
            if(distTwoPoints(P, hull[i]) < shortestDist && hull[i] != P){
                point = i;
                shortestDist = distTwoPoints(P, hull[i]);
            }
        } 
        return point;
    }

    public static void printConvexHull(City[] hull){
        System.out.println("Convex Hull of Infected");
        for(int i = 0; i<hull.length; i++){
            if(hull[i] != null){
                System.out.println(hull[i]);
            }
        }
        System.out.println("Area of Infected Region: " + getConvexHullArea());
    }
}
