/**
 * AspectJ integration package. Includes Spring AOP advice implementations for AspectJ 5
 * annotation-style methods, and an AspectJExpressionPointcut: a Spring AOP Pointcut
 * implementation that allows use of the AspectJ pointcut expression language with the Spring AOP
 * runtime framework.
 *
 * <p>Note that use of this package does <i>not</i> require the use of the {@code ajc} compiler
 * or AspectJ load-time weaver. It is intended to enable the use of a valuable subset of AspectJ
 * functionality, with consistent semantics, with the proxy-based Spring AOP framework.
 * <p>
 * AspectJ集成包。包括AspectJ 5的Spring AOP通知实现
 * *注释风格的方法和AspectJExpressionPointcut: Spring AOP切入点
 * *实现，允许在Spring AOP中使用AspectJ切入点表达式语言
 * *运行时框架。
 * *
 * * <p>注意，使用这个包并不需要使用{@code ajc}编译器来执行<i>而不是</i>
 * 或者AspectJ加载时编织器。它的目的是启用AspectJ的一个有价值的子集
 * *功能，具有一致的语义，以及基于代理的Spring AOP框架。
 */
@NonNullApi
@NonNullFields
package org.springframework.aop.aspectj;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
