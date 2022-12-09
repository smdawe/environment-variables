package io.github.smdawe.envvar

import spock.lang.Specification

class EnvironmentVariablesSpec extends Specification {
    EnvironmentVariables environmentVariables

    void setup() {
        environmentVariables = new EnvironmentVariables()
    }

    void 'add an environment variable'() {
        when:
            environmentVariables.addEnvironmentVariable('test', 'splurge')

        then:
            environmentVariables.getTestEnvVars().test == 'splurge'
    }

    void 'set environment variables'() {
        given:
           environmentVariables.addEnvironmentVariable('new-test', 'test')

        when:
            environmentVariables.setEnvironmentVariables()

        then:
            System.getenv('new-test') == 'test'
    }

    void 'set environment variables and reset'() {
        given:
            environmentVariables.addEnvironmentVariable('new-test', 'test')

        when:
            environmentVariables.setEnvironmentVariables()

        then:
            System.getenv('new-test') == 'test'

        and:
            environmentVariables.resetEnvironmentVariables()

        then:
            !System.getenv('new-test')
    }

    void 'set environment variables without adding env vars'() {
        when:
            environmentVariables.setEnvironmentVariables()

        and:
            environmentVariables.resetEnvironmentVariables()

        then:
            noExceptionThrown()
    }
}
