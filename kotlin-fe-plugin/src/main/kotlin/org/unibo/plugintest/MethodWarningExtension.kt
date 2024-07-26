package org.unibo.plugintest

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.diagnostics.*
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirFunctionCallChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall


object PluginErrors {
  val METHOD_CALLED by warning1<PsiElement, String>(SourceElementPositioningStrategies.CALL_ELEMENT_WITH_DOT)
}

object MethodCalledChecker : FirFunctionCallChecker() {
  override fun check(expression: FirFunctionCall,
                     context: CheckerContext,
                     reporter: DiagnosticReporter) {
    reporter.reportOn(
      expression.calleeReference.source,
      PluginErrors.METHOD_CALLED,
      "${expression.calleeReference.name.identifier} was called",
      context
    )
  }

}

class MethodCallCatchingExtension(session: FirSession): FirAdditionalCheckersExtension(session) {

  override val expressionCheckers: ExpressionCheckers = object : ExpressionCheckers() {
    override val functionCallCheckers : Set<FirFunctionCallChecker>
      get() = setOf(MethodCalledChecker)
  }
}
