/**
 * This package contains classes that allow easy access to EJBs.
 * The basis are AOP interceptors run before and after the EJB invocation.
 * In particular, the classes in this package allow transparent access
 * to stateless session beans (SLSBs) with local interfaces, avoiding
 * the need for application code using them to use EJB-specific APIs
 * and JNDI lookups, and work with business interfaces that could be
 * implemented without using EJB. This provides a valuable decoupling
 * of client (such as web components) and business objects (which may
 * or may not be EJBs). This gives us the choice of introducing EJB
 * into an application (or removing EJB from an application) without
 * affecting code using business objects.
 *
 * <p>The motivation for the classes in this package are discussed in Chapter 11 of
 * <a href="http://www.amazon.com/exec/obidos/tg/detail/-/0764543857/">Expert One-On-One J2EE Design and Development</a>
 * by Rod Johnson (Wrox, 2002).
 *
 * <p>However, the implementation and naming of classes in this package has changed.
 * It now uses FactoryBeans and AOP, rather than the custom bean definitions described in
 * <i>Expert One-on-One J2EE</i>.
 * <p>
 * *这个包包含允许轻松访问ejb的类。
 * *基础是在EJB调用之前和之后运行的AOP拦截器。
 * *特别地，这个包中的类允许透明访问
 * *到具有本地接口的无状态会话bean (SLSBs)，避免
 * *需要应用程序代码使用它们来使用特定于ejb的api
 * *和JNDI查找，并使用可能的业务接口
 * *不使用EJB实现。这提供了一个有价值的解耦
 * *客户端(如web组件)和业务对象(可能)
 * 或者可能不是ejb)。这使我们可以选择引入EJB
 * *进入应用程序(或从应用程序中删除EJB)
 * *使用业务对象影响代码。
 * *
 * 的第11章中讨论了这个包中类的动机
 * * <a href="http://www.amazon.com/exec/obidos/tg/detail/-/0764543857/">一对一J2EE设计开发专家</a>
 * *罗德·约翰逊(Wrox, 2002)。
 * *
 * 但是，这个包中的类的实现和命名已经改变。
 * *它现在使用factorybean和AOP，而不是在其中描述的自定义bean定义
 * * <i>专家一对一J2EE</i>。
 */
@NonNullApi
@NonNullFields
package org.springframework.ejb.access;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
