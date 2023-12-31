package com.ekachandra.githubuser.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekachandra.githubuser.R
import com.ekachandra.githubuser.core.domain.model.Users
import com.ekachandra.githubuser.core.ui.UserAdapter
import com.ekachandra.githubuser.databinding.FragmentFollowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        var position = 0
        var username = ""

        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1) {
            detailViewModel.getUserFollowers(username).observe(viewLifecycleOwner) {
                showData(it)
            }
        } else {
            detailViewModel.getUserFollowing(username).observe(viewLifecycleOwner) {
                showData(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rvUser.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onPause() {
        super.onPause()
        binding.rvUser.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0
        )
    }

    private fun showData(data: com.ekachandra.githubuser.core.data.Resource<List<Users>>?) {
        if (data != null) {
            when (data) {
                is com.ekachandra.githubuser.core.data.Resource.Loading -> {
                    stateLoading(true)
                    stateEmpty(false)
                    stateError(false)
                }

                is com.ekachandra.githubuser.core.data.Resource.Success -> {
                    stateLoading(false)
                    if (data.data.isNullOrEmpty()) {
                        stateEmpty(true)
                    } else {
                        stateEmpty(false)
                        adapter.submitList(data.data)
                        adapter.onItemClick = { selectedData ->
                            val intent = Intent(requireActivity(), DetailActivity::class.java)
                            intent.putExtra(DetailActivity.USER, selectedData.login)
                            startActivity(intent)
                        }
                    }
                    stateError(false)
                }

                is com.ekachandra.githubuser.core.data.Resource.Error -> {
                    stateLoading(false)
                    stateEmpty(false)
                    stateError(true, data.message)
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = UserAdapter()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(requireActivity())
            rvUser.setHasFixedSize(false)
            rvUser.adapter = adapter
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

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_username"
    }
}