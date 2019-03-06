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

package org.springframework.cache.config;

import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * {@link org.springframework.beans.factory.xml.BeanDefinitionParser}
 * implementation that allows users to easily configure all the
 * infrastructure beans required to enable annotation-driven cache
 * demarcation.
 *
 * <p>By default, all proxies are created as JDK proxies. This may cause
 * some problems if you are injecting objects as concrete classes rather
 * than interfaces. To overcome this restriction you can set the
 * '{@code proxy-target-class}' attribute to '{@code true}', which will
 * result in class-based proxies being created.
 *
 * <p>If the JSR-107 API and Spring's JCache implementation are present,
 * the necessary infrastructure beans required to handle methods annotated
 * with {@code CacheResult}, {@code CachePut}, {@code CacheRemove} or
 * {@code CacheRemoveAll} are also registered.
 *
 * @author Costin Leau
 * @author Stephane Nicoll
 * @since 3.1
 * / * *
 * * { @link org.springframework.beans.factory.xml.BeanDefinitionParser }
 * *实现，允许用户轻松配置所有
 * *启用注解驱动缓存所需的基础架构bean
 * *界定。
 * *
 * 默认情况下，所有代理都作为JDK代理创建。这可能导致
 * *如果你将对象注入到具体的类中，会出现一些问题
 * *比接口。要克服这个限制，可以设置
 * * '{@code proxy-target-class}'属性为'{@code true}'，将
 * *导致创建基于类的代理。
 * *
 * 如果存在JSR-107 API和Spring的JCache实现，
 * 处理带注释的方法所需的基础结构bean
 * *使用{@code CacheResult}、{@code CachePut}、{@code CacheRemove}或
 * * {@code CacheRemoveAll}也被注册。
 * *
 * *作者Costin Leau
 * *作者Stephane Nicoll
 * * @since 3.1
 * * /
 */
class AnnotationDrivenCacheBeanDefinitionParser implements BeanDefinitionParser {

	private static final String CACHE_ASPECT_CLASS_NAME =
			"org.springframework.cache.aspectj.AnnotationCacheAspect";

	private static final String JCACHE_ASPECT_CLASS_NAME =
			"org.springframework.cache.aspectj.JCacheCacheAspect";

	private static final boolean jsr107Present;

	private static final boolean jcacheImplPresent;

	static {
		ClassLoader classLoader = AnnotationDrivenCacheBeanDefinitionParser.class.getClassLoader();
		jsr107Present = ClassUtils.isPresent("javax.cache.Cache", classLoader);
		jcacheImplPresent = ClassUtils.isPresent(
				"org.springframework.cache.jcache.interceptor.DefaultJCacheOperationSource", classLoader);
	}

	/**
	 * Parses the '{@code <cache:annotation-driven>}' tag. Will
	 * {@link AopNamespaceUtils#registerAutoProxyCreatorIfNecessary
	 * register an AutoProxyCreator} with the container as necessary.
	 * / * *
	 * *解析“{@code <cache:annotation-driven>}”标签。将
	 * * {@link AopNamespaceUtils # registerAutoProxyCreatorIfNecessary
	 * *必要时向容器注册AutoProxyCreator}。
	 * * /
	 */
	@Override
	@Nullable
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String mode = element.getAttribute("mode");
		if ("aspectj".equals(mode)) {
			// mode="aspectj"
			registerCacheAspect(element, parserContext);
		} else {
			// mode="proxy"
			registerCacheAdvisor(element, parserContext);
		}

		return null;
	}

	private void registerCacheAspect(Element element, ParserContext parserContext) {
		SpringCachingConfigurer.registerCacheAspect(element, parserContext);
		if (jsr107Present && jcacheImplPresent) {
			JCacheCachingConfigurer.registerCacheAspect(element, parserContext);
		}
	}

	private void registerCacheAdvisor(Element element, ParserContext parserContext) {
		AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);
		SpringCachingConfigurer.registerCacheAdvisor(element, parserContext);
		if (jsr107Present && jcacheImplPresent) {
			JCacheCachingConfigurer.registerCacheAdvisor(element, parserContext);
		}
	}

	/**
	 * Parse the cache resolution strategy to use. If a 'cache-resolver' attribute
	 * is set, it is injected. Otherwise the 'cache-manager' is set. If {@code setBoth}
	 * is {@code true}, both service are actually injected.
	 * / * *
	 * *解析要使用的缓存解析策略。如果是cache-resolver属性
	 * *被设置，它被注入。否则设置cache-manager。如果{@code setBoth}
	 * *是{@code true}，两个服务都被实际注入。
	 * * /
	 */
	private static void parseCacheResolution(Element element, BeanDefinition def, boolean setBoth) {
		String name = element.getAttribute("cache-resolver");
		boolean hasText = StringUtils.hasText(name);
		if (hasText) {
			def.getPropertyValues().add("cacheResolver", new RuntimeBeanReference(name.trim()));
		}
		if (!hasText || setBoth) {
			def.getPropertyValues().add("cacheManager",
					new RuntimeBeanReference(CacheNamespaceHandler.extractCacheManager(element)));
		}
	}

	private static void parseErrorHandler(Element element, BeanDefinition def) {
		String name = element.getAttribute("error-handler");
		if (StringUtils.hasText(name)) {
			def.getPropertyValues().add("errorHandler", new RuntimeBeanReference(name.trim()));
		}
	}


	/**
	 * Configure the necessary infrastructure to support the Spring's caching annotations.
	 * 配置必要的基础设施以支持Spring的缓存注释。
	 */
	private static class SpringCachingConfigurer {

		private static void registerCacheAdvisor(Element element, ParserContext parserContext) {
			if (!parserContext.getRegistry().containsBeanDefinition(CacheManagementConfigUtils.CACHE_ADVISOR_BEAN_NAME)) {
				Object eleSource = parserContext.extractSource(element);

				// Create the CacheOperationSource definition.
				RootBeanDefinition sourceDef = new RootBeanDefinition("org.springframework.cache.annotation.AnnotationCacheOperationSource");
				sourceDef.setSource(eleSource);
				sourceDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				String sourceName = parserContext.getReaderContext().registerWithGeneratedName(sourceDef);

				// Create the CacheInterceptor definition.
				RootBeanDefinition interceptorDef = new RootBeanDefinition(CacheInterceptor.class);
				interceptorDef.setSource(eleSource);
				interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				parseCacheResolution(element, interceptorDef, false);
				parseErrorHandler(element, interceptorDef);
				CacheNamespaceHandler.parseKeyGenerator(element, interceptorDef);
				interceptorDef.getPropertyValues().add("cacheOperationSources", new RuntimeBeanReference(sourceName));
				String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

				// Create the CacheAdvisor definition.
				RootBeanDefinition advisorDef = new RootBeanDefinition(BeanFactoryCacheOperationSourceAdvisor.class);
				advisorDef.setSource(eleSource);
				advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				advisorDef.getPropertyValues().add("cacheOperationSource", new RuntimeBeanReference(sourceName));
				advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
				if (element.hasAttribute("order")) {
					advisorDef.getPropertyValues().add("order", element.getAttribute("order"));
				}
				parserContext.getRegistry().registerBeanDefinition(CacheManagementConfigUtils.CACHE_ADVISOR_BEAN_NAME, advisorDef);

				CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), eleSource);
				compositeDef.addNestedComponent(new BeanComponentDefinition(sourceDef, sourceName));
				compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
				compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, CacheManagementConfigUtils.CACHE_ADVISOR_BEAN_NAME));
				parserContext.registerComponent(compositeDef);
			}
		}

		/**
		 * Registers a cache aspect.
		 * <pre class="code">
		 * &lt;bean id="cacheAspect" class="org.springframework.cache.aspectj.AnnotationCacheAspect" factory-method="aspectOf"&gt;
		 *   &lt;property name="cacheManager" ref="cacheManager"/&gt;
		 *   &lt;property name="keyGenerator" ref="keyGenerator"/&gt;
		 * &lt;/bean&gt;
		 * </pre>
		 * <p>
		 * / * *
		 * 注册缓存方面。
		 * * < pre类=“代码”>
		 * bean id="cacheAspect" class="org.springframe .cache.aspectj. "AnnotationCacheAspect aspectOf“工厂方法=比;
		 * * &lt;属性名="cacheManager" ref="cacheManager"/&gt;
		 * 属性名="keyGenerator" ref="keyGenerator"/&gt;
		 * * & lt;/ bean&gt;
		 * * < / pre >
		 * * /
		 */
		private static void registerCacheAspect(Element element, ParserContext parserContext) {
			if (!parserContext.getRegistry().containsBeanDefinition(CacheManagementConfigUtils.CACHE_ASPECT_BEAN_NAME)) {
				RootBeanDefinition def = new RootBeanDefinition();
				def.setBeanClassName(CACHE_ASPECT_CLASS_NAME);
				def.setFactoryMethodName("aspectOf");
				parseCacheResolution(element, def, false);
				CacheNamespaceHandler.parseKeyGenerator(element, def);
				parserContext.registerBeanComponent(new BeanComponentDefinition(def, CacheManagementConfigUtils.CACHE_ASPECT_BEAN_NAME));
			}
		}
	}


	/**
	 * Configure the necessary infrastructure to support the standard JSR-107 caching annotations.
	 * 配置必要的基础设施以支持标准的JSR-107缓存注释。
	 */
	private static class JCacheCachingConfigurer {

		private static void registerCacheAdvisor(Element element, ParserContext parserContext) {
			if (!parserContext.getRegistry().containsBeanDefinition(CacheManagementConfigUtils.JCACHE_ADVISOR_BEAN_NAME)) {
				Object source = parserContext.extractSource(element);

				// Create the CacheOperationSource definition.
				BeanDefinition sourceDef = createJCacheOperationSourceBeanDefinition(element, source);
				String sourceName = parserContext.getReaderContext().registerWithGeneratedName(sourceDef);

				// Create the CacheInterceptor definition.
				RootBeanDefinition interceptorDef =
						new RootBeanDefinition("org.springframework.cache.jcache.interceptor.JCacheInterceptor");
				interceptorDef.setSource(source);
				interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				interceptorDef.getPropertyValues().add("cacheOperationSource", new RuntimeBeanReference(sourceName));
				parseErrorHandler(element, interceptorDef);
				String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

				// Create the CacheAdvisor definition.
				RootBeanDefinition advisorDef = new RootBeanDefinition(
						"org.springframework.cache.jcache.interceptor.BeanFactoryJCacheOperationSourceAdvisor");
				advisorDef.setSource(source);
				advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				advisorDef.getPropertyValues().add("cacheOperationSource", new RuntimeBeanReference(sourceName));
				advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
				if (element.hasAttribute("order")) {
					advisorDef.getPropertyValues().add("order", element.getAttribute("order"));
				}
				parserContext.getRegistry().registerBeanDefinition(CacheManagementConfigUtils.JCACHE_ADVISOR_BEAN_NAME, advisorDef);

				CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), source);
				compositeDef.addNestedComponent(new BeanComponentDefinition(sourceDef, sourceName));
				compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
				compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, CacheManagementConfigUtils.JCACHE_ADVISOR_BEAN_NAME));
				parserContext.registerComponent(compositeDef);
			}
		}

		private static void registerCacheAspect(Element element, ParserContext parserContext) {
			if (!parserContext.getRegistry().containsBeanDefinition(CacheManagementConfigUtils.JCACHE_ASPECT_BEAN_NAME)) {
				Object eleSource = parserContext.extractSource(element);
				RootBeanDefinition def = new RootBeanDefinition();
				def.setBeanClassName(JCACHE_ASPECT_CLASS_NAME);
				def.setFactoryMethodName("aspectOf");
				BeanDefinition sourceDef = createJCacheOperationSourceBeanDefinition(element, eleSource);
				String sourceName =
						parserContext.getReaderContext().registerWithGeneratedName(sourceDef);
				def.getPropertyValues().add("cacheOperationSource", new RuntimeBeanReference(sourceName));

				parserContext.registerBeanComponent(new BeanComponentDefinition(sourceDef, sourceName));
				parserContext.registerBeanComponent(new BeanComponentDefinition(def, CacheManagementConfigUtils.JCACHE_ASPECT_BEAN_NAME));
			}
		}

		private static RootBeanDefinition createJCacheOperationSourceBeanDefinition(Element element, @Nullable Object eleSource) {
			RootBeanDefinition sourceDef =
					new RootBeanDefinition("org.springframework.cache.jcache.interceptor.DefaultJCacheOperationSource");
			sourceDef.setSource(eleSource);
			sourceDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			// JSR-107 support should create an exception cache resolver with the cache manager
			// and there is no way to set that exception cache resolver from the namespace
			parseCacheResolution(element, sourceDef, true);
			CacheNamespaceHandler.parseKeyGenerator(element, sourceDef);
			return sourceDef;
		}
	}

}
