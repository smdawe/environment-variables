package com.simondawe.envvar.extensions

import com.simondawe.envvar.EnvironmentVariables
import com.simondawe.envvar.extensions.EnvironmentVariablesExtension.ErrorListener
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo

class ElasticsearchMethodInterceptor extends AbstractMethodInterceptor {

  private final SpecInfo spec
  private final ErrorListener errorListener

  ElasticsearchMethodInterceptor(SpecInfo spec, ErrorListener errorListener) {
    this.spec = spec
    this.errorListener = errorListener
  }

  @Override
  void interceptSetupSpecMethod(IMethodInvocation invocation) throws Throwable {
    invocation.proceed()
    findEnvironmentVariables(invocation)?.setEnvironmentVariables(true)
  }

  @Override
  void interceptCleanupSpecMethod(IMethodInvocation invocation) throws Throwable {
    invocation.proceed()
    findEnvironmentVariables(invocation)?.resetEnvironmentVariables(true)
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

  private EnvironmentVariables findEnvironmentVariables(IMethodInvocation invocation, boolean shared = false) {
    EnvironmentVariables environmentVariables

    spec.allFields.each { FieldInfo fieldInfo ->
      if (fieldInfo.type == EnvironmentVariables && fieldInfo.shared == shared) {
        environmentVariables = (fieldInfo.readValue(invocation.instance) as EnvironmentVariables)
      }
    }

    return environmentVariables
  }
}