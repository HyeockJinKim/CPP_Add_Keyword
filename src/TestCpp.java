import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Paths;

public class TestCpp {
    public static void main(String[] args) throws IOException {
        String filePath = Paths.get(".","src", "test").toString();
        CPP14Lexer lexer = new CPP14Lexer(new ANTLRFileStream(filePath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPP14Parser parser = new CPP14Parser(tokens);
        ParseTree tree = parser.translationunit();
    }
}
