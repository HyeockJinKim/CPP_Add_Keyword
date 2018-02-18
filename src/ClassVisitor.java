import g4.CPP14BaseVisitor;
import g4.CPP14Parser;
import java.util.HashMap;
import java.util.Map;

public class ClassVisitor<T> extends CPP14BaseVisitor<T> {
    private Map<String, CppClass> classMap = new HashMap<>();
    private CppClass currentClass;
    private boolean isWeak = false;

    @Override public T visitClassspecifier(CPP14Parser.ClassspecifierContext ctx) {
        String className = ctx.classhead().classheadname().getText();
        currentClass = new CppClass(className);

        T returnValue = visitChildren(ctx);

        classMap.put(className, currentClass);
        isWeak = false;
        currentClass = null;

        return returnValue;
    }

    @Override public T visitBasespecifier(CPP14Parser.BasespecifierContext ctx) {
        String superClassName = ctx.basetypespecifier().classordecltype().classname().getText();
        if (currentClass != null) {
            currentClass.superClassSet.add(classMap.get(superClassName));
            for (CppClass supClass : currentClass.superClassSet) {
                currentClass.virtualFunctionSet.addAll(supClass.virtualFunctionSet);
            }
            if (ctx.Weak() != null) {
                isWeak = true;
            }
        }
        return visitChildren(ctx);
    }

    @Override public T visitFunctiondefinition(CPP14Parser.FunctiondefinitionContext ctx) {
        if (currentClass != null) {
            if (ctx.declspecifierseq() != null) {
                if (ctx.declspecifierseq().getText().contains("virtual")) {
                    String virtualFunction = ctx.declarator().ptrdeclarator().noptrdeclarator().noptrdeclarator().getText();
                    if (currentClass.virtualFunctionSet.contains(virtualFunction)) {
                        if (!isWeak) {
                            System.out.println(currentClass.className + " is not weak class : " + virtualFunction);
                        }
                    } else {
                        currentClass.virtualFunctionSet.add(virtualFunction);
                    }
                }
            }
        }
        return visitChildren(ctx);
    }

}
