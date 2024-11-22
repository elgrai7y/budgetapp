package com.depi.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.depi.budgetapp.R
import com.depi.budgetapp.adapters.CategoryAdapter
import com.depi.budgetapp.adapters.TransactionAdapter
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.databinding.FragmentAddCategoryBinding
import com.depi.budgetapp.databinding.FragmentAddTransactionBinding
import com.depi.budgetapp.databinding.FragmentManageCategoryBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.depi.budgetapp.viewmodels.CategoryViewModel
import com.depi.budgetapp.viewmodels.TransactionViewModel
import com.google.android.material.navigation.NavigationView


class ManageCategoryFragment : Fragment(), OnCategoryClickListener {
    private lateinit var binding:FragmentManageCategoryBinding

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var transvm: CategoryViewModel

    private lateinit var authRepository: AuthRepository

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageCategoryBinding.inflate(inflater, container, false)
        binding.createBtn.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_manageCategoryFragment_to_addCategoryFragment)

        })



        binding.toolbar.setNavigationOnClickListener {
            if (binding.drawable.isDrawerOpen(GravityCompat.START)) {
                binding.drawable.closeDrawer(GravityCompat.START)
            } else {
                binding.drawable.openDrawer(GravityCompat.START)
            }
        }


        toggle =
            ActionBarDrawerToggle(activity, binding.drawable, R.string.nav_open, R.string.nav_close)
        binding.drawable.addDrawerListener(toggle)
        toggle.syncState()






        drawerLayout = binding.drawable

        val navigationView: NavigationView = binding.navView

        val headerView = navigationView.getHeaderView(0)
        val headerButton: Button = headerView.findViewById(R.id.profile_nv)
        headerButton.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.profileFragment)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        val headerButton2: Button = headerView.findViewById(R.id.home_nv)
        headerButton2.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.homeFragment)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val headerButton3: Button = headerView.findViewById(R.id.trans_nv)
        headerButton3.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.allTransactionFragment2)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val headerButton4: Button = headerView.findViewById(R.id.category_nv)
        headerButton4.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.manageCategoryFragment)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val headerButton5: Button = headerView.findViewById(R.id.logout)
        headerButton5.setOnClickListener{
            authViewModel.signOut()
            findNavController().navigate(R.id.mainFragment)
        }






        onClick(view,"income")

        val adapter= CategoryAdapter(this)
        val recyclerview=binding.transRv
        recyclerview.adapter=adapter
        recyclerview.layoutManager= LinearLayoutManager(requireContext())
        transvm = ViewModelProvider(this).get(CategoryViewModel::class.java)
        transvm.getIncomeCategory().observe(viewLifecycleOwner, Observer {
                cate->adapter.setData(cate)
        })



        binding.editIncomeButton.setOnClickListener(View.OnClickListener {
            onClick(view,"income")

            val adapter= CategoryAdapter(this)
            val recyclerview=binding.transRv
            recyclerview.adapter=adapter
            recyclerview.layoutManager= LinearLayoutManager(requireContext())
            transvm = ViewModelProvider(this).get(CategoryViewModel::class.java)
            transvm.getIncomeCategory().observe(viewLifecycleOwner, Observer {
                    cate->adapter.setData(cate)
            })
        })
        binding.editExpenseButton .setOnClickListener(View.OnClickListener {
            onClick(view,"expense")

            val adapter= CategoryAdapter(this)
            val recyclerview=binding.transRv
            recyclerview.adapter=adapter
            recyclerview.layoutManager= LinearLayoutManager(requireContext())
            transvm = ViewModelProvider(this).get(CategoryViewModel::class.java)
            transvm.getExpenseCategory().observe(viewLifecycleOwner, Observer {
                    cate->adapter.setData(cate)
            })
        })


        return binding.root
    }

    override fun onCategoryClick(category: Category) {
        TODO("Not yet implemented")
    }

    @Override
    fun onClick(v: View?,s:String) {
        // Change button background color on click
        if(s=="income") {
            binding.editIncomeButton.setBackgroundColor(android.graphics.Color.parseColor("#FDCB08"))
            binding.editIncomeButton.setTextColor((android.graphics.Color.parseColor("#FF000000")))
            binding.editExpenseButton.setBackgroundColor(android.graphics.Color.parseColor("#4DAB3A3A"))
            binding.editExpenseButton.setTextColor((android.graphics.Color.parseColor("#FDA09A")))
        }
        else{
            binding.editExpenseButton.setBackgroundColor(android.graphics.Color.parseColor("#FDCB08"))
            binding.editExpenseButton.setTextColor((android.graphics.Color.parseColor("#FF000000")))
            binding.editIncomeButton.setBackgroundColor(android.graphics.Color.parseColor("#5729662C"))
            binding.editIncomeButton.setTextColor((android.graphics.Color.parseColor("#6FFF74")))
        }
    }

}