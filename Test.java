import java.util.Arrays;

public class Test{

    static boolean makeVaccine() {
        String dna = DNA.createDNA();
        boolean gotVaccine = DNA.findVaccine(DNA.getDNAPattern(dna));
        if (gotVaccine) {
            System.out.println("vaccine got!");
        } else {
            System.out.println("vaccine failed.");
            System.out.println("Answer: " + Arrays.toString(DNA.getDNAPattern(dna)));
        }
        return gotVaccine;
    }

    static void UpdateCityCases(int i,City[] cities, City city, AdjacencyMatrix citiesRoads) {

        if (city.hasVaccine()) {
            
            if (city.P - city.V - 50000 > city.I) {
                city.V += 50000;
            } else {
                city.V = city.P - city.I;
            }

            for (int j = 0; j < citiesRoads.N; j++){
                if (citiesRoads.matrix[i][j] != 0 && (double)city.V/(double)city.P > Math.random() && !cities[j].hasVaccine()) {
                    cities[j].vaccinateCity();
                }
            }
        }
        int newCases = city.I;
        
        if (city.isInfected()) {
            if (city.I < city.P /2) {
                city.I += city.I * (1 - city.protection);
            } else {
                int remaining = city.P - city.I - city.V;
                city.I += remaining * (1 - city.protection);
            }

            for (int j = 0; j < citiesRoads.N; j++){
                if (citiesRoads.matrix[i][j] != 0 &&  (double)city.I/(double)city.P > Math.random()  && !cities[j].isInfected()) {
                    cities[j].infectCity();
                }
            }
        }

        newCases = -(newCases - city.I);
        if (newCases <= 0 && city.isInfected()) {
            city.I = city.P - city.V;
        }
    }

    
    public static void main(String[] args){
        int Days = 0, N = 15, Pop = 0, Inf = 0, Vac = 0, Cas = -1;
        double maxDistAdj = 20.0;
        AdjacencyMatrix adj = new AdjacencyMatrix(N);
        City[] city = new City[N];
    
        for (int i = 0; i<N; i++){
            city[i] = new City((double) Math.round(((double)(Math.random()*50)+1)*100)/100, (double) Math.round(((double)(Math.random()*50)+1)*100)/100, (int)(int)Math.floor(Math.random()*(2000000-50000+1)+50000));
        }

        System.out.println("Initialized Points: ");
        for(int i = 0; i < N; i++){
            Pop += city[i].P;
            System.out.println(city[i].toString());
        }
        
        for(int i = 0; i < N; i++){ //set adjacent connected cities
            for (int j = i+1; j < N; j++){
                if(Geometry.distTwoPoints(city[i], city[j]) <= maxDistAdj){
                    adj.addEdge(i, j, Geometry.distTwoPoints(city[i], city[j]));
                }
            }
            if(adj.isIsolated(i)){
                adj.addEdge(i, Geometry.closestPoint(city, city[i]), Geometry.distTwoPoints(city[i], city[Geometry.closestPoint(city, city[i])]));
            }
        }
        adj.printGraph();
        int infectedCount = 1;
        city[0].infectCity();
        while (Cas != 0) {
            City[] infectedCities = new City[infectedCount];
            infectedCount = 0;
            Days++;
            if (Vac == 0) {
                if (makeVaccine()) {
                    city[0].vaccinateCity();
                }
            }
            Cas = Inf;
            Inf = 0;
            Vac = 0;
            System.out.println("Day " + Days);
            for (int i = 0; i<N; i++){
                UpdateCityCases(i, city, city[i], adj);
                Inf += city[i].I;
                Vac += city[i].V;
                System.out.println("City: " + i + "\t| City Cases: " + city[i].I + " \t| City Vaccinated: " + city[i].V + " \t| isInfected: " + city[i].isInfected());
                if(city[i].isInfected()){
                    if(infectedCities.length < infectedCount + 1) {
                        City[] temp = infectedCities;
                        infectedCities = new City[infectedCount + 1];
                        for (int j = 0; j < temp.length; j++) {
                            infectedCities[j] = temp[j];
                        }
                    }
                    infectedCities[infectedCount] = city[i];
                    infectedCount++;
                }
            }
            City[] hull = Geometry.convexHull(infectedCities);

            Cas = -(Cas - Inf);
            System.out.println("New Cases: " + Cas + " Total Cases: " + Inf + " | Total Vaccinated: " + Vac + " | Day: " + Days);
            if(infectedCount>=3){
                Geometry.printConvexHull(hull);
            }
        }
    }
}