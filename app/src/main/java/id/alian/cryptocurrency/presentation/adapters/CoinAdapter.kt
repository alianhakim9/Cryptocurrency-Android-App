package id.alian.cryptocurrency.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.cryptocurrency.databinding.CoinItemLayoutBinding
import id.alian.cryptocurrency.domain.model.Coin

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    class CoinViewHolder(val binding: CoinItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            CoinItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = differ.currentList[position]
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(coin)
                }
            }
        }
        holder.binding.coinTextView.text =
            "${coin.rank}. ${coin.name} [ ${coin.symbol} ]"
        if (coin.isActive) {
            holder.binding.coinActive.text = "Active"
        } else {
            holder.binding.coinActive.text = "InActive"
            holder.binding.coinActive.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Coin) -> Unit)? = null

    fun setOnItemClickListener(listener: (Coin) -> Unit) {
        onItemClickListener = listener
    }
}