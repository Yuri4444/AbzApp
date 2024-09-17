package com.yuri_berezhnyi.abzapp.data.mapper

import com.yuri_berezhnyi.abzapp.data.UserCache
import com.yuri_berezhnyi.abzapp.data.cloud.UserDTO

fun userDtoToUserCache(userDTO: UserDTO) = UserCache(
    id = userDTO.id,
    name = userDTO.name,
    email = userDTO.email,
    phone = userDTO.phone,
    position = userDTO.position,
    positionId = userDTO.positionId,
    registrationTimestamp = userDTO.registrationTimestamp,
    photo = userDTO.photo
)