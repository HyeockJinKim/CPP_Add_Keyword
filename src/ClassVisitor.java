import g4.CPP14BaseVisitor;
import g4.CPP14Parser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassVisitor<T> extends CPP14BaseVisitor<T> {
    private Map<String, CppClass> classMap = new HashMap<>();
    private List<ParseTree> tempSuperClassList = new ArrayList<>();
    private CppClass currentClass;

//    @Override
//    public T visitTranslationunit(CPP14Parser.TranslationunitContext ctx) {
//        T returnValue =  visitChildren(ctx);
//        System.out.println(ctx.getText());
//        return returnValue;
//    }

    @Override public T visitTypespecifierseq(CPP14Parser.TypespecifierseqContext ctx) {
        T returnValue = visitChildren(ctx);

        ctx.children.addAll(0, tempSuperClassList);
        tempSuperClassList = new ArrayList<>();

        return returnValue;
    }

    @Override
    public T visitDeclaration(CPP14Parser.DeclarationContext ctx) {
//        System.out.println(ctx.getChild(0).getChild(0).getClass().getName() + "!!!");
//        new CPP14Parser.SimpledeclarationContext()
        T returnValue = visitChildren(ctx);

        return returnValue;
    }

    @Override public T visitClassspecifier(CPP14Parser.ClassspecifierContext ctx) {
        String className = ctx.classhead().classheadname().getText();

        currentClass = new CppClass(className);

        T returnValue = visitChildren(ctx);

        classMap.put(className, currentClass);
        currentClass = null;

        return returnValue;
    }

    @Override public T visitBasespecifier(CPP14Parser.BasespecifierContext ctx) {
        String superClassName = ctx.basetypespecifier().classordecltype().classname().getText();
        if (currentClass != null) {
            if (ctx.Weak() != null) {
                currentClass.superClassMap.put(classMap.get(superClassName), true);
            } else {
                currentClass.superClassMap.put(classMap.get(superClassName), false);
            }

            for (CppClass supClass : currentClass.superClassMap.keySet()) {
                for (String virtualFunction : supClass.virtualFunctionMap.keySet()) {
                    currentClass.virtualFunctionMap.put(virtualFunction, supClass);
                }
            }
        }
        return visitChildren(ctx);
    }

    @Override public T visitFunctiondefinition(CPP14Parser.FunctiondefinitionContext ctx) {
        if (currentClass != null) {
            if (ctx.declspecifierseq() != null) { // virtual
                String virtualFunction = ctx.declarator().ptrdeclarator().noptrdeclarator().noptrdeclarator().getText();
                if (currentClass.virtualFunctionMap.keySet().contains(virtualFunction)) {
                    if (!currentClass.superClassMap.get(currentClass.virtualFunctionMap.get(virtualFunction))) {  // super class weak problem
                        System.out.println(currentClass.className + " extends Nweak "
                                + currentClass.virtualFunctionMap.get(virtualFunction).className + " class : "+ virtualFunction);
                    } else {
                        currentClass.virtualFunctionMap.put(virtualFunction, currentClass);
                    }
                } else {
                    if (ctx.declspecifierseq().getText().contains("virtual")) {
                        currentClass.virtualFunctionMap.put(virtualFunction, currentClass);
                    }
                }

//                if (ctx.declspecifierseq().getText().contains("virtual")) {
//                    if (currentClass.virtualFunctionMap.keySet().contains(virtualFunction)) {
//                        if (!isWeak) {
//                            System.out.println(currentClass.className + " is not weak class : " + virtualFunction);
//                        }
//                    } else {
//                        currentClass.virtualFunctionMap.put(virtualFunction);
//                    }
//                }
            }
        }
        return visitChildren(ctx);
    }

}
