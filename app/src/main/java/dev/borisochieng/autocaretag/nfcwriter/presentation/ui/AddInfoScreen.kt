package dev.borisochieng.autocaretag.nfcwriter.presentation.ui

/*@Composable
fun AddAddress(viewModel: AddInfoViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    //Date data
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date = remember {
        mutableStateOf("$day/$month/$year")
    }

    val datePickerDialog = android.app.DatePickerDialog(
        context, { _: DatePicker, yearr: Int, monthh: Int, dayOfMonth: Int ->
            val selectedDateTime = Calendar.getInstance().apply {
                set(yearr, monthh, dayOfMonth)
            }.timeInMillis
            viewModel.onEvent(InfoScreenEvents.EnteredAppointmentDate(selectedDateTime.toString()))
        }, year, month, day
    )

    val systemTime = System.currentTimeMillis()
    val deliveryDateLong =
        if (viewModel.appointmentDate.value.appointmentDate.isNotEmpty()) viewModel.appointmentDate.value.appointmentDate.toLong() else systemTime
    val simpleDate = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    val deliveryDate = simpleDate.format(deliveryDateLong)




    LazyColumn {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Name",
// Text/md: Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2A2A2A),

                            )
                    )


                    OutlinedTextField(value = viewModel.customerName.value.customerName//viewModel.lastName,
                        , onValueChange = {

                            viewModel.onEvent(InfoScreenEvents.EnteredCustomerName(it))
                        }, placeholder = {
                            Text(
                                text = "Enter Customer  name",
// Text/md: Regular
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF9D9D9D),

                                    )
                            )
                        }, modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Cloth Type",
// Text/md: Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF2A2A2A),

                                )
                        )


                        OutlinedTextField(value = viewModel.clothType.value.clothType,
                            onValueChange = {

                                viewModel.onEvent(InfoScreenEvents.EnteredClothType(it))
                            },
                            placeholder = {
                                Text(
                                    text = "Enter the Cloth type",
// Text/md: Regular
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF9D9D9D),

                                        )
                                )
                            },
                            modifier = Modifier.size(width = 220.dp, height = 56.dp)
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.clickable {
                            datePickerDialog.show()
                        }) {
                        androidx.compose.material3.Text(
                            text = "Delivery Date",
// Text/md: Regular
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 12.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                                color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                                )
                        )
                        OutlinedTextField(
                            value = deliveryDate
                                ?: "Choose the delivery Date", //viewModel.lastName,
                            onValueChange = {

                            },
                            placeholder = {
                                Text(
                                    text = "Choose the delivery date",
// Text/md: Regular
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF9D9D9D),

                                        )
                                )
                            },
                            readOnly = true,
                            leadingIcon = {
                                Icon(painter = painterResource(id = R.drawable.calendar_month_fill1_wght400_grad0_opsz48),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .clickable {
                                            datePickerDialog.show()
                                        }
                                        .size(24.dp))
                            },


                            )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Prefix",
// Text/md: Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF2A2A2A),

                                )
                        )
                        OutlinedTextField(
                            modifier = Modifier.size(
                                width = 100.dp, height = 58.dp
                            ),
                            value = "+234",
                            onValueChange = {
                                // viewModel.onEvent(AddChallengeEvent.Frequency(it))
                            },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.expand_more),
                                    contentDescription = "expand_more"
                                )
                            },
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Phone Number",
// Text/md: Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF2A2A2A),

                                )
                        )
                        OutlinedTextField(
                            value = viewModel.customerPhoneNo.value.customerPhoneNo,
                            onValueChange = {
                                viewModel.onEvent(InfoScreenEvents.EnteredCustomerPhoneNo(it))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "Enter Customer's Phone No",
// Text/md: Regular
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF9D9D9D),

                                        )
                                )
                            },
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material3.Text(
                        text = "Washing instructions",
// Text/md: Regular
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                            color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                            )
                    )
                    OutlinedTextField(
                        value = viewModel.washingInstructions.value.washingInstructions, //viewModel.lastName,
                        onValueChange = {
                            //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                            viewModel.onEvent(InfoScreenEvents.EnteredWashingInstructions(it))
                        },

                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Enter washing Instructions",
// Text/md: Regular
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF9D9D9D),

                                    )
                            )
                        },
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    androidx.compose.material3.Text(
                        text = "Customer's Address",
// Text/md: Regular
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                            color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                            )
                    )
                    OutlinedTextField(value = viewModel.customerAddress.value.customerAddress, //viewModel.lastName,
                        onValueChange = {
                            //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                            viewModel.onEvent(InfoScreenEvents.EnteredCustomerAddress(it))
                        }, placeholder = {
                            Text(
                                text = "Enter Customer's Address",
// Text/md: Regular
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF9D9D9D),

                                    )
                            )
                        }, modifier = Modifier.fillMaxWidth()
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        androidx.compose.material3.Text(
                            text = "Price",
// Text/md: Regular
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 12.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                                color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                                )
                        )
                        OutlinedTextField(value = viewModel.amountToBePaid.value.amountToBePaid, //viewModel.lastName,
                            onValueChange = {
                                //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                                viewModel.onEvent(InfoScreenEvents.EnteredAmountToBePaid(it))
                            }, placeholder = {
                                Text(
                                    text = "Enter Price",
// Text/md: Regular
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF9D9D9D),

                                        )
                                )
                            }, modifier = Modifier.size(width = 220.dp, height = 56.dp)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        androidx.compose.material3.Text(
                            text = "Status",
// Text/md: Regular
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 12.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                                color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                                )
                        )
                        OutlinedTextField(
                            value = viewModel.status.value.status, //viewModel.lastName,
                            onValueChange = {
                                //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                                viewModel.onEvent(InfoScreenEvents.EnteredStatus(it))
                            },
                            placeholder = {
                                Text(
                                    text = "Enter Status",
// Text/md: Regular
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF9D9D9D),

                                        )
                                )
                            },
                        )
                    }
                }
            }
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    onClick = { *//*TODO*//* },
                    color = Color(0x8D89FD8D),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 320.dp, height = 200.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icons8_nfc_round_tag_50),
                            contentDescription = "",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(text = "Tap to check connectivity")

                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(modifier = Modifier
                    .width(141.dp)
                    .height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0072C6), contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(size = 8.dp),
                    onClick = {

                    }) {

                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Write",

                        // Text/lg: SemiBold
                        style = TextStyle(
                            fontSize = 15.sp,

                            fontWeight = FontWeight(600),
                            color = Color.White,
                        )
                    )
                }
            }
        }
    }


}*/


