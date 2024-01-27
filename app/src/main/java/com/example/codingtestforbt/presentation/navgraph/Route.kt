package com.example.codingtestforbt.presentation.navgraph

sealed class Route(
    val route: String
) {

    object NewsListScreen : Route(route = "newsListScreen")

    object DetailsScreen : Route(route = "detailsScreen")
}

