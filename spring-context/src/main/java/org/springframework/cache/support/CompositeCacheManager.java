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

import java.util.*;

/**
 * Composite {@link CacheManager} implementation that iterates over
 * a given collection of delegate {@link CacheManager} instances.
 *
 * <p>Allows {@link NoOpCacheManager} to be automatically added to the end of
 * the list for handling cache declarations without a backing store. Otherwise,
 * any custom {@link CacheManager} may play that role of the last delegate as
 * well, lazily creating cache regions for any requested name.
 *
 * <p>Note: Regular CacheManagers that this composite manager delegates to need
 * to return {@code null} from {@link #getCache(String)} if they are unaware of
 * the specified cache name, allowing for iteration to the next delegate in line.
 * However, most {@link CacheManager} implementations fall back to lazy creation
 * of named caches once requested; check out the specific configuration details
 * for a 'static' mode with fixed cache names, if available.
 * <p>
 * *重复的复合{@link CacheManager}实现
 * 一个给定的委托集合{@link CacheManager}实例。
 * *
 * * <p>允许{@link NoOpCacheManager}自动添加到末尾
 * *处理没有备份存储的缓存声明的列表。否则,
 * *任何自定义{@link CacheManager}都可以扮演最后一个委托的角色
 * 为任何请求的名称延迟创建缓存区域。
 * *
 * * <p>注意:此组合管理器委托需要的常规缓存管理器
 * *返回{@code null}从{@link #getCache(String)}如果他们不知道
 * *指定的缓存名称，允许对行中的下一个委托进行迭代。
 * 但是，大多数{@link CacheManager}实现会退回到惰性创建
 * *一旦被要求，指定的缓存;查看特定的配置细节
 * *适用于“静态”模式，并有固定的快取名称(如适用)。
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @see #setFallbackToNoOpCache
 * @see org.springframework.cache.concurrent.ConcurrentMapCacheManager#setCacheNames
 * @since 3.1
 */
public class CompositeCacheManager implements CacheManager, InitializingBean {

	private final List<CacheManager> cacheManagers = new ArrayList<>();

	private boolean fallbackToNoOpCache = false;


	/**
	 * Construct an empty CompositeCacheManager, with delegate CacheManagers to
	 * be added via the {@link #setCacheManagers "cacheManagers"} property.
	 * <p>
	 * *构造一个空的CompositeCacheManager，将cachemanager委托给它
	 * *通过{@link # setcachemanager " cachemanager "}属性添加。
	 */
	public CompositeCacheManager() {
	}

	/**
	 * Construct a CompositeCacheManager from the given delegate CacheManagers.
	 * <p>
	 * 从给定的委托缓存管理器构造一个CompositeCacheManager。
	 *
	 * @param cacheManagers the CacheManagers to delegate to
	 */
	public CompositeCacheManager(CacheManager... cacheManagers) {
		setCacheManagers(Arrays.asList(cacheManagers));
	}


	/**
	 * Specify the CacheManagers to delegate to.
	 * 指定要委托给的cachemanager。
	 */
	public void setCacheManagers(Collection<CacheManager> cacheManagers) {
		this.cacheManagers.addAll(cacheManagers);
	}

	/**
	 * Indicate whether a {@link NoOpCacheManager} should be added at the end of the delegate list.
	 * In this case, any {@code getCache} requests not handled by the configured CacheManagers will
	 * be automatically handled by the {@link NoOpCacheManager} (and hence never return {@code null}).
	 * <p>
	 * *指示是否在委托列表的末尾添加{@link NoOpCacheManager}。
	 * *在这种情况下，任何未被配置的cachemanager处理的{@code getCache}请求都将被处理
	 * *由{@link NoOpCacheManager}自动处理(因此永远不会返回{@code null})。
	 */
	public void setFallbackToNoOpCache(boolean fallbackToNoOpCache) {
		this.fallbackToNoOpCache = fallbackToNoOpCache;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.fallbackToNoOpCache) {
			this.cacheManagers.add(new NoOpCacheManager());
		}
	}


	@Override
	@Nullable
	public Cache getCache(String name) {
		for (CacheManager cacheManager : this.cacheManagers) {
			Cache cache = cacheManager.getCache(name);
			if (cache != null) {
				return cache;
			}
		}
		return null;
	}

	@Override
	public Collection<String> getCacheNames() {
		Set<String> names = new LinkedHashSet<>();
		for (CacheManager manager : this.cacheManagers) {
			names.addAll(manager.getCacheNames());
		}
		return Collections.unmodifiableSet(names);
	}

}
