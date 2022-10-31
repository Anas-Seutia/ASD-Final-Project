import java.util.Random;
import java.util.Scanner;

public class DNA {
    static Random random = new Random();
    static String virus = "";
    static int DNALength = 15;

    static String createDNA() {
        do {
        virus = "";
        String setOfCharacters = "ACGT";
        int randomInt;
        String randomChar;
        
        
            for (int i = 0; i < DNALength; i++) {
                randomInt = random.nextInt(setOfCharacters.length());
                randomChar = String.valueOf(setOfCharacters.charAt(randomInt));
                virus = virus + randomChar;
            } 
        } while (getDNAPattern(virus).length == DNALength - 2);

        return virus;
    }

    static String[] getDNAPattern(String DNA) {
        int maxPattern = DNA.length() - 2;
        char[] chars = DNA.toCharArray();
        String patterns[] = new String[maxPattern];
        int patternCount[] = new int[maxPattern];

        for (int i = 0; i < maxPattern; i++) {
            String pattern = "" + chars[i] + chars[i+1] + chars[i+2];
            int j = 0;
            boolean found = false;
            while (patterns[j] != null) {
                if (pattern.equals(patterns[j])) {
                    patternCount[j]++;
                    found = true;
                    break;
                }
                j++;
            }
            if (found == false){
                patterns[j] = pattern;
                patternCount[j] = 1;
            }  
        }

        int highest = 0, count = 0;

        for (int i = 0; i < maxPattern; i++) {
            if (highest < patternCount[i]) {
                highest = patternCount[i];
                count = 1;
            } else if (highest == patternCount[i]) {
                count++;
            }
        }

        String mostItterated[] = new String[count];
        count = 0;
        

        for (int i = 0; i < maxPattern; i++) {
            if (patternCount[i] == highest) {
                mostItterated[count] = patterns[i];
                count++;
            }
        }
        return mostItterated;
    }

    static boolean findVaccine(String DNAVac[]) {
        Scanner in = new Scanner(System.in);
        System.out.println("Virus DNA pattern: " + virus);
        System.out.print("Create a vaccine!: ");
        String c = in.nextLine();
        for (int i = 0; i < DNAVac.length; i++) {
            if (c.equals(DNAVac[i])) {
                return true;
            }
        }
        return false;
    }
}
