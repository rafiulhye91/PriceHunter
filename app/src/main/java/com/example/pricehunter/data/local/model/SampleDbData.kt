package com.example.pricehunter.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pricehunter.domain.model.SampleDomainData

@Entity
data class SampleDbData(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "details")
    val details: String?
) {
    fun toSampleDomainData(): SampleDomainData {
        return SampleDomainData(
            name = this.title
        )
    }
}
