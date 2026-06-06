package ec.edu.puce.githubclient.ui.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    onNavigateToForm: (Repository?) -> Unit = {}
) {
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    var repoToDelete by remember { mutableStateOf<Repository?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mis Repositorios", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToForm(null) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(all = 16.dp)
                )
            }

            errorMsg?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(all = 16.dp)
                )
            }


            if (!isLoading && errorMsg.isNullOrBlank()) {
                if (repos.isEmpty()) {
                    Text(
                        text = "No tienes repositorios públicos.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(repos.size) { i ->
                            RepoItem(
                                repository = repos[i],
                                onEditClick = { onNavigateToForm(repos[i]) },
                                onDeleteClick = { repoToDelete = repos[i] }
                            )
                        }
                    }
                }
            }
        }
    }

    repoToDelete?.let { repo ->
        AlertDialog(
            onDismissRequest = { repoToDelete = null },
            title = { Text(text = "Eliminar Repositorio") },
            text = { Text(text = "¿Estás seguro de que deseas eliminar el repositorio '${repo.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteRepo(repo.owner.login, repo.name)
                        repoToDelete = null
                    }
                ) {
                    Text(text = "Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { repoToDelete = null }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}


@Preview (showBackground = true)
@Composable
fun RepoListPreview () {
    GithubClientTheme {
        RepoList()
    }
}