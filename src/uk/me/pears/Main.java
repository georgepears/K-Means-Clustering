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
        km.generate(getDataSetChoiceFromInput(), getNumberOfClustersFromInput(), 0.55);

        openWebPage();
    }

    private static int getNumberOfClustersFromInput() {
        // Input number of clusters (K), retry if invalid
        System.out.println("\nEnter number of clusters to generate (max 7, recommended 5):");
        int noClusters = scanner.nextInt();

        while (noClusters > 7 || noClusters < 1) {
            System.out.println("Invalid number of clusters! Re-enter a number from 1 to 7: ");
            noClusters = scanner.nextInt();
        }
        return noClusters;
    }

    private static String getDataSetChoiceFromInput() {
        String[] choices = new String[]{"data/data_clustered.csv", "data/data_random.csv"};

        System.out.println("\nChoose a dataset to cluster (type a number):\n    1) Clustered dataset (recommended)\n    2) Random dataset");
        int choice = scanner.nextInt();

        while (choice != 1 && choice != 2) {
            System.out.println("\nInvalid option, choose a number, either: \n    1) Clustered dataset (recommended)\n    2) Random dataset");
            choice = scanner.nextInt();
        }

        return choices[choice-1];
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
