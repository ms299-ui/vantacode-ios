package com.vantacode

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vantacode.feature.credentials.presentation.CredentialViewModel
import com.vantacode.feature.credentials.presentation.component.CredentialScreen
import com.vantacode.feature.editor.presentation.component.CodeEditorScreen
import com.vantacode.feature.git.presentation.GitViewModel
import com.vantacode.feature.git.presentation.component.GitScreen

/** 大屏右栏（工作台）当前承载的内容。 */
internal enum class WorkbenchPaneKind { NONE, GIT, EDITOR }

/**
 * 大屏下与聊天并排的右栏内容。
 *
 * Git 页是「参数 + 回调」的独立组件，这里只是把它的返回动作接成「关闭右栏」；
 * 窄窗下该页面仍走 NavHost 全屏路由，两条路径共用同一份 UI 代码。
 * ViewModel 挂在聊天页的 NavBackStackEntry 上，右栏在几种内容间切换不会销毁它们。
 */
@Composable
internal fun WorkbenchPaneContent(
    kind: WorkbenchPaneKind,
    onClose: () -> Unit,
    onOpenFile: ((String) -> Unit)? = null,
    editorPath: String? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (kind) {
            WorkbenchPaneKind.NONE -> Unit

            WorkbenchPaneKind.EDITOR -> {
                CodeEditorScreen(
                    path = editorPath.orEmpty(),
                    fileAccess = com.vantacode.di.AppGraph.fileAccess,
                    onClose = onClose
                )
            }

            WorkbenchPaneKind.GIT -> {
                // 凭据页留在右栏内部翻一层，而不是跳全屏路由，否则大屏下会从双栏突然变成整屏。
                var showCredentials by rememberSaveable { mutableStateOf(false) }
                if (showCredentials) {
                    val credentialViewModel: CredentialViewModel = viewModel(factory = com.vantacode.di.AppGraph.viewModelFactory)
                    CredentialScreen(
                        viewModel = credentialViewModel,
                        onNavigateBack = { showCredentials = false }
                    )
                } else {
                    val gitViewModel: GitViewModel = viewModel(factory = com.vantacode.di.AppGraph.viewModelFactory)
                    GitScreen(
                        viewModel = gitViewModel,
                        onNavigateToCredentials = { showCredentials = true },
                        onNavigateBack = onClose,
                        onOpenFile = onOpenFile,
                        embedded = true
                    )
                }
            }
        }
    }
}
