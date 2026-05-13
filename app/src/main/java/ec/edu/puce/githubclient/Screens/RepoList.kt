package ec.edu.puce.githubclient.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ec.edu.puce.githubclient.ui.components.RepoItem

@Composable
fun RepoList(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ){
        RepoItem(
            name = "Repositorio de Android",
            description = "repo creado con Kotlin para deasrrollo movil",
            avatarURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkvr_3dB8bkj8y_hUvqGDIgSNiu-1pLarovA&s",
            language = "Kotlin"
        )
        RepoItem(
            name = "Repositorio de Django",
            description = "repo creado con Kotlin para deasrrollo movil",
            avatarURL = "https://preview.redd.it/shin-kamen-rider-ichigo-fanart-by-v0-hclbnt9frgud1.jpeg?width=640&crop=smart&auto=webp&s=4dd1935702f3e6fbe1ea5e6f8388b267609d7aad",
            language = "Kotlin"
        )
        RepoItem(
            name = "Repositorio de React",
            description = "repo creado con Kotlin para deasrrollo movil",
            avatarURL = "https://i.ebayimg.com/images/g/FqgAAOSwOmhlFaG4/s-l1200.jpg",
            language = "Kotlin"
        )


    }
}
