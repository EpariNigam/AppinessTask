package com.test.nigam.appiness.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.test.nigam.appiness.R
import com.test.nigam.appiness.databinding.ActivityMainBinding
import com.test.nigam.appiness.utils.Utility
import com.test.nigam.appiness.viewmodel.DataViewModel


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        Adapter(this)
    }

    private val viewModel by lazy {
        DataViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvData.adapter = adapter
        viewModel.liveData.observe(this, Observer {
            adapter.setData(it)
        })
        if (Utility.isNetworkAvailable(this)) {
            viewModel.getData()
        } else {
            AlertDialog.Builder(this).setMessage(R.string.no_internet)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    finish()
                }.create().show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.menu_toolbarsearch)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_title)
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = true
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            viewModel.search(it)
        }
        return true
    }
}
