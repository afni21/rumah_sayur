package com.nurulhidayati_222013.rumahsayur.model

data class Pesanan(
    val idPesanan: String,
    val strName: String,
    val strImage: String,
    val intPrice: Int,
    val intQuantity: Int,
    val idCustomer: String,
    val idFood: String,
    )
