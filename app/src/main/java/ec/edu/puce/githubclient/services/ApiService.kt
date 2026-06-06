package ec.edu.puce.githubclient.services

import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(value = "/user/repos")
    suspend fun getRepositories (
        @Query(value = "sort") sort : String = "created",
        @Query(value = "direction") direction : String = "desc",
        @Query(value = "affiliation") affiliation : String = "owner"
    ) : List<Repository>

    @POST(value = "/user/repos")
    suspend fun createRepository (
        @Body repository: RepositoryPayload
    ) : Repository

    @PATCH(value = "/repos/{owner}/{repo}")
    suspend fun updateRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body repository: RepositoryPayload
    ): Repository

    @DELETE(value = "/repos/{owner}/{repo}")
    suspend fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): retrofit2.Response<Unit>
}