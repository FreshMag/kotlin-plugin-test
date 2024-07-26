
@file:OptIn(ExperimentalCompilerApi::class)

package org.unibo.plugintest
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Test
import kotlin.test.assertEquals

class MethodWarningTest {
  @Test
  fun `Method warning present`() {

    val sourceFile = SourceFile.kotlin(
      "main.kt", """
fun main() {
  System.out.println("Hello world!")
  println(debug())
}

fun debug() = "Hello, World!"

fun temp() = 10
fun tempStat() = "HEI"
""")

    val result = KotlinCompilation().apply {
      sources = listOf(sourceFile)
      compilerPluginRegistrars = listOf(ExampleCompilerRegistrar())
      inheritClassPath = true
    }.compile()
    assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
  }
}
