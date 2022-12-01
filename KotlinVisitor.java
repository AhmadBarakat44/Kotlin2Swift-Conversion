import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class KotlinVisitor extends KotlinParserBaseVisitor<String> {
    //Importing kotlin lines
    @Override
    public String visitKotlinFile(KotlinParser.KotlinFileContext ctx) {
        return super.visitKotlinFile(ctx);
    }

    //Changing Function declaration
    @Override
    public String visitFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx) {
        return ("func " + visitSimpleIdentifier(ctx.simpleIdentifier())
                + visitFunctionValueParameters(ctx.functionValueParameters())
                + "\n" + visitFunctionBody(ctx.functionBody()));
    }

    // Getting values of the parameters of the function
    @Override
    public String visitFunctionValueParameters(KotlinParser.FunctionValueParametersContext ctx) {
        return "()";
    }

    //Get the content of the function body
    @Override
    public String visitBlock(KotlinParser.BlockContext ctx) {
        return ("{\n" + visitStatements(ctx.statements()) + "\n}");
    }

    // Make statements of the function
    @Override
    public String visitStatements(KotlinParser.StatementsContext ctx) {
        //todo implement statements
        return "no statement yet";
    }

    //Get class declaration
    @Override
    public String visitClassDeclaration(KotlinParser.ClassDeclarationContext ctx) {
        return ("class " + visitSimpleIdentifier(ctx.simpleIdentifier()) + "\n" + visitClassBody(ctx.classBody()));
    }

    // Get the content of the class body
    @Override
    public String visitClassBody(KotlinParser.ClassBodyContext ctx) {
        return ("{\n" + visitClassMemberDeclarations(ctx.classMemberDeclarations()) + "\n}");
    }

    //Get the type of the variables declared
    @Override
    public String visitPropertyDeclaration(KotlinParser.PropertyDeclarationContext ctx) {
        return ("var" + visitVariableDeclaration(ctx.variableDeclaration()) + "="
                + visitExpression(ctx.expression()) + "\n");
    }

    //Transform constant
    @Override
    public String visitLiteralConstant(KotlinParser.LiteralConstantContext ctx) {
        return (ctx.IntegerLiteral().getText());
    }

    //
    @Override
    public String visitSimpleIdentifier(KotlinParser.SimpleIdentifierContext ctx) {
        return ctx.Identifier().getText() + " ";
    }
    @Override
    public String visitScript(KotlinParser.ScriptContext ctx) {
        return visitChildren(ctx); }
}