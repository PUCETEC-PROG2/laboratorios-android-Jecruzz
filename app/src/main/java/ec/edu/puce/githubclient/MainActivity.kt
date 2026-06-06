package ec.edu.puce.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.Screens.RepoForm
import ec.edu.puce.githubclient.ui.Screens.RepoList
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubClientTheme {
                var currentScreen by remember { mutableStateOf("repoList") }
                var repositoryToEdit by remember { mutableStateOf<Repository?>(null) }
                val listViewModel: RepoListViewModel = viewModel()

                when (currentScreen) {
                    "repoList" -> RepoList(
                        viewModel = listViewModel,
                        onNavigateToForm = { repo ->
                            repositoryToEdit = repo
                            currentScreen = "repoForm"
                        }
                    )
                    "repoForm" -> RepoForm(
                        repository = repositoryToEdit,
                        onBackClick = {
                            repositoryToEdit = null
                            currentScreen = "repoList"
                        },
                        onSaveSuccess = {
                            listViewModel.fetchRepos()
                            repositoryToEdit = null
                            currentScreen = "repoList"
                        }
                    )
                }
            }
        }
    }
}



