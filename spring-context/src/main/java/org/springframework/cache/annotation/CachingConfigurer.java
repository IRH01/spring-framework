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

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.Nullable;

/**
 * Interface to be implemented by @{@link org.springframework.context.annotation.Configuration
 * Configuration} classes annotated with @{@link EnableCaching} that wish or need to
 * specify explicitly how caches are resolved and how keys are generated for annotation-driven
 * cache management. Consider extending {@link CachingConfigurerSupport}, which provides a
 * stub implementation of all interface methods.
 *
 * <p>See @{@link EnableCaching} for general examples and context; see
 * {@link #cacheManager()}, {@link #cacheResolver()} and {@link #keyGenerator()}
 * for detailed instructions.
 *
 * @author Chris Beams
 * @author Stephane Nicoll
 * @see EnableCaching
 * @see CachingConfigurerSupport
 * @since 3.1
 */

/**
 * 接口由@{@link org.springframework.context.annotation.Configuration
 * 用@{@link EnableCaching}注释的类，它希望或需要这样做
 * 明确指定如何解析缓存以及如何为注释驱动生成键
 * 缓存管理。考虑扩展{@link CachingConfigurerSupport}，它提供了
 * 所有接口方法的存根实现。
 *
 * <p>一般示例和上下文请参见@{@link EnableCaching};看到
 * {@link #cacheManager()}、{@link #cacheResolver()}和{@link #keyGenerator()}
 * 获取详细说明。
 * <p>
 * 作者Chris beam
 * 作者Stephane Nicoll
 *
 * @see EnableCaching
 * @see CachingConfigurerSupport
 * @since 3.1
 */
public interface CachingConfigurer {

	/**
	 * Return the cache manager bean to use for annotation-driven cache
	 * management. A default {@link CacheResolver} will be initialized
	 * behind the scenes with this cache manager. For more fine-grained
	 * management of the cache resolution, consider setting the
	 * {@link CacheResolver} directly.
	 * <p>Implementations must explicitly declare
	 * {@link org.springframework.context.annotation.Bean @Bean}, e.g.
	 * <pre class="code">
	 * &#064;Configuration
	 * &#064;EnableCaching
	 * public class AppConfig extends CachingConfigurerSupport {
	 *     &#064;Bean // important!
	 *     &#064;Override
	 *     public CacheManager cacheManager() {
	 *         // configure and return CacheManager instance
	 *     }
	 *     // ...
	 * }
	 * </pre>
	 * See @{@link EnableCaching} for more complete examples.
	 */
	/**
	 * 返回用于注释驱动缓存的缓存管理器bean
	 * 管理。将初始化默认的{@link CacheResolver}
	 * 在后台使用这个缓存管理器。更细粒度
	 * 管理缓存解析，考虑设置
	 * 直接* {@link CacheResolver}
	 * <p>实现必须显式声明
	 * { @link org.springframework.context.annotation。@ Bean } Bean。
	 * < pre类=“代码”>
	 * & # 064;配置
	 * & # 064;EnableCaching
	 * 公共类AppConfig扩展了CachingConfigurerSupport {
	 * Bean //很重要!
	 * & # 064;覆盖
	 * 公共CacheManager () {
	 * //配置并返回CacheManager实例
	 * }
	 * / /……
	 * }
	 * < / pre >
	 * 参见@{@link EnableCaching}获取更完整的示例。
	 */
	@Nullable
	CacheManager cacheManager();

	/**
	 * Return the {@link CacheResolver} bean to use to resolve regular caches for
	 * annotation-driven cache management. This is an alternative and more powerful
	 * option of specifying the {@link CacheManager} to use.
	 * <p>If both a {@link #cacheManager()} and {@code #cacheResolver()} are set,
	 * the cache manager is ignored.
	 * <p>Implementations must explicitly declare
	 * {@link org.springframework.context.annotation.Bean @Bean}, e.g.
	 * <pre class="code">
	 * &#064;Configuration
	 * &#064;EnableCaching
	 * public class AppConfig extends CachingConfigurerSupport {
	 *     &#064;Bean // important!
	 *     &#064;Override
	 *     public CacheResolver cacheResolver() {
	 *         // configure and return CacheResolver instance
	 *     }
	 *     // ...
	 * }
	 * </pre>
	 * See {@link EnableCaching} for more complete examples.
	 */
	/**
	 * 返回用于解析常规缓存的{@link CacheResolver} bean
	 * 注释驱动的缓存管理。这是一种更强大的替代方案
	 * 指定要使用的{@link CacheManager}选项。
	 * <p>如果同时设置{@link #cacheManager()}和{@code #cacheResolver()}，
	 * 缓存管理器被忽略。
	 * <p>实现必须显式声明
	 * { @link org.springframework.context.annotation。@ Bean } Bean。
	 * < pre类=“代码”>
	 * & # 064;配置
	 * & # 064;EnableCaching
	 * 公共类AppConfig扩展了CachingConfigurerSupport {
	 * Bean //很重要!
	 * & # 064;覆盖
	 * public CacheResolver () {
	 * //配置并返回CacheResolver实例
	 * }
	 * / /……
	 * }
	 * < / pre >
	 * 参见{@link EnableCaching}获得更完整的示例。
	 */
	@Nullable
	CacheResolver cacheResolver();

	/**
	 * Return the key generator bean to use for annotation-driven cache management.
	 * Implementations must explicitly declare
	 * {@link org.springframework.context.annotation.Bean @Bean}, e.g.
	 * <pre class="code">
	 * &#064;Configuration
	 * &#064;EnableCaching
	 * public class AppConfig extends CachingConfigurerSupport {
	 *     &#064;Bean // important!
	 *     &#064;Override
	 *     public KeyGenerator keyGenerator() {
	 *         // configure and return KeyGenerator instance
	 *     }
	 *     // ...
	 * }
	 * </pre>
	 * See @{@link EnableCaching} for more complete examples.
	 */
	/**
	 * 返回用于注释驱动的缓存管理的密钥生成器bean。
	 * 实现必须显式声明
	 * { @link org.springframework.context.annotation。@ Bean } Bean。
	 * < pre类=“代码”>
	 * & # 064;配置
	 * & # 064;EnableCaching
	 * 公共类AppConfig扩展了CachingConfigurerSupport {
	 * Bean //很重要!
	 * & # 064;覆盖
	 * 公共密钥生成器密钥生成器(){
	 * //配置并返回KeyGenerator实例
	 * }
	 * / /……
	 * }
	 * < / pre >
	 * 参见@{@link EnableCaching}获取更完整的示例。
	 */
	@Nullable
	KeyGenerator keyGenerator();

	/**
	 * Return the {@link CacheErrorHandler} to use to handle cache-related errors.
	 * <p>By default,{@link org.springframework.cache.interceptor.SimpleCacheErrorHandler}
	 * is used and simply throws the exception back at the client.
	 * <p>Implementations must explicitly declare
	 * {@link org.springframework.context.annotation.Bean @Bean}, e.g.
	 * <pre class="code">
	 * &#064;Configuration
	 * &#064;EnableCaching
	 * public class AppConfig extends CachingConfigurerSupport {
	 *     &#064;Bean // important!
	 *     &#064;Override
	 *     public CacheErrorHandler errorHandler() {
	 *         // configure and return CacheErrorHandler instance
	 *     }
	 *     // ...
	 * }
	 * </pre>
	 * See @{@link EnableCaching} for more complete examples.
	 */
	/**
	 * 返回用于处理缓存相关错误的{@link CacheErrorHandler}。
	 * <p>默认为{@link org.springframework.cache.interceptor.SimpleCacheErrorHandler}
	 * 使用*，并简单地将异常返回给客户端。
	 * <p>实现必须显式声明
	 * { @link org.springframework.context.annotation。@ Bean } Bean。
	 * < pre类=“代码”>
	 * & # 064;配置
	 * & # 064;EnableCaching
	 * 公共类AppConfig扩展了CachingConfigurerSupport {
	 * Bean //很重要!
	 * & # 064;覆盖
	 * public CacheErrorHandler () {
	 * //配置并返回CacheErrorHandler实例
	 * }
	 * / /……
	 * }
	 * < / pre >
	 * 参见@{@link EnableCaching}获取更完整的示例。
	 */
	@Nullable
	CacheErrorHandler errorHandler();

}
