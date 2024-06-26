package com.nurulhidayati_222013.rumahsayur.model

import com.google.firebase.Timestamp

data class Riwayat(
    val idRiwayat: String,
    val strName: String,
    val strImage: String,
    val intPrice: Int,
    val intTotPrice: Int,
    val intQuantity: Int,
    val intOngkir: Int,
    val strAlamat: String,
    val strJalan: String,
    val strTanggal: String,
    val idCustomer: String,
    val idFood: String,
    )
