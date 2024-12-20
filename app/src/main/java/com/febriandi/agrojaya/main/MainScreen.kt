package com.febriandi.agrojaya.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.model.NavItem
import com.febriandi.agrojaya.screens.home.HomeScreen
import com.febriandi.agrojaya.screens.Paket.PaketScreen
import com.febriandi.agrojaya.screens.profile.ProfileScreen
import com.febriandi.agrojaya.screens.artikel.DetailArtikelScreen
import com.febriandi.agrojaya.ui.theme.CustomFontFamily


@Composable
fun MainScreen(rootNavController: NavController) {
    val navController = rememberNavController()
    val navItemList = listOf(
        NavItem("Beranda", R.drawable.icon_home),
        NavItem("Paket", R.drawable.icon_paket),
        NavItem("Profile", R.drawable.icon_profile),
    )


    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route


    val selectedIndex = when (currentRoute) {
        "home" -> 0
        "paket" -> 1
        "profile" -> 2
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.green_50)
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            when (index) {
                                0 -> navController.navigate("home") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                1 -> navController.navigate("paket") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                                2 -> navController.navigate("profile") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.icon),
                                contentDescription = navItem.label,
                                tint = if (selectedIndex == index)
                                    colorResource(id = R.color.green_400)
                                else
                                    colorResource(id = R.color.natural_900)
                            )
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                color = if (selectedIndex == index)
                                    colorResource(id = R.color.green_400)
                                else
                                    colorResource(id = R.color.natural_900),
                                fontWeight = if (selectedIndex == index)
                                    FontWeight.SemiBold
                                else
                                    FontWeight.Normal,
                                fontSize = 12.sp,
                                fontFamily = CustomFontFamily
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.green_400),
                            unselectedIconColor = colorResource(id = R.color.natural_900),
                            selectedTextColor = colorResource(id = R.color.green_400),
                            unselectedTextColor = colorResource(id = R.color.natural_900),
                            indicatorColor = colorResource(id = R.color.green_50)
                        ),
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //Halaman Home
            composable("home") {
                HomeScreen(
                    navController = navController,
                    rootNavController = rootNavController,
                )
            }
            //Halaman Paket
            composable("paket") {
                PaketScreen(
                    navController = navController,
                    rootNavController = rootNavController
                )
            }
            //Halaman Profile
            composable("profile") {
                ProfileScreen(
                    rootNavController = rootNavController,
                )
            }
            //Halaman Detail Artikel
            composable(
                "detailArtikel/{artikelId}",
                arguments = listOf(
                    navArgument("artikelId") {
                        type = NavType.IntType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                DetailArtikelScreen(
                    rootNavController = rootNavController,
                    navController = navController,
                    artikelId = backStackEntry.arguments?.getInt("artikelId")
                )
            }
        }
    }
}
