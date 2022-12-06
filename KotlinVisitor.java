import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.nimbus.State;

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
        return "//no package header yet\n";
    }

    @Override
    public String visitImportList(KotlinParser.ImportListContext ctx) {
        //return super.visitImportList(ctx);
        return "//no imports yet\n";
    }

    @Override
    public String visitTopLevelObject(KotlinParser.TopLevelObjectContext ctx) {
        return (visitDeclaration(ctx.declaration()) + visitSemis(ctx.semis()));
    }

    @Override
    public String visitFunctionDeclaration(KotlinParser.FunctionDeclarationContext ctx) {
        String returntype = "";
        if(ctx.type_() != null)
        {
            returntype =" -> " + visitType_(ctx.type_());
        }
        return ("func " + visitSimpleIdentifier(ctx.simpleIdentifier())
                + visitFunctionValueParameters(ctx.functionValueParameters()) + returntype
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
        return ("{\n\n" + visitStatements(ctx.statements()) + "\n}\n");
    }


     @Override
    public String visitStatements(KotlinParser.StatementsContext ctx) {
        String statements = "";
        for (KotlinParser.StatementContext ct : ctx.statement())
        {
            statements += (visitStatement(ct)) + "\n";
        }
        return statements;
    }

    @Override
    public String visitStatement(KotlinParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public String visitClassDeclaration(KotlinParser.ClassDeclarationContext ctx) {
        return ("class " + visitSimpleIdentifier(ctx.simpleIdentifier()) + "\n" + visitClassBody(ctx.classBody()));
    }

    @Override
    public String visitClassBody(KotlinParser.ClassBodyContext ctx) {
        return ("{\n\n" + visitClassMemberDeclarations(ctx.classMemberDeclarations()) + "\n}\n");
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
        return ("var " + visitVariableDeclaration(ctx.variableDeclaration()) + ctx.ASSIGNMENT().getText() + " "
                + visitExpression(ctx.expression()) +"\n");
    }

    @Override
    public String visitAssignment(KotlinParser.AssignmentContext ctx) {
        return (visitDirectlyAssignableExpression(ctx.directlyAssignableExpression()) + ctx.ASSIGNMENT().getText() + " " +
                visitExpression(ctx.expression()));
    }

    @Override
    public String visitAdditiveExpression(KotlinParser.AdditiveExpressionContext ctx) {

        if(ctx.children.size() > 1)
        {
            String Statement = "";
            int statementsLenght = ctx.multiplicativeExpression().size() - 1;
            int i = 0;
            for (KotlinParser.MultiplicativeExpressionContext ct : ctx.multiplicativeExpression())
            {
                if(ct != ctx.multiplicativeExpression().get(statementsLenght))
                {
                    Statement += (visitMultiplicativeExpression(ct)) + visitAdditiveOperator(ctx.additiveOperator(i));
                    i++;
                }
                else Statement += (visitMultiplicativeExpression(ct));
            }
            return Statement;
        }
        else {return super.visitAdditiveExpression(ctx);}
    }

    @Override
    public String visitMultiplicativeExpression(KotlinParser.MultiplicativeExpressionContext ctx) {

        if(ctx.children.size() > 1)
        {
            String Statement = "";
            int statementsLenght = ctx.asExpression().size() - 1;
            int i = 0;
            for (KotlinParser.AsExpressionContext ct : ctx.asExpression())
            {
                if(ct != ctx.asExpression().get(statementsLenght))
                {
                    Statement += visitAsExpression(ct) + visitMultiplicativeOperator(ctx.multiplicativeOperator(i));
                    i++;
                }
                else Statement += (visitAsExpression(ct));
            }
            return Statement;
        }
        else {return super.visitMultiplicativeExpression(ctx);}

    }

    @Override
    public String visitIfExpression(KotlinParser.IfExpressionContext ctx) {
        if(ctx.children.size() > 1)
        {
            if(ctx.ELSE() != null)
            {
                return ("if (" + visitExpression(ctx.expression()) + " )\n"
                        + visitControlStructureBody(ctx.controlStructureBody(0)) + "\nelse\n"
                        + visitControlStructureBody(ctx.controlStructureBody(1)) );
            }
            else
            {
                return ("if (" + visitExpression(ctx.expression()) + " )\n"
                        + visitControlStructureBody(ctx.controlStructureBody(0)) );
            }

        }
        else {return super.visitIfExpression(ctx);}
    }

    @Override
    public String visitDisjunction(KotlinParser.DisjunctionContext ctx) {
        if(ctx.children.size() > 1)
        {
            String Statement = "";
            int statementsLenght = ctx.conjunction().size() - 1;
            for (KotlinParser.ConjunctionContext ct : ctx.conjunction())
            {
                if(ct != ctx.conjunction().get(statementsLenght))
                {
                    Statement += visitConjunction(ct) + " || ";
                }
                else {Statement += visitConjunction(ct);}
            }
            return Statement;
        }
        else{return super.visitDisjunction(ctx);}
    }

    @Override
    public String visitConjunction(KotlinParser.ConjunctionContext ctx) {
        if(ctx.children.size() > 1)
        {
            String Statement = "";
            int statementsLenght = ctx.equality().size() - 1;
            for (KotlinParser.EqualityContext ct : ctx.equality())
            {
                if(ct != ctx.equality().get(statementsLenght))
                {
                    Statement += visitEquality(ct) + " && ";
                }
                else {Statement += visitEquality(ct);}
            }
            return Statement;
        }
        else{return super.visitConjunction(ctx);}
    }

    @Override
    public String visitEquality(KotlinParser.EqualityContext ctx) {
        if(ctx.children.size() > 1)
        {
            return (visitComparison(ctx.comparison(0)) + " " + visitEqualityOperator(ctx.equalityOperator(0))
                    + " " + visitComparison(ctx.comparison(1)));
        }
        else{ return super.visitEquality(ctx);}
    }

    @Override
    public String visitComparison(KotlinParser.ComparisonContext ctx) {
        if(ctx.children.size() > 1)
        {
            return (visitInfixOperation(ctx.infixOperation(0)) + " " + visitComparisonOperator(ctx.comparisonOperator())
                    + " " + visitInfixOperation(ctx.infixOperation(1)));
        }
        else{ return super.visitComparison(ctx);}
    }

    @Override
    public String visitJumpExpression(KotlinParser.JumpExpressionContext ctx) {
        return ctx.RETURN().getText() + " " + visitExpression(ctx.expression());
    }

    @Override
    public String visitEqualityOperator(KotlinParser.EqualityOperatorContext ctx) {
        String myout = "";
        if(ctx.EXCL_EQ() != null) { myout =  "!=";}
        else if (ctx.EXCL_EQEQ() != null) {myout =  "!==";}
        else if (ctx.EQEQ() != null) {myout =  "==";}
        else {myout =  "===";}
        return myout;
    }

    @Override
    public String visitComparisonOperator(KotlinParser.ComparisonOperatorContext ctx) {
        String myout = "";
        if(ctx.LANGLE() != null) { myout =  "<";}
        else if (ctx.LE() != null) {myout =  "<=";}
        else if (ctx.RANGLE() != null) {myout =  ">";}
        else {myout =  ">=";}
        return myout;
    }

    @Override
    public String visitAdditiveOperator(KotlinParser.AdditiveOperatorContext ctx) {
        String myout = "";
        if(ctx.ADD()!= null) {myout = "+ ";}
        else{myout = "- ";}
        return myout;
    }

    @Override
    public String visitMultiplicativeOperator(KotlinParser.MultiplicativeOperatorContext ctx) {
        String myout = "";
        if(ctx.MULT()!= null) {myout = "* ";}
        else{myout = "/ ";}
        return myout;
    }

    @Override
    public String visitLiteralConstant(KotlinParser.LiteralConstantContext ctx) {
        return (ctx.IntegerLiteral().getText() + " ");
    }

    @Override
    public String visitSimpleIdentifier(KotlinParser.SimpleIdentifierContext ctx) {
        return ctx.Identifier().getText() + " ";
    }
}
