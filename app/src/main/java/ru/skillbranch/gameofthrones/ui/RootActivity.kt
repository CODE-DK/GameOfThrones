package ru.skillbranch.gameofthrones.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.gameofthrones.R
import java.util.Observer

class RootActivity : AppCompatActivity() {

    private lateinit var viewModel: RootViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        initViewModel()
        savedInstanceState ?: prepareData()
        navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment_container
        )
    }

    private fun initViewModel() {
        TODO("Not yet implemented")
    }

    private fun prepareData() {
        viewModel.syncDataIfNeed().observe(this, Observer<LoadResult<Boolean>> {
                when (it) {
                    is LoadResult.Loading -> {
                        navController.navigate(R.id.nav_splash)
                    }
                    is LoadResult.Success -> {
                        val action = SplashFragmentDirections.actionNavSplashToNavHouses()
                        navController.navigate(action)
                    }
                    is LoadResult.Error -> {
                        Snackbar.make(
                            root_container,
                            it.errorMessage.toString(),
                            Snackbar.LENGTH_INDEFINITE
                        )
                    }
                }
            }
        )
    }
}
