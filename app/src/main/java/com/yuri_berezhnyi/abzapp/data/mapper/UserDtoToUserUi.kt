package com.yuri_berezhnyi.abzapp.data.mapper

import com.yuri_berezhnyi.abzapp.data.UserCache
import com.yuri_berezhnyi.abzapp.data.cloud.UserDTO
import com.yuri_berezhnyi.abzapp.ui.users.UserUi

fun userDtoToUserUi(userDTO: UserDTO) = UserUi(
    id = userDTO.id,
    name = userDTO.name,
    email = userDTO.email,
    phone = userDTO.phone,
    position = userDTO.position,
    positionId = userDTO.positionId,
    registrationTimestamp = userDTO.registrationTimestamp,
    photo = userDTO.photo
)