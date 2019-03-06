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
 * A basic, no operation {@link CacheManager} implementation suitable
 * for disabling caching, typically used for backing cache declarations
 * without an actual backing store.
 *
 * <p>Will simply accept any items into the cache not actually storing them.
 * <p>
 * *一个基本的，没有操作{@link CacheManager}实现合适
 * *用于禁用缓存，通常用于支持缓存声明
 * *没有实际的备份存储。
 * *
 * * <p>只接受缓存中没有实际存储的任何项。
 *
 * @author Costin Leau
 * @author Stephane Nicoll
 * @see CompositeCacheManager
 * @since 3.1
 */
public class NoOpCacheManager implements CacheManager {

	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>(16);

	private final Set<String> cacheNames = new LinkedHashSet<>(16);


	/**
	 * This implementation always returns a {@link Cache} implementation that will not store items.
	 * Additionally, the request cache will be remembered by the manager for consistency.
	 * <p>
	 * / * *
	 * *这个实现总是返回一个{@link Cache}实现，它不会存储条目。
	 * *此外，为了一致性，请求缓存将被管理器记住。
	 * * /
	 */
	@Override
	@Nullable
	public Cache getCache(String name) {
		Cache cache = this.caches.get(name);
		if (cache == null) {
			this.caches.computeIfAbsent(name, key -> new NoOpCache(name));
			synchronized (this.cacheNames) {
				this.cacheNames.add(name);
			}
		}

		return this.caches.get(name);
	}

	/**
	 * This implementation returns the name of the caches previously requested.
	 * <p>
	 * 此实现返回先前请求的缓存的名称。
	 */
	@Override
	public Collection<String> getCacheNames() {
		synchronized (this.cacheNames) {
			return Collections.unmodifiableSet(this.cacheNames);
		}
	}

}
