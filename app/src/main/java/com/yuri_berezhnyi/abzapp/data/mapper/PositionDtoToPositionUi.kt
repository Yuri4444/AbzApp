package com.yuri_berezhnyi.abzapp.data.mapper

import com.yuri_berezhnyi.abzapp.data.cloud.PositionResponse
import com.yuri_berezhnyi.abzapp.ui.users.PositionUI

fun userDtoToUserUi(positionDto: PositionResponse.PositionDto) = PositionUI(
    id = positionDto.id,
    name = positionDto.name
)