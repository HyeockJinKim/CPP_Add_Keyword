import g4.CPP14BaseVisitor;
import g4.CPP14Parser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class ClassVisitor<T> extends CPP14BaseVisitor<T> {
    private Map<String, CppClass> classMap = new HashMap<>();
    private CppClass currentClass;
    private boolean isParam = false;
    static CppFunction cppFunction;

    /**
     * Class's start.
     * class like currentClass is created.
     * @param ctx
     * @return
     */
    @Override
    public T visitClassspecifier(CPP14Parser.ClassspecifierContext ctx) {
        String className = ctx.classhead().classheadname().getText();

        currentClass = new CppClass(className);

        T returnValue = visitChildren(ctx);

        classMap.put(className, currentClass);
        currentClass = null;

        return returnValue;
    }

    /**
     * Add class in inheritance to superclassMap.
     * @param ctx
     * @return
     */
    @Override
    public T visitBasespecifier(CPP14Parser.BasespecifierContext ctx) {
        String superClassName = ctx.basetypespecifier().classordecltype().classname().getText();
        if (currentClass != null) {
            currentClass.superClassMap.put(classMap.get(superClassName), ctx.Weak() != null);

            for (CppClass supClass : currentClass.superClassMap.keySet()) {
                for (CppFunction cppFunction : supClass.virtualFunctionMap.keySet()) {
                    currentClass.virtualFunctionMap.put(cppFunction, supClass);
                }
            }
        }
        return visitChildren(ctx);
    }

    /**
     * Put function parameter input into cppFunction.function parameter.
     *  to compare overload function.
     * @param ctx
     * @return
     */
    @Override
    public T visitTypespecifier(CPP14Parser.TypespecifierContext ctx) {
        if (isParam) {
            cppFunction.functionParameter.add(ctx.getText());
        }
        return super.visitTypespecifier(ctx);
    }

    /**
     * Function parameter input check.
     * @param ctx
     * @return
     */
    @Override
    public T visitParametersandqualifiers(CPP14Parser.ParametersandqualifiersContext ctx) {
        isParam = true;
        T returnValue = visitChildren(ctx);
        isParam = false;
        return returnValue;
    }

    /**
     * Put virtual function into virtual function map
     * key : virtual function name
     * value : super class (CppClass)
     * @param ctx
     * @return
     */
    @Override
    public T visitFunctiondefinition(CPP14Parser.FunctiondefinitionContext ctx) {
        cppFunction = new CppFunction();
        T returnValue = visitChildren(ctx);

        if (currentClass != null) {
            if (ctx.declspecifierseq() != null) {

                cppFunction.functionName = ctx.declarator().ptrdeclarator().noptrdeclarator().noptrdeclarator().getText();

                currentClass.virtualFunctionMap.keySet().stream()
                        .filter(x-> x.functionName.equals(cppFunction.functionName)
                                && x.functionParameter.equals(cppFunction.functionParameter))
                        .findAny()
                        .ifPresentOrElse(function-> {
                            if (!currentClass.superClassMap.get(currentClass.virtualFunctionMap.get(function))) {
                                System.out.println(currentClass.className + " extends Nweak "
                                        + currentClass.virtualFunctionMap.get(function).className + " class : "+ function.functionName);
                            } else {
                                currentClass.virtualFunctionMap.put(function, currentClass);
                            } }, ()-> {
                            if (ctx.declspecifierseq().getText().contains("virtual")) {
                                currentClass.virtualFunctionMap.put(cppFunction, currentClass);
                            }
                        });
            }
        }
        return returnValue;
    }

}
