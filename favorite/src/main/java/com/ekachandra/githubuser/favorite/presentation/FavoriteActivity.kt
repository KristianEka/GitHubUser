package com.ekachandra.githubuser.favorite.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekachandra.githubuser.core.ui.UserAdapter
import com.ekachandra.githubuser.favorite.databinding.ActivityFavoriteBinding
import com.ekachandra.githubuser.favorite.di.ViewModelFactory
import com.ekachandra.githubuser.presentation.detail.DetailActivity
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        showFavoriteData()
    }

    private fun showFavoriteData() {
        favoriteViewModel.getAllUserFavorite().observe(this) { users ->
            if (users.isNullOrEmpty()) {
                stateEmpty(true)
            } else {
                stateEmpty(false)
                adapter.submitList(users)
                adapter.onItemClick = { selectedData ->
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.USER, selectedData.login)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = UserAdapter()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

    }

    private fun stateEmpty(isEmpty: Boolean) {
        binding.viewEmpty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

}