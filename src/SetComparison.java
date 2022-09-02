import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.*;

public class SetComparison {
    private RandomAccessFile randomAccessFile;
    private RandomAccessFile fileWrite;

    /**
     * Method opens file if it existed
     * @param path path to File
     * @throws IOException
     */
    public void openReadFile(Path path) throws IOException {
        if (path.toFile().isFile()) {
            randomAccessFile = new RandomAccessFile(path.toFile(), "r");
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * Method opens file if it existed
     * @param path path to File
     * @throws IOException
     */
    public void openWriteFile(Path path) throws IOException {
        if (path.toFile().isFile()) {
            fileWrite = new RandomAccessFile(path.toFile(), "rw");
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * This method uses RandomAccessFile to read the lines of the file in the correct order.
     * Then compares the two sets by moving the pointer through the file
     * At the end closes file stream.
     * @return RandomAccessFile file
     * @throws IOException
     */
    public RandomAccessFile comparisonString() throws IOException {
        int n = Integer.parseInt(randomAccessFile.readLine());
        long firstTemp = randomAccessFile.getFilePointer();

        for (int i = 0; i < n; i++) {
            randomAccessFile.readLine();
        }
        int m = Integer.parseInt(randomAccessFile.readLine());
        long secondTemp = randomAccessFile.getFilePointer();

        for (int i = 0; i < m; i++) {
            randomAccessFile.readLine();
        }

        String tempFirstString2 = null;
        String tempSecondString2 = "?";
        int tempCoeff;
        String tempFirstString;
        String tempSecondString;
        long rowFirstPointer;
        long rowSecondPointer;
        int coeff2;

        for (int i = 0; i < n; i++) {

            tempCoeff = 0;
            tempFirstString = null;
            tempSecondString = "?";
            rowFirstPointer = firstTemp;
            rowSecondPointer = secondTemp;

            randomAccessFile.seek(rowFirstPointer);

            for (int j = 0; j < m; j++) {
                String str1 = randomAccessFile.readLine();
                String convertedString1 = new String(str1.getBytes("ISO-8859-1"), "UTF8");
                randomAccessFile.seek(rowSecondPointer);
                String str2 = randomAccessFile.readLine();
                rowSecondPointer = randomAccessFile.getFilePointer();
                String convertedString2 = new String(str2.getBytes("ISO-8859-1"), "UTF8");
                tempFirstString = convertedString1;
                coeff2 = 0;

                //Getting the most similar rows
                for (int k = 0; k < getTokens(convertedString1).size(); k++) {
                    for (int l = 0; l < getTokens(convertedString2).size(); l++) {
                        coeff2 += calculateCoefficient(normalizeSentence(getTokens(convertedString1).get(k)), normalizeSentence(getTokens(convertedString2).get(l)));
                    }
                }
                tempFirstString2 = convertedString2;

                if (coeff2 > tempCoeff) {
                    tempCoeff = coeff2;
                    tempFirstString2 = tempSecondString;
                    tempSecondString = convertedString2;
                }
                randomAccessFile.seek(rowFirstPointer);
            }
            fileWrite.write((tempFirstString + ":" + tempSecondString + "\n").getBytes());
            randomAccessFile.readLine();
            firstTemp = randomAccessFile.getFilePointer();
        }

        if (n < m)
            fileWrite.write((tempFirstString2 + ":" + tempSecondString2 + "\n").getBytes());
        randomAccessFile.close();
        fileWrite.close();
        return fileWrite;
    }

    /**
     * This method compares strings by characters and counts matches
     * @return int value of the matched characters
     */
    public int calculateCoefficient(String s, String s1) {
        int count = 0;
        int min = Math.min(s.length(), s1.length());
        for (int i = 0; i < min; i++) {
            String str = i < min - 1 ? s.substring(i, i + 1) : s.substring(i);
            String str1 = i < min - 1 ? s1.substring(i, i + 1) : s1.substring(i);
            if (str.equals(str1)) {
                count++;
            }
        }
        return count;
    }

    /**
     * This method transforms the sentence by iterating over the characters
     * @return String value of modified sentence
     */
    public String normalizeSentence(String sentence) {
        var resultContainer = new StringBuilder();
        String str = sentence.toLowerCase();
        for (var c : str.toCharArray()) {
            if (isNormalChar(c)) {
                resultContainer.append(c);
            }
        }
        return resultContainer.toString();
    }

    /**
     * This method checks if a character matches the required parameters
     * @return boolean value of the character being tested
     */
    public boolean isNormalChar(char c) {
        return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
    }

    /**
     * This method is converted to a List separating it by space
     * @return List<String>
     */
    public List<String> getTokens(String sentence) {
        List<String> tokens = new ArrayList();
        String[] words = sentence.split(" ");
        for (var w : words) {
            if (w.length() >= 1) {
                tokens.add(w);
            }
        }
        return tokens;
    }

}
