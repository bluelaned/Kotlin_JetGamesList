package com.blueland.jetgames.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blueland.jetgames.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.padding(top = 70.dp),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.image_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(225.dp)
                    .width(225.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(12.dp)) // Added space between image and text

            Text(
                text = "Joshua Feliex",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "felixjosua09@gmail.com",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileScreen()
}
