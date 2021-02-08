package uk.me.pears;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    public ArrayList<Coordinate> read(String dataSetURL) {
        ArrayList<Coordinate> dataFromCSV = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataSetURL))) {
            // Get each row of csv file
            String row;
            while ((row = reader.readLine()) != null) {
                // Split row by ','
                String[] stringCoords = row.split(",");
                dataFromCSV.add(convertStringToCoordinate(stringCoords));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataFromCSV;
    }

    public Coordinate convertStringToCoordinate(String[] stringCoords) {
        // Convert string coordinates to Coordinate object
        Coordinate coords = new Coordinate();
        coords.setX(Double.parseDouble(stringCoords[0]));
        coords.setY(Double.parseDouble(stringCoords[1]));
        return coords;
    }

}
