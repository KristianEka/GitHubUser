package com.ekachandra.githubuser.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.ekachandra.githubuser.R
import com.ekachandra.githubuser.core.domain.model.Users
import com.ekachandra.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USER)

        if (username != null) {
            supportActionBar?.title = username
            showDetailContent(username)
            initPager(username)
        }
    }

    private fun initPager(username: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showDetailContent(username: String) {
        detailViewModel.getUserDetail(username).observe(this@DetailActivity) { users ->
            if (users != null) {
                when (users) {
                    is com.ekachandra.githubuser.core.data.Resource.Loading -> {
                        stateLoading(true)
                        stateEmpty(false)
                        stateError(false)
                    }

                    is com.ekachandra.githubuser.core.data.Resource.Success -> {
                        stateLoading(false)
                        if (users.data != null) {
                            stateEmpty(false)
                            binding.apply {
                                Glide.with(applicationContext)
                                    .load(users.data!!.avatarUrl)
                                    .into(ivPhoto)

                                tvUsername.text = users.data!!.login
                                tvName.text = users.data!!.name
                                tabs.getTabAt(0)?.text =
                                    getString(R.string.followers_count, users.data!!.followers)
                                tabs.getTabAt(1)?.text =
                                    getString(R.string.following_count, users.data!!.following)
                                setStatusFavorite(users.data!!)
                            }
                            stateError(false)
                        } else {
                            stateEmpty(true)
                        }
                    }

                    is com.ekachandra.githubuser.core.data.Resource.Error -> {
                        stateLoading(false)
                        stateEmpty(false)
                        stateError(true, users.message)
                    }
                }
            }
        }
    }

    private fun setStatusFavorite(users: Users) {
        detailViewModel.getFavoriteIsExists(users.login).observe(this) { isFavorite ->
            if (isFavorite) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite_24
                    )
                )
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite_border_24
                    )
                )
            }

            binding.fabFavorite.setOnClickListener {
                if (isFavorite) {
                    showToast(getString(R.string.successfully_deleted_fav))
                    detailViewModel.deleteUserFavorite(users)
                } else {
                    showToast(getString(R.string.successfully_added_fav))
                    detailViewModel.insertUserFavorite(users)
                }
            }

        }
    }

    private fun stateLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val USER = "user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_count, R.string.following_count
        )

    }

}