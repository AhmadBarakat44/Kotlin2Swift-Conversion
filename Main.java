import org.antlr.v4.runtime.*;


import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        CharStream in = CharStreams.fromFileName("./src/text.kt");
        KotlinLexer lexer = new KotlinLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        KotlinParser parser = new KotlinParser(tokens);
        KotlinVisitor visitor = new KotlinVisitor();
        String str = visitor.visitKotlinFile(parser.kotlinFile());
        System.out.println(str);
    }
}