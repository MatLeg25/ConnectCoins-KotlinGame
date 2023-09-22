package com.example.connectcoins.data.models

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class Player(
    val name: String,
    val color: List<Color>
) {

    val id: String = UUID.randomUUID().toString()

    override fun equals(other: Any?): Boolean {
        // Check for reference equality
        if (this === other) return true
        // Check if the other object is null or of a different class
        if (other == null || other.javaClass != this.javaClass) return false
        // Cast the other object to the same type
        other as Player
        // Compare objects based on their id property
        return this.id == other.id
    }

    override fun hashCode(): Int {
        super.hashCode()
        return id.hashCode()
    }
}