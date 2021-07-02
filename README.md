# environment-variables-extension

Allow users of the spock framework to easily test code that requires environment variables.

Usage
```
repositories {
  mavenCentral()
}

dependencies {
  compile 'com.com.simondawe.envvar:environment-variables-extension:1.0.0'
}
```

```
@WithEnvironmentVariables
class WithEnvironmentVariablesSpec extends Specification {

  EnvironmentVariables environmentVariables = new EnvironmentVariables()

  String envvarValue

  void setup() {
    envvarValue = UUID.randomUUID().toString()
    environmentVariables.addEnvVar('test', envvarValue)
  }

  void 'get an env var'() {
    expect:
      System.getenv('test') == envvarValue
  }
}
```