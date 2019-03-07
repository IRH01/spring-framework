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

package org.springframework.cache.interceptor;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;
import org.springframework.util.function.SingletonSupplier;

/**
 * A base component for invoking {@link Cache} operations and using a
 * configurable {@link CacheErrorHandler} when an exception occurs.
 * <p>
 * *用于调用{@link Cache}操作和使用A的基本组件
 * *发生异常时可配置{@link CacheErrorHandler}
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @see org.springframework.cache.interceptor.CacheErrorHandler
 * @since 4.1
 */
public abstract class AbstractCacheInvoker {

	protected SingletonSupplier<CacheErrorHandler> errorHandler;


	protected AbstractCacheInvoker() {
		this.errorHandler = SingletonSupplier.of(SimpleCacheErrorHandler::new);
	}

	protected AbstractCacheInvoker(CacheErrorHandler errorHandler) {
		this.errorHandler = SingletonSupplier.of(errorHandler);
	}


	/**
	 * Set the {@link CacheErrorHandler} instance to use to handle errors
	 * thrown by the cache provider. By default, a {@link SimpleCacheErrorHandler}
	 * is used who throws any exception as is.
	 * <p>
	 * *设置{@link CacheErrorHandler}实例来处理错误
	 * *由缓存提供程序抛出。默认情况下，{@link SimpleCacheErrorHandler}
	 * *用于按原样抛出任何异常。
	 */
	public void setErrorHandler(CacheErrorHandler errorHandler) {
		this.errorHandler = SingletonSupplier.of(errorHandler);
	}

	/**
	 * Return the {@link CacheErrorHandler} to use.
	 */
	public CacheErrorHandler getErrorHandler() {
		return this.errorHandler.obtain();
	}


	/**
	 * Execute {@link Cache#get(Object)} on the specified {@link Cache} and
	 * invoke the error handler if an exception occurs. Return {@code null}
	 * if the handler does not throw any exception, which simulates a cache
	 * miss in case of error.
	 * <p>
	 * *在指定的{@link Cache}和上执行{@link Cache#get(Object)}
	 * 如果发生异常，调用错误处理程序。返回{ @code空}
	 * *如果处理程序没有抛出任何异常，则模拟缓存
	 * *出错时未命中。
	 *
	 * @see Cache#get(Object)
	 */
	@Nullable
	protected Cache.ValueWrapper doGet(Cache cache, Object key) {
		try {
			return cache.get(key);
		} catch (RuntimeException ex) {
			getErrorHandler().handleCacheGetError(ex, cache, key);
			return null;  // If the exception is handled, return a cache miss
		}
	}

	/**
	 * Execute {@link Cache#put(Object, Object)} on the specified {@link Cache}
	 * and invoke the error handler if an exception occurs.
	 * <p>
	 * *在指定的{@link Cache}上执行{@link Cache#put(Object, Object)}
	 * *并在异常发生时调用错误处理程序。
	 */
	protected void doPut(Cache cache, Object key, @Nullable Object result) {
		try {
			cache.put(key, result);
		} catch (RuntimeException ex) {
			getErrorHandler().handleCachePutError(ex, cache, key, result);
		}
	}

	/**
	 * Execute {@link Cache#evict(Object)} on the specified {@link Cache} and
	 * invoke the error handler if an exception occurs.
	 * <p>
	 * *在指定的{@link Cache}和上执行{@link Cache#evict(Object)}
	 * 如果发生异常，调用错误处理程序。
	 */
	protected void doEvict(Cache cache, Object key) {
		try {
			cache.evict(key);
		} catch (RuntimeException ex) {
			getErrorHandler().handleCacheEvictError(ex, cache, key);
		}
	}

	/**
	 * Execute {@link Cache#clear()} on the specified {@link Cache} and
	 * invoke the error handler if an exception occurs.
	 */
	protected void doClear(Cache cache) {
		try {
			cache.clear();
		} catch (RuntimeException ex) {
			getErrorHandler().handleCacheClearError(ex, cache);
		}
	}

}
