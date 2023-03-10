package com.denchic45.kiro.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

private fun String.toFontPath() = "font/$this.ttf"

val RobotoFamily = FontFamily(
    Font("roboto_thin".toFontPath(), FontWeight.Thin),
    Font("roboto_medium".toFontPath(), FontWeight.Light),
    Font("roboto_regular".toFontPath(), FontWeight.Normal),
    Font("roboto_medium".toFontPath(), FontWeight.Medium),
    Font("roboto_bold".toFontPath(), FontWeight.Bold),
    Font("roboto_black".toFontPath(), FontWeight.Black)
)

val GilroyFamily = FontFamily(
//    Font("gilroy_extralight".toFontPath(), FontWeight.ExtraLight),
//    Font("gilroy_light".toFontPath(), FontWeight.Light),
    Font("gilroy_regular".toFontPath(), FontWeight.Normal),
    Font("gilroy_medium".toFontPath(), FontWeight.Medium),
    Font("gilroy_semibold".toFontPath(), FontWeight.SemiBold),
    Font("gilroy_bold".toFontPath(), FontWeight.Bold),
    Font("gilroy_extrabold".toFontPath(), FontWeight.ExtraBold)
)

val ManropeFamily = FontFamily(
    Font("manrope_extralight".toFontPath(), FontWeight.ExtraLight),
    Font("manrope_light".toFontPath(), FontWeight.Light),
    Font("manrope_regular".toFontPath(), FontWeight.Normal),
    Font("manrope_medium".toFontPath(), FontWeight.Medium),
    Font("manrope_semibold".toFontPath(), FontWeight.SemiBold),
    Font("manrope_bold".toFontPath(), FontWeight.Bold),
    Font("manrope_extrabold".toFontPath(), FontWeight.ExtraBold)
)

val InterFamily = FontFamily(
    Font("inter_thin".toFontPath(), FontWeight.Thin),
    Font("inter_extralight".toFontPath(), FontWeight.ExtraLight),
    Font("inter_light".toFontPath(), FontWeight.Light),
    Font("inter_regular".toFontPath(), FontWeight.Normal),
    Font("inter_medium".toFontPath(), FontWeight.Medium),
    Font("inter_semibold".toFontPath(), FontWeight.SemiBold),
    Font("inter_bold".toFontPath(), FontWeight.Bold),
    Font("inter_extraBold".toFontPath(), FontWeight.ExtraBold),
    Font("inter_extraBlack".toFontPath(), FontWeight.Black),
)

val Typo = Typography(
    displayLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 64.sp,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 52.sp,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 44.sp,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 40.sp,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 36.sp,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
        lineHeight = 28.sp,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 16.sp,
        fontSize = 11.sp
    ),
)