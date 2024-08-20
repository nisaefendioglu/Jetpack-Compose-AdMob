
# Compose AdMob Integration

This repository demonstrates how to integrate AdMob ads into a Jetpack Compose Android application. The project includes examples of displaying banner ads and interstitial ads within a Compose UI, utilizing Google's AdMob SDK.

## Features

- **Banner Ads**: Display banner ads within your Compose UI using `AdView`.
- **Interstitial Ads**: Load and show interstitial ads on specific user actions.
- **LazyColumn Integration**: Seamlessly integrate ads within a `LazyColumn` list.

## Screens 

https://github.com/user-attachments/assets/24bd337d-ab79-4bcf-b40a-303da1d57308

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/nisaefendioglu/jetpack-compose-admob.git
   ```

2. Open the project in Android Studio.

3. Sync the project with Gradle to install dependencies.

### Usage

1. **Initialize AdMob SDK**: Before loading any ads, initialize the AdMob SDK in your `MainActivity`:

   ```kotlin
   MobileAds.initialize(this) {
       // AdMob SDK is ready
   }
   ```

2. **Display Banner Ads**: Use the `AdmobBanner` composable to show banner ads in your UI:

   ```kotlin
   @Composable
   fun AdmobBanner(modifier: Modifier = Modifier) {
       AndroidView(
           modifier = Modifier.fillMaxWidth(),
           factory = { context ->
               AdView(context).apply {
                   adSize = AdSize.BANNER
                   adUnitId = "YOUR_AD_UNIT_ID"
                   loadAd(AdRequest.Builder().build())
               }
           }
       )
   }
   ```

3. **Show Interstitial Ads**: Call the `showInterstialAd()` method on user actions to display interstitial ads:

   ```kotlin
   private fun showInterstialAd() {
       InterstitialAd.load(
           this,
           "YOUR_AD_UNIT_ID",
           AdRequest.Builder().build(),
           object : InterstitialAdLoadCallback() {
               override fun onAdFailedToLoad(adError: LoadAdError) {
                   // Handle error
               }

               override fun onAdLoaded(interstitialAd: InterstitialAd) {
                   interstitialAd.show(this@MainActivity)
               }
           }
       )
   }
   ```

4. **LazyColumn with Ads**: Integrate ads within a `LazyColumn` using the `MyComposeList` composable:

   ```kotlin
   @Composable
   fun MyComposeList(modifier: Modifier = Modifier, data: List<Any>) {
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
   ```
