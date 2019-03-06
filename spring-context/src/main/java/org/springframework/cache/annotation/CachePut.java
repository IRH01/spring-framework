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

package org.springframework.cache.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Annotation indicating that a method (or all methods on a class) triggers a
 * {@link org.springframework.cache.Cache#put(Object, Object) cache put} operation.
 *
 * <p>In contrast to the {@link Cacheable @Cacheable} annotation, this annotation
 * does not cause the advised method to be skipped. Rather, it always causes the
 * method to be invoked and its result to be stored in the associated cache. Note
 * that Java8's {@code Optional} return types are automatically handled and its
 * content is stored in the cache if present.
 *
 * <p>This annotation may be used as a <em>meta-annotation</em> to create custom
 * <em>composed annotations</em> with attribute overrides.
 *
 * @author Costin Leau
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @author Sam Brannen
 * @see CacheConfig
 * @since 3.1
 */

/**
 * 表示方法(或类上的所有方法)触发a的注释
 * { @link org.springframework.cache。缓存#put(对象，对象)缓存put}操作。
 *
 * <p>与{@link Cacheable @Cacheable}注释相反，这个注释
 * 不会导致跳过建议的方法。相反，它总是导致
 * 方法，并将其结果存储在关联的缓存中。请注意
 * Java8的{@code Optional}返回类型将被自动处理
 * 如果内容存在，则存储在缓存中。
 *
 * <p>此注释可作为<em>元注释</em>创建自定义
 * <em>使用属性覆盖组合注释</em>。
 * <p>
 * 作者Costin Leau
 * 作者Phillip Webb
 * 作者Stephane Nicoll
 * 作者Sam Brannen
 *
 * @see CacheConfig
 * @since 3.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CachePut {

	/**
	 * Alias for {@link #cacheNames}.
	 */
	@AliasFor("cacheNames")
	String[] value() default {};

	/**
	 * Names of the caches to use for the cache put operation.
	 * <p>Names may be used to determine the target cache (or caches), matching
	 * the qualifier value or bean name of a specific bean definition.
	 * @since 4.2
	 * @see #value
	 * @see CacheConfig#cacheNames
	 */
	/**
	 * 用于缓存put操作的缓存的名称。
	 * <p>名称可用于确定目标缓存(或多个缓存)，匹配
	 * 特定bean定义的限定符值或bean名称。
	 *
	 * @see #value()
	 * @see CacheConfig # cacheNames
	 * @since 4.2
	 */
	@AliasFor("value")
	String[] cacheNames() default {};

	/**
	 * Spring Expression Language (SpEL) expression for computing the key dynamically.
	 * <p>Default is {@code ""}, meaning all method parameters are considered as a key,
	 * unless a custom {@link #keyGenerator} has been set.
	 * <p>The SpEL expression evaluates against a dedicated context that provides the
	 * following meta-data:
	 * <ul>
	 * <li>{@code #result} for a reference to the result of the method invocation. For
	 * supported wrappers such as {@code Optional}, {@code #result} refers to the actual
	 * object, not the wrapper</li>
	 * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
	 * references to the {@link java.lang.reflect.Method method}, target object, and
	 * affected cache(s) respectively.</li>
	 * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
	 * ({@code #root.targetClass}) are also available.
	 * <li>Method arguments can be accessed by index. For instance the second argument
	 * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
	 * can also be accessed by name if that information is available.</li>
	 * </ul>
	 */
	/**
	 * 用于动态计算密钥的Spring表达式语言(SpEL)表达式。
	 * <p>默认为{@code ""}，表示将所有方法参数视为键值，
	 * 除非已设置自定义{@link #keyGenerator}。
	 * <p> SpEL表达式根据提供
	 * 元数据:
	 * < ul >
	 * <li>{@code #result}引用方法调用的结果。为
	 * 支持的包装器，如{@code Optional}， {@code #result}引用实际的
	 * 对象，而不是包装器</li>
	 * <李> {@code #根。方法},{ @code #根。目标}和{@code #root。缓存},
	 * 引用{@link java.lang.reflect.Method method}，目标对象，和
	 * 分别影响缓存(s) .</li>
	 * 方法名({@code #root.methodName})和目标类的快捷方式
	 * ({@code #root.targetClass})也可用。
	 * <li>方法参数可以通过索引访问。例如第二个参数
	 * 可以通过{@code #root访问。args[1]}， {@code #p1}或{@code #a1}。参数
	 * 如果信息可用，也可以通过名称访问。</li>
	 * < / ul >
	 */
	String key() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.interceptor.KeyGenerator}
	 * to use.
	 * <p>Mutually exclusive with the {@link #key} attribute.
	 *
	 * @see CacheConfig#keyGenerator
	 */
	/**
	 * 自定义{@link org.springframework.cache.interceptor.KeyGenerator}的bean名
	 * 使用。
	 * <p>与{@link #key}属性互斥。
	 *
	 * @see CacheConfig # keyGenerator
	 */
	String keyGenerator() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.CacheManager} to use to
	 * create a default {@link org.springframework.cache.interceptor.CacheResolver} if none
	 * is set already.
	 * <p>Mutually exclusive with the {@link #cacheResolver} attribute.
	 *
	 * @see org.springframework.cache.interceptor.SimpleCacheResolver
	 * @see CacheConfig#cacheManager
	 */
	/**
	 * 自定义{@link org.springframework.cache.CacheManager}
	 * 如果没有，创建默认{@link org.springframework.cache.interceptor.CacheResolver}
	 * 已经设置好了。
	 * <p>与{@link #cacheResolver}属性互斥。
	 *
	 * @see org.springframework.cache.interceptor.SimpleCacheResolver
	 * @see CacheConfig #缓存管理器
	 */
	String cacheManager() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.interceptor.CacheResolver}
	 * to use.
	 *
	 * @see CacheConfig#cacheResolver
	 */
	/**
	 * 自定义{@link org.springframework.cache.interceptor.CacheResolver}的bean名
	 * 使用。
	 *
	 * @see CacheConfig # cacheResolver
	 */
	String cacheResolver() default "";

	/**
	 * Spring Expression Language (SpEL) expression used for making the cache
	 * put operation conditional.
	 * <p>Default is {@code ""}, meaning the method result is always cached.
	 * <p>The SpEL expression evaluates against a dedicated context that provides the
	 * following meta-data:
	 * <ul>
	 * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
	 * references to the {@link java.lang.reflect.Method method}, target object, and
	 * affected cache(s) respectively.</li>
	 * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
	 * ({@code #root.targetClass}) are also available.
	 * <li>Method arguments can be accessed by index. For instance the second argument
	 * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
	 * can also be accessed by name if that information is available.</li>
	 * </ul>
	 */
	/**
	 * 用于创建缓存的Spring表达式语言(SpEL)表达式
	 * put操作有条件。
	 * <p>默认值是{@code ""}，这意味着方法结果总是被缓存。
	 * <p> SpEL表达式根据提供
	 * 元数据:
	 * < ul >
	 * <李> {@code #根。方法},{ @code #根。目标}和{@code #root。缓存},
	 * 引用{@link java.lang.reflect.Method method}，目标对象，和
	 * 分别影响缓存(s) .</li>
	 * 方法名({@code #root.methodName})和目标类的快捷方式
	 * ({@code #root.targetClass})也可用。
	 * <li>方法参数可以通过索引访问。例如第二个参数
	 * 可以通过{@code #root访问。args[1]}， {@code #p1}或{@code #a1}。参数
	 * 如果信息可用，也可以通过名称访问。</li>
	 * < / ul >
	 */
	String condition() default "";

	/**
	 * Spring Expression Language (SpEL) expression used to veto the cache put operation.
	 * <p>Unlike {@link #condition}, this expression is evaluated after the method
	 * has been called and can therefore refer to the {@code result}.
	 * <p>Default is {@code ""}, meaning that caching is never vetoed.
	 * <p>The SpEL expression evaluates against a dedicated context that provides the
	 * following meta-data:
	 * <ul>
	 * <li>{@code #result} for a reference to the result of the method invocation. For
	 * supported wrappers such as {@code Optional}, {@code #result} refers to the actual
	 * object, not the wrapper</li>
	 * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
	 * references to the {@link java.lang.reflect.Method method}, target object, and
	 * affected cache(s) respectively.</li>
	 * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
	 * ({@code #root.targetClass}) are also available.
	 * <li>Method arguments can be accessed by index. For instance the second argument
	 * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
	 * can also be accessed by name if that information is available.</li>
	 * </ul>
	 *
	 * @since 3.2
	 */
	/**
	 * 用于否决缓存put操作的Spring表达式语言(SpEL)表达式。
	 * <p>与{@link #condition}不同，这个表达式是在方法之后计算的
	 * 已被调用，因此可以引用{@code result}。
	 * <p>默认是{@code ""}，这意味着缓存从未被否决。
	 * <p> SpEL表达式根据提供
	 * 元数据:
	 * < ul >
	 * <li>{@code #result}引用方法调用的结果。为
	 * 支持的包装器，如{@code Optional}， {@code #result}引用实际的
	 * 对象，而不是包装器</li>
	 * <李> {@code #根。方法},{ @code #根。目标}和{@code #root。缓存},
	 * 引用{@link java.lang.reflect.Method method}，目标对象，和
	 * 分别影响缓存(s) .</li>
	 * 方法名({@code #root.methodName})和目标类的快捷方式
	 * ({@code #root.targetClass})也可用。
	 * <li>方法参数可以通过索引访问。例如第二个参数
	 * 可以通过{@code #root访问。args[1]}， {@code #p1}或{@code #a1}。参数
	 * 如果信息可用，也可以通过名称访问。</li>
	 * < / ul >
	 *
	 * @since 3.2
	 */
	String unless() default "";

}
