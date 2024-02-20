package com.example.pocfavapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pocfavapp.databinding.FragmentHomeBinding
import com.example.pocfavapp.databinding.FragmentHostBinding
import com.example.pocfavapp.home.HomeFragment
import com.example.pocfavapp.home.controller.ItemController
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.system.measureTimeMillis

class HostFragment : Fragment() {
    private lateinit var binding: FragmentHostBinding

    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHostBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupTab()
        observeBackstack()
    }

    private fun observeBackstack() {
        setFragmentResultListener("key") { _, result ->
            val a = result.getString("key")

            Log.d("TAG", "observeBackstack observeBackstack: $a")
            Toast.makeText(requireContext(), "Get result: $a", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupAdapter() {
        mainAdapter = MainAdapter(childFragmentManager, lifecycle)

        binding.viewPager.apply {
            isUserInputEnabled = false
            currentItem = 0
            adapter = mainAdapter
        }
    }

    private fun setupTab() {
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            tab.text = mainAdapter.getTitle(position)
        }.attach()
    }

    inner class MainAdapter(
        fm: FragmentManager,
        lifecycle: Lifecycle
    ) : FragmentStateAdapter(fm, lifecycle) {

        private val fragments = listOf(Pair("Item", HomeFragment.newInstance()))

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position].second
        }

        fun getTitle(position: Int): String {
            return fragments[position].first
        }
    }
}
