package com.vholodynskyi.assignment.db.contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class DbContact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val firstName: String? = null,
    @ColumnInfo val lastName: String? = null,
    @ColumnInfo val email: String? = null,
    @ColumnInfo val photo: String? = null,
)
