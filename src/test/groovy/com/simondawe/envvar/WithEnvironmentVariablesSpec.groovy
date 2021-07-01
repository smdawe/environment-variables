package com.simondawe.envvar

import com.simondawe.envvar.extensions.WithEnvironmentVariables
import spock.lang.Specification

@WithEnvironmentVariables
class WithEnvironmentVariablesSpec extends Specification {

  EnvironmentVariables environmentVariables = new EnvironmentVariables()

  String envvarValue

  void setup() {
    envvarValue = UUID.randomUUID().toString()
    environmentVariables.setTestEnvVars([a: envvarValue])
  }

  void 'get an env var'() {
    expect:
      System.getenv('a') == envvarValue
  }
}
