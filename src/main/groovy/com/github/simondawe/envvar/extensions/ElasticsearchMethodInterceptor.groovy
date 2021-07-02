package com.github.simondawe.envvar.extensions


import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo

class ElasticsearchMethodInterceptor extends AbstractMethodInterceptor {

  private final SpecInfo spec
  private final EnvironmentVariablesExtension.ErrorListener errorListener

  ElasticsearchMethodInterceptor(SpecInfo spec, EnvironmentVariablesExtension.ErrorListener errorListener) {
    this.spec = spec
    this.errorListener = errorListener
  }

  @Override
  void interceptSetupSpecMethod(IMethodInvocation invocation) throws Throwable {
    invocation.proceed()
    findEnvironmentVariables(invocation, true)?.setEnvironmentVariables()
  }

  @Override
  void interceptCleanupSpecMethod(IMethodInvocation invocation) throws Throwable {
    findEnvironmentVariables(invocation, true)?.resetEnvironmentVariables()
    invocation.proceed()
  }

  @Override
  void interceptSetupMethod(IMethodInvocation invocation) throws Throwable {
    invocation.proceed()
    findEnvironmentVariables(invocation)?.setEnvironmentVariables()
  }

  @Override
  void interceptCleanupMethod(IMethodInvocation invocation) throws Throwable {
    findEnvironmentVariables(invocation)?.resetEnvironmentVariables()
    invocation.proceed()
  }

  private com.github.simondawe.envvar.EnvironmentVariables findEnvironmentVariables(IMethodInvocation invocation, boolean shared = false) {
    com.github.simondawe.envvar.EnvironmentVariables environmentVariables

    spec.allFields.each { FieldInfo fieldInfo ->
      if (fieldInfo.type == com.github.simondawe.envvar.EnvironmentVariables && fieldInfo.shared == shared) {
        environmentVariables = (fieldInfo.readValue(invocation.instance) as com.github.simondawe.envvar.EnvironmentVariables)
      }
    }

    return environmentVariables
  }
}