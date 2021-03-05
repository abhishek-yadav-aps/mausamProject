package com.abhitom.mausamproject.data.provider

import com.abhitom.mausamproject.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}