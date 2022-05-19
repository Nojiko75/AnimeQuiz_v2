package com.nojiko.tanoshi.animequiz_v2.firebase

import com.google.firebase.firestore.QuerySnapshot
import com.nojiko.tanoshi.domain.EasyCharacterQuestion

fun convertToModel(query: QuerySnapshot): List<EasyCharacterQuestion> {
    return query.documents.map { document ->
        EasyCharacterQuestion(
            document.data?.get("right_answer").toString(),
            document.data?.get("image").toString(),
            document.data?.get("answer_a").toString(),
            document.data?.get("answer_b").toString(),
            document.data?.get("answer_c").toString()
        )
    }
}