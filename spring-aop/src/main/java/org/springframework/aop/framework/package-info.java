/**
 * Package containing Spring's basic AOP infrastructure, compliant with the
 * <a href="http://aopalliance.sourceforge.net">AOP Alliance</a> interfaces.
 *
 * <p>Spring AOP supports proxying interfaces or classes, introductions, and offers
 * static and dynamic pointcuts.
 *
 * <p>Any Spring AOP proxy can be cast to the ProxyConfig AOP configuration interface
 * in this package to add or remove interceptors.
 *
 * <p>The ProxyFactoryBean is a convenient way to create AOP proxies in a BeanFactory
 * or ApplicationContext. However, proxies can be created programmatically using the
 * ProxyFactory class.
 * <p>
 * 包含Spring的基本AOP基础设施的包，与
 * * <a href="http://aopalliance.sourceforge.net">AOP Alliance</a>接口。
 * *
 * <p>Spring AOP支持代理接口或类、介绍和提供
 * *静态和动态切入点。
 * *
 * 任何Spring AOP代理都可以转换到ProxyConfig AOP配置接口
 * *在此包中添加或删除拦截器。
 * *
 * ProxyFactoryBean是在BeanFactory中创建AOP代理的一种方便的方法
 * *或ApplicationContext。但是，可以使用
 * * ProxyFactory类。
 */
@NonNullApi
@NonNullFields
package org.springframework.aop.framework;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
