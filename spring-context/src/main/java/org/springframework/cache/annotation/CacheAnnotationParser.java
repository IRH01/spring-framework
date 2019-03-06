/*
 * Copyright 2002-2018 the original author or authors.
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

import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Strategy interface for parsing known caching annotation types.
 * {@link AnnotationCacheOperationSource} delegates to such parsers
 * for supporting specific annotation types such as Spring's own
 * {@link Cacheable}, {@link CachePut} and{@link CacheEvict}.
 *
 * @author Costin Leau
 * @author Stephane Nicoll
 * @see AnnotationCacheOperationSource
 * @see SpringCacheAnnotationParser
 * @since 3.1
 */

/**
 * 用于解析已知缓存注释类型的策略接口。
 * {@link AnnotationCacheOperationSource}委托给这些解析器
 * 用于支持特定的注释类型，例如Spring自己的注释类型
 * {@link Cacheable}， {@link CachePut} and{@link CacheEvict}.
 * <p>
 * 作者Costin Leau
 * 作者Stephane Nicoll
 *
 * @see AnnotationCacheOperationSource
 * @see SpringCacheAnnotationParser
 * @since 3.1
 */
public interface CacheAnnotationParser {

	/**
	 * Parse the cache definition for the given class,
	 * based on an annotation type understood by this parser.
	 * <p>This essentially parses a known cache annotation into Spring's metadata
	 * attribute class. Returns {@code null} if the class is not cacheable.
	 *
	 * @param type the annotated class
	 * @return the configured caching operation, or {@code null} if none found
	 * @see AnnotationCacheOperationSource#findCacheOperations(Class)
	 */
	/**
	 * 解析给定类的缓存定义，
	 * 基于此解析器理解的注释类型。
	 * 这实际上是将一个已知的缓存注释解析为Spring的元数据
	 * 属性类。如果类不可缓存，返回{@code null}。
	 *
	 * @param输入带注释的类
	 * @返回配置的缓存操作，如果没有找到，则返回{@code null}
	 * @see AnnotationCacheOperationSource # findCacheOperations(类)
	 */
	@Nullable
	Collection<CacheOperation> parseCacheAnnotations(Class<?> type);

	/**
	 * Parse the cache definition for the given method,
	 * based on an annotation type understood by this parser.
	 * <p>This essentially parses a known cache annotation into Spring's metadata
	 * attribute class. Returns {@code null} if the method is not cacheable.
	 *
	 * @param method the annotated method
	 * @return the configured caching operation, or {@code null} if none found
	 * @see AnnotationCacheOperationSource#findCacheOperations(Method)
	 */
	/**
	 * 解析给定方法的缓存定义，
	 * 基于此解析器理解的注释类型。
	 * 这实际上是将一个已知的缓存注释解析为Spring的元数据
	 * 属性类。如果方法不可缓存，返回{@code null}。
	 *
	 * @param方法注释的方法
	 * @返回配置的缓存操作，如果没有找到，则返回{@code null}
	 * @see AnnotationCacheOperationSource # findCacheOperations(方法)
	 */
	@Nullable
	Collection<CacheOperation> parseCacheAnnotations(Method method);

}
