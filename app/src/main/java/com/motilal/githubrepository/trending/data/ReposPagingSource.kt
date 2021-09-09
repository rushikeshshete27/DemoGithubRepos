package com.motilal.githubrepository.trending.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.motilal.githubrepository.db.dao.ReposDao
import com.motilal.githubrepository.trending.api.GithubApi
import com.motilal.githubrepository.trending.data.GithubRepository
import com.motilal.githubrepository.trending.data.model.Repo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class ReposPagingSource(private val reposDao: ReposDao): PagingSource<Int, Repo>() {
    @Inject lateinit var githubRepository: GithubRepository
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        var position = params.key ?: STARTING_PAGE_INDEX

        return try {

            if(position > 1)
                position = position + params.loadSize

            val response = reposDao.getReposByLimits(position, position+params.loadSize)
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