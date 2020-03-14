package org.wisdomrider.lazylibrary.modules

//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.InterstitialAd
//import com.google.android.gms.ads.MobileAds
//import com.google.android.gms.ads.initialization.InitializationStatus
//import org.wisdomrider.lazylibrary.LazyModule
//import org.wisdomrider.lazylibrary.NotFoundException
//import java.lang.Exception
//
//class GoogleAdsModule : LazyModule() {
//
//    var interstitialAds = ArrayList<InterstitialAd>()
//    fun initialize(whenInitialized: (status: InitializationStatus) -> Unit) {
//        MobileAds.initialize(context) {
//            whenInitialized(it)
//        }
//    }
//
//    fun addInterstitial(adId: String) {
//        interstitialAds.add(InterstitialAd(context))
//        interstitialAds[interstitialAds.size - 1].adUnitId = adId
//
//    }
//
//    fun loadInterstitialAds(
//        which: String? = null,
//        adRequest: AdRequest = AdRequest.Builder().build()
//    ) {
//        try {
//            if (which == null) {
//                interstitialAds.forEach {
//                    it.loadAd(adRequest)
//                }
//            } else {
//                interstitialAds.filter { it.adUnitId.toString() == which }[0].loadAd(adRequest)
//            }
//        } catch (e: Exception) {
//            throw NotFoundException(e.message)
//        }
//    }
//
//    fun showAdIfLoaded(which: String) {
//        try {
//            var x = interstitialAds.filter { it.adUnitId.toString() == which }[0]
//            if (x.isLoaded) x.show()
//        } catch (e: Exception) {
//            throw NotFoundException(e.message)
//        }
//    }
//
//
//
//}