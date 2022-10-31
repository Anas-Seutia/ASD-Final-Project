class City implements Comparable<City> {
    double x, y;
    int P;
    int I;
    int V;
    boolean hasVaccine;
    double protection;


    public City(){
        x = 0.0;
        y = 0.0;
        P = 0;
        I = 0;
        V = 0;
        hasVaccine = false;
    }

    public City(double _x, double _y){
        x = _x;
        y = _y;
        P = 0;
        I = 0;
        V = 0;
        hasVaccine = false;
    }

    public City(double _x, double _y, int p){
        x = _x;
        y = _y;
        P = p;
        I = 0;
        V = 0;
        hasVaccine = false;
    }

    public boolean isInfected() {
        if (I > 0) {
            return true;
        }
        return false;
    }

    public void infectCity() {
        I = (int) (Math.random() * 50) + 10;
    }

    public void vaccinateCity() {
        hasVaccine = true;
    }

    public void setProtection(double dec) {
        protection = dec;
    }

    public boolean hasVaccine() {
        return hasVaccine;
    }

    public int compareTo(City other) { //sort points
        double EPS = 1e-9;
        double tmp;

        if(Math.abs(x - other.x) > EPS){ //sort points by x-coordinate
            tmp = x - other.x;
            if(tmp > EPS) return 1;
            else return -1;
        }
        else if(Math.abs(y - other.y) > EPS){ //in case of tie, sort by y coordinate
            tmp = y - other.y;
            if(tmp > EPS) return 1;
            else return -1;
        }else{
            return 0;
        }
    }

    public String toString(){
        return "(" + x + ", " + y + ") (population: " + P + ", infected: " + I + ", vaccined: " + V + ")";
    }
}
