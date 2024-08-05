package dev.borisochieng.autocaretag.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.nfcwriter.domain.TagInfo
import dev.borisochieng.autocaretag.nfcwriter.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.nfcwriter.presentation.viewModel.InfoScreenEvents
import dev.borisochieng.autocaretag.nfcwriter.presentation.viewModel.UiEvent
import dev.borisochieng.autocaretag.ui.components.CustomTextField
import dev.borisochieng.autocaretag.ui.components.Inputs
import dev.borisochieng.autocaretag.ui.components.PrimaryButton
import dev.borisochieng.autocaretag.ui.navigation.Screens
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import dev.borisochieng.autocaretag.ui.theme.AutoCareTagTheme
import java.util.UUID
import java.util.UUID.randomUUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navigate: (Screens) -> Unit,
    viewModel: AddInfoViewModel,
    tag: Tag? = null,
    setupNFC: () -> Unit
) {
    var isDialogForAppointmentDate by remember { mutableStateOf(false) }
    var isDialogForNextAppointmentDate by remember { mutableStateOf(false) }

    val nfcWriteState by viewModel.nfcWriteState.collectAsState()

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val isButtonEnabled by viewModel.buttonEnabled.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val nameEntered = remember { mutableStateOf(false) }
    val nameError by remember {
        derivedStateOf {
            validateTextField(
                label = "Name",
                inputEntered = nameEntered.value,
                input = viewModel.customerName.value.customerName
            )
        }
    }
    val contactEntered = remember { mutableStateOf(false) }
    val contactError by remember {
        derivedStateOf {
            validateTextField(
                label = "Contact Details",
                inputEntered = contactEntered.value,
                input = viewModel.customerPhoneNo.value.customerPhoneNo
            )
        }
    }
    val vehicleModelEntered = remember { mutableStateOf(false) }
    val vehicleModelError by remember {
        derivedStateOf {
            validateTextField(
                label = "Vehicle Model",
                inputEntered = vehicleModelEntered.value,
                input = viewModel.vehicleModel.value.vehicleModel
            )
        }
    }
    val repairEntered = remember { mutableStateOf(false) }
    val repairError by remember {
        derivedStateOf {
            validateTextField(
                label = "Maintenance Done",
                inputEntered = repairEntered.value,
                input = viewModel.note.value.note
            )
        }
    }
    val dateIconClicked = remember { mutableStateOf(false) }
    val appointmentDateError by remember {
        derivedStateOf {
            checkIfDateIsToday(
                dateIconClicked.value && !isDialogForAppointmentDate,
                viewModel.appointmentDate.value.appointmentDate
            )
        }
    }
    val nextDateIconClicked = remember { mutableStateOf(false) }
    val nextAppointmentDateError by remember {
        derivedStateOf {
            checkIfDateIsInFuture(
                nextDateIconClicked.value && !isDialogForNextAppointmentDate,
                viewModel.nextAppointmentDate.value.nextAppointmentDate
            )
        }
    }

    if (isDialogForAppointmentDate) {
        ShowDatePickerDialog(context, calendar) { selectedDate ->
            viewModel.onEvent(InfoScreenEvents.EnteredAppointmentDate(selectedDate))
            isDialogForAppointmentDate = false
        }
    }

    if (isDialogForNextAppointmentDate) {
        ShowDatePickerDialog(context, calendar) { selectedDate ->
            viewModel.onEvent(InfoScreenEvents.EnteredNextAppointmentDate(selectedDate))
            isDialogForNextAppointmentDate = false
        }
    }

//    if (isWriteDialogVisible) {
//        WriteDialog(
//            viewModel = viewModel,
//            onCancel = { isWriteDialogVisible = false },
//            navigate = {
//                navigate(Screens.ClientDetailsScreen(client.clientId))
//            }
//
//        )
//    }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateToClientAddedScreen -> {
                    navigate(Screens.ClientAddedScreen)
                }

                is UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }

                else -> {
                    Log.w("AddScreen", "Unexpected Event: $event")
                }
            }
        }
    }

    LaunchedEffect(nfcWriteState) {
        if (nfcWriteState.errorMessage != null) {
            snackbarHostState.showSnackbar(nfcWriteState.errorMessage!!)
        }
    }

    Scaffold(
        containerColor = colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .background(colorScheme.background)
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart

                    ) {
                        Text(
                            text = "Add Client",
                            style = typography.title,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterStart)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigate(Screens.Back) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Go Back"
                        )

                    }
                }
            )
        },
        content = { innerPadding ->
            val scrollState = rememberScrollState()
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .imePadding(),
                color = colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                        .windowInsetsPadding(WindowInsets.ime),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_screen_title),
                        style = typography.title,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        color = colorScheme.primary
                    )

                    CustomTextField(
                        label = stringResource(R.string.name_label),
                        placeHolder = stringResource(R.string.name_placeholder),
                        inputType = Inputs.Text,
                        isTrailingIcon = false,
                        onTrailingIconClick = {},
                        inputValue = viewModel.customerName.value.customerName,
                        onInputValueChange = {
                            nameEntered.value = true
                            viewModel.onEvent(
                                InfoScreenEvents.EnteredCustomerName(
                                    it
                                )
                            )
                        },
                        errorMessage = nameError
                    )

                    CustomTextField(
                        label = stringResource(R.string.contact_label),
                        placeHolder = stringResource(R.string.contact_placeholder),
                        inputType = Inputs.PhoneNumber,
                        isTrailingIcon = false,
                        onTrailingIconClick = {},
                        inputValue = viewModel.customerPhoneNo.value.customerPhoneNo,
                        onInputValueChange = {
                            contactEntered.value = true
                            viewModel.onEvent(
                                InfoScreenEvents.EnteredCustomerPhoneNo(
                                    it
                                )
                            )
                        },
                        errorMessage = contactError
                    )

                    Text(
                        text = stringResource(R.string.vehicle_details),
                        style = typography.title,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        color = colorScheme.primary
                    )

                    CustomTextField(
                        label = stringResource(R.string.vehicle_model_label),
                        placeHolder = stringResource(R.string.vehicle_model_placeholder),
                        inputType = Inputs.Text,
                        isTrailingIcon = false,
                        onTrailingIconClick = {},
                        inputValue = viewModel.vehicleModel.value.vehicleModel,
                        onInputValueChange = {
                            viewModel.onEvent(
                                InfoScreenEvents.EnteredVehicleModel(it)
                            )
                        },
                        errorMessage = vehicleModelError
                    )

                    CustomTextField(
                        label = stringResource(R.string.repair_label),
                        placeHolder = stringResource(R.string.repair_placeholder),
                        inputType = Inputs.Text,
                        isTrailingIcon = false,
                        onTrailingIconClick = {},
                        inputValue = viewModel.note.value.note,
                        onInputValueChange = {
                            repairEntered.value = true
                            viewModel.onEvent(InfoScreenEvents.EnteredNote(it))
                        },
                        errorMessage = repairError
                    )

                    CustomTextField(
                        label = stringResource(R.string.appointment_date_label),
                        placeHolder = stringResource(R.string.appointment_date_placeholder),
                        inputType = Inputs.Text,
                        isTrailingIcon = true,
                        onTrailingIconClick = {
                            dateIconClicked.value = true
                            isDialogForAppointmentDate = !isDialogForAppointmentDate
                        },
                        inputValue = viewModel.appointmentDate.value.appointmentDate,
                        onInputValueChange = {},
                        errorMessage = appointmentDateError,
                        isReadable = true
                    )

                    CustomTextField(
                        label = stringResource(R.string.next_appointment_date_label),
                        placeHolder = stringResource(R.string.next_appointment_date_placeholder),
                        inputType = Inputs.Text,
                        isTrailingIcon = true,
                        onTrailingIconClick = {
                            nextDateIconClicked.value = true
                            isDialogForNextAppointmentDate = !isDialogForNextAppointmentDate
                        },
                        inputValue = viewModel.nextAppointmentDate.value.nextAppointmentDate,
                        onInputValueChange = {},
                        errorMessage = nextAppointmentDateError,
                        isReadable = true
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    PrimaryButton(
                        onClick = {
                            val info = TagInfo(
                                customerId = randomUUID().toString(),
                                customerName = viewModel.customerName.value.customerName,
                                customerPhoneNo = viewModel.customerPhoneNo.value.customerPhoneNo,
                                vehicleModel = viewModel.vehicleModel.value.vehicleModel,
                                workDone = viewModel.note.value.note,
                                nextAppointmentDate = viewModel.nextAppointmentDate.value.nextAppointmentDate,
                                appointmentDate = viewModel.appointmentDate.value.appointmentDate
                            )
                            tag?.let{
                                viewModel.uploadInfo(tag = it, info = info, setupNFC = setupNFC)
                            }
                            //viewModel.writeButtonState(true)
                        },
                        label = stringResource(R.string.bt_write_to_nfc),
                        isEnabled = isButtonEnabled
                    )
                }
            }
        }
    )
}

@Composable
fun ShowDatePickerDialog(
    context: Context,
    calendar: Calendar,
    onDateSelected: (String) -> Unit
) {
    DatePickerDialog(
        context,
        { _, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedDate = formatDate(selectedDayOfMonth, selectedMonth, selectedYear)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun formatDate(day: Int, month: Int, year: Int): String {
    val calendar = Calendar.getInstance().apply {
        set(year, month, day)
    }
    val format = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
    return format.format(calendar.time)
}

fun checkIfDateIsInFuture(
    dateIconClicked: Boolean,
    dateString: String
): String? {
    if (!dateIconClicked) return null
    if (dateString.isEmpty()) {
        return "Date cannot be empty"
    }
    val dateFormat = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
    val inputDate = dateFormat.parse(dateString)
    val currentDate = Calendar.getInstance().time

    return if (inputDate != null && inputDate.before(currentDate)) {
        "The date must be in the future"
    } else {
        null
    }
}

fun checkIfDateIsToday(
    dateIconClicked: Boolean,
    dateString: String
): String? {
    if (!dateIconClicked) return null
    if (dateString.isEmpty()) {
        return "Date cannot be empty"
    }
    val dateFormat = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
    val inputDate = dateFormat.parse(dateString)
    val currentDate = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time

    val inputCalendar = Calendar.getInstance().apply {
        time = inputDate ?: return "Invalid date format"
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
    return if (inputCalendar != currentDate) {
        "The date must be today"
    } else {
        null
    }
}


fun validateTextField(
    label: String,
    inputEntered: Boolean,
    input: String,
): String? =
    if (input.isEmpty() && inputEntered) "$label cannot be empty" else null


@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    AutoCareTagTheme {
        AddScreen({}, viewModel(),null, {})
    }
}
