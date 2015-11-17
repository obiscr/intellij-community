/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.liveTemplates.macro

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.template.Expression
import com.intellij.codeInsight.template.ExpressionContext
import com.intellij.codeInsight.template.Macro
import com.intellij.codeInsight.template.Result
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolder
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiNamedElement
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility
import org.jetbrains.kotlin.descriptors.VariableDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.getResolutionFacade
import org.jetbrains.kotlin.idea.codeInsight.ReferenceVariantsHelper
import org.jetbrains.kotlin.idea.core.isVisible
import org.jetbrains.kotlin.idea.util.CallTypeAndReceiver
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.getNonStrictParentOfType
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter
import org.jetbrains.kotlin.util.slicedMap.UserDataHolderImpl
import java.util.*

abstract class BaseKotlinVariableMacro : Macro() {
    private fun getVariables(params: Array<Expression>, context: ExpressionContext): Collection<PsiNamedElement> {
        if (params.size != 0) return emptyList()

        val project = context.project
        val psiDocumentManager = PsiDocumentManager.getInstance(project)
        psiDocumentManager.commitAllDocuments()

        val psiFile = psiDocumentManager.getPsiFile(context.editor!!.document) as? KtFile ?: return emptyList()

        val contextElement = psiFile.findElementAt(context.startOffset)?.getNonStrictParentOfType<KtElement>() ?: return emptyList()

        val resolutionFacade = psiFile.getResolutionFacade()

        val bindingContext = resolutionFacade.analyze(contextElement, BodyResolveMode.PARTIAL_FOR_COMPLETION)

        fun isVisible(descriptor: DeclarationDescriptor): Boolean {
            return descriptor !is DeclarationDescriptorWithVisibility || descriptor.isVisible(contextElement, null, bindingContext, resolutionFacade)
        }

        val userData = UserDataHolderImpl()
        initUserData(userData, contextElement, bindingContext)

        val helper = ReferenceVariantsHelper(bindingContext, resolutionFacade, resolutionFacade.moduleDescriptor, ::isVisible)
        val variants = helper
                .getReferenceVariants(contextElement, CallTypeAndReceiver.DEFAULT, DescriptorKindFilter.VARIABLES, { true })
                .filter { isSuitable(it as VariableDescriptor, project, userData) }

        val declarations = ArrayList<PsiNamedElement>()
        for (descriptor in variants) {
            val declaration = DescriptorToSourceUtils.descriptorToDeclaration(descriptor) as? PsiNamedElement ?: continue
            declarations.add(declaration)
        }
        return declarations
    }

    protected open fun initUserData(userData: UserDataHolder, contextElement: KtElement, bindingContext: BindingContext) {
    }

    protected abstract fun isSuitable(
            variableDescriptor: VariableDescriptor,
            project: Project,
            userData: UserDataHolder): Boolean

    override fun calculateResult(params: Array<Expression>, context: ExpressionContext): Result? {
        val vars = getVariables(params, context)
        if (vars.isEmpty()) return null
        return KotlinPsiElementResult(vars.first())
    }

    override fun calculateLookupItems(params: Array<Expression>, context: ExpressionContext): Array<LookupElement>? {
        val vars = getVariables(params, context)
        if (vars.size < 2) return null
        return vars.map { LookupElementBuilder.create(it) }.toTypedArray()
    }
}
