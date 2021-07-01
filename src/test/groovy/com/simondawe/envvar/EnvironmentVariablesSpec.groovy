package com.simondawe.envvar

import spock.lang.Specification

class EnvironmentVariablesSpec extends Specification {
    EnvironmentVariables environmentVariables

    void setup() {
        environmentVariables = new EnvironmentVariables()
    }

    void 'add an environment variable'() {
        when:
            environmentVariables.addEnvVar('test', 'splurge')

        then:
            environmentVariables.getTestEnvVars().test == 'splurge'
    }

    void 'set environment variables'() {
        given:
           environmentVariables.addEnvVar('new-test', 'test')

        when:
            environmentVariables.setEnvironmentVariables()

        then:
            System.getenv('new-test') == 'test'
    }

    void 'set environment variables and reset'() {
        given:
            environmentVariables.addEnvVar('new-test', 'test')

        when:
            environmentVariables.setEnvironmentVariables()

        then:
            System.getenv('new-test') == 'test'

        and:
            environmentVariables.resetEnvironmentVariables()

        then:
            !System.getenv('new-test')
    }
}
