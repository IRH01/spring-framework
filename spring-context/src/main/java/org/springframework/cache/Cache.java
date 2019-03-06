/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.cache;

import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;

/**
 * Interface that defines common cache operations.
 *
 * <b>Note:</b> Due to the generic use of caching, it is recommended that
 * implementations allow storage of <tt>null</tt> values (for example to
 * cache methods that return {@code null}).
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @since 3.1
 */
public interface Cache {

	/**
	 * Return the cache name.
	 */
	String getName();

	/**
	 * Return the underlying native cache provider.
	 * 返回底层本机缓存提供程序。
	 */
	Object getNativeCache();

	/**
	 * Return the value to which this cache maps the specified key.
	 * <p>Returns {@code null} if the cache contains no mapping for this key;
	 * otherwise, the cached value (which may be {@code null} itself) will
	 * be returned in a {@link ValueWrapper}.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value to which this cache maps the specified key,
	 * contained within a {@link ValueWrapper} which may also hold
	 * a cached {@code null} value. A straight {@code null} being
	 * returned means that the cache contains no mapping for this key.
	 * @see #get(Object, Class)
	 */
	/**
	 * 返回该缓存将指定键映射到的值。
	 * <p>返回{@code null}如果缓存不包含此键的映射;
	 * 否则，缓存的值(可能是{@code null}本身)将
	 * 在{@link ValueWrapper}中返回。
	 * <p>
	 * 返回关联值的键
	 *
	 * @返回该缓存将指定键映射到的值， 包含在{@link ValueWrapper}中，它也可以保存
	 * 缓存的{@code null}值。一个直接的{@code null}存在
	 * 返回意味着缓存不包含此键的映射。
	 * @see #get(Object, Class)
	 */
	@Nullable
	ValueWrapper get(Object key);

	/**
	 * Return the value to which this cache maps the specified key,
	 * generically specifying a type that return value will be cast to.
	 * <p>Note: This variant of {@code get} does not allow for differentiating
	 * between a cached {@code null} value and no cache entry found at all.
	 * Use the standard {@link #get(Object)} variant for that purpose instead.
	 *
	 * @param key  the key whose associated value is to be returned
	 * @param type the required type of the returned value (may be
	 *             {@code null} to bypass a type check; in case of a {@code null}
	 *             value found in the cache, the specified type is irrelevant)
	 * @return the value to which this cache maps the specified key
	 * (which may be {@code null} itself), or also {@code null} if
	 * the cache contains no mapping for this key
	 * @throws IllegalStateException if a cache entry has been found
	 *                               but failed to match the specified type
	 * @see #get(Object)
	 * @since 4.0
	 */
	/**
	 * 返回该缓存将指定键映射到的值，
	 * 一般指定将转换返回值的类型。
	 * <p>注意:这个{@code get}变量不允许微分
	 * 在缓存的{@code null}值和根本没有找到缓存条目之间。
	 * 为此使用标准的{@link #get(Object)}变量。
	 * <p>
	 * 返回关联值的键
	 * 返回值的所需类型(可能是
	 * {@code null}绕过类型检查;如果是{@code null}
	 * 在缓存中找到的值，指定的类型是不相关的)
	 *
	 * @返回该缓存将指定键映射到的值 (可以是 { @ code null } 本身)，也可以是{@code null}(如果)
	 * 缓存不包含此键的映射
	 * 如果找到缓存项，则抛出IllegalStateException
	 * 但未能匹配指定的类型
	 * @see # get(对象)
	 * @since 4.0
	 */
	@Nullable
	<T> T get(Object key, @Nullable Class<T> type);

	/**
	 * Return the value to which this cache maps the specified key, obtaining
	 * that value from {@code valueLoader} if necessary. This method provides
	 * a simple substitute for the conventional "if cached, return; otherwise
	 * create, cache and return" pattern.
	 * <p>If possible, implementations should ensure that the loading operation
	 * is synchronized so that the specified {@code valueLoader} is only called
	 * once in case of concurrent access on the same key.
	 * <p>If the {@code valueLoader} throws an exception, it is wrapped in
	 * a {@link ValueRetrievalException}
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the value to which this cache maps the specified key
	 * @throws ValueRetrievalException if the {@code valueLoader} throws an exception
	 * @since 4.3
	 */
	/**
	 * 返回该缓存将指定键映射到的值，获取
	 * 如果需要，该值来自{@code valueLoader}。这种方法提供了
	 * 一个简单的替代传统的“如果缓存，返回;否则
	 * 创建、缓存和返回“模式”。
	 * <p>如果可能，实现应该确保加载操作
	 * 同步，以便只调用指定的{@code valueLoader}
	 * 在同一密钥上进行并发访问时一次。
	 * <p>如果{@code valueLoader}抛出异常，则将其封装
	 * a {@link ValueRetrievalException}
	 * <p>
	 * 返回关联值的键
	 *
	 * @返回该缓存将指定键映射到的值 如果{@code valueLoader}抛出异常，则@throw ValueRetrievalException
	 * @since 4.3
	 */
	@Nullable
	<T> T get(Object key, Callable<T> valueLoader);

	/**
	 * Associate the specified value with the specified key in this cache.
	 * <p>If the cache previously contained a mapping for this key, the old
	 * value is replaced by the specified value.
	 *
	 * @param key   the key with which the specified value is to be associated
	 * @param value the value to be associated with the specified key
	 */
	/**
	 * 将指定值与此缓存中的指定键关联。
	 * <p>如果缓存之前包含此键的映射，则为旧
	 * 值被指定的值替换。
	 *
	 * @param键指定值与之关联的键
	 * @param值与指定键关联的值
	 */
	void put(Object key, @Nullable Object value);

	/**
	 * Atomically associate the specified value with the specified key in this cache
	 * if it is not set already.
	 * <p>This is equivalent to:
	 * <pre><code>
	 * Object existingValue = cache.get(key);
	 * if (existingValue == null) {
	 *     cache.put(key, value);
	 *     return null;
	 * } else {
	 *     return existingValue;
	 * }
	 * </code></pre>
	 * except that the action is performed atomically. While all out-of-the-box
	 * {@link CacheManager} implementations are able to perform the put atomically,
	 * the operation may also be implemented in two steps, e.g. with a check for
	 * presence and a subsequent put, in a non-atomic way. Check the documentation
	 * of the native cache implementation that you are using for more details.
	 *
	 * @param key   the key with which the specified value is to be associated
	 * @param value the value to be associated with the specified key
	 * @return the value to which this cache maps the specified key (which may be
	 * {@code null} itself), or also {@code null} if the cache did not contain any
	 * mapping for that key prior to this call. Returning {@code null} is therefore
	 * an indicator that the given {@code value} has been associated with the key.
	 * @since 4.1
	 */
	/**
	 * 原子地将指定的值与此缓存中的指定键关联
	 * 如果还没有设置。
	 * <p>等于:
	 * < pre > <代码>
	 * Object existingValue = cache.get(key);
	 * if (existingValue == null) {
	 * 缓存。put(关键字,值);
	 * 返回null;
	 * } else {
	 * 返回existingValue;
	 * }
	 * < /代码> < / pre >
	 * 除了操作是原子执行的。尽管所有开箱即用的
	 * {@link CacheManager}实现能够自动执行put，
	 * 这项工作亦可分两步进行，例如检查
	 * 以非原子方式显示和随后的put。检查文档
	 * 您正在使用的本机缓存实现的详细信息。
	 *
	 * @param键指定值与之关联的键
	 * @param值与指定键关联的值
	 * @返回该缓存将指定键映射到的值(可能是) {@code null}本身)，或者{@code null}(如果缓存不包含任何内容)
	 * 在此调用之前映射该键。因此返回{@code null}
	 * 表示给定的{@code值}已与键关联的指示器。
	 * @since 4.1
	 */
	@Nullable
	ValueWrapper putIfAbsent(Object key, @Nullable Object value);

	/**
	 * Evict the mapping for this key from this cache if it is present.
	 * 如果该键存在，则从该缓存中删除该键的映射。
	 *
	 * @param key the key whose mapping is to be removed from the cache
	 */
	void evict(Object key);

	/**
	 * Remove all mappings from the cache.
	 * 从缓存中删除所有映射。
	 */
	void clear();


	/**
	 * A (wrapper) object representing a cache value.
	 * 表示缓存值的(包装器)对象。
	 */
	@FunctionalInterface
	interface ValueWrapper {

		/**
		 * Return the actual value in the cache.
		 * 返回缓存中的实际值。
		 */
		@Nullable
		Object get();
	}


	/**
	 * Wrapper exception to be thrown from {@link #get(Object, Callable)}
	 * in case of the value loader callback failing with an exception.
	 *
	 * @since 4.3
	 */
	/**
	 * 从{@link #get(Object, Callable)}抛出的包装器异常
	 * 如果值加载器回调失败，异常。
	 *
	 * @since 4.3
	 */
	@SuppressWarnings("serial")
	class ValueRetrievalException extends RuntimeException {

		@Nullable
		private final Object key;

		public ValueRetrievalException(@Nullable Object key, Callable<?> loader, Throwable ex) {
			super(String.format("Value for key '%s' could not be loaded using '%s'", key, loader), ex);
			this.key = key;
		}

		@Nullable
		public Object getKey() {
			return this.key;
		}
	}

}
