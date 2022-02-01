package id.alian.cryptocurrency.presentation.ui.coin_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.alian.cryptocurrency.databinding.FragmentCoinListBinding
import id.alian.cryptocurrency.presentation.adapters.CoinAdapter
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!
    private val coinAdapter by lazy { CoinAdapter() }
    private val viewModel: CoinListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)

        binding.coinRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.coinRecyclerView.adapter = coinAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is CoinListState.Success -> {
                        hideLoading()
                        coinAdapter.differ.submitList(it.coins)
                    }

                    is CoinListState.Loading -> {
                        showLoading()
                    }

                    is CoinListState.Error -> {
                        hideLoading()
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                    }
                    else -> CoinListState.Empty
                }
            }
        }

        coinAdapter.setOnItemClickListener {
            val directions =
                CoinListFragmentDirections.actionCoinListFragmentToCoinDetailFragment(it.id)
            findNavController().navigate(directions)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.coinRecyclerView.visibility = View.GONE
        binding.error.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.coinRecyclerView.visibility = View.VISIBLE
        binding.error.visibility = View.GONE
    }
}