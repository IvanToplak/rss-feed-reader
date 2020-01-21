package agency.five.cu_it_rssfeedproject.data.db.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
    tableName = "feed_item",
    foreignKeys = [ForeignKey(
        entity = DbFeed::class,
        parentColumns = ["id"],
        childColumns = ["feed_id"],
        onDelete = CASCADE
    )],
    indices = [Index("feed_id", "link", unique = true),
    Index("publication_date")]
)
data class DbFeedItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "feed_id") val feedId: Int = 0,
    val title: String = "",
    @ColumnInfo(name = "publication_date") val publicationDate: Date?,
    val link: String = ""
)