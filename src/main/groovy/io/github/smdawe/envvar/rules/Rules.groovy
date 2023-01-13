package io.github.smdawe.envvar.rules

import java.lang.reflect.Field
import java.util.concurrent.Callable

import static java.lang.System.*

class Rules {
    private final Map<String, String> variables

    private Rules(
            Map<String, String> variables
    ) {
        this.variables = variables
    }


    Rules and(
            String name,
            String value
    ) {
        validateNotSet(name, value)
        HashMap<String, String> moreVariables = new HashMap<>(variables)
        moreVariables.put(name, value)
        return new Rules(moreVariables)
    }

    private void validateNotSet(
            String name,
            String value
    ) {
        if (variables.containsKey(name)) {
            String currentValue = variables.get(name)
            throw new IllegalArgumentException(
                    "The environment variable '" + name + "' cannot be set to "
                            + format(value) + " because it was already set to "
                            + format(currentValue) + "."
            )
        }
    }

    private String format(
            String text
    ) {
        if (text == null)
            return "null"
        else
            return "'" + text + "'"
    }

    public <T> T execute(
            Callable<T> callable
    ) throws Exception {
        Map<String, String> originalVariables = new HashMap<>(getenv())
        try {
            setEnvironmentVariables()
            return callable.call()
        } finally {
            restoreOriginalVariables(originalVariables)
        }
    }
    
    void execute(
            RuleStatement statement
    ) throws Exception {
        Map<String, String> originalVariables = new HashMap<>(getenv())
        try {
            setEnvironmentVariables()
            statement.execute()
        } finally {
            restoreOriginalVariables(originalVariables)
        }
    }

    private void setEnvironmentVariables() {
        overrideVariables(
                getEditableMapOfVariables()
        )
        overrideVariables(
                getTheCaseInsensitiveEnvironment()
        )
    }

    private void overrideVariables(
            Map<String, String> existingVariables
    ) {
        if (existingVariables != null) //theCaseInsensitiveEnvironment may be null
            variables.forEach(
                    (name, value) -> set(existingVariables, name, value)
            )
    }

    private void set(
            Map<String, String> variables,
            String name,
            String value
    ) {
        if (value == null)
            variables.remove(name)
        else
            variables.put(name, value)
    }

    void restoreOriginalVariables(
            Map<String, String> originalVariables
    ) {
        restoreVariables(
                getEditableMapOfVariables(),
                originalVariables
        )
        restoreVariables(
                getTheCaseInsensitiveEnvironment(),
                originalVariables
        )
    }

    void restoreVariables(
            Map<String, String> variables,
            Map<String, String> originalVariables
    ) {
        if (variables != null) { //theCaseInsensitiveEnvironment may be null
            variables.clear()
            variables.putAll(originalVariables)
        }
    }

    private static Map<String, String> getEditableMapOfVariables() {
        Class<?> classOfMap = getenv().getClass()
        try {
            return getFieldValue(classOfMap, getenv(), "m")
        } catch (IllegalAccessException e) {
            throw new RuntimeException("System Rules cannot access the field"
                    + " 'm' of the map System.getenv().", e)
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("System Rules expects System.getenv() to"
                    + " have a field 'm' but it has not.", e)
        }
    }

    /*
     * The names of environment variables are case-insensitive in Windows.
     * Therefore it stores the variables in a TreeMap named
     * theCaseInsensitiveEnvironment.
     */
    private static Map<String, String> getTheCaseInsensitiveEnvironment() {
        try {
            Class<?> processEnvironment = forName("java.lang.ProcessEnvironment")
            return getFieldValue(
                    processEnvironment, null, "theCaseInsensitiveEnvironment")
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("System Rules expects the existence of"
                    + " the class java.lang.ProcessEnvironment but it does not"
                    + " exist.", e)
        } catch (IllegalAccessException e) {
            throw new RuntimeException("System Rules cannot access the static"
                    + " field 'theCaseInsensitiveEnvironment' of the class"
                    + " java.lang.ProcessEnvironment.", e)
        } catch (NoSuchFieldException e) {
            //this field is only available for Windows
            return null
        }
    }

    private static Map<String, String> getFieldValue(
            Class<?> klass,
            Object object,
            String name
    ) throws NoSuchFieldException, IllegalAccessException {
        Field field = klass.getDeclaredField(name)
        field.setAccessible(true)
        return (Map<String, String>) field.get(object)
    }
}