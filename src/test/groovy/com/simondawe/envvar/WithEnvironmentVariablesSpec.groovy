package com.simondawe.envvar

import com.simondawe.envvar.extensions.WithEnvironmentVariables
import spock.lang.Specification

@WithEnvironmentVariables
class WithEnvironmentVariablesSpec extends Specification {

  EnvironmentVariables environmentVariables = new EnvironmentVariables()

  String envvarValue

  void setup() {
    envvarValue = UUID.randomUUID().toString()
    environmentVariables.addEnvVar('a', envvarValue)
  }

  void cleanup() {
    if (System.getenv('a')) {
      throw new Exception('environment variables have not been cleaned up')
    }
  }

  void 'get an env var'() {
    expect:
      System.getenv('a') == envvarValue
  }
}
