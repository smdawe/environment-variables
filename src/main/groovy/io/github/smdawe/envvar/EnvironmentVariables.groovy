package io.github.smdawe.envvar

import io.github.smdawe.envvar.rules.Rules

import static io.github.smdawe.envvar.rules.RulesHelper.*
class EnvironmentVariables {

  Map<String, String> testEnvVars = [:]

 // WithEnvironmentVariables envVars
  Rules rules

  /**
   * Set all environment variables
   */
  void setEnvironmentVariables() {
    if (testEnvVars) {
      testEnvVars.each { envVar ->
        rules = rules ? rules.and(envVar.key, envVar.value) : withEnvironmentVariable(envVar.key, envVar.value)
      }
    }
    rules?.setEnvironmentVariables()
  }

  /**
   * Reset environment variables
   */
  void resetEnvironmentVariables() {
    rules?.restoreOriginalVariables(System.getenv())
  }

  /**
   * Add an environment variable to the test
   * @param name
   * @param value
   */
  EnvironmentVariables addEnvironmentVariable(String name, String value) {
    testEnvVars.put(name, value)
    return this
  }

  EnvironmentVariables addEnvironmentVariables(Map<String, String> toAdd) {
    testEnvVars.putAll(toAdd)
    return this
  }
}
