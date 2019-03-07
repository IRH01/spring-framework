/**
 * Common annotations with language-level semantics: nullability as well as JDK API indications.
 * These annotations sit at the lowest level of Spring's package dependency arrangement, even
 * lower than {@code org.springframework.util}, with no Spring-specific concepts implied.
 *
 * <p>Used descriptively within the framework codebase. Can be validated by build-time tools
 * (e.g. FindBugs or Animal Sniffer), alternative JVM languages (e.g. Kotlin), as well as IDEs
 * (e.g. IntelliJ IDEA or Eclipse with corresponding project setup).
 * <p>
 * *带有语言级语义的常见注释:可空性以及JDK API指示。
 * *这些注释甚至位于Spring包依赖关系安排的最底层
 * *低于{@code org.springframework.util}，没有包含特定于spring的概念。
 * *
 * * <p>在框架代码库中描述使用。可以通过构建时工具进行验证吗
 * *(例如FindBugs或Animal Sniffer)、其他JVM语言(例如Kotlin)以及ide
 * *(例如IntelliJ IDEA或Eclipse及其相应的项目设置)。
 */
package org.springframework.lang;
