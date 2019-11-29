package agency.five.cu_it_rssfeedproject.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "feed",
    indices = [Index("url", unique = true)]
)
data class DbFeed(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    @ColumnInfo(name = "image_url") val imageUrl: String = ""
)