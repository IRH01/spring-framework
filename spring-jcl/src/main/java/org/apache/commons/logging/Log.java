/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.logging;

/**
 * A simple logging interface abstracting logging APIs.  In order to be
 * instantiated successfully by {@link LogFactory}, classes that implement
 * this interface must have a constructor that takes a single String
 * parameter representing the "name" of this Log.
 *
 * <p>The six logging levels used by <code>Log</code> are (in order):
 * <ol>
 * <li>trace (the least serious)</li>
 * <li>debug</li>
 * <li>info</li>
 * <li>warn</li>
 * <li>error</li>
 * <li>fatal (the most serious)</li>
 * </ol>
 * <p>
 * The mapping of these log levels to the concepts used by the underlying
 * logging system is implementation dependent.
 * The implementation should ensure, though, that this ordering behaves
 * as expected.
 *
 * <p>Performance is often a logging concern.
 * By examining the appropriate property,
 * a component can avoid expensive operations (producing information
 * to be logged).
 *
 * <p>For example,
 * <pre>
 *    if (log.isDebugEnabled()) {
 *        ... do something expensive ...
 *        log.debug(theResult);
 *    }
 * </pre>
 *
 * <p>Configuration of the underlying logging system will generally be done
 * external to the Logging APIs, through whatever mechanism is supported by
 * that system.
 *
 * @author Juergen Hoeller (for the {@code spring-jcl} variant)
 * @since 5.0
 */
public interface Log {

	/**
	 * Is fatal logging currently enabled? 当前是否启用了致命日志记录?
	 * <p>Call this method to prevent having to perform expensive operations 调用此方法以避免执行昂贵的操作
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than fatal. 当日志级别超过致命级别时。
	 *
	 * @return true if fatal is enabled in the underlying logger. 如果在底层日志记录器中启用了fatal，则为true。
	 */
	boolean isFatalEnabled();

	/**
	 * Is error logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than error.
	 * @return true if error is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用错误日志记录?
	 * <p>调用此方法以避免执行昂贵的操作
	 * (例如<code>String</code> concatenation)
	 * 当日志级别大于错误时。
	 * 如果在底层日志记录器中启用了错误，则返回true。
	 */
	boolean isErrorEnabled();

	/**
	 * Is warn logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than warn.
	 *
	 * @return true if warn is enabled in the underlying logger.
	 */
	/**
	 * 警告日志当前是否启用?
	 * <p>调用此方法以避免执行昂贵的操作
	 * (例如<code>String</code> concatenation)
	 * 当日志级别大于warn时。
	 * <p>
	 * 如果在底层日志记录器中启用warn，则返回true。
	 */
	boolean isWarnEnabled();

	/**
	 * Is info logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than info.
	 *
	 * @return true if info is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用了信息日志记录?
	 * <p>调用此方法以避免执行昂贵的操作
	 * (例如<code>String</code> concatenation)
	 * 当日志级别大于info级别时。
	 * <p>
	 * 如果在底层日志程序中启用了info，则返回true。
	 */
	boolean isInfoEnabled();

	/**
	 * Is debug logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than debug.
	 *
	 * @return true if debug is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用调试日志记录?
	 * <p>调用此方法以避免执行昂贵的操作
	 * (例如<code>String</code> concatenation)
	 * 当日志级别大于调试级别时。
	 * <p>
	 * 如果在底层日志程序中启用了调试，则返回true。
	 */
	boolean isDebugEnabled();

	/**
	 * Is trace logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than trace.
	 *
	 * @return true if trace is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用跟踪日志记录?
	 * <p>调用此方法以避免执行昂贵的操作
	 * (例如<code>String</code> concatenation)
	 * 当日志级别大于跟踪级别时。
	 * <p>
	 * 如果在底层日志记录器中启用跟踪，则返回true。
	 */
	boolean isTraceEnabled();


	/**
	 * Logs a message with fatal log level. 记录具有致命日志级别的消息。
	 *
	 * @param message log this message
	 */
	void fatal(Object message);

	/**
	 * Logs an error with fatal log level. 记录具有致命日志级别的错误。
	 *
	 * @param message log this message
	 * @param t       log this cause
	 */
	void fatal(Object message, Throwable t);

	/**
	 * Logs a message with error log level. 使用错误日志级别记录消息。
	 *
	 * @param message log this message
	 */
	void error(Object message);

	/**
	 * Logs an error with error log level.
	 *
	 * @param message log this message
	 * @param t       log this cause
	 */
	void error(Object message, Throwable t);

	/**
	 * Logs a message with warn log level.
	 *
	 * @param message log this message
	 */
	void warn(Object message);

	/**
	 * Logs an error with warn log level.
	 *
	 * @param message log this message
	 * @param t       log this cause
	 */
	void warn(Object message, Throwable t);

	/**
	 * Logs a message with info log level.
	 *
	 * @param message log this message
	 */
	void info(Object message);

	/**
	 * Logs an error with info log level.
	 *
	 * @param message log this message
	 * @param t       log this cause
	 */
	void info(Object message, Throwable t);

	/**
	 * Logs a message with debug log level.
	 *
	 * @param message log this message
	 */
	void debug(Object message);

	/**
	 * Logs an error with debug log level.
	 *
	 * @param message log this message
	 * @param t       log this cause
	 */
	void debug(Object message, Throwable t);

	/**
	 * Logs a message with trace log level.
	 *
	 * @param message log this message
	 */
	void trace(Object message);

	/**
	 * Logs an error with trace log level.
	 *
	 * @param message log this message
	 * @param t       log this cause
	 */
	void trace(Object message, Throwable t);

}
