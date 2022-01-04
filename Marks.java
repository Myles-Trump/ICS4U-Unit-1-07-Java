/*
* This is a program generates marks
* after reading in 2 text files.
*
* @author  Myles Trump
* @version 1.0
* @since   2022-01-03
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
* This is the marks program.
*/
final class Marks {

    /**
    * 10.
    */
    public static final int C10 = 10;
    /**
    * 75.
    */
    public static final int C75 = 75;

    /**
    * Prevent instantiation
    * Throw an exception IllegalStateException.
    * if this ever is called
    *
    * @throws IllegalStateException
    *
    */
    private Marks() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
    * The generateMarks() function.
    *
    * @param arrayOfStudents the collection of students
    * @param arrayOfAssignments the collection of assignments
    * @return the generated marks
    */
    public static String[][] generateMarks(
        final ArrayList<String> arrayOfStudents,
        final ArrayList<String> arrayOfAssignments) {

        int index;

        String[][] markArray = new String[arrayOfAssignments.size() + 1]
            [arrayOfStudents.size() + 1];

        for (index = 0; index < arrayOfAssignments.size(); index++) {
            markArray[index + 1][0] = arrayOfAssignments.get(index);
        }

        for (index = 0; index < arrayOfStudents.size(); index++) {
            markArray[0][index + 1] = arrayOfStudents.get(index);
        }

        // Normal Distribution numbers
        Random random = new Random();

        for (int indexX = 0; indexX < arrayOfAssignments.size();
            indexX++) {
            for (int indexY = 0; indexY < arrayOfStudents.size(); indexY++) {
                int mark = (int) Math.floor(random.nextGaussian() * C10 + C75);
                String markString = String.valueOf(mark);
                markArray[indexX + 1][indexY + 1] = markString + "%";
            }
        }
        return markArray;
    }

    /**
    * The starting main() function.
    *
    * @param args No args will be used
    */
    public static void main(final String[] args) {
        final ArrayList<String> listOfStudents = new ArrayList<String>();
        final ArrayList<String> listOfAssingments = new ArrayList<String>();
        final Path studentFilePath = Paths.get("./", args[0]);
        final Path assignmentFilePath = Paths.get("./", args[1]);
        final Charset charset = Charset.forName("UTF-8");

        try (BufferedReader readerStudent = Files.newBufferedReader(
                                     studentFilePath, charset)) {
            String lineStudent = null;
            while ((lineStudent = readerStudent.readLine()) != null) {
                listOfStudents.add(lineStudent);
                System.out.println(lineStudent);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        try (BufferedReader readerAssignment = Files.newBufferedReader(
                                     assignmentFilePath, charset)) {
            String lineAssignment = null;
            while ((lineAssignment = readerAssignment.readLine()) != null) {
                listOfAssingments.add(lineAssignment);
                System.out.println(lineAssignment);
            }
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }

        String[][] markArray = generateMarks(listOfStudents,
            listOfAssingments);
        // System.out.println(Arrays.deepToString(marksArray));

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("marks.csv"));
            StringBuilder sb = new StringBuilder();
            for (int indexY = 0; indexY < markArray[0].length; indexY++) {
                sb.setLength(0);
                for (int indexX = 0; indexX < markArray.length; indexX++) {
                    sb.append(markArray[indexX][indexY]);
                    if (indexX + 1 < markArray.length) {
                        sb.append(",");
                    }
                }
                br.write(sb.toString());
                br.newLine();
            }
            br.close();
        } catch (IOException errorCode) {
            System.err.println(errorCode);
        }
        System.out.println("\nDone.");
    }
}
