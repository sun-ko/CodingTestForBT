package com.example.codingtestforbt.presentation.navgraph

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.codingtestforbt.domain.model.Article
import com.example.codingtestforbt.presentation.news_details.DetailsScreen
import com.example.codingtestforbt.presentation.news_list.NewsListScreen
import com.example.codingtestforbt.presentation.news_list.NewsListViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.NewsListScreen.route) {
            val viewModel: NewsListViewModel = hiltViewModel()
            val state = viewModel.newsState.value

            NewsListScreen(
                navigateToDetails = { article ->
                    navigateToDetails(
                        navController = navController,
                        article = article
                    )
                },
                event = viewModel::onEvent,
                state = state
            )
        }
        composable(route = Route.DetailsScreen.route) {
            navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                ?.let { article ->
                    DetailsScreen(
                        article = article,
                        navigateUp = { navController.navigateUp() },
                    )
                }
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}