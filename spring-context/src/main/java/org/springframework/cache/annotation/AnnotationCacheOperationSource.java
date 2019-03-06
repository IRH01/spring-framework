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

import org.springframework.cache.interceptor.AbstractFallbackCacheOperationSource;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implementation of the {@link org.springframework.cache.interceptor.CacheOperationSource
 * CacheOperationSource} interface for working with caching metadata in annotation format.
 *
 * <p>This class reads Spring's {@link Cacheable}, {@link CachePut} and {@link CacheEvict}
 * annotations and exposes corresponding caching operation definition to Spring's cache
 * infrastructure. This class may also serve as base class for a custom
 * {@code CacheOperationSource}.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @since 3.1
 */

/**
 * {@link org.springframe .cache.interceptor. cacheoperationsource的实现
 * 接口，用于以注释格式缓存元数据。
 *
 * <p>这个类读取Spring的{@link Cacheable}， {@link CachePut}和{@link cache驱逐t}
 * 注释并向Spring的缓存公开相应的缓存操作定义
 * 基础设施。这个类也可以作为定制的基类
 * { @code CacheOperationSource }。
 * <p>
 * 作者Costin Leau
 * 作者Juergen Hoeller
 * 作者Stephane Nicoll
 *
 * @since 3.1
 */
@SuppressWarnings("serial")
public class AnnotationCacheOperationSource extends AbstractFallbackCacheOperationSource implements Serializable {

	private final boolean publicMethodsOnly;

	private final Set<CacheAnnotationParser> annotationParsers;


	/**
	 * Create a default AnnotationCacheOperationSource, supporting public methods
	 * that carry the {@code Cacheable} and {@code CacheEvict} annotations.
	 */
	/**
	 * 创建默认的AnnotationCacheOperationSource，支持公共方法
	 * 带有{@code cacheeable}和{@code cache驱逐t}注释。
	 */
	public AnnotationCacheOperationSource() {
		this(true);
	}

	/**
	 * Create a default {@code AnnotationCacheOperationSource}, supporting public methods
	 * that carry the {@code Cacheable} and {@code CacheEvict} annotations.
	 *
	 * @param publicMethodsOnly whether to support only annotated public methods
	 *                          typically for use with proxy-based AOP), or protected/private methods as well
	 *                          (typically used with AspectJ class weaving)
	 */
	/**
	 * 创建默认的{@code AnnotationCacheOperationSource}，支持公共方法
	 * 带有{@code cacheeable}和{@code cache驱逐t}注释。
	 * <p>
	 * 是否只支持带注释的公共方法
	 * 通常用于基于代理的AOP)，或者也用于受保护/私有方法
	 * (通常与AspectJ类编织一起使用)
	 */
	public AnnotationCacheOperationSource(boolean publicMethodsOnly) {
		this.publicMethodsOnly = publicMethodsOnly;
		this.annotationParsers = Collections.singleton(new SpringCacheAnnotationParser());
	}

	/**
	 * Create a custom AnnotationCacheOperationSource.
	 * 创建一个定制的AnnotationCacheOperationSource。
	 *
	 * @param annotationParser the CacheAnnotationParser to use
	 */
	public AnnotationCacheOperationSource(CacheAnnotationParser annotationParser) {
		this.publicMethodsOnly = true;
		Assert.notNull(annotationParser, "CacheAnnotationParser must not be null");
		this.annotationParsers = Collections.singleton(annotationParser);
	}

	/**
	 * Create a custom AnnotationCacheOperationSource.
	 * 创建一个定制的AnnotationCacheOperationSource。
	 *
	 * @param annotationParsers the CacheAnnotationParser to use
	 */
	public AnnotationCacheOperationSource(CacheAnnotationParser... annotationParsers) {
		this.publicMethodsOnly = true;
		Assert.notEmpty(annotationParsers, "At least one CacheAnnotationParser needs to be specified");
		this.annotationParsers = new LinkedHashSet<>(Arrays.asList(annotationParsers));
	}

	/**
	 * Create a custom AnnotationCacheOperationSource.
	 * 创建一个定制的AnnotationCacheOperationSource。
	 *
	 * @param annotationParsers the CacheAnnotationParser to use
	 */
	public AnnotationCacheOperationSource(Set<CacheAnnotationParser> annotationParsers) {
		this.publicMethodsOnly = true;
		Assert.notEmpty(annotationParsers, "At least one CacheAnnotationParser needs to be specified");
		this.annotationParsers = annotationParsers;
	}


	@Override
	@Nullable
	protected Collection<CacheOperation> findCacheOperations(Class<?> clazz) {
		return determineCacheOperations(parser -> parser.parseCacheAnnotations(clazz));
	}

	@Override
	@Nullable
	protected Collection<CacheOperation> findCacheOperations(Method method) {
		return determineCacheOperations(parser -> parser.parseCacheAnnotations(method));
	}

	/**
	 * Determine the cache operation(s) for the given {@link CacheOperationProvider}.
	 * <p>This implementation delegates to configured
	 * {@link CacheAnnotationParser CacheAnnotationParsers}
	 * for parsing known annotations into Spring's metadata attribute class.
	 * <p>Can be overridden to support custom annotations that carry caching metadata.
	 *
	 * @param provider the cache operation provider to use
	 * @return the configured caching operations, or {@code null} if none found
	 */
	/**
	 * 确定给定{@link CacheOperationProvider}的缓存操作。
	 * <p>此实现委托给已配置
	 * {@link CacheAnnotationParser CacheAnnotationParser}
	 * 用于将已知注释解析为Spring的元数据属性类。
	 * <p>可以被覆盖，以支持携带缓存元数据的自定义注释。
	 *
	 * @param提供要使用的缓存操作提供程序
	 * @返回配置的缓存操作，如果没有找到，则返回{@code null}
	 */
	@Nullable
	protected Collection<CacheOperation> determineCacheOperations(CacheOperationProvider provider) {
		Collection<CacheOperation> ops = null;
		for (CacheAnnotationParser annotationParser : this.annotationParsers) {
			Collection<CacheOperation> annOps = provider.getCacheOperations(annotationParser);
			if (annOps != null) {
				if (ops == null) {
					ops = annOps;
				} else {
					Collection<CacheOperation> combined = new ArrayList<>(ops.size() + annOps.size());
					combined.addAll(ops);
					combined.addAll(annOps);
					ops = combined;
				}
			}
		}
		return ops;
	}

	/**
	 * By default, only public methods can be made cacheable.
	 * 默认情况下，只有公共方法可以缓存。
	 */
	@Override
	protected boolean allowPublicMethodsOnly() {
		return this.publicMethodsOnly;
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnnotationCacheOperationSource)) {
			return false;
		}
		AnnotationCacheOperationSource otherCos = (AnnotationCacheOperationSource) other;
		return (this.annotationParsers.equals(otherCos.annotationParsers) &&
				this.publicMethodsOnly == otherCos.publicMethodsOnly);
	}

	@Override
	public int hashCode() {
		return this.annotationParsers.hashCode();
	}


	/**
	 * Callback interface providing {@link CacheOperation} instance(s) based on
	 * a given {@link CacheAnnotationParser}.
	 */
	/**
	 * 回调接口提供{@link CacheOperation}实例的基础上
	 * 一个给定的{@link CacheAnnotationParser}。
	 */
	@FunctionalInterface
	protected interface CacheOperationProvider {

		/**
		 * Return the {@link CacheOperation} instance(s) provided by the specified parser.
		 *
		 * @param parser the parser to use
		 * @return the cache operations, or {@code null} if none found
		 */
		/**
		 * 返回指定解析器提供的{@link CacheOperation}实例。
		 *
		 * @param解析器要使用的解析器
		 * @返回缓存操作，如果没有找到，则返回{@code null}
		 */
		@Nullable
		Collection<CacheOperation> getCacheOperations(CacheAnnotationParser parser);
	}

}
