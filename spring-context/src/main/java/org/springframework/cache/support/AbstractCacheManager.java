/*
 * Copyright 2002-2016 the original author or authors.
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

package org.springframework.cache.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Abstract base class implementing the common {@link CacheManager} methods.
 * Useful for 'static' environments where the backing caches do not change.
 * <p>
 * 抽象基类实现公共的{@link CacheManager}方法。
 * *适用于后台缓存不变的“静态”环境。
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @since 3.1
 */
public abstract class AbstractCacheManager implements CacheManager, InitializingBean {

	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

	private volatile Set<String> cacheNames = Collections.emptySet();


	// Early cache initialization on startup

	@Override
	public void afterPropertiesSet() {
		initializeCaches();
	}

	/**
	 * Initialize the static configuration of caches.
	 * <p>Triggered on startup through {@link #afterPropertiesSet()};
	 * can also be called to re-initialize at runtime.
	 * <p>
	 * *初始化缓存的静态配置。
	 * * <p>在启动时通过{@link #afterPropertiesSet()}触发;
	 * *也可以调用在运行时重新初始化。
	 *
	 * @see #loadCaches()
	 * @since 4.2.2
	 */
	public void initializeCaches() {
		Collection<? extends Cache> caches = loadCaches();

		synchronized (this.cacheMap) {
			this.cacheNames = Collections.emptySet();
			this.cacheMap.clear();
			Set<String> cacheNames = new LinkedHashSet<>(caches.size());
			for (Cache cache : caches) {
				String name = cache.getName();
				this.cacheMap.put(name, decorateCache(cache));
				cacheNames.add(name);
			}
			this.cacheNames = Collections.unmodifiableSet(cacheNames);
		}
	}

	/**
	 * Load the initial caches for this cache manager.
	 * <p>Called by {@link #afterPropertiesSet()} on startup.
	 * The returned collection may be empty but must not be {@code null}.
	 * <p>
	 * / * *
	 * 加载此缓存管理器的初始缓存。
	 * * <p>在启动时由{@link #afterPropertiesSet()}调用。
	 * *返回的集合可以是空的，但不能是{@code null}。
	 * * /
	 */
	protected abstract Collection<? extends Cache> loadCaches();


	// Lazy cache initialization on access

	@Override
	@Nullable
	public Cache getCache(String name) {
		Cache cache = this.cacheMap.get(name);
		if (cache != null) {
			return cache;
		} else {
			// Fully synchronize now for missing cache creation...
			synchronized (this.cacheMap) {
				cache = this.cacheMap.get(name);
				if (cache == null) {
					cache = getMissingCache(name);
					if (cache != null) {
						cache = decorateCache(cache);
						this.cacheMap.put(name, cache);
						updateCacheNames(name);
					}
				}
				return cache;
			}
		}
	}

	@Override
	public Collection<String> getCacheNames() {
		return this.cacheNames;
	}


	// Common cache initialization delegates for subclasses

	/**
	 * Check for a registered cache of the given name.
	 * In contrast to {@link #getCache(String)}, this method does not trigger
	 * the lazy creation of missing caches via {@link #getMissingCache(String)}.
	 *
	 * @param name the cache identifier (must not be {@code null})
	 * @return the associated Cache instance, or {@code null} if none found
	 * @see #getCache(String)
	 * @see #getMissingCache(String)
	 * @since 4.1
	 * / * *
	 * *检查给定名称的注册缓存。
	 * 与{@link #getCache(String)}相反，这个方法不触发
	 * *通过{@link #getMissingCache(String)}延迟创建丢失的缓存。
	 * *
	 * * @param指定缓存标识符(不能是{@code null})
	 * * @返回关联的缓存实例，如果没有找到，则返回{@code null}
	 * * @see # getCache(字符串)
	 * * @see # getMissingCache(字符串)
	 * * @since 4.1
	 * * /
	 */
	@Nullable
	protected final Cache lookupCache(String name) {
		return this.cacheMap.get(name);
	}

	/**
	 * Dynamically register an additional Cache with this manager.
	 * <p>
	 * 动态地向这个管理器注册一个额外的缓存。
	 *
	 * @param cache the Cache to register
	 * @deprecated as of Spring 4.3, in favor of {@link #getMissingCache(String)}
	 */
	@Deprecated
	protected final void addCache(Cache cache) {
		String name = cache.getName();
		synchronized (this.cacheMap) {
			if (this.cacheMap.put(name, decorateCache(cache)) == null) {
				updateCacheNames(name);
			}
		}
	}

	/**
	 * Update the exposed {@link #cacheNames} set with the given name.
	 * <p>This will always be called within a full {@link #cacheMap} lock
	 * and effectively behaves like a {@code CopyOnWriteArraySet} with
	 * preserved order but exposed as an unmodifiable reference.
	 * <p>
	 * *使用给定的名称更新公开的{@link #cacheNames}集。
	 * * <p>这将总是在一个完整的{@link #cacheMap}锁中调用
	 * 和{@code CopyOnWriteArraySet}类似
	 * *保留订单，但作为不可修改的参
	 *
	 * @param name the name of the cache to be added
	 */
	private void updateCacheNames(String name) {
		Set<String> cacheNames = new LinkedHashSet<>(this.cacheNames.size() + 1);
		cacheNames.addAll(this.cacheNames);
		cacheNames.add(name);
		this.cacheNames = Collections.unmodifiableSet(cacheNames);
	}


	// Overridable template methods for cache initialization

	/**
	 * Decorate the given Cache object if necessary.
	 * <p>
	 * 必要时装饰给定的缓存对象。
	 *
	 * @param cache the Cache object to be added to this CacheManager
	 * @return the decorated Cache object to be used instead,
	 * or simply the passed-in Cache object by default
	 */
	protected Cache decorateCache(Cache cache) {
		return cache;
	}

	/**
	 * Return a missing cache with the specified {@code name} or {@code null} if
	 * such cache does not exist or could not be created on the fly.
	 * <p>Some caches may be created at runtime if the native provider supports
	 * it. If a lookup by name does not yield any result, a subclass gets a chance
	 * to register such a cache at runtime. The returned cache will be automatically
	 * added to this instance.
	 * <p>
	 * *返回一个指定的{@code name}或{@code null}的缓存
	 * *此类缓存不存在或无法动态创建。
	 * * <p>如果本机提供程序支持，可以在运行时创建一些缓存
	 * *。如果按名称查找不产生任何结果，则子类将获得机会
	 * *在运行时注册这样的缓存。返回的缓存将自动执行
	 * 添加到这个实例。
	 *
	 * @param name the name of the cache to retrieve
	 * @return the missing cache or {@code null} if no such cache exists or could be
	 * created
	 * @see #getCache(String)
	 * @since 4.1
	 */
	@Nullable
	protected Cache getMissingCache(String name) {
		return null;
	}

}
