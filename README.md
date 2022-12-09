# environment-variables-extension

Allow users of the spock framework to easily test code that requires environment variables.

Usage
```
repositories {
  mavenCentral()
}

dependencies {
  implementation 'io.github.smdawe:environment-variables-extension:1.1.0'
}
```

```
@WithEnvironmentVariables
class WithEnvironmentVariablesSpec extends Specification {

  EnvironmentVariables environmentVariables = new EnvironmentVariables()

  String envvarValue

  void setup() {
    envvarValue = UUID.randomUUID().toString()
    environmentVariables.addEnvironmentVariable('test', envvarValue)
  }

  void 'get an env var'() {
    expect:
      System.getenv('test') == envvarValue
  }
}
```