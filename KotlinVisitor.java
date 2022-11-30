import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class KotlinVisitor extends KotlinParserBaseVisitor<String>
{
    @Override
    public String visitKotlinFile(KotlinParser.KotlinFileContext ctx) {
        return super.visitKotlinFile(ctx);
    }

    @Override
    public String visitFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx) {
        return ("func " + visitSimpleIdentifier(ctx.simpleIdentifier())
                + visitFunctionValueParameters(ctx.functionValueParameters())
                + "\n" + visitFunctionBody(ctx.functionBody()));
    }

    @Override
    public String visitFunctionValueParameters(KotlinParser.FunctionValueParametersContext ctx) {
        return "()";
    }


    @Override
    public String visitBlock(KotlinParser.BlockContext ctx) {
        return ("{\n" + visitStatements(ctx.statements()) + "\n}");
    }

    @Override
    public String visitStatements(KotlinParser.StatementsContext ctx) {
        //todo implement statements
        return "no statement yet";
    }

    @Override
    public String visitClassDeclaration(KotlinParser.ClassDeclarationContext ctx) {
        return ("class " + visitSimpleIdentifier(ctx.simpleIdentifier()) + "\n" + visitClassBody(ctx.classBody()));
    }

    @Override
    public String visitClassBody(KotlinParser.ClassBodyContext ctx) {
        return ("{\n" + visitClassMemberDeclarations(ctx.classMemberDeclarations()) + "\n}");
    }

    @Override
    public String visitPropertyDeclaration(KotlinParser.PropertyDeclarationContext ctx) {
        return ("var" + visitVariableDeclaration(ctx.variableDeclaration()) + "="
                + visitExpression(ctx.expression()) +"\n");
    }

    @Override
    public String visitLiteralConstant(KotlinParser.LiteralConstantContext ctx) {
        return (ctx.IntegerLiteral().getText());
    }

    @Override
    public String visitSimpleIdentifier(KotlinParser.SimpleIdentifierContext ctx) {
        return ctx.Identifier().getText() + " ";
    }
}
