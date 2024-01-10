package ru.otus.otuskotlin.real.estate.advertisements.blackbox.test.action.v1

import ok.real.estate.advertisements.api.v1.models.*

val debug = AdDebug(mode = AdRequestDebugMode.STUB, stub = AdRequestDebugStubs.SUCCESS)

val someCreateAd = AdCreateObject(
    realEstateType = RealEstateType.FLAT,
    realEstateYear = "2018",
    realEstateArea = "82",
    description = "Продается квартира на левом берегу г. Астаны",
    adStatus = AdStatus.ACTIVE,
    visibility = AdVisibility.PUBLIC
)
