package com.airbnb.lottie.sample.compose.examples

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.airbnb.lottie.sample.compose.R
import kotlinx.coroutines.launch

@Composable
fun BasicUsageExamplesPage() {
    UsageExamplePageScaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.height(16.dp))
            ExampleCard("Example 1", "Repeat once") {
                Example1()
            }
            ExampleCard("Example 2", "Repeat forever") {
                Example2()
            }
            ExampleCard("Example 3", "Repeat forever from 50% to 75%") {
                Example3()
            }
            ExampleCard("Example 4", "Using LottieAnimationResult") {
                Example4()
            }
            ExampleCard("Example 5", "Using LottieComposition") {
                Example5()
            }
            ExampleCard("Example 6", "Splitting out the animation driver") {
                Example6()
            }
            ExampleCard("Example 7", "Toggle on click - click me") {
                Example7()
            }
        }
    }
}

/**
 * Nice and easy... This will play one time as soon as the composition loads
 * then it will stop.
 */
@Composable
private fun Example1() {
    LottieAnimation(LottieCompositionSpec.RawRes(R.raw.heart))
}

/**
 * This will repeat forever.
 */
@Composable
private fun Example2() {
    LottieAnimation(
        LottieCompositionSpec.RawRes(R.raw.heart),
        repeatCount = LottieConstants.RepeatForever,
    )
}

/**
 * This will repeat between 50% and 75% forever.
 */
@Composable
private fun Example3() {
    LottieAnimation(
        LottieCompositionSpec.RawRes(R.raw.heart),
        repeatCount = LottieConstants.RepeatForever,
        clipSpec = LottieClipSpec.MinAndMaxProgress(0.5f, 0.75f),
    )
}

/**
 * Here, you can check the result for loading/failure states.
 */
@Composable
private fun Example4() {
    val compositionResult = lottieComposition(LottieCompositionSpec.RawRes(R.raw.heart))
    when {
        compositionResult.isLoading -> {
            Text("Animation is loading...")
        }
        compositionResult.isFailure -> {
            Text("Animation failed to load")
        }
        else -> {
            LottieAnimation(compositionResult())
        }
    }
}

/**
 * If you just want access to the composition itself, you can use the delegate
 * version of lottieComposition like this.
 */
@Composable
private fun Example5() {
    val composition by lottieComposition(LottieCompositionSpec.RawRes(R.raw.heart))
    LottieAnimation(
        composition,
        progress = 0.85f,
    )
}

/**
 * Here, you have access to the composition and animation individually.
 */
@Composable
private fun Example6() {
    val composition by lottieComposition(LottieCompositionSpec.RawRes(R.raw.heart))
    val progress by animateLottieComposition(
        composition,
        repeatCount = LottieConstants.RepeatForever,
    )
    LottieAnimation(
        composition,
        progress,
    )
}

/**
 * Here, you can toggle playback by clicking the animation.
 */
@Composable
private fun Example7() {
    val scope = rememberCoroutineScope()
    LottieAnimation(
        LottieCompositionSpec.RawRes(R.raw.heart),
        repeatCount = LottieConstants.RepeatForever,
        modifier = Modifier
            .clickable {
                scope.launch {

                }
            }
    )
}

@Preview
@Composable
fun ExampleCardPreview() {
    ExampleCard("Example 1", "Heart animation") {
        LottieAnimation(LottieCompositionSpec.RawRes(R.raw.heart))
    }
}