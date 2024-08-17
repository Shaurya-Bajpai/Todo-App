package com.example.to_dolist.home

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_dolist.R

@Composable
fun topbar(title: String) {
    TopAppBar(
        title = {
            Text(text = title,
                modifier = Modifier
                    .heightIn(8.dp),
                color = colorResource(id = R.color.white)
            )
        },
        elevation = 10.dp,
        backgroundColor = colorResource(id = R.color.topbar_color)
    )

}

@Preview(showBackground = true)
@Composable
fun pPreview() {
    topbar("Todo")
}