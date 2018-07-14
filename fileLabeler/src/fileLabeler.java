import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class fileLabeler {

    public String term;

    public fileLabeler() {
    }

    public static void main(String[] args) throws IOException {

        String tsvFile = "/Users/amymao/Documents/W2 TA/20180112_classlist.tsv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";
        List<String> label = new ArrayList();
        List<Integer> length = new ArrayList();
        int rowNum = 0;
        List<Integer> startingPos = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(tsvFile));

            while ((line = br.readLine()) != null) {
                String[] description = line.split(cvsSplitBy);

                rowNum ++;
                label.add(description[0]);
                length.add(Integer.parseInt(description[2]));
                startingPos.add(Integer.parseInt(description[3]));
                System.out.println("customer [label= " + description[0] + " , length=" + description[2] + "]");
            }
        } catch (FileNotFoundException var23) {
            var23.printStackTrace();
        } catch (IOException var24) {
            var24.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }

        }



        // read text file:

        String textFile = "/Users/amymao/Documents/W2 TA/20180112_classlist.tsv";

        BufferedReader br2 = null;
        String line2 = "";

        br2 = new BufferedReader(new FileReader(textFile));

        ArrayList<ArrayList<String>> listOfLists = new ArrayList<>();

        for (int i = 0; i<label.size(); i++){
            ArrayList<String> list = new ArrayList<>();
            list.add(label.get(i));
            listOfLists.add(list);
        }

        while ((line2 = br. readLine()) != null){
            for (int i = 0; i<label.size(); i++){
                listOfLists.get(i).add(line2.substring(startingPos.get(i), startingPos.get(i) + length.get(i)));
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet");
        //Create a new row in current sheet
        for (int i = 0; i < rowNum + 1; i++){
            Row row = sheet.createRow(i);
            for (int j = 0; j < label.size(); j++){
                Cell cell = row.createCell(j);
                cell.setCellValue(listOfLists.get(i).get(j));
            }

        }


        FileOutputStream out = new FileOutputStream("/Users/amymao/Documents/W2 TA/20180112_classlist.tsv");
        workbook.write(out);
        out.close();




    }


}
