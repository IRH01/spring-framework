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

package org.springframework.cache.interceptor;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Cache specific evaluation context that adds a method parameters as SpEL
 * variables, in a lazy manner. The lazy nature eliminates unneeded
 * parsing of classes byte code for parameter discovery.
 *
 * <p>Also define a set of "unavailable variables" (i.e. variables that should
 * lead to an exception right the way when they are accessed). This can be useful
 * to verify a condition does not match even when not all potential variables
 * are present.
 *
 * <p>To limit the creation of objects, an ugly constructor is used
 * (rather then a dedicated 'closure'-like class for deferred execution).
 * <p>
 * *缓存特定的计算上下文，该上下文将方法参数添加为SpEL
 * *变量，以一种惰性的方式。懒惰的天性消除了不必要的东西
 * 解析类的字节码以发现参数。
 * *
 * * <p>还定义了一组“不可用变量”(即应该不可用的变量)
 * *当它们被访问时，以正确的方式导致异常)。这很有用
 * 验证一个条件不匹配，即使不是所有的潜在变量
 * *。
 * *
 * * <p>为了限制对象的创建，使用了一个丑陋的构造函数
 * *(而不是一个专门的类似于“闭包”的类，用于延迟执行)。
 *
 * @author Costin Leau
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 3.1
 */
class CacheEvaluationContext extends MethodBasedEvaluationContext {

	private final Set<String> unavailableVariables = new HashSet<>(1);


	CacheEvaluationContext(Object rootObject, Method method, Object[] arguments,
						   ParameterNameDiscoverer parameterNameDiscoverer) {

		super(rootObject, method, arguments, parameterNameDiscoverer);
	}


	/**
	 * Add the specified variable name as unavailable for that context.
	 * Any expression trying to access this variable should lead to an exception.
	 * <p>This permits the validation of expressions that could potentially a
	 * variable even when such variable isn't available yet. Any expression
	 * trying to use that variable should therefore fail to evaluate.
	 */
	public void addUnavailableVariable(String name) {
		this.unavailableVariables.add(name);
	}


	/**
	 * Load the param information only when needed.
	 */
	@Override
	@Nullable
	public Object lookupVariable(String name) {
		if (this.unavailableVariables.contains(name)) {
			throw new VariableNotAvailableException(name);
		}
		return super.lookupVariable(name);
	}

}
