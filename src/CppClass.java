import java.util.HashSet;
import java.util.Set;

public class CppClass {
    String className;
    public Set<CppClass> superClassSet;
    public Set<String> virtualFunctionSet;

    CppClass(String className) {
        this.className = className;
        this.superClassSet = new HashSet<>();
        this.virtualFunctionSet = new HashSet<>();
    }
}
