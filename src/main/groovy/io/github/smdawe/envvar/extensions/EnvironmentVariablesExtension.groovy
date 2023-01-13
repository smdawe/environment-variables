package io.github.smdawe.envvar.extensions

import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.SpecInfo

class EnvironmentVariablesExtension extends AbstractAnnotationDrivenExtension<SpecEnvironmentVariables> {

  @Override
  void visitSpecAnnotation(SpecEnvironmentVariables annotation, SpecInfo spec) {
    ErrorListener listener = new ErrorListener()
    ElasticsearchMethodInterceptor interceptor = new ElasticsearchMethodInterceptor(spec, listener)
    spec.addSetupSpecInterceptor(interceptor)
    spec.addCleanupSpecInterceptor(interceptor)
    spec.addSetupInterceptor(interceptor)
    spec.addCleanupInterceptor(interceptor)
    spec.addListener(listener)
  }

  private class ErrorListener extends AbstractRunListener {
    List<ErrorInfo> errors = []

    @Override
    void error(ErrorInfo error) {
      errors.add(error)
    }
  }
}