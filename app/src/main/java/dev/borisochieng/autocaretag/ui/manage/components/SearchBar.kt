package dev.borisochieng.autocaretag.ui.manage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.borisochieng.autocaretag.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen() {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }


    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val isSearchViewVisible = remember { mutableStateOf(false) }


    SearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = { },
        active = expanded,
        onActiveChange = { expanded = it },
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = "Search client / vehicle",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFB3B3B3),
                    textAlign = TextAlign.Center,
                )
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "search icon"
            )
        },
        trailingIcon = {

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(46.dp)
                    .height(56.dp)
                    .background(
                        color = Color(0xFF0983FF),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .fillMaxHeight()

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "filter"
                )
            }
        },
        modifier = Modifier
            .clickable {
                isSearchViewVisible.value = true
            }
            .border(width = 1.dp, color = Color(0xFFBDE3FF))
            .padding(0.dp)
    ) {


    }
    /*if (isSearchViewVisible.value)  {
          Column {
              SearchView(textState, closeTextView = {
                  isSearchViewVisible.value = false
              })
              ItemList(state = textState)
          }
      }*/
}


@Preview(showBackground = true)
@Composable
fun ManageScreenPreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
        ){ ManageScreen() }
}