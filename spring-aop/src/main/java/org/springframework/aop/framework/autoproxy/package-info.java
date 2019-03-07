/**
 * Bean post-processors for use in ApplicationContexts to simplify AOP usage
 * by automatically creating AOP proxies without the need to use a ProxyFactoryBean.
 *
 * <p>The various post-processors in this package need only be added to an ApplicationContext
 * (typically in an XML bean definition document) to automatically proxy selected beans.
 *
 * <p><b>NB</b>: Automatic auto-proxying is not supported for BeanFactory implementations,
 * as post-processors beans are only automatically detected in application contexts.
 * Post-processors can be explicitly registered on a ConfigurableBeanFactory instead.
 * <p>
 * Bean后处理器，用于在应用程序上下文中简化AOP的使用
 * *无需使用ProxyFactoryBean自动创建AOP代理。
 * *
 * 这个包中的各种后处理程序只需要添加到ApplicationContext中
 * *(通常在XML bean定义文档中)自动代理选择的bean。
 * *
 * * <p><b>NB</b>: BeanFactory实现不支持自动代理，
 * *由于后处理程序bean只在应用程序上下文中自动检测。
 * 可以显式地在ConfigurableBeanFactory上注册后处理程序。
 */
@NonNullApi
@NonNullFields
package org.springframework.aop.framework.autoproxy;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
