package ro.vlad.search_data.mapper

import ro.vlad.search_domain.model.ResultEntry

fun ro.vlad.core.data.remote.dto.search.Result.toResultEntry(): ResultEntry {
    return ResultEntry(
        id = id.orEmpty(),
        description = description.orEmpty(),
        image = image.orEmpty(),
        resultType = resultType.orEmpty(),
        title = title.orEmpty()
    )
}