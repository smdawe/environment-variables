package io.github.smdawe.envvar

import static com.github.stefanbirkner.systemlambda.SystemLambda.*

class EnvironmentVariables {

  final Map<String, String> testEnvVars = [:]

  private WithEnvironmentVariables envVars

  /**
   * Set all environment variables
   */
  void setEnvironmentVariables() {
    if (testEnvVars) {
      testEnvVars.each { envVar ->
        envVars = envVars ? envVars.and(envVar.key, envVar.value) : withEnvironmentVariable(envVar.key, envVar.value)
      }
    }
    envVars?.setEnvironmentVariables()
  }

  /**
   * Reset environment variables
   */
  void resetEnvironmentVariables() {
    envVars?.restoreOriginalVariables(System.getenv())
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
}
