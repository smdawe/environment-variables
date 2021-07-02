package com.github.simondawe.envvar

import com.github.simondawe.envvar.extensions.WithEnvironmentVariables
import spock.lang.Shared
import spock.lang.Specification

@WithEnvironmentVariables
class WithEnvironmentVariablesSpec extends Specification {

  @Shared
  EnvironmentVariables environmentVariablesShared = new EnvironmentVariables()

  @Shared
  String envvarValueShared

  EnvironmentVariables environmentVariables = new EnvironmentVariables()

  String envvarValue

  void setupSpec() {
    envvarValueShared = UUID.randomUUID().toString()
    environmentVariablesShared.addEnvVar('shared', envvarValueShared)
  }

  void cleanupSpec() {
    if (System.getenv('shared')) {
      throw new Exception('shared environment variables have not been cleaned up')
    }
  }

  void setup() {
    envvarValue = UUID.randomUUID().toString()
    environmentVariables.addEnvVar('test', envvarValue)
  }

  void cleanup() {
    if (System.getenv('test')) {
      throw new Exception('environment variables have not been cleaned up')
    }
  }

  void 'get an env var'() {
    expect:
      System.getenv('test') == envvarValue
      System.getenv('shared') == envvarValueShared
  }
}
