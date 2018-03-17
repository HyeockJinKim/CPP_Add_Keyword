import java.util.ArrayList;

public class CppFunction {
    public String functionName;
    public ArrayList<String> functionParameter;

    CppFunction() {
        functionParameter = new ArrayList<>();
    }
    public boolean equals(CppFunction cppFunction) {
        return functionName.equals(cppFunction.functionName) && functionParameter.equals(cppFunction.functionParameter);
    }
}
