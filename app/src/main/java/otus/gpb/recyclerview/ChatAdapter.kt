package otus.gpb.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import otus.gpb.recyclerview.databinding.ChatItemBinding
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

class ChatAdapter: ListAdapter<ChatItem, ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatItem = getItem(position)
        val binding = holder.binding
        binding.textName.setText(chatItem.name)
        binding.textStatus.setText(chatItem.status)
        binding.message.setText(chatItem.message)
        binding.time.setText(chatItem.time)

        binding.iconVerified.visibility = if (chatItem.verified) VISIBLE else INVISIBLE
        binding.iconScam.visibility = if (chatItem.scam) VISIBLE else INVISIBLE
        binding.iconMute.visibility = if (chatItem.mute) VISIBLE else INVISIBLE
        binding.counter.visibility = if (chatItem.counter > 0) VISIBLE else INVISIBLE
        binding.counter.text = chatItem.counter.toString()
        binding.time.text = chatItem.time
        binding.delivered.visibility = if (chatItem.delivered && !chatItem.read) VISIBLE else INVISIBLE

        val id: Int? =  if (chatItem.read) R.drawable.doublecheck
        else if (chatItem.delivered && !chatItem.read) R.drawable.check
        else if (chatItem.delivered && chatItem.read) R.drawable.doublecheck
        else null

        if (id != null) {
            binding.delivered.setImageResource(id)
            binding.delivered.visibility = VISIBLE
        }
        else{
            binding.delivered.visibility = INVISIBLE
        }
    }

    fun submitNewList(list: List<ChatItem>){
        submitList(list)
    }
}
