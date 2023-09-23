package com.ekachandra.githubuser.presentation.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekachandra.githubuser.R
import com.ekachandra.githubuser.core.ui.UserAdapter
import com.ekachandra.githubuser.databinding.ActivityMainBinding
import com.ekachandra.githubuser.presentation.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val mainViewModel: MainViewModel by viewModel()
    private var isDarkMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stateTheme()
        setAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = getString(R.string.search_username)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showData(query.toString())
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })
        }

        val themeChanger = menu.findItem(R.id.theme_changer)
        themeChanger.setIcon(if (isDarkMode) R.drawable.ic_light_mode_24 else R.drawable.ic_dark_mode_24)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.favorite -> {
                val uri = Uri.parse("githubuser://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }

            R.id.theme_changer -> {
                if (isDarkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    mainViewModel.saveThemeSetting(false)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    mainViewModel.saveThemeSetting(true)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun stateTheme() {
        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive ->
            isDarkMode = isDarkModeActive
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setAdapter() {
        adapter = UserAdapter()

        binding.listUser.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

    }

    private fun showData(username: String) {
        mainViewModel.getUsersByUsername(username).observe(this) { users ->
            if (users != null) {
                when (users) {
                    is com.ekachandra.githubuser.core.data.Resource.Loading -> {
                        stateLoading(true)
                        stateEmpty(false)
                        stateError(false)
                    }

                    is com.ekachandra.githubuser.core.data.Resource.Success -> {
                        stateLoading(false)
                        if (users.data.isNullOrEmpty()) {
                            stateEmpty(true)
                        } else {
                            stateEmpty(false)
                            adapter.submitList(users.data)
                            adapter.onItemClick = { selectedData ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.USER, selectedData.login)
                                startActivity(intent)
                            }
                        }
                        stateError(false)
                    }

                    is com.ekachandra.githubuser.core.data.Resource.Error -> {
                        stateLoading(false)
                        stateEmpty(false)
                        stateError(true, users.message)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun stateLoading(isLoading: Boolean) {
        binding.listUser.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun stateEmpty(isEmpty: Boolean) {
        binding.viewEmpty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun stateError(
        isError: Boolean,
        message: String? = getString(R.string.oops_something_went_wrong),
    ) {
        binding.apply {
            if (isError) {
                viewError.root.visibility = View.VISIBLE
                viewError.tvError.text = message
            } else {
                viewError.root.visibility = View.GONE
            }
        }
    }

}