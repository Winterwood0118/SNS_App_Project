package kr.camp.sns

import kr.camp.sns.data.Posting
// UserRegsitry에서 모든 포스팅 가져오는 방식으로 변경해야 함
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