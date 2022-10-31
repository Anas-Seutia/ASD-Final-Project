public class AdjacencyMatrix {
    int N; //number of vertices
    public double matrix[][];

    public AdjacencyMatrix(int N) {
        this.N = N;
        matrix = new double[N][N];
    }

    public void addEdge(int from, int to, double dist) {
        matrix[from][to] = dist;
        matrix[to][from] = dist; 
    }

    public void printGraph() {
        //prints adjacent nodes of each vertex
        for (int i = 0; i < N; i++) {
            System.out.print("City " + i + " is connected to: ");
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] != 0){ //if entry is 1, print j value
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }
    public boolean isIsolated(int index){
        boolean isolated = true;
        for (int i = 0; i < N; i++){
            if(matrix[index][i] != 0){
                isolated = false;
                break;
            }
        }
        return isolated;
    }
    
}
