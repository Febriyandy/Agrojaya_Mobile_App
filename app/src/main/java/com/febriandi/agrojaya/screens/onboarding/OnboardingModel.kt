package com.febriandi.agrojaya.screens.onboarding

import androidx.annotation.DrawableRes
import com.febriandi.agrojaya.R

//onboarding model
sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
) {
     data object FirstPage : OnboardingModel(
        image = R.drawable.logo2,
        title = "Selamat Datang di \nAgrojaya",
        description = "Selamat datang di Agrojaya, solusi praktis untuk mewujudkan kebun urban di rumah Anda! Dengan Agrojaya, Anda bisa memulai perjalanan urban farming yang mudah dan efektif, bahkan di area terbatas."
    )

    data object SecondPage : OnboardingModel(
        image = R.drawable.onbording1,
        title = "Pemasangan Sistem \nPertanian Urban Farming",
        description = "Agrojaya menyediakan jasa pemasangan sistem urban farming lengkap, mulai dari hidroponik hingga vertikultur. Kami membantu menciptakan lingkungan hijau di area perkotaan untuk memenuhi kebutuhan pangan sehat Anda."
    )

    data object ThirdPage : OnboardingModel(
        image = R.drawable.onbording2,
        title = "Pilihan Bibit dan \nPerlengkapan Berkualitas",
        description = "Kami menyediakan beragam bibit tanaman berkualitas tinggi, mulai dari sayuran, buah, hingga rempah-rempah. Mulailah berkebun dengan perlengkapan terbaik untuk hasil panen yang memuaskan!"
    )
}
