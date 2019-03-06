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

package org.springframework.cache.support;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

/**
 * Common base class for {@link Cache} implementations that need to adapt
 * {@code null} values (and potentially other such special values) before
 * passing them on to the underlying store.
 *
 * <p>Transparently replaces given {@code null} user values with an internal
 * {@link NullValue#INSTANCE}, if configured to support {@code null} values
 * (as indicated by {@link #isAllowNullValues()}.
 *
 * @author Juergen Hoeller
 * @since 4.2.2
 */

/**
 * 需要调整的{@link Cache}实现的公共基类
 * {@code null}之前的值(可能还有其他这样的特殊值)
 * 将它们传递到底层存储。
 *
 * <p>透明地将给定的{@code null}用户值替换为内部值
 * {@link NullValue#INSTANCE}，如果配置为支持{@code null}值
 * (如{@link #isAllowNullValues()}所示。
 * <p>
 * 作者Juergen Hoeller
 *
 * @since 4.2.2
 */
public abstract class AbstractValueAdaptingCache implements Cache {

	private final boolean allowNullValues;


	/**
	 * Create an {@code AbstractValueAdaptingCache} with the given setting.
	 *
	 * @param allowNullValues whether to allow for {@code null} values
	 */
	protected AbstractValueAdaptingCache(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}


	/**
	 * Return whether {@code null} values are allowed in this cache.
	 */
	public final boolean isAllowNullValues() {
		return this.allowNullValues;
	}

	@Override
	@Nullable
	public ValueWrapper get(Object key) {
		Object value = lookup(key);
		return toValueWrapper(value);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T get(Object key, @Nullable Class<T> type) {
		Object value = fromStoreValue(lookup(key));
		if (value != null && type != null && !type.isInstance(value)) {
			throw new IllegalStateException(
					"Cached value is not of required type [" + type.getName() + "]: " + value);
		}
		return (T) value;
	}

	/**
	 * Perform an actual lookup in the underlying store.
	 * 在底层存储中执行实际的查找。
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the raw store value for the key, or {@code null} if none
	 */
	@Nullable
	protected abstract Object lookup(Object key);


	/**
	 * Convert the given value from the internal store to a user value
	 * returned from the get method (adapting {@code null}).
	 *
	 * @param storeValue the store value
	 * @return the value to return to the user
	 */
	/**
	 * 将给定的值从内部存储转换为用户值
	 * 从get方法返回(自适应{@code null})。
	 *
	 * @param storeValue 返回要返回给用户的值
	 */
	@Nullable
	protected Object fromStoreValue(@Nullable Object storeValue) {
		if (this.allowNullValues && storeValue == NullValue.INSTANCE) {
			return null;
		}
		return storeValue;
	}

	/**
	 * Convert the given user value, as passed into the put method,
	 * to a value in the internal store (adapting {@code null}).
	 *
	 * @param userValue the given user value
	 * @return the value to store
	 */
	/**
	 * 将给定的用户值转换为传入put方法的值，
	 * 到内部存储中的一个值(调整{@code null})。
	 *
	 * @param userValue 指定的用户值
	 * @返回要存储的值
	 */
	protected Object toStoreValue(@Nullable Object userValue) {
		if (userValue == null) {
			if (this.allowNullValues) {
				return NullValue.INSTANCE;
			}
			throw new IllegalArgumentException(
					"Cache '" + getName() + "' is configured to not allow null values but null was provided");
		}
		return userValue;
	}

	/**
	 * Wrap the given store value with a {@link SimpleValueWrapper}, also going
	 * through {@link #fromStoreValue} conversion. Useful for {@link #get(Object)}
	 * and {@link #putIfAbsent(Object, Object)} implementations.
	 *
	 * @param storeValue the original value
	 * @return the wrapped value
	 */
	/**
	 * 使用{@link SimpleValueWrapper}包装给定的存储值
	 * 通过{@link #fromStoreValue}转换。用于{@link #get(Object)}
	 * 和{@link #putIfAbsent(Object, Object)}实现。
	 *
	 * @param storeValue 初始值
	 * @返回包装的值
	 */
	@Nullable
	protected Cache.ValueWrapper toValueWrapper(@Nullable Object storeValue) {
		return (storeValue != null ? new SimpleValueWrapper(fromStoreValue(storeValue)) : null);
	}


}
