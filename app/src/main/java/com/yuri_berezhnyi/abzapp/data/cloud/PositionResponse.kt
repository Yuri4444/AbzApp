package com.yuri_berezhnyi.abzapp.data.cloud

import com.google.gson.annotations.SerializedName

data class PositionResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("positions")
    val positionDto: List<PositionDto>
) {
    data class PositionDto(
        val id: Int,
        val name: String
    )
}