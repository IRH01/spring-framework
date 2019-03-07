/**
 * SPI package allowing Spring AOP framework to handle arbitrary advice types.
 *
 * <p>Users who want merely to <i>use</i> the Spring AOP framework, rather than extend
 * its capabilities, don't need to concern themselves with this package.
 *
 * <p>You may wish to use these adapters to wrap Spring-specific advices, such as MethodBeforeAdvice,
 * in MethodInterceptor, to allow their use in another AOP framework supporting the AOP Alliance interfaces.
 *
 * <p>These adapters do not depend on any other Spring framework classes to allow such usage.
 * <p>
 * SPI包允许Spring AOP框架处理任意的通知类型。
 * *
 * * <p只想要<i>的>用户使用的是</i> Spring AOP框架，而不是扩展
 * *其功能，不需要自己关心这个包。
 * *
 * <p>您可能希望使用这些适配器来包装特定于spring的建议，比如MethodBeforeAdvice，
 * *在MethodInterceptor中，允许在支持AOP联盟接口的另一个AOP框架中使用它们。
 * *
 * 这些适配器不依赖于任何其他Spring框架类来允许这样的使用。
 */
@NonNullApi
@NonNullFields
package org.springframework.aop.framework.adapter;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
