package io.github.smdawe.envvar.rules
import static java.util.Collections.singletonMap;

class RulesHelper {

    static Rules withEnvironmentVariable(
            String name,
            String value
    ) {
        return new Rules(
                singletonMap(name, value)
        );
    }
}
