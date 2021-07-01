package com.simondawe.envvar

import static com.github.stefanbirkner.systemlambda.SystemLambda.*

class EnvironmentVariables {

  private final Map<String, String> testEnvVars = [:]

  private WithEnvironmentVariables envVars

  void setEnvironmentVariables() {
    if (testEnvVars) {
      testEnvVars.each { envVar ->
        envVars = envVars ? envVar s.and(envVar.key, envVar.value) : withEnvironmentVariable(envVar.key, envVar.value)
      }
    }
    envVars.setEnvironmentVariables()
  }

  void resetEnvironmentVariables() {
    envVars.restoreOriginalVariables(System.getenv())
  }

  void addEnvVar(String name, String value) {
    testEnvVars.put(name, value)
  }

}
