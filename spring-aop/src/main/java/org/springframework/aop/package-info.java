/**
 * Core Spring AOP interfaces, built on AOP Alliance AOP interoperability interfaces.
 *
 * <p>Any AOP Alliance MethodInterceptor is usable in Spring.
 *
 * <br>Spring AOP also offers:
 * <ul>
 * <li>Introduction support
 * <li>A Pointcut abstraction, supporting "static" pointcuts
 * (class and method-based) and "dynamic" pointcuts (also considering method arguments).
 * There are currently no AOP Alliance interfaces for pointcuts.
 * <li>A full range of advice types, including around, before, after returning and throws advice.
 * <li>Extensibility allowing arbitrary custom advice types to
 * be plugged in without modifying the core framework.
 * </ul>
 *
 * <p>Spring AOP can be used programmatically or (preferably)
 * integrated with the Spring IoC container.
 * <p>
 * 核心Spring AOP接口，建立在AOP联盟AOP互操作性接口的基础上。
 * *
 * 任何AOP Alliance MethodInterceptor在Spring中都是可用的。
 * *
 * * <br>Spring AOP还提供:
 * * < ul >
 * <李>介绍支持
 * * <li>一个切入点抽象，支持“静态”切入点
 * *(基于类和方法)和“动态”切入点(也考虑方法参数)。
 * *目前没有切入点的AOP联盟接口。
 * * <li>全方位的建议类型，包括前后左右，返回和抛出建议。
 * * <li>可扩展性，允许任意自定义通知类型
 * *无需修改核心框架即可插入。
 * * < / ul >
 * *
 * * <p>Spring AOP可以通过编程使用，或者(最好)
 * *与Spring IoC容器集成。
 */
@NonNullApi
@NonNullFields
package org.springframework.aop;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
