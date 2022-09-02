import java.io.*;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        SetComparison comparison = new SetComparison();
        comparison.openReadFile(Path.of("input.txt"));
        comparison.openWriteFile(Path.of("output.txt"));
        comparison.comparisonString();

    }
}
