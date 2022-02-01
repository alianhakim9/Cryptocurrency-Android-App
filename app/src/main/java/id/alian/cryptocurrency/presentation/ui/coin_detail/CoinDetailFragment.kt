package id.alian.cryptocurrency.presentation.ui.coin_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import id.alian.cryptocurrency.commons.Constants
import id.alian.cryptocurrency.databinding.FragmentCoinDetailBinding
import id.alian.cryptocurrency.presentation.adapters.TeamMemberAdapter
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!
    private val args: CoinDetailFragmentArgs by navArgs()
    private val viewModel: CoinDetailViewModel by viewModels()
    private val teamAdapter by lazy { TeamMemberAdapter() }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        savedInstanceState?.putString(Constants.PARAM_COIN_ID, args.coinId)

        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            viewModel.state.collect {
                when (it) {
                    is CoinDetailState.Success -> {
                        hideLoading()
                        it.coin.also { detail ->
                            binding.coinTextView.text =
                                "${detail.rank}. ${detail.name} (${detail.symbol})"
                            binding.coinStatusTextView.text = it.status
                            binding.coinStatusTextView.setTextColor(it.statusColor)
                            binding.coinDescTextView.text = detail.description
                            teamAdapter.differ.submitList(it.coin.team)
                            it.coin.team.map { team ->
                                val chip = Chip(requireContext())
                                chip.text = team.name
                                binding.tagChipGroup.addView(chip)
                            }
                        }
                    }

                    is CoinDetailState.Loading -> {
                        showLoading()
                    }

                    is CoinDetailState.Error -> {
                        hideLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> CoinDetailState.Empty
                }
            }
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideLoading() {
        binding.coinTextView.visibility = View.VISIBLE
        binding.coinStatusTextView.visibility = View.VISIBLE
        binding.coinDescTextView.visibility = View.VISIBLE
        binding.tagChipGroup.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.textView2.visibility = View.VISIBLE
        binding.rvTeamMember.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.coinTextView.visibility = View.GONE
        binding.coinStatusTextView.visibility = View.GONE
        binding.coinDescTextView.visibility = View.GONE
        binding.tagChipGroup.visibility = View.GONE
        binding.textView.visibility = View.GONE
        binding.rvTeamMember.visibility = View.GONE
        binding.textView2.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.rvTeamMember.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTeamMember.adapter = teamAdapter
        binding.rvTeamMember.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL)
        )
    }
}