package dev.borisochieng.autocaretag.ui.manage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.shape
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography

@Composable
fun ClientSearchBar(
    modifier: Modifier = Modifier,
    //viewModel: ClientScreenViewModel
    query: String,
    onQueryChange: (String) -> Unit
) {
//    var text by rememberSaveable { mutableStateOf("") }
//    var expanded by rememberSaveable { mutableStateOf(false) }
//
//
//    val textState = remember { mutableStateOf(TextFieldValue("")) }
//    val isSearchViewVisible = remember { mutableStateOf(false) }

    TextField(
        value = query,
        onValueChange = { newValue ->
            onQueryChange(newValue)
        },
        shape = shape.button,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.container,
            unfocusedContainerColor = colorScheme.container,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = colorScheme.primary
        ),
        placeholder = {
            Text(
                text = "Search Client",
                style = typography.bodyLight
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search clients...",
                tint = Color.Gray
            )
        }
    )


//    SearchBar(
//        modifier = Modifier
//            .clickable {
//                isSearchViewVisible.value = true
//            }
//            .border(shape = RoundedCornerShape(8.dp), width = 1.dp, color = Color(0xFFBDE3FF)),
//        query = text,
//        onQueryChange = { text = it },
//        onSearch = { },
//        active = expanded,
//        onActiveChange = { expanded = it },
//        shape = RoundedCornerShape(8.dp),
//        placeholder = {
//            Text(
//                text = "Search client / vehicle",
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    lineHeight = 21.sp,
//                    fontWeight = FontWeight(400),
//                    color = Color(0xFFB3B3B3),
//                    textAlign = TextAlign.Center,
//                )
//            )
//        },
//        colors = SearchBarDefaults.colors(
//            containerColor = Color.Transparent
//        ),
//        leadingIcon = {
//            Icon(
//                painter = painterResource(id = R.drawable.search_icon),
//                contentDescription = "search icon"
//            )
//        },
//        trailingIcon = {
//
//           if(!expanded) {
//                IconButton(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .width(46.dp)
//                        .height(56.dp)
//                        .background(
//                            color = Color(0xFF0983FF),
//                            shape = RoundedCornerShape(
//                                topStart = 0.dp,
//                                topEnd = 8.dp,
//                                bottomStart = 0.dp,
//                                bottomEnd = 8.dp
//                            )
//                        )
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.filter),
//                        contentDescription = "filter",
//                        tint = Color.White
//                    )
//                }
//            }
//            else{
//                Icon(painter = painterResource(id = R.drawable.close_fill0_wght400_grad0_opsz24),
//                    modifier = Modifier.clickable {
//
//                    },
//
//                    contentDescription ="Close" )
//           }
//
//        }
//
//    ) {
//
//
//    }
//    /*if (isSearchViewVisible.value)  {
//          Column {
//              SearchView(textState, closeTextView = {
//                  isSearchViewVisible.value = false
//              })
//              ItemList(state = textState)
//          }
//      }*/
}


@Preview(showBackground = true)
@Composable
fun ManageScreenPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) { ClientSearchBar(Modifier,"") {} }
}