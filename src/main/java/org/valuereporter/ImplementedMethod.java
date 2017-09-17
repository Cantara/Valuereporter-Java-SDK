package org.valuereporter;

/**
 * This class will represent each of the metods the crawler has detected as interesting.
 *
 * These methods will be reported to the Valuereporter analyzer, as a method that might be used.
 *
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ImplementedMethod implements Observation {
    private final String name;

    public ImplementedMethod(String name) {
        this.name = name;
    }

    public ImplementedMethod(String methodName, String className) {
        name = className.concat(".").concat(methodName);
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "ImplementedMethod{" +
                "name='" + name +
                '}';
    }

    @Override
    public String toJson() {
        String json = "{\"name\": \"" + name + "\"}";
        return json;
    }
}
