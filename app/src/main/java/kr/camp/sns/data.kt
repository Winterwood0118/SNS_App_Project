package kr.camp.sns

import kr.camp.sns.data.Posting

val itemList by lazy {
    mutableListOf(
        "test id1" to Posting(R.drawable.img_donut_glazeddonut, R.string.text_test.toString()),
        "test id2" to Posting(R.drawable.img_donut_glazeddonut, "(R.string.text_test)"),
        "test id3" to Posting(R.drawable.img_donut_glazeddonut, "(R.string.text_test)"),
        "test id4" to Posting(R.drawable.img_donut_glazeddonut, "getString(R.string.text_test)"),
        "test id5" to Posting(R.drawable.img_donut_glazeddonut, "getString(R.string.text_test)"),
        "test id6" to Posting(R.drawable.img_donut_glazeddonut, "getString(R.string.text_test)")
    )
}