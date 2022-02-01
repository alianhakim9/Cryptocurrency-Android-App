package id.alian.cryptocurrency.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.alian.cryptocurrency.data.remote.dto.coin_detail_dto.TeamMember
import id.alian.cryptocurrency.databinding.TeamMemberItemLayoutBinding

class TeamMemberAdapter : RecyclerView.Adapter<TeamMemberAdapter.TeamViewHolder>() {

    class TeamViewHolder(val binding: TeamMemberItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<TeamMember>() {
        override fun areItemsTheSame(oldItem: TeamMember, newItem: TeamMember): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: TeamMember, newItem: TeamMember): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamViewHolder {
        return TeamViewHolder(
            TeamMemberItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val member = differ.currentList[position]
        holder.binding.teamNameTextView.text = member.name
        holder.binding.teamTitleTextView.text = member.position
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}