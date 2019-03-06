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

package org.springframework.cache.concurrent;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentMap;

/**
 * {@link FactoryBean} for easy configuration of a {@link ConcurrentMapCache}
 * when used within a Spring container. Can be configured through bean properties;
 * uses the assigned Spring bean name as the default cache name.
 *
 * <p>Useful for testing or simple caching scenarios, typically in combination
 * with {@link org.springframework.cache.support.SimpleCacheManager} or
 * dynamically through {@link ConcurrentMapCacheManager}.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @since 3.1
 */

/**
 * {@link FactoryBean}方便配置{@link ConcurrentMapCache}
 * 在Spring容器中使用时。可以通过bean属性配置;
 * 使用指定的Spring bean名称作为默认缓存名称。
 *
 * <p>用于测试或简单的缓存场景，通常结合使用
 * 具有{@link org.springframework.cache.support。SimpleCacheManager}或
 * 动态通过{@link ConcurrentMapCacheManager}。
 * <p>
 * 作者Costin Leau
 * 作者Juergen Hoeller
 *
 * @since 3.1
 */
public class ConcurrentMapCacheFactoryBean
		implements FactoryBean<ConcurrentMapCache>, BeanNameAware, InitializingBean {

	private String name = "";

	@Nullable
	private ConcurrentMap<Object, Object> store;

	private boolean allowNullValues = true;

	@Nullable
	private ConcurrentMapCache cache;


	/**
	 * Specify the name of the cache.
	 * 指定缓存的名称。
	 * <p>Default is "" (empty String).
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Specify the ConcurrentMap to use as an internal store
	 * 指定要用作内部存储的ConcurrentMap
	 * (possibly pre-populated).
	 * (可能是预填充)。
	 * <p>Default is a standard {@link java.util.concurrent.ConcurrentHashMap}.
	 */
	public void setStore(ConcurrentMap<Object, Object> store) {
		this.store = store;
	}

	/**
	 * Set whether to allow {@code null} values
	 * (adapting them to an internal null holder value).
	 * <p>Default is "true".
	 */
	/**
	 * 设置是否允许{@code null}值
	 * (将它们调整为内部null holder值)。
	 * <p>默认为“true”。
	 */
	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	@Override
	public void setBeanName(String beanName) {
		if (!StringUtils.hasLength(this.name)) {
			setName(beanName);
		}
	}

	@Override
	public void afterPropertiesSet() {
		this.cache = (this.store != null ? new ConcurrentMapCache(this.name, this.store, this.allowNullValues) :
				new ConcurrentMapCache(this.name, this.allowNullValues));
	}


	@Override
	@Nullable
	public ConcurrentMapCache getObject() {
		return this.cache;
	}

	@Override
	public Class<?> getObjectType() {
		return ConcurrentMapCache.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
