package org.wisdomrider.lazylibrarydemo.sqlite

import org.wisdomrider.lazylibrary.modules.sqlite.SqliteAnnotations
import java.io.Serializable

class Books(
    @SqliteAnnotations.Primary
    @SqliteAnnotations.AutoIncrement
    var id: Int?= null,
    var name: String? = "In Search of Lost Time",
    var price: Double? = 40.00,
    var author: String? = "Marcel Proust",
    var description: String? = "Swann's Way, the first part of A la recherche de temps perdu, Marcel Proust's seven-part cycle, was published in 1913. In it, Proust introduces the themes that run through the entire work."
): Serializable