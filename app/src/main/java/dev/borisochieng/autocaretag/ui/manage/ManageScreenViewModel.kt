package dev.borisochieng.autocaretag.ui.manage

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ManageScreenViewModel () : ViewModel(){
    private val _vehicleList = mutableStateListOf<Vehicle>()
    val vehicleList: List<Vehicle> = _vehicleList

    init {
        getVehicleList()
    }

   private fun getVehicleList() {

   }
}