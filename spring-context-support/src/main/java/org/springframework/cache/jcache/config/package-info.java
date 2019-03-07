/**
 * Support package for declarative JSR-107 caching configuration. Used
 * by the regular Spring's caching configuration when it detects the
 * JSR-107 API and Spring's JCache implementation.
 *
 * <p>Provide an extension of the {@code CachingConfigurer} that exposes
 * the exception cache resolver to use, see {@code JCacheConfigurer}.
 * <p>
 * 支持声明性JSR-107缓存配置包。使用
 * *当它检测到
 * * JSR-107 API和Spring的JCache实现。
 * *
 * * <p>提供了公开的{@code CachingConfigurer}的扩展名
 * *要使用的异常缓存解析器，请参见{@code JCacheConfigurer}。
 */
@NonNullApi
@NonNullFields
package org.springframework.cache.jcache.config;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
