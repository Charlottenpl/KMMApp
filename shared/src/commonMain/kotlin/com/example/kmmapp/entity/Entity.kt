package com.example.kmmapp.entity

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 火箭发射信息类
 */
@Serializable //标记这个类可以被序列化
data class RocketLaunch(
    @SerialName("flight_number")
    var flightNumber: Int, //飞行编号

    @SerialName("mission_name")
    var missionName: String, //任务名称

    @SerialName("date_utc")
    val launchDateUTC: String,

    @SerialName("details")
    val details: String?,

    @SerialName("success")
    val launchSuccess: Boolean?,

    @SerialName("links")
    val links: Links
){
    var launchYear = launchDateUTC.toInstant().toLocalDateTime(TimeZone.UTC).year
}

@Serializable
data class Links(
    @SerialName("patch")
    val patch: Patch?,
    @SerialName("article")
    val article: String?
)

@Serializable
data class Patch(
    @SerialName("small")
    val small: String?,
    @SerialName("large")
    val large: String?
)