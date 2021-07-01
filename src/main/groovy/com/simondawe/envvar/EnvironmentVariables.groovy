package com.simondawe.envvar

import static com.github.stefanbirkner.systemlambda.SystemLambda.*

class EnvironmentVariables {

  Map<String, String> testEnvVars = [:]

  private WithEnvironmentVariables envVars

  void setEnvironmentVariables() {
    if (testEnvVars) {
      testEnvVars.each { envVar ->
        if (envVars) {
          envVars = envVars.and(envVar.key, envVar.value)
        } else {
          envVars = withEnvironmentVariable(envVar.key, envVar.value)
        }
      }
    }
    envVars.setEnvironmentVariables()
  }

  void resetEnvironmentVariables() {
    envVars.restoreOriginalVariables(System.getenv())
  }

}
