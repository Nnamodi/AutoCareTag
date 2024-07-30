package dev.borisochieng.autocaretag.ui.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.borisochieng.autocaretag.R
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.AddInfoViewModel
import dev.borisochieng.autocaretag.nfc_writer.presentation.viewModel.InfoScreenEvents
import dev.borisochieng.autocaretag.ui.components.CustomTextField
import dev.borisochieng.autocaretag.ui.components.PrimaryButton
import dev.borisochieng.autocaretag.ui.components.WriteDialog
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.colorScheme
import dev.borisochieng.autocaretag.ui.theme.AutoCareTheme.typography
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    onNavigateToScanTag: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: AddInfoViewModel,
) {
    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var vehicleModel by remember { mutableStateOf("") }
    var repair by remember { mutableStateOf("") }
    var selectedAppointmentDate by remember { mutableStateOf("") }
    var selectedNextAppointmentDate by remember { mutableStateOf("") }
    var isDialogForAppointmentDate by remember { mutableStateOf(false) }
    var isDialogForNextAppointmentDate by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var contactError by remember { mutableStateOf<String?>(null) }
    var vehicleModelError by remember { mutableStateOf<String?>(null) }
    var repairError by remember { mutableStateOf<String?>(null) }
    var appointmentDateError by remember { mutableStateOf<String?>(null) }
    var nextAppointmentDateError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()


    if (isDialogForAppointmentDate) {
        DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                selectedAppointmentDate =
                    formatDate(day = selectedDayOfMonth, month = selectedMonth, year = selectedYear)
                viewModel.onEvent(InfoScreenEvents.EnteredAppointmentDate(selectedAppointmentDate))
                Log.d("Selected Date", selectedAppointmentDate)
                appointmentDateError = checkIfDateIsToday(selectedAppointmentDate)
                isDialogForAppointmentDate = false

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    if (isDialogForNextAppointmentDate) {
        DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                selectedNextAppointmentDate =
                    formatDate(day = selectedDayOfMonth, month = selectedMonth, year = selectedYear)
                viewModel.onEvent(InfoScreenEvents.EnteredNextAppointmentDate(selectedNextAppointmentDate))
                nextAppointmentDateError = checkIfDateIsInFuture(selectedNextAppointmentDate)
                isDialogForNextAppointmentDate = false

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    val isButtonEnabled = listOf(
        viewModel.customerName.value.customerName,
        contact,
        vehicleModel,
        repair,
        selectedAppointmentDate,
        selectedNextAppointmentDate
    ).all { it.isNotEmpty() } && appointmentDateError == null && nextAppointmentDateError == null

    if (viewModel.buttonEnabled.value) {
        WriteDialog(
            viewModel = viewModel,
            onCancel = {
                viewModel.writeButtonState(false)
            },
            onOk = {
                viewModel.writeButtonState(false)

            }
        )

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.write_to_nfc),
                        style = typography.title
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                R.string.navigate_up
                            )
                        )
                    }
                },
            )
        },

        ) { innerPadding ->
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
                    style = typography.bodyLarge,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )



                CustomTextField(
                    label = stringResource(R.string.name_label),
                    placeHolder = stringResource(R.string.name_placeholder),
                    inputType = String,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    inputValue = viewModel.customerName.value.customerName,
                    onInputValueChange = {
                        viewModel.onEvent(InfoScreenEvents.EnteredCustomerName(it))
                        name = it
                        nameError = validateTextField("Name", it)
                    },
                    errorMessage = nameError
                )



                CustomTextField(
                    label = stringResource(R.string.contact_label),
                    placeHolder = stringResource(R.string.contact_placeholder),
                    inputType = Int,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    inputValue = viewModel.customerPhoneNo.value.customerPhoneNo,
                    onInputValueChange = {it ->
                        viewModel.onEvent(InfoScreenEvents.EnteredCustomerPhoneNo(it))
                        contact = it
                        contactError = validateTextField("Contact Details", it)
                    },
                    errorMessage = contactError
                )



                CustomTextField(
                    label = stringResource(R.string.vehicle_model_label),
                    placeHolder = stringResource(R.string.vehicle_model_placeholder),
                    inputType = String,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    inputValue = viewModel.vehicleModel.value.vehicleModel,
                    onInputValueChange = {
                        viewModel.onEvent(InfoScreenEvents.EnteredVehicleModel(it))
                        vehicleModel = it
                        vehicleModelError = validateTextField("Vehicle Model", it)
                    },
                    errorMessage = vehicleModelError
                )


                CustomTextField(
                    label = stringResource(R.string.repair_label),
                    placeHolder = stringResource(R.string.repair_placeholder),
                    inputType = String,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    inputValue = viewModel.workDone.value.workDone,
                    onInputValueChange = {
                        viewModel.onEvent(InfoScreenEvents.EnteredWorkDone(it))
                        repair = it
                        repairError = validateTextField("Maintenance Done", it)
                    },
                    errorMessage = repairError
                )



                CustomTextField(
                    label = stringResource(R.string.appointment_date_label),
                    placeHolder = stringResource(R.string.appointment_date_placeholder),
                    inputType = String,
                    isTrailingIcon = true,
                    onTrailingIconClick = {
                        isDialogForAppointmentDate = !isDialogForAppointmentDate
                    },
                    inputValue = viewModel.appointmentDate.value.appointmentDate ?: "DD-MM-YYYY",
                    onInputValueChange = {
                      //viewModel.onEvent(InfoScreenEvents.EnteredAppointmentDate(it))
                        appointmentDateError = validateTextField("Appointment Date", it)
                    },
                    errorMessage = appointmentDateError
                )



                CustomTextField(
                    label = stringResource(R.string.next_appointment_date_label),
                    placeHolder = stringResource(R.string.next_appointment_date_placeholder),
                    inputType = String,
                    isTrailingIcon = true,
                    onTrailingIconClick = {
                        isDialogForNextAppointmentDate = !isDialogForNextAppointmentDate
                    },
                    inputValue = viewModel.nextAppointmentDate.value.nextAppointmentDate?: " DD-MM-YYYY",
                    onInputValueChange = {
                        selectedNextAppointmentDate = it
                        nextAppointmentDateError =
                            validateTextField("Next Appointment Date", it)
                    },
                    errorMessage = nextAppointmentDateError
                )

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    onClick = {
                        onNavigateToScanTag()
                              viewModel.writeButtonState(true)
                              },
                    label = stringResource(R.string.bt_write_to_nfc),
                    isEnabled = isButtonEnabled
                )

            }
        }

    }
}

fun formatDate(day: Int, month: Int, year: Int): String {
    val calendar = Calendar.getInstance().apply {
        set(year, month, day)
    }
    val format = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
    return format.format(calendar.time)
}

fun checkIfDateIsInFuture(dateString: String): String? {
    val dateFormat = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
    val inputDate = dateFormat.parse(dateString)
    val currentDate = Calendar.getInstance().time

    return if (inputDate != null && inputDate.before(currentDate)) {
        "The date must be in the future"
    } else {
        null
    }
}

fun checkIfDateIsToday(dateString: String): String? {
    val dateFormat = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
    val inputDate = dateFormat.parse(dateString)
    // Get today's date and set the time to midnight
    val currentDate = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time

    // Get the input date and set the time to midnight
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

fun validateTextField(label: String, input: String): String? =
    if (input.isEmpty()) "$label cannot be empty" else null

/*
@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    AddScreen(onNavigateToScanTag = {}, onNavigateUp = {}, viewModel = AddInfoViewModel())
}*/
