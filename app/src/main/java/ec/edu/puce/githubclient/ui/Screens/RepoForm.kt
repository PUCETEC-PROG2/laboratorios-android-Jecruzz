package ec.edu.puce.githubclient.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.Edit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoForm (
    repository: Repository? = null,
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {},
    viewModel: RepoFormViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    var name by remember { mutableStateOf(repository?.name ?: "") }
    var description by remember { mutableStateOf(repository?.description ?: "") }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onSaveSuccess()
            viewModel.resetSuccess()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = if (repository == null) "Crear Repositorio" else "Editar Repositorio") },
                navigationIcon = {
                    IconButton(onClick = onBackClick){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top
        ){
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = if (repository == null) "Nuevo Repositorio" else "Actualizar Datos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else if (!errorMsg.isNullOrBlank()) {
                androidx.compose.material3.Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = errorMsg!!,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del repositorio") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (repository == null) {
                        viewModel.createRepo(name = name, description = description)
                    } else {
                        viewModel.updateRepo(
                            owner = repository.owner.login,
                            originalName = repository.name,
                            name = name,
                            description = description
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading && name.isNotBlank(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(
                    imageVector = if (repository == null) Icons.Default.Send else Icons.Default.Edit,
                    contentDescription = "Guardar"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = if (repository == null) "CREAR REPOSITORIO" else "GUARDAR CAMBIOS", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoFormPreview () {
    GithubClientTheme {
        RepoForm()
    }
}