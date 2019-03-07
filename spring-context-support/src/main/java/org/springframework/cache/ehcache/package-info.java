/**
 * Support classes for the open source cache
 * <a href="http://ehcache.sourceforge.net">EhCache 2.x</a>,
 * allowing to set up an EhCache CacheManager and Caches
 * as beans in a Spring context.
 *
 * <p>Note: EhCache 3.x lives in a different package namespace
 * and is not covered by the traditional support classes here.
 * Instead, consider using it through JCache (JSR-107), with
 * Spring's support in {@code org.springframework.cache.jcache}.
 * <p>
 * *支持开放源码缓存的类
 * * <a href="http://ehcache.sourceforge.net">EhCache 2.x</a>，
 * *允许设置EhCache缓存管理器和缓存
 * *作为Spring上下文中的bean。
 * *
 * * <p>注:EhCache 3。x位于不同的包名称空间中
 * 并且不包括在这里的传统支持类中。
 * 相反，考虑通过JCache (JSR-107) with使用它
 * * Spring在{@code org.springframework.cache.jcache}中的支持。
 */
@NonNullApi
@NonNullFields
package org.springframework.cache.ehcache;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
