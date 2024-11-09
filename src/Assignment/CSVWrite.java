package Assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVWrite {

    public CSVWrite() throws IOException {

    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(data1 -> CSVWrite.escapeSpecialCharacters(data1))
                .collect(Collectors.joining(","));
    }

    public static String escapeSpecialCharacters(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static void givenDataArray_whenConvertToCSV_thenOutputCreated(List<String[]> dataLines, String fileName, boolean isAppend) throws IOException {
        FileWriter csvOutputFile = new FileWriter(fileName, isAppend);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(data -> CSVWrite.convertToCSV(data))
                    .forEach(pw::println);
        }
    }

    public static double getTotalValueOfColumn(String fileName, int columnNo, int headerLines, String memberNo) throws IOException {  // specify the header lines in the csv file to skip

        double sum = 0;

        // Read the csv file
        File file = new File(fileName);

        // Read all lines
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        lines.remove(0);
        // variable to hold int values after conversion
        double a;
        //current row number
        int rowcount = 1;
        for (String line : lines) {

            //skip the number of header lines in csv file
            if (rowcount <= headerLines) {
                rowcount = rowcount + 1;
                continue;
            }
            String[] array = line.split(",", -1);

            //read the numbers from the second column after skipping the header lines
            a = Double.parseDouble(array[columnNo]);
            // System.out.println("Icecream Sales (in INR) for the month of "+ array[0] + " is "+ a);
            if (fileName == "earning.csv") {
                if (array[0].equals(memberNo) && array[3].equals("false") && LocalDate.parse(array[5]).isAfter(LocalDate.now())) {
                    sum += a;
                }
            } else {
                //Adding the numbers from the second column of CSV file
                sum = sum + a;
            }

        }

        return sum;
    }

    public static List<Earning> getAllEarnings() throws FileNotFoundException {
        List<Earning> records = new ArrayList<>();

        File file = new File("earning.csv");
        if (!(file.exists() && !(file.isDirectory()))) {
            return records;
        }
        try (Scanner scanner = new Scanner(new File("earning.csv"))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    records.add(getRecordFromLine(line));
                }

            }

        }
        return records;
    }

    private static Earning getRecordFromLine(String line) {
        if (line.isEmpty()) {
            return null;
        }

        String[] values = line.split(",");
        String memberNo = (values[0]);
        String invoiceNo = (values[1]);

        int value = (Integer.parseInt(values[2]));
        int oriValue = (Integer.parseInt(values[3]));
        LocalDate earningDate = LocalDate.of(Integer.parseInt(values[4].split("-")[0]), Integer.parseInt(values[4].split("-")[1]), Integer.parseInt(values[4].split("-")[2]));
        LocalDate expiryDate = LocalDate.of(Integer.parseInt(values[5].split("-")[0]), Integer.parseInt(values[5].split("-")[1]), Integer.parseInt(values[5].split("-")[2]));

        Earning earning = new Earning(invoiceNo, value, memberNo, oriValue, earningDate, expiryDate);


        return earning;
    }
}
