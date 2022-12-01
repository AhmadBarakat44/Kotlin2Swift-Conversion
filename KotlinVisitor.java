import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class KotlinVisitor extends KotlinParserBaseVisitor<String>
{
    @Override
    public String visitKotlinFile(KotlinParser.KotlinFileContext ctx) {
        String toplevelobjs = "";
        for (KotlinParser.TopLevelObjectContext ct : ctx.topLevelObject())
        {
            toplevelobjs += (visitTopLevelObject(ct));
        }
        return (visitPackageHeader(ctx.packageHeader()) + visitImportList(ctx.importList()) +toplevelobjs);
    }
    @Override
    public String visitPackageHeader(KotlinParser.PackageHeaderContext ctx) {
        //return super.visitPackageHeader(ctx);
        return "no package header yet\n";
    }

    @Override
    public String visitImportList(KotlinParser.ImportListContext ctx) {
        //return super.visitImportList(ctx);
        return "no imports yet\n";
    }

    @Override
    public String visitTopLevelObject(KotlinParser.TopLevelObjectContext ctx) {
        return (visitDeclaration(ctx.declaration()) + visitSemis(ctx.semis()));
    }

    @Override
    public String visitFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx) {
       return ("func " + visitSimpleIdentifier(ctx.simpleIdentifier())
                + visitFunctionValueParameters(ctx.functionValueParameters())
                + "\n" + visitFunctionBody(ctx.functionBody()));
    }

    @Override
    public String visitFunctionValueParameters(KotlinParser.FunctionValueParametersContext ctx) {
        String functionparameters = "";
        int parametersLength = ctx.functionValueParameter().size()-1;
        for (KotlinParser.FunctionValueParameterContext ct : ctx.functionValueParameter())
        {
            if(ct != ctx.functionValueParameter().get(parametersLength))
            {
                functionparameters += (visitFunctionValueParameter(ct)) +" , ";
            }
            else functionparameters += (visitFunctionValueParameter(ct));

        }
        return "(" + functionparameters +")";
    }

    @Override
    public String visitParameter(KotlinParser.ParameterContext ctx) {
        return (visitSimpleIdentifier(ctx.simpleIdentifier()) + ": " + visitType_(ctx.type_()));
    }

    @Override
    public String visitBlock(KotlinParser.BlockContext ctx) {
        return ("{\n" + visitStatements(ctx.statements()) + "\n}\n");
    }


     @Override
    public String visitStatements(KotlinParser.StatementsContext ctx) {
      /*  String statements = "";
        for (KotlinParser.StatementContext ct : ctx.statement())
        {
            statements += (visitStatement(ct));
        }
        return statements;*/
         return "no statements yet";
    }

 /*   @Override
    public String visitStatement(KotlinParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }*/

    @Override
    public String visitClassDeclaration(KotlinParser.ClassDeclarationContext ctx) {
        return ("class " + visitSimpleIdentifier(ctx.simpleIdentifier()) + "\n" + visitClassBody(ctx.classBody()));
    }

    @Override
    public String visitClassBody(KotlinParser.ClassBodyContext ctx) {
        return ("{\n" + visitClassMemberDeclarations(ctx.classMemberDeclarations()) + "\n}");
    }

    @Override
    public String visitClassMemberDeclarations(KotlinParser.ClassMemberDeclarationsContext ctx) {
        String myout = "";
        for (KotlinParser.ClassMemberDeclarationContext ct : ctx.classMemberDeclaration())
        {
           myout += (visitClassMemberDeclaration(ct));
        }
        return myout;
    }

    @Override
    public String visitSemis(KotlinParser.SemisContext ctx) {
        return "\n";
    }

    @Override
    public String visitPropertyDeclaration(KotlinParser.PropertyDeclarationContext ctx) {
        return ("var " + visitVariableDeclaration(ctx.variableDeclaration()) + "= "
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
