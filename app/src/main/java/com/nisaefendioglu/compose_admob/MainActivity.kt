package com.nisaefendioglu.compose_admob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.nisaefendioglu.compose_admob.data.AdDataItem
import com.nisaefendioglu.compose_admob.data.DataItem
import com.nisaefendioglu.compose_admob.ui.theme.ComposeAdMobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        setContent {
            ComposeAdMobTheme {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray),
                ) {
                    val data = generateData()
                    AdmobBanner(modifier = Modifier.fillMaxWidth())
                    MyComposeList(
                        data = data, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                }

            }
        }
    }

    private fun generateData(): MutableList<Any> {
        val data = mutableListOf<Any>()
        data.add(
            DataItem(
                "Hamburger Menu",
                "https://www.summahealth.org/-/media/project/summahealth/website/page-content/flourish/2_18a_fl_fastfood_400x400.webp?la=en&h=400&w=400&hash=145DC0CF6234A159261389F18A36742A"
            )
        )
        data.add(AdDataItem("Test 1"))
        data.add(
            DataItem(
                "Salad Menu",
                "https://www.foodandwine.com/thmb/IuZPWAXBp4YaT9hn1YLHhuijT3k=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/FAW-recipes-big-italian-salad-hero-83e6ea846722478f8feb1eea33158b00.jpg"
            )
        )
        data.add(AdDataItem("Test 2"))
        data.add(
            DataItem(
                "Fish Menu",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQa3ZdLYdHpIP_dQNZPNYZqAqtNiMR005xa1Q&s"
            )
        )
        return data
    }

    private fun showInterstialAd() {
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712", //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(this@MainActivity)
                }
            }
        )
    }


    @Composable
    fun MyComposeList(
        modifier: Modifier = Modifier,
        data: List<Any>,
    ) {

        LazyColumn(state = rememberLazyListState()) {
            items(data.size) {
                val item = data[it]
                if (item is DataItem) {
                    MySimpleListItem(item = item)
                } else {
                    AdmobBanner(modifier)
                }
            }
        }


    }


    @Composable
    fun MySimpleListItem(item: DataItem) {
        Box(
            Modifier
                .padding(16.dp)
                .clickable {
                    showInterstialAd()
                }) {
            Card {
                Image(
                    painter = rememberAsyncImagePainter(item.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.8f)
                )
                item.text?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(0.4f))
                            .padding(8.dp, 4.dp, 8.dp, 4.dp),
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                }
            }
        }
    }

    @Composable
    fun AdmobBanner(modifier: Modifier = Modifier) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                // on below line specifying ad view.
                AdView(context).apply {
                    // on below line specifying ad size
                    //adSize = AdSize.BANNER
                    // on below line specifying ad unit id
                    // currently added a test ad unit id.
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    // calling load ad to load our ad.
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}