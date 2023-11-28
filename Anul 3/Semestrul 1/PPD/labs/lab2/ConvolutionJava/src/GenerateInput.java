import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateInput {
    static int N, M;
    static int n, m;

    public static void main(String[] args){
        N = 1000;
        M = 1000;
        n = m = 3;
        generateInput("input.txt");
    }

    private static void generateInput(String file) {
        try{
            File f = new File(file);
            FileWriter fw = new FileWriter(f);
            fw.write(n + "\n");
            fw.write(m + "\n");
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < m; j++) {
                    fw.write((int)(Math.random() * 100) + " ");
                }
                fw.write("\n");
            }
            fw.write(N + "\n");
            fw.write(M + "\n");
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    fw.write((int)(Math.random() * 100) + " ");
                }
                fw.write("\n");
            }
            fw.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
