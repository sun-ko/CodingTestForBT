package com.example.codingtestforbt.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Source(
    val id: String? = null,
    val name: String? = null
): Parcelable