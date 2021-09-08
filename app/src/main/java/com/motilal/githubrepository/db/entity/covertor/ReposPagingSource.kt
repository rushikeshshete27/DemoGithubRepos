package com.motilal.githubrepository.db.entity.covertor

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.motilal.githubrepository.db.dao.ReposDao
import com.motilal.githubrepository.trending.api.GithubApi
import com.motilal.githubrepository.trending.data.model.Repo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class ReposPagingSource(private val reposDao: ReposDao, private val query: String): PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
           // val response = githubApi.getTrendingRepos(query, position, params.loadSize)
           // val repos = response.items

            val response = reposDao.getReposByLimits(position, params.loadSize)
            val repos = response


            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        TODO("Not yet implemented")
    }

}