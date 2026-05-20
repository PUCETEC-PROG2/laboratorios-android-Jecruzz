package ec.edu.puce.githubclient.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // AÑADIDO: Para manejar listas directamente
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue // IMPORTANTE: Corrige el error del "by"
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel

@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel()
) {
    // El "by" ahora funcionará correctamente gracias al import de 'getValue'
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
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

        // CORREGIDO: isNullOrBlank con 'O' mayúscula
        if (!isLoading && errorMsg.isNullOrBlank()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                // Optimizado: Pasamos la lista directamente en lugar de usar un índice (count)
                items(repos) { repo ->
                    RepoItem(repository = repo)
                }
            }
        }
    }
}