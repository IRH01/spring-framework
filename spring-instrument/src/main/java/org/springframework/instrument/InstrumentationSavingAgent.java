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

package org.springframework.instrument;

import java.lang.instrument.Instrumentation;

/**
 * Java agent that saves the {@link Instrumentation} interface from the JVM
 * for later use.
 * Java代理，它从JVM中保存{@link Instrumentation}接口供以后使用。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver
 * @since 2.0
 */
public final class InstrumentationSavingAgent {

	private static volatile Instrumentation instrumentation;


	private InstrumentationSavingAgent() {
	}


	/**
	 * Save the {@link Instrumentation} interface exposed by the JVM.
	 * 保存JVM公开的{@link Instrumentation}接口。
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		instrumentation = inst;
	}

	/**
	 * Save the {@link Instrumentation} interface exposed by the JVM.
	 * This method is required to dynamically load this Agent with the Attach API.
	 * 保存JVM公开的{@link Instrumentation}接口。
	 * 使用Attach API动态加载此代理需要此方法。
	 */
	public static void agentmain(String agentArgs, Instrumentation inst) {
		instrumentation = inst;
	}

	/**
	 * Return the {@link Instrumentation} interface exposed by the JVM.
	 * <p>Note that this agent class will typically not be available in the classpath
	 * unless the agent is actually specified on JVM startup. If you intend to do
	 * conditional checking with respect to agent availability, consider using
	 * {@link org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver#getInstrumentation()}
	 * instead - which will work without the agent class in the classpath as well.
	 *
	 * @return the {@code Instrumentation} instance previously saved when
	 * the {@link #premain} or {@link #agentmain} methods was called by the JVM;
	 * will be {@code null} if this class was not used as Java agent when this
	 * JVM was started or it wasn't installed as agent using the Attach API.
	 * @see org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver#getInstrumentation()
	 */
	/**
	 * 返回JVM公开的{@link Instrumentation}接口。
	 * <p>注意，这个代理类在类路径中通常不可用
	 * 除非在JVM启动时实际指定了代理。如果你打算这么做的话
	 * 关于代理可用性的条件检查，请考虑使用
	 * {@link org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver # getInstrumentation ()}
	 * 相反，如果类路径中没有代理类，它也可以工作。
	 * <p>
	 * 返回先前保存的{@code Instrumentation}实例
	 * JVM调用{@link #premain}或{@link #agentmain}方法;
	 * 将是{@code null}，如果这个类在这个时候没有被用作Java代理
	 * 使用Attach API启动JVM或未将其安装为代理。
	 *
	 * @see org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver # getInstrumentation ()
	 */
	public static Instrumentation getInstrumentation() {
		return instrumentation;
	}

}
