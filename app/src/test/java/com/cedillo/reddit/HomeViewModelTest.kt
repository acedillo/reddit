package com.cedillo.reddit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cedillo.reddit.model.Child
import com.cedillo.reddit.model.Data
import com.cedillo.reddit.model.Main
import com.cedillo.reddit.model.Parent
import com.cedillo.reddit.repository.Repository
import com.google.gson.Gson
import junit.framework.Assert.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader


class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var repository: Repository

    @Before
    fun setup() {
        repository = Mockito.mock(Repository::class.java)
        Mockito.`when`(repository.getMainReddit()).thenReturn(Gson().fromJson(loadFile(), Main::class.java))
        Mockito.`when`(repository.getSubreddit(anyObject())).thenReturn(Gson().fromJson(loadFile(), Main::class.java))

    }


    @Test
    fun loadMain() {
        val viewModel = HomeViewModel(repository, GlobalScope)
        runBlocking {
            val job = viewModel.getMain()
            assertTrue(viewModel.loading.value!!)
            job.join()
            assertFalse(viewModel.loading.value!!)
            assertEquals(26, viewModel.mainRedditList.value?.size)
        }
    }

    @Test
    fun loadSubreddit() {
        val viewModel = HomeViewModel(repository, GlobalScope)
        runBlocking {
            val job = viewModel.onSearchClick("d")
            job?.join()
            assert(viewModel.loading.value == false)
            assertEquals(26, viewModel.subRedditList.value?.size)
        }
    }

    @Test
    fun loadSubredditError() {
        val repo = Mockito.mock(Repository::class.java)
        val main = Main()
        val parent = Parent()
        parent.children = ArrayList<Child>()
        main.data = parent

        Mockito.`when`(repo.getSubreddit(anyObject())).thenReturn(main)
        val viewModel = HomeViewModel(repo, GlobalScope)
        runBlocking {
            val job = viewModel.onSearchClick("dd")
            job?.join()
            assertFalse(viewModel.loading.value!!)
            assertTrue(viewModel.notFound.value!!)
        }
    }


    @Test
    fun onPostSelected() {
        val viewModel = HomeViewModel(repository)

        viewModel.onPostSelected(Data("subReddit", "title", "thumbnail", "perm", "url", "selfText"))
        val value = viewModel.post.value
        assertEquals("subReddit", value!!.subreddit)
        assertEquals("title", value.title)
        assertEquals("thumbnail", value.thumbnail)
        assertEquals("perm", value.permalink)
        assertEquals("url", value.url)
        assertEquals("selfText", value.selftext_html)

    }


    private fun loadFile(): String {
        val `is` = FileInputStream("src/test/resources/funny.json")
        val buf = BufferedReader(InputStreamReader(`is`))

        var line = buf.readLine()
        val sb = StringBuilder()

        while (line != null) {
            sb.append(line).append("\n")
            line = buf.readLine()
        }
        return sb.toString()
    }

    private fun <T> anyObject(): T {
        return Mockito.any<T>()
    }
}