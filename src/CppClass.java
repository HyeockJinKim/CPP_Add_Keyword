import java.util.HashMap;
import java.util.Map;

public class CppClass {
    String className;
    public Map<CppClass, Boolean> superClassMap;// Map Weak, Nweak
    public Map<String, CppClass> virtualFunctionMap; // Map

    CppClass(String className) {
        this.className = className;
        this.superClassMap = new HashMap<>();
        this.virtualFunctionMap = new HashMap<>();
    }
}
