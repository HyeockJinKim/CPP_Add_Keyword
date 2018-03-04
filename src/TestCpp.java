import g4.CPP14Lexer;
import g4.CPP14Parser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class TestCpp {
    public static void main(String[] args) throws IOException {
        String folderPath = Paths.get(".", "src", "example").toString();
        String outPath = Paths.get(".", "src", "out").toString();
        File[] files = Objects.requireNonNull(new File(folderPath).listFiles());
        for (File file : files) {
            String filePath = file.getPath();
            CPP14Lexer lexer = new CPP14Lexer(new ANTLRFileStream(filePath));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CPP14Parser parser = new CPP14Parser(tokens);
            ParseTree tree = parser.translationunit();

            System.out.println(file.getName() + " :");
            ClassVisitor visitor = new ClassVisitor();
            visitor.visit(tree);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Paths.get(outPath, file.getName()).toString()));
            bufferedWriter.write(tree.getText());
            bufferedWriter.close();
        }
    }
}

