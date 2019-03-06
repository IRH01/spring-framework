/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cache.annotation;

import java.lang.annotation.*;

/**
 * {@code @CacheConfig} provides a mechanism for sharing common cache-related
 * settings at the class level.
 *
 * <p>When this annotation is present on a given class, it provides a set
 * of default settings for any cache operation defined in that class.
 *
 * @author Stephane Nicoll
 * @author Sam Brannen
 * @since 4.1
 */

/**
 * {@code @CacheConfig}提供了一种共享公共缓存相关的机制
 * 类级别的设置。
 * <p>
 * 当这个注释出现在一个给定的类上时，它提供了一个集合
 * 在该类中定义的任何缓存操作的默认设置。
 * <p>
 * 作者Stephane Nicoll
 * 作者Sam Brannen
 *
 * @since 4.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheConfig {

	/**
	 * Names of the default caches to consider for caching operations defined
	 * in the annotated class.
	 * <p>If none is set at the operation level, these are used instead of the default.
	 * <p>May be used to determine the target cache (or caches), matching the
	 * qualifier value or the bean names of a specific bean definition.
	 */
	/**
	 * 为定义的缓存操作考虑的默认缓存的名称
	 * 在带注释的类中。
	 * <p>如果在操作级别没有设置任何参数，则使用这些参数而不是默认值。
	 * <p>可用于确定目标缓存(或多个缓存)，匹配
	 * 限定符值或特定bean定义的bean名称。
	 */
	String[] cacheNames() default {};

	/**
	 * The bean name of the default {@link org.springframework.cache.interceptor.KeyGenerator} to
	 * use for the class.
	 * <p>If none is set at the operation level, this one is used instead of the default.
	 * <p>The key generator is mutually exclusive with the use of a custom key. When such key is
	 * defined for the operation, the value of this key generator is ignored.
	 */
	/**
	 * 默认的{@link org.springframework.cache.interceptor.KeyGenerator}的bean名称
	 * 用于类。
	 * <p>如果在操作级别没有设置任何参数，则使用这个参数而不是默认值。
	 * <p>键生成器与自定义键的使用是互斥的。当该键为
	 * 为该操作定义的键生成器的值将被忽略。
	 */
	String keyGenerator() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.CacheManager} to use to
	 * create a default {@link org.springframework.cache.interceptor.CacheResolver} if none
	 * is set already.
	 * <p>If no resolver and no cache manager are set at the operation level, and no cache
	 * resolver is set via {@link #cacheResolver}, this one is used instead of the default.
	 *
	 * @see org.springframework.cache.interceptor.SimpleCacheResolver
	 */
	/**
	 * 自定义{@link org.springframework.cache.CacheManager}
	 * 如果没有，创建默认{@link org.springframework.cache.interceptor.CacheResolver}
	 * 已经设置好了。
	 * <p>，如果在操作级别没有设置解析器和缓存管理器，且没有缓存
	 * 解析器是通过{@link #cacheResolver}设置的，使用的是这个而不是默认值。
	 *
	 * @see org.springframework.cache.interceptor.SimpleCacheResolver
	 */
	String cacheManager() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.interceptor.CacheResolver} to use.
	 * <p>If no resolver and no cache manager are set at the operation level, this one is used
	 * instead of the default.
	 */
	/**
	 * 要使用的自定义{@link org.springframework.cache.interceptor.CacheResolver}的bean名。
	 * <p>如果在操作级别没有设置解析器和缓存管理器，则使用这个
	 * 而不是默认值。
	 */
	String cacheResolver() default "";

}
