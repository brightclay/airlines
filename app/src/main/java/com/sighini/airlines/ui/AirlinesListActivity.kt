package com.sighini.airlines.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sighini.airlines.R
import com.sighini.airlines.data.AirlinesDatabase
import com.sighini.airlines.data.AirlinesRepository
import com.sighini.airlines.data.getNetworkService

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [AirlinesDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class AirlinesListActivity : AppCompatActivity() {

    private val airlinesViewModel: AirlinesViewModel by lazy {
        val database = AirlinesDatabase.getInstance(this)
        val repository = AirlinesRepository.getInstance(database.airlinesDao, getNetworkService())
        ViewModelProvider(this, AirlinesViewModel.FACTORY(repository)).get(AirlinesViewModel::class.java)
    }

    private lateinit var adapter: AirlinesAdapter
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(findViewById(R.id.item_list))

        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            airlinesViewModel.items.collectLatest {
                adapter.submitData(it)
            }
        }

        airlinesViewModel.selectedAirline.observe(this, Observer {
            if (twoPane) {
                val fragment = AirlinesDetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(AirlinesDetailFragment.ARG_ITEM, it)
                    }
                }
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(this, AirlinesDetailActivity::class.java).apply {
                    putExtra(AirlinesDetailFragment.ARG_ITEM, it)
                }
                startActivity(intent)
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = AirlinesAdapter(AirlinesAdapter.AirlineClickListener {
            airlinesViewModel.selectItem(it)
        } )
        recyclerView.adapter = adapter
    }

}