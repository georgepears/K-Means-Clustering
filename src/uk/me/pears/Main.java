package uk.me.pears;

import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Create instance of KMeans class
        KMeans km = new KMeans();
        // Generate cluster from given data source and specified number of clusters and expected dunn index (use 1 for first result)
        km.generate("data/data.csv", 5, 0.55);

        openWebPage();
    }

    private static int getNumberOfClustersFromInput() {
        // Input number of clusters (K), retry if invalid
        System.out.println("\nEnter number of clusters to generate (max 7):");
        int noClusters = scanner.nextInt();

        while (noClusters > 7 || noClusters < 1) {
            System.out.println("Invalid number of clusters! Re-enter a number from 1 to 7: ");
            noClusters = scanner.nextInt();
        }
        return noClusters;
    }

    private static String getDataSetChoiceFromInput() {
        // Return input
        return "";
    }

    private static Double getAcceptableDunnIndexFromInput() {
        // Return input
        return 0.0;
    }

    private static void openWebPage() {
        // Open generated HTML page in browser
        try {
            Desktop.getDesktop().browse(new File("data/out/output.html").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
