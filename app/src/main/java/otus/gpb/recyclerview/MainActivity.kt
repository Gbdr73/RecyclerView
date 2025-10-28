package otus.gpb.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.gpb.recyclerview.databinding.ActivityMainBinding
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import android.graphics.Canvas
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var chatList: MutableList<ChatItem> = mutableListOf()
    private lateinit var adapter: ChatAdapter
    var lastId = 0
    var previousItemsCount = 0
    private var pageLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = ChatAdapter()
        binding.recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        removeItem(position)
                    }
                    ItemTouchHelper.RIGHT -> {
                        removeItem(position)
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).create().decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        createItems(20)
        setScrollListener()
    }

    private fun setScrollListener(){
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val itemsCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                if (pageLoad && itemsCount > previousItemsCount) {
                    pageLoad = false
                    previousItemsCount = itemsCount
                }
                if (!pageLoad && lastVisibleItem >= itemsCount - 5) {
                    getNextPage()
                }
            }
        })
    }


    private fun getNextPage() {
        pageLoad = true
        createItems(20)
    }

    private fun removeItem(position: Int){
        val item = adapter.currentList[position]
        chatList.apply {
            remove(item)
        }
        adapter.notifyItemRemoved(position)
    }

    private fun createItems(size: Int){
        for (i in 1..size) {
            chatList.add(
                ChatItem(
                    lastId++,
                    "Name " + "$lastId",
                    "status " + "$lastId",
                    "Message " + "$lastId",
                    Random.nextInt(10),
                    Random.nextBoolean(),
                    Random.nextBoolean(),
                    Random.nextBoolean(),
                    Random.nextBoolean(),
                    Random.nextBoolean(),
                    "12:10"
                )
            )
        }
        adapter.submitList(chatList)
        adapter.notifyDataSetChanged()
    }
}