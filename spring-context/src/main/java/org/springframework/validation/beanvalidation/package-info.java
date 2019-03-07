/**
 * Support classes for integrating a JSR-303 Bean Validation provider
 * (such as Hibernate Validator) into a Spring ApplicationContext
 * and in particular with Spring's data binding and validation APIs.
 *
 * <p>The central class is {@link
 * org.springframework.validation.beanvalidation.LocalValidatorFactoryBean}
 * which defines a shared ValidatorFactory/Validator setup for availability
 * to other Spring components.
 * <p>
 * *支持集成JSR-303 Bean验证提供程序的类
 * *(例如Hibernate验证器)到Spring ApplicationContext中
 * *特别是Spring的数据绑定和验证api。
 * *
 * * <p>的中心类是{@link
 * * org.springframework.validation.beanvalidation.LocalValidatorFactoryBean}
 * *它定义了一个共享的ValidatorFactory/Validator可用性设置
 * *其他弹簧组件。
 */
@NonNullApi
@NonNullFields
package org.springframework.validation.beanvalidation;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
