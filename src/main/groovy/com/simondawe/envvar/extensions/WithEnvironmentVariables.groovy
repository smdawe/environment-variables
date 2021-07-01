package com.simondawe.envvar.extensions

import com.simondawe.envvar.extensions.EnvironmentVariablesExtension
import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])
@ExtensionAnnotation(EnvironmentVariablesExtension)
@interface WithEnvironmentVariables {
}